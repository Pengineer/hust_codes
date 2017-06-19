package edu.hust.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 有时候我们会将代理类的生成与回调的invoke方法分开写，这个时候Proxy.newProxyInstance的第三个参数就是invoke方法所在的类。
 * 
 * invoke所在的类必须实现InvocationHandler接口。
 */

public class InvokeClass implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println(".......................");
		return null;
	}

}
