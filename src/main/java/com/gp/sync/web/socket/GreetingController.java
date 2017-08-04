package com.gp.sync.web.socket;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gp.sync.web.model.Greeting;

@Controller
public class GreetingController {
	
	Logger log = LoggerFactory.getLogger(GreetingController.class);
	
	@Autowired
	private SimpMessagingTemplate template;
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        log.info("Received hello: {}", message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/test")
    @SendTo("/topic/greetings")
    public Greeting test(Message<?> message) throws Exception {
    		
    		byte[] bytes = (byte[]) message.getPayload();
    		String str = new String(bytes ,"UTF-8");
    		log.debug("msg: {} " , str);
    		
        return new Greeting("Hello, ss!");
    }
    
    @RequestMapping(path="/greetings", method=RequestMethod.POST)
    public void greet(String greeting) {
        String text = "[" + System.currentTimeMillis() + "]:" + greeting;
        this.template.convertAndSend("/topic/greetings", text);
    }
}
