package com.studies.zamproject.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchCriteriaRequestDTO {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    @Min(0)
    @Max(15)
    private Double radius;

    private String searchString = "";
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.MAX;
    private Boolean isFree = false;
}
