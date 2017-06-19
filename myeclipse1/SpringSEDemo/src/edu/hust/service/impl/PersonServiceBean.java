package edu.hust.service.impl;

import edu.hust.dao.PersonDao;
import edu.hust.service.PersonService;

public class PersonServiceBean implements PersonService {
	private PersonDao personDao;  //在service层注入Dao层的对象（通过属性set方法的方式）

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	@Override
	public void save(){
		personDao.add();   //我们并不关心personDao的实现类，直接调用接口的方法就可以了。
	}
	
	/*
	 * 上面具体实现过程：Spring容器在初始实例化的时候，会读取Spring的Xml配置文件，然后实例化配置文件里的所有bean对象，当实例化
	 * id="personService"的bean时，由于它有property子标签，那么Spring就会根据子标签的ref为该属性注入一个已经实例化的id=ref的bean对象，
	 * 这样就将容器中的PersonDaoBean对象注入给了PersonServiceBean的personDao属性。
	 * 
	 * 属性注入方式只适用于scope="singleton"的情况，也就是说所有的bean必须随着容器的实例化而实例化，否则会属性的导致注入失败，空指针异常。
	 */
	
	
	
   /* 
    * 不使用Spring的方式：在service层创建PersonDaoBean对象。
    private PersonDao personDao = new PersonDaoBean();
	
    public void save(){
       personDao.add();
   }*/

}
