//package com.java.controller;
//
//import java.sql.Timestamp;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.java.entity.Cart;
//import com.java.entity.Member;
//import com.java.entity.Orders;
//import com.java.service.OrderService;
//
//@Controller
//public class OrderController {
//
//	@Autowired OrderService orderService;
//	
//	@PostMapping("/order/order_form/all")
//	public String orderform(Model model) {
//		Member member = new Member(1,"aaa","1111","010-1111-1111","냠냠이","김냠냠","aaa@gmail.com",0,new Timestamp(System.currentTimeMillis()));
//		String session_id = "aaa";
////		Cart cart = orderService.getCartByMember_Id(session_id);
////		Orders order = orderService.createOrderFromCart(cart);
//		return "shop/4_shop_order";
//	}
//	
//	@PostMapping("/order/order_finish")
//	public String orderfinish() {
//		return "shop/5_shop_order_finish";
//	}
//}
