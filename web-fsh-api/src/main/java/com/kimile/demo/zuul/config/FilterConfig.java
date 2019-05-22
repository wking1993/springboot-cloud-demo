package com.kimile.demo.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kimile.demo.zuul.zuulfilter.IpFilter;

@Configuration
public class FilterConfig {
	
	@Bean
	public IpFilter ipFilter() {
		return new IpFilter();
	}
	
}
