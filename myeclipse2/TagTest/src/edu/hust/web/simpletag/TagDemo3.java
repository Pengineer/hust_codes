package edu.hust.web.simpletag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
//�޸ı�ǩ�壬��ʱ�Ͳ���invoke��������У�����Ҫinvoke���������С�Ȼ���ڻ��������õ����ݣ��޸ĺ���invoke�����
public class TagDemo3 extends SimpleTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {

		JspFragment jf = this.getJspBody();//�õ���ǩ�����
		
		StringWriter sw = new StringWriter();
		jf.invoke(sw);//������invoke���������У������������������л�ȡ�������ݵķ��������BufferedWriter�����У�
		
		String buf = sw.toString(); //�õ�������������
		
		buf = buf.toUpperCase();  //�޸�����
		
		this.getJspContext().getOut().write(buf);  //������д�뵽�������
		
	}

}
