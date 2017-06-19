package edu.hust.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * ���棨������뽻��Spring���������Ч----��Xml�����ļ��н�����ע��Spring������
 */

public class MyInterceptorByXml {
	
	public void doAccessCheck(){
		System.out.println("ǰ��֪ͨ...");
	}
	
	public void doAfterCheck(){
		System.out.println("����֪ͨ...");
	}
	
	public void doAfterThrowing(){
		System.out.println("����֪ͨ...");
	}
	
	public void doAfter(){
		System.out.println("���֪ͨ...");
	}
	
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		//if(){ //�ж��û��Ƿ����Ȩ��
		System.out.println("���뷽��...");
		Object result = pjp.proceed();
		System.out.println("��������...");
	    //}
		return result;
	}
}
