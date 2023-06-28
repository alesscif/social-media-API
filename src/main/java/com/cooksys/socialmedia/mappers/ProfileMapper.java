package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.embeddable.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    UserResponseDto entityToDto(Profile entity);
    Profile dtoToEntity(ProfileDto dto);
    
}
