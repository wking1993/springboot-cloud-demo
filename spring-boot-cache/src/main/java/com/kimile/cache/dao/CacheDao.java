package com.kimile.cache.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.kimile.cache.bean.Person;

@Repository
public class CacheDao {
	
//	@Cacheable(value = "get", key = "#id")
	@Cacheable(value = "get", keyGenerator = "keyGenerator")
	public Person get(String id) {
		Person person = new Person("first" + id, "last" + id);
		person.setId(id);
		System.out.println("============模拟从数据库中获取数据===============");
		return person;
	}
	
}
