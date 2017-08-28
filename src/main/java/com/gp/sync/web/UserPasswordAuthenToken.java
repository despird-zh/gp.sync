package com.gp.sync.web;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserPasswordAuthenToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = -3068745046717398175L;
	private Optional<String> audience;
	
	public UserPasswordAuthenToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public Optional<String> getAudience() {
		return audience;
	}

	public void setAudience(Optional<String> audience) {
		this.audience = audience;
	}

	
}
