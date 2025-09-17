package com.java.controller;

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
		Artist artist = artistService.findById(ano);
		model.addAttribute("artist",artist);
		
        String loginId = (String) session.getAttribute("session_id");
        Member member = memberService.findById(loginId);

        List<Chat> history = chatService.findByMemberAndArtistOrderByCreatedAtAsc(member, artist);

        model.addAttribute("artist", artist);
        model.addAttribute("history", history);
        model.addAttribute("loginId", loginId);
        System.out.println("artist : " + artist);
        System.out.println("history : " + history);
        System.out.println("history size: " + history.size());

		return "artist/chat";
	}
}