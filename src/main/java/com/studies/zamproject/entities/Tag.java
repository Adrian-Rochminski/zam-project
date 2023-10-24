package com.studies.zamproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "tag")
    private Set<EventTag> eventTags;
}