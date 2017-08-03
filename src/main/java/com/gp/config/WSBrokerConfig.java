package com.gp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import com.gp.sync.web.socket.AgentSessionRegistry;
import com.gp.sync.web.socket.HandlerDecorator;
import com.gp.sync.web.socket.HandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@ComponentScan(basePackages = { 
		"com.gp.sync.web"
 })
public class WSBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {
//public class WebSocketBrokerConfigurer extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    //protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/hello")
        		.setHandshakeHandler(userHandshakeHandler())
        		//.addInterceptors(new HttpSessionHandshakeInterceptor())
        		.setAllowedOrigins("*");
    }
    
    @Override
	public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
		registration.addDecoratorFactory(handlerDecoratorFactory());
		registration.setMessageSizeLimit(256 * 1024);
		super.configureWebSocketTransport(registration);
	}
    
   // @Override
    //public void configureClientInboundChannel(ChannelRegistration registration) {
      //registration.setInterceptors(new ChannelIntercepter());
    //}
	
    @Bean
    public WebSocketHandlerDecoratorFactory handlerDecoratorFactory(){
    		return new WebSocketHandlerDecoratorFactory() {
			@Override
			public WebSocketHandler decorate(final WebSocketHandler handler) {
				return new HandlerDecorator(handler);
			}
		};
    }
    
    @Bean
    public HandshakeHandler userHandshakeHandler(){
        return new HandshakeHandler();
    }
}
