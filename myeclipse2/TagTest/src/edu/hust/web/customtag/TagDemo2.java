package edu.hust.web.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class TagDemo2 extends TagSupport {

	//ͨ��doEndTag()�����ķ���ֵ����������jsp������������jsp�Ƿ������
	@Override
	public int doEndTag() throws JspException {
		
		return Tag.SKIP_PAGE;
	}
	
}
