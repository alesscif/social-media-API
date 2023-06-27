package com.cooksys.socialmedia.services;

public interface ValidateService {

    boolean hashTagExists(String label);
    boolean usernameExists(String username);
    boolean usernameIsAvailable(String username);

}
