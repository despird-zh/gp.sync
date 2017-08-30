package com.gp.sync.web;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

import com.gp.common.GPrincipal;

public class UserPasswordAuthenProvider implements AuthenticationProvider{

	static Logger LOGGER = LoggerFactory.getLogger(UserPasswordAuthenProvider.class);
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = null;
		String password = null;
		
		LOGGER.debug("FormLogin Auth :{}", authentication.getName());
		username = authentication.getName();
		password = (String)authentication.getCredentials();

		
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || !StringUtils.equals("usr1", username)) {
            throw new BadCredentialsException("Invalid Backend User Credentials");
        }
		
        GPrincipal details = new GPrincipal(username);
      
        details.setEmail("a@135.com");
        
        UserPasswordAuthenToken rtv =  new UserPasswordAuthenToken(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
        
        rtv.setDetails(details);
        
        return rtv;
	}
    
	@Override
	public boolean supports(Class<?> tokenClazz) {
		return tokenClazz.equals(UsernamePasswordAuthenticationToken.class);
	}

}
