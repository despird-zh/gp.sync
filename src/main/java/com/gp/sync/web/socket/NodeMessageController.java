package com.gp.sync.web.socket;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.gp.sync.message.SyncPushMessage;
import com.gp.sync.web.model.ChatMessage;

@Controller
@MessageMapping("/node")
public class NodeMessageController {

	Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);
	
	@Autowired
	private SimpMessagingTemplate messaging;
	
	@Autowired
	private SyncNodeSessionRegistry nodeSessionRegistry;
	
	@MessageMapping("/push")
    public void handleChat(SyncPushMessage message, Principal principal) {
		
	}
}
