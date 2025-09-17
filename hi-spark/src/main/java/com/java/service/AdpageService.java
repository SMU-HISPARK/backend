package com.java.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.java.entity.Product;
import com.java.repository.ProductRepository;

public interface AdpageService {

	Map<String, Map<String, Integer>> getStats(LocalDateTime start, LocalDateTime end);
	
	Map<String, Object> getOrdersData(Timestamp start, Timestamp end);

	Product save(Product product);

    Product update(int id, Product product);

    void delete(int id);

    Optional<Product> findById(int id);

    List<Product> findAll();

	List<Product> getAllProducts();
	
}
