package com.gp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.gp.sync.web.TokenAuthenFilter;
import com.gp.sync.web.TokenAuthenSuccessHandler;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
        // This is not for websocket authorization, and this should most likely not be altered.
        http.httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers("/stomp").permitAll()
            .anyRequest().denyAll();
        
        http.authorizeRequests( ).antMatchers( "/login*" ).permitAll( );
        http.authorizeRequests( ).antMatchers( "/register*" ).permitAll( );

        http.authorizeRequests( ).antMatchers( "/admin/**" ).hasAnyAuthority( "ROLE_ADMIN", "ROLE_USER" );//

        http.authorizeRequests( ).and( ).formLogin( )//
                .loginPage( "/login" )//
                .successHandler( successHandler( ) )//
                .failureUrl( "/login?error" ).permitAll( )//
                .and( ).logout( )//
                .logoutUrl( "/logout" ).logoutSuccessUrl( "/login?logout" ).permitAll( )//
                .and( ).rememberMe( ).key( "sync_key" ).tokenValiditySeconds( 2419200 ); // remember me for 2 weeks

        http.addFilterBefore( tokenAuthFilter( ), AnonymousAuthenticationFilter.class );
    }
	
	@Bean
	AuthenticationSuccessHandler successHandler() {
		
		return new TokenAuthenSuccessHandler();
	};
	
	@Bean
	AbstractAuthenticationProcessingFilter tokenAuthFilter() {
		
		AbstractAuthenticationProcessingFilter tokenFilter = new TokenAuthenFilter();
		tokenFilter.setAuthenticationSuccessHandler(successHandler());
		return tokenFilter;
	}
}