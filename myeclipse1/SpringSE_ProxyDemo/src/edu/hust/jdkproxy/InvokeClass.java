package edu.hust.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ��ʱ�����ǻὫ�������������ص���invoke�����ֿ�д�����ʱ��Proxy.newProxyInstance�ĵ�������������invoke�������ڵ��ࡣ
 * 
 * invoke���ڵ������ʵ��InvocationHandler�ӿڡ�
 */

public class InvokeClass implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println(".......................");
		return null;
	}

}
