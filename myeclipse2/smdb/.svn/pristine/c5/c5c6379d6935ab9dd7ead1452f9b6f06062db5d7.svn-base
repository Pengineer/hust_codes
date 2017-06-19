package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;

public class GeneralProjectApplication2005Importer extends Importer {
	
	/**
	 * 项目子类名称到项目子类实体的映射
	 */
	private Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();

	/**
	 * 学校代码变更
	 */
	private Map<String, String> univCodeChange = new HashMap<String, String>();

	
	
	public GeneralProjectApplication2005Importer(File file) {
		super(file);
		
		subTypeMap.put("教育部青年人文社会科学研究基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("教育部人文社会科学研究规划项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("教育部人文社会科学研究专项任务项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '专项任务项目'").get(0));
		
		univCodeChange.put("10575", "11078");
		univCodeChange.put("10266", "10248");
		univCodeChange.put("10079", "10054");
		univCodeChange.put("10916", "11071");
		univCodeChange.put("11515", "11070");
		univCodeChange.put("11292", "10351");
	}
	
	
	public void work() throws Exception {
		getContentFromExcel(0);
		StringBuffer exceptionMsg = new StringBuffer();
		
		while (next()) {
			if (univCodeChange.containsKey(row[6])) {
				row[6] = univCodeChange.get(row[6]);
			}
			String projectName = row[3].replaceAll("[　\\s]+", "");
			String applicantName = row[4].replaceAll("[　\\s]+", "");
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			String univCode = row[6];
			Agency university = tools.getUnivByCode(univCode);
			Teacher teacher = tools.getTeacher(applicantName, university);
			Person applicant = teacher.getPerson();
			Academic academic = tools.getAcademic(applicant);
			Department department = teacher.getDepartment();
			String disc = row[9] + " " + row[10] + " " + row[11] + " " + row[12] + " " + row[13] + " " + row[14];

			if (university == null) {
				exceptionMsg.append("项目:" + projectName + " 学校代码:" + univCode + " 学校不存在\n");
			}
			if (exceptionMsg.length() > 0) {
				continue;
			}
			
			GeneralApplication ga = new GeneralApplication();
			ga.setIsGranted(1);
			ga.setIsImported(1);
			ga.setDepartment(department);
			ga.setDivisionName(department.getName());
			ga.setYear(2005);
			ga.setUniversity(university);
			ga.setAgencyName(university.getName());
			ga.setSubtype(subTypeMap.get(row[2]));
			ga.setName(projectName);
			ga.setApplicant(applicant);
			ga.setApplicantName(applicantName);
			ga.setDiscipline(tools.transformDisc(disc));

			if (row[16] != null && !row[16].isEmpty()) {
				teacher.setPosition(row[16]);
			}

			academic.setDiscipline(tools.transformDisc(disc + " " + academic.getDiscipline()));
			academic.setSpecialityTitle(row[17]);

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
			
			applicant.setBirthday(tools.getDate(row[5]));

			
			if (department.getId() == null) {
				saveOrUpdate(department);
			}
			saveOrUpdate(applicant);
			saveOrUpdate(teacher);
			saveOrUpdate(academic);
			saveOrUpdate(ga);
			saveOrUpdate(gm);
		}
		if (exceptionMsg.length() > 0) {
			throw new Exception(exceptionMsg.toString());
		}
		
		finish();
	}

}
