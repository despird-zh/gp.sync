package com.gp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.gp.core.AppContextHelper;
import com.gp.sync.AppContextListener;
import com.gp.sync.web.socket.AgentSessionRegistry;

/**
 *
 */
@Configuration
@ImportResource({
		"classpath:/gpress-datasource.xml"
	})
@ComponentScan(basePackages = { 
		"com.gp.core",
		"com.gp.sync.svc"
 })
public class RootConfigurer {
	
	@Autowired(required=true)
	private Environment env;

	@Bean
    public AgentSessionRegistry webAgentSessionRegistry(){
        return new AgentSessionRegistry();
    }
	
	@Bean
	public AppContextListener appContextListener() {
		return new AppContextListener();
	}
	
	@Bean
	@Order(1)
	public AppContextHelper appContextHelper() {
		return new AppContextHelper();
	}
	
    @Bean
    public JedisConnectionFactory connectionFactory() {
    	
    		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

    		connectionFactory.setHostName("127.0.0.1");
    		connectionFactory.setPort(6379);
    		return connectionFactory;
    }
    
    @Bean
	public RedisTemplate<String, Object> redisTemplate() {

    		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
    		redisTemplate.setConnectionFactory(connectionFactory());
    		redisTemplate.setKeySerializer(new StringRedisSerializer());
    		
    		return redisTemplate;
	}
}
