package edu.hust.web.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class TagDemo3 extends TagSupport {
	private int i =0;  //���ܶ������static��
	
	@Override
	public int doAfterBody() throws JspException {
		i++;
		if(i<5){
			return IterationTag.EVAL_BODY_AGAIN;
		}else{
			return IterationTag.SKIP_BODY;
		}
	}

	//ֻҪ�б�ǩ��ı�ǩ����Ҫ����doStartTag()���������Ҫ��֤��ǩ����Ա�ִ��
	@Override
	public int doStartTag() throws JspException {
		
		return Tag.EVAL_BODY_INCLUDE;
	}

}
