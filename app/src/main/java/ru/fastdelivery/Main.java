package ru.fastdelivery;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Класс запускающий приложение
 */
@OpenAPIDefinition(
        info = @Info(title = "Tariff Calculator API", version = "1.0", description = "API для расчета тарифов")
)
@SpringBootApplication(scanBasePackages = { "ru.fastdelivery" })
@ConfigurationPropertiesScan(value = { "ru.fastdelivery.properties" })
@EnableConfigurationProperties
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}