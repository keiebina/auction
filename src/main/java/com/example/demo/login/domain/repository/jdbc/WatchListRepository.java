package com.example.demo.login.domain.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.login.domain.model.WatchList;

public interface WatchListRepository extends JpaRepository<WatchList, Integer> {

}
