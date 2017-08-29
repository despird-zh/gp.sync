package com.gp.sync.web;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.gp.common.GPrincipal;
import com.gp.common.JwtPayload;
import com.gp.util.JwtTokenUtils;

public class JwtAuthenToken extends AbstractAuthenticationToken{

	private static final long serialVersionUID = 3814947991896577360L;
	
	private final Object principal;
	private final String audience;
	 
    private Collection<GrantedAuthority>  authorities;
    
	private Optional<String> rawToken;
	
	public JwtAuthenToken(Optional<String> token) {
		super(null);
		this.rawToken = token;
		JwtPayload payload = JwtTokenUtils.parsePayload(token.get());
		this.principal = payload.getSubject();
		this.audience = payload.getAudience();
	}

	public String getToken() {
		return rawToken.get();
	}

	@Override
	public Object getCredentials() {
		return rawToken.get();
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

	public String getAudience() {
		return audience;
	}

	@Override
	public GPrincipal getDetails() {
		
		return (GPrincipal) this.getDetails();
	}
}
