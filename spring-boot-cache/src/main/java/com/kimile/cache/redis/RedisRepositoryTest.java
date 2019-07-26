package com.kimile.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kimile.cache.bean.Person;

@Repository
public class RedisRepositoryTest {
	
	@Autowired
	PersonRepository repository;
	
	public void basicCrudOperations() {
		long count1 = repository.count();
		Person person = new Person(""+count1, ""+count1);
		repository.save(person);
		Person findOne = repository.findOne(person.getId());
		System.out.println("====================从Redis中拿出来的数据" + findOne);
		long count = repository.count();
		System.out.println("====================Redis中数据个数" + count);
//		repository.delete(person);
//		repository.deleteAll();
	}
	
}
