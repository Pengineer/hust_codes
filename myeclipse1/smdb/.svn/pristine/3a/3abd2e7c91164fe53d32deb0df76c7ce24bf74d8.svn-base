package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Officer;
import csdc.bean.PostApplication;
import csdc.bean.PostEndinspection;
import csdc.bean.PostGranted;
import csdc.bean.PostMember;
import csdc.bean.Expert;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.PostProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 导入《2006-2011年后期资助项目立项及鉴定一览表_修正导入.xls》
 *
 * @author xuhan
 */
public class PostGranted2006_2011Importer extends Importer {
	
	@Autowired
	private Tool tool;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private ExpertFinder expertFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private PostProjectFinder postProjectFinder;
	
	Set<String> exMsg = new HashSet<String>();
	

	
	public PostGranted2006_2011Importer() {}

	public PostGranted2006_2011Importer(String filePath) {
		super(filePath);
	}
	
	@Override
	public void work() throws Exception {
		validateData();
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();
		subTypeMap.put("一般", (SystemOption) dao.query(SystemOption.class, "postGeneral"));
		subTypeMap.put("重大", (SystemOption) dao.query(SystemOption.class, "postKey"));
		subTypeMap.put("重点", (SystemOption) dao.query(SystemOption.class, "postMajor"));

		Officer 王晓蕾 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '王晓蕾'");

		getContentFromExcel(0);

		while (next()) if (!A.isEmpty()) {
			System.out.println(curRowIndex);
			
			PostApplication pa = postProjectFinder.findApplication(E, D);
			
			if (pa == null && postProjectFinder.findApplication(Integer.parseInt("20" + B.substring(0, 2)), D) != null) {
				exMsg.add("项目查找异常：" + E + " - " + D);
			}
			
			//申请库内没有，新建一个
			if (pa == null) {
				pa = new PostApplication();
				pa.setIsImported(1);
				pa.setImportedDate(new Date());

				pa.setName(E);
				pa.setYear(Integer.parseInt("20" + B.substring(0, 2)));
				pa.setSubtype(subTypeMap.get(G));

				Agency univ = universityFinder.getUnivByName(C);
				if (univ != null) {
					pa.setUniversity(univ);
					pa.setAgencyName(univ.getName());
				} else {
					pa.setAgencyName(C);
				}
				addApplicant(pa, D, C);
				pa.setDepartment(pa.getPostMember().iterator().next().getDepartment());
				pa.setInstitute(pa.getPostMember().iterator().next().getInstitute());
				pa.setDivisionName(pa.getPostMember().iterator().next().getDivisionName());

				pa.setApplicantSubmitDate(tool.getDate(pa.getYear(), 3, 1));
				pa.setFinalAuditDate(tool.getDate(pa.getYear(), 6, 1));
				pa.setFinalAuditResult(1);
				pa.setFinalAuditStatus(3);
				pa.setFinalAuditor(王晓蕾.getPerson());
				pa.setFinalAuditorName(王晓蕾.getPerson().getName());
				pa.setFinalAuditorAgency(王晓蕾.getAgency());
				pa.setFinalAuditorInst(王晓蕾.getInstitute());

				saveOrUpdate(pa);
			}
			
			if (F.length() > 0) {
				pa.setDisciplineType(F);
				pa.setDiscipline(tool.transformDisc(F));
			}
			
			pa.setFinalAuditResult(2);

			PostGranted pg = null;
			if (pa.getPostGranted() == null || pa.getPostGranted().isEmpty()) {
				pg = new PostGranted();
				pa.addGranted(pg);
			} else {
				pg = pa.getPostGranted().iterator().next();
			}
			
			pg.setNumber(B);
			pg.setName(E);
			
			pg.setSubtype(subTypeMap.get(G));

			pg.setStatus(1);
			pg.setIsImported(1);
			pg.setApplicantId(pa.getApplicantId());
			pg.setApplicantName(pa.getApplicantName());
			
			pg.setUniversity(pa.getUniversity());
			pg.setAgencyName(pa.getAgencyName());
			pg.setDepartment(pa.getDepartment());
			pg.setInstitute(pa.getInstitute());
			pg.setDivisionName(pa.getDivisionName());
			
			pg.setApproveDate(pa.getFinalAuditDate());

			//批准经费
			pg.setApproveFee(tool.getFee(H));
			
			//结项、结项鉴定
			if (J.length() > 0 || L.length() > 0) {
				PostEndinspection pei = new PostEndinspection();
				pg.addEndinspection(pei);
				
				pei.setApplicantSubmitDate(J.length() > 0 ? tool.getDate(J) : tool.getDate(L));
				
				if (J.length() > 0) {
					pei.setReviewDate(tool.getDate(J));
					pg.setStatus(5);
				}
				if (L.length() > 0) {
					if (I.isEmpty()) {
						pei.setFinalAuditResultNoevaluation(2);
					}
					pei.setFinalAuditDate(L.length() > 0 ? tool.getDate(L) : tool.getDate(J));
					pei.setFinalAuditResultEnd(2);
					pg.setStatus(2);
					pg.setEndStopWithdrawDate(pei.getFinalAuditDate());
					pg.setEndStopWithdrawPerson(王晓蕾.getPerson().getName());
				}
				
				pei.setFinalAuditorName(王晓蕾.getPerson().getName());
				pei.setFinalAuditor(王晓蕾.getPerson());
				pei.setFinalAuditorAgency(王晓蕾.getAgency());

				pei.setFinalAuditStatus(3);
			}
		}
	}
	
