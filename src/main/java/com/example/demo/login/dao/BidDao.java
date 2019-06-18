package com.example.demo.login.dao;

import java.io.Serializable;

public interface BidDao<T> extends Serializable{
	
	public long countByProductId(int productId);
}
