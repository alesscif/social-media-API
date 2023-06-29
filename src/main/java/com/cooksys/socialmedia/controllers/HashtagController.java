package com.cooksys.socialmedia.controllers;


import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.services.HashtagService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import com.cooksys.socialmedia.services.HashtagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
	
	private final HashtagService hashtagService;
	
	@GetMapping
	public List<HashtagDto> getAllHashtags() {
		return hashtagService.getAllHashtags();
	}

	@GetMapping
	public List<TweetResponseDto> getTweets(String label) {
		return null;
	}
}
