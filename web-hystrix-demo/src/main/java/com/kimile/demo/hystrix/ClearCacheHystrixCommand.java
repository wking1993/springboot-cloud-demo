package com.kimile.demo.hystrix;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class ClearCacheHystrixCommand extends HystrixCommand<String> {
	
	private final String name;
	
	private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("MyKey");
	
	protected ClearCacheHystrixCommand(String name) {
		super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyGroup"))
			.andCommandKey(GETTER_KEY)
		);
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		System.err.println("get data");
		return this.name + " : " + Thread.currentThread().getName();
	}
	
	public static void flushCache(String name) {
		HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(name);
	}
	
	@Override
	protected String getCacheKey() {
		return String.valueOf(this.name);
	}
	
	@Override
	protected String getFallback() {
		return "失败了";
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		//同步调用
		String result = new ClearCacheHystrixCommand("test-Hystrix").execute();
		System.out.println(result);
		ClearCacheHystrixCommand.flushCache("test-Hystrix");
		//异步调用
		Future<String> future = new MyHystrixCommand("test-Hystrix").queue();
		System.out.println(future.get());
		context.shutdown();
	}
	
}
