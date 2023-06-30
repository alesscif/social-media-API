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
    	 }
    	 feed.addAll(user.get().getTweets());
    	 feed.sort(Comparator.comparing(Tweet::getPosted));
         Collections.reverse(feed);
        return tweetMapper.entitiesToDtos(feed);
    }
   

    @Override
    public List<TweetResponseDto> getTweets(String username) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        return tweetMapper.entitiesToDtos(user.get().getTweets());
    }

    @Override
    public List<TweetResponseDto> getTweetsWithLabel(String label) {
    	Optional<Hashtag> hashtag = hashtagRepository.findByLabel(label);
    	if (hashtag.isEmpty()) throw new NotFoundException("no hashtag found with provided label");
    	List <Tweet> hTweets= hashtag.get().getTweets();
    	
    	hTweets.sort(Comparator.comparing(Tweet::getPosted));
    	Collections.reverse(hTweets);
    	return tweetMapper.entitiesToDtos(hTweets);
    	
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

        if ( !user.get().getCredentials().getPassword().equals(tweetToCreate.getCredentials().getPassword()))
            throw new NotAuthorizedException("unauthorized");
        
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
            if (!hashtagRepository.existsByLabel(label)) {
                Hashtag ht = new Hashtag();
                ht.setLabel(label.substring(1));
                hashtagRepository.saveAndFlush(ht);
                tags.add(ht);
            }
            else tags.add(hashtagRepository.findByLabel(label).get());
        }
        tweet.setHashtags(tags);

        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public TweetResponseDto getTweet(Long tweetID) {
        Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(tweetID);
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
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());

        if (user.isEmpty())
            throw new NotFoundException("no user found with provided username");

        if (!user.get().getCredentials().getPassword().equals(credentials.getPassword()))
            throw new NotAuthorizedException("unauthorized");

        Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(tweetId);
        if (tweet.isEmpty()) throw new NotFoundException("no tweet found with provided id");

        if (!tweet.get().getLikedBy().contains(user.get())) {
            tweet.get().getLikedBy().add(user.get());
        }
    }

    @Override
    public TweetResponseDto reply(Long tweetID, TweetRequestDto tweetRequest) {
        // replying user exists?
        Optional<User> replier = userRepository.findByCredentialsUsernameAndDeletedFalse(tweetRequest.getCredentials().getUsername());
        if (replier.isEmpty()) throw new NotFoundException("no user found with provided credentials");

        String providedPassword = tweetRequest.getCredentials().getPassword();

        // credentials ok?
        if (!replier.get().getCredentials().getPassword().equals(providedPassword))
            throw new NotAuthorizedException("unauthorized");

        // original tweet exists?
        Optional<Tweet> original = tweetRepository.findByIdAndDeletedFalse(tweetID);
        if (original.isEmpty()) throw new NotFoundException("no tweet found with provided id");

        Tweet tweet = new Tweet();
        // set author
        tweet.setAuthor(replier.get());

        String content = tweetRequest.getContent();
        // set content
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

        // set mentioned users
        tweet.setMentionedUsers(mentionedUsers);

        List<Hashtag> tags = new ArrayList<>();
        for (String label : hashtags) {
            if (!hashtagRepository.existsByLabel(label)) {
                Hashtag ht = new Hashtag();
                ht.setLabel(label.substring(1));
                hashtagRepository.saveAndFlush(ht);
                tags.add(ht);
            }
            else tags.add(hashtagRepository.findByLabel(label).get());        }
        // set hashtags
        tweet.setHashtags(tags);

        // add to replies to original
        List<Tweet> replies = original.get().getReplies();
        replies.add(tweet);

        // save reply and original
        tweetRepository.saveAndFlush(tweet);
        tweetRepository.saveAndFlush(original.get());

        // return reply
        return tweetMapper.entityToDto(tweet);
    }

    @Override
    public TweetResponseDto repost(Long tweetID, TweetRequestDto tweetRequest) {
    	 // replying user exists?
        Optional<User> reposter = userRepository.findByCredentialsUsernameAndDeletedFalse(tweetRequest.getCredentials().getUsername());
        if (reposter.isEmpty()) throw new NotFoundException("no user found with provided credentials");

         String providedPassword = tweetRequest.getCredentials().getPassword();

         // credentials ok?
         if (!reposter.get().getCredentials().getPassword().equals(providedPassword))
             throw new NotAuthorizedException("unauthorized");

         // original tweet exists?
         Optional<Tweet> original = tweetRepository.findByIdAndDeletedFalse(tweetID);
         if (original.isEmpty()) throw new NotFoundException("no tweet found with provided id");
         
         
    	 Tweet repos = new Tweet();
         repos.setAuthor(reposter.get());

         repos.setMentionedUsers(original.get().getMentionedUsers());
         repos.setHashtags(original.get().getHashtags());
         repos.setRepostOf(original.get());
         
         
         return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repos));
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
    	 Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(tweetID);
         if (tweet.isEmpty()) throw new NotFoundException("no tweet found with provided id");
         List <Tweet> replies=new ArrayList<>();
         for(Tweet t: tweet.get().getReplies())
         {	if(!t.isDeleted())
        	 	replies.add(t);
	
         }
         return  tweetMapper.entitiesToDtos(replies);
    }

    @Override
    public List<TweetResponseDto> getReposts(Long tweetID) {
    	 Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(tweetID);
         if (tweet.isEmpty()) throw new NotFoundException("no tweet found with provided id");
         List <Tweet> reposts=new ArrayList<>();
         for (Tweet t: tweet.get().getReposts()) {
             if (!t.isDeleted())
        	 	reposts.add(t);
         }
         return  tweetMapper.entitiesToDtos(reposts);
    }

    @Override
    public List<TweetResponseDto> getTweetsWithUserMentions(String username) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (user.isEmpty()) throw new NotFoundException("no user found with provided username");
        List <Tweet> tweets = user.get().getMentions();
        tweets.sort(Comparator.comparing(Tweet::getPosted));
        Collections.reverse(tweets);
        return tweetMapper.entitiesToDtos(tweets);
    }

}
