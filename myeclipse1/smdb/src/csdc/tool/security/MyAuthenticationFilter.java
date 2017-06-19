package csdc.tool.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csdc.tool.RequestIP;

/**
 * 自定义过滤器，插入spring-security的认证过滤器之前，
 * 实现账号、密码、验证码、IP防火墙等功能，辅助认证及访问控制
 * @author 龚凡
 * @version 2011.02.22
 */
public class MyAuthenticationFilter implements Filter {

	private MyAuthentication myAuthentication;// 自定义认证功能类
	
	public void doFilter(ServletRequest req, ServletResponse res,
		    FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String relativeUrl = request.getRequestURI();/* 以根开头的URL */
		String path = request.getContextPath();/* 获取客户端请求的上下文根 */
		
		/**
		 * 如果是登录请求，则进行登录认证。
		 * 如果是其它请求，则进行IP绑定匹配。
		 */
		if (relativeUrl.replaceAll(path, "").equals("/login")) {// 登录请求，登录认证
			if (request.getMethod().equals("POST")) {// 登录参数必须通过post方式传过来，security要求的
				int status = myAuthentication.getAuthenticationStatus(request);// 调用认证逻辑
				/**----------------- [ mobile 登录认证  start ] ------------------*/
				String mobileLoginTag = request.getParameter("mobileTag"); //获取mobile登录标识				
//				String userAgent = (String) request.getSession().getAttribute("user_agent");
//				System.out.println("User-Agent：" + userAgent);
				if(null != mobileLoginTag){
					request.getSession().setAttribute("mobileStatus", status);
					request.getSession().setAttribute("mobileTag", mobileLoginTag);
					if (status == 0) {// 通过认证，则保存登录IP到session，并通过此过滤器
						request.getSession().setAttribute("bindIp", RequestIP.getRequestIp(request));
						chain.doFilter(request, response);
					}else{
						response.sendRedirect(path + "/mobile/login/login.action");
					}
				}
				/**----------------- [ mobile 登录认证  end ] ------------------*/
				else{
					if (status == 0) {// 通过认证，则保存登录IP到session，并通过此过滤器
						request.getSession().setAttribute("bindIp", RequestIP.getRequestIp(request));
						chain.doFilter(request, response);
					} else {// 未通过认证，则拒绝登录，并返回登录页面提示相关信息
						response.sendRedirect(path + "/toIndex.action?error=" + status);
					}
				}
			} else {// 如果不是POST方式，则返回登录页面，并提示信息
				response.sendRedirect(path + "/toIndex.action?error=9");// 登录必须用POST方式
			}
		} else {// 其它请求(filters="none"的请求不会被处理，logout请求在此filter之前会被处理)
			String mobileLoginTag = (String)request.getSession().getAttribute("mobileTag");
			if(null != mobileLoginTag){//移动端不设IP限制
				chain.doFilter(request, response);
			}
			else {//PC端进行IP认证
				String loginIp = (String) request.getSession().getAttribute("bindIp");// 登录时记录的IP
				String currentIp = RequestIP.getRequestIp(request);// 当前请求的IP
				if (loginIp != null && !loginIp.equals(currentIp)) {// 如果此次请求的IP与登录IP不符，则禁止访问，并返回提示
					response.sendRedirect(path + "/toIndex.action?error=10");
				} else {// 如果IP匹配，则通过此过滤器
					chain.doFilter(request, response);
				}
			}
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void setMyAuthentication(MyAuthentication myAuthentication) {
		this.myAuthentication = myAuthentication;
	}

}
