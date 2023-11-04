/* (C)2023 */
package com.studies.zamproject.controllers;

import com.studies.zamproject.dtos.RegistrationRequest;
import com.studies.zamproject.dtos.UserDTO;
import com.studies.zamproject.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/organizer")
    public ResponseEntity<UserDTO> register(
            @RequestBody @Valid RegistrationRequest registerRequest) {
        return ResponseEntity.ok().body(registrationService.registerOrganizer(registerRequest));
    }

    @PostMapping("/activate/{token}")
    public ResponseEntity<String> activate(@PathVariable String token) {
        registrationService.activate(token);
        return ResponseEntity.noContent().build();
    }
}
