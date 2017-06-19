package org.csdc.controller;
/**
 *
 * @author skyger  E-mail: jintianfan@gmail.com
 * @version v0.1.0 created：2013-6-4 下午2:37:21
 * 
 */

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class TestUserController extends JUnitActionBase {
	@Test
	public void testLogin() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setServletPath("/user/login");
		request.addParameter("username", "jin");
		request.addParameter("password", "tianfan");
		request.setMethod("POST");
		// 执行URI对应的action
		final ModelAndView mav = this.excuteAction(request, response);
		// Assert logic
		String command = (String) mav.getModel().get("command");
		
		System.out.println(command);
	}
	
	@Test
	public void testAjax() throws Exception{
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

