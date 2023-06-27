package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.*;

import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getFeed(String username);
    List<TweetResponseDto> getTweets(String username);
    List<TweetResponseDto> getMentions(String username);
    List<TweetResponseDto> getTweetsWithLabel(String label);
    List<TweetResponseDto> getAllTweets();
    TweetResponseDto createTweet(TweetRequestDto tweetToCreate);
    TweetResponseDto getTweet(Long tweetID);
    TweetResponseDto deleteTweet(Long tweetID, CredentialsDto credentials);
    void likeTweet(Long tweetId, CredentialsDto credentials);
    TweetResponseDto reply(Long tweetID, String content, CredentialsDto credentials);
    TweetResponseDto repost(Long tweetID, CredentialsDto credentials);
    List<HashtagDto> getTags(Long tweetID);
    List<UserResponseDto> getLikes(Long tweetID);
    ContextDto getContext(Long tweetID);
    List<TweetResponseDto> getReplies(Long tweetID);
    List<TweetResponseDto> getReposts(Long tweetID);
    List<UserResponseDto> getMentions(Long tweetID);



}
