package edu.hust.web.choose;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class OtherwiseDemo extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		ChooseDemo parent = (ChooseDemo) this.getParent();
		if(parent.getIsDo()){
			parent.setIsDo(true);//ִ�����isDo�ظ�Ĭ��ֵtrue��������Ǵ�when��ǩ��ʼ�ж�
			this.getJspBody().invoke(null);
		}
	}
	
}
