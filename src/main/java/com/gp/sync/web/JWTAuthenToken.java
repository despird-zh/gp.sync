package com.gp.sync.web;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JWTAuthenToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = 3814947991896577360L;
	
	private Optional<String> token;
	
	public JWTAuthenToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public Optional<String> getToken() {
		return token;
	}

	public void setToken(Optional<String> token) {
		this.token = token;
	}

}
