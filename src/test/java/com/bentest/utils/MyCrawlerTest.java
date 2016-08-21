package com.bentest.utils;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MyCrawlerTest {

	private String path = "/Users/liuzheng/Downloads/data";

	private String host = "localhost";

	private String port = "27017";

	private String db = "test";

	private String url;

	private MyCrawler myCrawler;

	@Before
	public void setup() {
		myCrawler = new MyCrawler();
		url = "http://www.bbc.com/";
	}

	@Test
	public void testCrawl() throws Exception {
		 myCrawler.crawl(url, path, host, port, db);
		MongoClient mongoClient = new MongoClient(host, Integer.valueOf(port));
		DB database = mongoClient.getDB(db);
		DBCollection collection = database.getCollection("NewsEntity");
		BasicDBObject doc = new BasicDBObject();
		doc.append("crawlUrl", url);
		Assert.notNull(collection.findOne(doc));
	}
}
