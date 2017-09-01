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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import com.gp.sync.SyncConstants;

public class SyncAuthenFilter extends AbstractAuthenticationProcessingFilter {

	final static Logger logger = LoggerFactory.getLogger(SyncAuthenFilter.class);
	
	OrRequestMatcher targetUrlMatcher = new OrRequestMatcher(); 
	
	public SyncAuthenFilter(AuthenticationManager authenManager) {

		super("/sync/**");
		this.setAuthenticationManager(authenManager);
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		Optional<String> login = Optional.ofNullable(request.getHeader(SyncConstants.AUTH_HEADER_USERNAME));
		Optional<String> passcode = Optional.ofNullable(request.getHeader(SyncConstants.AUTH_HEADER_PASSWORD));
		Optional<String> audience = Optional.ofNullable(request.getHeader(SyncConstants.AUTH_HEADER_AUDIENCE));
		
		Optional<String> token = Optional.ofNullable(request.getHeader(SyncConstants.WS_HEADER_TOKEN));
		
		Authentication authResult = null;
		if(login.isPresent() && passcode.isPresent()) {
			
			UserPasswordAuthenToken userpassToken = new UserPasswordAuthenToken(login.get(), passcode.get());
			userpassToken.setAudience("");
			
			authResult = attemptAuthenticateUserPassword(userpassToken);
			
		}
		
		if (token.isPresent()) {
			
			JwtAuthenToken jwtAuthenToken = new JwtAuthenToken("");

			authResult = attemptAuthenticateToken(jwtAuthenToken);
		}
				
		return authResult;
	}

	/**
	 * Only Login and passcode is present or token is present  
	 **/
	@Override 
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		
		Optional<String> login = Optional.ofNullable(request.getHeader(SyncConstants.AUTH_HEADER_USERNAME));
		Optional<String> passcode = Optional.ofNullable(request.getHeader(SyncConstants.AUTH_HEADER_PASSWORD));
		Optional<String> token = Optional.ofNullable(request.getHeader(SyncConstants.WS_HEADER_TOKEN));
		
		String context = request.getContextPath();
        String fullURL = request.getRequestURI();
        String url = fullURL.substring(fullURL.indexOf(context)+context.length());
        
		if(login.isPresent() && passcode.isPresent()) {
			
			return super.requiresAuthentication(request, response);
			
		}else if(token.isPresent()) {
			
			return super.requiresAuthentication(request, response);
			
		}else {
			
			return false;
		}

	}
	
	private Authentication attemptAuthenticateUserPassword(UserPasswordAuthenToken userpassToken)
			throws AuthenticationException, IOException, ServletException {

		Authentication userAuthenticationToken = this.getAuthenticationManager().authenticate(userpassToken);
		if (userAuthenticationToken == null)
			throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad User/Pass"));

		return userAuthenticationToken;
	}

	private Authentication attemptAuthenticateToken(JwtAuthenToken jwtToken)
			throws AuthenticationException, IOException, ServletException {

		Authentication userAuthenticationToken = this.getAuthenticationManager().authenticate(jwtToken);
		if (userAuthenticationToken == null)
			throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));

		return userAuthenticationToken;
	}
	
	public void setSuccessTargetUrl(String successTargetUrl) {
		
		SavedRequestAwareAuthenticationSuccessHandler success = (SavedRequestAwareAuthenticationSuccessHandler) this.getSuccessHandler();
		
		success.setDefaultTargetUrl(successTargetUrl);
		
	}
	
	public void setFailureTargetUrl(String failureTargetUrl) {
		
		SimpleUrlAuthenticationFailureHandler failure = (SimpleUrlAuthenticationFailureHandler) this.getFailureHandler();
		failure.setDefaultFailureUrl(failureTargetUrl);
	}
}
