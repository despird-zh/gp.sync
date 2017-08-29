package com.gp.sync.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class SyncAuthenFailureHandler implements AuthenticationFailureHandler {
	
	private String targetUrl;
	
	public SyncAuthenFailureHandler(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		// some logic here..
		request.setAttribute("param", "error");
		response.sendRedirect(this.targetUrl);

	}
}
