package com.java.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.Member;
import com.java.dto.Orders;
import com.java.dto.Tracking;
import com.java.entity.OrderItem;

import jakarta.transaction.Transactional;

@Repository
public interface MypageRepository extends JpaRepository<Orders, Integer>{

	Orders findByOrderCode(String orderCode);

	// 특정 회원의 특정 주문 조회 
    Orders findByOrderCodeAndMemberLoginId(String orderCode, String loginId);
    
	@Query("SELECT t FROM Tracking t WHERE t.orders.orderCode = :orderCode")
	List<Tracking> findTrackingByOrderCode(@Param("orderCode") String orderCode);

	List<Orders> findByMemberLoginId(String loginId);

	@Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.orderState = :orderState WHERE o.orderCode = :orderCode AND o.member.loginId = :loginId")
    int updateOrderState(@Param("orderCode") String orderCode, @Param("loginId") String loginId, @Param("orderState") int orderState);


	@Modifying
    @Transactional
    int deleteByOrderCodeAndMemberLoginId(String orderCode, String loginId);
	

	@Query("SELECT o FROM Orders o WHERE o.orderCode = :orderCode AND o.member.loginId = :loginId")
	Orders getOrderByCode(@Param("orderCode") String orderCode, @Param("loginId") String loginId);

	
}
