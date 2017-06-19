package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashSet;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;

public class GeneralProjectApplication2007Importer extends Importer {
	
	/**
	 * 项目子类名
	 */
	private SystemOption 青年基金项目 = (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0);
	private SystemOption 规划基金项目 = (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0);
	private SystemOption 专项任务项目 = (SystemOption) baseService.query("select so from SystemOption so where so.name = '专项任务项目'").get(0);

	/**
	 * 不存在的学校名称/代码
	 */
	private HashSet<String> invalidUniv = new HashSet<String>();

	
	public GeneralProjectApplication2007Importer(File file) {
		super(file);
	}
	
	public void work() throws Exception {
		getContentFromExcel(0);
		
		while (next()) {
			String projectName = row[2].isEmpty() ? "#暂无信息#" : row[2].replaceAll("[　\\s]+", "");
			String applicantName = row[1].replaceAll("[　\\s]+", "");
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			String univName = tools.getNewestUnivName(row[0]);
			String univCode = row[14];
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
			ga.setYear(2007);
			ga.setUniversity(university);
			ga.setAgencyName(university.getName());
			if (row[3].contains("青年")) {
				ga.setSubtype(青年基金项目);
			} else if (row[3].contains("规划")) {
				ga.setSubtype(规划基金项目);
			} else if (row[3].contains("专项")) {
				ga.setSubtype(专项任务项目);
			}
			ga.setName(projectName);
			ga.setApplicant(applicant);
			ga.setApplicantName(applicantName);
			ga.setDiscipline(tools.transformDisc(disc));
			ga.setPlanEndDate(tools.getDate(row[11]));
			ga.setProductType(row[12]);
			ga.setApplyFee(tools.getFee(row[13]));
			ga.setDisciplineType(row[4]);

			//申请人
			applicant.setBirthday(tools.getDate(row[8]));
			
			//处理电话
			String phones[] = tools.getPhones(row[10] + " " + applicant.getMobilePhone() + " " + applicant.getOfficePhone() + "(o) " + applicant.getHomePhone() + "(h)");
			applicant.setMobilePhone(phones[0]);
			applicant.setOfficePhone(phones[1]);
			applicant.setHomePhone(phones[2]);

			//学术信息
			academic.setDiscipline(tools.transformDisc(disc + " " + academic.getDiscipline()));
			academic.setSpecialityTitle(row[7]);
			academic.setResearchField(row[5]);
			academic.setLastDegree(row[9]);
			
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
		if (invalidUniv.size() > 0) {
			throw new Exception("以下学校不存在:\n" + invalidUniv.toString().replaceAll(",", "\n"));
		}
		
		finish();
	}

}
