package com.cooksys.socialmedia.embeddables;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Profile {
	
	
	
	private String firstName;

	
	private String lastName;

	@Column(nullable = false)
	private String email;

	
	private String phone;

}
