package com.example.demo.login.dao;

import java.io.Serializable;

import com.example.demo.login.domain.model.User;


public interface BidDao<T> extends Serializable{
	
	public long countByProductId(int productId);
	public User getByProductIdOrderByBidPrice(int productId);
	
}
