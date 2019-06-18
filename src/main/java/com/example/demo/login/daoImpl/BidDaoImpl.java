package com.example.demo.login.daoImpl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.BidDao;
import com.example.demo.login.domain.model.Bid;
import com.example.demo.login.domain.service.DataAccessService;

@Repository
public class BidDaoImpl implements BidDao<Bid> {
	private static final long serialVersionUID = 1L;
	
	private EntityManager em;
	
	@Autowired
	DataAccessService dataAccessService;
	
	@Override
	public long countByProductId(int productId)throws DataAccessException {
		em = dataAccessService.setEntitymanager(em);
		long count =  (long)em
						.createNamedQuery("countByProductId")
						.setParameter("id", productId)
						.getSingleResult();
		em.close();
		return count;
	}

}
