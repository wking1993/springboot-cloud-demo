package com.kimile.cache.config;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

/**
 * 自定义Manger，可以在注解中加上具体的缓存时间，不设置则使用全局的<br>
 * 在name后用##分割，然后加上过期的时间，单位秒
 * <pre>@Cacheable (value = "GoodsService.queryGoods##60" , keyGenerator = "wiselyKeyGenerator")</pre>
 * @author wyk
 */
public class CustomizedRedisCacheManager extends RedisCacheManager {

	public CustomizedRedisCacheManager(RedisOperations redisOperations) {
		super(redisOperations);
	}
	
	/**
	 * @param name 就是@Cacheable注解中的value属性
	 */
	@Override
	public Cache getCache(String name) {
		String[] cacheParams = name.split("##");
		String cacheName = cacheParams[0];
		long expirationTime = this.computeExpiration(cacheName);
		if (cacheParams.length > 1) {
			expirationTime = Long.parseLong(cacheParams[1]);
			this.setDefaultExpiration(expirationTime);
		}
		Cache cache = super.getCache(cacheName);
		return cache;
	}
	
}
