package com.cooksys.socialmedia.controllers;


import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.services.HashtagService;
import com.cooksys.socialmedia.services.TweetService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import com.cooksys.socialmedia.services.HashtagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
	
	private final HashtagService hashtagService;
	private final TweetService tweetService;
	
	@GetMapping
	public List<HashtagDto> getAllHashtags() {
		return hashtagService.getAllHashtags();
	}

	@GetMapping (path = "/{label}")
	public List<TweetResponseDto> getTweetsWithLabel(@PathVariable String label) {
		return tweetService.getTweetsWithLabel(label);
	}

	
}
