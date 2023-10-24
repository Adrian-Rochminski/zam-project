package com.studies.zamproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizer_with_token")
public class OrganizerWithToken {
    @Id
    private int id;
    private String name;
    private String email;
    private String telephone;
    private String token;
}