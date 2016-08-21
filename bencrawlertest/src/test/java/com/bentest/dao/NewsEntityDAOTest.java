package com.bentest.dao;

import org.junit.Before;

public class NewsEntityDAOTest {

	private NewsEntityDAO newEntityDAO;
	private String searchInput;
	@Before
	public void setup(){
		newEntityDAO = new NewsEntityDAOImpl();
		searchInput = "test";
	}
	
//	@Test
//	public void testFindBySerarchInput(){
//		List<NewsEntity> list = newEntityDAO.findBySerarchInput(searchInput);
//		Assert.assertNotNull(list);
//	}
}
