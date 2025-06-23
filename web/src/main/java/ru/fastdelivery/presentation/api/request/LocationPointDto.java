package ru.fastdelivery.presentation.api.request;

import jakarta.validation.constraints.NotNull;
import ru.fastdelivery.domain.common.locationPoint.LocationPoint;
import ru.fastdelivery.usecase.GeoProvider;

public record LocationPointDto(
        @NotNull Double latitude,
        @NotNull Double longitude
) {
    public LocationPoint toDomain(GeoProvider geoProvider) {
        return LocationPoint.of(
                latitude,
                longitude,
                geoProvider.minLatitude(),
                geoProvider.maxLatitude(),
                geoProvider.minLongitude(),
                geoProvider.maxLongitude()
        );
    }
}
