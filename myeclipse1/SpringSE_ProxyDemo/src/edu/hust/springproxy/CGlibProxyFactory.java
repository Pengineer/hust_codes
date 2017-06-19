package edu.hust.springproxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import edu.hust.service.impl.PersonServiceBean;

/**
 * 使用Spring提供的动态字节码生成技术（CGlib）中最重要的两个类：
 *     1，Enhancer:代理类，可产生任意目标对象的代理对象。（其实就是产生一个目标对象的子类，这也是为什么目标类不用实现接口的原因）
 *     2，MethodInterceptor：拥有回调方法。在回调方法中用户可判断是否执行目标方法。
 *     
 * Spring的拦截机制就是通过动态代理实现的
 */
public class CGlibProxyFactory implements MethodInterceptor{
	
	private Object targetObject;
	
	/*
	 * 创建目标对象的代理对象。
	 */
	public Object createProxyObject(Object targetObject){
		this.targetObject = targetObject;
		Enhancer enhancer = new Enhancer();  //创建代理实例
		enhancer.setSuperclass(this.targetObject.getClass()); //设置目标类为父类
		enhancer.setCallback(this);      //设置回调函数在本类中（this就相当于动态代理中的执行器handler）
		return enhancer.create();        //返回创建的代理对象：enhancer会产生一个目标对象的子类，该子类会覆盖所有的非final方法并添加新的代码。
	}

	/*
	 * 在其它类中通过调用代理对象的方法来触发回调：在intercept回调方法中就可以判断权限问题，以决定是否执行目标对象的方法
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		Object result = null;
		if(this.targetObject instanceof PersonServiceBean){
			PersonServiceBean bean = (PersonServiceBean) this.targetObject;
			if(bean.getUser() != null){//省略前置通知，例外通知，后置通知，最终通知
				result = methodProxy.invoke(targetObject, args);
			}
		}
		return result;
	}
}
