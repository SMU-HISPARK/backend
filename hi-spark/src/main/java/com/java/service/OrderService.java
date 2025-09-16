package com.java.service;

import java.util.List;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Orders;

public interface OrderService {


	//주문 저장
	void save(Orders order);
	
	//주문번호 생성
	String generateOrderCode();


}
