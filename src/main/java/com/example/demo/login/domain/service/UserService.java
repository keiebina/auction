package com.example.demo.login.domain.service;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.jdbc.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
//ログインユーザー情報の取得==========================================================================================
	public User getLoginUser(Principal principal) {
		String userId = principal.getName();
		User loginUser = repository.findByUserId(userId);
		return loginUser;
	}
	
//パスワードの一致チェック=============================================================================================
	public boolean passwordCheck(String userId, String password ) {
		boolean result = false;
		String userPassword = repository.findByUserId(userId).getPassword();
		if (passwordEncoder.matches(password, userPassword)) {
			System.out.println("一致");
			result = true;
		}else {
			System.out.println("不一致");
			result = false;
		}
		return result;
	}
}
