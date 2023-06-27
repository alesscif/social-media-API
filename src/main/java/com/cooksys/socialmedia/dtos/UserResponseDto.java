package com.cooksys.socialmedia.dtos;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.cooksys.socialmedia.embeddables.Profile;


import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class UserResponseDto {

    private String username;

    private ProfileDto profile;

    private Timestamp joined;


}
