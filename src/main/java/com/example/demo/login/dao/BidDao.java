package com.example.demo.login.dao;

import java.io.Serializable;

public interface BidDao<T> extends Serializable{
	
	public Integer countByProductId(Integer productId);
}
