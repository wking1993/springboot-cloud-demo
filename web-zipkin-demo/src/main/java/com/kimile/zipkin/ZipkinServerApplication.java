package com.kimile.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
//使用http方式传输sleuth链路数据时使用@EnableZipkinServer注解
//@EnableZipkinServer
//通过RabbitMQ方式传输sleuth传输链路数据，使用@EnableZipkinStreamServer注解
@EnableZipkinStreamServer
public class ZipkinServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ZipkinServerApplication.class, args);
	}
	
}
