package com.gp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.DispatcherServlet;

import com.gp.core.AppContextHelper;
import com.gp.sync.AppContextListener;
import com.gp.sync.web.socket.AgentSessionRegistry;
import com.gp.web.servlet.ServiceFilter;

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
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");
		config.addAllowedHeader(ServiceFilter.AUTH_HEADER);
		config.addAllowedHeader("content-type");// required, otherwise the preflight not work
		config.addAllowedMethod("*");
		source.registerCorsConfiguration( ServiceFilter.FILTER_PREFIX + "/**", config);
        
		FilterRegistrationBean bean = new FilterRegistrationBean(new ServiceFilter(source));
		
		List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add(ServiceFilter.FILTER_PREFIX + "/*");
        
        bean.setUrlPatterns(urlPatterns);
		bean.setOrder(2);
		
		return bean;
	}
	
	@Bean
	public DispatcherServlet dispatcherServlet() {

		 // Create ApplicationContext
        AnnotationConfigWebApplicationContext webMvcContext = new AnnotationConfigWebApplicationContext();
        webMvcContext.register(WebMVCConfigurer.class);

	    DispatcherServlet servlet=new DispatcherServlet(webMvcContext);
 
	    return  servlet;
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
