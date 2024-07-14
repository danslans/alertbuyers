package com.danslans.alertbuyers.business.models;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEmail {

    private boolean status;
    private String idEmail;
}
