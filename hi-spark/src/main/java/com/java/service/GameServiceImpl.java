package com.java.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.entity.Member;
import com.java.entity.compositeId.ResponseId;
import com.java.entity.compositeId.UnlockedId;
import com.java.entity.sourceData.GameOptions;
import com.java.entity.sourceData.GameQuestion;
import com.java.entity.sourceData.GameResultClub;
import com.java.entity.sourceData.ScoringRules;
import com.java.entity.userData.GameRun;
import com.java.entity.userData.GameSession;
import com.java.entity.userData.QuestionResponse;
import com.java.entity.userData.ResultUnlocked;
import com.java.repository.GameOptionsRepository;
import com.java.repository.GameQuestionRepository;
import com.java.repository.GameResultClubRepository;
import com.java.repository.GameRunRepository;
import com.java.repository.GameSessionRepository;
import com.java.repository.MemberRepository;
import com.java.repository.QuestionResponseRepository;
import com.java.repository.ResultUnlockedRepository;
import com.java.repository.ScoringRulesRepository;

@Service
public class GameServiceImpl implements GameService {

	@Autowired GameQuestionRepository gqRep;
	@Autowired ScoringRulesRepository srRep;
	@Autowired GameResultClubRepository grcRep;
	@Autowired MemberRepository memRep;
	@Autowired GameSessionRepository sessionRep;
	@Autowired GameRunRepository gRunRep;
	@Autowired QuestionResponseRepository qrRep;
	@Autowired GameOptionsRepository gOptionsRep;
	@Autowired ResultUnlockedRepository ruRep;
	
	private static final int clubNumber = 5;
	
	@Override
	public GameQuestion findQuestionById(Integer questionId) {

		GameQuestion gq = gqRep.findById(questionId).orElse(null);
		return gq;
	}

	@Override
	public GameQuestion findByDayAndTime(Integer nextDay, Integer nextTime) {
		
		// 다음 순서의 질문 리스트 추출
		List<GameQuestion> gqList = gqRep.findByDayAndTime(nextDay, nextTime);
		
		// 질문 하나일 시 바로 리턴
		if(gqList.size() == 1) return gqList.get(0);
		
		// 질문 고르는 로직 - 랜덤 (필요 시 변경)
		int randomQ =  (int)(Math.random() * gqList.size());
		GameQuestion nextGameQ = gqList.get(randomQ);
		
		return nextGameQ;
	}
	
	@Override
	public Integer[] getResult(List<Integer> runResponses) {
		
		Integer lib = 0;
		Integer band = 0;
		Integer patrol = 0;
		Integer basket = 0;
		Integer dessert = 0;
		
		for(int i=0; i<runResponses.size(); i++) {
			GameOptions gameOptions = gOptionsRep.getReferenceById(runResponses.get(i));
			List<ScoringRules> scoreList = srRep.findByOption(gameOptions);
			
			for(int j=0; j<scoreList.size(); j++) {
				switch(scoreList.get(j).getClub().getClubId()) {
				case 1:
					lib += scoreList.get(j).getScoringDelta();
					break;
				case 2:
					band += scoreList.get(j).getScoringDelta();
					break;
				case 3:
					patrol += scoreList.get(j).getScoringDelta();
					break;
				case 4:
					basket += scoreList.get(j).getScoringDelta();
					break;
				case 5:
					dessert += scoreList.get(j).getScoringDelta();
					break;
				}
			}
		}
		Integer[] scores = {lib, band, patrol, basket, dessert};
		return scores;
	}

	// 결과 출력
	@Override
	@Transactional
	public GameResultClub findResultById(Integer clubId) {
		GameResultClub gameResult = grcRep.findById(clubId).orElse(null);
		// resultCount 업데이트
		gameResult.setResultCount(gameResult.getResultCount() + 1); 
		return gameResult;
	}


	@Override
	public Member findMemberById(String loginId) {
		Member member = memRep.findByLoginId(loginId).orElse(null);
		return member;
	}

	@Override
	public void sessionSave(GameSession gameSession) {
		sessionRep.save(gameSession);
	}

	@Override
	@Transactional
	public GameSession findSessionById(String guestId) {
		GameSession gameSession = sessionRep.findById(guestId).orElseThrow();
		// 세션 최근 접속 기록 및 만료일 갱신
		gameSession.setLastSeen(LocalDateTime.now());
		gameSession.setExpiresAt(LocalDateTime.now().plusDays(30));
		
		return gameSession;
	}

	@Override
	@Transactional
	public void save(GameRun gameRun, List<Integer> runResponse) {
		// GameRun 저장
		gRunRep.save(gameRun);
		
		// QuestionResponse 저장
		for(int i=0; i<runResponse.size(); i++) {
			GameOptions gOptions = gOptionsRep.getReferenceById(runResponse.get(i));
			QuestionResponse qResponse = QuestionResponse.builder()
				.responseId(new ResponseId(gameRun.getRunId(), runResponse.get(i)))
				.gameRun(gameRun)
				.options(gOptions)
				.build();
			qrRep.save(qResponse);
		}
		
		
		
	}

	@Override
	public void resultUnlock(String loginId, GameResultClub gameResult, GameRun gameRun) {
		
		Member member = findMemberById(loginId);
		UnlockedId unlockedId = new UnlockedId(member.getMemberId(), gameResult.getClubId());
		ResultUnlocked resultUnlocked = ruRep.findById(unlockedId).orElse(null);
		
		if(resultUnlocked == null) {
			ResultUnlocked newUnlockedResult = ResultUnlocked.builder()
					.unlockedId(unlockedId)
					.member(member)
					.resultClub(gameResult)
					.gameRun(gameRun)
					.build();
			
			ruRep.save(newUnlockedResult);
		}
	}

	// 게스트 플레이어의 게임 결과를 로그인 아이디에 저장
	@Override
	public void saveGuestRun(String guestId, String loginId) {
		// 로그인 한 아이디로 회원 정보 가져오기
		Member member = memRep.findByLoginId(loginId).orElseThrow();
		// 쿠키에 있는 식별자로 DB에 있는 게스트 정보 가져오기
		GameSession gameSession = sessionRep.getReferenceById(guestId);
		for(int i=1; i<=clubNumber; i++) {	// 모든 동아리에 대해 결과 검색 
			GameResultClub resultClub =  grcRep.findById(i).orElseThrow();
			// 게스트 정보와 특정 동아리 결과로 게임 플레이 정보 검색
			List<GameRun> gameRunList = gRunRep.findByGameSessionAndResultClubOrderByFinishedAtAsc(gameSession, resultClub);
			// 이미 특정 동아리를 결과로 봤으면
			if(gameRunList.size() != 0) {
				// 해당 멤버의 동아리 가입 여부를 검색할 수 있는 ID값 만들기
				UnlockedId unlockedId = new UnlockedId(member.getMemberId(),i);
				// 위에서 발급한 ID로 동아리 가입 여부 검사
				if(!ruRep.existsById(unlockedId)) {	// 동아리에 가입이 안 되어 있으면
					ruRep.save(		// 동아리 가입
							ResultUnlocked.builder()
							.member(member)
							.gameRun(gameRunList.getFirst())
							.resultClub(resultClub)
							.unlockedId(unlockedId)
							.build()
							);
				}
			}
		}
	}

	
	
}
