/* (C)2023 */
package com.studies.zamproject.dtos;

import com.studies.zamproject.validators.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerRegistrationRequest {
    @NotBlank private String name;

    @NotBlank private String email;

    @NotBlank
    @Size(min = 9, max = 20)
    private String telephone;

    @ValidPassword private String password;
}
