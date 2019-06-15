package com.example.demo.login.daoImpl;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.ProductDao;
import com.example.demo.login.domain.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao<Product>{
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getCommingSoon(LocalDateTime now)throws DataAccessException {
		return (List<Product>)entityManager
				.createNamedQuery("getCommingSoon")
				.setParameter("now", now)
				.setMaxResults(3)
				.getResultList();
	}

	@Override
	public Product findByProductId(Integer productId)throws DataAccessException {
		return (Product)entityManager
				.createNamedQuery("findByProductId")
				.setParameter("productId", productId)
				.getResultList();
	}

}
