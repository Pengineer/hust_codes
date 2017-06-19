package edu.hust.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import edu.hust.service.impl.PersonServiceBean;

/**
 * ʹ��java�����ṩ�Ķ�̬�������������Ҫ�������ࣺ
 *     1��Proxy:�û�����Ŀ�����Ĵ������
 *     2��InvocationHandler��ӵ�лص��������ڻص��������û����ж��Ƿ�ִ��Ŀ�귽����
 */

public class JDKProxyFactory implements InvocationHandler {
	private Object targetObject;  //ʹ�ö�̬����ĺô�֮һ���ǿ���Ϊ������ഴ��������
	
	/*
	 * ����Ŀ�����Ĵ������
	 * ��һ��������Ŀ���������������
	 * �ڶ���������Ŀ�����ʵ�ֵ����нӿ��ࡣ
	 * �������������ص�����this��ʾ�������������ͨ������������Ŀ�����ķ���ʱ���ᴥ���ص�ִ�б����е�invoke������
	 */
	public Object createProxyObject(Object targetObject){
		this.targetObject = targetObject;
		return Proxy.newProxyInstance(this.targetObject.getClass().getClassLoader(), 
				this.targetObject.getClass().getInterfaces(), this);
	}
	
	/*
	 * ͨ���������������Ŀ������ʵ�ַ���������֮ǰ�Ϳ����ж�Ȩ������
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		
		if(this.targetObject instanceof PersonServiceBean){
			PersonServiceBean bean = (PersonServiceBean) this.targetObject;
			if(bean.getUser() != null){
				//........ǰ��֪ͨ
				try{
					result = method.invoke(targetObject, args);
					//.........����֪ͨ
				}catch(RuntimeException e){
					//.........����֪ͨ
				}finally{
					//.........����֪ͨ
				}
			}
		}
		
		return result;
	}
}
