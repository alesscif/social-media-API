package com.cooksys.socialmedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;


@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TweetMapper {

	
	TweetResponseDto entityToDto(Tweet entity);

	List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

	Tweet dtoToEntity(TweetRequestDto dto);

	List<Tweet> dtostoEntities(List<TweetRequestDto> dtos);
}
