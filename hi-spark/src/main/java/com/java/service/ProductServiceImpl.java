package com.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Product;
import com.java.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired ProductRepository productRepository;
	
	@Override
	public void save(Product product) {
		productRepository.save(product);
		
	}

}
