package com.gp.sync.web.socket;

import java.security.Principal;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gp.sync.web.model.ChatMessage;
import com.gp.sync.web.model.Greeting;
import com.gp.sync.web.model.HelloMessage;

@Controller
@MessageMapping("test")
public class TestController {
	
	Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	SyncNodeSessionRegistry nodeSessionRegistry;
	
	/**
	 * Message be broadcast to all clients that subscribe the '/topic/greetings' 
	 **/
    @MessageMapping("sayhi")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message, Principal principal) throws Exception {
        log.info("Receive sayhi: {} - princ: {}", message.getName(), principal.getName());
        return new Greeting("Hi, " + message.getName() + "!");
    }

    @MessageMapping("sayhi.{username}")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message, Principal principal,@DestinationVariable("username") String username) throws Exception {
        log.info("Receive sayhi: {} - princ: {}", message.getName(), principal.getName());
        log.info("Receive sayhi: param is {} ", username);
        return new Greeting("Hi, prove the param in path" + message.getName() + "!");
    }
}
