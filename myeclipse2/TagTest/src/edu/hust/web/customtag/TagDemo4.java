package edu.hust.web.customtag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

//修改标签体
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
		//将标签体直接封装成BodyConten对象，并将该对象存入缓冲区中，原标签体不会被执行，只有通过操作该对象才能操作原标签体
		return BodyTag.EVAL_BODY_BUFFERED; 
	}
}
