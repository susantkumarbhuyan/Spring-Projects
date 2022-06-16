package com.bookstore.model;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class User {
	@Id
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	private String id;
	
	private String emailId; //user unique id (Email)
	
	@NotNull (message = "Name Shouldn't be Null")	
	private String fullName;
	
	@NotBlank(message = "username is Required")
	@Size(max = 20, message = "Username Should be beteewn 20 Characters")
	private String username;
	
	@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
	@Email(message = "Invalid Email address")
	private String email;
	
	@NotBlank(message = "password is required")
	private String password;
	
	@DBRef
	private Set<Role> roles = new HashSet<>();
	
//	private Address address;

	public User() {
	}


	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public User(String fullName, String username, String email, String password) {
		this.fullName=fullName;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
//
//	public Address getAddress() {
//		return address;
//	}
//
//	public void setAddress(Address address) {
//		this.address = address;
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
