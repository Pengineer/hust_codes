package edu.hust.action.attribute;

import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;



/**
 * 由于Struts2的解耦，使得我们的自定义Action与Servlet没有任何关系，那么我们就不能直接使用作用域，更不能直接向作用域中添加属性
 * 为了向各作用域中添加属性，Struts2提供了两种方式：
 *            （1）使用ActionContext对象：当我们仅仅只需要添加属性，不需要获取域对象时（多用）
 *            （2）先获取域对象，在向其中添加属性
 */
public class AttributeAction {
	
	public String execute(){
		ActionContext acon = ActionContext.getContext();
		acon.getApplication().put("attrname1", "data to contextScope");//往ServletContext作用域中添加属性
		acon.getSession().put("attrname2", "data to session");//往session作用域中添加属性
		acon.put("attrname3", "data to request");//往request作用域中添加属性
		
		acon.put("attrnames", Arrays.asList("东","西","南","北"));//添加属性集合
		return "message";
	}
	
	public String attrScope(){
		HttpServletRequest request = ServletActionContext.getRequest();    //获取request作用域
		ServletContext context = ServletActionContext.getServletContext(); //获取Context作用域
		
		request.setAttribute("attr1", "from requestScope");
		request.getSession().setAttribute("attr2", "from sessionScope");
		context.setAttribute("attr3", "from contextScope");
		return "message";
	}
}
