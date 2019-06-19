package com.example.demo.login.daoImpl;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.ProductDao;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.service.DataAccessService;

@Repository
public class ProductDaoImpl implements ProductDao<Product>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	DataAccessService dataAccessService;
	
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getCommingSoon(LocalDateTime now)throws DataAccessException {
		em = dataAccessService.setEntitymanager(em);
		List<Product> commingSoonProducts = (List<Product>)em
																.createNamedQuery("getCommingSoon")
																.setParameter("now", now)
																.setMaxResults(3)
																.getResultList();
		em.close();
		return commingSoonProducts;
	}
	@Override
	public Product findByProductId(Integer productId)throws DataAccessException {
		em = dataAccessService.setEntitymanager(em);
		Product product =  (Product) em
								.createNamedQuery("findByProductId")
								.setParameter("id", productId)
								.getSingleResult();
		em.close();
		return product;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductByStatusFlag()throws DataAccessException {
		em = dataAccessService.setEntitymanager(em);
		List<Product> list = (List<Product>)em
					.createNamedQuery("getProductByStatusFlag")
					.getResultList();
		em.close();
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductsByCategory(String category)throws DataAccessException {
		em = dataAccessService.setEntitymanager(em);
		List<Product> list = (List<Product>)em
					.createNamedQuery("getProductsByCategory")
					.setParameter("category", category)
					.getResultList();
		em.close();
		return list;
	}
}
