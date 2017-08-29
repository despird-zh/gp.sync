package com.gp.sync.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

import com.google.common.base.Optional;

public class UserPasswordAuthenProvider implements AuthenticationProvider{

	@SuppressWarnings("unchecked")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = null;
		String password = null;
		
		if(authentication instanceof UserPasswordAuthenToken) {
			username = ((Optional<String>) authentication.getPrincipal()).get();
			password = ((Optional<String>) authentication.getCredentials()).get();
		}else {
			username = authentication.getName();
			password = (String)authentication.getCredentials();
		}
		
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || !StringUtils.equals("usr1", username)) {
            throw new BadCredentialsException("Invalid Backend User Credentials");
        }
		
        return new UsernamePasswordAuthenticationToken(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_BACKEND_ADMIN"));
	}
    
	@Override
	public boolean supports(Class<?> tokenClazz) {
		return tokenClazz.equals(UserPasswordAuthenToken.class) 
				|| tokenClazz.equals(UsernamePasswordAuthenticationToken.class);
	}

}
