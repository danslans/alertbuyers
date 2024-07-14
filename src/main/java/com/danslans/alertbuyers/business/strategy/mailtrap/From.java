package com.danslans.alertbuyers.business.strategy.mailtrap;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class From implements Serializable {
    private String name;
    private String email;
}
