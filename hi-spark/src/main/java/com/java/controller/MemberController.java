package com.java.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.dto.Member;
import com.java.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired MemberService memberService;
	@Autowired HttpSession session;
	
	@GetMapping("/")
	public String index() {
		
		return "member/login";
	}
	
	@PostMapping("/")
	public String index(HttpServletResponse response, 
			RedirectAttributes redirect,
			Member member,Model model) {
		
		String url = "";
		String id = member.getLoginId();
		String pw = member.getPassword();
		
		Member m = memberService.findByLoginIdAndPassword(id,pw);
		
		if(m == null) {
			System.out.println("로그인 실패");
			url = "redirect:/";
		}else {
			System.out.println("로그인 성공");
			session.setAttribute("session_id", id);
			session.setAttribute("session_name", pw);
			model.addAttribute("m",m);
			url = "redirect:/mypage/member";
			System.out.println(session.getAttribute("session_id"));
		}
		
		
		
		return url;
	}
	
}
