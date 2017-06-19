package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.InstpApplication;
import csdc.bean.InstpMember;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2012年基地项目申报一览表（录入）.xls》
 * 
 * @author xuhan
 *
 */
public class InstpApplication2012Importer extends Importer {
	
	private ExcelReader reader;

	@Autowired
	private Tool tool;

	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private ExpertFinder expertFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private ProductTypeNormalizer productTypeNormalizer;

	@Autowired
	private InstpProjectFinder instpProjectFinder;

	@Autowired
	private SystemOptionDao systemOptionDao;

	/**
	 * 项目年度
	 */
	private final int year = 2012;

	
	
	

	@Override
	public void work() throws Exception {
		validate();
		importData();
	}
	
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		resetReader();
		
		Officer 白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");
		SystemOption 校级基地 = systemOptionDao.query("researchAgencyType", "04");
		
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			String[] directorNames = E.split("[\\s　]+");
			for (String string : directorNames) {
				string.charAt(0);
			}

			InstpApplication application = instpProjectFinder.findApplication(C, directorNames[0]);
			Agency university = universityFinder.getUnivByName(A);
			Institute institute = instituteFinder.getInstitute(university, B, true);
			if (institute.getType().getCode().equals("06")) {
				institute.setType(校级基地);
			}
			
			if (application == null) {
				application = new InstpApplication();
			}

			application.setYear(year);
			application.setUniversity(university);
			application.setAgencyName(university.getName());
			application.setInstitute(institute);
			application.setDivisionName(institute.getName());
			
			beanFieldUtils.setField(application, "name", C, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(application, "disciplineType", D, BuiltinMergeStrategies.REPLACE);
			
			for (int i = 0; i < directorNames.length; i++) {
				Date birthday = i == 0 ? tool.getDate(H) : null;  
				addApplicant(application, directorNames[i], F, G, I, birthday);
			}
			
			String[] productType = productTypeNormalizer.getNormalizedProductType(J);
			application.setProductType(productType[0]);
			application.setProductTypeOther(productType[1]);

			beanFieldUtils.setField(application, "planEndDate", tool.getDate(K), BuiltinMergeStrategies.PRECISE_DATE);
			//////////////////////////////////////////////////////////
			application.setImportedDate(new Date());
			application.setIsImported(1);
			application.setApplicantSubmitDate(tool.getDate(application.getYear(), 3, 1));
			application.setFinalAuditDate(tool.getDate(application.getYear(), 4, 1));
			application.setFinalAuditResult(1);
			application.setFinalAuditStatus(3);
			application.setFinalAuditor(白晓.getPerson());
			application.setFinalAuditorName(白晓.getPerson().getName());
			application.setFinalAuditorAgency(白晓.getAgency());
			
			saveOrUpdate(application);
		}
	}


	/**
	 * 添加一个负责人
	 * @param application
	 * @param personName
	 * @param agencyName
	 * @param title 职称
	 * @param positon 职务
	 * @throws Exception
	 */
	private void addApplicant(InstpApplication application, String personName, String agencyName, String title, String positon, Date birthday) throws Exception {
		if (agencyName.isEmpty()) {
			agencyName = "未知机构";
		}
		
		Person applicant = null;
		InstpMember member = new InstpMember();
		
		//判断机构名是否学校名称，以判断该负责人是否教师。
		Agency univ = universityFinder.getUnivByName(agencyName);
		if (univ != null) {
			//教师
			Teacher teacher = univPersonFinder.findTeacher(personName, univ);
			if (positon != null && !positon.isEmpty()) {
				teacher.setPosition(positon);
			}
			applicant = teacher.getPerson();

			member.setUniversity(teacher.getUniversity());
			member.setDepartment(teacher.getDepartment());
			member.setInstitute(teacher.getInstitute());
			member.setDivisionName(teacher.getDepartment() != null ? teacher.getDepartment().getName() : teacher.getInstitute() != null ? teacher.getInstitute().getName() : null);
			member.setMemberType(1);
			
			member.setDivisionType(teacher.getDepartment() != null ? 2 : 1);
		} else {
			//专家
			Expert expert = expertFinder.findExpert(personName, agencyName);
			if (positon != null && !positon.isEmpty()) {
				expert.setPosition(positon);
			}
			applicant = expert.getPerson();

			member.setMemberType(2);
		}
		beanFieldUtils.setField(applicant, "birthday", birthday, BuiltinMergeStrategies.PRECISE_DATE);

		member.setMember(applicant);
		member.setMemberName(applicant.getName());
		member.setAgencyName(agencyName);
		member.setIsDirector(1);
		
		Academic academic = applicant.getAcademicEntity();
		if (title.length() > 0) {
			academic.setSpecialityTitle(title);
		}
		member.setSpecialistTitle(academic.getSpecialityTitle());
		
		application.addMember(member);
		member.setMemberSn(application.getMember().size());
		
		String applicantName = application.getApplicantName() == null ? "" : application.getApplicantName();
		String applicantId = application.getApplicantId() == null ? "" : application.getApplicantId();
		if (!applicantName.contains(applicant.getName())) {
			if (applicantId.length() > 0) {
				applicantId += "; ";
			}
			applicantId += applicant.getId();
			
			if (applicantName.length() > 0) {
				applicantName += "; ";
			}
			applicantName += applicant.getName();
			
			application.setApplicantId(applicantId);
			application.setApplicantName(applicantName);
		}
		
		member.setIdcardType(applicant.getIdcardType());
		member.setIdcardNumber(applicant.getIdcardNumber());
		member.setGender(applicant.getGender());
		member.setGroupNumber(1);
	}
	
	private void validate() throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(A);
			if (university == null) {
				exMsg.add("不存在的高校: " + A);
			}
			Institute institute = instituteFinder.getInstitute(university, B, false);
			if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
				System.out.println("找不到的部级/省部共建基地：" + A + " - " + B);
//				exMsg.add("找不到的部级/省部共建基地：" + A + " - " + B);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}

	
	public InstpApplication2012Importer() {}
	
	public InstpApplication2012Importer(String file) {
		reader = new ExcelReader(file);
	}



}
