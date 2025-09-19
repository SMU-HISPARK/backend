package com.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.dto.Artist;
import com.java.dto.Chat;
import com.java.entity.Member;
import com.java.service.ArtistService;
import com.java.service.ChatService;
import com.java.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatViewController {

    @Autowired MemberService memberService;
    @Autowired ChatService chatService;
    @Autowired ArtistService artistService;

    @GetMapping("/chat/view")
    public String chatView(@RequestParam int ano, HttpSession session, Model model) {
    	String loginId = (String) session.getAttribute("session_id");
        Integer memberId = (Integer) session.getAttribute("memberId");
        Artist artist = artistService.findById(ano);

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
        model.addAttribute("loginId", loginId); // JSP에서 null 체크용 (String)
        
        System.out.println("artist : " + artist);
        

        return "Artist_chat";
    }
}