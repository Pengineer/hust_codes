package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java_cup.internal_error;

import org.apache.hadoop.mapred.machines_jsp;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Expert;
import csdc.bean.InstpApplication;
import csdc.bean.InstpGranted;
import csdc.bean.KeyApplication;
import csdc.bean.KeyGranted;
import csdc.bean.KeyMember;
import csdc.bean.KeyTopic;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.PostApplication;
import csdc.bean.PostEndinspection;
import csdc.bean.PostGranted;
import csdc.bean.PostMember;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.KeyProjectFinder;
import csdc.tool.execution.finder.PostProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《评价中心未结项项目名单（截至2014年3月17日）_修正导入.xls》
 * @author 
 * @status 
 * 备注：
 * 基地项目未结项名单数据库中已存在，添加字段C_IS_DUP_CHECK_GENERAL标识属查重范围，其中有三条问题数据：08JJD751073（已撤项）， 11JJD840001（已撤项），10JJD770024 （已结项）。
 */
public class KeyPostInstpNotEndinspection20140317Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private PostProjectFinder postProjectFinder;
	
	@Autowired
	private KeyProjectFinder keyProjectFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private ExpertFinder expertFinder;
	
	public KeyPostInstpNotEndinspection20140317Importer() {}
	
	public KeyPostInstpNotEndinspection20140317Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		checkProjectExistence();
