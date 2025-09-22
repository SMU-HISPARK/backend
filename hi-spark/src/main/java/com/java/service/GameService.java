package com.java.service;

import java.util.List;

import com.java.entity.Member;
import com.java.entity.sourceData.GameOptions;
import com.java.entity.sourceData.GameQuestion;
import com.java.entity.sourceData.GameResultClub;
import com.java.entity.userData.GameRun;
import com.java.entity.userData.GameSession;

public interface GameService {

	/// SELECT (findBy)
	
	// 첫 질문 호출
	GameQuestion findQuestionById(Integer question_id);
	// 다음 단계 질문 호출
	GameQuestion findByDayAndTime(Integer nextDay, Integer nextTime, String string);

	// 결과 출력
	GameResultClub findResultById(Integer club_id);
	
	// 로그인한 멤버 가져오기
	Member findMemberById(String loginId);

	// 게스트 쿠키로 세션 정보 찾기, 만료 갱신
	GameSession findSessionById(String guestId);
	
	///
	
	/// INSERT (save)
	// 이번 회차 게임 정보 저장
	void sessionSave(GameSession gameSession);
	void save(GameRun gameRun, List<Integer> runResponse);
	void resultUnlock(String loginId, GameResultClub gameResult, GameRun gameRun);
	void saveGuestRun(String guestId, String loginId);
	
	///
	
	
	/// game service
	
	// 이번 회차 게임 점수 계산
	Integer[] getResult(List<Integer> runResponses);
	
	
	/// stats
	
	Long gameRunCount();
	List<Double> calResultRate();
	Long calMemberCount();
	List<Double> calMultiRate();
	GameOptions calMostOption();
	


}
