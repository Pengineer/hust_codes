package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.Officer;
import csdc.bean.Person;
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
import csdc.tool.execution.importer.tool.GeneralApplicationMemberParser;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2013年一般项目申报一览表_修正导入.xls》
 * @author wangyi
 *
 */
public class GeneralProjectApplication2013Importer extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;

	@Autowired
	private AcademicFinder academicFinder;

	@Autowired
	private ProductTypeNormalizer productTypeNormalizer;

	@Autowired
	private GeneralApplicationMemberParser generalApplicationMemberParser;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private SystemOptionDao systemOptionDao;

	
	/**
	 * 项目年度
	 */
	private final int year = 2013;

	
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	public void work() throws Exception {
		validate();
		importData();
	}

	public void importData() throws Exception {
		resetReader();
		
		Officer 白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");

		SystemOption 规划 = systemOptionDao.query("projectType", "011");
		SystemOption 青年 = systemOptionDao.query("projectType", "013");
		SystemOption 自筹 = systemOptionDao.query("projectType", "015");

		SystemOption 基础研究 = systemOptionDao.query("researchType", "01");
		SystemOption 应用研究 = systemOptionDao.query("researchType", "02");
		SystemOption 实验与发展 = systemOptionDao.query("researchType", "03");

		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			GeneralApplication application = generalProjectFinder.findApplication(F, O, year);
			Agency university = universityFinder.getUnivByName(E);
			Department department = departmentFinder.getDepartment(university, S, true);
			Teacher applicant = univPersonFinder.findTeacher(O, department);
			
			Academic academic = academicFinder.findAcademic(applicant.getPerson());
			if (academic == null) {
				academic = new Academic();
				academic.setPerson(applicant.getPerson());
				applicant.getPerson().setAcademic(academic);
			}
			if (application == null) {
				application = new GeneralApplication();
			}
			
			application.setYear(year);
			application.setUniversity(university);
			application.setAgencyName(university.getName());
			application.setDepartment(department);
			application.setDivisionName(department.getName());
			application.setApplicantId(applicant.getPerson().getId());
			application.setApplicantName(applicant.getPerson().getName());

			
			beanFieldUtils.setField(application, "name", F, BuiltinMergeStrategies.REPLACE);
			if (G.contains("规划")) {
				application.setSubtype(规划);
			} else if (G.contains("青年")) {
				application.setSubtype(青年);
			} else if (G.contains("自筹")) {
				application.setSubtype(自筹);
			}
			beanFieldUtils.setField(application, "disciplineType", H, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(application, "discipline", tool.transformDisc(I), BuiltinMergeStrategies.REPLACE);
			if (J.contains("基础研究")) {
				application.setResearchType(基础研究);
			} else if (J.contains("实验与发展")) {
				application.setResearchType(实验与发展);
			} else if (J.contains("应用研究")) {
				application.setResearchType(应用研究);
			}
			beanFieldUtils.setField(application, "planEndDate", tool.getDate(K), BuiltinMergeStrategies.PRECISE_DATE);
			beanFieldUtils.setField(application, "applyFee", tool.getFee(L), BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(application, "otherFee", tool.getFee(M), BuiltinMergeStrategies.REPLACE);
			
			String[] productType = productTypeNormalizer.getNormalizedProductType(N);
			application.setProductType(productType[0]);
			application.setProductTypeOther(productType[1]);
			
			beanFieldUtils.setField(applicant.getPerson(), "gender", P, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(applicant.getPerson(), "birthday", tool.getDate(Q), BuiltinMergeStrategies.PRECISE_DATE);
			beanFieldUtils.setField(academic, "specialityTitle", R, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(applicant, "position", T, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(academic, "lastEducation", U, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(academic, "lastDegree", V, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(academic, "language", W, BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(applicant.getPerson(), "officeAddress", X, BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(applicant.getPerson(), "officePostcode", Y, BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(applicant.getPerson(), "officePhone", Z, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(applicant.getPerson(), "mobilePhone", AA, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(applicant.getPerson(), "email", AB, BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(applicant.getPerson(), "idcardNumber", AC, BuiltinMergeStrategies.REPLACE);
			if (AC.length() >= 15) {
				applicant.getPerson().setIdcardType("身份证");
			}
			
			addMember(application, applicant, true);
			for (Object object : generalApplicationMemberParser.getMembers(AD)) {
				addMember(application, object, false);
			}
			
			//////////////////////////////////////////////////////////
			application.setApplicantSubmitDate(tool.getDate(application.getYear(), 1, 11));
			application.setFinalAuditDate(tool.getDate(application.getYear(), 5, 21));
			application.setFinalAuditStatus(3);
			application.setFinalAuditor(白晓.getPerson());
			application.setFinalAuditorName(白晓.getPerson().getName());
			application.setFinalAuditorAgency(白晓.getAgency());
			application.setFinalAuditorInst(白晓.getInstitute());
			application.setFinalAuditStatus(3);
			application.setFinalAuditResult(1);
			application.setImportedDate(new Date());
			application.setIsImported(1);

			dao.addOrModify(application);
			dao.addOrModify(academic);
			if (reader.getCurrentRowIndex() % 500 == 0) {
				dao.flush();
				dao.clear();
			}
		}
	}
	
	private void addMember(GeneralApplication application, Object oMember, boolean isDirector) {
		GeneralMember member = new GeneralMember();
		application.addMember(member);
		
		Person person = null;
		if (oMember instanceof Teacher) {
			Teacher teacher = (Teacher) oMember;
			person = teacher.getPerson();
			member.setUniversity(teacher.getUniversity());
			member.setInstitute(teacher.getInstitute());
			member.setDepartment(teacher.getDepartment());
			member.setAgencyName(teacher.getUniversity().getName());
			if (teacher.getDepartment() != null) {
				member.setDivisionName(teacher.getDepartment().getName());
				member.setDivisionType(2);
			} else {
				member.setDivisionName(teacher.getInstitute().getName());
				member.setDivisionType(1);
			}
			
			member.setWorkMonthPerYear(teacher.getWorkMonthPerYear());
			member.setMemberType(1);
		} else {
			Expert expert = (Expert) oMember;
			person = expert.getPerson();
			member.setAgencyName(expert.getAgencyName());

			member.setMemberType(2);
		}
		
		member.setMember(person);
		member.setMemberName(person.getName());
		
		/**
		 * 下面这两个涉及查询Academic表，实在太慢，考虑导入后统一处理吧
		 * PS: 并不是通过申报报上来的信息，真的需要从academic表中把复制进member表吗？(冗余数据)
		 */
//		member.setMajor(person.getAcademicEntity().getMajor());
//		member.setSpecialistTitle(academicFinder.findAcademic(person).getSpecialityTitle());

		member.setIsDirector(isDirector ? 1 : 0);
		member.setMemberSn(application.getMember().size());
		member.setIdcardType(person.getIdcardType());
		member.setIdcardNumber(person.getIdcardNumber());
		member.setGender(person.getGender());
		
		member.setGroupNumber(1);
	}
	
	private void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
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
	
	public GeneralProjectApplication2013Importer(){
	}
	
	public GeneralProjectApplication2013Importer(String file) {
		reader = new ExcelReader(file);
	}
}
