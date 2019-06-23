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

@Table(name = "watch_list")
@NamedQueries({
	@NamedQuery(
			name = "checkWatchList",
			query = "SELECT count(wl) FROM WatchList wl WHERE wl.user.id = :userId AND wl.product.id = :productId"
			),
	@NamedQuery(
			name = "getWatchListIdByUserIdAndProductId",
			query = "SELECT wl.watchListId FROM WatchList wl WHERE wl.user.id = :userId AND wl.product.id = :productId"
			),
	@NamedQuery(
			name = "getWatchListByUserId",
			query = "SELECT wl FROM WatchList wl WHERE wl.user.id = :userId"
			),
	@NamedQuery(
			name = "countAllWatchListByUserId",
			query = "SELECT count(wl) FROM WatchList wl WHERE wl.user.id = :userId"
			)
	
	
	
})

@Entity
public class WatchList {

	@Id
	@Column(name = "watch_list_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer watchListId;                                                         		  //自動採番
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public Integer getWatchListId() {
		return watchListId;
	}

	public void setWatchListId(Integer watchListId) {
		this.watchListId = watchListId;
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
