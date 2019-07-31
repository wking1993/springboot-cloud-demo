package com.kimile.db.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kimile.db.mongodb.bean.Article;

@Service
public class DelMongoService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	//删除指定条件下的数据，删除时指定集合类
	public void deleteByCond() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		mongoTemplate.remove(query, Article.class);
	}
	
	//删除指定条件下的数据，删除时指定文档集合名称
	public void deleteByCond2() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		mongoTemplate.remove(query, "article_info");
	}
	
	//删除指定条件下的第一条数据
	public void deleteFirstByCond() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		Article article = mongoTemplate.findAndRemove(query, Article.class);
		System.out.println(article.getAuthor());
	}
	
	//删除指定条件下的所有数据
	public void deleteAllByCond() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		List<Article> articles = mongoTemplate.findAllAndRemove(query, Article.class);
		System.out.println("=========articles.size()==========" + articles.size());
	}
	
	//删除集合，可以传实体类，也可以传集合名称
	public void deleteCollection() {
		mongoTemplate.dropCollection(Article.class);
//		mongoTemplate.dropCollection("article_info");
	}
	
	//删除数据库
	public void deleteDatabase() {
		mongoTemplate.getDb().dropDatabase();
	}
	
}
