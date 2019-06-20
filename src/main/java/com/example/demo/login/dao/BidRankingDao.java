package com.example.demo.login.dao;

import java.io.Serializable;
import java.util.List;

import com.example.demo.login.domain.model.BidRanking;

public interface BidRankingDao<T> extends Serializable{

	public List<BidRanking> findAllOrderByBidCount();
}
