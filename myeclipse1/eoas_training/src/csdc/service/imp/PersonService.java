package csdc.service.imp;

import java.util.List;

import csdc.bean.Person;
import csdc.bean.PersonRoleDepartment;
import csdc.mappers.PersonMapper;
import csdc.service.IPersonService;


public class PersonService implements IPersonService {

	private PersonMapper personDao;

	// ---get & set---
	public PersonMapper getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonMapper personDao) {
		this.personDao = personDao;
	}

	@Override
	public List<Person> listAll() {
		return personDao.listAll();
	}

	@Override
	public List<Person> listPersonAccount() {
		return personDao.listPersonAccount();
	}

	@Override
	public void add(Person person) {
		this.personDao.add(person);
	}

	@Override
	public Person selectById(String id) {
		return personDao.selectById(id);
	}

	@Override
	public void modify(Person person) {
		this.personDao.modify(person);
	}

	@Override
	public void delete(String id) {
		this.personDao.delete(id);
	}

	@Override
	public Person listPersonRole() {
		return personDao.listPersonRole();
	}
}
