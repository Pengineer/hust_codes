package csdc.mappers;

import java.util.List;

import csdc.bean.Department;


public interface DepartmentMapper {
	/**
	 * Description:插入一个部门
	 * 
	 * @param department
	 */
	public void add(Department department);

	/**
	 * Description:根据id删除一个部门
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * Description: 部门信息修改
	 * 
	 * @param department
	 */
	public void modify(Department department);

	/**
	 * Description: 查询所有部门
	 * 
	 * @return:List<Department>
	 */
	public List<Department> listAll();

	/**
	 * Description: 根据id查询部门详情
	 * 
	 * @param id
	 * @return Department
	 */
	public Department selectById(String id);

	public List<Department> selectTopList();// 查询顶级部门

	public List<Department> selectChirdren(String parentID);// 查询子部门

}
