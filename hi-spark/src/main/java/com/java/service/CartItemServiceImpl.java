package com.java.service;

import java.util.List;
import java.util.Optional;

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

    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CartRepository cartRepository;
   
    @Override  //삭제
	public void deleteById(int cartItemId) {
		cartItemRepository.deleteById(cartItemId);
	}
	
    @Override  //저장
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);   
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    // cartId로 찾는 버전
    @Override
    public CartItem addCartItem(int cartId, int productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return cartItemRepository.findByCartAndProduct(cart, product)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity); // 이미 있으면 수량 증가
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
    }

    // Cart 객체로 받는 버전
    @Override
    public CartItem addCartItem(Cart cart, int productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return cartItemRepository.findByCartAndProduct(cart, product)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity); // 이미 있으면 수량 증가
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
    }
    
    @Override
	public Optional<CartItem> findByCartAndProduct(Cart cart, Product byId) {
		Optional<CartItem> item = cartItemRepository.findByCartAndProduct(cart, byId);
		return item;
	}


}
