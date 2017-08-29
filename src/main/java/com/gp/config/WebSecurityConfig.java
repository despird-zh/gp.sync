package com.gp.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.gp.sync.web.SyncAuthenFilter;
import com.gp.sync.web.JwtAuthenEntryPoint;
import com.gp.sync.web.JwtAuthenProvider;
import com.gp.sync.web.SyncAuthenFailureHandler;
import com.gp.sync.web.SyncAuthenSuccessHandler;
import com.gp.sync.web.UserPasswordAuthenProvider;
import com.gp.web.servlet.ServiceTokenFilter;
import com.gp.web.servlet.UrlMatcher;

@Configuration
@Order(3)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   
	@Override
 	public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
        		.antMatchers("/css/**","/js/**", "/images/**", "**/favicon.ico");
    }
	
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
        // This is not for websocket authorization, and this should most likely not be altered.
		http.httpBasic().disable()
			//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		//.and()
			//.exceptionHandling().authenticationEntryPoint(tokenAuthenEntryPoint())
		//.and()
			.csrf().disable()
			.authorizeRequests()
            .antMatchers("/", "/home", "/gpapi/**").permitAll()
            .anyRequest().authenticated()
        .and()
        		.formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/home")
            .successHandler(new SyncAuthenSuccessHandler())
            .loginProcessingUrl("/authen_form")
            .permitAll()
            .and()
        .logout()
        		.logoutUrl("/logout")
        		.logoutSuccessUrl( "/home" )
            .permitAll();

//        		.formLogin()//
//                .loginPage( "/login" )//
//                .successHandler( successHandler( ) )//
//                .failureUrl( "/login?error" ).permitAll( ).and( )
//            .logout( )//
//                .logoutUrl( "/logout" ).logoutSuccessUrl( "/login?logout" ).permitAll( );//
//                .and( ).rememberMe( ).key( "sync_key" ).tokenValiditySeconds( 2419200 ); // remember me for 2 weeks

       http.addFilterBefore( authenTokenFilter(), UsernamePasswordAuthenticationFilter.class );
    }
	
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
        auth.authenticationProvider(userPasswordAuthenProvider()).
        		authenticationProvider(tokenAuthenProvider());
    }
    
    @Bean
    AuthenticationProvider userPasswordAuthenProvider() {
    		
    		return new UserPasswordAuthenProvider();
    }
    
    @Bean
    AuthenticationProvider tokenAuthenProvider() {
    		
    		return new JwtAuthenProvider();
    }
    
    @Bean
    public ServiceTokenFilter authenTokenFilter() throws Exception {
    		
    		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
    		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");
		config.addAllowedHeader(ServiceTokenFilter.AUTH_HEADER);
		config.addAllowedHeader("content-type");// required, otherwise the preflight not work
		config.addAllowedMethod("*");
		source.registerCorsConfiguration( ServiceTokenFilter.FILTER_PREFIX + "/**", config);
		
		ServiceTokenFilter tokenFilter = new ServiceTokenFilter(source);
		
		AuthenUrlMatcher matcher = new AuthenUrlMatcher(ServiceTokenFilter.FILTER_PREFIX + "/**");
		tokenFilter.setUrlMatcher(matcher);
		
		return tokenFilter;
    }
    
    /**
     * Url Matcher it's used for ServiceTokenFilter which in charge of Jwt token authentication
     * 
     * @author gdiao
     * @version 0.1 2017-4-9
     **/
    public class AuthenUrlMatcher implements UrlMatcher{
    		
    		private RequestMatcher authRequestMatcher;
    		
    		public AuthenUrlMatcher(String filterProcessesUrl) {
    			authRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
    		}
    		
		@Override
		public boolean match(HttpServletRequest request) {
			
			return authRequestMatcher.matches(request);
		}
    	
    }
}