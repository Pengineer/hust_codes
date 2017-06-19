package edu.hust.service.impl;

import edu.hust.dao.PersonDao;
import edu.hust.service.PersonService;

public class PersonServiceBean implements PersonService {
	private PersonDao personDao;  //��service��ע��Dao��Ķ���ͨ������set�����ķ�ʽ��

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	@Override
	public void save(){
		personDao.add();   //���ǲ�������personDao��ʵ���ֱ࣬�ӵ��ýӿڵķ����Ϳ����ˡ�
	}
	
	/*
	 * �������ʵ�ֹ��̣�Spring�����ڳ�ʼʵ������ʱ�򣬻��ȡSpring��Xml�����ļ���Ȼ��ʵ���������ļ��������bean���󣬵�ʵ����
	 * id="personService"��beanʱ����������property�ӱ�ǩ����ôSpring�ͻ�����ӱ�ǩ��refΪ������ע��һ���Ѿ�ʵ������id=ref��bean����
	 * �����ͽ������е�PersonDaoBean����ע�����PersonServiceBean��personDao���ԡ�
	 * 
	 * ����ע�뷽ʽֻ������scope="singleton"�������Ҳ����˵���е�bean��������������ʵ������ʵ��������������Եĵ���ע��ʧ�ܣ���ָ���쳣��
	 */
	
	
	
   /* 
    * ��ʹ��Spring�ķ�ʽ����service�㴴��PersonDaoBean����
    private PersonDao personDao = new PersonDaoBean();
	
    public void save(){
       personDao.add();
   }*/

}
