package com.cooksys.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.socialmedia.entities.Hashtag;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long>  {

    boolean existsByLabel(String label);

    Optional<Hashtag> findByLabel(String label);

	

}
