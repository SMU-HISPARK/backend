package com.java.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.entity.sourceData.GameResultClub;
import com.java.entity.userData.GameRun;
import com.java.entity.userData.GameSession;
import com.java.service.GameService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class GameController {
	
	@Autowired HttpSession session;
	@Autowired GameService gServ;
	
	
	@GetMapping("/game")
	public String gamepage_start() {
		
		return "game/gamepage_start";
	}
	
	@PostMapping("/game/saveRun")
	public String saveRun(
			@RequestParam("answers") List<Integer> answers,
			@RequestParam("name") String name,
			@CookieValue(value = "guest_id", required = false) String guestId,
			HttpServletResponse response,
			Model model) {
		
		// 점수 계산
		Integer[] scores = gServ.getResult(answers);
		
		// 결과값 구하기
		MultiValueMap<Integer, Integer> scoreMap = new LinkedMultiValueMap<Integer, Integer>();
		// 점수와 동아리를 대응시키기 위해 Map객체 사용
		for(Integer i=0; i<scores.length; i++) {
			scoreMap.add(scores[i], i+1);
		}
		Integer[] scoresO = scores.clone();		// 점수 저장용 배열
		Arrays.sort(scores);	// 최고 점수 추출
		List<Integer> resultList = scoreMap.get(scores[4]);
		Integer runResult = 0;
		if(resultList.size() == 1) {	// 동점이 없는 경우
			runResult = resultList.getFirst();
		}else {	// 동점이 있을 때 getLast (시간 남으면 다르게 구현)
			runResult = resultList.getLast();
		}
		GameResultClub gameResult = gServ.findResultById(runResult);

		// 게임 런 객체 생성
		GameRun gameRun = GameRun.builder()
				.resultClub(gameResult)
				.score1(scoresO[0])
				.score2(scoresO[1])
				.score3(scoresO[2])
				.score4(scoresO[3])
				.score5(scoresO[4])
				.userName(name)
				.build();
		
		// 로그인 아이디 저장
		String loginId = "";
		if(session.getAttribute("session_id") != null) {	// 로그인 유저의 경우
			loginId = (String)session.getAttribute("session_id");
			gameRun.setMember(gServ.findMemberById(loginId));
			
		}else {	// 게스트 세션 저장
			// 생성된 쿠키 유무 확인
			if(guestId == null) {	// 생성된 쿠키가 없으면
				
				// 게스트 세션값 생성
				UUID uuid = UUID.randomUUID();
				String sessionId = uuid.toString();
				
				// 세션값 쿠키 설정
				Cookie cookie = new Cookie("guest_id", sessionId);
				cookie.setPath("/");
				cookie.setHttpOnly(true);
				cookie.setMaxAge(60 * 60 * 24 * 30);	// 30일 동안 유지
				response.addCookie(cookie);
				
				// DB에 게스트 세션 정보 저장
				GameSession gameSession = GameSession.builder()
						.sessionId(sessionId)
						.lastSeen(LocalDateTime.now())
						.build();
				gServ.sessionSave(gameSession);
				
				// 게임런 객체에 세션 정보 저장
				gameRun.setGameSession(gameSession);
				
			}else {	// 쿠키가 있으면
				// DB에서 세션 정보 찾아서 (+만료일 갱신)
				GameSession gameSession = gServ.findSessionById(guestId);
				// 게임런 객체에 저장
				gameRun.setGameSession(gameSession);
				// 쿠키 기간 연장 위해 새로 발급
				Cookie cookie = new Cookie("guest_id", guestId);
				cookie.setPath("/");
				cookie.setHttpOnly(true);
				cookie.setMaxAge(60 * 60 * 24 * 30);	// 30일로 연장
				response.addCookie(cookie);
			}
			
		}// 게스트 세션 저장 완료
		
		// 해당 게임 런, 응답 저장
		gServ.save(gameRun, answers);
		
		// 로그인 유저의 경우 resultUnlocked 업데이트
		if(!loginId.equals("")) {
			gServ.resultUnlock(loginId, gameResult, gameRun);			
		}
		
		
		model.addAttribute("result", gameResult);
		model.addAttribute("userName", name);
		
		return "game/gamepage_result";
		
	}
	
	@PostMapping("/game/saveGuestRun")
	public String saveGuestRun(
			@RequestParam("club_id") Integer clubId) {
		session.setAttribute("returnTo","game/gamepage_result");
		session.setAttribute("resultClubId", clubId);
		return "redirect:/member/login";
	}
	
	
}
