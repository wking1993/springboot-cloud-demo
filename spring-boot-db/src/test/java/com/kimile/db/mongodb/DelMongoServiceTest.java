package com.kimile.db.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kimile.db.mongodb.service.DelMongoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DelMongoServiceTest {
	
	@Autowired
	private DelMongoService delMongoService;
	
	@Test
	public void testDelelteByCond() {
		delMongoService.deleteByCond();
	}
	
	@Test
	public void testDelelteByCond2() {
		delMongoService.deleteByCond2();
	}
	
	@Test
	public void testDeleteFirstByCond() {
		delMongoService.deleteFirstByCond();
	}
	
	@Test
	public void testDeleteAllByCond() {
		delMongoService.deleteAllByCond();
	}
	
	@Test
	public void testDeleteCollection() {
		delMongoService.deleteCollection();
	}
	
	@Test
	public void testDeleteDatabase() {
		delMongoService.deleteDatabase();
	}
	
}
