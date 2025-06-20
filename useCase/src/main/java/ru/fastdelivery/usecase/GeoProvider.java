package ru.fastdelivery.usecase;


public interface GeoProvider {
    double minLatitude();
    double maxLatitude();
    double minLongitude();
    double maxLongitude();
    double minDistanceKm();
}

