package com.bentest.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bentest.dao.NewsEntityDAO;
import com.bentest.entity.NewsEntity;
import com.bentest.utils.MyCrawler;

@RestController
public class WebCrawlerService {

	static Logger log = Logger.getLogger(WebCrawlerService.class.getName());

	@Autowired
	MyCrawler myCrawler;

	@Autowired
	NewsEntityDAO newsEntityDAO;
	
	@Value("${CRAWLSTORAGEFOLDER}")
	private String CRAWLSTORAGEFOLDER;
	
	@Value("${spring.data.mongodb.host}")
	public String host;
	
	@Value("${spring.data.mongodb.port}")
	public String port;
	
	@Value("${spring.data.mongodb.database}")
	public String db;

	@RequestMapping(value = "/crawling/url", method = RequestMethod.POST)
	public ResultModel crawling(@RequestBody String url) {
		ResultModel result = new ResultModel();
		String decodedUrl;
		try {
			decodedUrl = URLDecoder.decode(url, "utf-8");
			HttpURLConnection urlConn = (HttpURLConnection) new URL(decodedUrl).openConnection();
			urlConn.connect();
			myCrawler.crawl(decodedUrl, CRAWLSTORAGEFOLDER, host, port, db);
			result.setResult("SUCCESS");
			return result;
		} catch (UnsupportedEncodingException e) {
			log.debug("Url Decode exception======" + e.getMessage());
			result.setResult("FAILURE");
			return result;
		} catch (IOException e) {
			log.debug("Url Connection exception======" + e.getMessage());
			result.setResult("FAILURE");
			return result;
		} catch (Exception e1) {
			log.debug("exception======" + e1.getMessage());
			result.setResult("FAILURE");
			return result;
		}
		
	}

	@RequestMapping(value = "/search/searchInput/{searchInput}", method = RequestMethod.GET)
	public List<NewsEntity> search(@PathVariable String searchInput) {
		return newsEntityDAO.findBySerarchInput(searchInput);
	}
}
