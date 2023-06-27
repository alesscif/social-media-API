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

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {
	
	  	@Id
	  	@GeneratedValue
		private Long id;

		@Column(nullable = false)
		private String label;

		@Column(nullable = false)
		private String firstUsed;

		@Column(nullable = false)
		private String lastUsed;
		
		
		@ManyToMany
	    @JoinTable(name="tweet_hashtags")
		private Long hashtag_id;

}
