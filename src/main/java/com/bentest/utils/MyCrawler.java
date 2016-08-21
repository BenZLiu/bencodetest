package com.bentest.utils;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

@Component
public class MyCrawler extends WebCrawler {

	static Logger log = Logger.getLogger(MyCrawler.class.getName());
	// Log log = x

	// @Autowired
	// private static NewsEntityDAO newsEntityDAO;

	// new NewsEntityDAOImpl();

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp3|zip|gz))$");

	private static String crawlUrl;
	
//	@Value("${spring.data.mongodb.host}")
	public  static String host;
	
//	@Value("${spring.data.mongodb.port}")
	public  static int port;
	
//	@Value("${spring.data.mongodb.database}")
	public  static String db;

	public  static String COLLECTION_NAME = "NewsEntity";


	/**
	 * This method receives two parameters. The first parameter is the page in
	 * which we have discovered this new url and the second parameter is the new
	 * url. You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic). In this example,
	 * we are instructing the crawler to ignore urls that have css, js, git, ...
	 * extensions and to only accept urls that start with
	 * "http://www.ics.uci.edu/". In this case, we didn't need the referringPage
	 * parameter to make the decision.
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches()
				&& href.startsWith(MyCrawler.crawlUrl);
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		// String url = page.getWebURL().getURL();
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();

			if (null != html && !"".equals(html)) {
				Document doc = Jsoup.parseBodyFragment(html);
				Elements titles = doc.select("meta[property^=og:title]");

				String title = (null == titles || titles.size() <= 0) ? null
						: titles.first().attr("content");
				Elements urls = doc.select("meta[property^=og:url]");
				String url = (null == urls || urls.size() <= 0) ? null : urls
						.first().attr("content");
				Elements articleTags = doc
						.select("meta[property^=article:tag]");
				String articleTag = (null == articleTags || articleTags.size() == 0) ? null
						: articleTags.first().attr("content");
				Elements authorS = doc.select("meta[name^=author]");
				Elements artiAuthorS = doc
						.select("meta[property^=article:author]");
				String author = (null == authorS || authorS.size() <= 0) ? ((null == artiAuthorS || artiAuthorS
						.size() <= 0) ? null : artiAuthorS.first().attr(
						"content")) : authorS.first().attr("content");
				StringBuilder sb = new StringBuilder();
				for (Element meta : doc.select("p")) {
					if (sb.length() > 0) {
						sb.append("\n");
					}
					sb.append(meta.text());
				}
				try {
					log.info("connect start ==========");
					MongoClient mongoClient = new MongoClient(host,
							port);
					DB database = mongoClient.getDB(db);
					DBCollection collection = database
							.getCollection("NewsEntity");
					BasicDBObject obj = new BasicDBObject();
					obj.append("crawlUrl", MyCrawler.crawlUrl);
					obj.append("title", title);
					obj.append("url", url);
					obj.append("articleTag", articleTag);
					obj.append("author", author);
					obj.append("content", sb.toString());
					collection.save(obj);
				} catch (UnknownHostException e) {
					log.info("connect exception ==========" + e.getMessage());
					// TODO Auto-generated catch block

				}
				// } catch(Exception e){
				// log.debug("crawler exception========" + e.getMessage());
				// }
			}
		}
	}

	public void crawl(String crawlingUrl,String path, String mHost, String mPort, String mDb) throws Exception {
		MyCrawler.crawlUrl = crawlingUrl;
		MyCrawler.host = mHost;
		MyCrawler.port = Integer.valueOf(mPort);
		MyCrawler.db = mDb;
		String crawlStorageFolder = path;
		int numberOfCrawlers = 10;

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		// config.set
		// config.set
		config.setMaxPagesToFetch(100);
		config.setMaxDepthOfCrawling(2);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher,
				robotstxtServer);

		controller.addSeed(crawlUrl);

		try {
			log.info("connect start ==========");
			MongoClient mongoClient = new MongoClient(host,
					port);
			DB database = mongoClient.getDB(db);
			DBCollection collection = database.getCollection("NewsEntity");
			BasicDBObject doc = new BasicDBObject();
			doc.append("crawlUrl", MyCrawler.crawlUrl);
			collection.remove(doc);
		} catch (UnknownHostException e) {
			log.info("connect exception ==========" + e.getMessage());
			// TODO Auto-generated catch block
		} finally{
			
		}
		controller.start(MyCrawler.class, numberOfCrawlers);
	}
	
}
