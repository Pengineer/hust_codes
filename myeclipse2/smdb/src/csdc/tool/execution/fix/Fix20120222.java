package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Department;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 
 * 修正名称中含有“其他”或者“其它”的院系名称为“其他院系”
 * @author xuhan
 *
 */
public class Fix20120222 extends Execution {

	@Autowired
	private IHibernateBaseDao dao;

	@Override
	protected void work() throws Throwable {
		List<Department> depts = dao.query("from Department dept where dept.name like '%其他%' or dept.name like '%其它%'");
		for (Department department : depts) {
			department.setName("其他院系");
		}
	}

}
