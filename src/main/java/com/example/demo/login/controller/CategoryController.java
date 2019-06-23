package com.example.demo.login.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.PaginationService;
import com.example.demo.login.domain.service.ProductService;

@Controller
public class CategoryController {
	
	@Autowired
	ProductService pService;
	
	@Autowired
	DataAccessService daService;
	
	@Autowired
	PaginationService paginationService;

	@RequestMapping(value = "/category", method =  RequestMethod.GET)
	public ModelAndView getCategory(@RequestParam("id") Integer categoryId, @RequestParam(name = "page", defaultValue = "1") int page, ModelAndView mav) {
		Map<Integer, String> map = pService.getCategoryItems();
		String category = map.get(categoryId);
		List<Product> products = daService.getProductsByCategory(category, page);
		boolean productsSizeFlag = false;
		if (products.size() > 0) {
			productsSizeFlag = true;
		}
		long count = daService.countProductsByCategory(category);
		List<Integer> pages = paginationService.pagination(count);
		mav.addObject("id", categoryId);
		mav.addObject("count", count);
		mav.addObject("pages", pages);
		mav.addObject("category", category);
		mav.addObject("products", products);										//選択したカテゴリーの商品リスト
		mav.addObject("productsSizeFlag", productsSizeFlag); 
		mav.addObject("categoryItems", map);     									 //サイドバー表示アイテム
		mav.setViewName("layout/layout");
		mav.addObject("contents", "product/category :: category_contents");
		return mav;
	}
	
}
