package com.example.demo.login.daoImpl;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.BidDao;
import com.example.demo.login.dao.ProductDao;
import com.example.demo.login.dao.UserDao;
import com.example.demo.login.domain.model.Bid;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.User;

@Repository
public class DaoImpl implements UserDao<User>,ProductDao<Product>, BidDao<Bid> {
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	EntityManager em;
	
//===============================================================================================================
//                     						BidDao
//===============================================================================================================
	@Override
	public long countByProductId(int productId)throws DataAccessException {
		return (long)em
				.createNamedQuery("countByProductId")
				.setParameter("id", productId)
				.getSingleResult();
	}
	@Override
	public long countBid()throws DataAccessException{
		return (long)em
				.createNamedQuery("countBid")
				.getSingleResult();
	}
//===============================================================================================================
//												ProductDao
//===============================================================================================================
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getCommingSoon(LocalDateTime now)throws DataAccessException {
		return (List<Product>)em
				.createNamedQuery("getCommingSoon")
				.setParameter("now", now)
				.setMaxResults(3)
				.getResultList();
	}
	@Override
	public Product findByProductId(Integer productId)throws DataAccessException {
		return (Product) em
				.createNamedQuery("findByProductId")
				.setParameter("id", productId)
				.getSingleResult();
	}

}
