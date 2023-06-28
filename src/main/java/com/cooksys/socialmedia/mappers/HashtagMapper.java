package com.cooksys.socialmedia.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.entities.Hashtag;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

	HashtagDto entityToDto(Hashtag entity);

	List<HashtagDto> entitiesToDtos(List<Hashtag> entities);

	Hashtag dtoToEntity(HashtagDto dto);

	List<Hashtag> dtostoEntities(List<HashtagDto> dtos);

}
