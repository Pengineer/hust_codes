package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.EntrustApplication;
import csdc.bean.EntrustGranted;
import csdc.bean.EntrustMember;
import csdc.bean.Person;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.EntrustProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《委托项目在研名单（截至2015年3月31日）.xls》
 * @author pengliang
 *
 */

public class EntrustProjectApplicationAndGranted20150409Importer extends Importer {
	
	private ExcelReader reader;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private EntrustProjectFinder entrustProjectFinder;
	
	@Autowired
	private AcademicFinder academicFinder;
	
	@Override
	protected void work() throws Exception {
//		validate();
//		checkEntrustExistInDB();
		importApplicationData();
	}
	
	private void resetReader() {
		reader.readSheet(0);		
	}	


	private void importApplicationData() throws Exception {
		resetReader();

		while (next(reader)) {
			if (A !=null && !A.isEmpty()) {
				if (entrustProjectFinder.findGranted(A.trim()) !=null){
					continue;
				}
				System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber() + ":" + B + "——"+ D.trim());
				EntrustApplication application = new EntrustApplication();
				Agency university = universityFinder.getUnivByName(C.trim());
				Teacher applicant = univPersonFinder.findTeacher(D.replaceAll("\\s+", ""), university);
				Academic academic = academicFinder.findAcademic(applicant.getPerson());
				if (academic == null) {
					academic = new Academic();
					academic.setPerson(applicant.getPerson());
					applicant.getPerson().setAcademic(academic);
				}
				application.setYear(Integer.parseInt(E.trim()));
				application.setName(B.trim());
				application.setUniversity(university);
				application.setAgencyName(university.getName());
				application.setApplicantId(applicant.getPerson().getId());
				application.setApplicantName(applicant.getPerson().getName());
				
				application.setImportedDate(new Date());
				application.setIsImported(1);
				dao.addOrModify(application);
				
				EntrustMember entrustMember = new EntrustMember();
				application.addMember(entrustMember);
				Person person = applicant.getPerson();
				entrustMember.setUniversity(applicant.getUniversity());
				entrustMember.setInstitute(applicant.getInstitute());
				entrustMember.setDepartment(applicant.getDepartment());
				entrustMember.setAgencyName(applicant.getUniversity().getName());
				if (applicant.getDepartment() != null) {
					entrustMember.setDivisionName(applicant.getDepartment().getName());
					entrustMember.setDivisionType(2);
				} else {
					entrustMember.setDivisionName(applicant.getInstitute().getName());
					entrustMember.setDivisionType(1);
				}
				entrustMember.setWorkMonthPerYear(applicant.getWorkMonthPerYear());
				entrustMember.setMemberType(1);
				entrustMember.setMember(person);
				entrustMember.setMemberName(person.getName());
				entrustMember.setIsDirector(1);
				entrustMember.setMemberSn(application.getMember().size());
				entrustMember.setIdcardType(person.getIdcardType());
				entrustMember.setIdcardNumber(person.getIdcardNumber());
				entrustMember.setGender(person.getGender());			
				entrustMember.setGroupNumber(1);
				
				EntrustGranted granted = new EntrustGranted();
				granted.setStatus(1);
				granted.setIsImported(1);
				granted.setImportedDate(new Date());
				application.addGranted(granted);
				granted.setUniversity(university);
//				granted.setApproveDate(tool.getDate(2014, 7, 29));
				granted.setAgencyName(application.getAgencyName());
				
				granted.setApplicantId(application.getApplicantId());
				granted.setApplicantName(application.getApplicantName());
				granted.setMemberGroupNumber(1);
				
				granted.setSubtype(application.getSubtype());
				granted.setName(B.trim());
				granted.setNumber(A.trim());
				
				granted.getApplication().setFinalAuditResult(2);
				granted.getApplication().setFinalAuditStatus(3);
//				granted.getApplication().setFinalAuditDate(tool.getDate(2014, 7, 28));
				
				dao.add(granted);
				dao.add(entrustMember);
			}
		}
	}
	
	/**
	 * 判断所有项目在库中是否已经存在
	 * @throws Exception 
	 */
	public void checkEntrustExistInDB() throws Exception{
		reader.readSheet(0);
		Set<String> exMsg = new HashSet<String>();
		int noGrantedCount =0, noAppCount =0;
		
		while(next(reader)) {
			if (A !=null && !A.isEmpty()) {
				EntrustGranted granted = entrustProjectFinder.findGranted(A.trim());
				if (granted == null) {
					exMsg.add("entrust项目：找不到的项目：" + B + " - " + D + " - " + E);
					noGrantedCount++;
					EntrustApplication postApplication = entrustProjectFinder.findApplication(B.trim(), D.trim(), Integer.parseInt(E.trim()));
					if (postApplication == null) {
						exMsg.add("entrust项目：找不到的项目的申请数据：" + B + " - " + D + " - " + E);
						noAppCount++;
					}
				} else {
					System.out.println(B + " - " + D + " - " + E);
				}
				if (granted != null && granted.getStatus() == 0) {
					granted.setStatus(1);
					dao.addOrModify(granted);
				}
			}
		}
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			System.out.println("申请不存在项目数：" + noAppCount);
			System.out.println("立项不存在项目数：" + noGrantedCount);
			System.out.println("总项目条数：" + reader.getRowNumber());
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
	
	public EntrustProjectApplicationAndGranted20150409Importer(){
	}
	
	public EntrustProjectApplicationAndGranted20150409Importer(String file) {
		reader = new ExcelReader(file);
	}
}
