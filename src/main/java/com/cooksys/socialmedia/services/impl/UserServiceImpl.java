package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.ProfileMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetRepository tweetRepository;
    private final ProfileMapper profileMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return null;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userToCreate) {
        return null;
    }

    @Override
    public UserResponseDto getUser(String username) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        return userMapper.entityToDto(user.get());
    }

    @Override
    public UserResponseDto updateUser(String username, UserRequestDto updateData) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        if (user.isEmpty())
            throw new NotFoundException("no user found with provided username");

        String providedUsername = updateData.getCredentials().getUsername();
        String providedPassword = updateData.getCredentials().getPassword();

        if (!user.get().getCredentials().getPassword().equals(providedPassword)
                || !user.get().getCredentials().getUsername().equals(providedUsername))
            throw new NotAuthorizedException("unauthorized");

        user.get().setProfile(profileMapper.dtoToEntity(updateData.getProfile()));
        return userMapper.entityToDto(userRepository.saveAndFlush(user.get()));
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
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        return userMapper.entitiesToDtos(user.get().getFollowers());
    }

    @Override
    public List<UserResponseDto> getFollowing(String username) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        return userMapper.entitiesToDtos(user.get().getFollowing());
    }

    @Override
    public List<UserResponseDto> getMentionedUsers(Long tweetID) {
        Optional<Tweet> tweet = tweetRepository.findById(tweetID);
        if (tweet.isEmpty()) throw new NotFoundException("no tweet found with provided id");
        return userMapper.entitiesToDtos(tweet.get().getMentionedUsers());
    }

    @Override
    public List<UserResponseDto> getLikes(Long tweetID) {
        return null;
    }


}
