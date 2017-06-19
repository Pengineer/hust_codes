package edu.hust.web.choose;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class WhenDemo extends SimpleTagSupport {
	private boolean login;

	public void setLogin(boolean login) {
		this.login = login;
	}

	@Override
	public void doTag() throws JspException, IOException {
		ChooseDemo parent = (ChooseDemo) this.getParent();
		if(parent.getIsDo() && login){
			parent.setIsDo(false);
			this.getJspBody().invoke(null);
		}
	}
	
	
}
