package com.example.demo.login.daoImpl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.login.dao.UserDao;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.DataAccessService;

@Repository
public class UserDaoImpl implements UserDao<User> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	DataAccessService daService;
	
	private EntityManager em;

	@Override
	public long countByUserId(String userId)throws DataAccessException {
		em = daService.setEntitymanager(em);
		long count = (long)em
					.createNamedQuery("countByUserId")
					.setParameter("userId", userId)
					.getSingleResult();
		em.close();
		return count;
	}

}
