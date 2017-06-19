package edu.hust.springproxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import edu.hust.service.impl.PersonServiceBean;

/**
 * ʹ��Spring�ṩ�Ķ�̬�ֽ������ɼ�����CGlib��������Ҫ�������ࣺ
 *     1��Enhancer:�����࣬�ɲ�������Ŀ�����Ĵ�����󡣣���ʵ���ǲ���һ��Ŀ���������࣬��Ҳ��ΪʲôĿ���಻��ʵ�ֽӿڵ�ԭ��
 *     2��MethodInterceptor��ӵ�лص��������ڻص��������û����ж��Ƿ�ִ��Ŀ�귽����
 *     
 * Spring�����ػ��ƾ���ͨ����̬����ʵ�ֵ�
 */
public class CGlibProxyFactory implements MethodInterceptor{
	
	private Object targetObject;
	
	/*
	 * ����Ŀ�����Ĵ������
	 */
	public Object createProxyObject(Object targetObject){
		this.targetObject = targetObject;
		Enhancer enhancer = new Enhancer();  //��������ʵ��
		enhancer.setSuperclass(this.targetObject.getClass()); //����Ŀ����Ϊ����
		enhancer.setCallback(this);      //���ûص������ڱ����У�this���൱�ڶ�̬�����е�ִ����handler��
		return enhancer.create();        //���ش����Ĵ������enhancer�����һ��Ŀ���������࣬������Ḳ�����еķ�final����������µĴ��롣
	}

	/*
	 * ����������ͨ�����ô������ķ����������ص�����intercept�ص������оͿ����ж�Ȩ�����⣬�Ծ����Ƿ�ִ��Ŀ�����ķ���
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		Object result = null;
		if(this.targetObject instanceof PersonServiceBean){
			PersonServiceBean bean = (PersonServiceBean) this.targetObject;
			if(bean.getUser() != null){//ʡ��ǰ��֪ͨ������֪ͨ������֪ͨ������֪ͨ
				result = methodProxy.invoke(targetObject, args);
			}
		}
		return result;
	}
}
