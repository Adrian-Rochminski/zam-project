/* (C)2023 */
package com.studies.zamproject.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class EventDTO {
    private Long id;
    private String name;
    private Long owner;
    private Boolean free;
    private String description;
    private Double latitude;
    private Double longitude;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private String imageUrl;

    private Set<TagDTO> tags;

    private Boolean isFavorite;
}
