package com.danslans.alertbuyers.business.strategy.mailtrap;

import com.danslans.alertbuyers.business.adapters.INotificationApiAdapter;
import com.danslans.alertbuyers.business.models.Email;
import com.danslans.alertbuyers.business.models.ResponseEmail;
import com.danslans.alertbuyers.business.strategy.IEmailSenderStrategy;
import com.danslans.alertbuyers.exceptions.NotificationEmailException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component("mailTrapSender")
public class MailTrapSender  implements IEmailSenderStrategy {

    private static final String BASE_URL = "https://send.api.mailtrap.io/api/send";
    WebClient webClient;
    private static final String API_KEY = "ba2eb9d31e5f580ded7bf1f3fd6cf3bd";
    private static final String FROM_EMAIL = "mailtrap@demomailtrap.com";
    private static final String FROM_NAME = "Mailtrap Test";
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(MailTrapSender.class);
    INotificationApiAdapter notificationApiAdapter;

    @Autowired
    public MailTrapSender(WebClient webClient, INotificationApiAdapter notificationApiAdapter) {
        this.webClient = webClient;
        this.notificationApiAdapter = notificationApiAdapter;
    }

    public MailTrapSender() {
    }

    @Override
    public Mono<ResponseEmail> sendNotification(Email email)  {
        RequestEmail emailRequest = getRequestEmail(email);
        return webClient.post().uri(BASE_URL)
                .headers(httpHeaders ->
                        httpHeaders.setBearerAuth(API_KEY)
                )
                .bodyValue(emailRequest)
                .retrieve()
                .bodyToMono(ResponseMailTrap.class)
                .onErrorResume(throwable -> {
                    logger.error(throwable.getMessage());
                    return Mono.error(new NotificationEmailException(NotificationEmailException.ERROR_SENDING_EMAIL_MAILTRAPSENDER, throwable,NotificationEmailException.ERROR_SENDING_EMAIL_MAILTRAPSENDER_CODE));
                }).flatMap(responseMailTrap ->
                        Mono.just(notificationApiAdapter.convertToResponseEmail(responseMailTrap))
                );
    }

    private static RequestEmail getRequestEmail(Email email) {
        RequestEmail requestEmail = RequestEmail.builder()
                .from(From.builder().email(FROM_EMAIL).name(FROM_NAME).build())
                .to(new ArrayList<>())
                .subject(email.getSubject())
                .text(email.getMessage())
                .build();

        requestEmail.getTo().add(To.builder().email(email.getEmail()).build());
        return requestEmail;
    }

}
