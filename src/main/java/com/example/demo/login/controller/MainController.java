package com.example.demo.login.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.ProductService;


@Controller
public class MainController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	DataAccessService daService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLogin(ModelAndView mav) {
		mav.setViewName("login/login");
		return mav;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndex(ModelAndView mav, Principal principal) {
		String userId = null;
		mav.addObject("in", false);
		try {
			userId = principal.getName();
		}catch (Exception e) { }
		if (userId != null) {
			mav.addObject("userId", userId);
			mav.addObject("in", true);
		}
		//終了間近商品の取得
		boolean commingSoonFlag = false;
		try {
			LocalDateTime now = LocalDateTime.now();
			List<Product> products = daService.getCommingSoon(now);
			commingSoonFlag = true;
			mav.addObject("products", products);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mav.addObject("commingSoonFlag", commingSoonFlag);								//終了間近商品
		mav.addObject("categoryItems", productService.getCategoryItems());			//サイドバー表示アイテム
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/index :: index_contents");
		return mav;
	}
}
