package com.studies.zamproject.services;

import com.studies.zamproject.entities.Event;
import com.studies.zamproject.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepo;

    public Event addEvent(Event eventRequest) {
        return null;
    }

    public Optional<Event> getEvent(Event eventRequest) {
        return Optional.empty();
    }

    public Event updateEvent(Event eventRequest) {
        return null;
    }

    public void deleteEvent(Event eventRequest) {

    }

}
