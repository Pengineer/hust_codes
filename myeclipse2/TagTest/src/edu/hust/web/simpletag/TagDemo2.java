package edu.hust.web.simpletag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
//控制执行次数
public class TagDemo2 extends SimpleTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {

		JspFragment jf = this.getJspBody();  
		for(int i=0 ; i<5 ; i++){
			jf.invoke(null);
		}
	}

}
