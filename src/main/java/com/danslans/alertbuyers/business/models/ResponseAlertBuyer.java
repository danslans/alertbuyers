package com.danslans.alertbuyers.business.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAlertBuyer {

    private int forecastCode;
    private String forecastDescription;
    private boolean buyerNotification;
    private String message;
}
