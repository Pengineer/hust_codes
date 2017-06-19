package edu.hust.action.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

//�Զ�����������Action:Ҫʵ��Interceptor�ӿ�


/**
 * @author liangjian
 * invocation.invoke()���������������������ܵ�ʵ�ֺ���,���ǾͿ��Խ�invocation.invoke()��ΪAction�������������ص㣬�Ӷ�ʵ��AOP��
 * ���ǿ�����invocation.invoke()Ϊ�磬���������еĴ���ֳ�2�����֣���invocation.invoke()֮ǰ�Ĵ��룬������Action֮ǰ������ִ�У�����invocation.invoke()֮��Ĵ��룬
 * ������Action֮������ִ�С�Ϊʲô�������أ���Ϊinvoke()�������л᲻�ϵ�����һ����������intercept������ֱ�����һ����Ȼ�����return��
 * 
 * ************���ǣ�һ��Ҫע�⣺���ݹ鵽���һ��������ʱ���ͻ�ȥ���������Action��ִ�����󷽷�������÷����з���ֵ����ֱ�ӷ��ظ�ֵ����ת����Ӧ��JSP��ֱ�Ӻ��������������ķ���ֵ��ϵͳ����������ֵ��null��
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
	 * ��Action������ʱ��Ҫ��ִ��BusinessCode�еķ��������ͻ���ø���������ִ�д˷�����
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object user = ActionContext.getContext().getSession().get("user");
		if(user!=null){
			/*
			 * ........Action�ķ�����ִ��֮ǰ�����û���¼��֤��
			 */
			String passinvoke = invocation.invoke(); //��������������(ϵͳ�Դ�)��������������ͨ��ͨ�������ڲ�ִ��Action�ķ����壬ֱ�ӷ��ط������ֵ����ת��Action������ָ����JSPҳ��   
			/*
			 * ........Action�ķ�����ִ��֮��Ĵ���
			 */
			return passinvoke; //returnʲô�����ԣ���Ϊ���ǵķ���������return�����ǲ���ʡ�ԣ���Ϊ����������Ҫ��intercept�ķ������Ͳ���Ϊ�գ���Ϊ��֤��ͨ��ʱ��Ҫͨ������ֵ�����Զ���ת��  
		}else{
			ActionContext.getContext().put("methodInfo", "��û��Ȩ�޽��д˲���");
			return "message";  //��ת��result����
		}
		
		
	}


}
