package edu.hust.web.iterator;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class IteratorTagDemo extends SimpleTagSupport {
	private Object items;
	private String var;   //var就相当于一个标记，其类型只能是String
	
	public void setItems(Object items) {
		this.items = items;
	}
	public void setVar(String var) {
		this.var = var;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		List list = (List)items;
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object value = it.next();
			this.getJspContext().setAttribute(var, value);  //将list元素的值保存在context域中（用var记住）以便el表达式读取
			this.getJspBody().invoke(null);
		}
	}
	
}
