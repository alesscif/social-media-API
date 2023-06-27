package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto entityToDto(User entity);
    List<UserResponseDto> entitiesToDtos(List<User> entities);
    User dtoToEntity(UserRequestDto dto);
    List<User> dtostoEntities(List<UserRequestDto> dtos);

}
