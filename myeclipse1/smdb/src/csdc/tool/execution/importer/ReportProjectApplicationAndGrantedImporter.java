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
import csdc.bean.DevrptApplication;
import csdc.bean.DevrptGranted;
import csdc.bean.DevrptMember;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.DevrptProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入发展报告项目
 * 
 * @author maowh
 *
 */

public class ReportProjectApplicationAndGrantedImporter extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private DevrptProjectFinder reportProjectFinder;
	
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
		reader.readSheet(4);
		
		Officer 王楠 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '王楠'");
		SystemOption 发展报告项目 = systemOptionDao.query("projectType", "06");
		SystemOption 培育 = systemOptionDao.query("projectType", "062");
		SystemOption 建设 = systemOptionDao.query("projectType", "061");
		
		while (next(reader)) if (!A.isEmpty()) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			if (reader.getCurrentRowIndex() == 68) {
				System.out.println("ok");
			}
			DevrptApplication application = new DevrptApplication();
			Agency university = universityFinder.getUnivByName(G);
			Teacher applicant = univPersonFinder.findTeacher(C.replaceAll("\\s+", ""), university);
			Academic academic = academicFinder.findAcademic(applicant.getPerson());
			if (academic == null) {
				academic = new Academic();
				academic.setPerson(applicant.getPerson());
				applicant.getPerson().setAcademic(academic);
			}

			application.setYear(Integer.valueOf(D));
			application.setName(B);
			application.setUniversity(university);
			application.setAgencyName(university.getName());
			application.setApplicantId(applicant.getPerson().getId());
			application.setApplicantName(applicant.getPerson().getName());
			if ("建设".equals(F)) {
				application.setSubtype(建设);
			} else if ("培育".equals(F)) {
				application.setSubtype(培育);
			}			
			
			application.setFinalAuditStatus(3);
			application.setFinalAuditor(王楠);
			application.setFinalAuditorName(王楠.getPerson().getName());
			application.setFinalAuditorAgency(王楠.getAgency());
			application.setFinalAuditorInst(王楠.getInstitute());
			application.setFinalAuditStatus(3);
			application.setFinalAuditResult(1);
			application.setApplicantSubmitDate(tool.getDate(I));
			application.setFinalAuditDate(tool.getDate(J));
			application.setImportedDate(new Date());
			application.setIsImported(1);
			
			DevrptMember reportMember = new DevrptMember();
			application.addMember(reportMember);
			Person person = applicant.getPerson();
			reportMember.setUniversity(applicant.getUniversity());
			reportMember.setInstitute(applicant.getInstitute());
			reportMember.setDepartment(applicant.getDepartment());
			reportMember.setAgencyName(applicant.getUniversity().getName());
			if (applicant.getDepartment() != null) {
				reportMember.setDivisionName(applicant.getDepartment().getName());
				reportMember.setDivisionType(2);
			} else {
				reportMember.setDivisionName(applicant.getInstitute().getName());
				reportMember.setDivisionType(1);
			}
			reportMember.setWorkMonthPerYear(applicant.getWorkMonthPerYear());
			reportMember.setMemberType(1);
			reportMember.setMember(person);
			reportMember.setMemberName(person.getName());
			reportMember.setIsDirector(1);
			reportMember.setMemberSn(application.getMember().size());
			reportMember.setIdcardType(person.getIdcardType());
			reportMember.setIdcardNumber(person.getIdcardNumber());
			reportMember.setGender(person.getGender());			
			reportMember.setGroupNumber(1);
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
		reader.readSheet(4);
		
		SystemOption 发展报告项目 = systemOptionDao.query("projectType", "06");
		SystemOption 培育 = systemOptionDao.query("projectType", "062");
		SystemOption 建设 = systemOptionDao.query("projectType", "061");
		
		departmentFinder.initDeptMap();

		while (next(reader)) {
			try {
//				if (reader.getCurrentRowIndex() == 124) {
//					System.out.println("rter");
//				}
				System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());

				DevrptApplication application = reportProjectFinder.findApplication(C.replaceAll("\\s+", ""),Integer.valueOf(D));
				DevrptGranted granted = null;
				Agency university = universityFinder.getUnivByName(G);
				
				granted = new DevrptGranted();
				granted.setStatus(1);
				granted.setIsImported(1);
				application.addGranted(granted);

				
				granted.setUniversity(university);
				granted.setAgencyName(application.getAgencyName());
				

				Department otherDepartment = departmentFinder.getDepartment(university, null, true);
				granted.setDepartment(otherDepartment);

				
				granted.setApplicantId(application.getApplicantId());
				granted.setApplicantName(application.getApplicantName());
				granted.setMemberGroupNumber(1);
				if ("建设".equals(F)) {
					granted.setSubtype(建设);
				} else if ("培育".equals(F)) {
					granted.setSubtype(培育);
				}
				granted.setNumber(E);
				granted.setName(application.getName());
				granted.setApproveFee(Double.valueOf(H));
			
//				//若没有任何拨款信息，则导入首批拨款
//				if (granted.getFunding() == null || granted.getFunding().isEmpty()) {
//					GeneralFunding funding = new GeneralFunding();
//					funding.setDate(tool.getDate(2012, 4, 1));
//					funding.setFee(tool.getFee(K));
//					granted.addFunding(funding);
//				}
				
				granted.getApplication().setFinalAuditResult(2);
				dao.addOrModify(application);
				dao.add(granted);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		System.out.println("ok");
	
		
	}

	private void validate() throws Exception {
		reader.readSheet(4);
		
		HashSet exMsg = new HashSet();
		HashSet nullDiscipline = new HashSet();
		while (next(reader)) {
			if (A.isEmpty()) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			Agency university = universityFinder.getUnivByName(G);
			if (university == null) {
				exMsg.add("不存在的高校: " + G);
			}					
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}	
	}
	
	public ReportProjectApplicationAndGrantedImporter(){
	}
	
	public ReportProjectApplicationAndGrantedImporter(String file) {
		reader = new ExcelReader(file);
	}


	
}


