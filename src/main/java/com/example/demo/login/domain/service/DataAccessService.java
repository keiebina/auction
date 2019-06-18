package com.example.demo.login.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.daoImpl.DaoImpl;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.WatchList;

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

	public long countByProductId(int productId) {
		return dao.countByProductId(productId);
	}
	
//==================================================================================================================
//														watchList
//==================================================================================================================
	
	public boolean checkWatchList(String userId, Integer productId) {
		return dao.checkWatchList(userId, productId);
	}
	public Integer getWatchListIdByUserIdAndProductId(String userId, Integer productId) {
		return dao.getWatchListIdByUserIdAndProductId(userId, productId);
	}
	public List<WatchList> getWatchListByUserId(String userId){
		return dao.getWatchListByUserId(userId);
	}
}
