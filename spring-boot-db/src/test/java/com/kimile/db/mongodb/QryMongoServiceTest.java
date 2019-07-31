package com.kimile.db.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kimile.db.mongodb.service.QryMongoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class QryMongoServiceTest {
	
	@Autowired
	private QryMongoService qryMongoService;

	@Test
	public void testQueryAllByCond() {
		qryMongoService.queryAllByCond();
	}
	
	@Test
	public void testQueryFirstByCond() {
		qryMongoService.queryFirstByCond();
	}
	
	@Test
	public void testQueryAll() {
		qryMongoService.queryAll();
	}
	
	@Test
	public void testQueryCountByCond() {
		qryMongoService.queryCountByCond();
	}
	
	@Test
	public void testQueryById() {
		qryMongoService.queryById();
	}
	
	@Test
	public void testQueryAllInCond() {
		qryMongoService.queryAllInCond();
	}
	
	@Test
	public void testQueryAllNeCond() {
		qryMongoService.queryAllNeCond();
	}
	
	@Test
	public void testQueryAllLtCond() {
		qryMongoService.queryAllLtCond();
	}

	@Test
	public void testQueryAllGtCond() {
		qryMongoService.queryAllGtCond();
	}
	
	@Test
	public void testQueryAllRegexCond() {
		qryMongoService.queryAllRegexCond();
	}
	
	@Test
	public void testQueryAllByArray() {
		qryMongoService.queryAllByArray();
	}
	
}
