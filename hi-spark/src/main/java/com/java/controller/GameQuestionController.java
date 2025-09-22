package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.ResponseDto;
import com.java.dto.StatDto;
import com.java.entity.sourceData.GameOptions;
import com.java.entity.sourceData.GameQuestion;
import com.java.service.GameService;

@RestController
public class GameQuestionController {

	@Autowired GameService gServ;
	
	// 초기 질문의 question_id
	private final int initalQNum = 1;
	
	public int getInitalQNum() {
		return initalQNum;
	}

	
	
	@PostMapping("/game/nextQuestion")
	public GameQuestion nextQuestion(@RequestBody ResponseDto dto) {
		
		GameQuestion questionInit = gServ.findQuestionById(getInitalQNum());
		Integer nextTime = 0;
		Integer nextDay = 0;
		
		// System.out.println(dto.toString());
		
		// 첫 번째 질문 리턴
		if(dto.getQuestionId() == null) {
			System.out.println(questionInit.toString());
			return questionInit;
		}
		
		GameQuestion qSubmitted = gServ.findQuestionById(dto.getQuestionId());
		
		if(qSubmitted.getTime() == 3) {
			nextTime = 1;
			nextDay = qSubmitted.getDay() + 1;
		}else {
			nextTime = qSubmitted.getTime() + 1;
			nextDay = qSubmitted.getDay();
		}
		
		GameQuestion nextGameQ = gServ.findByDayAndTime(nextDay, nextTime, dto.getTag());
		
		System.out.println(nextGameQ.toString());
		
		return nextGameQ;
	}
	
	@GetMapping("/game/getStat")
	public StatDto getStat() {
		
		StatDto statDto = new StatDto();
		
		// 1. 테스트 참여자 수
		statDto.setRuns(gServ.gameRunCount());
		
		// 2. 각 결과 비율 
		statDto.setResultRateList(gServ.calResultRate());
		
		// 3. 다중 가입 비율
		// 참여자 중 회원 수
		statDto.setMemberCount(gServ.calMemberCount());
		// 회원 중 여러 결과를 본 비율
		statDto.setMultiClubMemberRateList(gServ.calMultiRate());
		
		// 4. 가장 높은 비율로 선택된 선택지... 
		// (질문 생성 후 특정 선택지에 한정해서 뽑을 수도 있음) 
		GameOptions mostOption = gServ.calMostOption();
		statDto.setMostSelectedOption(mostOption);
		
		// 위 선택지와 연결된 문항
		if(mostOption == null) {
			statDto.setRelatedQuestion(null);
		}else {
			statDto.setRelatedQuestion(mostOption.getQuestion());			
		}
		
		return statDto;
	}
}
