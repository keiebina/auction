package com.example.demo.login.domain.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.login.domain.model.User;

public interface UserRepository extends JpaRepository<User, String>{

}
