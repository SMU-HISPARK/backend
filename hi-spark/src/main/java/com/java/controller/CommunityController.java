package com.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class CommunityController {

	@GetMapping("/community")
	public String main() {
		return "community/main";
	}
	
	@GetMapping("/community/detail")
	public String detail() {
		return "community/detail";
	}
	
}
