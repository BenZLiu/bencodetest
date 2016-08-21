package com.bentest.service;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bentest.dao.NewsEntityDAO;
import com.bentest.dao.NewsEntityDAOImpl;
import com.bentest.entity.NewsEntity;
import com.bentest.utils.MyCrawler;

public class WebCrawlerServiceTest {

	private WebCrawlerService crawlerService;
	private MyCrawler myCrawler;
	private String correctURL = "https://www.theguardian.com/au";
	private String incorrectURL = "test.test.com";
	private String incorrecDecodeURL = "Æ Ø Å谁的粉丝地方a谁的粉丝地方a谁的粉丝";
	// new String("コードから".getBytes(StandardCharsets.UTF_16BE));
	private NewsEntityDAO newsEntityDAO;
	private String searchInput = "input";
	private List<NewsEntity> list;
	private NewsEntity newsEntity;
	private String DATA_FOLDER = "/Users/liuzheng/Downloads/data";

	private String host = "localhost";

	private String port = "27017";

	private String db = "test";

	@Before
	public void setup() {
		crawlerService = new WebCrawlerService();
		myCrawler = EasyMock.createMock(MyCrawler.class);
		crawlerService.myCrawler = myCrawler;
		newsEntityDAO = EasyMock.createMock(NewsEntityDAOImpl.class);
		crawlerService.newsEntityDAO = newsEntityDAO;
		list = new ArrayList<NewsEntity>();
		newsEntity = new NewsEntity();
		newsEntity.setTitle("test");
		list.add(newsEntity);
	}

	@Test
	public void testSearch() {
		EasyMock.expect(newsEntityDAO.findBySerarchInput(searchInput))
				.andReturn(list);
		EasyMock.replay(newsEntityDAO);
		List<NewsEntity> newsList = crawlerService.search(searchInput);
		Assert.assertNotNull(newsList);
		Assert.assertEquals(1, newsList.size());
		Assert.assertEquals("test", newsList.get(0).getTitle());
	}

	@Test
	public void testCrawlingSuccess() throws Exception {
		myCrawler.crawl(correctURL, DATA_FOLDER, host, port, db);
		EasyMock.expectLastCall().times(5);
		ResultModel result = crawlerService.crawling(correctURL);
		Assert.assertEquals("SUCCESS", result.getResult());
	}

	@Test
	public void testCrawlingFailure() throws Exception {
		myCrawler.crawl(incorrectURL, DATA_FOLDER, host, port, db);
		EasyMock.expectLastCall().times(5);
		ResultModel result = crawlerService.crawling(incorrectURL);
		Assert.assertEquals("FAILURE", result.getResult());
	}

	@Test
	public void testCrawlingFailure2() throws Exception {
		myCrawler.crawl(incorrecDecodeURL, DATA_FOLDER, host, port, db);
		EasyMock.expectLastCall().times(5);
		ResultModel result = crawlerService.crawling(incorrecDecodeURL);
		Assert.assertEquals("FAILURE", result.getResult());
	}

}
