package com.gp.sync.web.socket;

import java.security.Principal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.gp.common.GPrincipal;
import com.gp.common.JwtPayload;
import com.gp.util.JwtTokenUtils;

public class HandshakeHandler extends DefaultHandshakeHandler {

	Logger LOGGER = LoggerFactory.getLogger(HandshakeHandler.class);
	
	
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                    Map<String, Object> attributes) {
    		
    		HttpHeaders headers = request.getHeaders();
    		
    		String mapHeaders = headers.toSingleValueMap().toString();
    		if(LOGGER.isDebugEnabled()) {
    			LOGGER.info("determineUser headers : {}", mapHeaders);
    		}
    		String login = headers.toSingleValueMap().get("login");
    		String passcode = headers.toSingleValueMap().get("passcode");
    		String token = headers.toSingleValueMap().get("token");
    		if(StringUtils.isNotBlank(token)) {
    			JwtPayload jwt = JwtTokenUtils.parsePayload(token);
    			login = jwt.getSubject();
    			passcode = token;
    		}
        return new GPrincipal(login == null? "blind":login );
    }

}
