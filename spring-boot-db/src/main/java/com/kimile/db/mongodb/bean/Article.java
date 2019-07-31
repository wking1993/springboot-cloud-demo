package com.kimile.db.mongodb.bean;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//Document注解标识这是一个文档，等同于Mysql中的表，collection值标识MongoDB中集合的名称，不写默认为实体类名article
@Document(collection = "article_info")
public class Article {
	
	//主键标识
	@Id
	private String id;
	
	//字段标识
	@Field("title")
	private String title;
	
	@Field("url")
	private String url;
	
	@Field("author")
	private String author;
	
	@Field("tags")
	private List<String> tags;
	
	@Field("visit_count")
	private Long visitCount;
	
	@Field("add_time")
	private Date addTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Long getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Long visitCount) {
		this.visitCount = visitCount;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
}
