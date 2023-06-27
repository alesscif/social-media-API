package com.cooksys.socialmedia.mappers;

import java.util.List;

import org.mapstruct.Mapping;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;



public interface TweetMapper {

	@Mapping(target = "username", source = "credentials.username")
	TweetResponseDto entityToDto(Tweet entity);

	List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

	Tweet dtoToEntity(TweetRequestDto dto);

	List<Tweet> dtostoEntities(List<TweetRequestDto> dtos);
}
