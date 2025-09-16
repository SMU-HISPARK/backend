package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
