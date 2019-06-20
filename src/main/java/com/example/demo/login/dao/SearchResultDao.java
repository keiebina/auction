package com.example.demo.login.dao;

import java.io.Serializable;
import java.util.List;

import com.example.demo.login.domain.model.Product;

public interface SearchResultDao<T> extends Serializable{

	public List<Product> getAllSearchResult();
}
