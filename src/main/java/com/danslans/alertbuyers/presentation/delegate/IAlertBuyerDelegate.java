package com.danslans.alertbuyers.presentation.delegate;

import com.danslans.alertbuyers.presentation.dto.RequestAlertBuyerDto;
import com.danslans.alertbuyers.presentation.dto.ResponseAlertBuyerDto;
import com.danslans.alertbuyers.presentation.dto.ResponseNotificationDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IAlertBuyerDelegate {

    Mono<ResponseEntity<ResponseAlertBuyerDto>> sendAlertBuyer(RequestAlertBuyerDto requestAlertBuyer);
    ResponseEntity<List<ResponseNotificationDto>> getNotificationsByEmail(String email);
}
