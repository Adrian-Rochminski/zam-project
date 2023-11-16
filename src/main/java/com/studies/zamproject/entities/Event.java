/* (C)2023 */
package com.studies.zamproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 256)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private Boolean free;

    @Size(max = 10000)
    private String description;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    @NotNull
    private LocalDateTime startTime;

    @ManyToMany(
            cascade = {CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private Set<Tag> tags;
}
