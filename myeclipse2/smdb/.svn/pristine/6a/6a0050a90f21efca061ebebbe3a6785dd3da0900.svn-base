package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.dao.IHibernateBaseDao;
import csdc.tool.beanutil.mergeStrategy.Append;
import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralMember;
import csdc.bean.Institute;
import csdc.bean.Person;

/**
 * 修正一般项目成员表的多个字段值
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class FixGeneralMember extends Execution {
	
	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private UniversityFinder universityFinder;

	@Autowired
	private InstituteFinder instituteFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;
	
	@Autowired
	private IHibernateBaseDao dao;
	

	@Override
	protected void work() throws Throwable {
		int batchSize = 20000;
		for (int i = 0; ; i += batchSize) {
			List<GeneralMember> gms = dao.query("from GeneralMember gm order by gm.id", i, batchSize);
			if (gms.isEmpty()) {
				break;
			}

			batchWork(i, gms);

			dao.flush();
			dao.clear();
		}
	}
	

	public void batchWork(int cnt, List<GeneralMember> gms) throws Throwable {
		
		for (int i = 0; i < gms.size(); i++) {
			GeneralMember gm = gms.get(i);
			System.out.println(cnt + " " + i + " / " + gms.size());
			
			Agency agency = gm.getUniversity();
			if (agency == null) {
				agency = universityFinder.getUniversityWithLongestName(gm.getAgencyName());
			}
			if (agency == null) {
				agency = universityFinder.getUniversityWithLongestName(gm.getDivisionName());
			}
			if (agency == null) {
				String agencyName = (gm.getAgencyName() == null ? "" : gm.getAgencyName()).replace("未知机构", "");
				String divisionName = (gm.getDivisionName() == null ? "" : gm.getDivisionName()).replace("未知机构", "");
				gm.setAgencyName((String) new Append().merge(agencyName, divisionName));
				if (gm.getAgencyName().isEmpty()) {
					gm.setAgencyName("未知机构");
				}
				gm.setDivisionName(null);
				gm.setUniversity(null);
				gm.setDepartment(null);
				gm.setInstitute(null);
				gm.setMemberType(0);
				continue;
			}
			
			String divisionName1 = removeSubStringWithPrefix(gm.getAgencyName(), agency.getName()).replace("未知机构", "");
			String divisionName2 = removeSubStringWithPrefix(gm.getDivisionName(), agency.getName()).replace("未知机构", "");
			gm.setDivisionName(divisionName1.length() > divisionName2.length() ? divisionName1 : divisionName2);
			gm.setUniversity(agency);
			gm.setAgencyName(agency.getName());
			
			gm.setDepartment(null);
			gm.setInstitute(null);
			Institute institute = instituteFinder.getInstitute(agency, gm.getDivisionName(), false);
			Department department = null;
			if (institute != null) {
				gm.setInstitute(institute);
			} else {
				department = departmentFinder.getDepartment(agency, gm.getDivisionName(), true);
				gm.setDepartment(department);
				if (gm.getDivisionName().isEmpty()) {
					//这里当院系为其他院系时，有可能gm.divisionName为空
					gm.setDivisionName(department.getName());
				}
			}
			
			if (gm.getMemberName() != null && !gm.getMemberName().isEmpty()) {
				Person member = univPersonFinder.findTeacher(gm.getMemberName(), agency, department, institute).getPerson();
				gm.setMember(member);
			}
			gm.setMemberType(1);
		}
	}

	
	/**
	 * 把子串和前面的串都去掉
	 * @param string
	 * @param univName
	 * @return
	 */
	private String removeSubStringWithPrefix(String string, String subString) {
		if (string == null) {
			string = "";
		}
		int index = string.indexOf(subString);
		if (index >= 0) {
			string = string.substring(index).replace(subString, "");
		}
		return string;
	}

}
