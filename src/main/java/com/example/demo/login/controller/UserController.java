package com.example.demo.login.controller;

import java.security.Principal;
import java.time.LocalDateTime;
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

import com.example.demo.login.daoImpl.DaoImpl;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.jdbc.ProductRepository;
import com.example.demo.login.domain.repository.jdbc.UserRepository;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.ProductService;
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
	UserRepository uRepository;
	
	@Autowired
	ProductRepository pRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	DataAccessService dataService;
	
	@Autowired
	DaoImpl dao;
	
	
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
		//終了間近商品の取得
		boolean commingSoonSize = false;
		try {
			LocalDateTime now = LocalDateTime.now();
			List<Product> products = dao.getCommingSoon(now);
			commingSoonSize = true;
			mav.addObject("products", products);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mav.addObject("commingSoonSize", commingSoonSize);
		mav.addObject("categoryItems", productService.getCategoryItems());
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
	public ModelAndView postUserCreate(@ModelAttribute @Validated User user,BindingResult bindingResult, ModelAndView mav) {
		if (bindingResult.hasErrors()) {
			//エラーがある場合の処理
			return getUserNew(user, mav);
		}else {
			//ユーザー情報の保存実行
			user.setRole("ROLE_GENERAL");           //管理者は手動で生成すること
			//パスワードの暗号化
			String password = passwordEncoder.encode(user.getPassword());
			user.setPassword(password);
			uRepository.saveAndFlush(user);
			System.out.println("ユーザー情報の登録が完了 user:" + user);
			return new ModelAndView("redirect:/login");
		}
	}
	
	@RequestMapping(value = "/userShow", method = RequestMethod.GET)
	public ModelAndView getUserShow(ModelAndView mav, Principal principal) {
		//ログインユーザーの情報を取得、格納
		String userId = principal.getName();
		User loginUser = uRepository.findByUserId(userId);
		mav.addObject("loginUser", loginUser);
		mav.addObject("userId", userId);
		mav.addObject("in", true);
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/show :: show_contents");
		return mav;
	}
	
	@RequestMapping(value = "/userEdit", method = RequestMethod.GET)
	public ModelAndView getUserEdit(@ModelAttribute User user, ModelAndView mav, Principal principal) {
		String userId = principal.getName();
		User loginUser = uRepository.findByUserId(userId);
		mav.addObject("loginUser", loginUser);
		mav.addObject("userId", userId);
		mav.addObject("in", true);
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/edit :: edit_contents");
		return mav;
	}
	
	@RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView postUserUpdate(@ModelAttribute @Validated User user, BindingResult bindingResult,Principal principal, ModelAndView mav) {
		if (bindingResult.hasErrors()) {
			//エラーがある場合の処理
			System.out.println("エラーがあります");
			return getUserEdit(user, mav, principal);
		}
		String userId = principal.getName();
		String password = user.getPassword();
		if (userService.passwordCheck(userId, password) == false) {      //パスワードの一致チェック
			System.out.println("パスワードが一致しません");
			return getUserEdit(user, mav, principal);
		}
		password = passwordEncoder.encode(password);
		user.setPassword(password);
		uRepository.saveAndFlush(user);
		return new ModelAndView("redirect:/userShow");
	}
	
	@RequestMapping(value = "/userDestroy", method = RequestMethod.POST)
	public ModelAndView postUserDestroy(ModelAndView mav) {
		mav.setViewName("layout/layout");
		return mav;
	}

}
