package com.kimile.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**")
			.permitAll();
		http.authorizeRequests().antMatchers("/**").authenticated();
		http.httpBasic();
	}
	
}
