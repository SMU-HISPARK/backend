package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders,Integer> {

}
