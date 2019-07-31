package com.kimile.db.mongodb.service;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.kimile.db.mongodb.bean.Article;
import com.kimile.db.mongodb.repository.ArticleRepositor;

@Service
public class MongoRepositoryService {
	
	@Autowired
	private ArticleRepositor articleRepositor;
	
	public void findAll() {
		Iterable<Article> articles = articleRepositor.findAll();
		articles.forEach(new Consumer<Article>() {
			@Override
			public void accept(Article article) {
				System.out.println(article.getId());
			}
		});
//		Iterator<Article> iterator = articles.iterator();
//		while (iterator.hasNext()) {
//			Article article = iterator.next();
//			System.out.println(article.getId());
//		}
	}
	
	public void findByAuthor() {
		Iterable<Article> articles = articleRepositor.findByAuthor("another");
		articles.forEach(new Consumer<Article>() {
			@Override
			public void accept(Article article) {
				System.out.println(article.getId());
			}
		});
	}
	
	public void findByAuthorIgnoreCase() {
		Iterable<Article> articles = articleRepositor.findByAuthorIgnoreCase("WYk");
		articles.forEach(new Consumer<Article>() {
			@Override
			public void accept(Article article) {
				System.out.println(article.getId());
			}
		});
	}
	
	public void findByAuthorOrderByVisitCountDesc() {
		Iterable<Article> articles = articleRepositor.findByAuthorOrderByVisitCountDesc("wyk");
		articles.forEach(new Consumer<Article>() {
			@Override
			public void accept(Article article) {
				System.out.println("id===" + article.getId() + "************visitCount===" + article.getVisitCount());
			}
		});
	}
	
	public void findByAuthorBySort() {
		List<Article> articles = articleRepositor.findByAuthor("wyk", new Sort(Direction.ASC, "VisitCount"));
		articles.forEach(article -> {
			System.out.println("id===" + article.getId() + "************visitCount===" + article.getVisitCount());
		});
	}
	
	public void findByPage() {
		int page = 0;
		int size = 2;
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "VisitCount"));
		Page<Article> pageInfo = articleRepositor.findAll(pageable);
		System.out.println(pageInfo.getTotalElements());
		System.out.println(pageInfo.getTotalPages());
		for (Article article : pageInfo.getContent()) {
			System.out.println("Author=" + article.getAuthor() + ", VisitCount=" + article.getVisitCount());
		}
	}
	
}
