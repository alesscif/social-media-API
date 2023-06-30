package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TweetService tweetService;

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path="/@{username}/followers")
    public List<UserResponseDto> getFollowers(@PathVariable String username) {
        return userService.getFollowers(username);
    }

    @GetMapping(path="/@{username}/following")
    public List<UserResponseDto> getFollowing(@PathVariable String username) {
        return userService.getFollowing(username);
    }

    @GetMapping(path="/@{username}/mentions")
    public List<TweetResponseDto> getMentions(@PathVariable String username) {
        return tweetService.getTweetsWithUserMentions(username);
    }

    @GetMapping(path="/@{username}/tweets")
    public List<TweetResponseDto> getTweetsByUser(@PathVariable String username) {
        return tweetService.getTweets(username);
    }

    @GetMapping(path="/@{username}")
    public UserResponseDto getUser(@PathVariable String username) {
        return userService.getUser(username);
    }
    
    @GetMapping(path="/@{username}/feed")
    public List<TweetResponseDto> getFeed(@PathVariable String username) {
        return tweetService.getFeed(username);
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto createData) {
        return userService.createUser(createData);
    }

    @PostMapping(path="/@{username}/follow")
    public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentials) {
        userService.followUser(username, credentials);
    }

    @PostMapping(path="/@{username}/unfollow")
    public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentials) {
        userService.unfollowUser(username, credentials);
    }

    @PatchMapping(path="/@{username}")
    public UserResponseDto updateUser(@PathVariable String username, @RequestBody UserRequestDto updateData) {
        return userService.updateUser(username, updateData);
    }

    @DeleteMapping(path="/@{username}")
    public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentials) {
        return userService.deleteUser(username, credentials);
    }

}
