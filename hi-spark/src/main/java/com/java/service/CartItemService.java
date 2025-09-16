package com.java.service;

import com.java.entity.Cart;
import com.java.entity.CartItem;

public interface CartItemService {
    CartItem addCartItem(int cartId, int productId, int quantity);
    CartItem addCartItem(Cart cart, int productId, int quantity); // 오버로드 가능
    CartItem save(CartItem cartItem);
    
    
    
    //카트아이템 삭제
	void deleteById(int cartItemId);
}
