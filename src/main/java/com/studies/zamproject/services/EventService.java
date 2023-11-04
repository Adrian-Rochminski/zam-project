/* (C)2023 */
package com.studies.zamproject.services;

import com.studies.zamproject.dtos.EventDTO;
import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.entities.Tag;
import com.studies.zamproject.entities.User;
import com.studies.zamproject.exceptions.NotFoundException;
import com.studies.zamproject.mappers.EventMapper;
import com.studies.zamproject.repositories.EventRepository;
import com.studies.zamproject.repositories.TagRepository;
import com.studies.zamproject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepo;
    private final UserRepository userRepo;
    private final TagRepository tagRepo;
    private final EventMapper eventMapper;

    public EventDTO addEvent(EventRequestDTO eventRequest) {
        Event event = eventMapper.eventReqDtoToEvent(eventRequest);
        return eventMapper.eventToEventDto(eventRepo.save(event));
    }

    public EventDTO getEventDto(Long eventId) {
        var event =
                eventRepo
                        .findById(eventId)
                        .orElseThrow(() -> NotFoundException.eventWithIdNotFound(eventId));
        return eventMapper.eventToEventDto(event);
    }

    @Transactional(rollbackFor = Exception.class)
    public EventDTO updateEvent(EventRequestDTO update, Long id) {
        Event updatedEvent =
                eventRepo.findById(id).orElseThrow(() -> NotFoundException.eventWithIdNotFound(id));
        if (update.getOwner() != null) {
            User owner =
                    userRepo.findById(update.getOwner())
                            .orElseThrow(() -> NotFoundException.userWithIdNotFound(id));
            updatedEvent.setOwner(owner);
        }
        updateTags(update, updatedEvent);

        updateRest(update, updatedEvent);

        return eventMapper.eventToEventDto(updatedEvent);
    }

    private void updateRest(EventRequestDTO update, Event updatedEvent) {
        if (update.getFree() != null) {
            updatedEvent.setFree(update.getFree());
        }
        if (update.getDescription() != null) {
            updatedEvent.setDescription(update.getDescription());
        }
        if (update.getLatitude() != null) {
            updatedEvent.setLatitude(update.getLatitude());
        }
        if (update.getLongitude() != null) {
            updatedEvent.setLongitude(update.getLongitude());
        }
    }

    private void updateTags(EventRequestDTO update, Event updatedEvent) {
        if (update.getTags() != null) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : update.getTags()) {
                try {
                    Tag tag = tagRepo.getReferenceById(tagId);
                    tags.add(tag);
                } catch (EntityNotFoundException ignored) {
                }
            }
            updatedEvent.setTags(tags);
        }
    }

    public void deleteEvent(Long id) {
        eventRepo.deleteById(id);
    }

    public Page<EventDTO> getEvents(Pageable pageable) {
        var events = eventRepo.findAll(pageable);
        var eventDTOS = events.stream().map(eventMapper::eventToEventDto).toList();
        return new PageImpl<>(eventDTOS, pageable, events.getTotalElements());
    }
}
