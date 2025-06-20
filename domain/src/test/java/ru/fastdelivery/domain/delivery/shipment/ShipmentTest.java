package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);

        var volume = new Volume(100, 100, 100); // любой валидный объем

        var packages = List.of(
                new Pack(weight1, volume),
                new Pack(weight2, volume)
        );

        var shipment = new Shipment(
                packages,
                new CurrencyFactory(code -> true).create("RUB"),
                null, // можно подставить реальные координаты при необходимости
                null
        );

        var totalWeight = shipment.weightAllPackages();

        assertThat(totalWeight.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }
}