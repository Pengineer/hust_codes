package edu.hust.action.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

//自定义拦截器的Action:要实现Interceptor接口


/**
 * @author liangjian
 * invocation.invoke()这个方法是整个拦截器框架的实现核心,我们就可以将invocation.invoke()作为Action代码真正的拦截点，从而实现AOP。
 * 我们可以以invocation.invoke()为界，将拦截器中的代码分成2个部分，在invocation.invoke()之前的代码，将会在Action之前被依次执行，而在invocation.invoke()之后的代码，
 * 将会在Action之后被逆序执行。为什么是逆序呢？因为invoke()方法体中会不断调用下一个拦截器的intercept方法，直到最后一个，然后逐个return。
 * 
 * ************但是，一定要注意：当递归到最后一个拦截器时，就会去调用请求的Action，执行请求方法，如果该方法有返回值，就直接返回该值并跳转到对应的JSP，直接忽略其他拦截器的返回值（系统拦截器返回值是null）
 * 
 */
public class PermissionInterceptorAction implements Interceptor {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @author liangjian
	 * 当Action请求到来时（要求执行BusinessCode中的方法），就会调用该拦截器，执行此方法。
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object user = ActionContext.getContext().getSession().get("user");
		if(user!=null){
			/*
			 * ........Action的方法被执行之前经行用户登录验证，
			 */
			String passinvoke = invocation.invoke(); //调用其它拦截器(系统自带)，所有拦截器都通过通过后，在内部执行Action的方法体，直接返回方法体的值并跳转到Action方法体指定的JSP页面   
			/*
			 * ........Action的方法被执行之后的代码
			 */
			return passinvoke; //return什么都可以，因为我们的方法体中有return，但是不可省略，因为本方法的需要（intercept的返回类型不能为空，因为验证不通过时，要通过返回值进行自动跳转）  
		}else{
			ActionContext.getContext().put("methodInfo", "您没有权限进行此操作");
			return "message";  //跳转到result界面
		}
		
		
	}


}
