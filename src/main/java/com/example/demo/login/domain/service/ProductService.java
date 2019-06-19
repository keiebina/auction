package com.example.demo.login.domain.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.SuccessfulBid;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.jdbc.ProductRepository;
import com.example.demo.login.domain.repository.jdbc.SuccessfulBidRepository;

@Service
public class ProductService {
	
	@Autowired
	DataAccessService daService;
	
	@Autowired
	ProductRepository pRepository;
	
	@Autowired
	SuccessfulBidRepository sbRepository;
	
//	===================================================================================================
//										フォームのプルダウンリストの内容を設定
//	===================================================================================================

	//state用
	public List<String> getStateItems(){
		List<String> stateItems = new ArrayList<String>();
		stateItems.add("とても良い");
		stateItems.add("良い");
		stateItems.add("普通");
		stateItems.add("悪い");
		stateItems.add("とても悪い");
		return stateItems;
	}
		
	//category用
		public List<String> getCategoryItems(){
			List<String> categoryItems = new ArrayList<String>();
			categoryItems.add("コンピュータ");
			categoryItems.add("家電、AV｜カメラ");
			categoryItems.add("音楽｜CD");
			categoryItems.add("本、雑誌｜漫画");
			categoryItems.add("映画、ビデオ｜DVD");
			categoryItems.add("おもちゃ｜ゲーム");
			categoryItems.add("ホビー｜カルチャー");
			categoryItems.add("アンティーク、コレクション");
			categoryItems.add("スポーツ、レジャー");
			categoryItems.add("ファッション｜ブランド別");
			categoryItems.add("アクセサリー｜時計");
			categoryItems.add("住まい、インテリア");
			categoryItems.add("事務、店舗用品");
			return categoryItems;
	}
		
//====================================================================================================
//									オークション残り時間
//====================================================================================================
	
	public String timeCalculation(LocalDateTime endTime) {
		LocalDateTime now = LocalDateTime.now();
		long secondsToFinish = ChronoUnit.SECONDS.between(now, endTime);
		StringBuilder sb = new StringBuilder();
		//残り1日以上の判断
		if (secondsToFinish > 86400) {
			long day = secondsToFinish/86400;
			secondsToFinish = secondsToFinish%86400;
			long hour = secondsToFinish/3600;
			secondsToFinish = secondsToFinish%3600;
			long minutes = secondsToFinish /60;
			long seconds = secondsToFinish %60;
			sb.append(day);
			sb.append("日と");
			sb.append(hour);
			sb.append("時間");
			sb.append(minutes);
			sb.append("分");
			sb.append(seconds);
			sb.append("秒");
			String timeToFinish = sb.toString();
			return timeToFinish;
		
		//残り1時間以上の判断
		}else if (secondsToFinish > 3600) {
			long hour = secondsToFinish/3600;
			secondsToFinish = secondsToFinish%3600;
			long minutes = secondsToFinish /60;
			long seconds = secondsToFinish %60;
			sb.append(hour);
			sb.append("時間");
			sb.append(minutes);
			sb.append("分");
			sb.append(seconds);
			sb.append("秒");
			String timeToFinish = sb.toString();
			return timeToFinish;
			
		//残り1分以上の判断
		}else if(secondsToFinish > 60){
			long minutes = secondsToFinish/60;
			long seconds = secondsToFinish%60;
			sb.append(minutes);
			sb.append("分");
			sb.append(seconds);
			sb.append("秒");
			String timeToFinish = sb.toString();
			return timeToFinish;
		}else {
			sb.append(secondsToFinish);
			sb.append("秒");
			String timeToFinish = sb.toString();
			return timeToFinish;
		}
	}
	
//========================================================================================================
//											オークション終了判断、商品登録時バリデーション
//========================================================================================================
	
	public boolean checkNowIsAfterEndTime(LocalDateTime endTime) {
		LocalDateTime now = LocalDateTime.now();
		boolean isAfter = false;
		isAfter = now.isAfter(endTime);      		//終了時間を過ぎていたらtrueを返す
		return isAfter;
	}

//========================================================================================================
//											終了時間の過ぎた商品のstatusFlag変更（全商品のチェック）
//========================================================================================================

	public void changeStatusFlag(){
		List<Product> list = daService.getProductByStatusFlag();
		Product product = new Product();
		for (int i = 0; i < list.size(); i++) {
			product = list.get(i);
			boolean result = checkNowIsAfterEndTime(product.getEndTime());
			if (result) {
				product.setStatusFlag(0);
				pRepository.saveAndFlush(product);
				User successfulBidUser = daService.getByProductIdOrderByBidPrice(product.getProductId());
				if (successfulBidUser != null) {
					SuccessfulBid successfulBid = new SuccessfulBid();
					successfulBid.setUser(successfulBidUser);
					successfulBid.setProduct(product);
					sbRepository.saveAndFlush(successfulBid);
				}
			}
		}
	}
}
