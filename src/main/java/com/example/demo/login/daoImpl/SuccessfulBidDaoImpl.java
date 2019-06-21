package com.example.demo.login.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.SuccessfulBidDao;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.SuccessfulBid;
import com.example.demo.login.domain.service.DataAccessService;

@Repository
public class SuccessfulBidDaoImpl implements SuccessfulBidDao<SuccessfulBid> {
	private static final long serialVersionUID = 1L;

	@Autowired
	DataAccessService daService;
	
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductsByUserId(String userId, int page) {
		em = daService.setEntitymanager(em);
		List<Product> list = (List<Product>)em
					.createNamedQuery("getProductsByUserId")
					.setParameter("id", userId)
					.setFirstResult(6 * (page - 1))
					.setMaxResults(6)
					.getResultList();
		em.close();
		return list;
	}

	@Override
	public long countProductsByUserId(String userId) {
		em = daService.setEntitymanager(em);
		long count = (long)em
					.createNamedQuery("countProductsByUserId")
					.setParameter("id", userId)
					.getSingleResult();
		em.close();
		return count;
	}
	

}
