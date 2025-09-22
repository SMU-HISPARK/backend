package com.java.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.java.entity.Product;

public interface ProductService {

	List<Product> findAll();
    Optional<Product> findById(int productId);
    Product save(Product product);
    Product update(int productId, Product product);
    void delete(int productId);
	List<Product> findAllOrderByIdDesc();
	Page<Product> findAll(Pageable pageable);
    
    

}