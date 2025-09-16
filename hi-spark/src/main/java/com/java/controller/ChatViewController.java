package com.java.controller;

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
        Member member = memberService.findById(loginId);
        Artist artist = artistService.findById(ano);

        List<Chat> history = chatService.findByMemberAndArtistOrderByCreatedAtAsc(member, artist);

        model.addAttribute("artist", artist);
        model.addAttribute("history", history);
        model.addAttribute("loginId", loginId);
        System.out.println("artist : " + artist);
        System.out.println("history : " + history);
        System.out.println("history size: " + history.size());

        return "Artist_chat"; // JSP 이름 (Artist_chat.jsp)
    }
}
