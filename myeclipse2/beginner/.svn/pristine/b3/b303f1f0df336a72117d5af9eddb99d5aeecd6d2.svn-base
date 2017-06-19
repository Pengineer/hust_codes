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
		Visitor visitor = (Visitor) session.get("visitor");
		if (visitor == null || visitor.getUser() == null || visitor.getUser().getIssuperuser() == 0) {
			// 如果未登入，或者不是超级用户，则无权使用right中的功能。
			return Action.LOGIN;
		} else {
			return arg0.invoke();
		}
	}
}