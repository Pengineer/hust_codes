package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 院系查找辅助类
 * @author xuhan
 *
 */
@Component
public class DepartmentFinder {
	
	/**
	 * [学校代码+院系名称]到[院系ID]的映射
	 */
	private Map<String, String> deptMap;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	/**
	 * 置空辅助类，释放部分内存
	 */
	public void reset() {
		deptMap = null;
	}
	
	/**
	 * 根据[学校]和[院系名称]找到院系实体
	 * @param university
	 * @param departmentName
	 * @param addIfNotFound 如果不存在此院系，是否向库内添加一个
	 * @return
	 */
	public Department getDepartment(Agency university, String departmentName, boolean addIfNotFound) {
		if (university.getType() != 3 && university.getType() != 4) {
			throw new RuntimeException("传入的agency[" + university.getName() + "]不是学校!");
		}
		if (deptMap == null) {
			initDeptMap();
		}
		if (departmentName == null) {
			departmentName = "";
		}
		int univNameIndex = departmentName.lastIndexOf(university.getName());
		if (univNameIndex >= 0) {
			departmentName = departmentName.substring(univNameIndex).replace(university.getName(), "");
		}
		if (departmentName.isEmpty()) {
			departmentName = "其他院系";
		}
		
		Department department = null;
		String key = university.getCode() + StringTool.fix(departmentName);
		String departmentId = deptMap.get(key);
		if (departmentId != null) {
			department = (Department) dao.query(Department.class, departmentId);
		}
		if (department == null && addIfNotFound) {
			department = new Department();
			department.setName(departmentName);
			department.setUniversity(university);
			
			deptMap.put(key, dao.add(department));
		}
		return department;
	}
	
	/**
	 * 初始化[学校代码+院系名称]到[院系ID]的映射
	 * @return
	 */
	public void initDeptMap() {
		long beginTime = new Date().getTime();

		deptMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select d.university.code, d.name, d.id from Department d");
		for (Object[] str : list) {
			String univCode = (String) str[0];
			String deptName = (String) str[1];
			if (!deptName.isEmpty()) {
				deptMap.put(univCode + StringTool.fix(deptName), (String) str[2]);
			}
		}

		System.out.println("initDeptMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
 

}
