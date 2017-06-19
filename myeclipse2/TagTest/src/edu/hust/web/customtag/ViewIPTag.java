package edu.hust.web.customtag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ViewIPTag extends TagSupport {
	
	/*ÿ��ִ��jsp�е�һ����ǩʱ��ϵͳ�ͻ��Զ�����Tag�ӿ��е���Ӧ������
	 * 	doStartTag()���÷����ǵ�ִ�е�jsp�ı�ǩ�Ŀ�ʼ�����Զ����õġ�
	 */
	@Override
	public int doStartTag() throws JspException {
		//ͨ��pageContext����ʽ�����ȡ����8����ʽ����request��response��config��application��exception��session��page��out
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		JspWriter out = this.pageContext.getOut();
		
		//��jsp�и��������java����
		String IP = request.getRemoteAddr();
		try {
			out.write(IP);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		return super.doStartTag();
	}

	
}
