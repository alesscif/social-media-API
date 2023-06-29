package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;
    @Override
    public List<UserResponseDto> getAllUsers() {
    	return userMapper.entitiesToDtos(userRepository.findAll(Sort.by(Sort.Direction.DESC,"posted")));
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userToCreate) {
        return null;
    }

    @Override
    public UserResponseDto getUser(String username) {
        return null;
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto updateData) {
        return null;
    }

    @Override
    public UserResponseDto deleteUser(UserRequestDto userToDelete) {
        return null;
    }

    @Override
    public void followUser(UserRequestDto userToFollow) {

    }

    @Override
    public void unfollowUser(UserRequestDto userToUnfollow) {

    }

    @Override
    public List<UserResponseDto> getFollowers(String username) {
        return null;
    }

    @Override
    public List<UserResponseDto> getFollowing(String username) {
        return null;
    }
}
