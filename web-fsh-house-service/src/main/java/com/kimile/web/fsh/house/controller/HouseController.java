package com.kimile.web.fsh.house.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kimile.web.fsh.house.ov.HouseInfo;

@RestController
@RequestMapping("/house")
public class HouseController {
	
	@Value("${server.port}")
	private String serverPort;
	
	@Value(value="${house.waitTime}")
	private int waitTime;
	
	@GetMapping("/hello")
	public String hello() {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Hello" + serverPort;
	}
	
	@GetMapping("/data")
	public HouseInfo getData(@RequestParam("name") String name) {
		return new HouseInfo(1L, "上海", "虹口", "东体小区");
	}
	
	@GetMapping("/data/{name}")
	public String getData2(@PathVariable("name") String name) {
		return name;
	}
	
	@PostMapping("/save")
	public Long addData(@RequestBody HouseInfo houseInfo) {
		System.out.println(houseInfo.getName());
		return 1001L;
	}
}
