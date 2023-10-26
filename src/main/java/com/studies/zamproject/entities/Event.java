/* (C)2023 */
package com.studies.zamproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    private Boolean free;

    @Size(max = 10000)
    private String description;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    @ManyToMany(
            cascade = {CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private Set<Tag> tags;
}
