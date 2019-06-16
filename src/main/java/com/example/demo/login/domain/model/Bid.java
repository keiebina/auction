package com.example.demo.login.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Table(name = "bid")
@Entity
@Data
public class Bid {
	
	@Id
	@Column(name = "bid_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bidId;                                                         		  //自動採番
	
	@Transient
	private Integer currentPrice;													//現在価格（バリデーションに使うため一応）
	
	@NotNull
	@Column(name = "bid_price")
	private Integer bidPrice;                                                           //入札価格
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;																	//入札ユーザーID
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;														//入札商品ID

}
