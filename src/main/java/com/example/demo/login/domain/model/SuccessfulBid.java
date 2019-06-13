package com.example.demo.login.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SuccessfulBid {
	
	//落札済み商品
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;              //自動採番
	
	@Column
	private String productName;         //商品名
	
	@Column(name = "category")
	private String category;               //カテゴリ
	
	@Column
	private Integer state;                   //5段階 5,とても良い 4,良い 3,普通 2,やや悪い 1,悪い
	
	@Column
	private String Description;           //商品の説明
	
	@Column
	private LocalDateTime startTime;      //落札日時日時
	
	@Column
	private Integer startPrice;                //落札価格価格

}
