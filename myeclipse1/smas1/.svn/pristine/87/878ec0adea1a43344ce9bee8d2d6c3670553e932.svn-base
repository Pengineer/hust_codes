package csdc.tool.filter;

import csdc.bean.common.Visitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalRightFilter implements Filter {
	
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest req, ServletResponse res,  
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		Set<String> userRight = new HashSet<String>();// 登陆用户拥有的权限
		List<Object> rightUrl = new ArrayList<Object>();// 权限名称与url对应列表
		List<String> requireRight = new ArrayList<String>();// 拥有该url的权限名称数组
		
		/* 从application中获取rightUrl */
		rightUrl = (List<Object>) request.getSession().getServletContext().getAttribute("rightUrl");
		
		/* 获取工程上下文根 */
		String path = request.getContextPath();
		/* 获取客户端请求的url */
		String url = request.getServletPath();
		//System.out.println(url.indexOf("."));
		if (url.indexOf(".") < 0) {
			url += ".action";
		}
		//System.out.println(url);
		/* 找到拥有该url的权限名 */
		for (int i=0; i<rightUrl.size(); i++) {
			Object[] str = (Object[]) rightUrl.get(i);
			/* 若有相应的url则把它所属的权限名称写入requireRight */
			if (str[1].toString().equals(url)) {
				//System.out.println(str[0].toString());
				requireRight.add(str[0].toString());
			}
		}
		
		Visitor visitor = (Visitor) request.getSession().getAttribute("visitor");
		
		/* 判断用户是否拥有此权限 */
		if(requireRight.size() > 0) {
			//System.out.println("需要进行权限控制" + requireRight.size());
			if(visitor != null) {
				userRight = visitor.getUserRight();
				if(userRight.size() != 0 ) {
					//System.out.println("当前用户拥有的权限");
					//java.util.Iterator<String> aa = userRight.iterator();
					//while (aa.hasNext()) {
						//System.out.println(aa.next());
					//}
					int label = 0;
					for (int i=0; i<requireRight.size(); i++) {
						if (userRight.contains(requireRight.get(i))){
							//System.out.println("当前用户拥有此权限");
							label = 1;
							chain.doFilter(req, res);
							break;
						}
					}
					if (label == 0) {
						response.sendError(500, "你无权访问该页面!");
					}
				} else if (visitor.getUser().getIssuperuser() == 1) {
					chain.doFilter(req, res);
				} else {
					response.sendError(500, "你无权访问该页面!");
				}
			} else {
				response.sendRedirect(path + "/user/logout.action");
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}