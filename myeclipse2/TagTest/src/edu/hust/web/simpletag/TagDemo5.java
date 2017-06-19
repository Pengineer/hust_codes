package edu.hust.web.simpletag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
//通过标签属性来控制执行次数
public class TagDemo5 extends SimpleTagSupport {

	private int count = 0;  //系统只支持8种基本数据类型的转换
	private Date date ;
	
	public void setDate(Date date) {
		this.date = date;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void doTag() throws JspException, IOException {		
		JspFragment jf = this.getJspBody();
		
		this.getJspContext().getOut().write(date.toLocaleString()+"<br>");
		for(int i=0 ; i<count ; i++){
			jf.invoke(null);
		}
	}

}
