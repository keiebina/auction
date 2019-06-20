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

@NamedQueries({
	@NamedQuery(
			name = "findAllOrderByBidCount",
			query = "SELECT br from BidRanking br WHERE bidCount > 0 ORDER BY bidCount DESC"
			)
})

@Table(name = "bid_ranking")
@Entity
public class BidRanking {

	@Id
	@Column(name = "ranking_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bidRankingId;                                  //自動採番
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;										//商品ID
	
	@Column(name = "bid_count", nullable = false)
	private long bidCount;											//入札数

	public Integer getBidRankingId() {
		return bidRankingId;
	}

	public void setBidRankingId(Integer bidRankingId) {
		this.bidRankingId = bidRankingId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getBidCount() {
		return bidCount;
	}

	public void setBidCount(long bidCount) {
		this.bidCount = bidCount;
	}
	
	
}
