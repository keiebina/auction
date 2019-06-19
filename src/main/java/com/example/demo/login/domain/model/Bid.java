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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Table(name = "bid")
@NamedQueries({
	@NamedQuery(
			name = "countByProductId",
			query = "SELECT count(b) FROM Bid b WHERE b.product.id = :id"
			),
	@NamedQuery(
			name = "getByProductIdOrderByBidPrice",
			query = "SELECT b.user FROM Bid b WHERE b.product.id = :id ORDER BY b.bidPrice DESC"
			),
	@NamedQuery(
			name = "getProductIdBy"
			
			)
})

@Entity
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
	private Product product;															//入札商品ID

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}

	public Integer getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Integer currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Integer getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Integer bidPrice) {
		this.bidPrice = bidPrice;
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
