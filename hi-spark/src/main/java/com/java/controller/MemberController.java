package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.dto.MemberDto;
import com.java.entity.Member;
import com.java.entity.Product;
import com.java.entity.sourceData.GameResultClub;
import com.java.service.GameService;
import com.java.service.MainService;
import com.java.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired HttpSession session;
	@Autowired MemberService mServ;
	@Autowired MainService mainService;
	@Autowired GameService gServ;

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
	public String login() {
		return "member/login";
	}
	
	

	@PostMapping("/member/login")
	public String login(
			@RequestParam(name="redirectTo",required=false) String redirectURL,
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@CookieValue(value = "guest_id", required = false) String guestId,
			Model model
			) {
		
		// 일치하는 아이디 찾기
		MemberDto memfind = mServ.findByLoginIdAndPassword(loginId, password);
		if(memfind == null) {
			model.addAttribute("notFound", "1");
			return "member/login";
		}
		Member member = mServ.findById(loginId);
		
		// 로그인 세션 설정
		session.setAttribute("session_id", memfind.getLoginId());
		session.setAttribute("session_name", memfind.getNickname());
	    session.setAttribute("loggedInMember", member);
	    session.setAttribute("session_id", memfind.getLoginId());
	    session.setAttribute("session_name", memfind.getNickname());
	    session.setAttribute("memberId", member.getMemberId());
		
		
		// 세션에 리턴 정보가 있으면 해당 페이지로 리턴
		 if(session.getAttribute("returnTo") != null) {
			// 세션에 있는 리턴 정보 변수에 담고 삭제
			String returnTo = (String)session.getAttribute("returnTo");
			session.removeAttribute("returnTo");
			if(returnTo.equals("game/gamepage_result")) {	// 리턴 정보가 게임페이지라면,
				// 게스트 결과를 회원 정보에 저장
				gServ.saveGuestRun(guestId, memfind.getLoginId());
				// 다시 출력할 게임결과 페이지 정보 가져오기
				GameResultClub gameResult = gServ.findResultById((Integer)session.getAttribute("resultClubId"));
				// 세션에 있는 게임결과 정보 삭제
				session.removeAttribute("resultClubId");
				model.addAttribute("result", gameResult);
				model.addAttribute("userName", (String)session.getAttribute("userName"));
			}
			return returnTo;
			
			}// redirectTo가 관리자 페이지인 경우에만 해당 페이지로 리다이렉트
			else if(redirectURL != null && !redirectURL.isEmpty() && redirectURL.startsWith("/adpage/")) {
				System.out.println("관리자 페이지로 리다이렉트: " + redirectURL);
				return "redirect:" + redirectURL;
		}else {
			return "redirect:/";
		}
		
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