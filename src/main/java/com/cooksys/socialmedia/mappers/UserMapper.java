package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.ProfileRequestDto;
import com.cooksys.socialmedia.dtos.ProfileResponseDto;
import com.cooksys.socialmedia.entities.Profile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ProfileResponseDto entityToDto(Profile entity);
    List<ProfileResponseDto> entitiesToDtos(List<Profile> entities);
    Profile dtoToEntity(ProfileRequestDto dto);
    List<Profile> dtostoEntities(List<ProfileRequestDto> dtos);

}
