package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralVariation;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.DepartmentFinder;

/**
 * 修复generalApplication generalGranted generalVariation 表的涉及divisionName为department.name，如果department为空，则先修为其他院系，再修复divisionName
 * @author xuhan
 *
 */
public class FixDepartmentField extends Execution {

	@Autowired
	private DepartmentFinder departmentFinder;

	@Autowired
	private IHibernateBaseDao dao;

	@Override
	protected void work() throws Throwable {
		List<GeneralApplication> gas = dao.query("select ga from GeneralApplication ga left join ga.department dept where dept is null or dept.name <> ga.divisionName");
		for (GeneralApplication ga : gas) {
			if (ga.getDepartment() == null && ga.getInstitute() == null) {
				ga.setDepartment(departmentFinder.getDepartment(ga.getUniversity(), null, false));
			}
			if (ga.getDepartment() != null) {
				ga.setDivisionName(ga.getDepartment().getName());
			}
		}
		dao.flush();
		dao.clear();
		

		List<GeneralGranted> ggs = dao.query("select gg from GeneralGranted gg left join gg.department dept where dept is null or dept.name <> gg.divisionName");
		for (GeneralGranted gg : ggs) {
			if (gg.getDepartment() == null && gg.getInstitute() == null) {
				gg.setDepartment(departmentFinder.getDepartment(gg.getUniversity(), null, false));
			}
			if (gg.getDepartment() != null) {
				gg.setDivisionName(gg.getDepartment().getName());
			}
		}
		dao.flush();
		dao.clear();
		
		
		List<GeneralVariation> gvs = dao.query("select gv from GeneralVariation gv where gv.changeAgency = 1");
		for (GeneralVariation gv : gvs) {
			if (gv.getOldDepartment() == null && gv.getOldInstitute() == null) {
				gv.setOldDepartment(departmentFinder.getDepartment(gv.getOldAgency(), null, false));
			}
			if (gv.getOldDepartment() != null) {
				gv.setOldDivisionName(gv.getOldDepartment().getName());
			}

			if (gv.getNewDepartment() == null && gv.getNewInstitute() == null) {
				gv.setNewDepartment(departmentFinder.getDepartment(gv.getNewAgency(), null, false));
			}
			if (gv.getNewDepartment() != null) {
				gv.setNewDivisionName(gv.getNewDepartment().getName());
			}
		}
		dao.flush();
		dao.clear();
		
	}

}
