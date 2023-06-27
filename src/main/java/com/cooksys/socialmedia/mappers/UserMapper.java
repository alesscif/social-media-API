package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.ProfileRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.embeddable.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {

    // TweetMappper:
    // uses = {UserMapper.class}

    @Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToDto(Profile entity);
    List<UserResponseDto> entitiesToDtos(List<Profile> entities);
    Profile dtoToEntity(ProfileRequestDto dto);
    List<Profile> dtostoEntities(List<ProfileRequestDto> dtos);

}
