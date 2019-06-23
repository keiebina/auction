package com.example.demo.login.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.repository.jdbc.SuccessfulBidRepository;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.PaginationService;
import com.example.demo.login.domain.service.ProductService;

@Controller
public class SuccessfulBidController {
	
	@Autowired
	SuccessfulBidRepository sbRepository;
	
	@Autowired
	ProductService pService;
	
	@Autowired
	DataAccessService daService;
	
	@Autowired
	PaginationService paginationService;

	@RequestMapping(value = "/successfulBid", method = RequestMethod.GET)
	public ModelAndView getSuccessfulBid(@RequestParam(name = "page", defaultValue = "1") int page, ModelAndView mav, Principal principal) {
		String userId = principal.getName();
		List<Product> productList = daService.getProductsByUserId(userId, page);
		boolean successfulBidFlag = false;
		if (productList.size() > 0) {
			successfulBidFlag = true;
		}
		long count = daService.countProductsByUserId(userId);
		List<Integer> pages = paginationService.pagination(count);
		mav.addObject("pages", pages);															//ページネーション
		mav.addObject("count", count);														//全落札数を格納
		mav.addObject("successfulBidFlag", successfulBidFlag);						//落札した商品があるか判断
		mav.addObject("productList", productList);										//ログインユーザーが落札した商品一覧を格納
		mav.addObject("categoryItems", pService.getCategoryItems());    	  	//サイドバー表示アイテム
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/successfulBid :: successfulBid_contents");
		return mav;
	}
}
