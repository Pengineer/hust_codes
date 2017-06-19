package edu.hust.web.simpletag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
//修改标签体，这时就不能invoke到浏览器中，而是要invoke到缓冲区中。然后在缓冲区中拿到数据，修改后再invoke输出。
public class TagDemo3 extends SimpleTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {

		JspFragment jf = this.getJspBody();//得到标签体对象
		
		StringWriter sw = new StringWriter();
		jf.invoke(sw);//将数据invoke到缓冲区中（这个缓冲流对象必须有获取缓冲数据的方法，因此BufferedWriter流不行）
		
		String buf = sw.toString(); //拿到缓冲区中数据
		
		buf = buf.toUpperCase();  //修改数据
		
		this.getJspContext().getOut().write(buf);  //将数据写入到浏览器中
		
	}

}
