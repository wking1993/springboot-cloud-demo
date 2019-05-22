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
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.metric.HystrixCommandStartStream;

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
	
	/**
	 * 使用Hystrix服务，指定依赖服务调用延迟或失败时调用的方法
	 * HystrixCommand中的配置：
	 * ☆hystrix.command.default.execution.isolation.strategy:用来配置指定隔离策略：
	 * 		THREAD：线程隔离，在单独的线程上执行，并发请求受线程池大小的控制
	 * 		SEMAPHORE：信号量隔离，在调用线程上执行，并发请求受信号量计数器的限制
	 * ☆hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds：配置超时时间，
	 * 	当HystrixCommand执行的时间超过该配置所设置的数值后，就会进入服务降级处理，单位是毫秒，默认为1000
	 * ☆hystrix.command.default.execution.timeout.enabled:配置用于确定是否启用
	 * 	hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds设置的超时时间，默认为true
	 * ☆hystrix.command.default.execution.isolation.thread.interruptOnTimeout:配置用于指定超时后是否中断，默认为true
	 * ☆hystrix.command.default.execution.isolation.thread.interruptOnCancel：配置用于指定被取消时，是否需要中断，默认为false
	 * ☆hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests：配置用于确定Hystrix使用信号量策略时的
	 * 	最大请求并发数
	 * ☆hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests：配置如果并发数到达该设定值，
	 * 	请求会被拒绝和抛出异常，且fallback不会被调用，默认值为10
	 * ☆hystrix.command.default.fallback.enabled：配置用于确定当执行失败或者请求拒绝时，是否会尝试调用fallback方法，默认为true
	 * ☆hystrix.command.default.circuitBreaker.enabled：配置用来跟踪circuit的健康性，如果未达标则让request短路，默认为true
	 * ☆hystrix.command.default.circuitBreaker.requestVolumeThreshold：配置设置一个rolling window内最小的请求数，
	 * 	如果设为20，那么当一个rolling window的时间内收到19个请求，即使19个请求都失败，也不会触发circuit break，默认为20
	 * ☆hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds：配置一个触发短路的时间值，
	 * 	值为5000时，触发circuit break后的5000毫秒内的request都会拒绝，也就是5000毫秒后关闭circuit，默认为5000
	 * ☆hystrix.command.default.circuitBreaker.errorThresholdPercentage：配置错误率阈值，
	 * 	当错误率超过此值时，所有请求都会触发fallback，默认为50
	 * ☆hystrix.command.default.circuitBreaker.forceOpen：如果配置为true，将强制打开熔断器，在这个状态下将拒绝所有请求，默认为false
	 * ☆hystrix.command.default.metrics.rollingStats.timeInMilliseconds：配置统计的时间窗口值，单位为毫秒。
	 * 	circuit break的打开会根据1个rolling window的统计来计算，若将其设为10000毫秒，则rolling window会被分成多个buckets，每个buckets
	 * 	包含success、failure、timeout、rejection的次数的统计信息，默认为10000毫秒
	 * ☆hystrix.command.default.metrics.rollingStats.numBuckets：配置一个rolling window被划分的数量，过numBuckets=10、
	 * 	rolling window=10000,那么一个bucket的时间就是1秒，必须符合rolling window%numBuckets==0，默认值为10
	 * ☆hystrix.command.default.metrics.rollingPercentile.enabled：是否开启指标的计算和跟踪，默认为true
	 * ☆hystrix.command.default.metrics.rollingPercentile.timeInMilliseconds：设置rolling percentile window的时间，默认60000
	 * ☆hystrix.command.default.metrics.rollingPercentile.numBuckets：设置rolling percentile window的numberBuckets，默认为6
	 * ☆hystrix.command.default.metrics.rollingPercentile.bucketSize：如果bucketSize=100、window=10秒，若这10秒内有500次执行，
	 * 	只有最后100次执行会被统计到bucket里去，增加该值会增加内存的开销及排序的开销，默认为100
	 * ☆hystrix.command.default.metrics.healthSnapshot.intervalInMilliseconds：配置用来计算影像断路器状态的健康快照的间隔等待时间，
	 * 	默认为500毫秒
	 * ☆hystrix.command.default.requestCache.enabled：是否开启请求缓存功能，默认为true
	 * ☆hystrix.command.default.requestLog.enabled：配置是否记录日志到HystrixRequestLog，默认为true
	 * ☆hystrix.collapser.default.maxRequestInBatch：单词批处理的最大请求数，达到该数量触发批处理，默认为Integer.MAX_VALUE
	 * ☆hystrix.collapser.default.timerDelayInMilliseconds：触发批处理的延迟，延迟也可以为创建批处理的时间与该值的和，默认值为10毫秒
	 * ☆hystrix.collapser.default.requestCache.enabled：是否启用对HystrixCollapser.execute()和HystrixCollapser.queue()的请求缓存，默认为true
	 * ☆hystrix.threadpool.default.coreSize：并发执行的最大线程数，默认为10
	 * ☆hystrix.threadpool.default.maxQueueSize：BlockingQueue的最大队列数，当设为-1时，会使用SynchronousQueue；
	 * 	值为正数时，会使用LinkedBlockingQueue，该设置只会在初始化时有效，之后不能修改threadpool的queue size。默认为-1
	 * ☆hystrix.threadpool.default.queueSizeRejectionThreshold：即使没有达到maxQueueSize，但若达到queueSizeRejectionThreshold
	 * 	值后，请求也会被拒绝。因为maxQueueSize不能被动态修改，而queueSizeRejectionThreshold参数将允许我们动态设置该值；
	 * 	如果maxQueueSize == -1，该字段将不会起作用
	 * ☆hystrix.threadpool.default.keepAliveTimeMinutes：设置存活时间，单位为分钟。
	 * 	如果coreSize小于maximumSize，那么该属性控制一个线程从使用完成到被释放的时间，默认值为1分钟。
	 * ☆hystrix.threadpool.default.allowMaximumSizeToDivergeFromCoreSize：maximumSize的配置生效。那么该值可以等于或高于coreSize。
	 * 	设置coreSize小于maximumSize会创建一个线程池，该线程池可以支持maximumSize并发，但在相对不活动的期间将向系统返回线程，默认值为false
	 * ☆hystrix.threadpool.default.metrics.rollingStats.timeInMilliseconds：设置滚动时间窗的时间，单位为毫秒，默认为10000；
	 * ☆hystrix.threadpool.default.metrics.rollingStats.numBuckets：设置滚动时间窗划分桶的数量，默认值为10；
	 */
	@HystrixCommand(fallbackMethod = "defaultCallHello",
			commandProperties = {
					@HystrixProperty(
							name = "execution.isolation.strategy",
							value = "THREAD"
						)
			})
	@GetMapping("/callHelloH")
	public String callHelloH() {
		String result = houseRemoteClient.hello();
		System.out.println("调用结果：" + result);
		return result;
	}

	public String defaultCallHello() {
		return "fail!";
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
	
	@GetMapping("/{houseId}")
	public HouseInfo houseInfo(@PathVariable("houseId") Long houseId) {
		HouseInfo houseInfo = houseRemoteClient.houseInfo(houseId);
		return houseInfo;
	}
	
}
