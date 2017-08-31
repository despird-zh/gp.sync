package com.gp.sync.web.socket;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.gp.sync.message.SyncPushMessage;

@Controller
@MessageMapping("group")
public class ChatMessageController {

	Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);
	
	@Autowired
	private SimpMessagingTemplate messaging;
	
	@Autowired
	private SyncNodeSessionRegistry nodeSessionRegistry;
	
	/**
	 * node send the SyncPushMessage, center server route the message to 
	 * other related nodes.
	 **/
	@MessageMapping("chat-square.{baz}")
    public void handleGroupChat(SyncPushMessage message, Principal principal) {
		
		// get the group code from message
		String groupCode = "w001";
		messaging.convertAndSend( "/topic/group/" + groupCode,  "" ); 
	}
}
