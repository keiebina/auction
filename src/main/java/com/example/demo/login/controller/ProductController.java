package com.example.demo.login.controller;

import java.io.File;
import java.nio.file.Files;
import java.security.Principal;
import java.time.LocalDateTime;

import javax.xml.bind.DatatypeConverter;

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

import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.jdbc.BidRepository;
import com.example.demo.login.domain.repository.jdbc.ProductRepository;
import com.example.demo.login.domain.repository.jdbc.UserRepository;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	ProductService pService;
	
	@Autowired
	DataAccessService daService;
	
	@Autowired
	ProductRepository pRepository;
	
	@Autowired
	UserRepository uRepository;
	
	@Autowired
	BidRepository bRepository;
	
	
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
				product.getImageResource().transferTo(convFile);
				byte[] data = Files.readAllBytes(convFile.toPath());
				String base64str = DatatypeConverter.printBase64Binary(data);
				StringBuilder sb = new StringBuilder();
				sb.append("data:");
				sb.append("image");
				sb.append("/jpeg");
				sb.append(";base64,");
				sb.append(base64str);
	            product.setImage(sb.toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
			//オークション開始日時の登録
			LocalDateTime startTime = LocalDateTime.now();
			product.setStartTime(startTime);
			product.setCurrentPrice(product.getStartPrice()); //現在価格に開始価格を格納
			pRepository.saveAndFlush(product);
			return new ModelAndView("redirect:/");
		}
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/productShow", method = RequestMethod.GET)
	public ModelAndView getProductShow(@RequestParam("id") int productId,ModelAndView mav, Principal principal) {
		String userId = principal.getName();
		User loginUser = uRepository.findByUserId(userId);
		//商品IDをデータベースから検索
		Product product = daService.findByProductId(productId);
		//商品IDから入札数をカウント
		long count = daService.countByProductId(productId);
		//ウォッチリストテーブルを検索
		boolean watchListFlag = daService.checkWatchList(loginUser.getUserId(), productId);      //既にウォッチリストに入っていた場合はtrue
		mav.addObject("watchListFlag", watchListFlag);		//ウォッチリストに登録しているか判断
		mav.addObject("count", count); 							//入札数
		mav.addObject("product", product);						//標品情報
		mav.addObject("loginUser", loginUser);                  //ログインユーザー情報
		mav.addObject("userId", userId);                           //ログインユーザーID
		mav.addObject("in", true);                                   //ログイン状態
		mav.setViewName("layout/offSidebar");
		mav.addObject("contents", "product/show :: show_contents");	
		return mav;
	}
}
