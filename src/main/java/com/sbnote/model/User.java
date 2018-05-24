package com.sbnote.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Size(max = 8)
    private String userId;

    @NotNull(message="Name is required.")
    @Size(min=5, max=50, message="Field cannot exceed 50 characters in length.")
    private String name;
    
    @NotNull(message="Password is required.")
    @Size(max=60, message="Field cannot exceed 60 characters in length.")
    private String password;

    @NotNull
    @Email(message="Please enter valid email address.")
    private String email;
    
    public User() {}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(Long id, String userId, String name, String password, String email) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String user_id) {
		this.userId = user_id;
	}

}
