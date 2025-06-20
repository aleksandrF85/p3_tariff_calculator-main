package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.fastdelivery.presentation.api.request.LocationPoint;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(

        @Schema(description = "Список упаковок отправления",
                example = "[{\"weight\": 4056, \"length\": 350, \"width\": 600, \"height\": 250}]")
        @NotNull
        @NotEmpty
        List<CargoPackage> packages,

        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull
        String currencyCode,

        @Schema(description = "Координаты пункта отправления")
        @NotNull
        @Valid
        LocationPoint departure,

        @Schema(description = "Координаты пункта получения")
        @NotNull
        @Valid
        LocationPoint destination
) {
}
