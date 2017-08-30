package com.gp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import com.gp.sync.svc.impl.WebSocketAuthenService;
import com.gp.sync.web.socket.AuthChannelInterceptorAdapter;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@ComponentScan(basePackages = { 
		"com.gp.sync.web.socket"
 })
@Order(3)//Ordered.HIGHEST_PRECEDENCE + 99
public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Autowired
    private WebSocketAuthenService webSocketAuthenService;
	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/queue");
       
        config.setApplicationDestinationPrefixes("/app");//gpwsi
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp")// sync-center
        		.setAllowedOrigins("*");
    }
    
    @Override
	public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {

		registration.setMessageSizeLimit(256 * 1024);
		super.configureWebSocketTransport(registration);
	}
    
    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.setInterceptors(new AuthChannelInterceptorAdapter(this.webSocketAuthenService));
    }

}
