package com.example.demo.login.dao;

import java.io.Serializable;
import java.util.List;

public interface UserDao<T> extends Serializable{
	
	public List<T> getAll();
	
}
