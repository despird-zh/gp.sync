package com.gp.sync.web.socket;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.gp.common.GPrincipal;
import com.gp.common.JwtPayload;
import com.gp.sync.SyncConstants;
import com.gp.util.JwtTokenUtils;

public class SyncHandshakeHandler extends DefaultHandshakeHandler {

	Logger LOGGER = LoggerFactory.getLogger(SyncHandshakeHandler.class);
		
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                    Map<String, Object> attributes) {
    		
    		GPrincipal gprincipal = parseFromURI(request.getURI());
    		if(null == gprincipal) {
    		
    			gprincipal = parseFromHeader(request.getHeaders());
    			
    			if(null == gprincipal) {
    				gprincipal = new GPrincipal("blind" );
    			}
    		}
    		
        return gprincipal;
    }

    private GPrincipal parseFromURI(URI websocketUri) {
    	
    		MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(websocketUri).build().getQueryParams();
		
		List<String> tokenParam = parameters.get(SyncConstants.WS_HEADER_TOKEN);
	    List<String> passParam = parameters.get(SyncConstants.WS_HEADER_PASSWORD);
	    List<String> loginParam = parameters.get(SyncConstants.WS_HEADER_USERNAME);
	    //List<String> audienceParam = parameters.get(SyncConstants.WS_HEADER_AUDIENCE);
	    
	    if(CollectionUtils.isNotEmpty(tokenParam)) {
	    		JwtPayload jwt = JwtTokenUtils.parsePayload(tokenParam.get(0));
			return new GPrincipal( jwt.getSubject() );
	    }
	    else if(CollectionUtils.isNotEmpty(passParam) && CollectionUtils.isNotEmpty(loginParam)) {
	    		return new GPrincipal( loginParam.get(0) );
	    }
	    
	    return null;
    }
    
    private GPrincipal parseFromHeader(HttpHeaders headers) {
    	
    		String mapHeaders = headers.toSingleValueMap().toString();
		if(LOGGER.isDebugEnabled()) {
			LOGGER.info("determineUser headers : {}", mapHeaders);
		}
		String login = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_USERNAME);
		String passcode = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_PASSWORD);
		String token = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_TOKEN);
		//String audience = headers.toSingleValueMap().get(SyncConstants.WS_HEADER_AUDIENCE);

	    
	    if(StringUtils.isNotEmpty(token)) {
	    		JwtPayload jwt = JwtTokenUtils.parsePayload(token);
			return new GPrincipal( jwt.getSubject() );
	    }
	    else if(StringUtils.isNotEmpty(passcode) && StringUtils.isNotEmpty(login)) {
	    		return new GPrincipal( login );
	    }
	    
	    return null;
	}
}
