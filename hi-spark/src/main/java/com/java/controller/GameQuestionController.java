package com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.ResponseDto;
import com.java.entity.sourceData.GameQuestion;
import com.java.service.GameService;

@RestController
public class GameQuestionController {

	@Autowired GameService gServ;
	
	
	@PostMapping("/game/nextQuestion")
	public GameQuestion nextQuestion(@RequestBody ResponseDto dto) {
		
		// 첫 번째 질문 리턴
		if(dto.getQuestion_id().equals("")) {
			return gServ.findById(3);
		}
		
		
		// 고른 선택지를 받아와서 해당 GameQuestion 객체를 찾음 
		GameQuestion QuestionInput =  gServ.findById(dto.getQuestion_id());
		
		
		
		
		return null;
	}
	
}
