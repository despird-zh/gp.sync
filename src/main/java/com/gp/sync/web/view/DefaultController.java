package com.gp.sync.web.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gp.sync.web.service.AuthController;
import com.gp.web.ActionResult;
import com.gp.web.BaseController;

@Controller
public class DefaultController extends BaseController{

	static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@RequestMapping({ "/", "/index", "/home" })
	public ModelAndView index () throws Exception {
		
		ModelAndView mav = super.getJspModelView("home");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mav.addObject("user", auth.getName());
		return mav;
	}
	
	@RequestMapping(
		    value = "/login")
	public String login () throws Exception {
		
		return "login";
	}
	
	@RequestMapping(
		    value = "/hello")
	public String hello () throws Exception {
		
		return "hello";
	}
	
	@RequestMapping(
		    value = "/authenticate")
	public ModelAndView authenticate () throws Exception {
		
		ModelAndView mav = super.getJsonModelView();
		
		ActionResult result = ActionResult.success("success fetch the token");
		
		result.setData("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
		
		return mav.addAllObjects(result.asMap());
	}
}
