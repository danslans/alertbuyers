package com.danslans.alertbuyers.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEmailException extends RuntimeException{
    public static final String ERROR_SENDING_EMAIL_MAILTRAPSENDER = "Error sending email using MailtrapSender";
    public static final String ERROR_SENDING_EMAIL_MAILTRAPSENDER_CODE = "003";
    private String code;


    public NotificationEmailException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

}
