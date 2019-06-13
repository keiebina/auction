package com.example.demo.login.domain.model;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
	
	//商品（落札前）
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;              //自動採番
	
	@Column(name = "product_name")
	@NotBlank
	private String productName;         //商品名
	
	@Column(name = "category")
	@NotNull
	private String category;               //カテゴリ
	
	@Column(name = "state")
	@NotNull
	private String state;                   // とても良い ,良い ,普通 ,やや悪い ,悪い
	
	@Lob
	@NotBlank
	@Column(name = "description")
	private String description;           //商品の説明
	
	@Column(name = "end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull
	private LocalDateTime endTime;      //オークション終了日時
	
	@NotNull
	@Max(value = 9999999)
	@Min(value = 1)
	@Column(name = "start_price")
	private Integer startPrice;                //オークション開始価格

}
