package com.example.demo.login.domain.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.SearchResult;

@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, Integer> {

}
