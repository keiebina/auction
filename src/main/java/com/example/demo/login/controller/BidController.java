package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.Bid;

@Controller
public class BidController {

	@RequestMapping(value = "/bidNew", method = RequestMethod.GET)
	public ModelAndView getBid(@ModelAttribute Bid bid, ModelAndView mav) {
		
		mav.setViewName("product/bid");
		return mav;
	}
}
