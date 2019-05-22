package com.kimile.demo.hystrix;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class MyHystrixCommand extends HystrixCommand<String> {

	private final String name;
	
	protected MyHystrixCommand(String name) {
//		super(HystrixCommandGroupKey.Factory.asKey("MyGroup"));
		//信号量策略配置
		/*super(HystrixCommand.Setter
			.withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyGroup"))
			.andCommandPropertiesDefaults(
				HystrixCommandProperties.Setter()
					.withExecutionIsolationStrategy(
						HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE
					)
				)
		);*/
		//线程隔离配置策略
		super(HystrixCommand.Setter.withGroupKey(
			HystrixCommandGroupKey.Factory.asKey("MyGroup"))
			.andCommandPropertiesDefaults(
				HystrixCommandProperties.Setter()
					.withExecutionIsolationStrategy(
						HystrixCommandProperties.ExecutionIsolationStrategy.THREAD
				)
			).andThreadPoolPropertiesDefaults(
				HystrixThreadPoolProperties.Setter()
					.withCoreSize(10)
					.withMaxQueueSize(100)
					.withMaximumSize(100)
			)
		);
		this.name = name;
	}

	@Override
	protected String run() {
		/*try {
			Thread.sleep(1000*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		System.err.println("get data");
		return this.name + " : " + Thread.currentThread().getName();
	}
	
	@Override
	protected String getFallback() {
		return "失败了";
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		//同步调用
		String result = new MyHystrixCommand("test-Hystrix").execute();
		System.out.println(result);
		//异步调用
		Future<String> future = new MyHystrixCommand("test-Hystrix").queue();
		System.out.println(future.get());
		context.shutdown();
	}
	
	//配置结果缓存
	@Override
	protected String getCacheKey() {
		return String.valueOf(this.name);
	}
}
