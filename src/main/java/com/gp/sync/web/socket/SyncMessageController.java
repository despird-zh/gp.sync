package com.gp.sync.web.socket;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gp.sync.SyncConstants;
import com.gp.sync.message.SyncPullMessage;
import com.gp.sync.message.SyncPushMessage;
import com.gp.sync.web.model.ChatMessage;
import com.gp.sync.web.model.Greeting;
import com.gp.sync.web.model.HelloMessage;

/**
 * Node side subscribe the /user/queue/sync-notify to receive the {@link SyncNotifyMessage}
 * 
 **/
@Controller
@MessageMapping("sync")
@RequestMapping(SyncConstants.SYNC_VIEW)
public class SyncMessageController {

	Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);
	
	@Autowired
	private SimpMessagingTemplate messaging;
	
	@Autowired
	private SyncNodeSessionRegistry nodeSessionRegistry;
	
	/**
	 * node send the SyncPushMessage, center server route the message to 
	 * other related nodes.
	 * message path: /app/node.sync-push
	 **/
	@MessageMapping("sync-push")
    public void handlePush(SyncPushMessage message, Principal principal) {
		
		//template.convertAndSendToUser( message.getTarget(), "/queue/chat",  greeting ); 
	}
	
	/**
	 * node subscribe to receive notification 
	 **/
	@RequestMapping("sync-pull")
	@ResponseBody
	public SyncPullMessage handleSpittle(HelloMessage message) { 
		
		return null;
	}
}
