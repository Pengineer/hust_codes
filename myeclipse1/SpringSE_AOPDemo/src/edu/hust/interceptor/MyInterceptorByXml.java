package edu.hust.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 切面（切面必须交由Spring管理才能生效----在Xml配置文件中将本类注入Spring容器）
 */

public class MyInterceptorByXml {
	
	public void doAccessCheck(){
		System.out.println("前置通知...");
	}
	
	public void doAfterCheck(){
		System.out.println("后置通知...");
	}
	
	public void doAfterThrowing(){
		System.out.println("例外通知...");
	}
	
	public void doAfter(){
		System.out.println("最后通知...");
	}
	
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		//if(){ //判断用户是否存在权限
		System.out.println("进入方法...");
		Object result = pjp.proceed();
		System.out.println("结束方法...");
	    //}
		return result;
	}
}
