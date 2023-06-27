package com.cooksys.socialmedia.repositories;

import com.cooksys.socialmedia.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Profile, Long> {

}
