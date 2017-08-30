package com.gp.sync.web.socket;

import org.apache.commons.lang.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import com.gp.sync.SyncConstants;
import com.gp.sync.svc.impl.WebSocketAuthenService;

public class AuthenChannelInterceptorAdapter extends ChannelInterceptorAdapter {
	    
    private final WebSocketAuthenService webSocketAuthenService;

    public AuthenChannelInterceptorAdapter(final WebSocketAuthenService webSocketAuthenticatorService) {
        this.webSocketAuthenService = webSocketAuthenticatorService;
    }

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
        	
            final String username = accessor.getFirstNativeHeader(SyncConstants.WS_HEADER_USERNAME);
            final String password = accessor.getFirstNativeHeader(SyncConstants.WS_HEADER_PASSWORD);
            final String token = accessor.getFirstNativeHeader(SyncConstants.WS_HEADER_TOKEN);
            
            UsernamePasswordAuthenticationToken user = null;
            if(StringUtils.isNotBlank(token)) {
            		user = webSocketAuthenService.getAuthenticatedOrFail(username, password);
            }else {
            		user = webSocketAuthenService.getAuthenticatedOrFail(token);
            }
            accessor.setUser(user);
        }
        return message;
    }
}
