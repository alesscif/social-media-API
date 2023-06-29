package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.*;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.mappers.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    public List<TweetResponseDto> getFeed(String username) {
    	Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
    	 if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
    	 List<User> feedUsers=user.get().getFollowing();
    	 List<Tweet> feed = new ArrayList<>();
    	 for(User u : feedUsers) {
    		 for (Tweet t : u.getTweets()) {
                 feed.add(t);
                 feed.addAll(t.getReposts());
                 feed.addAll(t.getReplies());
             }
             feed.sort(Comparator.comparing(Tweet::getPosted));
             Collections.reverse(feed);
    	 }
        return tweetMapper.entitiesToDtos(feed);
    }
   

    @Override
    public List<TweetResponseDto> getTweets(String username) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        return tweetMapper.entitiesToDtos(tweetRepository.findByAuthorAndDeletedFalseOrderByPostedDesc(user.get()));
    }

    @Override
    public List<TweetResponseDto> getTweetsWithLabel(String label) {
        return null;
    }

    @Override
    public List<TweetResponseDto> getAllTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalseOrderByPostedDesc());
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetToCreate) {
        String username = tweetToCreate.getCredentials().getUsername();

        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided credentials");

        Tweet tweet = new Tweet();
        tweet.setAuthor(user.get());

        String content = tweetToCreate.getContent();
        tweet.setContent(content);

        List<String> mentions = Pattern.compile("(@[a-zA-Z0-9]+)")
                .matcher(content)
                .results()
                .map(MatchResult::group)
                .toList();

        List<String> hashtags = Pattern.compile("(#[a-zA-Z0-9]+)")
                .matcher(content)
                .results()
                .map(MatchResult::group)
                .toList();

        List<User> mentionedUsers = new ArrayList<>();
        for (String mention : mentions) {
            Optional<User> u = userRepository.findByCredentialsUsernameAndDeletedFalse(mention.substring(1));
            u.ifPresent(mentionedUsers::add);
        }
        tweet.setMentionedUsers(mentionedUsers);

        List<Hashtag> tags = new ArrayList<>();
        for (String label : hashtags) {
            Optional<Hashtag> h = hashtagRepository.findByLabel(label.substring(1));
            if (h.isEmpty()) {
                Hashtag ht = new Hashtag();
                ht.setLabel(label.substring(1));
                hashtagRepository.saveAndFlush(ht);
                tags.add(ht);
                continue;
            }
            tags.add(h.get());
        }
        tweet.setHashtags(tags);

        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public TweetResponseDto getTweet(Long tweetID) {
        Optional<Tweet> tweet = tweetRepository.findById(tweetID);
        if (tweet.isEmpty()) throw new NotFoundException("no tweet found with provided id");
        return tweetMapper.entityToDto(tweet.get());
    }

    @Override
    public TweetResponseDto deleteTweet(Long tweetID, CredentialsDto credentials) {
        Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(tweetID);

        if (tweet.isEmpty())
            throw new NotFoundException("no tweet found with provided id");

        User author = tweet.get().getAuthor();

        if (!author.getCredentials().getUsername().equals(credentials.getUsername())
                || !author.getCredentials().getPassword().equals(credentials.getPassword()))
            throw new NotAuthorizedException("unauthorized");

        tweet.get().setDeleted(true);
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet.get()));
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
        	if(!bt.isDeleted()) beforeT.add(bt);
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
                if(!at.isDeleted()) afterT.add(at);
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
        return tweetMapper.entitiesToDtos(tweetRepository.findByAuthorAndDeletedFalseOrderByPostedDesc(user.get()));
    }

}
