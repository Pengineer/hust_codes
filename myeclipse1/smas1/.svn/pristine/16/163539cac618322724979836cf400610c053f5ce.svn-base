package csdc.tool.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareFilter;


/**
 * 当使用FCK上传文件时，跳过struts的拦截器，否则会出现bug无法上传
 * @author 雷达
 *
 */
public class MyStrutsFilter extends StrutsPrepareFilter{
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		String url = ((HttpServletRequest)req).getRequestURI();
		if (url.indexOf("fckeditor") >= 0 && url.indexOf("filemanager") >= 0 || url.indexOf("ReportServer") >= 0) {
			chain.doFilter(req, res);
		} else {
			super.doFilter(req, res, chain);
		}
	}
}