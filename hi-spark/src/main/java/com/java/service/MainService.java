package com.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.entity.Product;

@Service
public interface MainService {
	// 상품리스트 가져오기
	List<Product> findAll();
	// 상품 상세보기
	Product findByID(int productId);

}
