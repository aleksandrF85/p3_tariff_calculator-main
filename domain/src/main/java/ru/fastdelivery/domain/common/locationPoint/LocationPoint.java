package ru.fastdelivery.domain.common.locationPoint;

public record LocationPoint(Double latitude, Double longitude) {

    public LocationPoint {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude and longitude must not be null");
        }
    }
    public static LocationPoint of(Double latitude, Double longitude,
                                   double minLat, double maxLat,
                                   double minLon, double maxLon) {

        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude and longitude must not be null");
        }

        if (latitude < minLat || latitude > maxLat) {
            throw new IllegalArgumentException("Latitude out of bounds: " + latitude);
        }

        if (longitude < minLon || longitude > maxLon) {
            throw new IllegalArgumentException("Longitude out of bounds: " + longitude);
        }

        return new LocationPoint(latitude, longitude);
    }
}


