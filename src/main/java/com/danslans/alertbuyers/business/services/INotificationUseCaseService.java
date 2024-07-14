package com.danslans.alertbuyers.business.services;

import com.danslans.alertbuyers.business.models.Location;
import com.danslans.alertbuyers.business.models.entities.NotificationEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface INotificationUseCaseService {

    Mono<Boolean> sendNotification(String email, Boolean existsCode, String description, Location location);

    List<NotificationEntity> getNotificationsByEmail(String email);
}
