package com.gp.sync.web.socket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.web.socket.WebSocketSession;

public class AgentSessionRegistry {

	private static ConcurrentMap<String, WebSocketSession> agentMap = new ConcurrentHashMap<String, WebSocketSession>();
	
	public static void addSession(String agentId, WebSocketSession session) {
		agentMap.put(agentId, session);
	} 
	
	public static WebSocketSession getSession(String agentId) {
		
		return agentMap.get(agentId);
	}
	
	public static WebSocketSession removeSession(String agentId) {
		
		return agentMap.remove(agentId);
	}
	
	public String show() {
		return agentMap.toString();
	}
}
