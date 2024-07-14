package com.danslans.alertbuyers.presentation.delegate;


import com.danslans.alertbuyers.business.models.RequestAlertBuyer;
import com.danslans.alertbuyers.business.models.ResponseAlertBuyer;
import com.danslans.alertbuyers.business.models.entities.NotificationEntity;
import com.danslans.alertbuyers.business.services.IAlertBuyerUseCaseService;
import com.danslans.alertbuyers.business.services.INotificationUseCaseService;
import com.danslans.alertbuyers.presentation.dto.LocationDto;
import com.danslans.alertbuyers.presentation.dto.RequestAlertBuyerDto;
import com.danslans.alertbuyers.presentation.dto.ResponseNotificationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class IAlertBuyerDelegateTest {
    IAlertBuyerDelegate alertBuyerDelegate;
    @Mock
    IAlertBuyerUseCaseService alertBuyerUseCaseService;
    @Mock
    INotificationUseCaseService notificationUseCaseService;


    @BeforeEach
    void setUp() {
        alertBuyerDelegate = new AlertBuyerDelegate(alertBuyerUseCaseService,notificationUseCaseService);
    }

    @Test
    void whenSendAlertBuyer_then_expectConvertMapperOK() {
        LocationDto locationDto = new LocationDto();
        locationDto.setLatitude("0.0");
        locationDto.setLongitude("0.0");
        RequestAlertBuyerDto requestAlertBuyerDto = new RequestAlertBuyerDto();
        requestAlertBuyerDto.setEmail("a@a.com");
        requestAlertBuyerDto.setLocation(locationDto);

        when(alertBuyerUseCaseService.sendAlertBuyer(any(RequestAlertBuyer.class))).thenReturn(Mono.just(ResponseAlertBuyer.builder()
                        .message("")
                        .buyerNotification(true)
                        .forecastDescription("hot day")
                        .forecastCode(10)
                .build()));
        alertBuyerDelegate.sendAlertBuyer(requestAlertBuyerDto)
                .doOnError(throwable -> fail("Unexpected exception: " + throwable.getMessage()))
                .subscribe(responseAlertBuyerDto -> {
                    assertEquals(10,responseAlertBuyerDto.getBody().getForecastCode());
                    assertEquals("hot day",responseAlertBuyerDto.getBody().getForecastDescription());
                    assertEquals(true,responseAlertBuyerDto.getBody().isBuyerNotification());
        });
    }

    @Test
    void getNotificationsByEmail() {
        NotificationEntity notificationEntity = NotificationEntity.builder().build();
        notificationEntity.setEmail("a@a.com");
        notificationEntity.setIdEmail("043534sdfsdfsdfsd");
        notificationEntity.setDate(LocalDateTime.now());
        notificationEntity.setStatus(true);
        notificationEntity.setLocation("0.0,0.0");

        when(notificationUseCaseService.getNotificationsByEmail("a@a.com")).thenReturn(Arrays.asList(notificationEntity));
        ResponseEntity<List<ResponseNotificationDto>> notifications = alertBuyerDelegate.getNotificationsByEmail("a@a.com");
        assertEquals(1,notifications.getBody().size());
        assertEquals(notificationEntity.getDate(),notifications.getBody().get(0).getDate());
        assertEquals(notificationEntity.getIdEmail(),notifications.getBody().get(0).getIdEmail());
        assertEquals(notificationEntity.getLocation(),notifications.getBody().get(0).getLocation());
    }
}