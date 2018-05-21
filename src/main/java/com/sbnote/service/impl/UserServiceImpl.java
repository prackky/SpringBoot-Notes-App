package com.sbnote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbnote.model.User;
import com.sbnote.repository.UserRepository;
import com.sbnote.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    UserRepository userRepository;
    
    public boolean createUser(User user) {

        return userRepository.save(user) != null ? true : false;
    }
    
    public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByuserId(String userId) {
		return userRepository.findByuserId(userId);
	}


}