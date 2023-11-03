package com.studies.zamproject.controllers;

import com.studies.zamproject.dtos.EventDTO;
import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.mappers.EventMapper;
import com.studies.zamproject.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value="/events", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addEvent(@Valid @RequestBody EventRequestDTO eventRequestDTO) {
        Event event = eventService.addEvent(eventRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(event.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {
        Optional<Event> event = eventService.getEvent(id);
        return ResponseEntity.of(event.map(eventMapper::eventToEventDto));
    }

    @GetMapping(value = "/events")
    public ResponseEntity<Page<EventDTO>> readEvents(Pageable pageable) {
        return new ResponseEntity<>(eventService.getEvents(pageable).map(eventMapper::eventToEventDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/events/{id}")
    public ResponseEntity<Void> updateEvent(@Valid @RequestBody EventRequestDTO eventRequestDTO, @PathVariable Long id) {
        Event event = eventService.updateEvent(eventRequestDTO, id);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(event.getId()).toUri();

        return ResponseEntity.status(HttpStatus.OK).location(location).build();
    }

    @DeleteMapping(value = "/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
