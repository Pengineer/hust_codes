package edu.hust.web.choose;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class OtherwiseDemo extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		ChooseDemo parent = (ChooseDemo) this.getParent();
		if(parent.getIsDo()){
			parent.setIsDo(true);//执行完后isDo回复默认值true，因此总是从when标签开始判断
			this.getJspBody().invoke(null);
		}
	}
	
}
