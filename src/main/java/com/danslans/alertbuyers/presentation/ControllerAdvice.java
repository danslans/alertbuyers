package com.danslans.alertbuyers.presentation;

import com.danslans.alertbuyers.exceptions.NotificationEmailException;
import com.danslans.alertbuyers.exceptions.WeatherException;
import com.danslans.alertbuyers.presentation.dto.ErrorDto;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ControllerAdvice.class);
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException e) {
        logger.error(e.getMessage(), e);
        ErrorDto errorDto = ErrorDto.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotificationEmailException.class)
    public ResponseEntity<ErrorDto> handleCustomException(NotificationEmailException e) {
        logger.error(e.getMessage(), e);
        ErrorDto errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .errorCode(e.getCode())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = WeatherException.class)
    public ResponseEntity<ErrorDto> handleCustomException(WeatherException e) {
        logger.error(e.getMessage(), e);
        ErrorDto errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .errorCode(e.getCode())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

}
