package com.cooksys.socialmedia.repositories;

import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByAuthorAndDeletedFalseOrderByPostedDesc(User author);

    Optional<Tweet> findByIdAndDeletedFalse(Long id);

    List<Tweet> findAllByDeletedFalseOrderByPostedDesc();
}

