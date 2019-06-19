package com.example.demo.login.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.repository.jdbc.SuccessfulBidRepository;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.ProductService;

@Controller
public class SuccessfulBidController {
	
	@Autowired
	SuccessfulBidRepository sbRepository;
	
	@Autowired
	ProductService pService;
	
	@Autowired
	DataAccessService daService;

	@RequestMapping(value = "/successfulBid", method = RequestMethod.GET)
	public ModelAndView getSuccessfulBid(ModelAndView mav, Principal principal) {
		String userId = principal.getName();
		List<Product> productList = daService.getProductsByUserId(userId);
		boolean successfulBidFlag = false;
		if (productList.size() > 0) {
			successfulBidFlag = true;
		}
		mav.addObject("successfulBidFlag", successfulBidFlag);
		mav.addObject("productList", productList);										//ログインユーザーが落札した商品一覧を格納
		mav.addObject("categoryItems", pService.getCategoryItems());    	  	//サイドバー表示アイテム
		mav.addObject("userId", userId);
		mav.addObject("in", true);
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/successfulBid :: successfulBid_contents");
		return mav;
	}
}
