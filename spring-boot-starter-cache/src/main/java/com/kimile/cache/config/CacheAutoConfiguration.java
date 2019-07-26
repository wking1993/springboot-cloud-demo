package com.kimile.cache.config;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimile.cache.util.ClassUtil;
import com.kimile.cache.util.JsonUtils;

public class CacheAutoConfiguration {
	
	private Logger logger = LoggerFactory.getLogger(CacheAutoConfiguration.class);
	
	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		CustomizedRedisCacheManager cacheManager = new CustomizedRedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(1000*60*60);
		return null;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
		setSerializer(template);
		template.afterPropertiesSet();
		return null;
	}
	
	public void setSerializer(RedisTemplate<String, Object> template) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setKeySerializer(new StringRedisSerializer());
	}
	
	/**
	 * Key自动生成类
	 * @return
	 */
	@Bean
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(ClassUtil.getShortClassName(target.getClass().getName()));
				sb.append(".");
				sb.append(method.getName());
				if (params != null && params.length > 0) {
					for (Object obj : params) {
						sb.append(".");
						sb.append(JsonUtils.toJson(obj).replaceAll("\"", ""));
					}
				}
				return sb.toString();
			}
		};
	}
	
	/**
	 * Redis数据异常操作，这里的处理：在日志中打印出异常信息，但是放行
	 * 保证redis服务器出现连接等问题的时候，不影响程序的正常运行，使得能够出问题时，不使用缓存，继续执行业务逻辑去查询DB
	 * @return
	 */
	@Bean
	public CacheErrorHandler errorHandler() {
		CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
			
			@Override
			public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
				logger.error("redis异常：key=[{}]", key, exception);
			}
			
			@Override
			public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
				logger.error("redis异常：key=[{}]", key, exception);
			}
			
			@Override
			public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
				logger.error("redis异常：key=[{}]", key, exception);
			}
			
			@Override
			public void handleCacheClearError(RuntimeException exception, Cache cache) {
				logger.error("redis异常：key=[{}]", exception);
			}
		};
		return cacheErrorHandler;
	}
}
