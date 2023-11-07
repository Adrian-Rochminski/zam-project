/* (C)2023 */
package com.studies.zamproject.mappers;

import com.studies.zamproject.dtos.TagDTO;
import com.studies.zamproject.entities.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO tagToTagDTO(Tag tag);
}
