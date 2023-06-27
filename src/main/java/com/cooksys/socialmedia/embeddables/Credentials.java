package com.cooksys.socialmedia.embeddables;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;


@Embeddable
@NoArgsConstructor
@Data
public class Credentials {
	@Column(nullable = false)
	private String userName;

	@Column(nullable = false)
	private String password;
}
