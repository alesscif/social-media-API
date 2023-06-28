package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.*;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    @Override
    public List<TweetResponseDto> getFeed(String username) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getTweets(String username) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getMentions(String username) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getTweetsWithLabel(String label) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getAllTweets() {
        return null;
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetToCreate) {
        return null;
    }

    @Override
    public TweetResponseDto getTweet(Long tweetID) {
        return null;
    }

    @Override
    public TweetResponseDto deleteTweet(Long tweetID, CredentialsDto credentials) {
        return null;
    }

    @Override
    public void likeTweet(Long tweetId, CredentialsDto credentials) {

    }

    @Override
    public TweetResponseDto reply(Long tweetID, String content, CredentialsDto credentials) {
        return null;
    }

    @Override
    public TweetResponseDto repost(Long tweetID, CredentialsDto credentials) {
        return null;
    }

    @Override
    public List<HashtagDto> getTags(Long tweetID) {
        return null;
    }

    @Override
    public List<UserResponseDto> getLikes(Long tweetID) {
        return null;
    }

    @Override
    public ContextDto getContext(Long tweetID) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getReplies(Long tweetID) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getReposts(Long tweetID) {
        return null;
    }

    @Override
    public List<UserResponseDto> getMentions(Long tweetID) {
        return null;
    }
}
