package com.kimile.web.fsh.house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;

@RestController
@RequestMapping("/house")
public class ConfigController {
	
	//在Eureka的服务端可以按照路径访问到注册了的客户端服务信息，在Server客户端并不能通过路径获取到具体信息
	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	private DiscoveryClient DiscoveryClient;
	
	@GetMapping("/infosByEC")
	public Object serviceUrlByEC() {
		return eurekaClient.getInstancesByVipAddress("fsh-substitution", false);
	}
	
	@GetMapping("/infosByDC")
	public Object serviceUrlByDC() {
		return DiscoveryClient.getInstances("fsh-house");
	}
	
}
