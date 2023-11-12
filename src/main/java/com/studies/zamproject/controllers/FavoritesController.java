/* (C)2023 */
package com.studies.zamproject.controllers;

import com.studies.zamproject.dtos.EventDTO;
import com.studies.zamproject.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoritesController {
    private final UserService userService;

    @PostMapping("/{eventId}")
    public ResponseEntity<Void> addFavorite(
            @PathVariable Long eventId, Authentication authentication) {
        userService.addFavorite(eventId, authentication);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteFavorite(
            @PathVariable Long eventId, Authentication authentication) {
        userService.deleteFavorite(eventId, authentication);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getUserFavorites(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserFavorites(authentication));
    }
}
