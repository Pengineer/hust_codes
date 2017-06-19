package csdc.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import csdc.bean.common.Visitor;

public class RightAuthorizationInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation arg0) throws Exception {
		Map session = arg0.getInvocationContext().getSession();		
		// 获取命名空间及action名，并拼成一个简略的URL
		String nameSpace = arg0.getProxy().getNamespace();
		String actionName = arg0.getProxy().getActionName();
		String url = nameSpace.substring(1) + "/" + actionName;
		if(url.equals("main/toIndex") || url.equals("main/rand") || url.equals("user/login")) {
			return arg0.invoke();
		}
		Visitor visitor = (Visitor) session.get("visitor");
		if (visitor == null || visitor.getUser() == null) {
			// 如果未登入，或者不是超级用户，则无权使用right中的功能。
			return Action.LOGIN;
		} else {
			return arg0.invoke();
		}
	}
}