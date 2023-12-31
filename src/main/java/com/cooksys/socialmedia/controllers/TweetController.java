package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dtos.*;
import com.cooksys.socialmedia.services.HashtagService;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;
    private final HashtagService hashtagService;

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping(path = "/{id}")
    public TweetResponseDto getTweet(@PathVariable Long id) {
        return tweetService.getTweet(id);
    }

    @GetMapping(path = "/{id}/context")
    public ContextDto getContext(@PathVariable Long id) {
        return tweetService.getContext(id);
    }

    @PostMapping
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    @GetMapping(path = "/{id}/reposts")
    public List<TweetResponseDto> getReposts(@PathVariable Long id) {
        return tweetService.getReposts(id);
    }

    @GetMapping(path = "/{id}/replies")
    public List<TweetResponseDto> getReplies(@PathVariable Long id) {
        return tweetService.getReplies(id);
    }

    @GetMapping(path = "/{id}/likes")
    public List<UserResponseDto> getLikes(@PathVariable Long id) {
        return userService.getLikes(id);
    }

    @GetMapping(path = "/{id}/tags")
    public List<HashtagDto> getTags(@PathVariable Long id) {
        return hashtagService.getTags(id);
    }

    @PostMapping(path = "/{id}/reply")
    public TweetResponseDto reply(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequest) {
        return tweetService.reply(id, tweetRequest);
    }

    @PostMapping(path = "/{id}/repost")
    public TweetResponseDto repost(@PathVariable Long id, @RequestBody CredentialsDto credentials) {
        return tweetService.repost(id, credentials);
    }

    @PostMapping(path = "/{id}/like")
    public void like(@PathVariable Long id, @RequestBody CredentialsDto credentials) {
        tweetService.likeTweet(id, credentials);
    }

    @GetMapping(path = "/{id}/mentions")
    public List<UserResponseDto> getMentionedUsers(@PathVariable Long id) {
        return userService.getMentionedUsers(id);
    }

    @DeleteMapping(path = "/{id}")
    public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentials) {
        return tweetService.deleteTweet(id, credentials);
    }

}
