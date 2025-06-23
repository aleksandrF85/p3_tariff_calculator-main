package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        var volume = new Volume(300, 400, 500); // допустимые значения

        assertThatThrownBy(() -> new Pack(weight, volume))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Package can't be more than");
    }

    @Test
    void whenWeightIsValid_thenPackCreated() {
        var weight = new Weight(BigInteger.valueOf(10_000));
        var volume = new Volume(350, 600, 250); // допустимые значения

        var pack = new Pack(weight, volume);

        assertThat(pack.weight()).isEqualTo(weight);
        assertThat(pack.volume()).isEqualTo(volume);
    }
}
