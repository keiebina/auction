package com.example.demo.login.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.example.demo.login.domain.service.BidService;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class BidController {
	
	@Autowired
	DataAccessService daService;
	
	@Autowired
	UserService uService;
	
	@Autowired
	BidService bService;
	
	@Autowired
	BidRepository bRepository;

	@RequestMapping(value = "/bidNew", method = RequestMethod.GET)
	public ModelAndView getBidNew(@ModelAttribute Bid bid, ModelAndView mav, @RequestParam("id") Integer id) {
		System.out.println(mav);
		Product product = daService.findByProductId(id);
		Integer currentPrice = product.getCurrentPrice();
		mav.addObject("productId", id);
		mav.addObject("currentPrice", currentPrice);
		mav.setViewName("product/bid");
		return mav;
	}
	
	@RequestMapping(value = "/bidCreate", method = RequestMethod.POST)
	public ModelAndView postBidCreate(@ModelAttribute @Validated Bid bid, BindingResult bindingResult, ModelAndView mav,@RequestParam("id") Integer id, Principal principal) {
		if (bindingResult.hasErrors()) {
			return getBidNew(bid, mav, id);
		}
		User loginUser = uService.getLoginUser(principal);									//ログインユーザー情報の取得
		Product bidProduct = daService.findByProductId(id);                               //入札する商品情報の取得
		Integer currentPrice = bid.getCurrentPrice();
		Integer bidPrice = bid.getBidPrice();
		boolean validation = bService.bidPriceValidator(currentPrice, bidPrice);
		if (validation) {
			bid.setUser(loginUser);
			bid.setProduct(bidProduct);
			//入札数の追加
			//currentPriceの変更
			
			bRepository.saveAndFlush(bid);
			mav.addObject("message", null);
		}else {
			mav.addObject("message", "現在価格より大きな価格を入力してください");
			return getBidNew(bid, mav, id);
		}
		return new ModelAndView("redirect:/");
	}
}
