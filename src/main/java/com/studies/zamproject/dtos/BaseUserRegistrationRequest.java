/* (C)2023 */
package com.studies.zamproject.dtos;

import com.studies.zamproject.validators.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserRegistrationRequest {
    @NotBlank private String name;

    @NotBlank private String email;

    @ValidPassword private String password;
}
