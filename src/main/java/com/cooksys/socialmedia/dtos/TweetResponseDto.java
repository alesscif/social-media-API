package com.cooksys.socialmedia.dtos;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.cooksys.socialmedia.entities.User;

public class TweetResponseDto {

	
	private Long id;


	private User author;


	private String posted;

	
	private String content;

}
