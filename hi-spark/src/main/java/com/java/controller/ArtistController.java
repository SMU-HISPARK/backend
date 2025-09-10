package com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.service.ArtistService;

@Controller
public class ArtistController {
	
//	@Autowired ArtistService artistService;
	
	@GetMapping("/artist")
	public String main() {
		return "artist/main";
	}
	
	@GetMapping("/artist/detail")
	public String detail() {
		return "artist/detail";
	}
	
	@GetMapping("/artist/chat")
	public String chat() {
		return "artist/chat";
	}
}