	/**
	 * 添加一个负责人
	 * @param pa
	 * @param personName
	 * @param agencyName
	 * @param title 职称
	 * @param positon 职务
	 * @throws Exception
	 */
	private void addApplicant(PostApplication pa, String personName, String agencyName) throws Exception {
		if (agencyName.isEmpty()) {
			agencyName = "未知机构";
		}
		
		Person applicant = null;
		PostMember pm = new PostMember();
		
		//判断机构名是否学校名称，以判断该负责人是否教师。
		Agency univ = universityFinder.getUnivByName(agencyName);
		if (univ != null) {
			//教师
			Teacher teacher = univPersonFinder.findTeacher(personName, univ);
			applicant = teacher.getPerson();

			pm.setUniversity(teacher.getUniversity());
			pm.setDepartment(teacher.getDepartment());
			pm.setInstitute(teacher.getInstitute());
			pm.setDivisionName(teacher.getDepartment() != null ? teacher.getDepartment().getName() : teacher.getInstitute() != null ? teacher.getInstitute().getName() : null);
			pm.setMemberType(1);
		} else {
			//外部专家
			Expert expert = expertFinder.findExpert(personName, agencyName);
			applicant = expert.getPerson();

			pm.setMemberType(2);
		}

		pm.setMember(applicant);
		pm.setMemberName(applicant.getName());
		pm.setAgencyName(agencyName);
		pm.setIsDirector(1);
		
		Academic academic = applicant.getAcademicEntity();
		pm.setSpecialistTitle(academic.getSpecialityTitle());
		
		pa.addMember(pm);
		pm.setMemberSn(pa.getPostMember().size());
		
		String applicantName = pa.getApplicantName() == null ? "" : pa.getApplicantName();
		String applicantId = pa.getApplicantId() == null ? "" : pa.getApplicantId();
		if (!applicantName.contains(applicant.getName())) {
			if (applicantId.length() > 0) {
				applicantId += "; ";
			}
			applicantId += applicant.getId();
			
			if (applicantName.length() > 0) {
				applicantName += "; ";
			}
			applicantName += applicant.getName();
			
			pa.setApplicantId(applicantId);
			pa.setApplicantName(applicantName);
		}
	}
	
	/**
	 * 检查学校是否库内存在
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedEncodingException 
	 */
	private void validateData() throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, Exception {
		getContentFromExcel(0);
		
		while (next()) if (!A.isEmpty()) {
			Agency univ = universityFinder.getUnivByName(C);
			if (univ == null) {
				System.out.println("找不到的学校：" + C);
//				exMsg.add("找不到的学校：" + C);
			}
		}
		
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
}
