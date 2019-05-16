package com.kimile.web.fsh.substitution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients //配置启用集成Feign组件，如果Feign接口定义和启动类不在同一包下，则需要通过basePackages属性进行指定
public class FshSubstitutionServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FshSubstitutionServiceApplication.class, args);
	}
	
}
