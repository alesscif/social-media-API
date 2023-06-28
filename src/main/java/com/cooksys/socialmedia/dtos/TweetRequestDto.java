package com.cooksys.socialmedia.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class TweetRequestDto {
	
	
	private UserRequestDto author;

	
	private Timestamp posted;

	
	private String content;

}
