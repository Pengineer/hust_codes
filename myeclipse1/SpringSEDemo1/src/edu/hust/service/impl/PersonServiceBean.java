package edu.hust.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.hust.dao.PersonDao;
import edu.hust.service.PersonService;

/*
 * Spring��⵽Bean��@Component���ͻὫ��Beanʵ�������ҽ���Spring������������ͨ��Spring����ȡ��Bean��ʵ������
 * 
 * ʹ��@Component������ע����������û����Xml����<bean>ʱ��id����ô��getBean(String beanName)��ȡ��Beanʱ��
 * beanName��Ĭ��ȡֵ�ǽ�class��������һ����ĸСд����Ȼ���ǿ�����@Component��������id��
 * Ϊ��ͳһ�淶��������Xml������<bean>ʱ��idҲ��һ����ô��д�ġ�
 */

@Component   //���Լ� @Scope("prototype")��ָ��ÿ��getBean�õ�����һ��ȫ�µ�ʵ������
public class PersonServiceBean implements PersonService {
	
	/*
	 * ���PersonDao������ʵ�����౻@Componentע�ͻ�����Xml����������(�ѱ�����Spring��������)����ô�Ϳ���ʹ��@Autowired�������personDao����ע��ʵ������
	 */
	@Autowired
	private PersonDao personDao;
	
	public void service(){
		personDao.add();
	}
}
