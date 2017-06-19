package edu.hust.web.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class TagDemo2 extends TagSupport {

	//通过doEndTag()方法的返回值来控制整个jsp或则余下所有jsp是否输出。
	@Override
	public int doEndTag() throws JspException {
		
		return Tag.SKIP_PAGE;
	}
	
}
