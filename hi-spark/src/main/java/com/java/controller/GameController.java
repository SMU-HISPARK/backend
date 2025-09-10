package com.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
	
	@GetMapping("/game")
	public String gamepage_start() {
		
		
		return "game/gamepage_start";
	}
	
	
	@GetMapping("/game/result")
	public String gamepage_result() {
		
		return "game/gamepage_result";
	}
}
