package com.gp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.gp.sync.web.SyncAuthenFilter;
import com.gp.sync.web.JWTAuthenProvider;
import com.gp.sync.web.TokenAuthenSuccessHandler;
import com.gp.sync.web.UserPasswordAuthenProvider;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   
	@Override
    public void init(WebSecurity web) {
        web.ignoring()
        		.antMatchers("/", "/index","/home")
        		.antMatchers("/css/**","/js/**", "/images/**", "**/favicon.ico");
    }
	
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
        // This is not for websocket authorization, and this should most likely not be altered.
        http.httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers("/stomp").permitAll();
           // .anyRequest().denyAll();
        
//        http.authorizeRequests( ).antMatchers( "/login*" ).permitAll( ).and()
//        		.authorizeRequests( ).antMatchers( "/", "/index","/home" ).permitAll( );

        //http.authorizeRequests( ).antMatchers( "/admin/**" ).hasAnyAuthority( "ROLE_ADMIN", "ROLE_USER" );//

        http	.formLogin( )//
                .loginPage( "/login" )//
                .successHandler( successHandler( ) )//
                .failureUrl( "/login?error" ).permitAll( ).and( )
            .logout( )//
                .logoutUrl( "/logout" ).logoutSuccessUrl( "/login?logout" ).permitAll( );//
//                .and( ).rememberMe( ).key( "sync_key" ).tokenValiditySeconds( 2419200 ); // remember me for 2 weeks

        http.addFilterBefore( tokenAuthFilter(), BasicAuthenticationFilter.class );
    }
	
	@Bean
	AuthenticationSuccessHandler successHandler() {
		
		return new TokenAuthenSuccessHandler();
	};
	
	@Bean
	AbstractAuthenticationProcessingFilter tokenAuthFilter() throws Exception {
		
		AbstractAuthenticationProcessingFilter tokenFilter = new SyncAuthenFilter(authenticationManager());
		tokenFilter.setAuthenticationSuccessHandler(successHandler());
		return tokenFilter;
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userPasswordAuthenProvider()).
                authenticationProvider(tokenAuthenProvider());
    }
    
    @Bean
    AuthenticationProvider userPasswordAuthenProvider() {
    		
    		return new UserPasswordAuthenProvider();
    }
    
    @Bean
    AuthenticationProvider tokenAuthenProvider() {
    		
    		return new JWTAuthenProvider();
    }
}