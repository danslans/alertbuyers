package com.danslans.alertbuyers.business.adapters;

import com.danslans.alertbuyers.business.models.Weather;
import com.danslans.alertbuyers.business.strategy.weatherapi.ResponseWeather;

public interface IWeatherApiAdapter {

    Weather getWeather(ResponseWeather weatherApiResponse);
}
