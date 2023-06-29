package com.cooksys.socialmedia.services.impl;

import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.socialmedia.dtos.*;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.mappers.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;
@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private TweetRepository tweetRepository;
    private TweetMapper tweetMapper;
    private UserRepository userRepository;

    private Tweet getTweetById(Long id) {
		Optional<Tweet> optionalTweet = tweetRepository.findById(id);

		if (optionalTweet.isEmpty()) {
			throw new IllegalStateException();
		}

		return optionalTweet.get();
	}

	private User getUser(String userName) {
		Optional<User> optionalUser = tweetRepository.findUserByUserName(userName);

		if (optionalUser.isEmpty()) {
			throw new IllegalStateException();
		}
    @Override
    public List<TweetResponseDto> getFeed(String username) {
    	
    		User user = getUser(username);
    		List <Tweet> feed=user.getTweets(); 
    		
    		
    		
    		
    		

    		
    		
    	
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
        return tweetMapper.entitiesToDtos(tweetRepository.findAll());
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

}
