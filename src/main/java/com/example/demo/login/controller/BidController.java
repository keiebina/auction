package com.example.demo.login.controller;

import java.security.Principal;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.Bid;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.jdbc.BidRepository;
import com.example.demo.login.domain.repository.jdbc.ProductRepository;
import com.example.demo.login.domain.service.BidService;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class BidController {
	
	EntityManager em;
	
	@Autowired
	DataAccessService daService;
	
	@Autowired
	UserService uService;
	
	@Autowired
	BidService bService;
	
	@Autowired
	BidRepository bRepository;
	
	@Autowired
	ProductRepository pRepository;

	@RequestMapping(value = "/bidNew", method = RequestMethod.GET)
	public ModelAndView getBidNew(@ModelAttribute Bid bid, ModelAndView mav, @RequestParam("id") Integer id) {
		Product product = daService.findByProductId(id);
		Integer currentPrice = product.getCurrentPrice();
		mav.addObject("productId", id);
		mav.addObject("currentPrice", currentPrice);
		mav.setViewName("product/bid");
		return mav;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/bidCreate", method = RequestMethod.POST)
	public ModelAndView postBidCreate(@ModelAttribute @Validated Bid bid, BindingResult bindingResult, ModelAndView mav,@RequestParam("id") Integer productId, Principal principal) {
		if (bindingResult.hasErrors()) {
			return getBidNew(bid, mav, productId);
		}
		User loginUser = uService.getLoginUser(principal);									//ログインユーザー情報の取得
		Product bidProduct = daService.findByProductId(productId);                               //入札する商品情報の取得
		Integer currentPrice = bid.getCurrentPrice();
		Integer bidPrice = bid.getBidPrice();
		boolean validation = bService.bidPriceValidator(currentPrice, bidPrice);
		if (validation) {
			bid.setUser(loginUser);
			bid.setProduct(bidProduct);
			bRepository.saveAndFlush(bid);
			//currentPriceの変更
			bidProduct.setCurrentPrice(bidPrice);
			pRepository.saveAndFlush(bidProduct);
			mav.addObject("message", null);
		}else {
			mav.addObject("message", "現在価格より大きな価格を入力してください");
			return getBidNew(bid, mav, productId);
		}
		return new ModelAndView("redirect:/productShow?id="+ productId);
	}
}
