package edu.hust.web.customtag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

//�޸ı�ǩ��
public class TagDemo4 extends BodyTagSupport {

	@Override
	public int doEndTag() throws JspException {
		BodyContent bc = this.getBodyContent();
		String content = bc.getString();
		content = content.toUpperCase();
		JspWriter out= this.pageContext.getOut();
		try {
			out.write(content);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
		return Tag.EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		//����ǩ��ֱ�ӷ�װ��BodyConten���󣬲����ö�����뻺�����У�ԭ��ǩ�岻�ᱻִ�У�ֻ��ͨ�������ö�����ܲ���ԭ��ǩ��
		return BodyTag.EVAL_BODY_BUFFERED; 
	}
}
