package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.entity.Cart;

import jakarta.transaction.Transactional;

public interface ShoppingRepository extends JpaRepository<Cart, Integer> {

	Optional<Cart> findByMember_LoginId(String id);
	
	

}
