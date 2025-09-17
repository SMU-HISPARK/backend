package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

	
}