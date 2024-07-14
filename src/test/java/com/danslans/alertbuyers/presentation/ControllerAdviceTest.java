package com.danslans.alertbuyers.presentation;

import com.danslans.alertbuyers.exceptions.NotificationEmailException;
import com.danslans.alertbuyers.exceptions.WeatherException;
import com.danslans.alertbuyers.presentation.dto.ErrorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class ControllerAdviceTest {

    ControllerAdvice controllerAdvice;
    @BeforeEach
    void setUp() {
        controllerAdvice = new ControllerAdvice();
    }

    @Test
    void handleRuntimeException() {
        ResponseEntity<ErrorDto> response = controllerAdvice.handleRuntimeException(new RuntimeException("ERROR GENERIC"));

        assertEquals(HttpStatus.BAD_REQUEST.value() , response.getStatusCodeValue());
        assertEquals("ERROR GENERIC", response.getBody().getMessage());
    }

    @Test
    void customExceptionNotification() {
        ResponseEntity<ErrorDto> response = controllerAdvice.handleCustomException(new NotificationEmailException(NotificationEmailException.ERROR_SENDING_EMAIL_MAILTRAPSENDER,new RuntimeException(),NotificationEmailException.ERROR_SENDING_EMAIL_MAILTRAPSENDER_CODE));

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(NotificationEmailException.ERROR_SENDING_EMAIL_MAILTRAPSENDER, response.getBody().getMessage());
        assertEquals(NotificationEmailException.ERROR_SENDING_EMAIL_MAILTRAPSENDER_CODE, response.getBody().getErrorCode());
    }

    @Test
    void customExceptionWeatherApi () {
        ResponseEntity<ErrorDto> response = controllerAdvice.handleCustomException(new WeatherException(WeatherException.ERROR_API,new RuntimeException(),WeatherException.ERROR_API_CODE));

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(WeatherException.ERROR_API, response.getBody().getMessage());
        assertEquals(WeatherException.ERROR_API_CODE, response.getBody().getErrorCode());
    }

    @Test
    void customExceptionWeather () {
        ResponseEntity<ErrorDto> response = controllerAdvice.handleCustomException(new WeatherException(WeatherException.WEATHER_NOT_FOUND,new RuntimeException(),WeatherException.WEATHER_NOT_FOUND_CODE));

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(WeatherException.WEATHER_NOT_FOUND, response.getBody().getMessage());
        assertEquals(WeatherException.WEATHER_NOT_FOUND_CODE, response.getBody().getErrorCode());
    }
}