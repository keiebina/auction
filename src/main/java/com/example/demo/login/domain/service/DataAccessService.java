package com.example.demo.login.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.daoImpl.BidDaoImpl;
import com.example.demo.login.daoImpl.ProductDaoImpl;
import com.example.demo.login.daoImpl.WatchListDaoImpl;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.WatchList;

@Service
public class DataAccessService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	ProductDaoImpl productDaoImpl;
	
	@Autowired
	BidDaoImpl bidDaoimpl;
	
	@Autowired
	WatchListDaoImpl watchListDaoImpl;
	
	public EntityManager setEntitymanager(EntityManager em) {
		em = entityManager;
		return em;
	}
	
	
	
//==================================================================================================================
//														product
//==================================================================================================================
	
	public List<Product> getCommingSoon(LocalDateTime now){
		return productDaoImpl.getCommingSoon(now);
	}
	
	public Product findByProductId(Integer productId) {
		return productDaoImpl.findByProductId(productId);
	}
	
//==================================================================================================================
//														bid
//==================================================================================================================

	public long countByProductId(int productId) {
		return bidDaoimpl.countByProductId(productId);
	}
	
//==================================================================================================================
//														watchList
//==================================================================================================================
	
	public boolean checkWatchList(String userId, Integer productId) {
		return watchListDaoImpl.checkWatchList(userId, productId);
	}
	public Integer getWatchListIdByUserIdAndProductId(String userId, Integer productId) {
		return watchListDaoImpl.getWatchListIdByUserIdAndProductId(userId, productId);
	}
	public List<WatchList> getWatchListByUserId(String userId){
		return watchListDaoImpl.getWatchListByUserId(userId);
	}
}
