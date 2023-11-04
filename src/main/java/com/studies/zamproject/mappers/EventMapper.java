/* (C)2023 */
package com.studies.zamproject.mappers;

import com.studies.zamproject.dtos.EventDTO;
import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.entities.Tag;
import com.studies.zamproject.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    Event eventReqDtoToEvent(EventRequestDTO eventReqDto);

    EventDTO eventToEventDto(Event event);

    Tag tagIdToTag(Long id);

    String tagToName(Tag tag);

    User userIdToUser(Long id);

    default Long userToId(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }
}
