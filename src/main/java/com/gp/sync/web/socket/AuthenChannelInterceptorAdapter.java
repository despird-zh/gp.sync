package com.gp.sync.web.socket;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class AuthenChannelInterceptorAdapter extends ChannelInterceptorAdapter {
	    
    static Logger LOGGER = LoggerFactory.getLogger(AuthenChannelInterceptorAdapter.class);

    private AuthenticationManager authenManager = null;
    
    public AuthenChannelInterceptorAdapter(AuthenticationManager authenManager) {
    		this.authenManager = authenManager;
    }
    
    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        
    		final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        Principal principal = accessor.getUser();
        
        if (StompCommand.CONNECT == accessor.getCommand()) {
        		LOGGER.debug("Try to authorization during connect : {}", principal.getName());
        		
        		Authentication authen = (Authentication) principal;
        		
        		Authentication passedAuthen = authenManager.authenticate(authen);
	        
        		accessor.setUser(passedAuthen);
	    }
        return message;
    }
}
