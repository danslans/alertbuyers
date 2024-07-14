package com.danslans.alertbuyers.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ResponseNotificationDto {

    private LocalDateTime date;
    private String idEmail;
    private String location;
    private String weatherForecast;
}
