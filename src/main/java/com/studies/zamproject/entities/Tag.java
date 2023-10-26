/* (C)2023 */
package com.studies.zamproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Event> eventTags;
}
