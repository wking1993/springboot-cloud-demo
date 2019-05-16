package com.kimile.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kimile.web.service.LogService;

@RestController
public class AsyncTestController {
	
	@Autowired
	private LogService logService;
	
	@GetMapping("/printLog")
	public String printLog() {
		logService.saveLog();
		return "return end!";
	}
}
