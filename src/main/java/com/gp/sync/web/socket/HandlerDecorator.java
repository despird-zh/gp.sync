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

import com.gp.core.AppContextHelper;

public class HandlerDecorator extends WebSocketHandlerDecorator{

	static Logger LOGGER = LoggerFactory.getLogger(HandlerDecorator.class);
	
	private SyncNodeSessionRegistry sessionRegistry ;
	
	public HandlerDecorator(WebSocketHandler delegate) {
		
		super(delegate);
		LOGGER.debug("initial a new decorator");
	}

	private void initialRegistry() {
		if( null == this.sessionRegistry )
			this.sessionRegistry = AppContextHelper.getSpringBean(SyncNodeSessionRegistry.class);
	}
	
	@Override
	public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
		
		initialRegistry();
		super.afterConnectionEstablished(session);
		Principal p = session.getPrincipal();
		if(StringUtils.isNotBlank(p.getName()))
			sessionRegistry.addNodeSession(p.getName(), session);
	}
	
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		LOGGER.info("handle message principal: {}", session.getPrincipal());
		
		if("dev2".equals(session.getPrincipal().getName())) {
			session.close();
		}
		super.handleMessage(session, message);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)	throws Exception {
		
		initialRegistry();
		super.afterConnectionClosed(session, closeStatus);
		Principal p = session.getPrincipal();
		if(StringUtils.isNotBlank(p.getName()))
			sessionRegistry.removeNodeSession(p.getName());
	}

}