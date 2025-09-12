package com.java.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.entity.Cart;
import com.java.entity.Member;
import com.java.service.MemberService;
import com.java.service.CartService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CartController {
	
	@Autowired CartService cartService;
	@Autowired MemberService memberService;
	
	@GetMapping("/shop")
	public String shop_index() {
		return "shop/1_shop_main";
	}
	
	@GetMapping("/detail")
	public String detail() {
		return "shop/2_shop_detail";
	}
	
	@GetMapping("/cart")
	public String cart(@CookieValue(value="session_id",defaultValue="aaa") String sessionId,
			Model model) {
		
		
		Member member = memberService.findByLoginId(sessionId);
		if ( member == null ) {  
			// to-do 
			// 오류처리
			model.addAttribute("error", "member not found");
			return "shop/3_shop_cart";
		}
		
		Cart cart = cartService.findByMember(member);

		model.addAttribute("cart", cart);
		return "shop/3_shop_cart";
	}
}
