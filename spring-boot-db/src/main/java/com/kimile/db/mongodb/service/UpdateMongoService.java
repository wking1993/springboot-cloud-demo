package com.kimile.db.mongodb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kimile.db.mongodb.bean.Article;

@Service
public class UpdateMongoService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	//根据条件修改第一条
	public void updateByCond() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		Update update = Update.update("title", "MongoTemplate").set("visitCount", 10);
		mongoTemplate.updateFirst(query, update, Article.class);
	}
	
	//根据条件修改所有数据
	public void updateAllByCond() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		Update update = Update.update("title", "MongoTemplate").set("visitCount", 10);
		mongoTemplate.updateMulti(query, update, Article.class);
	}
	
	//特殊更新，如果没有符合条件的文档，就以这个条件和更新文档为基础，创建一个新的文档，如果找到匹配的文档就正常更新
	public void updateOrInsertByCond() {
		Query query = Query.query(Criteria.where("author").is("another"));
		Update update = Update.update("title", "MongoTemplate").set("visitCount", 10);
		mongoTemplate.upsert(query, update, Article.class);
	}
	
	//特殊更新，如果有符合条件的文档，但是用set方法更新的key不存在，则创建一个新的key
	public void updateOrInsertField() {
		Query query = Query.query(Criteria.where("author").is("another"));
		Update update = Update.update("title", "MongoTemplate").set("money", 100);
		mongoTemplate.updateMulti(query, update, Article.class);
	}
	
	//根据条件进行累加操作
	public void incValueByCond() {
		Query query = Query.query(Criteria.where("author").is("another"));
		Update update = Update.update("title", "MongoTemplate").inc("money", 100);
		mongoTemplate.updateMulti(query, update, Article.class);
	}
	
	//使用Update的rename方法来修改该条数据的key的名称
	public void renameFieldByCond() {
		Query query = Query.query(Criteria.where("author").is("another"));
		Update update = Update.update("title", "MongoTemplate").rename("visitCount", "vc");
		mongoTemplate.updateMulti(query, update, Article.class);
	}
	
	//使用Update的unset方法来删除该条数据的指定key
	public void unsetFieldByCond() {
		Query query = Query.query(Criteria.where("author").is("another"));
		Update update = Update.update("title", "MongoTemplate").unset("vc");
		mongoTemplate.updateMulti(query, update, Article.class);
	}
	
	//使用Update的pull方法来删除该条文档中一个数组中的值
	public void pullValueByCond() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		Update update = Update.update("title", "MongoTemplate").pull("tags", "java");
		mongoTemplate.updateMulti(query, update, Article.class);
	}
}
