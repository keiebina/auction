package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.User;

@Controller
public class controller {
	
	//ラジオボタンの実装
	private Map<String, String> radioGender;
	//ラジオボタンの初期化メソッド
	private Map<String, String> initRadioGender(){
		Map<String, String> radio = new LinkedHashMap<>();
		//男性、女性をMapに格納
		radio.put("男性", "true");
		radio.put("女性", "false");
		return radio;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getTop(ModelAndView mav) {
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/index :: index_contents");
		return mav;
	}
	
	@RequestMapping(value = "/userNew", method = RequestMethod.GET)
	public ModelAndView getUserNew(@ModelAttribute User user, ModelAndView mav) {
		//ラジオボタンの初期化メソッドの呼び出し、画面表示用に格納
		radioGender = initRadioGender();
		mav.addObject("radioGender", radioGender);
		mav.setViewName("form/userForm");
		return mav;
	}
	
	@RequestMapping(value = "/userCreate", method = RequestMethod.POST)
	public ModelAndView postUserCreate(@ModelAttribute @Validated(GroupOrder.class) User user,BindingResult bindingResult, ModelAndView mav) {
		if (bindingResult.hasErrors()) {
			System.out.println("エラーがあります");
			return getUserNew(user, mav);
		}
		System.out.println(user);
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/index :: index_contents");
		return getTop(mav);
	}
	
	@RequestMapping(value = "/userShow", method = RequestMethod.GET)
	public ModelAndView getUserShow(ModelAndView mav) {
		mav.setViewName("layout/topLayout");
		return mav;
	}
	
	@RequestMapping(value = "/userEdit", method = RequestMethod.GET)
	public ModelAndView getUserEdit(ModelAndView mav) {
		mav.setViewName("layout/topLayout");
		return mav;
	}
	
	@RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
	public ModelAndView postUserUpdate(ModelAndView mav) {
		mav.setViewName("layout/topLayout");
		return mav;
	}
	
	@RequestMapping(value = "/userDestroy", method = RequestMethod.POST)
	public ModelAndView postUserDestroy(ModelAndView mav) {
		mav.setViewName("layout/topLayout");
		return mav;
	}

}
