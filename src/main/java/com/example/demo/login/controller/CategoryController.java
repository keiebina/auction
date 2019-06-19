package com.example.demo.login.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.service.ProductService;

@Controller
public class CategoryController {
	
	@Autowired
	ProductService pService;

	@RequestMapping(value = "/category", method =  RequestMethod.GET)
	public ModelAndView getCategory(@RequestParam("id") Integer categoryId, ModelAndView mav) {
		Map<Integer, String> map = pService.getCategoryItems();
		String category = map.get(categoryId);
		System.out.println(category);
		return mav;
	}
	
}
