package com.example.demo.login.dao;

import java.io.Serializable;

public interface UserDao<T> extends Serializable{
	
	public long countByUserId(String userId);
}
