package com.bentest.dao;

import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.bentest.entity.NewsEntity;
import com.bentest.repository.NewsEntityRepo;

@Component
public class NewsEntityDAOImpl implements NewsEntityDAO {
	
	Logger log = Logger.getLogger(NewsEntityDAOImpl.class);

	@Autowired
	NewsEntityRepo newsEntityRepo;

	@Autowired
	MongoOperations mongoOperations;

	@Override
	public List<NewsEntity> findBySerarchInput(String searchInput) {
//		String regex = "/" + searchInput + "/";
		try{
			
//			BasicQuery query = new BasicQuery("{\"crawlUrl\":\"https://www.theguardian.com/au\"}");
//			BasicQuery query = new BasicQuery("{title:{$regex:'" + searchInput +"', $options:'i'}}");
            Query query = new Query();
//            System.out.println("query==============" + query.toString());
            Criteria cre = new Criteria();
            cre.orOperator(Criteria.where("articleTag").regex(searchInput,"i"),Criteria.where("title").regex(searchInput,"i"),
            		Criteria.where("author").regex(searchInput,"i"));
            query.addCriteria(cre);
            return mongoOperations.find(query, NewsEntity.class);
//            return list;
        } catch(PatternSyntaxException e) {
        	log.debug("Query Exception====" + e.getMessage());
            return Collections.emptyList();
        }
	}

}
