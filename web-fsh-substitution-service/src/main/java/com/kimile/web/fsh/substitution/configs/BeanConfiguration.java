package com.kimile.web.fsh.substitution.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.kimile.web.fsh.substitution.annotation.MyLoadBalanced;

@Configuration
public class BeanConfiguration {
	
	@Bean
	/*
	 * 为RestTemplate加上@LoadBalanced注解后，主要是为RestTemplate增加了拦截器：
	 * 在请求之前对请求的地址进行了替换，或者根据具体的负载均衡选择服务地址，然后再去调用
	 */
	@LoadBalanced //这个注解会自动构造LoadBalancerClient接口的实现类，并注册到Spring容器中
//	@MyLoadBalanced //自定义的LoadBalanced，对RestTemplate进行拦截器设置
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	/** 通过代码的方式来配置Ribbon */
//	@Bean
//	public MyIRule rule() {
//		return new MyIRule();
//	}
	
}
