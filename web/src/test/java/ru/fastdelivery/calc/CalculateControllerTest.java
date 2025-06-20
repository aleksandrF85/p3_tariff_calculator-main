package ru.fastdelivery.calc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.LocationPoint;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CalculateControllerTest extends ControllerTest {

    final String baseCalculateApi = "/api/v1/calculate/";

    @MockBean
    TariffCalculateUseCase useCase;
    @MockBean
    CurrencyFactory currencyFactory;

    @Test
    @DisplayName("Валидные данные -> 200 OK")
    void whenValidRequest_thenReturn200() {
        var cargo = new CargoPackage(BigInteger.valueOf(140000), 1500, 1500, 1000);
        var departure = new LocationPoint(55.7558, 37.6173); // Москва
        var destination = new LocationPoint(43.1155, 131.8855); // Владивосток

        var request = new CalculatePackagesRequest(
                List.of(cargo),
                "RUB",
                departure,
                destination
        );

        var rub = new CurrencyFactory(code -> true).create("RUB");

        when(useCase.calc(any())).thenReturn(new Price(new BigDecimal("10000.00"), rub));
        when(useCase.minimalPrice()).thenReturn(new Price(new BigDecimal("5000.00"), rub));

        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("10000.00"), response.getBody().totalPrice());
    }

    @Test
    @DisplayName("Габарит больше 1500 -> 400 Bad Request")
    void whenOversizedDimension_thenReturn400() {
        var cargo = new CargoPackage(BigInteger.TEN, 1600, 600, 500); // длина > 1500
        var departure = new LocationPoint(55.0, 37.0);
        var destination = new LocationPoint(56.0, 38.0);

        var request = new CalculatePackagesRequest(List.of(cargo), "RUB", departure, destination);

        ResponseEntity<String> response =
                restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Широта вне диапазона -> 400 Bad Request")
    void whenInvalidLatitude_thenReturn400() {
        var cargo = new CargoPackage(BigInteger.TEN, 500, 600, 500);
        var departure = new LocationPoint(10.0, 37.0); // широта вне диапазона
        var destination = new LocationPoint(56.0, 38.0);

        var request = new CalculatePackagesRequest(List.of(cargo), "RUB", departure, destination);

        ResponseEntity<String> response =
                restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Список упаковок == null -> 400 Bad Request")
    void whenNullPackageList_thenReturn400() {
        var departure = new LocationPoint(55.0, 37.0);
        var destination = new LocationPoint(56.0, 38.0);

        var request = new CalculatePackagesRequest(null, "RUB", departure, destination);

        ResponseEntity<String> response =
                restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
