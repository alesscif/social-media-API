package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private HashtagRepository hashtagRepository;
    private UserRepository userRepository;

    @Override
    public boolean hashtagExists(String label) {
        return hashtagRepository.existsByLabel(label);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean usernameIsAvailable(String username) {
        return false;
    }

}
