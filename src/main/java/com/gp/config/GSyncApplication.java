package com.gp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ 
	RootConfigurer.class, 
	RedisConfig.class,
	ServiceConfigurer.class,
	WebSocketBrokerConfig.class
	})

public class GSyncApplication{

    public static void main(String[] args) {
    		SpringApplication.run(GSyncApplication.class, args);
    }

}
