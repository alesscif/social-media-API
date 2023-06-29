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

import java.util.*;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserRepository userRepository;

    @Override
    public List<TweetResponseDto> getFeed(String username) {
    	Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
    	 if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
    	 List<User> feedUsers=user.get().getFollowing();
    	 List<Tweet> feed = new ArrayList<>();
    	 for(User u : feedUsers) {
//    		 for (Tweet t : u.getTweets()) {
//                 feed.add(t);
//                 feed.addAll(t.getReposts());
//                 feed.addAll(t.getReplies());
//             }
             feed.addAll(tweetRepository.findByAuthorAndDeletedFalseOrderByPostedAsc(u));
    	 }
        return tweetMapper.entitiesToDtos(feed);
    }
   

    @Override
    public List<TweetResponseDto> getTweets(String username) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        return tweetMapper.entitiesToDtos(tweetRepository.findByAuthorAndDeletedFalseOrderByPostedAsc(user.get()));
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
        Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(tweetID);
        if (tweet.isEmpty()) throw new NotFoundException("no tweet found with provided id");
        ContextDto context = new ContextDto();
        context.setTarget(tweetMapper.entityToDto(tweet.get()));

        List <Tweet> beforeT= new ArrayList<>();
        Tweet bt = tweet.get().getInReplyTo();
        while(bt != null) {
            beforeT.add(bt);
            bt = bt.getInReplyTo();
        }
        beforeT.sort( Comparator.comparing(Tweet::getPosted));
        context.setBefore(tweetMapper.entitiesToDtos(beforeT));

        List <Tweet> afterT= new ArrayList<>();
        Stack<Tweet> stack = new Stack<>();
        stack.push(tweet.get());
        while(!stack.isEmpty()) {
            List<Tweet> replies = stack.pop().getReplies();
            for (Tweet at : replies) {
                stack.push(at);
                afterT.add(at);
            }
        }
        afterT.sort(Comparator.comparing(Tweet::getPosted));
        context.setAfter(tweetMapper.entitiesToDtos(afterT));

        return context;
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
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        return tweetMapper.entitiesToDtos(tweetRepository.findByAuthorAndDeletedFalseOrderByPostedAsc(user.get()));
    }

}
