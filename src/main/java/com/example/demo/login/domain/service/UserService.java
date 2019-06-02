package com.example.demo.login.domain.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.repository.jdbc.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
//パスワードの一致チェック=============================================================================================
	public boolean passwordCheck(String userId, String password ) {
		boolean result = false;
		String userPassword = repository.findByUserId(userId).getPassword();
		if (passwordEncoder.matches(password, userPassword)) {
			System.out.println("一致したよ");
			result = true;
		}else {
			System.out.println("一致しなかったよ");
			result = false;
		}
		return result;
	}
}
