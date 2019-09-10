package com.jackchen.utils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//进行登录过滤
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod)handler;
			//用到requiredLogin的时候，获取用到的注解并拦截
			RequiredLogin annotation = hm.getMethodAnnotation(RequiredLogin.class);
			if (annotation != null){
				if (request.getSession().getAttribute(UserContext
				.LOGIN_IN_SESSION) == null){
					response.sendRedirect("/login.jsp");
					return false;
				}
			}
		}
		//其他没用到的请求继续传递
		return super.preHandle(request, response, handler);
	}

	/*@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			RequiredLogin rl = hm.getMethodAnnotation(RequiredLogin.class);
			if (rl != null) {
				if (request.getSession().getAttribute(
						UserContext.LOGIN_IN_SESSION) == null) {
					response.sendRedirect("/login.html");
					return false;
				}
			}
		}
		return super.preHandle(request, response, handler);
	}*/
}
