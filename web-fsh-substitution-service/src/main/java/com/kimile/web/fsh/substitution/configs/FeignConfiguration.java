package com.kimile.web.fsh.substitution.configs;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.kimile.web.fsh.substitution.configs.jackson.JacksonDecoder;
import com.kimile.web.fsh.substitution.configs.jackson.JacksonEncoder;
import com.kimile.web.fsh.substitution.interceptor.FeignBasicAuthRequestInterceptor;

import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;

/* 配置Feign的相关信息 ，但要生效需要在定义Feign调用接口，即@FeignClient注解中指定使用配置类*/
@Configuration
public class FeignConfiguration {
	
	/*
	 * 配置Feign的日志输出等级，但想要输出对应的日志，需要在配置文件中设置Client的日志级别才行：logging.level.client类地址=级别
	 * NONE:不输出日志
	 * BASIC:只输出请求方法的URL和响应的状态码，以及接口执行的时间
	 * HEADERS:将BASIC中包含的信息和请求头信息输出
	 * FULL:输出完整的请求信息
	 */
	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
	
	/*
	 * 契约配置，配置使用默认契约之后，Spring的@FeignClient注解失效，定义的Client就无法使用，只能通过原生的Feign方法来定义Client
	 */
//	@Bean
	public Contract feignContract() {
		return new feign.Contract.Default();
	}
	
	/*
	 * 配置Feign中自带的Basic认证配置，可以在请求头中添加认证信息
	 */
//	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		return new BasicAuthRequestInterceptor("user", "password");
	}
	
	//使用自定义的认证配置
	@Bean
	public FeignBasicAuthRequestInterceptor feignBasicAuthRequestInterceptor() {
		return new FeignBasicAuthRequestInterceptor();
	}
	
	/*
	 * 通过Options可以配置连接超时时间（默认10*1000）和读取超时时间(默认60*1000)，单位都是ms
	 */
	@Bean
	public Request.Options options() {
		return new Request.Options(5000, 10000);
	}
	
	/* 配置解码器 */
//	@Bean
	public Decoder decoder() {
		return new JacksonDecoder();
	}
	
	/* 配置编码器 */
//	@Bean
	public Encoder encoder() {
		return new JacksonEncoder();
	}
	
	/*
	 * 通过代码的方式让Feign禁用Hystrix组件
	 */
//	@Bean
	@Scope("prototype")
	public Feign.Builder feignBuilder() {
		return Feign.builder();
	}
}
