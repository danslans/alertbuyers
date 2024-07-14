package com.danslans.alertbuyers.presentation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {

    private String errorCode;
    private String message;
}
