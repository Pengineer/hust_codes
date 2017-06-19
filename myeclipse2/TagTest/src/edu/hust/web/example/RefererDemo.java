package edu.hust.web.example;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
/*
 * 判断盗链的原理：
 * 1，如果是在浏览器中直接输入的网址获得的资源，那么它就是盗链，且referer值为null，（referer用于存储本页面是从哪个页面链接过来的）；
 * 2，如果网页的地址与我给定的资源地址(资源的实际地址)不一样，那么该链接也是盗链
 */
public class RefererDemo extends SimpleTagSupport {
	private String site;  //实施盗链的网站
	private String page;  //设置跳转页面
	
	public void setSite(String site) {
		this.site = site;
	}
	public void setPage(String page) {
		this.page = page;
	}
	@Override
	public void doTag() throws JspException, IOException {
		PageContext context = (PageContext) this.getJspContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		
		String referer = request.getHeader("referer");
		if(referer==null || !referer.startsWith(site)){
			//如果是盗链网站,则跳转到首页,其它内容均不显示
			response.sendRedirect(request.getContextPath()+page);
			
			throw new SkipPageException();
		}
		
		//如果不是盗链，就正常执行jsp下面的内容（这里只需啥都不做）
		
	}
	
	
	
}
