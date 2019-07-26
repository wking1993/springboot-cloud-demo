package com.kimile.cache.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StringRedisTemplateTest {
	
	/**
	 * opsForValue:操作Key Value类型
	 * opsForHash:操作Hash类型
	 * opsForList:操作List类型
	 * opsForSet:操作Set类型
	 * opsForZSet:操作Zset类型
	 */
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 设置一个新的缓存，缓存时间为1小时
	 * @param key
	 * @param value
	 */
	public void addCache(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
	}
	
	/**
	 * 获取指定的缓存
	 * @param key
	 */
	public String getCache(String key) {
		String value = stringRedisTemplate.opsForValue().get(key);
		return value;
	}
	
	/**
	 * 删除指定的缓存
	 * @param key
	 */
	public void delCache(String key) {
		stringRedisTemplate.delete(key);
	}
	
	/**
	 * 判断一个缓存是否存在
	 * @param key
	 */
	public boolean isExistCache(String key) {
		boolean exists = stringRedisTemplate.hasKey(key);
		return exists;
	}

}
