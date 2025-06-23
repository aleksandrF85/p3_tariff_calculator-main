package ru.fastdelivery.properties.provider;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.fastdelivery.usecase.GeoProvider;

@ConfigurationProperties(prefix = "geo")
@Component
public class GeoProperties implements GeoProvider {
    private double minLatitude;
    private double maxLatitude;
    private double minLongitude;
    private double maxLongitude;
    private double minDistanceKm; // 450 км


    @Override
    public double minLatitude() {
        return minLatitude;
    }

    @Override
    public double maxLatitude() {
        return maxLatitude;
    }

    @Override
    public double minLongitude() {
        return minLongitude;
    }

    @Override
    public double maxLongitude() {
        return maxLongitude;
    }

    @Override
    public double minDistanceKm() {
        return minDistanceKm;
    }

    public double getMinLatitude() {
        return minLatitude;
    }

    public void setMinLatitude(double minLatitude) {
        this.minLatitude = minLatitude;
    }

    public double getMaxLatitude() {
        return maxLatitude;
    }

    public void setMaxLatitude(double maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public double getMinLongitude() {
        return minLongitude;
    }

    public void setMinLongitude(double minLongitude) {
        this.minLongitude = minLongitude;
    }

    public double getMaxLongitude() {
        return maxLongitude;
    }

    public void setMaxLongitude(double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public double getMinDistanceKm() {
        return minDistanceKm;
    }

    public void setMinDistanceKm(double minDistanceKm) {
        this.minDistanceKm = minDistanceKm;
    }
}

