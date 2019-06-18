package com.example.demo.login.dao;

import java.io.Serializable;
import java.util.List;

import com.example.demo.login.domain.model.WatchList;

public interface WatchListDao<T> extends Serializable {

	public boolean checkWatchList(String userId, Integer productId);
	public Integer getWatchListIdByUserIdAndProductId(String userId, Integer productId);
	public List<WatchList> getWatchListByUserId(String userId);
}
