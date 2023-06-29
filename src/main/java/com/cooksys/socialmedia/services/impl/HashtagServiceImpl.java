package com.cooksys.socialmedia.services.impl;

import java.util.List;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private final HashtagMapper hashtagMapper;
	private final HashtagRepository hashtagRepository;
	
	
	@Override
	public List<HashtagDto> getAllHashtags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

	@Override
	public List<HashtagDto> getTags(Long tweetID) {
		return null;
	}

}
