package com.studies.zamproject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationRequestDTO {
    @NotBlank
    @NotNull
    private Double latitude;
    @NotBlank
    @NotNull
    private Double longitude;
    @NotBlank
    @NotNull
    private Double radius;
}
