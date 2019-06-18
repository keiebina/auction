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
import com.example.demo.login.dao.WatchListDao;
import com.example.demo.login.domain.model.Bid;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.WatchList;

@Repository
public class DaoImpl implements UserDao<User>,ProductDao<Product>, BidDao<Bid>,WatchListDao<WatchList> {
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	EntityManager em;
	

//===============================================================================================================
//												ProductDao
//===============================================================================================================
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getCommingSoon(LocalDateTime now)throws DataAccessException {
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
		Product product =  (Product) em
								.createNamedQuery("findByProductId")
								.setParameter("id", productId)
								.getSingleResult();
		em.close();
		return product;
	}
	@Override
	public List<Product> findByUserId(String userId) {
		return null;
		
	}
	
//===============================================================================================================
//												BidDao
//===============================================================================================================
	@Override
	public long countByProductId(int productId)throws DataAccessException {
		long count =  (long)em
						.createNamedQuery("countByProductId")
						.setParameter("id", productId)
						.getSingleResult();
		em.close();
		return count;
	}

//===============================================================================================================
//												watchListDao
//===============================================================================================================
	@Override
	public boolean checkWatchList(String userId, Integer productId) {
		boolean checkFlag = false;
		long count = (long)em
						.createNamedQuery("checkWatchList")
						.setParameter("userId", userId)
						.setParameter("productId", productId)
						.getSingleResult();
		em.close();
		System.out.println(count);
		if (count > 0) {
			checkFlag = true;
		}
		return checkFlag;
	}
	@Override
	public Integer getWatchListIdByUserIdAndProductId(String userId, Integer productId) {
		Integer watchListId = (Integer)em
						.createNamedQuery("getWatchListIdByUserIdAndProductId")
						.setParameter("userId", userId)
						.setParameter("productId", productId)
						.getSingleResult();
		em.close();
		return watchListId;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<WatchList> getWatchListByUserId(String userId) {
		List<WatchList> watchList = (List<WatchList>)em
				.createNamedQuery("getWatchListByUserId")
				.setParameter("userId", userId)
				.getResultList();
		em.close();
		return watchList;
	}
	
	
	
}
