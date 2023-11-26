/* (C)2023 */
package com.studies.zamproject.services;

import com.studies.zamproject.configuration.config.AppConfig;
import com.studies.zamproject.dtos.EventDTO;
import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.dtos.SearchCriteriaRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.entities.Tag;
import com.studies.zamproject.entities.User;
import com.studies.zamproject.exceptions.ForbiddenException;
import com.studies.zamproject.exceptions.NotFoundException;
import com.studies.zamproject.mappers.EventMapper;
import com.studies.zamproject.repositories.EventRepository;
import com.studies.zamproject.repositories.TagRepository;
import com.studies.zamproject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepo;
    private final UserRepository userRepo;
    private final TagRepository tagRepo;
    private final EventMapper eventMapper;
    private final AppConfig appConfig;

    public EventDTO addEvent(EventRequestDTO eventRequest) {
        Event event = eventMapper.eventReqDtoToEvent(eventRequest);
        return eventMapper.eventToEventDto(eventRepo.save(event));
    }

    public EventDTO getEventDto(Long eventId) {
        var event = getEvent(eventId);
        return eventMapper.eventToEventDto(event);
    }

    public List<EventDTO> getEventsByOrganiserEmail(String email) {
        return eventRepo.findByOwnerEmail(email).stream()
                .map(eventMapper::eventToEventDto)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public EventDTO updateEvent(EventRequestDTO update, Long id, Authentication authentication) {
        checkPermissionForDeleteAndUpdate(id, authentication);
        Event updatedEvent = getEvent(id);
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

    private Event getEvent(Long id) {
        return eventRepo.findById(id).orElseThrow(() -> NotFoundException.eventWithIdNotFound(id));
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
                } catch (EntityNotFoundException e) {
                    log.error("Tag not found", e);
                }
            }
            updatedEvent.setTags(tags);
        }
    }

    private void checkPermissionForDeleteAndUpdate(Long eventId, Authentication authentication) {
        if (authentication
                .getAuthorities()
                .contains(new SimpleGrantedAuthority(appConfig.getAdminRole()))) return;
        var event = getEvent(eventId);
        if (!event.getOwner().getEmail().equals(authentication.getName()))
            throw ForbiddenException.noPermissionToOperation();
    }

    public void deleteEvent(Long id, Authentication authentication) {
        checkPermissionForDeleteAndUpdate(id, authentication);
        eventRepo.deleteById(id);
    }

    public Page<EventDTO> getEvents(Pageable pageable) {
        var events = eventRepo.findAll(pageable);
        var eventDTOS = events.stream().map(eventMapper::eventToEventDto).toList();
        return new PageImpl<>(eventDTOS, pageable, events.getTotalElements());
    }

    public List<EventDTO> getEventByCriteria(SearchCriteriaRequestDTO searchCriteriaRequestDTO) {
        return eventRepo.findBySearchCriteria(searchCriteriaRequestDTO)
                .stream().map(eventMapper::eventToEventDto).toList();
    }
}
