package com.kimile.cache.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kimile.cache.bean.Person;
import com.kimile.cache.dao.CacheDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestRedisCacheDao {
	
	@Autowired
	private CacheDao cacheDao;
	
	@Test
	public void testGet() {
		Person person = cacheDao.get("321");
		System.out.println(person.getId() + "---" + person.getFirstname() + "---" + person.getLastname());
		Person person1 = cacheDao.get("321");
		System.out.println(person1.getId() + "---" + person1.getFirstname() + "---" + person1.getLastname());
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
