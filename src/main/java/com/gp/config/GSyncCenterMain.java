package com.gp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GSyncCenterMain{

    /**
	 * The main entrance of application 
	 **/
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GSyncCenterMain.class);
		//app.addListeners(new AppContextListener());
        app.run( args);
    }

}
