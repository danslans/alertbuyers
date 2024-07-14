package com.danslans.alertbuyers.business.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    private List<Date> date;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Date {

        private String date;
        private String description;
        private int code;
    }
}
