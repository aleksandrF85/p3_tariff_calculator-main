package ru.fastdelivery.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "tariff.volume")
public class VolumeTariffProperties {
    private BigDecimal pricePerM3;

    public BigDecimal getPricePerM3() {
        return pricePerM3;
    }

    public void setPricePerM3(BigDecimal pricePerM3) {
        this.pricePerM3 = pricePerM3;
    }
}

