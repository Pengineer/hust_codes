package edu.hust.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import edu.hust.service.impl.PersonServiceBean;

/**
 * 使用java本身提供的动态代理机制中最重要的两个类：
 *     1，Proxy:用户创建目标对象的代理对象。
 *     2，InvocationHandler：拥有回调方法。在回调方法中用户可判断是否执行目标方法。
 */

public class JDKProxyFactory implements InvocationHandler {
	private Object targetObject;  //使用动态代理的好处之一就是可以为任意的类创建代理类
	
	/*
	 * 创建目标对象的代理对象
	 * 第一个参数：目标对象的类加载器。
	 * 第二个参数：目标对象实现的所有接口类。
	 * 第三个参数：回调对象。this表示创建完代理对象后，通过代理对象调用目标对象的方法时，会触发回调执行本类中的invoke方法，
	 */
	public Object createProxyObject(Object targetObject){
		this.targetObject = targetObject;
		return Proxy.newProxyInstance(this.targetObject.getClass().getClassLoader(), 
				this.targetObject.getClass().getInterfaces(), this);
	}
	
	/*
	 * 通过代理对象来调用目标对象的实现方法：调用之前就可以判断权限问题
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		
		if(this.targetObject instanceof PersonServiceBean){
			PersonServiceBean bean = (PersonServiceBean) this.targetObject;
			if(bean.getUser() != null){
				//........前置通知
				try{
					result = method.invoke(targetObject, args);
					//.........后置通知
				}catch(RuntimeException e){
					//.........例外通知
				}finally{
					//.........最终通知
				}
			}
		}
		
		return result;
	}
}
