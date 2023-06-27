package com.cooksys.socialmedia.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.cooksys.socialmedia.embeddables.Credentials;
import com.cooksys.socialmedia.embeddables.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Entity
@NoArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private Credentials credentials;

	@Column(nullable = false)
	private String joined;

	@Embedded
	private Profile profile;

	@Column(nullable = false)
	private boolean deleted;

}
