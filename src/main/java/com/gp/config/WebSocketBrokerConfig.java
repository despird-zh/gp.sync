package com.gp.config;


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
import org.springframework.util.AntPathMatcher;
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

	@Autowired
	public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
	     try {
			this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/queue");
        config.setApplicationDestinationPrefixes("/app");//gpwsi
        
        config.setPathMatcher(new AntPathMatcher(".")); 
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp")//
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
