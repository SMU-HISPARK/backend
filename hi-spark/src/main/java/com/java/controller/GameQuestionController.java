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
	
	// 초기 질문의 question_id
	private final int initalQNum = 3;
	
	public int getInitalQNum() {
		return initalQNum;
	}

	
	
	@PostMapping("/game/nextQuestion")
	public GameQuestion nextQuestion(@RequestBody ResponseDto dto) {
		
		GameQuestion questionInit = gServ.findById(getInitalQNum());
		Integer nextTime = 0;
		Integer nextDay = 0;
		
		// 첫 번째 질문 리턴
		if(dto.getQuestion_id() == null) {
			return questionInit;
		}
		
		GameQuestion qSubmitted = gServ.findById(dto.getQuestion_id());
		
		if(qSubmitted.getTime() == 3) {
			nextTime = 1;
			nextDay = qSubmitted.getDay() + 1;
		}else {
			nextTime = qSubmitted.getTime() + 1;
			nextDay = qSubmitted.getDay();
		}
		
		GameQuestion nextGameQ = gServ.findByDayAndTime(nextDay, nextTime);
		
		return nextGameQ;
	}
	
}
