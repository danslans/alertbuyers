package com.danslans.alertbuyers.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {

    @NotBlank(message = "Latitude is mandatory")
    private String latitude;
    @NotBlank(message = "Longitude is mandatory")
    private String longitude;
}