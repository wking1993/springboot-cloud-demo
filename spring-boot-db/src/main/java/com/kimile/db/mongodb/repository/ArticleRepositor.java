package com.kimile.db.mongodb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kimile.db.mongodb.bean.Article;

@Repository("ArticleRepositor")
public interface ArticleRepositor extends PagingAndSortingRepository<Article, String> {
	
	//分页查询
	public Page<Article> findAll(Pageable pageable);
	
	//根据author查询
	public List<Article> findByAuthor(String author);
	
	//根据author和title查询
	public List<Article> findByAuthorAndTitle(String author, String title);
	
	//根据author忽略大小写查询
	public List<Article> findByAuthorIgnoreCase(String author);
	
	//根据author和title，忽略大小写查询
	public List<Article> findByAuthorAndTitleIgnoreCase(String author, String title);
	
	//排序
	public List<Article> findByAuthorOrderByVisitCountDesc(String author);
	public List<Article> findByAuthorOrderByVisitCountAsc(String author);
	
	//自带排序条件
	public List<Article> findByAuthor(String author, Sort sort);
	
}
