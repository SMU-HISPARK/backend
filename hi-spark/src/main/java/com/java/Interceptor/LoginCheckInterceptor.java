package com.java.Interceptor;

import java.io.PrintWriter;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor{

	// 컨트롤러 실행 전 호출
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception{
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("session_id");
		
		if (loginId == null) {
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    out.println("<script>alert('로그인 후 이용 가능합니다.'); location.href='/member/login'</script>");
		    out.flush();
		    return false;
		}
	
		return true;
	}
	
	
}
