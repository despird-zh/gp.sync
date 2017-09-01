package com.gp.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import com.gp.sync.web.socket.AuthenChannelInterceptorAdapter;
import com.gp.sync.web.socket.SyncHandlerDecorator;
import com.gp.sync.web.socket.SyncHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@ComponentScan(basePackages = { 
	"com.gp.sync.web.socket"
 })
@Order(4)//Ordered.HIGHEST_PRECEDENCE + 99
public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	static Logger LOGGER = LoggerFactory.getLogger(WebSocketBrokerConfig.class);
	
	@Autowired
	public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
	     try {
			this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
		} catch (Exception e) {
			LOGGER.debug("Fail to assign the authenticationManager in socket broker config");
		}
	}

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/queue", "/exchange/");
        config.setApplicationDestinationPrefixes("/gpapp");//gpwsi
        /**
         * The Ant Path Matcher setting must ignore, because it affect the /user/bla/bla...
         * Message forwarding.
        	 * config.setPathMatcher(new AntPathMatcher("."));
         */
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gpcenter")//
        		.setHandshakeHandler(new SyncHandshakeHandler())
        		.setAllowedOrigins("*");
    }
    
    @Override
	public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
    		registration.addDecoratorFactory(handlerDecoratorFactory());
		registration.setMessageSizeLimit(256 * 1024);
		super.configureWebSocketTransport(registration);
	}

	@Bean
	public WebSocketHandlerDecoratorFactory handlerDecoratorFactory() {
		return new WebSocketHandlerDecoratorFactory() {
			@Override
			public WebSocketHandler decorate(final WebSocketHandler handler) {
				return new SyncHandlerDecorator(handler);
			}
		};
	}
	
	/**
	 *  prepare the authenticationMananger so as to inject it into {@link AuthenChannelInterceptorAdapter}
	 */
	private AuthenticationManager authenticationManager;
	
    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.setInterceptors(new AuthenChannelInterceptorAdapter(authenticationManager));
    }
}
