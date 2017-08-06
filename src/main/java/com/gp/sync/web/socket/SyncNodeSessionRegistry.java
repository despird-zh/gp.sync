package com.gp.sync.web.socket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

public class SyncNodeSessionRegistry {

	static Logger LOGGER = LoggerFactory.getLogger(SyncNodeSessionRegistry.class);
	
	private ConcurrentMap<String, WebSocketSession> syncNodeMap = new ConcurrentHashMap<String, WebSocketSession>();
	
	public void addNodeSession(String agentId, WebSocketSession session) {
		
		LOGGER.debug("add node session: {}" , session.getPrincipal().getName());
		syncNodeMap.put(agentId, session);
	}
	
	public WebSocketSession getNodeSession(String agentId) {
		
		return syncNodeMap.get(agentId);
	}
	
	public WebSocketSession removeNodeSession(String agentId) {
		
		LOGGER.debug("remove node session: {}" , agentId);
		return syncNodeMap.remove(agentId);
	}
	
	public String show() {
		return syncNodeMap.toString();
	}
}
