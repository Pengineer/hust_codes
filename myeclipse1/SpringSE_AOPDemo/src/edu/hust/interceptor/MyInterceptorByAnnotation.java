package edu.hust.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 切面（切面必须交由Spring管理才能生效----在Xml配置文件中将本类注入Spring容器）
 */
@Aspect
public class MyInterceptorByAnnotation {
	@Pointcut("execution (* edu.hust.service.impl.PersonServiceBean.*(..))")
	private void anyMethod() {}  //申明一个切入点
	
	@Before("anyMethod()")
	public void doAccessCheck(){
		System.out.println("前置通知");
	}
	
	@AfterReturning("anyMethod()")     
	public void doAfterCheck(){
		System.out.println("后置通知");
	}
	
	@AfterThrowing("anyMethod()")     
	public void doAfterThrowing(){
		System.out.println("例外通知");
	}
	
	@After("anyMethod()")     
	public void doAfter(){
		System.out.println("最后通知");
	}
	
	@Around("anyMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		//if(){ //判断用户是否存在权限
		System.out.println("进入方法");
		Object result = pjp.proceed();
		System.out.println("结束方法");
	    //}
		return result;
	}
}
