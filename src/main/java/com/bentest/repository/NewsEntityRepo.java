package com.bentest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.bentest.entity.NewsEntity;

@Component
public interface NewsEntityRepo extends MongoRepository<NewsEntity, String> {
	
}
