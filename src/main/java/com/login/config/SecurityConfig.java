package com.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	

	
	
	 @Override protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests() .antMatchers("/**").permitAll()
	  .anyRequest().authenticated() .and() .csrf().disable()
	  .headers().frameOptions().disable();
	  
	  }
	 
	 
	 
	 
	 
    
	
		
		  //@Override protected void configure(HttpSecurity http) throws Exception { //
		  //http.authorizeRequests() .anyRequest().permitAll() .and() .csrf().disable();
		  // Disable CSRF protection 
		  //http.authorizeRequests().anyRequest().permitAll().and().csrf().disable(); }
		 
}