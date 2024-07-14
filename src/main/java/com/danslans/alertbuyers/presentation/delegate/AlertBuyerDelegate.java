package com.danslans.alertbuyers.presentation.delegate;

import com.danslans.alertbuyers.business.models.RequestAlertBuyer;
import com.danslans.alertbuyers.business.models.entities.NotificationEntity;
import com.danslans.alertbuyers.business.services.IAlertBuyerUseCaseService;
import com.danslans.alertbuyers.business.services.INotificationUseCaseService;
import com.danslans.alertbuyers.presentation.dto.RequestAlertBuyerDto;
import com.danslans.alertbuyers.presentation.dto.ResponseAlertBuyerDto;
import com.danslans.alertbuyers.presentation.dto.ResponseNotificationDto;
import com.danslans.alertbuyers.presentation.mappers.INotificationMapper;
import com.danslans.alertbuyers.presentation.mappers.IRequestAlertBuyerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AlertBuyerDelegate implements IAlertBuyerDelegate {

    IAlertBuyerUseCaseService alertBuyerUseCaseService;
    INotificationUseCaseService notificationUseCaseService;

    @Autowired
    public AlertBuyerDelegate(IAlertBuyerUseCaseService alertBuyerUseCaseService, INotificationUseCaseService notificationUseCaseService) {
        this.alertBuyerUseCaseService = alertBuyerUseCaseService;
        this.notificationUseCaseService = notificationUseCaseService;
    }

    @Override
    public Mono<ResponseEntity<ResponseAlertBuyerDto>> sendAlertBuyer(RequestAlertBuyerDto requestAlertBuyer) {
        RequestAlertBuyer alertBuyer =  IRequestAlertBuyerMapper.INSTANCE.requestAlertBuyerDtoToRequestAlertBuyer(requestAlertBuyer);
        return alertBuyerUseCaseService.sendAlertBuyer(alertBuyer)
            .flatMap(responseAlertBuyer ->
                Mono.just(ResponseEntity.ok( IRequestAlertBuyerMapper.INSTANCE.responseAlertBuyerDtoToResponseAlertBuyer(responseAlertBuyer)))
            );
    }

    @Override
    public ResponseEntity<List<ResponseNotificationDto>> getNotificationsByEmail(String email) {
        List<NotificationEntity> notifications = notificationUseCaseService.getNotificationsByEmail(email);
        List<ResponseNotificationDto> responseNotifications = INotificationMapper.INSTANCE.notificationEntityToResponseNotificationDto(notifications);
        return ResponseEntity.ok(responseNotifications);
    }

}
