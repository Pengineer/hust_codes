package edu.hust.web.simpletag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TagDemo1 extends SimpleTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {
		//得到标签体对象
		JspFragment jf = this.getJspBody();  
		
		/*如果要执行标签体就执行invoke()方法，该方法的参数是一字符流对象，也就是通过流将数据输出到指定位置，out是输出到浏览器；
		      如果不想执行某些数据，就通过标签将该数据封装成标签体，在doTag方法里面得到标签体对象，但是不执行invoke方法就可以了。
		     也可以写成jf.invoke(null);默认就是输出到浏览器。    
		 */
		jf.invoke(this.getJspContext().getOut());
	}

}
