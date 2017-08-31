package com.gp.sync.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.gp.svc.SecurityService;

public class JwtAuthenProvider implements AuthenticationProvider{

	static Logger LOGGER = LoggerFactory.getLogger(UserPasswordAuthenProvider.class);
	
	@Autowired
	SecurityService securitysvc;
	
	@Override
	public Authentication authenticate(Authentication arg0) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> tokenClazz) {
		return tokenClazz.equals(JwtAuthenToken.class);
	}

}
