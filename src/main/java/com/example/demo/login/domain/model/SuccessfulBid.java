package com.example.demo.login.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "successful_bid")
@Entity
@Data
public class SuccessfulBid {
	
	//落札済み商品
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer successfulId;              //自動採番
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;						//落札者
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;				//落札商品
}
