package ru.fastdelivery.properties.provider;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.usecase.VolumePriceProvider;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "tariff.volume")
public class VolumeTariffProperties implements VolumePriceProvider {
    private BigDecimal pricePerM3;

    @Override
    public BigDecimal pricePerCubicMeter() {
        return pricePerM3;
    }

    public BigDecimal getPricePerM3() {
        return pricePerM3;
    }

    public void setPricePerM3(BigDecimal pricePerM3) {
        this.pricePerM3 = pricePerM3;
    }
}

