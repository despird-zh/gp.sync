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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class AuthenChannelInterceptorAdapter extends ChannelInterceptorAdapter {
	    
    static Logger LOGGER = LoggerFactory.getLogger(AuthenChannelInterceptorAdapter.class);

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        
    		final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        Principal principal = accessor.getUser();
        
        if (StompCommand.CONNECT == accessor.getCommand()) {
        		LOGGER.debug("Try to authorization during connect : {}", principal.getName());
	        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
	                principal.getName(),
	                null,
	                AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
	        
	        user.setDetails(principal);
	        accessor.setUser(user);
	    }
        return message;
    }
}
