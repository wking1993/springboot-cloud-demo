package com.kimile.db.mongodb.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.kimile.db.mongodb.annotation.GeneratedValue;

@Document
public class Student {

	@GeneratedValue
	@Id
	private long id;
	
	@Field("name")
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
