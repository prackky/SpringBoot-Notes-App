package com.sbnote.service;

import com.sbnote.model.User;

public interface UserService {

	boolean createUser(User user);
	User findByEmail(String email);
	User findByuserId(String userId);

}
