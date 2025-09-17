package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
	
	//이미 있는 카트 아이템 확인
	Optional<CartItem> findByCart_CartIdAndProduct_ProductId(int cartId, int productId);
	
	// /cart/add
	static void addCartItem(Cart cart, int productId, int quantity) { }

	Optional<CartItem> findByCartAndProduct(Cart cart, Product byId);

	//
	
	

}
