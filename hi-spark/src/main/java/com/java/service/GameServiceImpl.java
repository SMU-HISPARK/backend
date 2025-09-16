package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.sourceData.GameQuestion;
import com.java.repository.GameQuestionRepository;

@Service
public class GameServiceImpl implements GameService {

	@Autowired GameQuestionRepository gqRep;
	
	@Override
	public GameQuestion findById(Integer question_id) {

		GameQuestion gq = gqRep.findById(question_id).orElse(null);
		return gq;
	}

	@Override
	public GameQuestion findByDayAndTime(Integer nextDay, Integer nextTime) {
		
		// 다음 순서의 질문 리스트 추출
		List<GameQuestion> gqList = gqRep.findByDayAndTime(nextDay, nextTime);
		
		// 질문 하나일 시 바로 리턴
		if(gqList.size() == 1) return gqList.get(0);
		
		// 질문 고르는 로직 - 랜덤 (필요 시 변경)
		int random_q =  (int)(Math.random() * gqList.size());
		GameQuestion nextGameQ = gqList.get(random_q);
		
		return nextGameQ;
	}

	
	
}
