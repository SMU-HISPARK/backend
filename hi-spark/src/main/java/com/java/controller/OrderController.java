package com.java.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.entity.Member;
import com.java.entity.Orders;
import com.java.service.CartService;
import com.java.service.MemberService;
import com.java.service.OrderService;

@Controller
public class OrderController {

	@Autowired OrderService orderService;
	@Autowired CartService cartService;
	@Autowired MemberService memberService;
	
	@PostMapping("/order/order_form")
	public String orderform(@RequestParam List<Integer> cartItemIds, Model model) {
		int memberId = 1;
		Cart cart = cartService.getCartByMember_MemberId(memberId);
        model.addAttribute("cart", cart);
	    return "order/orderForm";
	}
	
	@PostMapping("/order/order_finish")
	public String orderfinish() {
		
		return "shop/shop_order_finish";
	}
}
