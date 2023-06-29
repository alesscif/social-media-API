package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.*;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.mappers.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private TweetRepository tweetRepository;
    private TweetMapper tweetMapper;
    private UserRepository userRepository;

    @Override
    public List<TweetResponseDto> getFeed(String username) {
    	Optional<User> user = userRepository.findFirstByCredentialsUsername(username);
    	 if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
    	 List<User> feedUsers=user.get().getFollowing();
    	 List<Tweet> feed=new ArrayList<>();
    	 for(User u : feedUsers)
    	 {
    		 for (Tweet t : u.getTweets()) {
    		 
    			 feed.add(t);
    		 if(t.getRepostOf()!=null)
    			 feed.add(t.getRepostOf());
    		 if(t.getInReplyTo()!=null)
    			 feed.add(t.getInReplyTo());
    		 }
    	 }
    	 Collections.sort(feed, Comparator.comparing(Tweet::getPosted));
    	 
    	 
        return tweetMapper.entitiesToDtos(feed);
    }
   

    @Override
    public List<TweetResponseDto> getTweets(String username) {
        Optional<User> user = userRepository.findFirstByCredentialsUsername(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        return tweetMapper.entitiesToDtos(tweetRepository.findByAuthor(user.get()));
    }

    @Override
    public List<TweetResponseDto> getTweetsWithLabel(String label) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getAllTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findAll(Sort.by(Sort.Direction.DESC,"posted")));
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetToCreate) {
        return null;
    }

    @Override
    public TweetResponseDto getTweet(Long tweetID) {
        Optional<Tweet> tweet = tweetRepository.findById(tweetID);
        if (tweet.isEmpty()) throw new NotFoundException("no tweet found with provided id");
        return tweetMapper.entityToDto(tweet.get());
    }

    @Override
    public TweetResponseDto deleteTweet(Long tweetID, CredentialsDto credentials) {
        return null;
    }

    @Override
    public void likeTweet(Long tweetId, CredentialsDto credentials) {
        return;
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

    @Override
    public List<TweetResponseDto> getTweetsWithUserMentions(String username) {
        Optional<User> user = userRepository.findFirstByCredentialsUsername(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        return tweetMapper.entitiesToDtos(tweetRepository.findByAuthor(user.get()));
    }

}
