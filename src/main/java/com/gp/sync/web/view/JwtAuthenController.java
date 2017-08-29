package com.gp.sync.web.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gp.sync.SyncConstants;
import com.gp.sync.web.service.AuthController;
import com.gp.web.ActionResult;
import com.gp.web.BaseController;

@Controller
@RequestMapping(SyncConstants.SYNC_VIEW)
public class JwtAuthenController extends BaseController{

	static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	
	@RequestMapping(
		    value = "/authen_token")
	public ModelAndView authenticate () throws Exception {
		
		ModelAndView mav = super.getJsonModelView();
		
		ActionResult result = ActionResult.success("success fetch the token");
		
		result.setData("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ");
		
		return mav.addAllObjects(result.asMap());
	}
	
	@RequestMapping(
		    value = "/authen_fail")
	public ModelAndView authenFail () throws Exception {
		
		ModelAndView mav = super.getJsonModelView();
		
		ActionResult result = ActionResult.failure("fail fetch the token");
		
		result.setData("__blank_token");
		
		return mav.addAllObjects(result.asMap());
	}
}
