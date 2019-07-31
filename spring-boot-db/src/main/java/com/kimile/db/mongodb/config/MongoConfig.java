package com.kimile.db.mongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kimile.db.mongodb.listener.SaveMongoEventListener;

@Configuration
public class MongoConfig {
	
	@Bean
	public SaveMongoEventListener saveMongoEventListener() {
		return new SaveMongoEventListener();
	}
	
}
