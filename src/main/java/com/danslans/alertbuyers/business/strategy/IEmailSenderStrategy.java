package com.danslans.alertbuyers.business.strategy;

import com.danslans.alertbuyers.business.models.Email;
import com.danslans.alertbuyers.business.models.ResponseEmail;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

public interface IEmailSenderStrategy {

    Mono<ResponseEmail> sendNotification(Email email) ;
}
