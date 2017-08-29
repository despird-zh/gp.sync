package com.gp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.gp.sync.CoreStarter;
import com.gp.web.DatabaseMessageSource;
import com.gp.web.PrincipalLocaleResolver;
import com.gp.web.servlet.ServiceTokenFilter;

@Configuration
@Order(2)
@ComponentScan(basePackages = { 
		"com.gp.web.service",
		"com.gp.sync.web.view" })
public class WebMVCConfigurer extends WebMvcConfigurerAdapter {

	/**
	 * The CoreStart listener, it starts the CoreEngine which detect and prepare the CoreInitializer via java serviceloader(SPI).
	 * assembly the initializer to sort the LifecycleHooker with priority. 
	 **/
//	@Bean 
//	ServletListenerRegistrationBean<CoreStarter> coreStarterListener(){
//		ServletListenerRegistrationBean<CoreStarter> listenerReg = new ServletListenerRegistrationBean<CoreStarter>();
//		
//		listenerReg.setListener(new CoreStarter());
//		return listenerReg;
//	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	/**
	 * Create locale resolver to extract locale from request.
	 **/
	@Bean
	public LocaleResolver localeResolver() {
		return new PrincipalLocaleResolver();
	}

	/**
	 * Create the message source to inject it into Controller.
	 **/
	@Bean
	public MessageSource messageSource() {

		DatabaseMessageSource source = new DatabaseMessageSource();
		return source;
	}

}