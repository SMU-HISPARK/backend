package com.java.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.entity.Cart;
import com.java.entity.Member;
import com.java.service.MemberService;
import com.java.service.CartItemService;
import com.java.service.CartService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller

public class CartController {
	
	@Autowired CartService cartService;
	@Autowired CartItemService cartItemService;
	@Autowired MemberService memberService;
	
	@GetMapping("/shop")
	public String shop_main() {
		return "shop/shop_main";
	}
	
	@GetMapping("/shop/detail")
	public String shop_detail() {
		return "shop/shop_detail";
	}
	
	@GetMapping("/shop/cart")
	public String cart(
			
			HttpServletRequest request,
			Model model) {
		HttpSession session = request.getSession();
		int memberId = 1;
		session.setAttribute("memberId",memberId);
		
		Member member = memberService.findById(memberId);
		if ( member == null ) {  
			// to-do 
			// 오류처리
			model.addAttribute("error", "member not found");
			return "shop/shop_cart";
		}
		
		Cart cart = cartService.getCartByMember(member);

		model.addAttribute("cart", cart);
		
		
		return "shop/shop_cart";
	}
	
	
	@DeleteMapping("/cart/delete")
	@ResponseBody
	public String deleteCartItem(@RequestParam("cartItemId") int CartItemId) {
		cartItemService.deleteById(CartItemId);
		return "success";
	}
	
}
