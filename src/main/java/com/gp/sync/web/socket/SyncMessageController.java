package com.gp.sync.web.socket;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
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
 * Node side subscribe the /user/queue/sync.notify to receive the {@link SyncNotifyMessage} per node
 * Node side subscribe the /topic/sync.notify to receive the {@link SyncNotifyMessage} that sent globally
 * 
 * @author gdiao
 * 
 * @version 0.1 2016-8-3
 **/
@Controller
@RequestMapping(SyncConstants.SYNC_VIEW)
public class SyncMessageController {

	Logger LOGGER = LoggerFactory.getLogger(SyncMessageController.class);
	
	@Autowired
	private SimpMessagingTemplate messaging;
	
	@Autowired
	private SyncNodeSessionRegistry nodeSessionRegistry;
	
	/**
	 * node send the SyncPushMessage, center server route the message to 
	 * other related nodes.
	 * message path: /gpapp/sync.push
	 **/
	@MessageMapping("/sync.push")
    public void handlePush(Message<?> message, Principal principal) {
		
		byte[] payload = (byte[]) message.getPayload();
		String payloadStr = new String(payload, StandardCharsets.UTF_8);
		
		LOGGER.debug("Receive: {}", payloadStr);
		
		//template.convertAndSendToUser( message.getTarget(), "/queue/chat",  greeting ); 
	}
	
	/**
	 * node subscribe to receive notification 
	 **/
	@RequestMapping("/pull")
	@ResponseBody
	public SyncPullMessage handleSpittle(HelloMessage message) { 
		
		return null;
	}
}
