package com.gp.sync.web;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.gp.common.AccessPoint;

public class UserPasswordAuthenToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = -3068745046717398175L;
	private Optional<String> audience;
	
	private AccessPoint accessPoint;
	
	public UserPasswordAuthenToken(String username, String password) {
		super(username, password);
	}

	public UserPasswordAuthenToken(String principal, String credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}
	
	public Optional<String> getAudience() {
		return audience;
	}

	public void setAudience(Optional<String> audience) {
		this.audience = audience;
	}

	public AccessPoint getAccessPoint() {
		return accessPoint;
	}

	public void setAccessPoint(AccessPoint accessPoint) {
		this.accessPoint = accessPoint;
	}

}
