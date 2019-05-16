package com.kimile.web.fsh.substitution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kimile.web.fsh.substitution.client.HouseRemoteClient;
import com.kimile.web.fsh.substitution.ov.HouseInfo;

@RestController
@RequestMapping("/substitution")
public class Substitution {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LoadBalancerClient loadBalancer;
	
	@Autowired
	private HouseRemoteClient houseRemoteClient;
	
	@GetMapping("/choose")
	public Object chooseUri() {
		ServiceInstance instance = loadBalancer.choose("fsh-house");
		return instance;
	}
	
	/**
	 * 直接调用服务接口
	 * 此时的RestTemplate获取方法上不需要配置@LoadBalanced注解，配置完@LoadBalanced注解后，此方法调用会失败
	 */
	@GetMapping("/callHello")
	public String callHello() {
		return restTemplate.getForObject("http://localhost:8081/house/hello", String.class);
	}
	
	/**
	 * 通过Eureka来消费接口
	 * 必须要在RestTemplate获取方法上配置@LoadBalanced注解
	 * 此时，不需要写固定的地址，而是写成服务的名称，这个名称也就是我们注册到Eureka中的名称，是属性文件中spring.application.name
	 */
	@GetMapping("/callHelloE")
	public String callHelloE() {
		String result = restTemplate.getForObject("http://fsh-house/house/house/hello", String.class);
		System.out.println("调用结果：" + result);
		return result;
	}
	
	/**
	 * 通过Eureka来消费接口，通过Feign来发送交易请求，而不是使用RestTemplate
	 */
	@GetMapping("/callHelloF")
	public String callHelloF() {
		String result = houseRemoteClient.hello();
		System.out.println("调用结果：" + result);
		return result;
	}
	
	/*@GetMapping("/data")
	public HouseInfo getData(@RequestParam("name") String name) {
		return restTemplate.getForObject("http://fsh-house/house/house/data?name=" + name, HouseInfo.class);
	}*/
	
	//使用ResponseEntity对象来获取数据
	@GetMapping("/data")
	public HouseInfo getData(@RequestParam("name") String name) {
		ResponseEntity<HouseInfo> resEntity = restTemplate.getForEntity("", HouseInfo.class);
		if (200 == resEntity.getStatusCodeValue()) {
			return resEntity.getBody();
		}
		return null;
	}
	
	@GetMapping("/data/{name}")
	public String getData2(@PathVariable("name") String name) {
		return restTemplate.getForObject("http://fsh-house/house/house/data/{name}", String.class, name);
	}
	
	@PostMapping("/add")
	public Long add() {
		HouseInfo houseInfo = new HouseInfo();
		houseInfo.setCity("上海");
		houseInfo.setRegion("虹口");
		houseInfo.setName("测试者");
		Long id = restTemplate.postForObject("http://fsh-house/house/house/save", houseInfo, Long.class);
		return id;
	}
	
}
