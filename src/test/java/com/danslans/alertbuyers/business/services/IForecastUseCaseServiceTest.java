package com.danslans.alertbuyers.business.services;

import com.danslans.alertbuyers.business.services.impl.ForecastUseCaseService;
import com.danslans.alertbuyers.data.repositories.IForecastCodesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class IForecastUseCaseServiceTest {

    IForecastUseCaseService forecastUseCaseService;
    @Mock
    IForecastCodesRepository forecastCodesRepository;

    @BeforeEach
    void setUp() {
        forecastUseCaseService = new ForecastUseCaseService(forecastCodesRepository);
    }

    @Test
    void when_findByCodeToNotification_then_returnTrue() {
        when(forecastCodesRepository.existsByCode(1192)).thenReturn(true);
        boolean result = forecastUseCaseService.getExistsCode(1192);
        assertEquals(true, result);
    }

    @Test
    void when_findByCodeNotNotification_then_returnFalse() {
        when(forecastCodesRepository.existsByCode(0)).thenReturn(false);
        boolean result = forecastUseCaseService.getExistsCode(0);
        assertEquals(false, result);
    }
}