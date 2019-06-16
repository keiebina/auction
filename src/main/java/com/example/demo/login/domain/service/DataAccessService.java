package com.example.demo.login.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.dao.ProductDao;
import com.example.demo.login.dao.UserDao;
import com.example.demo.login.daoImpl.ProductDaoImpl;
import com.example.demo.login.daoImpl.UserDaoImpl;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.User;

@Service
public class DataAccessService {
	
	@PersistenceContext
	EntityManager em;

	@Autowired
	UserDao<User> uDao;
	
	@Autowired
	ProductDao<Product> pDao;
	
	@Autowired
	UserDaoImpl userDaoImpl;
	
	@Autowired
	ProductDaoImpl productDaoImpl;
	
	public List<Product> getCommingSoon(LocalDateTime now){
		return pDao.getCommingSoon(now);
	}
	
	public Product findByProductId(Integer productId) {
		return pDao.findByProductId(productId);
	}
	
}
