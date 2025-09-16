package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.Cart;
import com.java.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	static void addCartItem(Cart cart, int productId, int quantity) {
		
	}
	
	// 이미 있는 카트 아이템
	Optional<CartItem> findByCart_CartIdAndProduct_ProductId(int cartId, int productId);

	
	
}
