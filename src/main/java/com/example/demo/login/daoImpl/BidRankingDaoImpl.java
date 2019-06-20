package com.example.demo.login.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.BidRankingDao;
import com.example.demo.login.domain.model.BidRanking;
import com.example.demo.login.domain.service.DataAccessService;

@Repository
public class BidRankingDaoImpl implements BidRankingDao<BidRanking>{
	private static final long serialVersionUID = 1L;

	@Autowired 
	DataAccessService daService;
	
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<BidRanking> findAllOrderByBidCount() {
		em = daService.setEntitymanager(em);
		List<BidRanking> list = (List<BidRanking>)em
					.createNamedQuery("findAllOrderByBidCount")
					.setMaxResults(3)
					.getResultList();
		em.close();
		return list;
	}
}
