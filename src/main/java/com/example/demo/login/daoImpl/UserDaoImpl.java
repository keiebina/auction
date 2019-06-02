package com.example.demo.login.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.UserDao;
import com.example.demo.login.domain.model.User;

@Repository
public class UserDaoImpl implements UserDao<User> {
	
	private static final long serialVersionUID = 1L;
	
	private EntityManager entityManager;

	public UserDaoImpl() {
		super();
	}
	
	public UserDaoImpl(EntityManager manager) {
		this();
		entityManager = manager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		return entityManager.createQuery("from Users").getResultList();
	}

}
