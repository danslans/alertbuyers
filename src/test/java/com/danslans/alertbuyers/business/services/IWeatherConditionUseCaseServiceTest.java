package com.danslans.alertbuyers.business.services;

import com.danslans.alertbuyers.business.adapters.IWeatherApiAdapter;
import com.danslans.alertbuyers.business.adapters.WeatherApiAdapter;
import com.danslans.alertbuyers.business.models.Location;
import com.danslans.alertbuyers.business.services.impl.WeatherConditionUseCaseService;
import com.danslans.alertbuyers.business.strategy.IWeatherClientStrategy;
import com.danslans.alertbuyers.business.strategy.weatherapi.*;
import com.danslans.alertbuyers.exceptions.WeatherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class IWeatherConditionUseCaseServiceTest {

    @Mock
    WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    IWeatherConditionUseCaseService weatherConditionUserCaseService;

    @BeforeEach
    void setUp() {
        IWeatherApiAdapter weatherApiAdapter = new WeatherApiAdapter();
        IWeatherClientStrategy weatherClientStrategy = new WeatherApiClient(webClient,weatherApiAdapter);
        weatherConditionUserCaseService = new WeatherConditionUseCaseService(weatherClientStrategy);

        when(webClient.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(any(), any(), any(), any())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        ResponseWeather responseWeather = ResponseWeather.builder()
                .forecast(Forecast.builder()
                        .forecastday(Arrays.asList(ForecastDay.builder()
                                .date(LocalDate.now().plusDays(1).toString())
                                .day(Day.builder()
                                        .condition(Condition.builder()
                                                .code(101)
                                                .text("OK")
                                                .build())
                                        .build())
                                .build()))
                        .build())
                .build();
        when(responseSpecMock.bodyToMono(ResponseWeather.class)).thenReturn(Mono.just(responseWeather));

    }


    @Test
    void when_getServiceWeatherIsOK_thenReturnResponseWeather() {
        var weather = weatherConditionUserCaseService.getWeatherCondition(Location.builder()
                        .longitude("10.0")
                        .latitude("10.0")
                .build()).block();

        assertEquals(101, weather.getCode());
    }

    @Test
    void when_getServiceWeatherAndDateNotFound_thenExpectException() {
        ResponseWeather responseWeather = ResponseWeather.builder()
                .forecast(Forecast.builder()
                        .forecastday(Arrays.asList(ForecastDay.builder()
                                .date(LocalDate.now().toString())
                                .day(Day.builder()
                                        .condition(Condition.builder()
                                                .code(101)
                                                .text("OK")
                                                .build())
                                        .build())
                                .build()))
                        .build())
                .build();
        when(responseSpecMock.bodyToMono(ResponseWeather.class)).thenReturn(Mono.just(responseWeather));
        weatherConditionUserCaseService.getWeatherCondition(Location.builder()
                .longitude("10.0")
                .latitude("10.0")
                .build()).doOnError(throwable -> {
                        assertEquals(WeatherException.WEATHER_NOT_FOUND, throwable.getMessage());
        }).subscribe(date -> {
            fail("Exception expected");
        });

    }
}