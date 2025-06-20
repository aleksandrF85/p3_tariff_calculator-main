package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;

    private final VolumePriceProvider volumePriceProvider;

    private final GeoProvider geoProvider;

    public Price calc(Shipment shipment) {
        // === 1. Расчет стоимости по весу ===
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var minimalWeightPrice = weightPriceProvider.minimalPrice();

        var weightPrice = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalWeightPrice);

        // === 2. Расчет стоимости по объему ===
        var totalVolumeM3 = shipment.totalVolumeM3();
        var volumeRate = volumePriceProvider.pricePerCubicMeter();

        var rawVolumeAmount = volumeRate
                .multiply(totalVolumeM3)
                .setScale(2, RoundingMode.HALF_UP);

        var volumePrice = new Price(rawVolumeAmount, shipment.currency());

        // === 3. Выбираем базовую стоимость (по весу или объему) ===
        var basePrice = weightPrice.max(volumePrice);

        // === 4. Расчет расстояния ===
        double distanceKm = GeoDistanceCalculator.calculate(
                shipment.departure(), shipment.destination()
        );

        // === 5. Масштабирование стоимости на основе расстояния ===
        double minDistanceKm = geoProvider.minDistanceKm(); // например, 450.0
        BigDecimal distanceMultiplier = BigDecimal.valueOf(
                Math.max(1.0, distanceKm / minDistanceKm)
        );

        BigDecimal scaledAmount = basePrice.amount()
                .multiply(distanceMultiplier)
                .setScale(2, RoundingMode.UP); // округляем вверх

        return new Price(scaledAmount, basePrice.currency());
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
