package com.danslans.alertbuyers.business.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private String email;
    private String message;
    private String subject;

}
