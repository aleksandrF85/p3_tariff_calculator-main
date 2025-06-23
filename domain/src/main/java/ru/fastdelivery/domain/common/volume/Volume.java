package ru.fastdelivery.domain.common.volume;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Volume(int lengthMm, int widthMm, int heightMm) {

    public Volume {
        if (lengthMm <= 0 || widthMm <= 0 || heightMm <= 0) {
            throw new IllegalArgumentException("Габариты должны быть положительными");
        }
        if (lengthMm > 1500 || widthMm > 1500 || heightMm > 1500) {
            throw new IllegalArgumentException("Каждая сторона не должна превышать 1500 мм");
        }

        // округление к ближайшим 50
        lengthMm = roundUpToNearest50(lengthMm);
        widthMm = roundUpToNearest50(widthMm);
        heightMm = roundUpToNearest50(heightMm);
    }

    private static int roundUpToNearest50(int value) {
        return ((value + 49) / 50) * 50;
    }

    public BigDecimal inCubicMeters() {
        BigDecimal volumeMm3 = BigDecimal.valueOf(lengthMm)
                .multiply(BigDecimal.valueOf(widthMm))
                .multiply(BigDecimal.valueOf(heightMm));

        return volumeMm3
                .divide(BigDecimal.valueOf(1_000_000_000), 4, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "Volume{" +
                "length=" + lengthMm +
                ", width=" + widthMm +
                ", height=" + heightMm +
                '}';
    }
}
