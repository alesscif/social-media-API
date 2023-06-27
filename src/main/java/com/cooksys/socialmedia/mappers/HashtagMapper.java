package com.cooksys.socialmedia.mappers;

import org.mapstruct.Mapping;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.entities.Hashtag;

import java.util.List;

public interface HashtagMapper {

	@Mapping(target = "username", source = "credentials.username")
	HashtagDto entityToDto(Hashtag entity);

	List<HashtagDto> entitiesToDtos(List<Hashtag> entities);

	Hashtag dtoToEntity(HashtagDto dto);

	List<Hashtag> dtostoEntities(List<HashtagDto> dtos);

}
