package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    @Override
    public boolean hashTagExists(String label) {
        return false;
    }

    @Override
    public boolean usernameExists(String username) {
        return false;
    }

    @Override
    public boolean usernameIsAvailable(String username) {
        return false;
    }

}
