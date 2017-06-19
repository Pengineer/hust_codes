package edu.hust.web.iterator;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

//创建一个标签能实现所有类型集合和数组的遍历：思路就是将其所有变成一个单列集合collection
public class IteratorTagDemo1 extends SimpleTagSupport {
	private Object items;
	private String var;
	private Collection collection;   //单列集合List Set
	
	public void setItems(Object items) {
		this.items = items;
		//如果是单列集合
		if(items instanceof Collection){
			collection = (Collection)items;
		}
		//如果是双列集合
		if(items instanceof Map){
			collection =  ((Map) items).entrySet();
		}
		//如果是数组：最复杂，因为数组元素类型不知道，但是可以通过反射技术先判断是不是数组，然后把数组的元素添加到集合中（不能通过Array将数组变成集合，因为它接受的是一个对象，而基本类型的数组不满足条件）
		if(items.getClass().isArray()){
			this.collection = new ArrayList();
			int len = Array.getLength(items) ;
			for(int i=0 ; i<len ; i++){
				this.collection.add(Array.get(items, i));
			}
		}
	}
	public void setVar(String var) {
		this.var = var;
	}
	@Override
	public void doTag() throws JspException, IOException {
		Iterator iterator = collection.iterator();
		while(iterator.hasNext()){
			Object value= iterator.next();
			this.getJspContext().setAttribute(var, value);
			this.getJspBody().invoke(null);
		}
	}
}
