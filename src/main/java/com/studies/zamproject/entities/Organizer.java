package com.studies.zamproject.entities;

import com.studies.zamproject.entities.Event;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "organizer")
public class Organizer {
    @Id
    private int id;
    private String name;
    private String email;
    private String telephone;

    @OneToMany(mappedBy = "organizer")
    private Set<Event> events;
}