package com.danslans.alertbuyers.business.services.impl;

import com.danslans.alertbuyers.business.models.Location;
import com.danslans.alertbuyers.business.models.Weather;
import com.danslans.alertbuyers.business.services.IWeatherConditionUseCaseService;
import com.danslans.alertbuyers.business.strategy.IWeatherClientStrategy;
import com.danslans.alertbuyers.exceptions.WeatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class WeatherConditionUseCaseService implements IWeatherConditionUseCaseService {


    IWeatherClientStrategy weatherClientStrategy;

    @Autowired
    public WeatherConditionUseCaseService(IWeatherClientStrategy weatherClientStrategy) {
        this.weatherClientStrategy = weatherClientStrategy;
    }

    @Override
    public Mono<Weather.Date> getWeatherCondition(Location location) {
        return getWeather(location);
    }

    private Mono<Weather.Date> getWeather(Location location) {
        return weatherClientStrategy.getWeatherCondition(location).flatMap(weather ->
            Mono.just(getNextDay(weather))
        );
    }

    private Weather.Date getNextDay(Weather weather) {
        LocalDate today = LocalDate.now().plusDays(1);
        return weather.getDate().stream().filter(date -> date.getDate().equals(today.toString())).findFirst().orElseThrow(() -> new WeatherException(WeatherException.WEATHER_NOT_FOUND,WeatherException.WEATHER_NOT_FOUND_CODE));
    }

}
