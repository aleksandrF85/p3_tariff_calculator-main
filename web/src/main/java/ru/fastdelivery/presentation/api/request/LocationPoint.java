package ru.fastdelivery.presentation.api.request;

import jakarta.validation.constraints.NotNull;

public record LocationPoint(
        @NotNull Double latitude,
        @NotNull Double longitude
) {
    public ru.fastdelivery.domain.common.locationPoint.LocationPoint toDomain(ru.fastdelivery.usecase.GeoProvider geoProvider) {
        return ru.fastdelivery.domain.common.locationPoint.LocationPoint.of(
                latitude,
                longitude,
                geoProvider.minLatitude(),
                geoProvider.maxLatitude(),
                geoProvider.minLongitude(),
                geoProvider.maxLongitude()
        );
    }
}
