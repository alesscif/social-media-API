package com.cooksys.socialmedia.entities;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {
	
	  	@Id
		@GeneratedValue
		private Long id;

		@Column(nullable = false)
		private String label;

		@CreationTimestamp
		@Column(nullable = false)
		private Timestamp firstUsed;

		@UpdateTimestamp
		@Column(nullable = false)
		private Timestamp lastUsed;
		
		@ManyToMany
		@JoinTable(name="tweet_hashtags",
		joinColumns = @JoinColumn(name="tweet_id"),
		inverseJoinColumns = @JoinColumn(name="hashtag_id"))
		private List<Tweet> tweets;

}
