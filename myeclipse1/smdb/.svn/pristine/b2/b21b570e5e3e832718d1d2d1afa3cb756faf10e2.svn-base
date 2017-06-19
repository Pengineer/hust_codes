package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
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
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2013、2014专项项目_高校思想政治理论课专项_修正导入.xls》
 * 
 * @author maowh
 *
 */

public class SpecialIPTEApplicationAndGranted2013_2014Importer extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private AcademicFinder academicFinder;

	@Autowired
	private DepartmentFinder departmentFinder;

	@Autowired
	private SystemOptionDao systemOptionDao;
	
	
	
	@Override
	protected void work() throws Exception {
//		validate();
//		importApplicationData();
		importGrantedData();//执行完上一个导申请数据的代码之后再执行
	}	


	private void importApplicationData() throws Exception {
		reader.readSheet(0);
		
		Officer 宋义栋 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '宋义栋'");
		SystemOption 专项任务项目 = systemOptionDao.query("projectType", "014");
		
		while (next(reader)) if (!A.isEmpty()) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			GeneralApplication application = new GeneralApplication();
			Agency university = universityFinder.getUnivByName(B);
			Department department = departmentFinder.getDepartment(university, D, true);
			Teacher applicant = univPersonFinder.findTeacher(A.replaceAll("\\s+", ""), university);
			Academic academic = academicFinder.findAcademic(applicant.getPerson());
			if (academic == null) {
				academic = new Academic();
				academic.setPerson(applicant.getPerson());
				applicant.getPerson().setAcademic(academic);
			}
			
			application.setDepartment(department);
			application.setDivisionName(department.getName());
			application.setYear(Integer.valueOf(F));
			application.setName(E);
			application.setUniversity(university);
			application.setAgencyName(university.getName());
			application.setApplicantId(applicant.getPerson().getId());
			application.setApplicantName(applicant.getPerson().getName());
			application.setSubtype(专项任务项目);

			
			application.setFinalAuditStatus(3);
			application.setFinalAuditor(宋义栋);
			application.setFinalAuditorName(宋义栋.getPerson().getName());
			application.setFinalAuditorAgency(宋义栋.getAgency());
			application.setFinalAuditorInst(宋义栋.getInstitute());
			application.setFinalAuditStatus(3);
			application.setFinalAuditResult(1);
			application.setApplicantSubmitDate(tool.getDate(G));
			application.setFinalAuditDate(tool.getDate(H));
			application.setImportedDate(new Date());
			application.setIsImported(1);
			
			GeneralMember generalMember = new GeneralMember();
			application.addMember(generalMember);
			Person person = applicant.getPerson();
			generalMember.setUniversity(applicant.getUniversity());
			generalMember.setInstitute(applicant.getInstitute());
			generalMember.setDepartment(applicant.getDepartment());
			generalMember.setAgencyName(applicant.getUniversity().getName());
			if (applicant.getDepartment() != null) {
				generalMember.setDivisionName(applicant.getDepartment().getName());
				generalMember.setDivisionType(2);
			} else {
				generalMember.setDivisionName(applicant.getInstitute().getName());
				generalMember.setDivisionType(1);
			}
			generalMember.setWorkMonthPerYear(applicant.getWorkMonthPerYear());
			generalMember.setMemberType(1);
			generalMember.setMember(person);
			generalMember.setMemberName(person.getName());
			generalMember.setIsDirector(1);
			generalMember.setMemberSn(application.getMember().size());
			generalMember.setIdcardType(person.getIdcardType());
			generalMember.setIdcardNumber(person.getIdcardNumber());
			generalMember.setGender(person.getGender());			
			generalMember.setGroupNumber(1);
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
	
	private void importGrantedData() throws Exception {
		reader.readSheet(1);
		
		SystemOption 专项任务项目 = systemOptionDao.query("projectType", "014");
		
		departmentFinder.initDeptMap();

		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());

			GeneralApplication application = generalProjectFinder.findApplication(A.replaceAll("\\s+", ""),Integer.valueOf(F));
			GeneralGranted granted = null;
			Agency university = universityFinder.getUnivByName(B);
			
			granted = new GeneralGranted();
			granted.setStatus(1);
			granted.setIsImported(1);
			application.addGranted(granted);

			
			granted.setUniversity(university);
			granted.setAgencyName(application.getAgencyName());
			
			if (reader.getCurrentRowIndex() == 72) {
				System.out.println("ok");
			}
			
			if (application.getDepartment() != null && (application.getDepartment().getUniversity().getName().equals(university.getName()))) {
				granted.setDepartment(application.getDepartment());
				granted.setDivisionName(application.getDivisionName());
			} else {
				Department otherDepartment = departmentFinder.getDepartment(university, null, true);
				granted.setDepartment(otherDepartment);
				granted.setDivisionName(otherDepartment.getName());
			}
			
			granted.setApplicantId(application.getApplicantId());
			granted.setApplicantName(application.getApplicantName());
			granted.setMemberGroupNumber(1);
			granted.setSubtype(专项任务项目);
			granted.setNumber(D);
			granted.setName(application.getName());
			granted.setApproveFee(Double.valueOf(E));
		
//			//若没有任何拨款信息，则导入首批拨款
//			if (granted.getFunding() == null || granted.getFunding().isEmpty()) {
//				GeneralFunding funding = new GeneralFunding();
//				funding.setDate(tool.getDate(2012, 4, 1));
//				funding.setFee(tool.getFee(K));
//				granted.addFunding(funding);
//			}
			
			granted.getApplication().setFinalAuditResult(2);
			dao.addOrModify(application);
			dao.addOrModify(granted);
		}
		
	
		
	}

	private void validate() throws Exception {
		reader.readSheet(0);
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			if (A.isEmpty()) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			Agency university = universityFinder.getUnivByName(B);
			if (university == null) {
				exMsg.add("不存在的高校: " + B);
			}					
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}	
	}
	
	public SpecialIPTEApplicationAndGranted2013_2014Importer(){
	}
	
	public SpecialIPTEApplicationAndGranted2013_2014Importer(String file) {
		reader = new ExcelReader(file);
	}


	
}

