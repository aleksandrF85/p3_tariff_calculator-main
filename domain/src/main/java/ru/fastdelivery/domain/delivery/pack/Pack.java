package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

public record Pack(Weight weight, Volume volume) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));

    public Pack {
        if (weight == null || volume == null) {
            throw new IllegalArgumentException("Weight and volume must not be null");
        }
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        }
    }
}
