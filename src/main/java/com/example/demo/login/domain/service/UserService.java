package com.example.demo.login.domain.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;

@Service
public class UserService {

	@Autowired
	DBUtil dbUtil;
	
	EntityManager em = DBUtil.createEntityManager();
	
	//ユーザー情報登録
	public boolean insert(User user) {
		User u = new User();
		u.setUserId(user.getUserId());
		u.setPassword(user.getPassword());
		u.setNickname(user.getNickname());
		u.setUserName(user.getUserName());
		u.setBirthday(user.getBirthday());
		u.setGender(user.isGender());
		u.setPostalCode(user.getPostalCode());
		u.setAddress(user.getAddress());
		//user情報登録
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		em.close();
		return true;
	}
	
}
