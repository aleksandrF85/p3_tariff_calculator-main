package ru.fastdelivery.domain.common.locationPoint;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocationPointTest {

    @Test
    void whenLatitudeOutOfBounds_thenThrow() {
        assertThatThrownBy(() ->
                LocationPoint.of(10.0, 50.0, 45.0, 65.0, 30.0, 96.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Latitude out of bounds");
    }

    @Test
    void whenValidCoordinates_thenSuccess() {
        var point = LocationPoint.of(55.0, 50.0, 45.0, 65.0, 30.0, 96.0);
        assertThat(point.latitude()).isEqualTo(55.0);
        assertThat(point.longitude()).isEqualTo(50.0);
    }
}

