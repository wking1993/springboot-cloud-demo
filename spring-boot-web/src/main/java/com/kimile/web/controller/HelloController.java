package com.kimile.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kimile.web.configs.MyConfig;

@RestController
public class HelloController {
	
	//注入对象
	@Autowired
	private Environment env;
	
	//注入配置
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private MyConfig myConfig;
	
	/*@GetMapping("/hello")
	public String hello() {
		String port = env.getProperty("server.port");
		return port;
	}*/
	
	/*@GetMapping("/hello")
	public String hello() {
		return port;
	}*/
	
	@GetMapping("/hello")
	public String hello() {
		return myConfig.getName();
	}
	
}
