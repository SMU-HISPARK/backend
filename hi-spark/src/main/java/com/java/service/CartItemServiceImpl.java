package com.java.service;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Product;
import com.java.repository.CartItemRepository;
import com.java.repository.CartRepository;
import com.java.repository.ProductRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    // cartId로 찾는 버전
    @Override
    public CartItem addCartItem(int cartId, int productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        // 이미있는 카트 아이템 있는지 확인
        return cartItemRepository.findByCart_CartIdAndProduct_ProductId(cartId,productId)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity); // 수량 증가
                    return cartItemRepository.save(item);
                })
                .orElseGet(() -> {
                    // 없으면 새로 생성
                    CartItem cartItem = CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .quantity(quantity)
                            .build();
                    return cartItemRepository.save(cartItem);
                });
        		
        
        
        
        
        
        
        
        
        
        
//        CartItem cartItem = CartItem.builder()
//                .cart(cart)
//                .product(product)
//                .quantity(quantity)
//                .build();
//
//        return cartItemRepository.save(cartItem);
    }

    // Cart 객체로 받는 버전
    @Override
    public CartItem addCartItem(Cart cart, int productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow();
        
        return cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(),productId)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity); // 수량 증가
                    return cartItemRepository.save(item);
                }).orElseGet(()->{
                    CartItem cartItem = CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .quantity(quantity)
                            .build();
                    
                    
                    return cartItemRepository.save(cartItem);
                });
        
        
        
   

    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);   
    }

	@Override
	public void deleteById(int cartItemId) {
		cartItemRepository.deleteById(cartItemId);
	}
}
