package com.example.demo.login.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.WatchListDao;
import com.example.demo.login.domain.model.WatchList;
import com.example.demo.login.domain.service.DataAccessService;

@Repository
public class WatchListDaoImpl implements WatchListDao<WatchList> {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	DataAccessService dataAccessService;
	
	private EntityManager em;
	
	@Override
	public boolean checkWatchList(String userId, Integer productId) {
		em = dataAccessService.setEntitymanager(em);
		boolean checkFlag = false;
		long count = (long)em
						.createNamedQuery("checkWatchList")
						.setParameter("userId", userId)
						.setParameter("productId", productId)
						.getSingleResult();
		em.close();
		if (count > 0) {
			checkFlag = true;
		}
		return checkFlag;
	}
	@Override
	public Integer getWatchListIdByUserIdAndProductId(String userId, Integer productId) {
		em = dataAccessService.setEntitymanager(em);
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
	public List<WatchList> getWatchListByUserId(String userId, int page) {
		em = dataAccessService.setEntitymanager(em);
		List<WatchList> watchList = (List<WatchList>)em
				.createNamedQuery("getWatchListByUserId")
				.setParameter("userId", userId)
				.setFirstResult(6 * (page - 1))
				.setMaxResults(6)
				.getResultList();
		em.close();
		return watchList;
	}
	@Override
	public long countAllWatchListByUserId(String userId) {
		em = dataAccessService.setEntitymanager(em);
		long count = (long)em
					.createNamedQuery("countAllWatchListByUserId")
					.setParameter("userId", userId)
					.getSingleResult();
		em.close();
		return count;
	}

}
