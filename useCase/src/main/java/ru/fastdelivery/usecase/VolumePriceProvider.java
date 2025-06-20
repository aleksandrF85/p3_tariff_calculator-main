package ru.fastdelivery.usecase;


import java.math.BigDecimal;

public interface VolumePriceProvider {
    BigDecimal pricePerCubicMeter();
}

