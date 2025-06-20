package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.locationPoint.LocationPoint;

public class GeoDistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.0088;

    public static double calculate(LocationPoint from, LocationPoint to) {
        double lat1Rad = Math.toRadians(from.latitude());
        double lat2Rad = Math.toRadians(to.latitude());
        double deltaLat = Math.toRadians(to.latitude() - from.latitude());
        double deltaLon = Math.toRadians(to.longitude() - from.longitude());

        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}

