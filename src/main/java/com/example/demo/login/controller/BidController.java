package com.example.demo.login.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	BidService bService;
	
	@Autowired
	BidRepository bRepository;

	@RequestMapping(value = "/bidNew", method = RequestMethod.GET)
	public ModelAndView getBidNew(@ModelAttribute Bid bid, ModelAndView mav, @RequestParam("id") Integer id) {
		Product product = daService.findByProductId(id);
		Integer currentPrice = product.getCurrentPrice();
		mav.addObject("currentPrice", currentPrice);
		mav.setViewName("product/bid");
		return mav;
	}
	
	@RequestMapping(value = "/bidCreate", method = RequestMethod.POST)
	public ModelAndView postBidCreate(@ModelAttribute Bid bid,ModelAndView mav,@RequestParam("id") Integer id, Principal principal) {
		String userId = principal.getName();         //ログインユーザーIDの取得
		Integer currentPrice = bid.getCurrentPrice();
		Integer bidPrice = bid.getBidPrice();
		boolean validation = bService.bidPriceValidator(currentPrice, bidPrice);
		if (validation) {
			bid.setUserId(userId);
			
		}
		return mav;
	}
}
