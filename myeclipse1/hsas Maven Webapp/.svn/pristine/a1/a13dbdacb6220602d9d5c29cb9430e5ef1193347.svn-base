package csdc.tool.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 日志拦截器，根据配置的日志要求，记录用户日志
 * @author 龚凡
 * @version 2011.03.03
 */

public class TestInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = -5217958754311186266L;

	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = invocation.getInvocationContext().getSession();
		System.out.println("在执行action之前:	"+invocation.getProxy().getNamespace().substring(1)+"/"+invocation.getProxy().getActionName());
		String result = invocation.invoke();			
		System.out.println("在执行action之后:	"+invocation.getResultCode()+"	"+invocation.getProxy().getNamespace().substring(1)+"/"+invocation.getProxy().getActionName());
		return result;
	}
}
