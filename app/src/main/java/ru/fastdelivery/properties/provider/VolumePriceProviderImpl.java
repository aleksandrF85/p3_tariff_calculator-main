package ru.fastdelivery.properties.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fastdelivery.config.VolumeTariffProperties;
import ru.fastdelivery.usecase.VolumePriceProvider;

import java.math.BigDecimal;

@Component
public class VolumePriceProviderImpl implements VolumePriceProvider {
    private final VolumeTariffProperties properties;

    public VolumePriceProviderImpl(VolumeTariffProperties properties) {
        this.properties = properties;
    }

    @Override
    public BigDecimal pricePerCubicMeter() {
        return properties.getPricePerM3();
    }
}

