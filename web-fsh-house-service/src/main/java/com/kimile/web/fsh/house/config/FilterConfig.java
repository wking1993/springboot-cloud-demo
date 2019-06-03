package com.kimile.web.fsh.house.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kimile.web.fsh.house.common.HttpBasicAuthorizeFilter;

/**
 * 再服务中注册HttpBasicAuthorizeFilter的拦截器，并对指定的urlPatterns请求进行拦截
 */
@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		HttpBasicAuthorizeFilter httpBasicFilter = new HttpBasicAuthorizeFilter();
		registrationBean.setFilter(httpBasicFilter);
		List<String> urlPatterns = new ArrayList<>();
		urlPatterns.add("/*");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
	
}
