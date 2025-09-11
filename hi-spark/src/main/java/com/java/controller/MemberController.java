package com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.dto.MemberDto;
import com.java.entity.Member;
import com.java.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired HttpSession session;
	@Autowired MemberService mServ;
	
	@GetMapping("/member/step01")
	public String step01() {
		return "member/step01";
	}
	
	@GetMapping("/member/step02")	// 추후 post로 바꿀 것
	public String step02() {
		return "member/step02";
	}
	
	@PostMapping("/member/step03")
	public String step03(Member member) {
		
		mServ.save(member);
		
		return "member/step03";
	}
	

	
	
	@GetMapping("/member/login")
	public String login() {
//		session.setAttribute("session_id", "admin");
//		session.setAttribute("session_name", "namee");
		return "member/login";
	}
	
	
	@PostMapping("/member/login")
	public String login(
			@RequestParam(name="redirectTo",required=false) String redirectURL,
			@RequestParam("id") String id,
			@RequestParam("password") String password,
			Model model
			) {
		
		// 일치하는 아이디 찾기
		MemberDto memfind = mServ.findByIdAndPassword(id, password);
		if(memfind == null) {
			model.addAttribute("notFound", "1");
			return "member/login";
		}
		
		// 로그인 세션 설정
		session.setAttribute("session_id", memfind.getId());
		session.setAttribute("session_name", memfind.getNickname());
		
		// 로그인 요청이 들어온 페이지로 리다이렉트
		if(redirectURL != null) {
			return "redirect:"+redirectURL;
		}
		return "index";
	}
	
	@GetMapping("/member/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	
	
}
