package com.sbnote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbnote.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByuserId(String userId);
	User findByEmail(String email);
}