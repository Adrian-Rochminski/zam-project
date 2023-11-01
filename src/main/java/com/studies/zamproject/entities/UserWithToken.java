/* (C)2023 */
package com.studies.zamproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "user_with_token")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserWithToken {
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

    @NotNull private String role;

    @NotNull private String password;

    @Column(unique = true)
    private String token;
}
