package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.locationPoint.LocationPoint;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency,
        LocationPoint departure,
        LocationPoint destination
) {
    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::getWeight)
                .reduce(Weight.zero(), Weight::add);
    }

    public BigDecimal totalVolumeM3() {
        return packages.stream()
                .map(Pack::getVolume)
                .map(Volume::inCubicMeters)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

