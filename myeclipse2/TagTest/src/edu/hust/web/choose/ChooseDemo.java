package edu.hust.web.choose;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
//choose标签是父标签，isDo是标志，当执行了when标签后，isDO就是false，那么otherwise标签就不会执行
public class ChooseDemo extends SimpleTagSupport {
	private boolean isDo = true;

	public void setIsDo(boolean isDo) {
		this.isDo = isDo;
	}

	public boolean getIsDo() {
		return isDo;
	}

	@Override
	public void doTag() throws JspException, IOException {
		
		this.getJspBody().invoke(null);
		
	}
	
	
}
