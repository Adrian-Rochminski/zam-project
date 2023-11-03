package com.studies.zamproject.services;

import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.entities.Tag;
import com.studies.zamproject.entities.User;
import com.studies.zamproject.mappers.EventMapper;
import com.studies.zamproject.repositories.EventRepository;
import com.studies.zamproject.repositories.TagRepository;
import com.studies.zamproject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepo;
    private final UserRepository userRepo;
    private final TagRepository tagRepo;
    private final EventMapper eventMapper;

    public Event addEvent(EventRequestDTO eventRequest) {
        Event event = eventMapper.eventReqDtoToEvent(eventRequest);
        return eventRepo.save(event);
    }

    public Optional<Event> getEvent(Long eventId) {
        return eventRepo.findById(eventId);
    }

    public Event updateEvent(EventRequestDTO update, Long id) {
        Event newEvent = eventMapper.eventReqDtoToEvent(update);
        if (update.getOwner() != null) {
            User owner = userRepo.getReferenceById(update.getOwner());
            newEvent.setOwner(owner);
        }
        if (update.getTags() != null) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : update.getTags()) {
                try {
                    Tag tag = tagRepo.getReferenceById(tagId);
                    tags.add(tag);
                } catch (EntityNotFoundException ignored) {}
            }
            newEvent.setTags(tags);
        }

        newEvent.setId(id);

        return eventRepo.save(newEvent);
    }

    public void deleteEvent(Long id) {
        eventRepo.deleteById(id);
    }

    public Page<Event> getEvents(Pageable pageable) {
        return eventRepo.findAll(pageable);
    }
}
