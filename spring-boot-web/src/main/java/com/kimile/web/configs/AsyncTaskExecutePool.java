package com.kimile.web.configs;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncTaskExecutePool implements AsyncConfigurer {
	
	private Logger logger = LoggerFactory.getLogger(AsyncTaskExecutePool.class);
	
	@Autowired
	private TaskThreadPoolConfig config;
	
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(config.getCorePoolSize());
		executor.setMaxPoolSize(config.getMaxPoolSize());
		executor.setQueueCapacity(config.getQueueCapacity());
		executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
		executor.setThreadNamePrefix(config.getThreadNamePrefix());
		/*
		 * 线程池配置的拒绝策略。
		 * 当我们的线程数量高于线程池的处理速度时，任务会被缓存到本地的队列中；
		 * 队列也是有大小的，如果超过了大小，则需要有拒绝的策略，不然会造成内存溢出的问题，目前支持2种策略：
		 * 1）AbortPolicy：直接抛出java.util.concurrent.RejectedExecutionException异常
		 * 2）CallerRunsPolicy：主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，这样可以有效地降低向线程池内添加任务的速度。
		 */
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		//异步任务重异常处理
		return new AsyncUncaughtExceptionHandler() {
			
			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				logger.error("============================="
						+ ex.getMessage() + "=======================", ex);
				logger.error("exception method:" + method.getName());
			}
		};
	}

}
