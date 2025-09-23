package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.dto.MemberDto;
import com.java.entity.Member;
import com.java.entity.Product;
import com.java.service.MainService;
import com.java.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired HttpSession session;
	@Autowired MemberService mServ;
	@Autowired MainService mainService;
	
	@GetMapping("/member/step01")
	public String step01() {
		return "member/step01";
	}
	
	@GetMapping("/member/step02")	// 추후 post로 바꿀 것
	public String step02() {
		return "member/step02";
	}
	
	@ResponseBody
	@PostMapping("/member/idCheck")
	public boolean idCheck(@RequestParam("loginId") String loginId) {
		return !mServ.existsByLoginId(loginId);
	}
	
	@ResponseBody
	@PostMapping("/member/mailCheck")
	public boolean mailCheck(@RequestParam("email") String email) {
		return !mServ.existsByEmail(email);
	}
	
	
	@PostMapping("/member/step03")
	public String step03(Member member) {
		
		mServ.save(member);
		
		return "member/step03";
	}
	

	@GetMapping("/member/login")
	public String login(
	        @RequestParam(name="redirectTo", required=false) String redirectTo,
	        Model model
	) {
	    // GET 요청 시 redirectTo를 JSP로 전달
	    if(redirectTo != null && !redirectTo.isEmpty()) {
	        model.addAttribute("redirectTo", redirectTo);
	    }
	    return "member/login";
	}

	@PostMapping("/member/login")
	public String login(
	        @RequestParam(name="redirectTo", required=false) String redirectTo,
	        @RequestParam("loginId") String loginId,
	        @RequestParam("password") String password,
	        Model model
	) {
	    MemberDto memfind = mServ.findByLoginIdAndPassword(loginId, password);
	    if(memfind == null) {
	        model.addAttribute("notFound", "1");
	        model.addAttribute("redirectTo", redirectTo); // 로그인 실패 후에도 유지
	        return "member/login";
	    }

	    Member member = mServ.findById(loginId);
	    session.setAttribute("loggedInMember", member);
	    session.setAttribute("session_id", memfind.getLoginId());
	    session.setAttribute("session_name", memfind.getNickname());
	    session.setAttribute("memberId", member.getMemberId());

	    return "redirect:/";
	}

	@GetMapping("/member/logout")
	public String logout(@RequestParam(name="redirectTo", required=false) String redirectTo) {
	    session.invalidate();
	    if(redirectTo != null && !redirectTo.isEmpty()) {
	        return "redirect:" + redirectTo;
	    }
	    return "redirect:/";
	}
	
}