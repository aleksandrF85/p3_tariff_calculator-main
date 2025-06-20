package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.domain.common.locationPoint.LocationPoint;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TariffCalculateUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
    final GeoProvider geoProvider = mock(GeoProvider.class);

    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    final TariffCalculateUseCase useCase =
            new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider, geoProvider);

    @Test
    @DisplayName("Расчет стоимости доставки с весом и объемом и расстоянием -> успешно")
    void whenCalculateFullTariff_thenSuccess() {
        // 1. Исходные данные
        var weight = new Weight(BigInteger.valueOf(1000)); // 1 кг
        var volume = new Volume(300, 400, 500); // 0.06 м3
        var pack = new Pack(weight, volume);

        var departure = new LocationPoint(55.75, 37.62); // Москва
        var destination = new LocationPoint(43.12, 131.88); // Владивосток

        var shipment = new Shipment(List.of(pack), currency, departure, destination);

        // 2. Заглушки
        when(weightPriceProvider.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(50), currency));
        when(weightPriceProvider.costPerKg()).thenReturn(new Price(BigDecimal.valueOf(100), currency));
        when(volumePriceProvider.pricePerCubicMeter()).thenReturn(BigDecimal.valueOf(15000)); // 15k за м³
        when(geoProvider.minDistanceKm()).thenReturn(450.0);

        // 3. Ожидаемое расстояние (например, 9000 км / 450 = 20)
        var expectedDistanceKm = 9000.0;
        mockStatic(GeoDistanceCalculator.class)
                .when(() -> GeoDistanceCalculator.calculate(departure, destination))
                .thenReturn(expectedDistanceKm);

        // 4. Ожидаемая базовая стоимость
        // по весу: 100 * 1 = 100
        // по объему: 0.06 * 15000 = 900
        // выбирается max = 900
        // 9000 / 450 = 20 → итог: 900 * 20 = 18000.00

        var expected = new Price(BigDecimal.valueOf(18000.00).setScale(2), currency);

        // 5. Проверка
        var actual = useCase.calc(shipment);

        assertThat(actual).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenReturn() {
        var minimal = new Price(BigDecimal.valueOf(100), currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimal);

        var actual = useCase.minimalPrice();

        assertThat(actual).isEqualTo(minimal);
    }
}