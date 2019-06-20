package com.example.demo.login.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.login.domain.model.Product;

public interface ProductDao<T> extends Serializable {

	public List<T> getCommingSoon(LocalDateTime now);
	
	public Product findByProductId(Integer productId); 
	
	public List<Product> getProductByStatusFlag();
	
	public List<Product> getProductsByCategory(String category);
	
}
