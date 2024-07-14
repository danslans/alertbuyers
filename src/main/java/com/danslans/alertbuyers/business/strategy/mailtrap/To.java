package com.danslans.alertbuyers.business.strategy.mailtrap;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class To implements Serializable {
    private String email;
}
