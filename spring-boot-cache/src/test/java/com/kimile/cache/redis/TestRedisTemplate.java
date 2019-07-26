package com.kimile.cache.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestRedisTemplate {
	
	@Autowired
	private StringRedisTemplateTest redisTemplateTest;
	
	@Test
	public void testAddCache() {
		String key = "key111";
		if (redisTemplateTest.isExistCache(key)) {
			System.out.println("存在key值-----------------" + key);
			String cache = redisTemplateTest.getCache(key);
			System.out.println("-----------------" + key + "存储的数据为" + cache);
			redisTemplateTest.delCache(key);
			System.out.println("删除成功key值-----------------" + key);
		}
		redisTemplateTest.addCache(key, "value111");
		System.out.println("添加成功key值-----------------" + key);
	}
	
	@Before
    public void init() {
        System.out.println("开始测试-----------------");
    }
 
    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
	
}
