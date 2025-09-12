package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.entity.Product;
import com.java.service.MainService;

@Controller
public class MainController {
	
	@Autowired MainService mainService;
	
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
	
	@GetMapping("/cart/add") // GET 방식으로 바꾸기
	public String addCart(@RequestParam int productId, @RequestParam int quantity) {
	    // 장바구니 추가 로직
	    return "redirect:/shop/3_shop_cart";
	}

		
	
}
