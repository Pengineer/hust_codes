package edu.hust.web.choose;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
//choose��ǩ�Ǹ���ǩ��isDo�Ǳ�־����ִ����when��ǩ��isDO����false����ôotherwise��ǩ�Ͳ���ִ��
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
