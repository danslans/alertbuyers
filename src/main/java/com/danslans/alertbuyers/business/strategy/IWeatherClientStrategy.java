package com.danslans.alertbuyers.business.strategy;

import com.danslans.alertbuyers.business.models.Location;
import com.danslans.alertbuyers.business.models.Weather;
import reactor.core.publisher.Mono;

public interface IWeatherClientStrategy {

    Mono<Weather> getWeatherCondition(Location location);
}
