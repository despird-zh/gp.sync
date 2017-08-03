package com.gp.sync.web.socket;

import java.security.Principal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

public class HandlerDecorator extends WebSocketHandlerDecorator{

	static Logger LOGGER = LoggerFactory.getLogger(HandlerDecorator.class);
		
	public HandlerDecorator(WebSocketHandler delegate) {
		super(delegate);
	}

	@Override
	public void afterConnectionEstablished(final WebSocketSession session) throws Exception {

		LOGGER.info("online principal: {}", session.getPrincipal());
		super.afterConnectionEstablished(session);
		Principal p = session.getPrincipal();
		if(StringUtils.isNotBlank(p.getName()))
			AgentSessionRegistry.addSession(p.getName(), session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		Object mp = message.getPayload();
		LOGGER.debug("type payload: {}", mp.getClass().getName());
		LOGGER.debug("session principal: {}", session.getPrincipal());
		super.handleMessage(session, message);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
			throws Exception {
		//String username = session.getPrincipal().getName();
		LOGGER.info("offline: {}", session);
		super.afterConnectionClosed(session, closeStatus);
		Principal p = session.getPrincipal();
		if(StringUtils.isNotBlank(p.getName()))
			AgentSessionRegistry.removeSession(p.getName());
	}

}
