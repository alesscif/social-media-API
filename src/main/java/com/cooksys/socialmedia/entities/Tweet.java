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
@Table(name = "tweet")
@SQLDelete(sql = "UPDATE tweet SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Tweet {
	
	@Id
	@OneToMany(mappedBy="hashtag")
	@GeneratedValue
	private Long id;

	
	@Column(nullable = false)
	private Long author;

	
	@CreationTimestamp
	@Column(nullable = false)
	private Timestamp posted;

	
	private String content;
	
	
	private boolean deleted = Boolean.FALSE;
	
	private List <Long> inReplyTo;
	
	
	private List <Long> repostOf;
	
	@ManyToMany
    @JoinTable(name="tweet_hashtags")
	private List <Hashtag> hashtags;

	private List <User> mentions; 

}
