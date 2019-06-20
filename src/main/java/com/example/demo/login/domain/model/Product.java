package com.example.demo.login.domain.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Table(name = "products")
@NamedQueries({
	@NamedQuery(
			name = "getCommingSoon",
			query = "SELECT p FROM Product p WHERE p.endTime > :now ORDER BY p.endTime"
			),
	@NamedQuery(
			name = "findByProductId",
			query = "SELECT p FROM Product p WHERE p.productId = :id"
			),
	@NamedQuery(
			name = "getProductByStatusFlag",
			query = "SELECT p FROM Product p WHERE p.statusFlag = 1"
			),
	@NamedQuery(
			name = "getProductsByCategory",
			query = "SELECT p FROM Product p WHERE p.statusFlag = 1 AND p.category = :category"
			),
	@NamedQuery(
			name = "findProductsBySearchWord",
			query = "SELECT p FROM Product p WHERE p.statusFlag = 1 AND p.productName like :searchWord OR p.category like :searchWord OR p.description like :searchWord"
			)
})
@Entity
public class Product {
	
	//商品（落札前）
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;                                                         		  //自動採番
	
	@Column(name = "product_name")
	@NotBlank
	private String productName;                                                          	 //商品名
	
	@Column(name = "category")
	@NotNull
	private String category;                                                            		 //カテゴリ
	
	@Column(name = "state")
	@NotNull
	private String state;                                                                      	 // とても良い ,良い ,普通 ,やや悪い ,悪い
	
	@Lob
	@NotBlank
	@Column(name = "description")
	private String description;                   												//商品の説明
	
	@Column(name = "start_time")
	private LocalDateTime startTime;  													//オークション開始日時（商品登録日時）
	
	@Column(name = "end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull
	private LocalDateTime endTime;      													//オークション終了日時
	
	@NotNull
	@Max(value = 9999999)
	@Min(value = 1)
	@Column(name = "start_price")
	private Integer startPrice;               													 //オークション開始価格
	
	@Column(name = "current_price")
	private Integer currentPrice;															//現在価格
	
	@Column(name = "image", length = 10000000)
	private String image;                    													  //画像(BASE64形式）
	
	@Column(name = "status_flag")
	private Integer statusFlag;																//オークション状況判断 0:終了 1:出品中
	
	@Transient                    																	//@Transientを使用するとテーブルに反映されない
	private MultipartFile imageResource;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Integer getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Integer startPrice) {
		this.startPrice = startPrice;
	}

	public Integer getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Integer currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}

	public MultipartFile getImageResource() {
		return imageResource;
	}

	public void setImageResource(MultipartFile imageResource) {
		this.imageResource = imageResource;
	}

	
}
