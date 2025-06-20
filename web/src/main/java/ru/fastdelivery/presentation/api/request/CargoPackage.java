package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,

        @Schema(description = "Длина, мм", example = "345")
        Integer length,

        @Schema(description = "Ширина, мм", example = "589")
        Integer width,

        @Schema(description = "Высота, мм", example = "234")
        Integer height
) {
}
