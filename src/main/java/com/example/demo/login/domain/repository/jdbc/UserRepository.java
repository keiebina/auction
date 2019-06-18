package com.example.demo.login.domain.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	public User findByUserId(String userId); 
	
}
