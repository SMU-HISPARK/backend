package com.java.service;

import java.util.List;
import java.util.Optional;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Product;

public interface CartItemService {
	
	

	//삭제
	void deleteById(int cartItemId);

	//아이디로 카트아이템찾기
	List<CartItem> findAllById(List<Integer> cartItemIds);

	//전체저장?
	void saveAll(List<CartItem> cartItems);

	//카트아이템아이디로 카트아이템찾기
	CartItem findById(int cartItemId);
	
	//카트번호,제품번호,수량으로 카트아이템 작성
    CartItem addCartItem(int cartId, int productId, int quantity);
    
    //카트,제품번호,수량으로 카트아이템 작성
    CartItem addCartItem(Cart cart, int productId, int quantity); // 오버로드 가능
    
    //카트아이템 개별저장
    CartItem save(CartItem cartItem);

    //
	Optional<CartItem> findByCartAndProduct(Cart cart, Product byId);
	
	
	
	
//	
//    CartItem addCartItem(int cartId, int productId, int quantity);
//    CartItem addCartItem(Cart cart, int productId, int quantity); // 오버로드 가능
//    CartItem save(CartItem cartItem);
//    
//    
//    
//    //카트아이템 삭제
//	void deleteById(int cartItemId);
}
