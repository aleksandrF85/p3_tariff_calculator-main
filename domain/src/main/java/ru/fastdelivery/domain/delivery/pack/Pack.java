package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

public class Pack {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));

    private final Weight weight;
    private final Volume volume;

    public Pack(Weight weight, Volume volume) {
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        }
        this.weight = weight;
        this.volume = volume;
    }

    public Weight getWeight() {
        return weight;
    }

    public Volume getVolume() {
        return volume;
    }
}
