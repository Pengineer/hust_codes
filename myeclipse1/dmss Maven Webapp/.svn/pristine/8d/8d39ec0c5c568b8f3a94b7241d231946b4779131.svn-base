package org.cdsc.service;

import org.cdsc.BaseTest;
import org.csdc.service.imp.CategoryService;
import org.junit.Before;
import org.junit.Test;

public class CategoryServiceTest extends BaseTest{
	private CategoryService categoryService;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		categoryService = (CategoryService) ac.getBean("categoryService");
	}
	
	@Test
	public void testGetCatgeoryIdByCategoryString(){
		System.out.println(categoryService.getCatgeoryIdByCategoryString("/CMIS/ggg/hhh/1", true));
		//System.out.println(categoryService.getCatgeoryIdByCategoryString("/SMDB/award"));;
	}
}
