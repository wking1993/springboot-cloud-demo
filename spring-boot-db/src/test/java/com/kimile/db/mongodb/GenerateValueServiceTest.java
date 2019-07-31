package com.kimile.db.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kimile.db.mongodb.service.GenerateValueService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GenerateValueServiceTest {
	
	@Autowired
	private GenerateValueService service;
	
	@Test
	public void testInsertStudents() {
		service.insertStudent();
	}
	
}
