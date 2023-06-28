package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.embeddable.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {

    @Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToDto(Profile entity);
    @Mapping(target = "username", source = "credentials.username")
    List<UserResponseDto> entitiesToDtos(List<Profile> entities);
    Profile dtoToEntity(ProfileDto dto);
    List<Profile> dtostoEntities(List<ProfileDto> dtos);

}
