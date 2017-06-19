package csdc.tool.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import csdc.tool.RequestIP;

/**
 * 在session里记录访客的有用信息
 * 
 * @author xuhan
 * 
 */
public class MyFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();

		if (session.getAttribute("client_ip") == null) {
			session.setAttribute("client_ip", RequestIP.getRequestIp(request));
			session.setAttribute("request_uri", req.getServerName() + ":" + req.getServerPort() + request.getRequestURI());
			session.setAttribute("user_agent", request.getHeader("user-agent"));
			session.setAttribute("referer", request.getHeader("referer"));
		}
		chain.doFilter(req, res);
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}