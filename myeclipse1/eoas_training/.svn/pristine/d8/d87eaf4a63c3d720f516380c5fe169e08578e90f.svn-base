package csdc.service;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import csdc.bean.Department;


public interface IDepartmentService {

	public List<Department> listAll();// 查找所有部门

	public List<Department> selectTopList();// 查找所有顶级部门

	public List<Department> selectChirdren(String parentId);// 查找子部门

	public Department selectById(String id);

	public void add(Department department);

	public void delete(String id);

	public void modify(Department department);
	
}
