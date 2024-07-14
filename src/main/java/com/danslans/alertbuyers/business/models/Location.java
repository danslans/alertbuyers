package com.danslans.alertbuyers.business.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private String latitude;
    private String longitude;

    @Override
    public String toString() {
        return this.latitude + "," + this.longitude;
    }
}
