package com.jackchen.utils;

import com.jackchen.DTO.TuserDto;
import com.jackchen.pojo.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserContext {

	public static final String LOGIN_IN_SESSION = "logininfo";
	public static final String VERIFYCODE_IN_SESSION = "VERIFYCODE_IN_SESSION";

	private static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}
	
	public static void putLogininfo(User logininfo) {
		getRequest().getSession().setAttribute(LOGIN_IN_SESSION, logininfo);
	}

	public static User getCurrent() {
		return (User) getRequest().getSession().getAttribute(
				LOGIN_IN_SESSION);
	}


}
