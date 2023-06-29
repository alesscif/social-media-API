package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
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

	@GetMapping
	public List<TweetResponseDto> getAllTweets() {
		return tweetService.getAllTweets();
	}

	@GetMapping(path="/{id}")
	public TweetResponseDto getTweet(@PathVariable Long id) {
		return tweetService.getTweet(id);
	}
	
	@GetMapping(path="/{id}/context")
	public ContextDto getContext(@PathVariable Long id) {
		return tweetService.getContext(id);
	}

	@PostMapping
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweet(tweetRequestDto);
	}

	@GetMapping(path="/{id}/reposts")
	public List<TweetResponseDto> getReposts(@PathVariable Long id){
		return tweetService.getReposts(id);
	}
	
	@GetMapping(path="/{id}/replies")
	public List<TweetResponseDto> getReplies(@PathVariable Long id) {
		return tweetService.getReplies(id);
		
	}
	
	@GetMapping(path="/{id}/likes")
    public List<UserResponseDto> getLikes(@PathVariable Long id) {
    	return userService.getLikes(id);
    	
    }
}
