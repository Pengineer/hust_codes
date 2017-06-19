package edu.hust.web.simpletag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
//控制jsp余下的标签不执行:只需要抛出一个SkipPageException异常就可以了
public class TagDemo4 extends SimpleTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {
		
		throw new SkipPageException();
	}

}
