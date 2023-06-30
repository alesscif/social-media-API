package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.exceptions.ResourceExistsException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.ProfileMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import com.cooksys.socialmedia.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetRepository tweetRepository;
    private final ProfileMapper profileMapper;
    private final CredentialsMapper credentialsMapper;
    private final ValidateService validateService;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userToCreate) {

        CredentialsDto credentials = userToCreate.getCredentials();
        if (credentials == null || credentials.getUsername().isEmpty() || credentials.getPassword().isEmpty())
            throw new BadRequestException("missing required fields: username, password");

        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedTrue(credentials.getUsername());
        if (user.isPresent()) {
            if (!user.get().getCredentials().getPassword().equals(userToCreate.getCredentials().getPassword()))
                throw new NotAuthorizedException("unauthorized");
            user.get().setDeleted(false);
            return userMapper.entityToDto(userRepository.saveAndFlush(user.get()));
        }
        ProfileDto profile = userToCreate.getProfile();
        if (profile == null || profile.getEmail().isEmpty())
            throw new BadRequestException("missing required field: email");

        if (!validateService.usernameIsAvailable(credentials.getUsername()))
            throw new ResourceExistsException("username already exists");

        User newUser = new User();
        newUser.setCredentials(credentialsMapper.dtoToEntity(credentials));
        newUser.setProfile(profileMapper.dtoToEntity(profile));
        return userMapper.entityToDto(userRepository.saveAndFlush(newUser));
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
    public UserResponseDto deleteUser(String username, CredentialsDto credentials) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        if (user.isEmpty())
            throw new NotFoundException("no user found with provided username");

        if (!user.get().getCredentials().getUsername().equals(credentials.getUsername())
                || !user.get().getCredentials().getPassword().equals(credentials.getPassword()))
            throw new NotAuthorizedException("unauthorized");

        user.get().setDeleted(true);
        for (Tweet tweet : user.get().getTweets()) {
            tweet.setDeleted(true);
            tweetRepository.saveAndFlush(tweet);
        }
        return userMapper.entityToDto(userRepository.save(user.get()));
    }

    @Override
    public void followUser(String username, CredentialsDto credentials) {

        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());

        if (user.isEmpty())
            throw new NotFoundException("no user found with provided username");

        if (!user.get().getCredentials().getUsername().equals(credentials.getUsername())
                || !user.get().getCredentials().getPassword().equals(credentials.getPassword()))
            throw new NotAuthorizedException("unauthorized");

        Optional<User> userToFollow = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        if (userToFollow.isEmpty())
            throw new NotFoundException("no user to follow found with provided username");

        if (userToFollow.get().getFollowers().contains(user.get()))
            throw new BadRequestException("following relationship already exists");

        userToFollow.get().getFollowers().add(user.get());
        userRepository.saveAndFlush(userToFollow.get());

    }

    @Override
    public void unfollowUser(String username, CredentialsDto credentials) {

        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());

        if (user.isEmpty())
            throw new NotFoundException("no user found with provided username");

        if (!user.get().getCredentials().getUsername().equals(credentials.getUsername())
                || !user.get().getCredentials().getPassword().equals(credentials.getPassword()))
            throw new NotAuthorizedException("unauthorized");

        Optional<User> userToFollow = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        if (userToFollow.isEmpty())
            throw new NotFoundException("no user to unfollow found with provided username");

        if (!userToFollow.get().getFollowers().contains(user.get()))
            throw new BadRequestException("no following relationship exists");

        userToFollow.get().getFollowers().remove(user.get());
        userRepository.saveAndFlush(userToFollow.get());

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
    	Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(tweetID);
    	if (tweet.isEmpty()) throw new NotFoundException("no tweet found with provided id");
    	List<User> users = new ArrayList<>();
    			for (User u : tweet.get().getLikedBy()) {
    				if (!u.isDeleted()) users.add(u);
    			}
          return userMapper.entitiesToDtos(users);
    }

}
