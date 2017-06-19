package csdc.tool.execution.importer;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.InstpApplication;
import csdc.bean.InstpMember;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.PostApplication;
import csdc.bean.PostMember;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.PostProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.GeneralApplicationMemberParser;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2013年后期资助项目申请数据.xls》
 * 
 * @author maowh
 *
 */

public class PostProjectApplication2013Importer extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private AcademicFinder academicFinder;


	@Autowired
	private SystemOptionDao systemOptionDao;
	
	
	
	/**
	 * 项目年度
	 */
	private final int year = 2014;
	
	
	
	
	
	@Override
	protected void work() throws Exception {
		//validate();
		importData();				
	}
	
	private void resetReader() {
		reader.readSheet(0);		
	}	


	private void importData() throws Exception {
		resetReader();
		
		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'");
		SystemOption 一般 = systemOptionDao.query("projectType", "033");
		SystemOption 重大 = systemOptionDao.query("projectType", "032");
		SystemOption 重点 = systemOptionDao.query("projectType", "031");
		
		while (next(reader)) if (!A.isEmpty()) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			PostApplication application = new PostApplication();
			Agency university = universityFinder.getUnivByName(B);
			Teacher applicant = univPersonFinder.findTeacher(C, university);
			Academic academic = academicFinder.findAcademic(applicant.getPerson());
			if (academic == null) {
				academic = new Academic();
				academic.setPerson(applicant.getPerson());
				applicant.getPerson().setAcademic(academic);
			}
			
			application.setYear(year);
			application.setName(D);
			application.setUniversity(university);
			application.setAgencyName(university.getName());
			application.setApplicantId(applicant.getPerson().getId());
			application.setApplicantName(applicant.getPerson().getName());
//			application.setDisciplineType(C);
//			application.setDiscipline(tool.transformDisc(C));
			
			if (E.contains("一般")) {
				application.setSubtype(一般);
			} else if (E.contains("重点")) {
				application.setSubtype(重点);
			} else if (E.contains("重大")) {
				application.setSubtype(重大);
			}
			
			application.setFinalAuditStatus(3);
			application.setFinalAuditor(刘杰);
			application.setFinalAuditorName(刘杰.getPerson().getName());
			application.setFinalAuditorAgency(刘杰.getAgency());
			application.setFinalAuditorInst(刘杰.getInstitute());
			application.setFinalAuditStatus(3);
			application.setFinalAuditResult(1);
			application.setFinalAuditDate(tool.getDate(2014,4,17));
			application.setImportedDate(new Date());
			application.setIsImported(1);
			
//			if (D.contains("学校通过")) {
//				application.setDeptInstAuditResult(2);
//				application.setDeptInstAuditStatus(3);
//				application.setUniversityAuditResult(2);	
//				application.setUniversityAuditStatus(3);
//			} else if (D.contains("主管部门通过")) {
//				application.setDeptInstAuditResult(2);
//				application.setDeptInstAuditStatus(3);
//				application.setUniversityAuditResult(2);	
//				application.setUniversityAuditStatus(3);
//				application.setProvinceAuditResult(2);
//				application.setProvinceAuditStatus(3);
//			}
//			beanFieldUtils.setField(academic, "specialityTitle", G, BuiltinMergeStrategies.REPLACE);
			
			PostMember postMember = new PostMember();
			application.addMember(postMember);
			Person person = applicant.getPerson();
			postMember.setUniversity(applicant.getUniversity());
			postMember.setInstitute(applicant.getInstitute());
			postMember.setDepartment(applicant.getDepartment());
			postMember.setAgencyName(applicant.getUniversity().getName());
			if (applicant.getDepartment() != null) {
				postMember.setDivisionName(applicant.getDepartment().getName());
				postMember.setDivisionType(2);
			} else {
				postMember.setDivisionName(applicant.getInstitute().getName());
				postMember.setDivisionType(1);
			}
			postMember.setWorkMonthPerYear(applicant.getWorkMonthPerYear());
			postMember.setMemberType(1);
			postMember.setMember(person);
			postMember.setMemberName(person.getName());
			postMember.setIsDirector(1);
			postMember.setMemberSn(application.getMember().size());
			postMember.setIdcardType(person.getIdcardType());
			postMember.setIdcardNumber(person.getIdcardNumber());
			postMember.setGender(person.getGender());			
			postMember.setGroupNumber(1);
			dao.addOrModify(application);
			
//			//所在学校联系人信息
//			Officer slinkMan = null;
//			if (H.length() > 0) {
//				slinkMan = univPersonFinder.findOfficer(H, university);
//				//设置该管理员从属于该校的社科管理部门
//				slinkMan.setOrgan(2);
//				university.setSlinkman(slinkMan.getPerson());
//				
//				beanFieldUtils.setField(slinkMan.getPerson(), "officePhone", I, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
//				beanFieldUtils.setField(slinkMan.getPerson(), "mobilePhone", J, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
//				beanFieldUtils.setField(university, "sphone", I, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
//				
//			}
		}
	}

	private void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		HashSet nullDiscipline = new HashSet();
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(E);
			if (university == null) {
				exMsg.add("不存在的高校: " + E);
			}					
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}	
	}
	
	public PostProjectApplication2013Importer(){
	}
	
	public PostProjectApplication2013Importer(String file) {
		reader = new ExcelReader(file);
	}


	
}
