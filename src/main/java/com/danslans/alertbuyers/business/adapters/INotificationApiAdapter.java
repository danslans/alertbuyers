package com.danslans.alertbuyers.business.adapters;

import com.danslans.alertbuyers.business.models.ResponseEmail;
import com.danslans.alertbuyers.business.strategy.mailtrap.ResponseMailTrap;

public interface INotificationApiAdapter {

    ResponseEmail convertToResponseEmail(ResponseMailTrap responseMailTrap);
}
