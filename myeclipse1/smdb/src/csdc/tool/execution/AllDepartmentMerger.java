package csdc.tool.execution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.dao.IHibernateBaseDao;
import csdc.tool.merger.DepartmentMerger;

/**
 * 合并所有需要合并的院系
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class AllDepartmentMerger extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private DepartmentMerger departmentMerger;
	
	@Override
	protected void work() throws Throwable {
		List<Object []> deptInfo = dao.query("select dept.id, dept.name, dept.university.name from Department dept");

		for (Object[] objects : deptInfo) {
			objects[1] = ((String)objects[1]).replace((String) objects[2], "").replace("[待删除]", "").replace("　", "").trim();
			if ("".equals(objects[1])) {
				objects[1] = "其他院系";
			}
		}
		
		Collections.sort(deptInfo, new Comparator<Object []>() {
			public int compare(Object [] a, Object [] b) {
				String univName1 = (String) a[2];
				String univName2 = (String) b[2];
				String deptName1 = (String) a[1];
				String deptName2 = (String) b[1];
				if (!univName1.equals(univName2)) {
					return univName1.compareTo(univName2);
				} else {
					return deptName1.compareTo(deptName2);
				}
			}
		});
		
		//同一学校、名字相同的院系作为一组，合并
		for (int begin = 0; begin < deptInfo.size();) {
			int end = begin + 1;
			while (end < deptInfo.size() && deptInfo.get(end)[2].equals(deptInfo.get(begin)[2]) && deptInfo.get(end)[1].equals(deptInfo.get(begin)[1])) {
				++end;
			}

			if (end > begin + 1) {
				System.out.println((end - begin) + "\t: " + deptInfo.get(begin)[2] + " - " + deptInfo.get(begin)[1]);
				
				List<Serializable> incomeDeptId = new ArrayList<Serializable>();
				for (int i = begin + 1; i < end; i++) {
					incomeDeptId.add((Serializable) deptInfo.get(i)[0]);
				}
				
				//如果直接调用this.mergeDepartment，相当于简单的方法调用，绕过了Spring的外围事务配置，因此需要裹一层再调用。
				//departmentMerger.mergeDepartment使用REQUIRES_NEW，实现每批院系的合并本身为一个独立事务，如果成功可以单独提交。
				try {
					departmentMerger.mergeDepartment((Serializable) deptInfo.get(begin)[0], incomeDeptId);
				} catch (Exception e) {
					//如果一个批次的院系合并失败，不停止，继续合并其他院系
					System.out.println("院系合并失败：" + deptInfo.get(begin)[2] + " " + deptInfo.get(begin)[1]);
					e.printStackTrace();
				}
			}

			begin = end;
		}
	}

}
