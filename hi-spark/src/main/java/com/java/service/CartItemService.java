package com.java.service;

import java.util.List;

import com.java.entity.CartItem;

public interface CartItemService {

	//삭제
	void deleteById(int cartItemId);

	//아이디로 카트아이템찾기
	List<CartItem> findAllById(List<Integer> cartItemIds);

	//전체저장?
	void saveAll(List<CartItem> cartItems);

	//카트아이템아이디로 카트아이템찾기
	CartItem findById(int cartItemId);

}
