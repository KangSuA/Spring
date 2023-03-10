package com.multi.campus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
 interceptor처리 할 클래스
 반드시 HandlerInterceptorAdater를 상속받아 만들어야한다. 
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {
	//컨트롤러가 호출되기 전에 실행되는 메소드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		//로그인 유무를 확인하고 로그인 된 경우 호출한 매핑주소로 이동
		//로그인 안된 경우 로그인 폼으로 이동
		HttpSession session = request.getSession();
		
		String lodId = (String)session.getAttribute("logId");
		String logStatus = (String)session.getAttribute("logStatus"); //null, "Y"
		
		if(logStatus==null || !logStatus.equals("Y")) { //로그인 안된 경우
			response.sendRedirect(request.getContextPath()+"/loginForm");
			return false;
		}
		
		//반환형이 false이면 매핑을 변경
		//반환형이 true면 매핑을 지속
		return true;
	}
	//컨트롤러가 실행된 후 뷰로 이동하기 전 실행되는 메소드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView mav) throws Exception {
		
	}
	//컨트롤러가 실행된 후 호출되는 메소드
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
		
	}
}
