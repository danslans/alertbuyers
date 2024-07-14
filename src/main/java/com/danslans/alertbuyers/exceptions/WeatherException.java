package com.danslans.alertbuyers.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherException extends RuntimeException {

    public static final String ERROR_API = "Error with the API";
    public static final String ERROR_API_CODE = "001";

    public static final String WEATHER_NOT_FOUND = "Weather not found";
    public static final String WEATHER_NOT_FOUND_CODE = "OO2";
    private String code;


    public WeatherException(String message, String code) {
        super(message);
        this.code = code;
    }

    public WeatherException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

}
