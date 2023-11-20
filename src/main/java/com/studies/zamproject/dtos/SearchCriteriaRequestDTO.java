/* (C)2023 */
package com.studies.zamproject.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchCriteriaRequestDTO {
    @NotNull private Double latitude;
    @NotNull private Double longitude;
    @NotNull private Double radius;
}
