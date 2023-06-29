package com.cooksys.socialmedia.entities;


import com.cooksys.socialmedia.entities.embeddable.Credentials;
import com.cooksys.socialmedia.entities.embeddable.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
@NoArgsConstructor
@Data
@Table(name="user_table")
@SQLDelete(sql = "UPDATE user_table SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Credentials credentials;

    @Embedded
    @Column(nullable = false)
    private Profile profile;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp joined;

    private boolean deleted = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name="followers_following")
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;

    @ManyToMany
    @JoinTable(name="user_likes",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="tweet_id"))
    private List<Tweet> likedTweets;

    @ManyToMany(mappedBy = "mentionedUsers")
    private List<Tweet> mentions;

}
