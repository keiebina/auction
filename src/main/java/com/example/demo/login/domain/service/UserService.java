package com.example.demo.login.domain.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.daoImpl.UserDaoImpl;
import com.example.demo.login.domain.model.User;


@Service
public class UserService {
	
	@Autowired
	UserDaoImpl userDaoImpl;
	
	public List<User> getAll(){
		List<User> users = userDaoImpl.getAll();
		return users;
	}
}
