package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.InstpApplication;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.PostApplication;
import csdc.bean.PostGranted;
import csdc.bean.PostMember;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.PostProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《评价中心在研项目名单（截至2015年3月31日）_修正导入.xls》
 * 本代码只入库2014年后期资助项目
 * @author pengliang
 *
 */

public class PostProjectApplicationAndGranted2014Importer extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private PostProjectFinder postProjectFinder;
	
	@Autowired
	private AcademicFinder academicFinder;


	@Autowired
	private SystemOptionDao systemOptionDao;

	/**
	 * 项目年度
	 */
	private final int year = 2014;
	
	@Override
	protected void work() throws Exception {
		validate();
		checkPostExistInDB();
		importApplicationData();
	}
	
	private void resetReader() {
		reader.readSheet(2);		
	}	


	private void importApplicationData() throws Exception {
		resetReader();
		
		Officer 白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");
		SystemOption 一般 = systemOptionDao.query("projectType", "0403");
		SystemOption 重大 = systemOptionDao.query("projectType", "0402");
		SystemOption 重点 = systemOptionDao.query("projectType", "0401");
		
		reader.setCurrentRowIndex(170);
		while (next(reader)) {
			if (A !=null && !A.isEmpty()) {
				System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber() + ":" + B + "——"+ E.trim());
				PostApplication application = new PostApplication();
				Agency university = universityFinder.getUnivByName(C.trim());
				Teacher applicant = univPersonFinder.findTeacher(D.replaceAll("\\s+", ""), university);
				Academic academic = academicFinder.findAcademic(applicant.getPerson());
				if (academic == null) {
					academic = new Academic();
					academic.setPerson(applicant.getPerson());
					applicant.getPerson().setAcademic(academic);
				}
				application.setYear(year);
				application.setName(E.trim());
				application.setUniversity(university);
				application.setAgencyName(university.getName());
				application.setApplicantId(applicant.getPerson().getId());
				application.setApplicantName(applicant.getPerson().getName());
				
				if (F.contains("一般")) {
					application.setSubtype(一般);
				} else if (F.contains("重点")) {
					application.setSubtype(重点);
				} else if (F.contains("重大")) {
					application.setSubtype(重大);
				}
				
				application.setImportedDate(new Date());
				application.setIsImported(1);
				dao.addOrModify(application);
				
				PostMember postMember = new PostMember();
				application.addMember(postMember);
				Person person = applicant.getPerson();
				postMember.setUniversity(applicant.getUniversity());
				postMember.setInstitute(applicant.getInstitute());
				postMember.setDepartment(applicant.getDepartment());
				postMember.setAgencyName(applicant.getUniversity().getName());
				if (applicant.getDepartment() != null) {
					postMember.setDivisionName(applicant.getDepartment().getName());
					postMember.setDivisionType(2);
				} else {
					postMember.setDivisionName(applicant.getInstitute().getName());
					postMember.setDivisionType(1);
				}
				postMember.setWorkMonthPerYear(applicant.getWorkMonthPerYear());
				postMember.setMemberType(1);
				postMember.setMember(person);
				postMember.setMemberName(person.getName());
				postMember.setIsDirector(1);
				postMember.setMemberSn(application.getMember().size());
				postMember.setIdcardType(person.getIdcardType());
				postMember.setIdcardNumber(person.getIdcardNumber());
				postMember.setGender(person.getGender());			
				postMember.setGroupNumber(1);
				
				PostGranted granted = new PostGranted();
				granted.setStatus(1);
				granted.setIsImported(1);
				granted.setImportedDate(new Date());
				application.addGranted(granted);
				granted.setUniversity(university);
				granted.setApproveDate(tool.getDate(2014, 7, 29));
				granted.setAgencyName(application.getAgencyName());
				
				granted.setApplicantId(application.getApplicantId());
				granted.setApplicantName(application.getApplicantName());
				granted.setMemberGroupNumber(1);
				
				granted.setSubtype(application.getSubtype());
				granted.setName(E.trim());
				granted.setNumber(A.trim());
				
				granted.getApplication().setFinalAuditResult(2);
				granted.getApplication().setFinalAuditStatus(3);
				granted.getApplication().setFinalAuditor(白晓); 
				granted.getApplication().setFinalAuditorName(白晓.getPerson().getName());
				granted.getApplication().setFinalAuditorAgency(白晓.getAgency());
				granted.getApplication().setFinalAuditorInst(白晓.getInstitute());
				granted.getApplication().setFinalAuditDate(tool.getDate(2014, 7, 28));
				
				dao.add(granted);
				dao.add(postMember);
				
			}
		}
	}
	
	/**
	 * 判断2014年之前的项目在库中是否已经存在
	 * @throws Exception 
	 */
	public void checkPostExistInDB() throws Exception{
		reader.readSheet(2);
		Set<String> exMsg = new HashSet<String>();
		
		while(next(reader)) {
			if (A !=null && !A.isEmpty() && Integer.parseInt(B) < 2014) {
				PostGranted granted = postProjectFinder.findGranted(A.trim());
				if (granted == null) {
					exMsg.add("post项目：找不到的项目：" + E + " - " + D + " - " + A);
					PostApplication postApplication = postProjectFinder.findApplication(C.trim(), E.trim());
					if (postApplication == null) {
						exMsg.add("post项目：找不到的项目的申请数据：" + E + " - " + D);
					}
				} 
				if (granted != null && granted.getStatus() == 0) {
					granted.setStatus(1);
					dao.addOrModify(granted);
				}
			}
		}
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}

	private void validate() throws Exception {
		resetReader();
		
		HashSet<String> exMsg = new HashSet<String>();
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(C.trim());
			if (university == null) {
				exMsg.add("不存在的高校: " + C);
			}					
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}	
	}
	
	public PostProjectApplicationAndGranted2014Importer(){
	}
	
	public PostProjectApplicationAndGranted2014Importer(String file) {
		reader = new ExcelReader(file);
	}


	
}
