package com.java.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>{

	// 기간별 주문 조회 (취소 주문 제외)
    @Query("SELECT o FROM Orders o WHERE o.createdAt BETWEEN :start AND :end AND o.orderState != -1 ORDER BY o.createdAt ASC")
    List<Orders> findOrdersBetween(@Param("start") Timestamp start, @Param("end") Timestamp end);
    
    // 모든 주문 조회 (취소 주문 포함) - 필요한 경우를 위해
    @Query("SELECT o FROM Orders o WHERE o.createdAt BETWEEN :start AND :end ORDER BY o.createdAt ASC")
    List<Orders> findAllOrdersBetween(@Param("start") Timestamp start, @Param("end") Timestamp end);
    
    // 기간별 총 매출 조회 (취소 주문 제외)
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Orders o WHERE o.createdAt BETWEEN :start AND :end AND o.orderState != -1")
    Integer getTotalSalesBetween(@Param("start") Timestamp start, @Param("end") Timestamp end);
    
    // 기간별 총 주문 건수 조회 (취소 주문 제외)
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.createdAt BETWEEN :start AND :end AND o.orderState != -1")
    Long getTotalOrderCountBetween(@Param("start") Timestamp start, @Param("end") Timestamp end);
	
}
