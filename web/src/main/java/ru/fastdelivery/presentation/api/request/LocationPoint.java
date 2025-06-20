package ru.fastdelivery.presentation.api.request;

import jakarta.validation.constraints.NotNull;

public record LocationPoint(
        @NotNull Double latitude,
        @NotNull Double longitude
) {
    public ru.fastdelivery.domain.common.locationPoint.LocationPoint toDomain() {
        return new ru.fastdelivery.domain.common.locationPoint.LocationPoint(latitude, longitude);
    }
}
