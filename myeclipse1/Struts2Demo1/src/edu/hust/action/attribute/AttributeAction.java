package edu.hust.action.attribute;

import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;



/**
 * ����Struts2�Ľ��ʹ�����ǵ��Զ���Action��Servletû���κι�ϵ����ô���ǾͲ���ֱ��ʹ�������򣬸�����ֱ�������������������
 * Ϊ�������������������ԣ�Struts2�ṩ�����ַ�ʽ��
 *            ��1��ʹ��ActionContext���󣺵����ǽ���ֻ��Ҫ������ԣ�����Ҫ��ȡ�����ʱ�����ã�
 *            ��2���Ȼ�ȡ��������������������
 */
public class AttributeAction {
	
	public String execute(){
		ActionContext acon = ActionContext.getContext();
		acon.getApplication().put("attrname1", "data to contextScope");//��ServletContext���������������
		acon.getSession().put("attrname2", "data to session");//��session���������������
		acon.put("attrname3", "data to request");//��request���������������
		
		acon.put("attrnames", Arrays.asList("��","��","��","��"));//������Լ���
		return "message";
	}
	
	public String attrScope(){
		HttpServletRequest request = ServletActionContext.getRequest();    //��ȡrequest������
		ServletContext context = ServletActionContext.getServletContext(); //��ȡContext������
		
		request.setAttribute("attr1", "from requestScope");
		request.getSession().setAttribute("attr2", "from sessionScope");
		context.setAttribute("attr3", "from contextScope");
		return "message";
	}
}
