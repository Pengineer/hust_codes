package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.SpecialApplication;
import csdc.bean.SpecialGranted;
import csdc.bean.SpecialMember;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.SpecialProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 附件：《2015年教育部人文社会科学专项任务项目（中国特色社会主义理论体系研究）立项一览表_修正一览表.xls》
 * 附件：《2015年教育部人文社会科学专项任务项目（中国特色社会主义理论体系研究）立项一览表_修正导入.xls》
 * @author pengliang
 * 
 * 备注：没有对应申请项目的，直接添加对应申请项目
 */
public class SpecialProjectGranted2015Importer extends Importer{
	
	private ExcelReader reader;
	
	public final static int YEAR = 2015;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private AcademicFinder academicFinder;

	@Autowired
	private SpecialProjectFinder specialProjectFinder;

	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private Tool tool;
	
	
	@Override
	protected void work() throws Throwable {
		validate();
//		importDataWithoutApplication();
		importGrantedData();
	}
	
	private void importGrantedData() throws Exception {
		reader.readSheet(1);
		Officer 王晓蕾 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '王晓蕾'");
		SystemOption 中特理论 = systemOptionDao.query("projectType", "0707");
		
		while (next(reader)) {
			if (A.isEmpty() || A == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber() + ":" + B);
			SpecialApplication application = specialProjectFinder.findApplication(B, D, YEAR);
			if(application == null) {
				application = specialProjectFinder.findApplication(F, D, YEAR);
			}
			SpecialGranted granted = null;
			Agency university = universityFinder.getUnivByName(E);
			if (application.getSpecialGranted().isEmpty()) {
				granted = new SpecialGranted();
				granted.setStatus(1);
				granted.setCreateMode(2);
				granted.setCreateDate(new Date());
				granted.setApproveDate(tool.getDate(2015, 6, 16));
				granted.setPlanEndDate(application.getPlanEndDate());
				application.addGranted(granted);
			} else {
				granted = application.getSpecialGranted().iterator().next();
			}
			
			beanFieldUtils.setField(granted, "name", B, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(granted, "number", C, BuiltinMergeStrategies.REPLACE);
			
			granted.setUniversity(university);
			granted.setAgencyName(application.getAgencyName());
			if(application.getDepartment() != null) {
				if (application.getDepartment().getUniversity().getName().equals(university.getName())) {
					granted.setDepartment(application.getDepartment());
					granted.setDivisionName(application.getDivisionName());
				} else {
					Department otherDepartment = departmentFinder.getDepartment(university, null, true);
					granted.setDepartment(otherDepartment);
					granted.setDivisionName(otherDepartment.getName());
				}
			}
			
			granted.setApplicantId(application.getApplicantId());
			granted.setApplicantName(application.getApplicantName());
			granted.setMemberGroupNumber(1);
			
			granted.setSubtype(中特理论);
			
			granted.getApplication().setFinalAuditResult(2);
			granted.getApplication().setFinalAuditStatus(3);
			granted.getApplication().setFinalAuditor(王晓蕾);
			granted.getApplication().setFinalAuditorName(王晓蕾.getPerson().getName());
			granted.getApplication().setFinalAuditorAgency(王晓蕾.getAgency());
			granted.getApplication().setFinalAuditorInst(王晓蕾.getInstitute());
			granted.getApplication().setFinalAuditDate(tool.getDate(2015, 6, 16));
			dao.add(granted);
			dao.modify(application);
		}
		//不立项
		SpecialApplication ungrantedApplication = specialProjectFinder.findApplication("传承与引领：红色文化与大学生意识形态教育研究", "程小强", YEAR);
		ungrantedApplication.setFinalAuditResult(1);
		ungrantedApplication.setFinalAuditStatus(3);
		ungrantedApplication.setFinalAuditor(王晓蕾);
		ungrantedApplication.setFinalAuditorName(王晓蕾.getPerson().getName());
		ungrantedApplication.setFinalAuditorAgency(王晓蕾.getAgency());
		ungrantedApplication.setFinalAuditorInst(王晓蕾.getInstitute());
		ungrantedApplication.setFinalAuditDate(tool.getDate(2015, 6, 16));
		dao.modify(ungrantedApplication);
	}
	
	//附件：《2015年教育部人文社会科学专项任务项目（中国特色社会主义理论体系研究）立项一览表_修正一览表.xls》
	private void importDataWithoutApplication() throws Exception {
		reader.readSheet(1);
		SystemOption 中特理论 = systemOptionDao.query("projectType", "0707");
		
		while(next(reader)) {
			if (A.isEmpty() || A == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber() + ":" + C);
			Agency university = universityFinder.getUnivByName(B);
			SpecialApplication application = specialProjectFinder.findApplication(C, D, YEAR);
			Teacher applicant = univPersonFinder.findTeacher(D, university);
			Academic academic = academicFinder.findAcademic(applicant.getPerson());
			if (academic == null) {
				academic = new Academic();
				academic.setPerson(applicant.getPerson());	
				applicant.getPerson().setAcademic(academic);
			}
			
			if (application == null) {
				application = new SpecialApplication();   
			}
			
			application.setYear(2015);
			application.setApplicantSubmitStatus(3);
			application.setStatus(5);
			application.setName(C.trim());
			application.setUniversity(university);
			application.setAgencyName(university.getName());
			application.setApplicantId(applicant.getPerson().getId());
			application.setApplicantName(D.replaceAll("\\s+", ""));
			application.setSubtype(中特理论);
			application.setCreateMode(2);
			application.setCreateDate(new Date());
			
			application.setDeptInstAuditResult(2);
			application.setDeptInstAuditStatus(3);
			application.setUniversityAuditResult(2);
			application.setUniversityAuditStatus(3);
			if(university.getType() == 4) {  //地方高校
				application.setProvinceAuditResult(2);
				application.setProvinceAuditStatus(3);
			}
			application.setMinistryAuditResult(2);
			application.setMinistryAuditStatus(3);
			
			addMember(application, 1, applicant);
			
			dao.addOrModify(application);
			dao.addOrModify(academic);
		}
	}
	
	private void addMember(SpecialApplication application, int order, Teacher director) {
		SpecialMember member = new SpecialMember();  		
		Person person = director.getPerson();
		System.out.println("项目申请人：" + person.getName());
		member.setUniversity(application.getUniversity());
		member.setAgencyName(B.trim());	

		member.setMemberType(1);
		member.setIsDirector(1);
		member.setMemberName(person.getName());
		member.setMemberSn(order);	
		application.addMember(member);
	
		member.setMember(person);	
		member.setGroupNumber(1);	
	}
	
	private void validate() throws Exception {
		reader.readSheet(1);
		
		Set<String> exMsg = new HashSet<String>();
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(E);
			if (university == null) {
				exMsg.add("不存在的高校: " + E);
			}
			SpecialApplication application = specialProjectFinder.findApplication(B, D, YEAR);
			if(application == null) {
				application = specialProjectFinder.findApplication(F, D, YEAR);
			}
			if (application == null) {
				exMsg.add("不存在的申请: " + B);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public SpecialProjectGranted2015Importer(){
	}
	
	public SpecialProjectGranted2015Importer(String file) {
		reader = new ExcelReader(file);
	}

}
