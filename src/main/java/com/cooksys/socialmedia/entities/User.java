package com.cooksys.socialmedia.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name="user_table")
public class User {

    @Id
    private String username;

    @Embedded
    private Credentials credentials;

    @Embedded
    private Profile profile;

    private Timestamp joined;

    private Boolean deleted;

    @ManyToMany
    @JoinTable(name="followers_following",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;

    @OneToMany(mappedBy = "")
    private List<Tweet> tweets;

    @OneToMany(mappedBy = "")
    private List<Tweet> likes;

    @OneToMany(mappedBy = "")
    private List<Tweet> mentions;







}
