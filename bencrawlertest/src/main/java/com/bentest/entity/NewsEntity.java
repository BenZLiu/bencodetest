package com.bentest.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "NewsEntity")
public class NewsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	String id;
	String crawlUrl;
	String title;
	String url;
	String articleTag;
	String author;
	String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCrawlUrl() {
		return crawlUrl;
	}
	public void setCrawlUrl(String crawlUrl) {
		this.crawlUrl = crawlUrl;
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
	public String getArticleTag() {
		return articleTag;
	}
	public void setArticleTag(String articleTag) {
		this.articleTag = articleTag;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
