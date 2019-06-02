package com.example.demo.login.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.jdbc.UserRepository;
import com.example.demo.login.domain.service.UserService;


@Controller
public class UserController {

//=====================================================================================================================
	//ラジオボタンの実装
	private Map<String, String> radioGender;
	//ラジオボタンの初期化メソッド
	private Map<String, String> initRadioGender(){
		Map<String, String> radio = new LinkedHashMap<>();
		//男性、女性をMapに格納
		radio.put("男性", "true");
		radio.put("女性", "false");
		return radio;
	}
//=======================================================================================================================
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLogin(ModelAndView mav) {
		mav.setViewName("login/login");
		return mav;
	}
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ModelAndView postLogin(ModelAndView mav, Principal principal) {
//		System.out.println("ログイン処理実行");
//		String userId = principal.getName();
//		System.out.println(userId);
//		return new ModelAndView("redirect:/");
//	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndex(ModelAndView mav, Principal principal) {
		String userId = null;
		mav.addObject("in", false);
		try {
			userId = principal.getName();
			mav.addObject("userId", userId);
			mav.addObject("in", true);
		}catch (Exception e) { }
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/index :: index_contents");
		return mav;
	}
	
	@RequestMapping(value = "/userNew", method = RequestMethod.GET)
	public ModelAndView getUserNew(@ModelAttribute User user, ModelAndView mav) {
		//ラジオボタンの初期化メソッドの呼び出し、画面表示用に格納
		radioGender = initRadioGender();
		mav.addObject("radioGender", radioGender);
		mav.setViewName("form/userForm");
		return mav;
	}
	
	@RequestMapping(value = "/userCreate", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView postUserCreate(@ModelAttribute @Validated(GroupOrder.class)User user,BindingResult bindingResult, ModelAndView mav) {
		if (bindingResult.hasErrors()) {
			//エラーがある場合の処理
			return getUserNew(user, mav);
		}else {
			//ユーザー情報の保存実行
			user.setRole("ROLE_GENERAL");           //管理者は手動で生成すること
			//パスワードの暗号化
			String password = passwordEncoder.encode(user.getPassword());
			user.setPassword(password);
			repository.saveAndFlush(user);
			System.out.println("ユーザー情報の登録が完了 user:" + user);
			return new ModelAndView("redirect:/login");
		}
	}
	
	@RequestMapping(value = "/userShow", method = RequestMethod.GET)
	public ModelAndView getUserShow(ModelAndView mav, Principal principal) {
		//ログインユーザーの情報を取得、格納
		String userId = principal.getName();
		User loginUser = repository.findByUserId(userId);
		mav.addObject("loginUser", loginUser);
		mav.addObject("userId", userId);
		mav.addObject("in", true);
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/show :: show_contents");
		return mav;
	}
	
	@RequestMapping(value = "/userEdit", method = RequestMethod.GET)
	public ModelAndView getUserEdit(ModelAndView mav) {
		mav.setViewName("layout/topLayout");
		return mav;
	}
	
	@RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
	public ModelAndView postUserUpdate(ModelAndView mav) {
		mav.setViewName("layout/topLayout");
		return mav;
	}
	
	@RequestMapping(value = "/userDestroy", method = RequestMethod.POST)
	public ModelAndView postUserDestroy(ModelAndView mav) {
		mav.setViewName("layout/topLayout");
		return mav;
	}

}
