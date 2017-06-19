package csdc.tool.execution.fix;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Department;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 修复院系名称
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class FixDepartmentName extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Override
	protected void work() throws Throwable {
		int batchSize = 10000;
		for (int i = 0; ; i += batchSize) {
			List<Department> depts = dao.query("from Department dept order by dept.id", i, batchSize);
			if (depts.isEmpty()) {
				break;
			}
			
			System.out.println(i + " - " + (i + batchSize - 1));

			batchWork(depts);

			dao.flush();
			dao.clear();
		}

	}

	private void batchWork(List<Department> depts) {
		for (Department dept : depts) {
			String name = dept.getName();
			
			//1.删除学校子串及前面的东西
			String univName = dept.getUniversity().getName();
			int univNameIndex = name.indexOf(univName);
			if (univNameIndex >= 0) {
				name = name.substring(univNameIndex).replace(univName, "");
			}
			
			
			//2.删除包含下列怪异字符的前缀： 
			name = name.replaceAll("^[　\\s,，\\.。;；/\\\\、·\\-—]+", "");

			
			//3.删除下列子串：
			//（）
			//()
			name = name.replace("()", "").replace("（）", "");
			
			
			//4.删除省份前缀
			String provinveName = dept.getUniversity().getProvince().getName();
			if (name.startsWith(provinveName)) {
				name = name.replace(provinveName, "");
			}

			
			//5.trim()
			name = name.trim();

			
			//6.长度小于2的变成：其他院系
			if (name.length() < 2) {
				name = "其他院系";
			}
			
			
			if (!dept.getName().equals(name)) {
				System.out.println(dept.getName() + " - " + name);
				dept.setName(name);
			}
		}
	}

}
