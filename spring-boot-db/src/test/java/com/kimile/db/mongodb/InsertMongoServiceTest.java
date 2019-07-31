package com.kimile.db.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kimile.db.mongodb.service.InsertMongoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class InsertMongoServiceTest {
	
	@Autowired
	private InsertMongoService insertMongoService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void testSave() {
		insertMongoService.addArticle();
	}
	
	@Test
	public void testInsert() {
		insertMongoService.addArticles();
	}
	
	@Test
	public void testGetIndexes() {
		mongoTemplate.getCollection("person").getIndexInfo().forEach(index -> {
			System.out.println(index);
		});
//		mongoTemplate.getCollection("person").getIndexInfo().forEach(new Consumer<DBObject>() {
//			@Override
//			public void accept(DBObject index) {
//				System.out.println(index);
//			}
//		});
	}
	
}
