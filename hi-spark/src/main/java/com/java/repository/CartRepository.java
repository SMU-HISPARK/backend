package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.entity.Cart;
import com.java.entity.Member;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	Optional<Cart> findByMember(Member member);

}
