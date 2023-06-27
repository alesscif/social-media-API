package com.cooksys.socialmedia.dtos;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.cooksys.socialmedia.embeddables.Profile;

public class UserResponseDto {
	
	private Long id;

	
	private String joined;


	private Profile profile;

}
