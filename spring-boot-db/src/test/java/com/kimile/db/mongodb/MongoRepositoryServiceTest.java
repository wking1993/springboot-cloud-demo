package com.kimile.db.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kimile.db.mongodb.service.MongoRepositoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MongoRepositoryServiceTest {
	
	@Autowired
	private MongoRepositoryService service;
	
	@Test
	public void testFindAll() {
		service.findAll();
	}
	
	@Test
	public void testFindByAuthor() {
		service.findByAuthor();
	}
	
	@Test
	public void testFindByAuthorIgnoreCase() {
		service.findByAuthorIgnoreCase();
	}
	
	@Test
	public void testFindByAuthorOrderByVisitCountDesc() {
		service.findByAuthorOrderByVisitCountDesc();
	}
	
	@Test
	public void testFindByAuthorBySort() {
		service.findByAuthorBySort();
	}
	
	@Test
	public void testFindByPage() {
		service.findByPage();
	}
	
}
