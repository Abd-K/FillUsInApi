package org.fillUsIn.dto.mapper;

import org.fillUsIn.dto.UserDTO;
import org.fillUsIn.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDTO userToUserDTO(User user);

}
