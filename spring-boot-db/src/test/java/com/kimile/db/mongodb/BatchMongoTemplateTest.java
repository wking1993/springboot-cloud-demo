package com.kimile.db.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kimile.db.mongodb.batch.BatchMongoTemplate;
import com.kimile.db.mongodb.batch.BatchUpdateOptions;
import com.kimile.db.mongodb.bean.Article;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BatchMongoTemplateTest {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void testBatchUpdate() {
		List<BatchUpdateOptions> options = new ArrayList<BatchUpdateOptions>();
		options.add(
				new BatchUpdateOptions(Query.query(Criteria.where("author").is("wyk")), 
						Update.update("title", "批量更新"), true, true));
		options.add(
				new BatchUpdateOptions(Query.query(Criteria.where("author").is("another")), 
						Update.update("title", "批量更新"), true, true));
		int count = BatchMongoTemplate.batchUpdate(mongoTemplate, Article.class, options);
		System.out.println("受影响的行数为：" + count);
	}
	
}
