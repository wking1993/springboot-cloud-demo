package com.kimile.db.mongodb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.kimile.db.mongodb.bean.Article;

@Service
public class InsertMongoService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void addArticle() {
		Article article = new Article();
		article.setTitle("MongTemplate的基本使用");
		article.setAuthor("wyk");
		article.setUrl("http://www.baidu.com");
		article.setTags(Arrays.asList("java", "mongodb", "spring"));
		article.setVisitCount(0L);
		article.setAddTime(new Date());
		mongoTemplate.save(article);
	}
	
	public void addArticles() {
		//批量添加
		List<Article> articles = new ArrayList<Article>();
		for (int i = 0; i < 10; i++) {
			Article article = new Article();
			article.setTitle("MongTemplate的基本使用");
			article.setAuthor("wyk");
			article.setUrl("http://www.baidu.com");
			article.setTags(Arrays.asList("java", "mongodb", "spring"));
			article.setVisitCount(0L);
			article.setAddTime(new Date());
			articles.add(article);
		}
		mongoTemplate.insert(articles, Article.class);
	}
	
}
