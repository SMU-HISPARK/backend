package com.java.service;

import java.util.List;

import com.java.dto.Member;
import com.java.dto.OrderItemDto;
import com.java.dto.Orders;
import com.java.entity.OrderItem;

public interface MypageService {

	String getTrackingStatus(String orderCode);

	List<Orders> getOrdersList();

	Orders getOrderByCode(String orderCode);

	void updateMember(String loginId, String nickname, String phone1, String phone2, String phone3);

	void updateMemberPassword(String loginId, String password);


	void updateMemberPoint(String loginId, int point);

	List<Orders> getOrdersByMemberId(String loginId);

	void updateOrderAddress(String orderCode, String receiver, String phone, String zipcode, String addressMain, String addressDetail, String deliveryMessage);

	boolean cancelOrder(String orderCode, String loginId);

	List<OrderItemDto> getOrderItemsByOrderCode(String orderCode);


}
