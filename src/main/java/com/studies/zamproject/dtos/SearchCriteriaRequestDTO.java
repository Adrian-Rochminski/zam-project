/* (C)2023 */
package com.studies.zamproject.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class SearchCriteriaRequestDTO {
    @NotNull private Double latitude;
    @NotNull private Double longitude;

    @NotNull
    @Min(0)
    @Max(15)
    private Double radius;

    @Size(max = 60)
    private String searchString = "";

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", pattern = "yyyy-MM-dd")
    private LocalDate startDate = LocalDate.now();

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", pattern = "yyyy-MM-dd")
    private LocalDate endDate = LocalDate.of(3000, 12, 31);

    private Set<Long> tags = new HashSet<>();

    private Boolean isFree = false;
}
