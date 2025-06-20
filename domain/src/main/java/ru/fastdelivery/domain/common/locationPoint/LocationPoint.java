package ru.fastdelivery.domain.common.locationPoint;

import java.util.Objects;

public record LocationPoint(Double latitude, Double longitude) {

    public LocationPoint {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude and longitude must not be null");
        }
    }

}


