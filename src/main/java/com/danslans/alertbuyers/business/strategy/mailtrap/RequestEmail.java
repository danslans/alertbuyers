package com.danslans.alertbuyers.business.strategy.mailtrap;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestEmail implements Serializable {

    private From from;
    private List<To> to;
    private String subject;
    private String text;

    public List<To> getTo() {
        return to;
    }

    public void setTo(List<To> to) {
        this.to = to;
    }
}
