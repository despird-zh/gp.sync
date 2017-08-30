package com.gp.sync.svc.impl;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAuthenService {
	
    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  username, final String password) throws AuthenticationException {
        if (username == null || username.trim().length()>0) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().length()>0) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }
        // Add your own logic for retrieving user in fetchUserFromDb()
        if ("usr1".equals(username)) {
            throw new BadCredentialsException("Bad credentials for user " + username);
        }
        
        // null credentials, we do not pass the password along to prevent security flaw
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("USER")
        );
    }
    
    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  token) throws AuthenticationException {
        
        // null credentials, we do not pass the password along to prevent security flaw
        return new UsernamePasswordAuthenticationToken(
                "token-user",
                null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("USER")
        );
    }
   
}
