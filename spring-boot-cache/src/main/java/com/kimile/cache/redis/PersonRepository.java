package com.kimile.cache.redis;

import org.springframework.data.repository.CrudRepository;

import com.kimile.cache.bean.Person;

public interface PersonRepository extends CrudRepository<Person, String> {
	
}
