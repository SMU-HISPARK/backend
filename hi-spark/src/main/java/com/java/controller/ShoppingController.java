package com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.entity.Cart;
import com.java.service.ShoppingService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ShoppingController {
	
	@Autowired ShoppingService shoppingService;

	@GetMapping("/shop")
	public String shop_index() {
		return "shop/1_shop_main";
	}
	
	@GetMapping("/detail")
	public String detail() {
		return "shop/2_shop_detail";
	}
	
	@GetMapping("/cart")
	public String cart(@CookieValue(value="session_id",defaultValue="aaa") String sessionId, Model model) {
		Cart cart = shoppingService.getCartBySessionId(sessionId);

		model.addAttribute("cart", cart);
		return "shop/3_shop_cart";
	}
}
