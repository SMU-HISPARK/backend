package com.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingController {

	@GetMapping("/shop")
	public String shop_index() {
		return "shop/1_shop_main";
	}
	
	@GetMapping("/cart")
	public String cart() {
		return "shop/3_shop_cart";
	}
}
