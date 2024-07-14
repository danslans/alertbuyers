package com.danslans.alertbuyers.business.adapters;

import com.danslans.alertbuyers.business.models.Weather;
import com.danslans.alertbuyers.business.strategy.weatherapi.ResponseWeather;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class WeatherApiAdapter implements IWeatherApiAdapter {

    @Override
    public Weather getWeather(ResponseWeather weatherApiResponse) {
        Weather weather = Weather.builder()
                .date(new ArrayList<>())
                .build();

        weatherApiResponse.getForecast().getForecastday().forEach(forecastDay -> {
            weather.getDate().add(Weather.Date.builder()
                            .code(forecastDay.getDay().getCondition().getCode())
                            .date(forecastDay.getDate())
                            .description(forecastDay.getDay().getCondition().getText())
                    .build());
        });

        return weather;
    }
}
