package com.example.demo.login.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.SearchResultDao;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.SearchResult;
import com.example.demo.login.domain.service.DataAccessService;

@Repository
public class SearchResultDaoImpl implements SearchResultDao<SearchResult> {
	private static final long serialVersionUID = 1L;

	@Autowired
	DataAccessService daService;
	
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllSearchResult() {
		em = daService.setEntitymanager(em);
		List<Product> list = (List<Product>)em
					.createNamedQuery("getAllSearchResult")
					.getResultList();
		em.close();
		return list;
	}
	
	
}
