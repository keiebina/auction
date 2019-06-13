package com.example.demo.login.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	//フォームのプルダウンリストの内容を設定======================================================================
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
	}//=================================================================================================


}
