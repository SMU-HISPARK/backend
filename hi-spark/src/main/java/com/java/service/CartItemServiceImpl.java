package com.java.service;

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

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        return cartItemRepository.save(cartItem);
    }

    // Cart 객체로 받는 버전
    @Override
    public CartItem addCartItem(Cart cart, int productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);   
    }
}
