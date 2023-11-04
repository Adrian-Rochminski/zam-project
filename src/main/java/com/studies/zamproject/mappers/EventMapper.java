/* (C)2023 */
package com.studies.zamproject.mappers;

import com.studies.zamproject.dtos.EventDTO;
import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.entities.Tag;
import com.studies.zamproject.entities.User;
import com.studies.zamproject.exceptions.NotFoundException;
import com.studies.zamproject.repositories.TagRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EventMapper {
    @Autowired protected TagRepository tagRepository;

    @Mapping(target = "id", ignore = true)
    public abstract Event eventReqDtoToEvent(EventRequestDTO eventReqDto);

    public abstract EventDTO eventToEventDto(Event event);

    protected Tag tagIdToTag(Long id) {
        return tagRepository
                .findById(id)
                .orElseThrow(() -> NotFoundException.tagWithIdNotFound(id));
    }

    protected String tagToName(Tag tag) {
        return tag.getName();
    }

    protected abstract User userIdToUser(Long id);

    protected Long userToId(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }
}
