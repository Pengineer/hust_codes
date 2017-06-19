package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
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
import csdc.tool.execution.importer.tool.GeneralApplicationMemberParser;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2013年重大基地项目申请一览表_修正导入.xls》
 * 
 * @author maowh
 *
 */

public class InstpApplication2013Importer extends Importer {
	
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
	
	@Autowired
	private GeneralApplicationMemberParser instpApplicationMemberParser;

	/**
	 * 项目年度
	 */
	private final int year = 2013;
	
	
	
	
	@Override
	public void work() throws Exception {
		//validate();
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
		
		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'");
		SystemOption 校级基地 = systemOptionDao.query("researchAgencyType", "04");
		
		while (next(reader)) {
			if(E.length() == 0) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
		
			String[] directorNames = I.split("[\\s　]+");//负责人（以空格隔开）
			String[] titleStrings = K.split("[\\s ]+");//负责人多个时，职称多个（以空格隔开）
			String[] agencyNameStrings = L.split("[\\s ]+");//负责人多个时，所在部门多个
			
			InstpApplication application = instpProjectFinder.findApplication(C, directorNames[0]);//项目名称
			Agency university = universityFinder.getUnivByName(A);
			Institute institute = instituteFinder.getInstitute(university, B, true);//基地名称
			if (institute.getType().getCode().equals("06")) {
				institute.setType(校级基地);
			}
			
			if (application == null) {
				application = new InstpApplication();
			}
			
			application.setYear(year);//年份
			application.setDiscipline(D);//学科代码
			application.setUniversity(university);//学校实体
			application.setAgencyName(university.getName());//学校名字
			application.setInstitute(institute);//机构实体
			application.setDivisionName(institute.getName());//机构名称
			
			
			beanFieldUtils.setField(application, "name", C, BuiltinMergeStrategies.REPLACE);//课题名称
			beanFieldUtils.setField(application, "disciplineType", E, BuiltinMergeStrategies.REPLACE);//一级学科
			
			for (int i = 0; i < directorNames.length; i++) {
				Date birthday = i == 0 ? tool.getDate(J) : null;  //出生日期（只记录了第一个申请人的？）
				addApplicant(application, directorNames[i], agencyNameStrings[i], titleStrings[i], M, birthday);//所在部门、职称、职位
			}
			
			String[] productType = productTypeNormalizer.getNormalizedProductType(H);//最终成果形式
			application.setProductType(productType[0]);
			application.setProductTypeOther(productType[1]);
			
			beanFieldUtils.setField(application, "planEndDate", tool.getDate(G), BuiltinMergeStrategies.PRECISE_DATE);//计划完成日期
			
			for (Object object : instpApplicationMemberParser.getMembers(I)) {
				addMember(application, object, true);
			}
			for (Object object1 : instpApplicationMemberParser.getMembers(N)) {
				if(object1 instanceof Teacher){
					if (!I.contains(((Teacher) object1).getPerson().getName())) {
						addMember(application, object1, false);
					}
				} else {
					if (!I.contains(((Expert) object1).getPerson().getName())) {
						addMember(application, object1, false);
					}
				}
			}
			
			application.setImportedDate(new Date());
			application.setIsImported(1);
			
			application.setApplicantSubmitDate(tool.getDate(F));
			application.setFinalAuditDate(tool.getDate(application.getYear(), 4, 1));
			application.setFinalAuditResult(1);
			application.setFinalAuditStatus(3);
			
			application.setFinalAuditor(刘杰.getPerson());
			application.setFinalAuditorName(刘杰.getPerson().getName());
			application.setFinalAuditorAgency(刘杰.getAgency());
			
			saveOrUpdate(application);
		}
	}
	
	
	private void addMember(InstpApplication application, Object oMember, boolean isDirector) {
		InstpMember member = new InstpMember();
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
		 * PS: 并不是通过申请报上来的信息，真的需要从academic表中把复制进member表吗？(冗余数据)
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
		
		while (next(reader)) {
			if(A.length() == 0) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			Agency university = universityFinder.getUnivByName(A);
			if (university == null) {
				System.out.println("不存在的高校: " + A);
			} else {
				Institute institute = instituteFinder.getInstitute(university, B, false);
				if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
					System.out.println("找不到的部级/省部共建基地：" + A + " - " + B);
				}
			}
		}
	}

	
	public InstpApplication2013Importer() {}
	
	public InstpApplication2013Importer(String file) {
		reader = new ExcelReader(file);
	}



}

