/* (C)2023 */
package com.studies.zamproject.dtos;

import java.util.Set;
import lombok.Data;

@Data
public class EventDTO {
    private Long id;
    private String name;
    private Long owner;
    private Boolean free;
    private String description;
    private Double latitude;
    private Double longitude;

    private Set<String> tags;
}
