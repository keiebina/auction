package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.repository.jdbc.ProductRepository;
import com.example.demo.login.domain.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	ProductService service;
	
	@Autowired
	ProductRepository repository;
	
	@RequestMapping(value = "/newProduct", method = RequestMethod.GET)
	public ModelAndView getProductNew(@ModelAttribute  Product product, ModelAndView mav) {
		mav.setViewName("form/productForm");
		mav.addObject("stateItems", service.getStateItems());              //セレクトボックスのリストを格納
		mav.addObject("categoryItems", service.getCategoryItems());    //セレクトボックスのリストを格納
		return mav;
	}
	
	@RequestMapping(value = "/productCreate", method = RequestMethod.POST)
	public ModelAndView postProductCreate(@ModelAttribute @Validated Product product,BindingResult bindingResult, ModelAndView mav) {
		if (bindingResult.hasErrors()) {
			//エラーがある場合の処理
			return getProductNew(product, mav);
		}else {
			//商品情報の保存実行
			System.out.println(product.getEndTime());
			return new ModelAndView("redirect:/");
		}
	}

}
