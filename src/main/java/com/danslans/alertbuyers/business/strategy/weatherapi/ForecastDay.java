package com.danslans.alertbuyers.business.strategy.weatherapi;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForecastDay {
    private String date;
    private Day day;
}
