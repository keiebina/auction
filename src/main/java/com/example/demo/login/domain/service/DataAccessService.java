package com.example.demo.login.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.login.dao.BidDao;
import com.example.demo.login.dao.BidRankingDao;
import com.example.demo.login.dao.ProductDao;
import com.example.demo.login.dao.SearchResultDao;
import com.example.demo.login.dao.SuccessfulBidDao;
import com.example.demo.login.dao.UserDao;
import com.example.demo.login.dao.WatchListDao;
import com.example.demo.login.domain.model.Bid;
import com.example.demo.login.domain.model.BidRanking;
import com.example.demo.login.domain.model.Product;
import com.example.demo.login.domain.model.SearchResult;
import com.example.demo.login.domain.model.SuccessfulBid;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.WatchList;

@Service
public class DataAccessService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	UserDao<User> userDao;
	
	@Autowired
	ProductDao<Product> productDao;
	
	@Autowired
	BidDao<Bid> bidDao;
	
	@Autowired
	WatchListDao<WatchList> watchListDao;
	
	@Autowired
	SuccessfulBidDao<SuccessfulBid> successfulBidDao;
	
	@Autowired
	BidRankingDao<BidRanking> bidRankingDao;
	
	@Autowired
	SearchResultDao<SearchResult> searchResultDao;
	
	//@PersistenceContextは一か所のみの為、これを各daoImplで呼び出す
	public EntityManager setEntitymanager(EntityManager em) {
		em = entityManager;
		return em;
	}
	

//==================================================================================================================
//														user
//==================================================================================================================
	
	public long countByUserId(String userId) {
		return userDao.countByUserId(userId);
	}
	
//==================================================================================================================
//														product
//==================================================================================================================
	
	public List<Product> getCommingSoon(LocalDateTime now){					//indexページで表示する終了間近商品３件を取得
		return productDao.getCommingSoon(now);
	}
	
	public Product findByProductId(Integer productId) {									//商品IDから商品情報全て取得
		return productDao.findByProductId(productId);
	}
	public List<Product> getProductByStatusFlag(){
		return productDao.getProductByStatusFlag();								//出品中の商品情報全て取得
	}
	public List<Product> getProductsByCategory(String category, int page){
		return productDao.getProductsByCategory(category, page);					//出品中の商品をカテゴリーで検索
	}
	public List<Product> findProductsBySearchWord(String searchWord) {
		System.out.println(searchWord);
		return productDao.findProductsBySearchWord(searchWord);			//検索ワードで商品情報を取得
	}
	
//==================================================================================================================
//														bid
//==================================================================================================================

	public long countByProductId(int productId) {												//商品IDから商品の入札数を数える
		return bidDao.countByProductId(productId);					
	}
	public User getByProductIdOrderByBidPrice(int productId) {							//商品IDから一番入札価格が大きいユーザーを取得
		try {
			return bidDao.getByProductIdOrderByBidPrice(productId);
		} catch (DataAccessException e) {
			// 入札数が０の場合に起こる
		}
		return null;
	}
	
//==================================================================================================================
//														watchList
//==================================================================================================================
	
	public boolean checkWatchList(String userId, Integer productId) {									//ユーザーIDと商品IDが一致している商品があるか判断
		return watchListDao.checkWatchList(userId, productId);
	}
	public Integer getWatchListIdByUserIdAndProductId(String userId, Integer productId) {		//ユーザーIDと商品IDが一致しているウォッチリストIDを取得
		return watchListDao.getWatchListIdByUserIdAndProductId(userId, productId);
	}
	public List<WatchList> getWatchListByUserId(String userId, int page){										//ユーザーIDからウォッチリスト情報全て取得
		return watchListDao.getWatchListByUserId(userId, page);
	}
	
//==================================================================================================================
//														successfulBid
//==================================================================================================================

	public List<Product> getProductsByUserId(String userId, int page){
		return successfulBidDao.getProductsByUserId(userId, page);				//ユーザーが落札した商品情報を全て取得
	}

//==================================================================================================================
//														bidRanking
//==================================================================================================================
	
	public List<BidRanking> findAllOrderByBidCount(){
		return bidRankingDao.findAllOrderByBidCount();
	}
	
//==================================================================================================================
//														SearchResult
//==================================================================================================================

	public List<Product> getAllSearchResult(int page){
		return searchResultDao.getAllSearchResult(page);
	}
	
//==================================================================================================================
//														ページネーション用
//==================================================================================================================
	
	public long countProductsByUserId(String userId) {
		return successfulBidDao.countProductsByUserId(userId);				//ログインユーザー落札商品カウント
	}
	
	public long countAllWatchListByUserId(String userId) {
		return watchListDao.countAllWatchListByUserId(userId);				//ログインユーザーウォッチリストのカウント
	}
	
	public long countProductsByCategory(String category) {
		return productDao.countProductsByCategory(category);				//選択したカテゴリー商品カウント
	}
	
	public long countAllSearchResult() {
		return searchResultDao.countAllSearchResult();						//検索ワードにかかった商品カウント
	}
}
