package com.danslans.alertbuyers.business.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestNotification {

    private String email;
    private Location location;

    @Getter
    @Setter
    private class Location {
        private String latitude;
        private String longitude;
    }
}
