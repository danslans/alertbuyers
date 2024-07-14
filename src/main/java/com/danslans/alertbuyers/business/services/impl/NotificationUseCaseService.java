package com.danslans.alertbuyers.business.services.impl;

import com.danslans.alertbuyers.business.models.Email;
import com.danslans.alertbuyers.business.models.Location;
import com.danslans.alertbuyers.business.models.entities.NotificationEntity;
import com.danslans.alertbuyers.business.models.entities.TemplateEntity;
import com.danslans.alertbuyers.business.services.INotificationUseCaseService;
import com.danslans.alertbuyers.business.strategy.IEmailSenderStrategy;
import com.danslans.alertbuyers.data.repositories.INotificationRepository;
import com.danslans.alertbuyers.data.repositories.ITemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationUseCaseService implements INotificationUseCaseService {
    ITemplateRepository templateRepository;
    INotificationRepository notificationRepository;
    IEmailSenderStrategy emailSenderStrategy;
    private static final String TEMPLATE_REPLACE = "XXXX";

    @Autowired
    public NotificationUseCaseService(ITemplateRepository templateRepository, INotificationRepository notificationRepository, IEmailSenderStrategy emailSenderStrategy) {
        this.templateRepository = templateRepository;
        this.notificationRepository = notificationRepository;
        this.emailSenderStrategy = emailSenderStrategy;
    }

    @Override
    public Mono<Boolean> sendNotification(String email, Boolean existsCode, String description, Location location) {
        return validateNotification(email,existsCode,description,location);
    }

    @Override
    public List<NotificationEntity> getNotificationsByEmail(String email) {
        return notificationRepository.findByEmail(email);
    }

    private Mono<Boolean> validateNotification(String email, Boolean existsCode, String description, Location location) {
        if (existsCode) {
            return callSenderNotification(email,description,location);
        }
        return Mono.just(false);
    }

    private Mono<Boolean> callSenderNotification(String email, String description, Location location) {
        TemplateEntity template = templateRepository.findByActiveIsTrue();
        template.setContent(template.getContent().replace(TEMPLATE_REPLACE, description));
        return emailSenderStrategy.sendNotification(Email.builder()
                .email(email)
                .message(template.getContent())
                .subject(template.getSubject())
                .build())
                .flatMap(responseEmail -> {
                    notificationRepository.save(NotificationEntity.builder()
                        .message(template.getContent())
                        .email(email)
                        .status(responseEmail.isStatus())
                        .idEmail(responseEmail.getIdEmail())
                        .date(LocalDateTime.now())
                        .weatherForecast(description)
                        .location(location.toString())
                    .build());
                    return Mono.just(responseEmail.isStatus());
                });
    }
}
