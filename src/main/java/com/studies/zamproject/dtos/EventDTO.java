package com.studies.zamproject.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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