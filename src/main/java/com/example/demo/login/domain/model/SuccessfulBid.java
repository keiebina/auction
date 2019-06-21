package com.example.demo.login.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "successful_bid")
@NamedQueries({
	@NamedQuery(
			name = "getProductsByUserId",
			query = "SELECT sb.product FROM SuccessfulBid sb WHERE sb.user.id = :id "
			),
	@NamedQuery(
			name = "countProductsByUserId",
			query = "SELECT count(sb) FROM SuccessfulBid sb WHERE sb.user.id = :id"
			)
})

@Entity
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

	public Integer getSuccessfulId() {
		return successfulId;
	}

	public void setSuccessfulId(Integer successfulId) {
		this.successfulId = successfulId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
