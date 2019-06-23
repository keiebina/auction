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

@Table(name = "search_result")
	@NamedQueries({
		@NamedQuery(
				name = "getAllSearchResult",
				query = "SELECT sr.product FROM SearchResult sr GROUP BY sr.product ORDER BY count(sr) DESC"
				),
		@NamedQuery(
				name = "countAllSearchResult",
				query = "SELECT count(DISTINCT sr.product) FROM SearchResult sr"
				)
	})
@Entity
public class SearchResult {
	
	@Id
	@Column(name = "search_result_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer searchResultId;                                  //自動採番
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public Integer getSearchResultId() {
		return searchResultId;
	}

	public void setSearchResultId(Integer searchResultId) {
		this.searchResultId = searchResultId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
