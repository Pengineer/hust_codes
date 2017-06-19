package edu.hust.web.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class TagDemo1 extends TagSupport {

	//通过doStartTag()返回值来判定jsp文件中自定义标签的标签内容是否执行,不能判定整个page是否执行(doEndTag()方法)
	@Override
	public int doStartTag() throws JspException {
		
	//	return Tag.EVAL_BODY_INCLUDE;   //执行标签体
		
		return Tag.SKIP_BODY;           //不执行标签体

	}
	
}
