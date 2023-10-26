/* (C)2023 */
package com.studies.zamproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "organizer_with_token")
public class OrganizerWithToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    @Column(unique = true)
    @Size(max = 256)
    private String email;

    @Size(max = 20)
    private String telephone;

    @Size(max = 20)
    private String token;
}
