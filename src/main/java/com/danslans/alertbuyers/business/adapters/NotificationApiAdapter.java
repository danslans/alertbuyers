package com.danslans.alertbuyers.business.adapters;

import com.danslans.alertbuyers.business.models.ResponseEmail;
import com.danslans.alertbuyers.business.strategy.mailtrap.ResponseMailTrap;
import org.springframework.stereotype.Component;

@Component
public class NotificationApiAdapter implements INotificationApiAdapter {

    @Override
    public ResponseEmail convertToResponseEmail(ResponseMailTrap responseMailTrap) {
        ResponseEmail responseEmail = ResponseEmail.builder()
                .status(responseMailTrap.isSuccess())
                .build();
        responseMailTrap.getMessage_ids().stream().findFirst().ifPresent(responseEmail::setIdEmail);
        return responseEmail;
    }
}
