package com.studies.zamproject.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EventRequestDTO {
    @NotBlank
    @Size(max = 256)
    private String name;

    private Boolean free;

    @NotNull
    private Long owner;

    @Size(max = 10000)
    private String description;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    private Set<String> tags;
}
