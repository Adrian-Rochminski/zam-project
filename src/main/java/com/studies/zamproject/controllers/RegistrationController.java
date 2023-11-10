/* (C)2023 */
package com.studies.zamproject.controllers;

import com.studies.zamproject.dtos.BaseUserRegistrationRequest;
import com.studies.zamproject.dtos.OrganizerRegistrationRequest;
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
    public ResponseEntity<UserDTO> registerOrganizer(
            @RequestBody @Valid OrganizerRegistrationRequest registerRequest) {
        return ResponseEntity.ok().body(registrationService.registerOrganizer(registerRequest));
    }

    @PostMapping("/organizer/activate/{token}")
    public ResponseEntity<String> activateOrganizer(@PathVariable String token) {
        registrationService.activate(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/base")
    public ResponseEntity<UserDTO> registerUser(
            @RequestBody @Valid BaseUserRegistrationRequest registerRequest) {
        return ResponseEntity.ok().body(registrationService.registerUser(registerRequest));
    }

    @GetMapping("/base/activate/{token}")
    public ResponseEntity<String> activateBaseUser(@PathVariable String token) {
        registrationService.activate(token);
        return ResponseEntity.ok().body("Użytkownik został aktywowany");
    }
}
