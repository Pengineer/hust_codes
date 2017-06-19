package org.cdsc.service;

import org.cdsc.BaseTest;
import org.csdc.service.imp.AccountService;
import org.csdc.service.imp.CategoryService;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceTest extends BaseTest{
	private AccountService accountService;
	@Before
	public void setUp() throws Exception {
		super.setUp();
		accountService = (AccountService) ac.getBean("accountService");
	}
	
	@Test
	public void testGetRightByAccountName(){
		System.out.println(accountService.getRightByAccountName("xujw"));
	}
}
