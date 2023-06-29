package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TweetService tweetService;

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

}
