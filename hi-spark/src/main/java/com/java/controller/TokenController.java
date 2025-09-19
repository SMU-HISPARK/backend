package com.java.controller;

import com.java.config.GoogleCalendarConfig;
import com.google.api.services.calendar.Calendar;
import com.google.api.client.auth.oauth2.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private GoogleCalendarConfig googleCalendarConfig;

    @GetMapping("/token-info")
    public String getToken() {
        try {
            // GoogleCalendarConfig를 통해 Calendar 서비스 가져오기
            Calendar calendarService = googleCalendarConfig.getCalendarService();
            
            // Calendar 서비스가 성공적으로 생성되면 토큰이 있다는 의미
            return "<h2>Google Calendar 연결 성공!</h2>" +
                   "<p>✅ 인증이 완료되었습니다.</p>" +
                   "<p>✅ 구글 캘린더 API를 사용할 수 있습니다.</p>" +
                   "<br>" +
                   "<h3>테스트 링크:</h3>" +
                   "<ul>" +
                   "<li><a href='/api/events'>API 이벤트 데이터 (JSON)</a></li>" +
                   "<li><a href='/calendars'>캘린더 목록</a></li>" +
                   "<li><a href='/events'>이벤트 목록 (HTML)</a></li>" +
                   "<li><a href='/schedule.html'>FullCalendar UI</a></li>" +
                   "</ul>";
                   
        } catch (Exception e) {
            return "<h2>인증 필요</h2>" +
                   "<p>❌ 구글 캘린더 인증이 필요합니다.</p>" +
                   "<p>에러: " + e.getMessage() + "</p>" +
                   "<p>브라우저에서 인증 창이 나타날 예정입니다...</p>";
        }
    }
}