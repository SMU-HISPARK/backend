package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.Cart;
import com.java.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	static void addCartItem(Cart cart, int productId, int quantity) {
		
	}

	
	
}
