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
 * ���棨������뽻��Spring���������Ч----��Xml�����ļ��н�����ע��Spring������
 */
@Aspect
public class MyInterceptorByAnnotation {
	@Pointcut("execution (* edu.hust.service.impl.PersonServiceBean.*(..))")
	private void anyMethod() {}  //����һ�������
	
	@Before("anyMethod()")
	public void doAccessCheck(){
		System.out.println("ǰ��֪ͨ");
	}
	
	@AfterReturning("anyMethod()")     
	public void doAfterCheck(){
		System.out.println("����֪ͨ");
	}
	
	@AfterThrowing("anyMethod()")     
	public void doAfterThrowing(){
		System.out.println("����֪ͨ");
	}
	
	@After("anyMethod()")     
	public void doAfter(){
		System.out.println("���֪ͨ");
	}
	
	@Around("anyMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		//if(){ //�ж��û��Ƿ����Ȩ��
		System.out.println("���뷽��");
		Object result = pjp.proceed();
		System.out.println("��������");
	    //}
		return result;
	}
}
