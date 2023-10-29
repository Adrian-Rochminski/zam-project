package com.studies.zamproject.mappers;

import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.entities.Tag;
import com.studies.zamproject.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event eventReqDtoToEvent(EventRequestDTO eventReqDto);

    Tag tagIdToTag(Long id);

    User userIdToUser(Long id);

}
