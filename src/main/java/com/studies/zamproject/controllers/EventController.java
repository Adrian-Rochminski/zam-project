/* (C)2023 */
package com.studies.zamproject.controllers;

import com.studies.zamproject.dtos.EventDTO;
import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.dtos.SearchCriteriaRequestDTO;
import com.studies.zamproject.services.EventService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDTO> addEvent(@Valid @RequestBody EventRequestDTO eventRequestDTO) {
        var eventDTO = eventService.addEvent(eventRequestDTO);
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(eventDTO.getId())
                        .toUri();
        return ResponseEntity.created(location).body(eventDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventDto(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public ResponseEntity<Page<EventDTO>> readEvents(Pageable pageable) {
        return ResponseEntity.ok(eventService.getEvents(pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public ResponseEntity<EventDTO> updateEvent(
            @Valid @RequestBody EventRequestDTO eventRequestDTO,
            @PathVariable Long id,
            Authentication authentication) {
        var eventDTO = eventService.updateEvent(eventRequestDTO, id, authentication);
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .buildAndExpand(eventDTO.getId())
                        .toUri();

        return ResponseEntity.status(HttpStatus.OK).location(location).body(eventDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, Authentication authentication) {
        eventService.deleteEvent(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            value = "/search",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDTO>> getEventByCriteria(
            @RequestBody SearchCriteriaRequestDTO searchCriteriaRequestDTO, Authentication authentication) {
        List<EventDTO> events = eventService.getEventByCriteria(searchCriteriaRequestDTO, authentication.getName());
        return ResponseEntity.ok(events);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/owner")
    public ResponseEntity<List<EventDTO>> getEventsByOwner(Authentication authentication) {
        return ResponseEntity.ok(eventService.getEventsByOrganiserEmail(authentication.getName()));
    }
}
