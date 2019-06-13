package com.example.demo.login.domain.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