//		importData();
//		validateData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);//重大攻关
		int i = 0;
		
		while (next(excelReader)) {
			if(A.length() == 0) {
				break;
			}
			
			if ("2013".equals(B)) {
				i++;
				KeyTopic kts = new KeyTopic();
				kts.setIsImported(1);
				kts.setImportedDate(new Date());

				kts.setName(D);
				kts.setYear(Integer.parseInt(B));
				//判断机构名是否学校名称，以判断该负责人是否教师。
				Agency univ = universityFinder.getUnivByName(E);
				Person applicant = null;
				if (univ != null) {
					//教师
					Teacher teacher = univPersonFinder.findTeacher(G, univ);
					applicant = teacher.getPerson();

					kts.setUniversity(teacher.getUniversity());
					kts.setExpDepartment(teacher.getDepartment());
					kts.setExpInstitute(teacher.getInstitute());
					kts.setExpDivisionName(teacher.getDepartment() != null ? teacher.getDepartment().getName() : teacher.getInstitute() != null ? teacher.getInstitute().getName() : null);
					kts.setApplicantType(1);
				} else {
					//外部专家
					Expert expert = expertFinder.findExpert(G, E);
					applicant = expert.getPerson();

					kts.setApplicantType(2);
				}
				kts.setApplicantName(applicant.getName());

				kts.setFinalAuditResult(2);
				kts.setFinalAuditStatus(3);

				saveOrUpdate(kts);
				
				KeyApplication ka = new KeyApplication();
				ka.setTopicSelection(kts);
				
				ka.setIsImported(1);
				ka.setImportedDate(new Date());

				ka.setName(D);
				ka.setYear(Integer.parseInt(B));

				if (univ != null) {
					ka.setUniversity(univ);
					ka.setAgencyName(univ.getName());
				} else {
					ka.setAgencyName(E);
				}
				addApplicant(ka, G, E);
				ka.setDepartment(ka.getKeyMember().iterator().next().getDepartment());
				ka.setInstitute(ka.getKeyMember().iterator().next().getInstitute());
				ka.setDivisionName(ka.getKeyMember().iterator().next().getDivisionName());

				ka.setFinalAuditResult(2);
				ka.setFinalAuditStatus(3);

				saveOrUpdate(ka);
				
				KeyGranted kg = new KeyGranted();
				ka.addGranted(kg);
				
				kg.setNumber(A);
				kg.setName(D);

				kg.setStatus(1);
				kg.setIsImported(1);
				kg.setImportedDate(new Date());
				kg.setIsDupCheckGeneral(1);
				kg.setApplicantId(ka.getApplicantId());
				kg.setApplicantName(ka.getApplicantName());
				
				kg.setUniversity(ka.getUniversity());
				kg.setAgencyName(ka.getAgencyName());
				kg.setDepartment(ka.getDepartment());
				kg.setInstitute(ka.getInstitute());
				kg.setDivisionName(ka.getDivisionName());
				System.out.println(i);
			}

		}
		
		Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();
		subTypeMap.put("一般", (SystemOption) dao.query(SystemOption.class, "postGeneral"));
		subTypeMap.put("重大", (SystemOption) dao.query(SystemOption.class, "postKey"));
		subTypeMap.put("重点", (SystemOption) dao.query(SystemOption.class, "postMajor"));

		excelReader.readSheet(1);//后期资助项目

		while (next(excelReader)) {
			if(A.length() == 0) {
				break;
			}
			
			//2013年之前的数据库中已存在，故仅处理2013年数据（其中有60条只有申请没有立项，6条既无申请也无立项）
			if ("2013".equals(C)) {
				PostApplication pa = postProjectFinder.findApplication(A, B);			
				if (pa == null) {
					pa = new PostApplication();
					pa.setIsImported(1);
					pa.setImportedDate(new Date());

					pa.setName(A);
					pa.setYear(Integer.parseInt(C));
					pa.setSubtype(subTypeMap.get(F));

					Agency univ = universityFinder.getUnivByName(E);
					if (univ != null) {
						pa.setUniversity(univ);
						pa.setAgencyName(univ.getName());
					} else {
						pa.setAgencyName(E);
					}
					addApplicant(pa, B, E);
					pa.setDepartment(pa.getPostMember().iterator().next().getDepartment());
					pa.setInstitute(pa.getPostMember().iterator().next().getInstitute());
					pa.setDivisionName(pa.getPostMember().iterator().next().getDivisionName());

					pa.setFinalAuditResult(2);
					pa.setFinalAuditStatus(3);

					saveOrUpdate(pa);
					
					if (H.length() > 0) {
						pa.setDisciplineType(G);
						pa.setDiscipline(tool.transformDisc(G));
					}
				}

				PostGranted pg = new PostGranted();
				pa.addGranted(pg);
				
				pg.setNumber(D);
				pg.setName(A);
				
				pg.setSubtype(subTypeMap.get(F));

				pg.setStatus(1);
				pg.setIsImported(1);
				pg.setApproveFee(Double.valueOf(H));
				pg.setImportedDate(new Date());
				pg.setApplicantId(pa.getApplicantId());
				pg.setApplicantName(pa.getApplicantName());
				
				pg.setUniversity(pa.getUniversity());
				pg.setAgencyName(pa.getAgencyName());
				pg.setDepartment(pa.getDepartment());
				pg.setInstitute(pa.getInstitute());
				pg.setDivisionName(pa.getDivisionName());
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
	private void addApplicant(KeyApplication pa, String personName, String agencyName) throws Exception {
		if (agencyName.isEmpty()) {
			agencyName = "未知机构";
		}
		
		Person applicant = null;
		KeyMember km = new KeyMember();
		
		//判断机构名是否学校名称，以判断该负责人是否教师。
		Agency univ = universityFinder.getUnivByName(agencyName);
		if (univ != null) {
			//教师
			Teacher teacher = univPersonFinder.findTeacher(personName, univ);
			applicant = teacher.getPerson();

			km.setUniversity(teacher.getUniversity());
			km.setDepartment(teacher.getDepartment());
			km.setInstitute(teacher.getInstitute());
			km.setDivisionName(teacher.getDepartment() != null ? teacher.getDepartment().getName() : teacher.getInstitute() != null ? teacher.getInstitute().getName() : null);
			km.setMemberType(1);
		} else {
			//外部专家
			Expert expert = expertFinder.findExpert(personName, agencyName);
			applicant = expert.getPerson();

			km.setMemberType(2);
		}

		km.setMember(applicant);
		km.setMemberName(applicant.getName());
		km.setAgencyName(agencyName);
		km.setIsDirector(1);
		
//		Academic academic = applicant.getAcademicEntity();
//		km.setSpecialistTitle(academic.getSpecialityTitle());
		
		pa.addMember(km);
		km.setMemberSn(pa.getKeyMember().size());
		
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
	 * 检查数据是否库内存在
	 * @throws Exception 
	 */
	private void checkProjectExistence() throws Exception {
		
		excelReader.readSheet(0);//攻关
		int i = 0;
		int k = 0;
		int m = 0;
		while (next(excelReader)) {
			if(C.length() == 0) {
				break;
			}
			KeyGranted granted = keyProjectFinder.findGranted(A);
			if (granted == null) {
				System.out.println("找不到的项目的立项数据：" + D + " - " + G + " - " + A + "项目年度为：" + B);
				i++;
				KeyApplication keyApplication = keyProjectFinder.findApplication(D, G);
				if (keyApplication == null) {
					System.out.println("找不到的项目的申请数据：" + D + " - " + G + " - " + A);
					m++;
				}
			} else {
				if (granted.getStatus() == 1) {
					k++;
				} else {
					System.out.println("剩余的项目：" + D + " - " + G);
				}
			}
			if (granted != null && granted.getStatus() == 0) {
				granted.setStatus(1);
				dao.addOrModify(granted);
			}
		}
		System.out.println("找不到立项信息的项目数"+i);
		System.out.println("找不到立项也找不到申请的项目数"+m);
		System.out.println("存在立项且状态是在研的项目数"+k);
		
		
		excelReader.readSheet(2);//基地
		while (next(excelReader)) {
			if(C.length() == 0) {
				break;
			}
			InstpGranted granted = instpProjectFinder.findGranted(G);
			if (granted == null) {
				System.out.println("找不到的项目：" + C + " - " + E + " - " + G);
				i++;
				InstpApplication instpApplication = instpProjectFinder.findApplication(C, E);
				if (instpApplication == null) {
					System.out.println("找不到的项目的申请数据：" + D + " - " + G + " - " + A);
					m++;
				}
			} else {
				if (granted.getStatus() == 1) {
					k++;
				} else {
					System.out.println("剩余的项目：" + C + " - " + G);
				}
			}
			if (granted != null && granted.getStatus() == 0) {
				granted.setStatus(1);
				dao.addOrModify(granted);
			}
		}
		System.out.println("找不到立项信息的项目数"+i);
		System.out.println("找不到立项也找不到申请的项目数"+m);
		System.out.println("存在立项且状态是在研的项目数"+k);
		
		excelReader.readSheet(1);//后期资助
		while (next(excelReader)) {
			if(A.length() == 0) {
				break;
			}
			PostGranted granted = postProjectFinder.findGranted(D);
			if (granted == null) {
				System.out.println("找不到的项目：" + D + " - " + B + " - " + A + "立项年度：" + C);
				i++;
				PostApplication postApplication = postProjectFinder.findApplication(A, B);
				if (postApplication == null) {
					System.out.println("找不到的项目的申请数据：" + A + " - " + B + "立项年度：" + C);
					m++;
				}
			} else {
				if (granted.getStatus() == 1) {
					k++;
				} else {
					System.out.println("剩余的项目：" + D + " - " + A);
				}
			}
			if (granted != null && granted.getStatus() == 0) {
				granted.setStatus(1);
				dao.addOrModify(granted);				
			}
		}
		System.out.println("找不到立项信息的项目数"+i);
		System.out.println("找不到立项也找不到申请的项目数"+m);
		System.out.println("存在立项且状态是在研的项目数"+k);
	}

	/**
	 * 检查学校是否库内存在
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedEncodingException 
	 */
	private void validateData() throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, Exception {
		excelReader.readSheet(0);//重大攻关
		
		while (next(excelReader)) if (!A.isEmpty()) {
			Agency univ = universityFinder.getUnivByName(F);
			if (univ == null) {
				System.out.println("找不到的学校：" + F);
			}
		}
		
		excelReader.readSheet(1);//后期资助
		
		while (next(excelReader)) if (!A.isEmpty()) {
			Agency univ = universityFinder.getUnivByName(F);
			if (univ == null) {
				System.out.println("找不到的学校：" + F);
			}
		}
		
	}
	
}

