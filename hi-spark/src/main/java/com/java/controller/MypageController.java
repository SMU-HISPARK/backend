package com.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@GetMapping("/member")
	public String member() {
		return "mypage/member";
	}
	
	@GetMapping("/chgpw")
	public String chgpw() {
		return "mypage/chgpw";
	}
	
	@GetMapping("/club")
	public String club() {
		return "mypage/club";
	}
	
	@GetMapping("/community")
	public String community() {
		return "mypage/community";
	}
	
	@GetMapping("/shop")
	public String shop() {
		return "mypage/shop";
	}
	
	@GetMapping("/shopupdate")
	public String shopupdate() {
		return "mypage/shopupdate";
	}
	
	@GetMapping("/point")
	public String point() {
		return "mypage/point";
	}
	
	
	
}
