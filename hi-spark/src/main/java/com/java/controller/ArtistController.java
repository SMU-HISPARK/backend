package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.dto.Artist;
import com.java.service.ArtistService;


@Controller
public class ArtistController {
	@Autowired ArtistService artistService;
	
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
	public String chat() {
		return "artist/chat";
	}
}
