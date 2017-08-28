package com.gp.sync.web.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gp.sync.web.service.AuthController;

@Controller
public class DefaultController {

	static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@RequestMapping({ "/", "/index", "/home" })
	public String index () throws Exception {
		
		return "home";
	}
	
	@RequestMapping(
		    value = "/login")
	public String login () throws Exception {
		
		return "login";
	}
}
