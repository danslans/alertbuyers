package com.danslans.alertbuyers.business.strategy.weatherapi;

import com.danslans.alertbuyers.business.adapters.IWeatherApiAdapter;
import com.danslans.alertbuyers.business.models.Location;
import com.danslans.alertbuyers.business.models.Weather;
import com.danslans.alertbuyers.business.strategy.IWeatherClientStrategy;
import com.danslans.alertbuyers.exceptions.WeatherException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component("weatherApiClient")
public class WeatherApiClient implements IWeatherClientStrategy {

    private WebClient webClient;
    private static final String BASE_URL = "https://api.weatherapi.com/v1";
    private static final String URI = "/forecast.json?key=83b7c8c1fa89489fa81224129240506&q={latitude},{longitude}&days={days}&aqi=no&alerts=no&lang=es";
    private static final int DAYS = 2;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(WeatherApiClient.class);
    IWeatherApiAdapter weatherApiAdapter;

    @Autowired
    public WeatherApiClient(WebClient webClient, IWeatherApiAdapter weatherApiAdapter) {
        this.webClient = webClient;
        this.weatherApiAdapter = weatherApiAdapter;
    }

    public WeatherApiClient() {
    }

    @Override
    public Mono<Weather> getWeatherCondition(Location location) {
        return webClient
                .get()
                .uri(BASE_URL+URI, location.getLatitude(), location.getLongitude(),DAYS)
                .retrieve()
                .bodyToMono(ResponseWeather.class)
                .onErrorResume(throwable -> {
                    logger.error(throwable.getMessage());
                    return Mono.error(new WeatherException(WeatherException.ERROR_API, throwable,WeatherException.ERROR_API_CODE));
                })
                .flatMap(json ->
                        Mono.just(weatherApiAdapter.getWeather(json))
                );
    }

}
