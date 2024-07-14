package com.danslans.alertbuyers.business.services.impl;

import com.danslans.alertbuyers.business.models.RequestAlertBuyer;
import com.danslans.alertbuyers.business.models.ResponseAlertBuyer;
import com.danslans.alertbuyers.business.models.Weather;
import com.danslans.alertbuyers.business.services.IAlertBuyerUseCaseService;
import com.danslans.alertbuyers.business.services.IForecastUseCaseService;
import com.danslans.alertbuyers.business.services.INotificationUseCaseService;
import com.danslans.alertbuyers.business.services.IWeatherConditionUseCaseService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AlertBuyerUseCaseService implements IAlertBuyerUseCaseService {

    IWeatherConditionUseCaseService weatherConditionUserCaseService;
    INotificationUseCaseService notificationUseCaseService;
    IForecastUseCaseService forecastUseCaseService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AlertBuyerUseCaseService.class);

    @Autowired
    public AlertBuyerUseCaseService(IWeatherConditionUseCaseService weatherConditionUserCaseService,
                                    INotificationUseCaseService notificationUseCaseService,
                                    IForecastUseCaseService forecastUseCaseService) {
        this.weatherConditionUserCaseService = weatherConditionUserCaseService;
        this.notificationUseCaseService = notificationUseCaseService;
        this.forecastUseCaseService = forecastUseCaseService;
    }

    @Override
    public Mono<ResponseAlertBuyer> sendAlertBuyer(RequestAlertBuyer requestAlertBuyer) {
        return weatherConditionUserCaseService.getWeatherCondition(requestAlertBuyer.getLocation()).flatMap(date -> {
            Boolean existsCode = forecastUseCaseService.getExistsCode(date.getCode());
            return notificationUseCaseService.sendNotification(requestAlertBuyer.getEmail(), existsCode,date.getDescription(),requestAlertBuyer.getLocation())
                    .flatMap(isSent ->
                        validateEmailSent(isSent,existsCode,date,"")
                    ).onErrorResume(throwable -> validateEmailSent(false,existsCode,date,throwable.getMessage()));

        });
    }

    private Mono<ResponseAlertBuyer> validateEmailSent(boolean isSent, boolean existsCode, Weather.Date date, String message) {
        if (isSent && existsCode) return Mono.just(ResponseAlertBuyer.builder()
                .forecastCode(date.getCode())
                .forecastDescription(date.getDescription())
                .buyerNotification(true)
                .build());
        return Mono.just(ResponseAlertBuyer.builder()
                .forecastCode(date.getCode())
                .forecastDescription(date.getDescription())
                .buyerNotification(false)
                        .message(message)
                .build());
    }

}
