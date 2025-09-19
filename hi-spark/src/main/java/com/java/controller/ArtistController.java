package com.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.dto.Artist;
import com.java.dto.Chat;
import com.java.entity.Member;
import com.java.service.ArtistService;
import com.java.service.ChatService;
import com.java.service.MemberService;

import jakarta.servlet.http.HttpSession;


@Controller
public class ArtistController {
	@Autowired ArtistService artistService;
	@Autowired MemberService memberService;
	@Autowired ChatService chatService;
	
	@GetMapping("/artist")
	public String main() {
		return "artist/main";
	}
	
	@GetMapping("/artist/detail")
	public String detail(
			@RequestParam("ano") int ano, Model model) {
		Artist artist = artistService.findById(ano);
		System.out.println("controller ano : "+ano);
		List<Artist> list = artistService.findAll();
		model.addAttribute("artist",artist);
		model.addAttribute("list",list);
		return "artist/detail";
	}
	
	
	@GetMapping("/artist/chat")
	public String chat(
			@RequestParam("ano") int ano, HttpSession session, Model model) {
		Integer memberId = (Integer) session.getAttribute("memberId");
		Artist artist = artistService.findById(ano);
		String loginId = (String) session.getAttribute("session_id");

        // 로그인된 경우에만 Member 조회하고 채팅 기록 가져오기
        if (memberId != null) {
            Member member = memberService.findById(memberId);
            List<Chat> history = chatService.findByMemberAndArtistOrderByCreatedAtAsc(member, artist);
            model.addAttribute("history", history);
            model.addAttribute("member", member);
            
            System.out.println("memberId : " + memberId);
            System.out.println("history : " + history);
            System.out.println("history size: " + history.size());
        } else {
            // 비로그인시 빈 히스토리
            model.addAttribute("history", new ArrayList<>());
            System.out.println("Not logged in - empty history");
        }
        
        model.addAttribute("artist", artist);
        model.addAttribute("loginId", loginId); // JSP에서 null 체크용
        
        System.out.println("artist : " + artist);
        
        return "artist/chat";
    }
}