package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired CartItemRepository cartItemRepository;
	
	@Override
	public void deleteById(int cartItemId) {
		cartItemRepository.deleteById(cartItemId);
		
	}

	@Override
	public List<CartItem> findAllById(List<Integer> cartItemIds) {
		List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);
		return cartItems;
	}

	@Override
	public void saveAll(List<CartItem> cartItems) {
		cartItemRepository.saveAll(cartItems);
	}

	@Override
	public CartItem findById(int cartItemId) {
		CartItem item = cartItemRepository.findById(cartItemId).orElseGet(
				()->{return (new CartItem());}
				);
		return item;
	}

	
}
