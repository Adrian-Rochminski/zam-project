package com.studies.zamproject.services;

import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.mappers.EventMapper;
import com.studies.zamproject.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepo;
    private final EventMapper eventMapper;

    public Event addEvent(EventRequestDTO eventRequest) {
        Event event = eventMapper.eventReqDtoToEvent(eventRequest);
        return eventRepo.save(event);
    }

    public Optional<Event> getEvent(Long eventId) {
        return eventRepo.findById(eventId);
    }

    public Event updateEvent(Event eventRequest) {
        return null;
    }

    public void deleteEvent(Event eventRequest) {

    }

    public Page<Event> getEvents(Pageable pageable) {
        return eventRepo.findAll(pageable);
    }
}
