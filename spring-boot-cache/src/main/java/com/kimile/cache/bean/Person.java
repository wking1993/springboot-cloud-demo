package com.kimile.cache.bean;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("persons")
public class Person implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7338833744288719905L;
	
	String id;
	String firstname;
	String lastname;
	
	public Person() {
	}

	public Person(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
