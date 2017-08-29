package com.gp.sync.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.gp.common.GPrincipal;
import com.gp.web.util.ExWebUtils;

public class SyncAuthenSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	static Logger LOGGER = LoggerFactory.getLogger(SyncAuthenSuccessHandler.class);
	
	@Override
	protected void handle(HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
		
		LOGGER.debug("customize succ handler");
		
		Object detail = authentication.getDetails();
		if(detail != null && detail instanceof GPrincipal) {
			
			GPrincipal principal = (GPrincipal) detail;
			ExWebUtils.setPrincipal(request, principal);
		}
		
		super.handle(request, response, authentication);
	}

}
