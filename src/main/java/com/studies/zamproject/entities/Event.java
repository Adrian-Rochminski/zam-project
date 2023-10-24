package com.studies.zamproject.entities;

@Entity
public class Event {
    @Id
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name="organizer_id", nullable=false)
    private Organizer organizer;
    private int addressId;
    private boolean free;
    private String description;
    private float latitude;
    private float longitude;

    @OneToMany(mappedBy="event")
    private Set<EventTags> eventTags;
}