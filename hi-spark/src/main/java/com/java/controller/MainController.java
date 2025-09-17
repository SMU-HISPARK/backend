package com.java.controller;

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
import com.java.entity.Product;
import com.java.service.CartService;
import com.java.service.MainService;
import com.java.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired MainService mainService;
	@Autowired MemberService memberService;
	@Autowired CartService cartService;
	
	@GetMapping("/shop")
	public String shop_main(Model model) {
		List<Product> list = mainService.findAll();
		model.addAttribute("list",list);
		return "shop/shop_main";
	}
	
	@GetMapping("/shop/detail")
	public String shop_main(
			@RequestParam("productId") int productId,
			Model model) {
		Product product = mainService.findByID(productId);
		model.addAttribute("product",product);
		return "shop/shop_detail";
	}	


	
}
