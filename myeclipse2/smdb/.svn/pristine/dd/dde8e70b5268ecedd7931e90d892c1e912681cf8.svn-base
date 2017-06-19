package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Officer;
import csdc.bean.PostApplication;
import csdc.bean.PostMember;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.StringTool;
import csdc.tool.beanutil.mergeStrategy.Append;
import csdc.tool.beanutil.mergeStrategy.MergePhoneNumber;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.PostProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 导入《2010-2011年后期资助项目申报一览表_修正导入.xls》
 * @author xuhan
 *
 */
public class PostProjectApplication2010_2011Importer extends Importer {
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private PostProjectFinder postProjectFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;
	
	
	
	@Autowired
	private MergePhoneNumber mergePhoneNumber;
	
	@Autowired
	private Append append;
	

	public PostProjectApplication2010_2011Importer() {
	}

	public PostProjectApplication2010_2011Importer(String filePath) {
		super(filePath);
	}

	@Override
	protected void work() throws Throwable {
		testUniversityNames();
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		getContentFromExcel(0);
		
		Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();
		subTypeMap.put("一般项目", (SystemOption) dao.query(SystemOption.class, "postGeneral"));
		subTypeMap.put("重大项目", (SystemOption) dao.query(SystemOption.class, "postKey"));
		subTypeMap.put("重点项目", (SystemOption) dao.query(SystemOption.class, "postMajor"));

		Officer 王晓蕾 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '王晓蕾'");

		while (next()) {
			System.out.println(curRowIndex);
			
			/////////////////////////项目申报信息//////////////////////////
			if (postProjectFinder.findApplication(B, I) != null) {
				continue;
			}
			PostApplication pa = new PostApplication();

			pa.setYear(Integer.parseInt(A));
			pa.setIsReviewable(1);
			pa.setIsImported(1);
			pa.setImportedDate(new Date());
			
			pa.setName(B);
			pa.setSubtype(subTypeMap.get(C));
			pa.setDiscipline(tool.transformDisc(D));
			pa.setDisciplineType(D);
			
			Agency university = universityFinder.getUnivByName(E);
			pa.setAgencyName(university.getName());
			pa.setUniversity(university);

			/////////////////////////所在学校信息//////////////////////////

			Officer slinkMan = null;
			if (F.length() > 0) {
				slinkMan = univPersonFinder.findOfficer(F, university);
				//设置该管理员从属于该校的社科管理部门
				slinkMan.setOrgan(2);
				university.setSlinkman(slinkMan.getPerson());
				
				slinkMan.getPerson().setOfficePhone(mergePhoneNumber.merge(slinkMan.getPerson().getOfficePhone(), G));
				slinkMan.getPerson().setMobilePhone(mergePhoneNumber.merge(slinkMan.getPerson().getMobilePhone(), H));
				university.setSphone(mergePhoneNumber.merge(university.getSphone(), G));
			}
			
			/////////////////////////申报人信息//////////////////////////
			Department department = departmentFinder.getDepartment(university, K, true);
			Teacher applicant = univPersonFinder.findTeacher(I, department);
			Academic academic = applicant.getPerson().getAcademicEntity();
			academic.setSpecialityTitle(append.merge(academic.getSpecialityTitle(), J));
			pa.setApplicantId(applicant.getPerson().getId());
			pa.setApplicantName(applicant.getPerson().getName());

			pa.setDepartment(department);
			pa.setDivisionName(department.getName());
			
			if (L.length() > 0) {
				L += "-";
			}
			applicant.getPerson().setOfficePhone(mergePhoneNumber.merge(applicant.getPerson().getOfficePhone(), L + M));
			applicant.getPerson().setHomePhone(mergePhoneNumber.merge(applicant.getPerson().getHomePhone(), L + N));
			applicant.getPerson().setMobilePhone(mergePhoneNumber.merge(applicant.getPerson().getMobilePhone(), O));
			applicant.getPerson().setOfficeFax(mergePhoneNumber.merge(applicant.getPerson().getOfficeFax(), L + P));
			applicant.getPerson().setEmail(append.merge(applicant.getPerson().getEmail(), StringTool.toDBC(Q)));
			applicant.getPerson().setOfficeAddress(append.merge(applicant.getPerson().getOfficeAddress(), R));
			applicant.getPerson().setOfficePostcode(append.merge(applicant.getPerson().getOfficePostcode(), S));
			academic.setDiscipline(tool.transformDisc(academic.getDiscipline() + " " + T));
			academic.setResearchSpeciality(append.merge(academic.getResearchSpeciality(), U));
			
			PostMember pm = new PostMember();
			pa.addMember(pm);
			pm.setUniversity(university);
			pm.setDepartment(department);
			pm.setMember(applicant.getPerson());
			pm.setMemberName(applicant.getPerson().getName());
			pm.setAgencyName(university.getName());
			pm.setDivisionName(department.getName());
			pm.setSpecialistTitle(academic.getSpecialityTitle());
			pm.setIsDirector(1);
			pm.setMemberSn(1);
			pm.setMemberType(1);
			
			if (W.isEmpty()) {
				pa.setApplicantSubmitDate(tool.getDate(pa.getYear(), 3, 1));
				pa.setFinalAuditDate(tool.getDate(pa.getYear(), 3, 1));
			} else {
				pa.setApplicantSubmitDate(tool.getDate(W));
				pa.setFinalAuditDate(tool.getDate(W));
			}
			pa.setFinalAuditResult(1);
			pa.setFinalAuditStatus(3);
			pa.setFinalAuditor(王晓蕾.getPerson());
			pa.setFinalAuditorName(王晓蕾.getPerson().getName());
			pa.setFinalAuditorAgency(王晓蕾.getAgency());
			pa.setFinalAuditorInst(王晓蕾.getInstitute());

			
			//////////////////////////////////////////////////////////////
			saveOrUpdate(pa);
		}
	}

	/**
	 * 检测学校名合法性
	 * @throws Exception
	 */
	private void testUniversityNames() throws Exception {
		/**
		 * 不存在的学校名称
		 */
		HashSet<String> invalidUnivNames = new HashSet<String>();
		
		getContentFromExcel(0);
		Agency university = null;
		while (next()) {
			if (E.length() > 0) {
				university = universityFinder.getUnivByName(E);
				if (university == null) {
					invalidUnivNames.add(E);
				}
			}
		}
		
		if (invalidUnivNames.size() > 0) {
			for (Iterator<String> iterator = invalidUnivNames.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				System.err.println("不存在的学校: " + string);
			}
			throw new Exception();
		}
		System.out.println("学校名称正常");
		
	}
}
