package com.danslans.alertbuyers.business.strategy.weatherapi;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {
    private List<ForecastDay> forecastday;
}
