package edu.hust.web.iterator;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

//����һ����ǩ��ʵ���������ͼ��Ϻ�����ı�����˼·���ǽ������б��һ�����м���collection
public class IteratorTagDemo1 extends SimpleTagSupport {
	private Object items;
	private String var;
	private Collection collection;   //���м���List Set
	
	public void setItems(Object items) {
		this.items = items;
		//����ǵ��м���
		if(items instanceof Collection){
			collection = (Collection)items;
		}
		//�����˫�м���
		if(items instanceof Map){
			collection =  ((Map) items).entrySet();
		}
		//��������飺��ӣ���Ϊ����Ԫ�����Ͳ�֪�������ǿ���ͨ�����似�����ж��ǲ������飬Ȼ��������Ԫ����ӵ������У�����ͨ��Array�������ɼ��ϣ���Ϊ�����ܵ���һ�����󣬶��������͵����鲻����������
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
