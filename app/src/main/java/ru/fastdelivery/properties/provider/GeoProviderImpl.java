package ru.fastdelivery.properties.provider;

import org.springframework.stereotype.Component;
import ru.fastdelivery.config.GeoProperties;
import ru.fastdelivery.usecase.GeoProvider;

@Component
public class GeoProviderImpl implements GeoProvider {

    private final GeoProperties geoProperties;

    public GeoProviderImpl(GeoProperties geoProperties) {
        this.geoProperties = geoProperties;
    }

    @Override
    public double minLatitude() {
        return geoProperties.getMinLatitude();
    }
    @Override
    public double maxLatitude() {
        return geoProperties.getMaxLatitude();
    }
    @Override
    public double minLongitude() {
        return geoProperties.getMinLongitude();
    }
    @Override
    public double maxLongitude() {
        return geoProperties.getMaxLongitude();
    }
    @Override
    public double minDistanceKm() {
        return geoProperties.getMinDistanceKm();
    }
}

