package com.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MemberController {

	@GetMapping("/login")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("id") String id,@RequestParam("pw") String pw,HttpServletResponse response) {
		Cookie cookie = new Cookie("session_id","aaa");
		cookie.setMaxAge(24*60*60);
		response.addCookie(cookie);
		return "redirect:/cart";
	}
}
