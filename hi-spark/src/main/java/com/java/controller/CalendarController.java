package com.java.controller;

import com.java.config.GoogleCalendarConfig;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CalendarController {

    @Autowired
    private GoogleCalendarConfig googleCalendarConfig;

    // 캘린더 목록 조회
    @GetMapping("/calendars")
    public String getCalendars() {
        try {
            Calendar service = googleCalendarConfig.getCalendarService();
            CalendarList calendarList = service.calendarList().list().execute();

            StringBuilder result = new StringBuilder();
            result.append("<h2>내 캘린더 목록</h2>");

            for (CalendarListEntry entry : calendarList.getItems()) {
                result.append("<div style='border: 1px solid #ccc; padding: 10px; margin: 10px;'>");
                result.append("<h3>").append(entry.getSummary()).append("</h3>");
                result.append("<p>ID: ").append(entry.getId()).append("</p>");
                result.append("<p>설명: ").append(entry.getDescription() != null ? entry.getDescription() : "없음").append("</p>");
                result.append("<p>색상: ").append(entry.getBackgroundColor()).append("</p>");
                result.append("</div>");
            }

            return result.toString();

        } catch (Exception e) {
            return "<h2>에러 발생:</h2><p>" + e.getMessage() + "</p>";
        }
    }

    // 기본 캘린더의 이벤트 (HTML)
    @GetMapping("/events")
    public String getEvents() {
        try {
            Calendar service = googleCalendarConfig.getCalendarService();

            Events events = service.events().list("primary")
                    .setMaxResults(10)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();

            StringBuilder result = new StringBuilder();
            result.append("<h2>최근 일정 10개</h2>");

            if (events.getItems().isEmpty()) {
                result.append("<p>일정이 없습니다.</p>");
            } else {
                for (Event event : events.getItems()) {
                    result.append("<div style='border: 1px solid #ccc; padding: 10px; margin: 10px;'>");
                    result.append("<h3>").append(event.getSummary()).append("</h3>");
                    if (event.getStart() != null) {
                        if (event.getStart().getDateTime() != null) {
                            result.append("<p>시작: ").append(event.getStart().getDateTime()).append("</p>");
                        } else {
                            result.append("<p>날짜: ").append(event.getStart().getDate()).append("</p>");
                        }
                    }
                    if (event.getDescription() != null) {
                        result.append("<p>설명: ").append(event.getDescription()).append("</p>");
                    }
                    if (event.getLocation() != null) {
                        result.append("<p>위치: ").append(event.getLocation()).append("</p>");
                    }
                    result.append("</div>");
                }
            }
            return result.toString();

        } catch (Exception e) {
            return "<h2>에러 발생:</h2><p>" + e.getMessage() + "</p>";
        }
    }

    // 기본 캘린더의 이벤트 (JSON - FullCalendar 연동용)
    @GetMapping("/api/events")
    public List<Map<String, Object>> getEventsJson() throws Exception {
        Calendar service = googleCalendarConfig.getCalendarService();
        Events events = service.events().list("b6787d4ee7c0ab597028a73c8b8404764b420c5433dfa7b346323dc395d634a6@group.calendar.google.com")
                        .setMaxResults(10)
                        .setOrderBy("startTime")
                        .setSingleEvents(true)
                        .execute();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Event e : events.getItems()) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", e.getSummary());
            if (e.getStart().getDateTime() != null) {
                map.put("start", e.getStart().getDateTime().toStringRfc3339());
            } else if (e.getStart().getDate() != null) {
                map.put("start", e.getStart().getDate().toString());
                map.put("allDay", true);
            }
            map.put("description", e.getDescription());
            map.put("location", e.getLocation());
            result.add(map);
        }
        return result;
    }


    // 테스트 일정 생성
    @GetMapping("/create-event")
    public String createEvent() {
        try {
            Calendar service = googleCalendarConfig.getCalendarService();

            Event event = new Event()
                    .setSummary("테스트 일정")
                    .setDescription("API로 만든 테스트 일정입니다.");

            long startTime = System.currentTimeMillis() + 3600000; // 1시간 후
            com.google.api.client.util.DateTime startDateTime =
                    new com.google.api.client.util.DateTime(startTime);
            event.setStart(new com.google.api.services.calendar.model.EventDateTime()
                    .setDateTime(startDateTime));

            long endTime = startTime + 3600000;
            com.google.api.client.util.DateTime endDateTime =
                    new com.google.api.client.util.DateTime(endTime);
            event.setEnd(new com.google.api.services.calendar.model.EventDateTime()
                    .setDateTime(endDateTime));

            Event createdEvent = service.events().insert("primary", event).execute();

            return "<h2>일정 생성 성공!</h2>" +
                   "<p>제목: " + createdEvent.getSummary() + "</p>" +
                   "<p>ID: " + createdEvent.getId() + "</p>" +
                   "<p>링크: <a href='" + createdEvent.getHtmlLink() + "'>구글 캘린더에서 보기</a></p>";

        } catch (Exception e) {
            return "<h2>일정 생성 실패:</h2><p>" + e.getMessage() + "</p>";
        }
    }

    // 메인 페이지
    @GetMapping("/")
    public String home() {
        return "<h1>구글 캘린더 API 테스트</h1>" +
               "<ul>" +
               "<li><a href='/token-info'>토큰 정보</a></li>" +
               "<li><a href='/calendars'>캘린더 목록</a></li>" +
               "<li><a href='/events'>최근 일정 (HTML)</a></li>" +
               "<li><a href='/api/events'>최근 일정 (JSON - FullCalendar용)</a></li>" +
               "<li><a href='/create-event'>테스트 일정 만들기</a></li>" +
               "<li><a href='/schedule.html'>FullCalendar UI</a></li>" +
               "</ul>";
    }
}
