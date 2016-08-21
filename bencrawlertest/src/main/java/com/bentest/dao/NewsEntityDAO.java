package com.bentest.dao;

import java.util.List;

import com.bentest.entity.NewsEntity;

public interface NewsEntityDAO {

	List<NewsEntity> findBySerarchInput(String searchInput);
}
