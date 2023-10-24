package com.studies.zamproject.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "event_tags")
public class EventTag {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

}