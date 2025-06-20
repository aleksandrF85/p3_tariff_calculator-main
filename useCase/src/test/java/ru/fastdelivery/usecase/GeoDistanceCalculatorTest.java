package ru.fastdelivery.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.locationPoint.LocationPoint;

import static org.assertj.core.api.Assertions.*;

class GeoDistanceCalculatorTest {

    @Test
    @DisplayName("Расстояние между одной и той же точкой = 0 км")
    void whenSamePoints_thenDistanceIsZero() {
        var point = new LocationPoint(55.7558, 37.6173); // Москва
        double distance = GeoDistanceCalculator.calculate(point, point);

        assertThat(distance).isCloseTo(0.0, within(0.0001));
    }

    @Test
    @DisplayName("Расстояние между Москвой и Санкт-Петербургом ~ 633 км")
    void whenMoscowToSPB_thenDistanceCorrect() {
        var moscow = new LocationPoint(55.7558, 37.6173);
        var spb = new LocationPoint(59.9343, 30.3351);

        double distance = GeoDistanceCalculator.calculate(moscow, spb);

        assertThat(distance).isCloseTo(633.0, within(1.0));
    }

    @Test
    @DisplayName("Расстояние между Москвой и Владивостоком ~ 6415 км")
    void whenMoscowToVladivostok_thenDistanceCorrect() {
        var moscow = new LocationPoint(55.7558, 37.6173);
        var vladivostok = new LocationPoint(43.1155, 131.8855);

        double distance = GeoDistanceCalculator.calculate(moscow, vladivostok);

        assertThat(distance).isCloseTo(6415.0, within(10.0));
    }
}
