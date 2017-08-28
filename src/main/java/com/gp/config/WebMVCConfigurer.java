package com.gp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.gp.web.DatabaseMessageSource;
import com.gp.web.PrincipalLocaleResolver;

@Configuration
@ComponentScan(basePackages = { "com.gp.sync.web.service" })
public class WebMVCConfigurer extends WebMvcConfigurerAdapter {
	
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