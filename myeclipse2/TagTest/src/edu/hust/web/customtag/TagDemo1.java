package edu.hust.web.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class TagDemo1 extends TagSupport {

	//ͨ��doStartTag()����ֵ���ж�jsp�ļ����Զ����ǩ�ı�ǩ�����Ƿ�ִ��,�����ж�����page�Ƿ�ִ��(doEndTag()����)
	@Override
	public int doStartTag() throws JspException {
		
	//	return Tag.EVAL_BODY_INCLUDE;   //ִ�б�ǩ��
		
		return Tag.SKIP_BODY;           //��ִ�б�ǩ��

	}
	
}
