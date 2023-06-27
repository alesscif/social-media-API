package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.ProfileRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.embeddable.Profile;

public interface ProfileMapper {
    UserResponseDto entityToDto(Profile entity);
    Profile dtoToEntity(ProfileRequestDto dto);
    
}
