package com.java.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.java.dto.MemberDto;
import com.java.entity.Member;

@Component
public class AdminCheckInterceptor implements HandlerInterceptor {
    
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	        
	        String requestURI = request.getRequestURI();
	        System.out.println("관리자 페이지 접근 시도: " + requestURI);
	        
	        HttpSession session = request.getSession(false);
	        if (session == null) {
	            System.out.println("세션이 없음 - 로그인 페이지로 리다이렉트");
	            // JSP 경로는 redirectTo에 포함하지 않음
	            if (isValidRedirectUrl(requestURI)) {
	                response.sendRedirect("/member/login?redirectTo=" + requestURI);
	            } else {
	                response.sendRedirect("/member/login");
	            }
	            return false;
	        }
	        
	        // 기존 세션 구조 사용: loggedInMember 와 session_id
	        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
	        String sessionId = (String) session.getAttribute("session_id");
	        
	        if (loggedInMember == null || sessionId == null) {
	            System.out.println("로그인 정보 없음 - 로그인 페이지로 리다이렉트");
	            if (isValidRedirectUrl(requestURI)) {
	                response.sendRedirect("/member/login?redirectTo=" + requestURI);
	            } else {
	                response.sendRedirect("/member/login");
	            }
	            return false;
	        }
	        
	        // 관리자 체크 (hi_spark 계정만 허용)
	        if (!"hi_spark".equals(sessionId)) {
	            System.out.println("관리자 권한 없음: " + sessionId);
	            response.sendRedirect("/?error=no_permission");
	            return false;
	        }
	        
	        System.out.println("관리자 권한 확인 완료: " + sessionId);
	        return true;
	    }
	    
	    // 유효한 리다이렉트 URL인지 체크
	    private boolean isValidRedirectUrl(String url) {
	        if (url == null || url.isEmpty()) {
	            return false;
	        }
	        
	        // JSP 파일 경로나 내부 경로는 제외
	        if (url.contains("/WEB-INF/") || 
	            url.contains(".jsp") || 
	            url.contains(".html") ||
	            url.startsWith("/static/") ||
	            url.startsWith("/css/") ||
	            url.startsWith("/js/") ||
	            url.startsWith("/images/")) {
	            return false;
	        }
	        
	        // 관리자 페이지 URL만 허용
	        return url.startsWith("/adpage/");
	    }
}