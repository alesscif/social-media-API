package com.cooksys.socialmedia.entities;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name="tweets", nullable = false)
	private User author;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Timestamp posted;

	private String content;
	
	private boolean deleted = false;

	@ManyToOne
	private Tweet inReplyTo;

	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies;

	@ManyToOne
	private Tweet repostOf;

	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts;
	
	@ManyToMany
	@JoinTable(name="tweet_hashtags",
			joinColumns = @JoinColumn(name="tweet_id"),
			inverseJoinColumns = @JoinColumn(name="hashtag_id"))
	private List<Hashtag> hashtags;

	@ManyToMany
	@JoinTable(name="user_mentions",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="tweet_id"))
	private List<User> mentionedUsers;
	
	@ManyToMany(mappedBy="likedTweets")
	private List<User> likedBy;

}
