package com.gp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

import com.gp.sync.AppContextListener;

@SpringBootApplication
@Import({ 
	RootConfigurer.class, 
	ServiceConfigurer.class,
	WSBrokerConfig.class
	})

public class GSyncApplication{

    /**
	 * The main entrance of application 
	 **/
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GSyncApplication.class);
		app.addListeners(new AppContextListener());
        app.run( args);
    }

}
