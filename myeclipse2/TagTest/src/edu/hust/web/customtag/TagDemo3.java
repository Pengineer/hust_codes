package edu.hust.web.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class TagDemo3 extends TagSupport {
	private int i =0;  //不能定义成了static了
	
	@Override
	public int doAfterBody() throws JspException {
		i++;
		if(i<5){
			return IterationTag.EVAL_BODY_AGAIN;
		}else{
			return IterationTag.SKIP_BODY;
		}
	}

	//只要有标签体的标签，都要覆盖doStartTag()方法，因此要保证标签体可以被执行
	@Override
	public int doStartTag() throws JspException {
		
		return Tag.EVAL_BODY_INCLUDE;
	}

}
