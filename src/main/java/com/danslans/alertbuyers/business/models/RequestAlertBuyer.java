package com.danslans.alertbuyers.business.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAlertBuyer {
    private String email;
    private Location location;

}
