package com.danslans.alertbuyers.business.services;

import com.danslans.alertbuyers.business.models.Location;
import com.danslans.alertbuyers.business.models.Weather;
import reactor.core.publisher.Mono;

public interface IWeatherConditionUseCaseService {

    Mono<Weather.Date> getWeatherCondition(Location location);

}
