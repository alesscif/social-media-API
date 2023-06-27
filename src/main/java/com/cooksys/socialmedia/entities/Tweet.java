package com.cooksys.socialmedia.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
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
	
	
	private boolean deleted;

	
	private List <Long> inReplyTo;
	
	
	private List <Long> repostOf;
	
	@ManyToMany
    @JoinTable(name="tweet_hashtags")
	private Long tweet_id;


}
