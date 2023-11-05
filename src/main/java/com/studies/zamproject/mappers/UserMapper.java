/* (C)2023 */
package com.studies.zamproject.mappers;

import com.studies.zamproject.dtos.UserDTO;
import com.studies.zamproject.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
}
