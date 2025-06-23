package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.GeoProvider;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;
    private final GeoProvider geoProvider;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Пример запроса для маршрута Москва → Екатеринбург",
            required = true,
            content = @Content(
                    examples = @ExampleObject(name = "Москва → Екатеринбург", value = """
            {
              "packages": [
                {
                  "weight": 4056,
                  "length": 350,
                  "width": 600,
                  "height": 250
                }
              ],
              "currencyCode": "RUB",
              "departure": {
                "latitude": 55.7558,
                "longitude": 37.6173
              },
              "destination": {
                "latitude": 56.8389,
                "longitude": 60.6057
              }
            }
        """)
        )
    )
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {

        var packs = request.packages().stream()
                .map(p -> {
                    var weight = new Weight(p.weight());
                    var volume = new Volume(p.length(), p.width(), p.height());
                    return new Pack(weight, volume);
                })
                .toList();

        var shipment = new Shipment(
                packs,
                currencyFactory.create(request.currencyCode()),
                request.departure().toDomain(geoProvider),
                request.destination().toDomain(geoProvider)
        );
        var calculatedPrice = tariffCalculateUseCase.calc(shipment);
        var minimalPrice = tariffCalculateUseCase.minimalPrice();

        return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
    }
}

