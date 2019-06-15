package com.example.demo.login.controller;

import java.io.File;
import java.nio.file.Files;
import java.security.Principal;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.jdbc.ProductRepository;
import com.example.demo.login.domain.repository.jdbc.UserRepository;
import com.example.demo.login.domain.service.PictureDecodeService;
import com.example.demo.login.domain.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	ProductService pService;
	
	@Autowired
	ProductRepository pRepository;
	
	@Autowired
	UserRepository uRepository;
	
	@Autowired
	PictureDecodeService pdService;
	
	@RequestMapping(value = "/productNew", method = RequestMethod.GET)
	public ModelAndView getProductNew(@ModelAttribute  Product product, ModelAndView mav) {
		mav.setViewName("form/productForm");
		mav.addObject("stateItems", pService.getStateItems());              //セレクトボックスのリストを格納
		mav.addObject("categoryItems", pService.getCategoryItems());    //セレクトボックスのリストを格納
		return mav;
	}
	
	@RequestMapping(value = "/productCreate", method = RequestMethod.POST)
	public ModelAndView postProductCreate(@ModelAttribute @Validated Product product,BindingResult bindingResult,  ModelAndView mav) throws Exception {
		if (bindingResult.hasErrors()) {
			//エラーがある場合の処理
			return getProductNew(product, mav);
		}else {
			//商品情報の保存実行
			//ファイルインスタンスを取得し、ImageIOへ読み込ませる
			try {
				 File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+product.getImageResource().getName());
	                byte[] data = Files.readAllBytes(convFile.toPath());
	                String base64str = DatatypeConverter.printBase64Binary(data);
	                StringBuilder sb = new StringBuilder();
	                sb.append("data:");
	                sb.append("image");
	                sb.append("/png");
	                sb.append(";base64,");
	                sb.append(base64str);
	                System.out.println(sb.toString());
	                product.setImage(sb.toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
			pRepository.saveAndFlush(product);
			return new ModelAndView("redirect:/");
		}
	}
	
	@RequestMapping(value = "/productShow", method = RequestMethod.GET)
	public ModelAndView getProductShow(ModelAndView mav, Principal principal) {
		String userId = principal.getName();
		User loginUser = uRepository.findByUserId(userId);
		
		//商品IDをデータベースから検索してそれをパラメータとして送る
		
		mav.addObject("loginUser", loginUser);                  //ログインユーザー情報
		mav.addObject("userId", userId);                           //ログインユーザーID
		mav.addObject("in", true);                                   //ログイン状態
		mav.setViewName("layout/offSidebar");
		mav.addObject("contents", "product/show :: show_contents");	
		return mav;
	}
}
