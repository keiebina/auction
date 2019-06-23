package com.example.demo.login.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.BidRanking;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.repository.jdbc.BidRankingRepository;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.ProductService;


@Controller
public class MainController {
	
	@Autowired
	ProductService pService;
	
	@Autowired
	DataAccessService daService;
	
	@Autowired
	BidRankingRepository brRepository;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLogin(ModelAndView mav) {
		mav.setViewName("login/login");
		return mav;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndex( ModelAndView mav) {
		pService.changeStatusFlag(); 		//終了時間が過ぎた商品があった場合、落札情報の更新
		//終了間近商品の取得
		boolean commingSoonFlag = false;
		try {
			LocalDateTime now = LocalDateTime.now();
			List<Product> products = daService.getCommingSoon(now);
			if (products.size() > 0) {
				commingSoonFlag = true;
			}
			mav.addObject("products", products);
		} catch (Exception e) {
			// TODO: handle exception
		}
		//入札数ランキング
		List<Product> list = daService.getProductByStatusFlag();	//出品中の商品の全取得
		Product product = new Product();
		brRepository.deleteAll();
		for (int i = 0; i < list.size(); i++) {
			BidRanking bidRanking = new BidRanking();
			product = list.get(i);
			long bidCount = daService.countByProductId(product.getProductId());
			bidRanking.setProduct(product);
			bidRanking.setBidCount(bidCount);
			brRepository.saveAndFlush(bidRanking);
		}
		List<BidRanking> bidRankingList = daService.findAllOrderByBidCount();	//入札数ランキングTOP３の取得
		boolean bidRankingListFlag = false;
		if (bidRankingList.size() > 0) {
			bidRankingListFlag = true;
		}
		mav.addObject("bidRankingListFlag", bidRankingListFlag);						//入札されている商品がない場合はfalse;
		mav.addObject("bidRankingList", bidRankingList);
		mav.addObject("commingSoonFlag", commingSoonFlag);						//終了間近商品
		mav.addObject("categoryItems", pService.getCategoryItems());			//サイドバー表示アイテム
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/index :: index_contents");
		return mav;
	}
}
