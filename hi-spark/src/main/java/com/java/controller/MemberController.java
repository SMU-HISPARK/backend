package com.java.controller;

import com.java.dto.Member;
import com.java.Service.MemberService;
import jakarta.servlet.http.HttpSession; // 세션을 위해 필요
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원 가입 페이지로 이동
    @GetMapping("member/join")
    public String showJoinForm() {
        return "member/join"; // templates/member/join.html 반환
    }

    // 회원 가입 처리
    @PostMapping("member/join")
    public String registerMember(@ModelAttribute Member member) {
        memberService.registerMember(member);
        return "redirect:/member/login";
    }
    
    // 로그인 페이지로 이동
    @GetMapping("member/login")
    public String showLoginForm() {
        return "member/login"; // templates/member/login.html 반환
    }

    // 로그인 처리
    @PostMapping("member/login")
    public String login(@RequestParam("loginId") String loginId,
                        @RequestParam("password") String password,
                        HttpSession session) {
        
        Optional<Member> member = memberService.getMemberByLoginIdAndPassword(loginId, password);

        if (member.isPresent()) {
            // 로그인 성공 시 세션에 회원 정보 저장
            session.setAttribute("loggedInMember", member.get());
            session.setAttribute("loggedInMemberId", member.get().getMemberId());
            return "redirect:/"; // 홈 페이지로 이동
        } else {
            // 로그인 실패
            return "redirect:/login?error=true";
        }
    }
    
    // 로그아웃 처리
    @GetMapping("member/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/"; // 로그아웃 후 홈 페이지로 이동
    }
}