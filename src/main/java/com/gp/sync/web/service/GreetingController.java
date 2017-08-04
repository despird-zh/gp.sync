package com.gp.sync.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import com.gp.sync.web.model.Greeting;
import com.gp.sync.web.socket.HelloMessage;

@Controller
public class GreetingController {
	
	Logger log = LoggerFactory.getLogger(GreetingController.class);
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        log.info("Received hello: {}", message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/test")
    @SendTo("/topic/greetings")
    public Greeting test(Message message) throws Exception {
    		log.debug("msg: {} " , message);
        return new Greeting("Hello, ss!");
    }
}
