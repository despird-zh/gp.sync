package com.gp.sync.web.socket;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.gp.common.AccessPoint;
import com.gp.common.SystemOptions;
import com.gp.sync.SyncConstants;
import com.gp.sync.web.JwtAuthenToken;
import com.gp.sync.web.UserPasswordAuthenToken;
import com.gp.util.ConfigSettingUtils;

public class SyncHandshakeHandler extends DefaultHandshakeHandler {

	Logger LOGGER = LoggerFactory.getLogger(SyncHandshakeHandler.class);
	
	/**
	 * Here we not authenticate the request principal, just prepare the Principal object for
	 * further authentication and authorization in {@link AuthenChannelInterceptorAdapter } 
	 **/
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                    Map<String, Object> attributes) throws HandshakeFailureException {
    		
    		Authentication gprincipal = parseFromURI(request);
    		if(null == gprincipal) {
    		
    			gprincipal = parseFromHeader(request);
    		}
    		
    		if(null == gprincipal || "bad1".equals(gprincipal.getName())) {
    			
			throw new HandshakeFailureException("illegal user principal");
		}    		
    		
        return gprincipal;
    }

    /**
     * parse form the URI request parameters 
     **/
    private Authentication parseFromURI(ServerHttpRequest request) {
    	
    		URI websocketUri = request.getURI();
    		MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(websocketUri).build().getQueryParams();
		
		List<String> tokenParam = parameters.get(SyncConstants.WS_HEADER_TOKEN);
	    List<String> passParam = parameters.get(SyncConstants.WS_HEADER_PASSWORD);
	    List<String> loginParam = parameters.get(SyncConstants.WS_HEADER_USERNAME);

	    Authentication authen = null;
	    AccessPoint ap = getAccessPoint(request);
	    if(CollectionUtils.isNotEmpty(tokenParam)) {
			authen =  new JwtAuthenToken( tokenParam.get(0));
			((JwtAuthenToken) authen).setAccessPoint(ap);
	    }
	    else if(CollectionUtils.isNotEmpty(passParam) && CollectionUtils.isNotEmpty(loginParam)) {
	    		authen = new UserPasswordAuthenToken( loginParam.get(0), passParam.get(0) );
	    		((UserPasswordAuthenToken) authen).setAccessPoint(ap);
	    }
	    
	    return authen;
    }
    
    /**
     * parse the principal from the Http headers
     **/
    private Authentication parseFromHeader(ServerHttpRequest request) {
    		
    		HttpHeaders headers = request.getHeaders();
    		String mapHeaders = headers.toSingleValueMap().toString();
		if(LOGGER.isDebugEnabled()) {
			LOGGER.info("determineUser headers : {}", mapHeaders);
		}
		String login = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_USERNAME);
		String passcode = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_PASSWORD);
		String token = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_TOKEN);
		//String audience = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_AUDIENCE);
		Authentication authen = null;
		AccessPoint ap = getAccessPoint(request);
	    if(StringUtils.isNotEmpty(token)) {
	    		authen =   new JwtAuthenToken( token);
	    		((JwtAuthenToken) authen).setAccessPoint(ap);
	    }
	    else if(StringUtils.isNotEmpty(passcode) && StringUtils.isNotEmpty(login)) {
	    		authen =   new UserPasswordAuthenToken( login, passcode );
	    		((UserPasswordAuthenToken) authen).setAccessPoint(ap);
	    }
	    
	    return authen;
	}
        
    /**
     * Get the AccessPoint information from request
     **/
    private AccessPoint getAccessPoint(ServerHttpRequest  request){
		
		String client = request.getHeaders().getFirst("User-Agent");
		String host = request.getRemoteAddress().getHostName();
		String app = ConfigSettingUtils.getSystemOption(SystemOptions.SYSTEM_APP);
		String version = ConfigSettingUtils.getSystemOption(SystemOptions.SYSTEM_VERSION);
		
		AccessPoint ap = new AccessPoint(client, host, app, version);
		
		return ap;
	}
}
