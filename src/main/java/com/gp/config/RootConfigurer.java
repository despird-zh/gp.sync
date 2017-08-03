package com.gp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

import com.gp.sync.web.socket.AgentSessionRegistry;

/**
 *
 */
@Configuration
@ImportResource({
		"classpath:/gpress-datasource.xml"
	})
@ComponentScan(basePackages = { 
		"com.gp.sync.svc"
 })
public class RootConfigurer {
	
	@Autowired(required=true)
	private Environment env;

	@Bean
    public AgentSessionRegistry webAgentSessionRegistry(){
        return new AgentSessionRegistry();
    }
}
