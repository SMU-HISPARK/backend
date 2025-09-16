package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    // 카트와 상품을 기준으로 조회 (객체 기준)
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}
