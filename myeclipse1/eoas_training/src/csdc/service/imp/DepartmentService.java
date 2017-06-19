package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import csdc.bean.Department;
import csdc.dao.IBaseDao;
import csdc.mappers.DepartmentMapper;
import csdc.service.IDepartmentService;


public class DepartmentService implements IDepartmentService {
	private IBaseDao baseDao;
	private DepartmentMapper departmentDao;
	
	
	public IBaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public DepartmentMapper getDepartmentDao() {
		return departmentDao;
	}
	public void setDepartmentDao(DepartmentMapper departmentDao) {
		this.departmentDao = departmentDao;
	}
	@Override
	public List<Department> listAll() {
		return departmentDao.listAll();
	}
	@Override
	public Department selectById(String id) {
		return departmentDao.selectById(id);
	}
	@Override
	public void add(Department department) {
		this.departmentDao.add(department);
	}
	@Override
	public List<Department> selectTopList() {
		return departmentDao.selectTopList();
	}
	@Override
	public List<Department> selectChirdren(String parentId) {
		return departmentDao.selectChirdren(parentId);
	}
	@Override
	public void delete(String id) {
		this.departmentDao.delete(id);
	}
	@Override
	public void modify(Department department) {
		this.departmentDao.modify(department);
	}
}
