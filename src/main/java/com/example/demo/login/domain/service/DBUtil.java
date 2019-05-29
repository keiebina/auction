package com.example.demo.login.domain.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

@PersistenceContext
@Service
public class DBUtil {
	private static final String PERSISTENCE_UNIT_NAME = "auction";
    private static EntityManagerFactory emf;
    public static EntityManager createEntityManager() {
        return _getEntityManagerFactory().createEntityManager();
    }
    private static EntityManagerFactory _getEntityManagerFactory() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }
    
}
