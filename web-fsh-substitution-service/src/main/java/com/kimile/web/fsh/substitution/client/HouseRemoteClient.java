package com.kimile.web.fsh.substitution.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.kimile.web.fsh.substitution.configs.FeignConfiguration;

/*
 * 定义Feign客户端接口
 * @FeignClient注解用来标识当前是一个Feign的客户端，
 * value属性是对应的服务的名称
 * path属性是接口中URI统一的前缀
 */
@FeignClient(value = "fsh-house", path = "/house/house", configuration = FeignConfiguration.class)
public interface HouseRemoteClient {
	
	/*
	 * 此处配置的@GetMapping标签标识，调用此方法时，对应发送的服务请求的地址，
	 * 和上边配置的path、value组成访问对应服务的路径：fsh-house/house/house/hello
	 */
	@GetMapping("/hello")
	String hello();
	
}
