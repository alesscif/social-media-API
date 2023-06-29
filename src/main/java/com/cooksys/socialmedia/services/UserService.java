package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();
    UserResponseDto createUser(UserRequestDto userToCreate);
    UserResponseDto getUser(String username);
    UserResponseDto updateUser(UserRequestDto updateData);
    UserResponseDto deleteUser(UserRequestDto userToDelete);
    void followUser(UserRequestDto userToFollow);
    void unfollowUser(UserRequestDto userToUnfollow);
    List<UserResponseDto> getFollowers(String username);
    List<UserResponseDto> getFollowing(String username);
    List<UserResponseDto> getMentions(Long tweetID);
    List<UserResponseDto> getLikes(Long tweetID);
}
