package org.csdc.controller.sm.security;

import java.util.Date;

import net.sf.json.JSONArray;

import org.csdc.controller.JUnitActionBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class AccountControllerTest extends JUnitActionBase{
	@Test
	public void addUser() throws Exception{
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setServletPath("/user/ajax");	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setMethod("post");
		request.setParameter("name", "wy");
		request.setParameter("age", "18");
		request.setParameter("date", new Date().toString());
		final ModelAndView mav = this.excuteAction(request, response);
		System.out.println(response.getContentAsString());	
		JSONArray jsonArray =JSONArray.fromObject(response.getContentAsString());
		Assert.assertEquals(true, jsonArray.contains("空调"));
		Assert.assertEquals(true, jsonArray.contains("空调1"));
	}
}
