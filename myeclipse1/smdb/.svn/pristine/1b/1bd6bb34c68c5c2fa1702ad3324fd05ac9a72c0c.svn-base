package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Expert;
import csdc.bean.InstpApplication;
import csdc.bean.InstpGranted;
import csdc.bean.KeyApplication;
import csdc.bean.KeyFunding;
import csdc.bean.KeyGranted;
import csdc.bean.KeyMember;
import csdc.bean.KeyTopic;
import csdc.bean.Person;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.KeyProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《评价中心在研项目名单（截至2015年3月31日）_修正导入.xls》
 * @author pengliang
 * 说明：入库2014年重大攻关项目，同时核查评价中心提供的基地项目在库中是否存在
 */
public class KeyInstpOnStudy20150408Importer extends Importer {

	ExcelReader excelReader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private KeyProjectFinder keyProjectFinder;
	
	@Autowired
	private ExpertFinder expertFinder;
	
	@Autowired
	private Tool tool;
	
	@Override
	protected void work() throws Throwable {
//		validateUniversity();
		checkInstpExistInDB();
		checkKeyExistInDB();
		importData();
	}
	
	/**
	 * 导入2014年重大攻关在研项目：申请 + 成员 + 立项
	 * @throws Exception
	 */
	public void importData() throws Exception{
		excelReader.readSheet(0);//重大攻关
		excelReader.setCurrentRowIndex(233);
		while (next(excelReader)) {
			if (A == null || "".equals(A.trim())) {
				break;
			}
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber() + ":" + D + "——" + G);
			//新建重大攻关选题
			KeyTopic kts = new KeyTopic();
			kts.setIsImported(1);
			kts.setImportedDate(new Date());

			kts.setName(D.trim());
			kts.setYear(2014);
			//判断机构名是否学校名称，以判断该负责人是否教师。
			Agency univ = universityFinder.getUnivByName(E.trim());
			Person applicant = null;
			if (univ != null) {
				//教师
				Teacher teacher = univPersonFinder.findTeacher(G.replaceAll("\\s+", ""), univ);
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
			//新建重大攻关项目
			KeyApplication kapplication = new KeyApplication();
			kapplication.setTopicSelection(kts);
			
			kapplication.setIsImported(1);
			kapplication.setImportedDate(new Date());

			kapplication.setApplicantName(applicant.getName());
			kapplication.setApplicantId(applicant.getId());
			kapplication.setName(D.trim());
			kapplication.setYear(2014);

			if (univ != null) {
				kapplication.setUniversity(univ);
				kapplication.setAgencyName(univ.getName());
			} else {
				kapplication.setAgencyName(E.trim());
			}
			addApplicant(kapplication, G, E);
			kapplication.setDepartment(kapplication.getKeyMember().iterator().next().getDepartment());
			kapplication.setInstitute(kapplication.getKeyMember().iterator().next().getInstitute());
			kapplication.setDivisionName(kapplication.getKeyMember().iterator().next().getDivisionName());

			kapplication.setFinalAuditResult(2);
			kapplication.setFinalAuditStatus(3);
			
			//添加立项信息
			KeyGranted kg = new KeyGranted();
			KeyFunding funding = new KeyFunding();
			kapplication.addGranted(kg);
			
			kg.setApproveDate(tool.getDate(2014, 9, 11));
			kg.setNumber(A);
			kg.setName(D);

			kg.setNote(M);
			kg.setStatus(1);
			kg.setIsImported(1);
			kg.setImportedDate(new Date());
			kg.setIsDupCheckGeneral(1);
			kg.setApplicantId(kapplication.getApplicantId());
			kg.setApplicantName(kapplication.getApplicantName());
			
			kg.setUniversity(kapplication.getUniversity());
			kg.setAgencyName(kapplication.getAgencyName());
			kg.setDepartment(kapplication.getDepartment());
			kg.setInstitute(kapplication.getInstitute());
			kg.setDivisionName(kapplication.getDivisionName());
			
			funding.setFee(tool.getFee(F));
			funding.setStatus(1);
			funding.setType("granted");
			kg.addFunding(funding);
			
			saveOrUpdate(kapplication);
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
			throw new RuntimeException();
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
	}
	
	/**
	 * 核查评价中心提供的2014年之前的重大攻关项目在库中是否存在
	 * @throws Exception
	 */
	public void checkKeyExistInDB() throws Exception {
		excelReader.readSheet(0);//重大攻关
		
		Set<String> exMsg = new HashSet<String>();
		
		while (next(excelReader)) {
			if(A == null || "".equals(A.trim()) || Integer.parseInt(C.trim()) > 2013) {
				break;
			}

			KeyGranted granted = keyProjectFinder.findGranted(A);
			
			if (granted == null) {
				exMsg.add("重大攻关：找不到的项目：" + D + " - " + G + " - " + A);
				KeyApplication keyApplication = keyProjectFinder.findApplication(D.trim(), G.trim());
				if (keyApplication == null) {
					exMsg.add("重大攻关：找不到的项目的申请数据：" + D + " - " + G);
				}
			} 
			if (granted != null && granted.getStatus() == 0) {
				granted.setStatus(1);
				dao.addOrModify(granted);
			}
		}
		
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	/**
	 * 核查评价中心提供的在研基地项目在库中是否存在
	 * @throws Exception
	 */
	public void checkInstpExistInDB() throws Exception {
		excelReader.readSheet(1);//基地
		
		Set<String> exMsg = new HashSet<String>();
		
		while (next(excelReader)) {
			if(A == null || "".equals(A.trim())) {
				break;
			}
			InstpGranted granted = instpProjectFinder.findGranted(G.trim());
			if (granted == null) {
				exMsg.add("基地项目：找不到的项目：" + C + " - " + E + " - " + G);
				InstpApplication instpApplication = instpProjectFinder.findApplication(C.trim(), E.trim());
				if (instpApplication == null) {
					exMsg.add("基地项目：找不到的项目的申请数据：" + C + " - " + E + "-" + F);
				}
			} 
			if (granted != null && granted.getStatus() == 0) {
				granted.setStatus(1);
				dao.addOrModify(granted);
			}
		}
		
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}

	/**
	 * 检查学校是否库内存在
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws UnsupportedEncodingException 
	 */
	private void validateUniversity() throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, Exception {
		excelReader.readSheet(0);//重大攻关
		
		Set<String> exMsg = new HashSet<String>();
		while (next(excelReader)) if (!A.isEmpty()) {
			Agency univ = universityFinder.getUnivByName(E.trim());
			if (univ == null) {
				exMsg.add("重大攻关：找不到的学校：" + E);
			}
		}
		
		excelReader.readSheet(1);//基地
		
		while (next(excelReader)) if (!A.isEmpty()) {
			Agency univ = universityFinder.getUnivByName(A.trim());
			if (univ == null) {
				exMsg.add("基地：找不到的学校：" + A);
			}
		}
		
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public KeyInstpOnStudy20150408Importer() {

	}
	
	public KeyInstpOnStudy20150408Importer(String fileName) {
		excelReader = new ExcelReader(fileName);
	}

}
