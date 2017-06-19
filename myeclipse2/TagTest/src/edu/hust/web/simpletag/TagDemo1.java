package edu.hust.web.simpletag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TagDemo1 extends SimpleTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {
		//�õ���ǩ�����
		JspFragment jf = this.getJspBody();  
		
		/*���Ҫִ�б�ǩ���ִ��invoke()�������÷����Ĳ�����һ�ַ�������Ҳ����ͨ���������������ָ��λ�ã�out��������������
		      �������ִ��ĳЩ���ݣ���ͨ����ǩ�������ݷ�װ�ɱ�ǩ�壬��doTag��������õ���ǩ����󣬵��ǲ�ִ��invoke�����Ϳ����ˡ�
		     Ҳ����д��jf.invoke(null);Ĭ�Ͼ���������������    
		 */
		jf.invoke(this.getJspContext().getOut());
	}

}
