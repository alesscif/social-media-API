package com.cooksys.socialmedia.repositories;

import com.cooksys.socialmedia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByCredentialsUsernameAndDeletedFalse(String username);

    Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

    Optional<User> findByIdAndDeletedFalse(Long id);

}
