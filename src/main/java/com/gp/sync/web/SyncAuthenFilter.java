package com.gp.sync.web;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import com.gp.sync.SyncConstants;

public class SyncAuthenFilter extends AbstractAuthenticationProcessingFilter {

	final static Logger logger = LoggerFactory.getLogger(SyncAuthenFilter.class);
	
	public SyncAuthenFilter(AuthenticationManager authenManager) {

		super("/");
		this.setAuthenticationManager(authenManager);
	}

	private Authentication attemptAuthenticateUserPassword(UserPasswordAuthenToken userpassToken)
			throws AuthenticationException, IOException, ServletException {

		Authentication userAuthenticationToken = this.getAuthenticationManager().authenticate(userpassToken);
		if (userAuthenticationToken == null)
			throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad User/Pass"));

		return userAuthenticationToken;
	}

	private Authentication attemptAuthenticateToken(JWTAuthenToken jwtToken)
			throws AuthenticationException, IOException, ServletException {

		Authentication userAuthenticationToken = this.getAuthenticationManager().authenticate(jwtToken);
		if (userAuthenticationToken == null)
			throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));

		return userAuthenticationToken;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		Optional<String> login = Optional.ofNullable(request.getParameter(SyncConstants.WS_HEADER_USERNAME));
		Optional<String> passcode = Optional.ofNullable(request.getParameter(SyncConstants.WS_HEADER_PASSWORD));
		Optional<String> audience = Optional.ofNullable(request.getParameter(SyncConstants.WS_HEADER_AUDIENCE));
		Optional<String> token = Optional.ofNullable(request.getHeader(SyncConstants.WS_HEADER_TOKEN));
		
		Authentication authResult = null;
		if(login.isPresent() && passcode.isPresent()) {
			
			UserPasswordAuthenToken userpassToken = new UserPasswordAuthenToken(login, passcode);
			userpassToken.setAudience(audience);
			
			authResult = attemptAuthenticateUserPassword(userpassToken);
			
		}
		
		if (token.isPresent()) {
			
			JWTAuthenToken jwtAuthenToken = new JWTAuthenToken("tokenuser", "tokenpass");
			jwtAuthenToken.setToken(token);
			
			authResult = attemptAuthenticateToken(jwtAuthenToken);
		}
		return authResult;
	}

}
