package com.studies.zamproject.mappers;

import com.studies.zamproject.dtos.EventRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.entities.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event eventReqDtoToEvent(EventRequestDTO eventReqDto);

    Tag mapTagIdToTag(String tagId);
}
