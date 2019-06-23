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
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.WatchList;
import com.example.demo.login.domain.repository.jdbc.WatchListRepository;
import com.example.demo.login.domain.service.DataAccessService;
import com.example.demo.login.domain.service.PaginationService;
import com.example.demo.login.domain.service.ProductService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class WatchListController {
	
	@Autowired
	UserService uService;
	
	@Autowired
	DataAccessService daService;
	
	@Autowired
	ProductService pService;
	
	@Autowired
	WatchListRepository wlRepository;
	
	@Autowired
	PaginationService paginationService;
	
	@RequestMapping(value = "/watchListShow", method = RequestMethod.GET)
	public ModelAndView getWatchListShow(@RequestParam(name = "page", defaultValue = "1") int page, ModelAndView mav, Principal principal) {
		String userId = principal.getName();
		List<WatchList> allWatchTable = daService.getWatchListByUserId(userId,page); 			//ログインユーザーの全ウォッチリストの取得
		//ウォッチリストからproduct情報のみを格納
		List<Product> watchList = new ArrayList<>();			//格納用のリスト作成
		Product product = new Product();							//for内で回すproductの作成
		for (int i = 0; i < allWatchTable.size(); i++) {
			product = allWatchTable.get(i).getProduct();			
			watchList.add(product);
		}
		boolean watchListFlag = false;
		if (watchList.size() > 0) {
			watchListFlag = true;
		}
		long count = daService.countAllWatchListByUserId(userId);
		List<Integer> pages = paginationService.pagination(count);
		mav.addObject("pages", pages);
		mav.addObject("count", count);
		mav.addObject("watchListFlag",watchListFlag);
		mav.addObject("watchLists", watchList);
		mav.addObject("categoryItems", pService.getCategoryItems());      //サイドバー表示アイテム
		mav.setViewName("layout/layout");
		mav.addObject("contents", "user/watchList :: watchList_contents");
		return mav;
	}
	

	@Transactional(readOnly = false)
	@RequestMapping(value = "/watchListUpdate", method = RequestMethod.GET)
	public ModelAndView getWatchListUpdate(@RequestParam("id") Integer productId, ModelAndView mav, Principal principal) {
		User loginUser = uService.getLoginUser(principal);							//ログインユーザー情報の取得
		Product product = daService.findByProductId(productId);					//見ている商品情報の取得
		WatchList watchList = new WatchList();
		//ウォッチリストテーブルを検索
		boolean watchListFlag = daService.checkWatchList(loginUser.getUserId(), productId);      //既にウォッチリストに入っていた場合はtrue
		//ウォッチリストに入っていた場合の処理
		if (watchListFlag) {
			Integer watchListId = daService.getWatchListIdByUserIdAndProductId(loginUser.getUserId(), productId);
			watchList.setUser(loginUser);
			watchList.setProduct(product);
			wlRepository.deleteById(watchListId);
		}else {
			//ウォッチリストに入ってなかった場合の処理
			watchList.setUser(loginUser);
			watchList.setProduct(product);
			wlRepository.saveAndFlush(watchList);
		}
		return new ModelAndView("redirect:/productShow?id="+ productId).addObject("watchListFlag", watchListFlag);
	}
}
