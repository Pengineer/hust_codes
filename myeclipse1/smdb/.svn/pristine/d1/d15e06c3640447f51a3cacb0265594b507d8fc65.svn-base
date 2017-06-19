package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;

public class GeneralProjectApplication2008Importer extends Importer {
	
	/**
	 * 项目子类名称到项目子类实体的映射
	 */
	private Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();

	/**
	 * 不存在的学校名称/代码
	 */
	private HashSet<String> invalidUniv = new HashSet<String>();

	/**
	 * 异常信息
	 */
	private StringBuffer exceptionMsg = new StringBuffer();
	

	public GeneralProjectApplication2008Importer(File file) {
		super(file);
		
		subTypeMap.put("青年基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("规划基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("专项任务项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '专项任务项目'").get(0));
	}
	
	
	public void work() throws Exception {
		getContentFromExcel(0);
		
		while (next()) {
			String projectName = row[2].replaceAll("[　\\s]+", "");
			String applicantName = row[7].replaceAll("[　\\s]+", "");
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			String univName = tools.getNewestUnivName(row[1]);
			String univCode = row[0];
			Agency university = tools.getUnivByCode(univCode);
			if (university == null) {
				university = tools.getUnivByName(univName);
			}
			if (university == null) {
				invalidUniv.add(univName);
				invalidUniv.add(univCode);
			}
			if (invalidUniv.size() > 0) {
				continue;
			}

			Teacher teacher = tools.getTeacher(applicantName, university);
			Person applicant = teacher.getPerson();
			Academic academic = tools.getAcademic(applicant);
			Department department = teacher.getDepartment();
			String disc = row[4] + " " + row[5] + " " + row[6];
			
			//项目申请
			GeneralApplication ga = new GeneralApplication();
			ga.setIsGranted(1);
			ga.setIsImported(1);
			ga.setDepartment(department);
			ga.setDivisionName(department.getName());
			ga.setYear(2008);
			ga.setUniversity(university);
			ga.setAgencyName(university.getName());
			ga.setSubtype(subTypeMap.get(row[3].replaceAll("\\s+", "")));
			ga.setName(projectName);
			ga.setApplicant(applicant);
			ga.setApplicantName(applicantName);
			ga.setDiscipline(tools.transformDisc(disc));
			ga.setPlanEndDate(tools.getDate(row[12]));
			ga.setProductType(row[13]);
			ga.setApplyFee(tools.getFee(row[14]));
			ga.setDisciplineType(row[4]);

			//申请人
			applicant.setBirthday(tools.getDate(row[9]));
			
			//处理电话
			String phones[] = tools.getPhones(row[11] + " " + applicant.getMobilePhone() + " " + applicant.getOfficePhone() + "(o) " + applicant.getHomePhone() + "(h)");
			applicant.setMobilePhone(phones[0]);
			applicant.setOfficePhone(phones[1]);
			applicant.setHomePhone(phones[2]);

			//学术信息
			academic.setDiscipline(tools.transformDisc(disc + " " + academic.getDiscipline()));
			academic.setSpecialityTitle(row[8]);
			academic.setResearchField(row[5]);
			academic.setLastDegree(row[10]);
			
			//项目成员
			GeneralMember gm = new GeneralMember();
			gm.setUniversity(university);
			gm.setDepartment(department);
			gm.setDivisionName(department.getName());
			gm.setApplication(ga);
			gm.setMember(applicant);
			gm.setMemberName(applicant.getName());
			gm.setAgencyName(university.getName());
			gm.setSpecialistTitle(academic.getSpecialityTitle());
			gm.setIsDirector(1);
			gm.setMemberSn(1);

			if (department.getId() == null) {
				saveOrUpdate(department);
			}
			saveOrUpdate(applicant);
			saveOrUpdate(teacher);
			saveOrUpdate(academic);
			saveOrUpdate(ga);
			saveOrUpdate(gm);
		}
		if (invalidUniv.size() > 0 || exceptionMsg.length() > 0) {
			throw new Exception("以下学校不存在:\n" + invalidUniv.toString().replaceAll(",", "\n") + exceptionMsg);
		}
		
		finish();
	}

}
