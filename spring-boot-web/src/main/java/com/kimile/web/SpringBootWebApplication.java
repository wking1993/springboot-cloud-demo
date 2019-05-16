package com.kimile.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import com.kimile.web.utils.StartCommand;

@EnableAsync //全局启用异步调用，具体异步方法上的@Async注解起作用
@SpringBootApplication
public class SpringBootWebApplication {

	public static void main(String[] args) {
		//生成一个未被使用的port端口号，并加入到配置文件中
		new StartCommand(args);
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

}
