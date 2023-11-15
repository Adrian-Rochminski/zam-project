/* (C)2023 */
package com.studies.zamproject.services;

import com.studies.zamproject.dtos.EventDTO;
import com.studies.zamproject.entities.User;
import com.studies.zamproject.exceptions.BadRequestException;
import com.studies.zamproject.exceptions.NotFoundException;
import com.studies.zamproject.mappers.EventMapper;
import com.studies.zamproject.repositories.EventRepository;
import com.studies.zamproject.repositories.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addFavorite(Long eventId, Authentication authentication) {
        var email = authentication.getName();
        User user = getUser(email);
        var event =
                eventRepository
                        .findById(eventId)
                        .orElseThrow(() -> NotFoundException.eventWithIdNotFound(eventId));
        if (user.getFavorites().contains(event))
            throw BadRequestException.eventIsAlreadyInFavorites(eventId);
        user.getFavorites().add(event);
    }

    private User getUser(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> NotFoundException.userNotFound(email));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteFavorite(Long eventId, Authentication authentication) {
        var email = authentication.getName();
        User user = getUser(email);
        var event =
                eventRepository
                        .findById(eventId)
                        .orElseThrow(() -> NotFoundException.eventWithIdNotFound(eventId));
        if (!user.getFavorites().contains(event))
            throw BadRequestException.eventIsNotInYourFavorites(eventId);
        user.getFavorites().remove(event);
    }

    public List<EventDTO> getUserFavorites(Authentication authentication) {
        var email = authentication.getName();
        User user = getUser(email);
        return user.getFavorites().stream().map(eventMapper::eventToEventDto).toList();
    }
}
