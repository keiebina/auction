package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.service.ProductService;

@Controller
public class SearchController {
	
	@Autowired
	ProductService pService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView getSearch(@RequestParam("search") String searchWord, ModelAndView mav) {
		String[] word = searchWord.split("[ ,　]");
		System.out.println("検索条件1つ目" + word[0] + "検索条件2つ目" +word[1]);
		
		mav.addObject("searchWord", searchWord);
		mav.addObject("categoryItems", pService.getCategoryItems());			//サイドバー表示アイテム
		mav.setViewName("layout/layout");
		mav.addObject("contents", "product/search :: search_contents");
		return mav;
	}
}
