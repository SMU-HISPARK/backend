package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.Product;

public interface MainRepository extends JpaRepository<Product, Integer> {

}
