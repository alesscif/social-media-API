package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.ProfileRequestDto;
import com.cooksys.socialmedia.dtos.ProfileResponseDto;
import com.cooksys.socialmedia.entities.Profile;

import java.util.List;

public interface ProfileMapper {
    ProfileResponseDto entityToDto(Profile entity);
    Profile dtoToEntity(ProfileRequestDto dto);
    
}
