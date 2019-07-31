package com.kimile.db.mongodb.service;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kimile.db.mongodb.bean.Article;

@Service
public class QryMongoService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	//查询符合条件的所有数据
	public void queryAllByCond() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		List<Article> articles = mongoTemplate.find(query, Article.class);
		System.out.println("=========articles.size()========" + articles.size());
	}
	
	//查询符合条件的第一条数据
	public void queryFirstByCond() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		Article article = mongoTemplate.findOne(query, Article.class);
		System.out.println("=========article.getAuthor()=============" + article.getAuthor());
	}
	
	//查询集合中的所有数据
	public void queryAll() {
		List<Article> articles = mongoTemplate.findAll(Article.class);
		System.out.println("=========articles.size()=============" + articles.size());
	}
	
	//查询符合条件的数量
	public void queryCountByCond() {
		Query query = Query.query(Criteria.where("author").is("wyk"));
		long count = mongoTemplate.count(query, Article.class);
		System.out.println("=========count=============" + count);
	}
	
	//查询符合条件的数量
	public void queryById() {
//		Article article = mongoTemplate.findById(new ObjectId("5d3f1fe9fc0ea231848520d4"), Article.class);
		Article article = mongoTemplate.findById("5d3f1fe9fc0ea231848520d4", Article.class);
		if (article == null) {
			System.out.println("查询无记录");
			return;
		}
		System.out.println("==========article.getId()===========" + article.getId());
	}
	
	//in查询
	public void queryAllInCond() {
		List<String> authors = Arrays.asList("wyk","another");
		Query query = Query.query(Criteria.where("author").in(authors));
		List<Article> articles = mongoTemplate.find(query, Article.class);
		System.out.println("==========articles.size()===========" + articles.size());
	}
	
	//ne(!=)查询，查询不符合条件的数据
	public void queryAllNeCond() {
		Query query = Query.query(Criteria.where("author").ne("wyk"));
		List<Article> articles = mongoTemplate.find(query, Article.class);
		System.out.println("==========articles.size()===========" + articles.size());
	}
	
	//lt(<)查询
	public void queryAllLtCond() {
		Query query = Query.query(Criteria.where("visitCount").lt(10));
		List<Article> articles = mongoTemplate.find(query, Article.class);
		System.out.println("==========articles.size()===========" + articles.size());
	}
	
	//gt(>)查询
	public void queryAllGtCond() {
		Query query = Query.query(Criteria.where("visitCount").gt(5).lt(10));
		List<Article> articles = mongoTemplate.find(query, Article.class);
		System.out.println("==========articles.size()===========" + articles.size());
	}
	
	//模糊查询
	public void queryAllRegexCond() {
		Query query = Query.query(Criteria.where("author").regex("y"));
		List<Article> articles = mongoTemplate.find(query, Article.class);
		System.out.println("==========articles.size()===========" + articles.size());
	}
	
	//数组查询
	public void queryAllByArray() {
		Query query = Query.query(Criteria.where("tags").size(3));
		List<Article> articles = mongoTemplate.find(query, Article.class);
		System.out.println("==========articles.size()===========" + articles.size());
	}
	
	//or查询
	public void queryAllOrCond() {
		Query query = Query.query(Criteria.where("tags").orOperator(Criteria.where("author").is("wyk"), Criteria.where("visitCount").is(0)));
		List<Article> articles = mongoTemplate.find(query, Article.class);
		System.out.println("==========articles.size()===========" + articles.size());
	}
	
}
