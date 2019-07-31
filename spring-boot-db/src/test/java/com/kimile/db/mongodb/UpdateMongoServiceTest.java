package com.kimile.db.mongodb;

import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kimile.db.mongodb.service.UpdateMongoService;
import com.mongodb.DBObject;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UpdateMongoServiceTest {
	
	@Autowired
	private UpdateMongoService updateMongoService;
	
	@Test
	public void testUpdateByCond() {
		updateMongoService.updateByCond();
	}
	
	@Test
	public void testUpdateAllByCond() {
		updateMongoService.updateAllByCond();
	}
	
	@Test
	public void testUpdateOrInsertByCond() {
		updateMongoService.updateOrInsertByCond();
	}
	
	@Test
	public void testUpdateOrInsertField() {
		updateMongoService.updateOrInsertField();
	}
	
	@Test
	public void testIncValueByCond() {
		updateMongoService.incValueByCond();
	}
	
	@Test
	public void testRenameFieldByCond() {
		updateMongoService.renameFieldByCond();
	}
	
	@Test
	public void testUnsetFieldByCond() {
		updateMongoService.unsetFieldByCond();
	}
	
	@Test
	public void testPullValueByCond() {
		updateMongoService.pullValueByCond();
	}
	
}
