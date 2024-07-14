package com.danslans.alertbuyers.business.services;

import com.danslans.alertbuyers.business.models.RequestAlertBuyer;
import com.danslans.alertbuyers.business.models.ResponseAlertBuyer;
import reactor.core.publisher.Mono;

public interface IAlertBuyerUseCaseService {

    Mono<ResponseAlertBuyer> sendAlertBuyer(RequestAlertBuyer requestAlertBuyer);
}
