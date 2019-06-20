package com.example.demo.login.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.SearchResult;
import com.example.demo.login.domain.repository.jdbc.SearchResultRepository;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.ProductService;

@Controller
public class SearchController {
	
	@Autowired
	DataAccessService daService;
	
	@Autowired
	ProductService pService;
	
	@Autowired
	SearchResultRepository srRepository;

	@Transactional(readOnly = false)
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView getSearch(@RequestParam("search") String searchWord, ModelAndView mav, Principal principal) {
		String userId = principal.getName();													//ナビゲーションバー表示用
		srRepository.deleteAll();																	//テーブル全削除
		String[] word = searchWord.split("[ ,　]");											//半角・全角スペースで文字列をわける
		if (searchWord.equals("")) {
			searchWord = "全商品";
		}
		mav.addObject("searchWord", searchWord);
		//検索ワードで商品をあいまい検索して、データベースに保存
		for (int i = 0; i < word.length; i++) {
			List<Product> list = new ArrayList<>();
			searchWord = "%" + word[i] + "%";
			list = daService.findProductsBySearchWord(searchWord);
			System.out.println(list);
			for (int j = 0; j < list.size(); j++) {
				SearchResult searchResult = new SearchResult();
				searchResult.setProduct(list.get(j));
				srRepository.saveAndFlush(searchResult);
			}
		}
		List<Product> products = daService.getAllSearchResult();
		boolean searchResultFlag = false;
		if (products.size() > 0) {
			searchResultFlag = true;
		}
		mav.addObject("searchResultFlag", searchResultFlag);
		mav.addObject("userId", userId);
		mav.addObject("in", true);
		mav.addObject("products", products);
		mav.addObject("categoryItems", pService.getCategoryItems());			//サイドバー表示アイテム
		mav.setViewName("layout/layout");
		mav.addObject("contents", "product/search :: search_contents");
		return mav;
	}
}
