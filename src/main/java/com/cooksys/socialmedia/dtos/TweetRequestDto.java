package com.cooksys.socialmedia.dtos;

import javax.persistence.Column;

import com.cooksys.socialmedia.entities.User;

public class TweetRequestDto {
	
	
	private User author;

	
	private String posted;

	
	private String content;

}
