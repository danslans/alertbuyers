package com.danslans.alertbuyers.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAlertBuyerDto {
    @JsonProperty("forecast_code")
    private int forecastCode;
    @JsonProperty("forecast_description")
    private String forecastDescription;
    @JsonProperty("buyer_notification")
    private boolean buyerNotification;
    private String message;
}
