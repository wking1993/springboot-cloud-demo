package com.kimile.web.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogService {
	
	@Async //需要在启动类上开启异步任务的执行，添加@EnableAsync，且这个方法只有在外部的类中调用才有效果，本类中调用不起作用
	public void saveLog() {
		int i = 0;
		while (i++ < 10) {
			System.err.println(Thread.currentThread().getName() + " : " + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
