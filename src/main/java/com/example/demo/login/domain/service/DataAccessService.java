package com.example.demo.login.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.login.dao.SearchResultDao;
import com.example.demo.login.daoImpl.BidDaoImpl;
import com.example.demo.login.daoImpl.BidRankingDaoImpl;
import com.example.demo.login.daoImpl.ProductDaoImpl;
import com.example.demo.login.daoImpl.SearchResultDaoImpl;
import com.example.demo.login.daoImpl.SuccessfulBidDaoImpl;
import com.example.demo.login.daoImpl.WatchListDaoImpl;
import com.example.demo.login.domain.model.BidRanking;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.SearchResult;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.WatchList;

@Service
public class DataAccessService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	ProductDaoImpl productDaoImpl;
	
	@Autowired
	BidDaoImpl bidDaoimpl;
	
	@Autowired
	WatchListDaoImpl watchListDaoImpl;
	
	@Autowired
	SuccessfulBidDaoImpl successfulBidDaoImpl;
	
	@Autowired
	BidRankingDaoImpl bidRankingDaoImpl;
	
	@Autowired
	SearchResultDao<SearchResult> searchResultDao;
	
	public EntityManager setEntitymanager(EntityManager em) {
		em = entityManager;
		return em;
	}
	
	
	
//==================================================================================================================
//														product
//==================================================================================================================
	
	public List<Product> getCommingSoon(LocalDateTime now){					//indexページで表示する終了間近商品３件を取得
		return productDaoImpl.getCommingSoon(now);
	}
	
	public Product findByProductId(Integer productId) {									//商品IDから商品情報全て取得
		return productDaoImpl.findByProductId(productId);
	}
	public List<Product> getProductByStatusFlag(){
		return productDaoImpl.getProductByStatusFlag();								//出品中の商品情報全て取得
	}
	public List<Product> getProductsByCategory(String category){
		return productDaoImpl.getProductsByCategory(category);					//出品中の商品をカテゴリーで検索
	}
	public List<Product> findProductsBySearchWord(String searchWord) {
		System.out.println(searchWord);
		return productDaoImpl.findProductsBySearchWord(searchWord);			//検索ワードで商品情報を取得
	}
	
//==================================================================================================================
//														bid
//==================================================================================================================

	public long countByProductId(int productId) {												//商品IDから商品の入札数を数える
		return bidDaoimpl.countByProductId(productId);					
	}
	public User getByProductIdOrderByBidPrice(int productId) {							//商品IDから一番入札価格が大きいユーザーを取得
		try {
			return bidDaoimpl.getByProductIdOrderByBidPrice(productId);
		} catch (DataAccessException e) {
			// 入札数が０の場合に起こる
		}
		return null;
	}
	
//==================================================================================================================
//														watchList
//==================================================================================================================
	
	public boolean checkWatchList(String userId, Integer productId) {									//ユーザーIDと商品IDが一致している商品があるか判断
		return watchListDaoImpl.checkWatchList(userId, productId);
	}
	public Integer getWatchListIdByUserIdAndProductId(String userId, Integer productId) {		//ユーザーIDと商品IDが一致しているウォッチリストIDを取得
		return watchListDaoImpl.getWatchListIdByUserIdAndProductId(userId, productId);
	}
	public List<WatchList> getWatchListByUserId(String userId){										//ユーザーIDからウォッチリスト情報全て取得
		return watchListDaoImpl.getWatchListByUserId(userId);
	}
	
//==================================================================================================================
//														successfulBid
//==================================================================================================================

	public List<Product> getProductsByUserId(String userId){
		return successfulBidDaoImpl.getProductsByUserId(userId);				//ユーザーが落札した商品情報を全て取得
	}

//==================================================================================================================
//														bidRanking
//==================================================================================================================
	
	public List<BidRanking> findAllOrderByBidCount(){
		return bidRankingDaoImpl.findAllOrderByBidCount();
	}
	
//==================================================================================================================
//														SearchResult
//==================================================================================================================

	public List<Product> getAllSearchResult(){
		return searchResultDao.getAllSearchResult();
	}
}
