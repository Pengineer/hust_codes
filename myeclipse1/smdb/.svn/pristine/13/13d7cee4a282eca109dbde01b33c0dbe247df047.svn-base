package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.InstpApplication;
import csdc.bean.InstpMember;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 基地项目申请表导入
 * 《2006-2011年基地项目申请数据_修正导入.xls》
 * @author xuhan
 * @status 已导入smdb
 *
 */
public class InstpApplicationImporter extends Importer {
	
	@Autowired
	private Tool tool;

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

	private Officer 白晓;
	
	
	public InstpApplicationImporter() {}
	
	public InstpApplicationImporter(String filePath) {
		super(filePath);
	}
	

	@Override
	public void work() throws Exception {
		checkInstituteExistence();
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");

		getContentFromExcel(0);
		
		int idx = 0;
		
		while (next()) {
			System.out.println(++idx);
			
			InstpApplication ba = instpProjectFinder.findApplication(D, E);
			if (ba == null) {
				System.out.println(D);
				continue;
			}

			ba.setIsImported(1);
			ba.setImportedDate(new Date());
			
			String projectName = D;

			Agency instituteUniversity = universityFinder.getUnivByName(B);
			Institute institute = instituteFinder.getInstitute(instituteUniversity, C.replace(instituteUniversity.getName(), ""), false);
			
			ba.setName(projectName);
			ba.setBelongInstitute(institute);
			ba.setBelongInstituteName(institute.getName());
			ba.setYear(Integer.valueOf(A));

			if (E.length() > 0) {
				addApplicant(ba, E, F, G, N);
			}
			if (H.length() > 0) {
				addApplicant(ba, H, I, J, null);
			}
			if (K.length() > 0) {
				addApplicant(ba, K, L, M, null);
			}
			
			String[] productType = productTypeNormalizer.getNormalizedProductType(O);
			ba.setProductType(productType[0]);
			ba.setProductTypeOther(productType[1]);
			
			ba.setPlanEndDate(tool.getDate(P));
			//////////////////////////////////////////////////////////
			ba.setApplicantSubmitDate(tool.getDate(ba.getYear(), 3, 1));
			ba.setFinalAuditDate(tool.getDate(ba.getYear(), 6, 1));
			if (ba.getInstpGranted().isEmpty()) {
				ba.setFinalAuditResult(1);
			}
			ba.setFinalAuditStatus(3);
			ba.setFinalAuditor(白晓.getPerson());
			ba.setFinalAuditorName(白晓.getPerson().getName());
			ba.setFinalAuditorAgency(白晓.getAgency());
			ba.setFinalAuditorInst(白晓.getInstitute());
			
			saveOrUpdate(ba);
		}
	}


	/**
	 * 添加一个负责人
	 * @param ba
	 * @param personName
	 * @param agencyName
	 * @param title 职称
	 * @param positon 职务
	 * @throws Exception
	 */
	private void addApplicant(InstpApplication ba, String personName, String agencyName, String title, String positon) throws Exception {
		if (agencyName.isEmpty()) {
			agencyName = "未知机构";
		}
		
		Person applicant = null;
		InstpMember bm = new InstpMember();
		
		//判断机构名是否学校名称，以判断该负责人是否教师。
		Agency univ = universityFinder.getUnivByName(agencyName);
		if (univ != null) {
			//教师
			Teacher teacher = univPersonFinder.findTeacher(personName, univ);
			if (positon != null && !positon.isEmpty()) {
				teacher.setPosition(positon);
			}
			applicant = teacher.getPerson();

			bm.setUniversity(teacher.getUniversity());
			bm.setDepartment(teacher.getDepartment());
			bm.setInstitute(teacher.getInstitute());
			bm.setDivisionName(teacher.getDepartment() != null ? teacher.getDepartment().getName() : teacher.getInstitute() != null ? teacher.getInstitute().getName() : null);
			bm.setMemberType(1);
		} else {
			//专家
			Expert expert = expertFinder.findExpert(personName, agencyName);
			if (positon != null && !positon.isEmpty()) {
				expert.setPosition(positon);
			}
			applicant = expert.getPerson();

			bm.setMemberType(2);
		}

		bm.setMember(applicant);
		bm.setMemberName(applicant.getName());
		bm.setAgencyName(agencyName);
		bm.setIsDirector(1);
		
		Academic academic = applicant.getAcademicEntity();
		if (title.length() > 0) {
			academic.setSpecialityTitle(title);
		}
		bm.setSpecialistTitle(academic.getSpecialityTitle());
		
		ba.addMember(bm);
		bm.setMemberSn(ba.getInstpMember().size());
		
		String applicantName = ba.getApplicantName() == null ? "" : ba.getApplicantName();
		String applicantId = ba.getApplicantId() == null ? "" : ba.getApplicantId();
		if (!applicantName.contains(applicant.getName())) {
			if (applicantId.length() > 0) {
				applicantId += "; ";
			}
			applicantId += applicant.getId();
			
			if (applicantName.length() > 0) {
				applicantName += "; ";
			}
			applicantName += applicant.getName();
			
			ba.setApplicantId(applicantId);
			ba.setApplicantName(applicantName);
		}
	}
	
	/**
	 * 检查基地是否库内存在
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedEncodingException 
	 */
	private void checkInstituteExistence() throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, Exception {
		Set<String> exMsg = new HashSet<String>();
		
		getContentFromExcel(0);
		
		while (next()) {
			Agency univ = universityFinder.getUnivByName(B);
			if (univ == null) {
				exMsg.add("找不到的学校：" + B);
				continue;
			}
			Institute institute = instituteFinder.getInstitute(univ, C.replace(B, ""), false);
			if (institute == null || !institute.getType().getCode().equals("1") && !institute.getType().getCode().equals("2")) {
				exMsg.add("找不到的部级/省部共建基地：" + B + " - " + C);
			}
		}
		
		if (exMsg.size() > 0) {
			throw new RuntimeException(exMsg.toString().replaceAll(",\\s+", "\n"));
			//System.out.println(exMsg);
		}
	}



}
