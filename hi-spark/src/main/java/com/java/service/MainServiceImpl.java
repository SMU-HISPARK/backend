package com.java.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Product;
import com.java.repository.MainRepository;

@Service
public class MainServiceImpl implements MainService {

	@Autowired MainRepository mainRepository;
	
	@Override
	public List<Product> findAll() {
		List<Product> list = mainRepository.findAll();
		return list;
	}
	//디테일 페이지로 이동
	@Override
	public Product findByID(int productId) {
		Product product = mainRepository.findById(productId).orElseThrow(() -> new RuntimeException("Artist not found with id: " + productId));;
		return product;
	}
	
	//장바구니
	@Override
	public Product findById(int productId) {
		Product product = mainRepository.findById(productId).orElseThrow(() -> new RuntimeException("Artist not found with id: " + productId));;
		return product;
	}
	
	
	
	

}
