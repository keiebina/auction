package com.example.demo.login.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.daoImpl.DaoImpl;
import com.example.demo.login.domain.model.Product;

@Service
public class DataAccessService {
	
	@Autowired
	DaoImpl dao;
	
	
//==================================================================================================================
//														product
//==================================================================================================================
	
	public List<Product> getCommingSoon(LocalDateTime now){
		return dao.getCommingSoon(now);
	}
	
	public Product findByProductId(Integer productId) {
		return dao.findByProductId(productId);
	}
	
//==================================================================================================================
//														bid
//==================================================================================================================

	//ここでcatchに
	public long countByProductId(int productId) {
//		try {
			return dao.countByProductId(productId);
//		} catch (DataAccessException e) {
//			return 0L;
//		}
	}
	
}
