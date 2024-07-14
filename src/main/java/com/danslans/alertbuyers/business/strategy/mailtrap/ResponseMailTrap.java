package com.danslans.alertbuyers.business.strategy.mailtrap;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMailTrap {
    private boolean success;
    private List<String> message_ids;
}
