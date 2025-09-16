package com.java.service;

import com.java.dto.RunDto;
import com.java.entity.sourceData.GameQuestion;

public interface GameService {

	// 첫 질문 호출
	GameQuestion findById(Integer question_id);

	// 다음 단계 질문 호출
	GameQuestion findByDayAndTime(Integer nextDay, Integer nextTime);

	// 이번 회차 게임 정보 저장
	void save(RunDto runDto);

}
