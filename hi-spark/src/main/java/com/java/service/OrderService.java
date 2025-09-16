package com.java.service;

import java.util.List;
import java.util.Map;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Orders;

public interface OrderService {


	//주문 저장
	void save(Orders order);
	
	//주문번호 생성
	String generateOrderCode();
	
	//주문 생성·재고 차감·포인트 차감
	Orders placeOrder(int memberId, List<Integer> selectedItemIds, Map<String,String> params);


}
