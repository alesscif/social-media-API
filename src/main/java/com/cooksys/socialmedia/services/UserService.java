package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();
    UserResponseDto createUser(UserRequestDto userToCreate);
    UserResponseDto getUser(String username);
    UserResponseDto updateUser(String username, UserRequestDto updateData);
    UserResponseDto deleteUser(String username, CredentialsDto credentials);
    void followUser(String username, CredentialsDto credentials);
    void unfollowUser(String username, CredentialsDto credentials);
    List<UserResponseDto> getFollowers(String username);
    List<UserResponseDto> getFollowing(String username);
    List<UserResponseDto> getMentionedUsers(Long tweetID);
    List<UserResponseDto> getLikes(Long tweetID);

}
