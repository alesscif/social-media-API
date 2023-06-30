package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.entities.embeddable.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {

    @Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToDto(User entity);

    @Mapping(target = "username", source = "credentials.username")
    List<UserResponseDto> entitiesToDtos(List<User> entities);

    Profile dtoToEntity(UserRequestDto dto);

    List<Profile> dtostoEntities(List<UserRequestDto> dtos);

}
