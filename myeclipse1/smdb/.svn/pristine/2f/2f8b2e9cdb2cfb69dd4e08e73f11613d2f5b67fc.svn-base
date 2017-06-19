package csdc.tool.execution.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;

@SuppressWarnings("unchecked")
public class GeneralProjectApplication2009Importer extends Importer {
	
	private List dataList = new ArrayList();

	/**
	 * 项目子类名称到项目子类实体的映射
	 */
	private Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();

	/**
	 * 研究类型名称到研究类型实体的映射
	 */
	private Map<String, SystemOption> researchTypeMap = new HashMap<String, SystemOption>();
	
	
	/**
	 * 学校代码变更
	 */
	private Map<String, String> univCodeChange = new HashMap<String, String>();
	
	/**
	 * 不存在的学校名称/代码
	 */
	private HashSet<String> invalidUniv = new HashSet<String>();

	
	
	public GeneralProjectApplication2009Importer(File file) {
		super(file);
		
		subTypeMap.put("青年基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("规划基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("自筹经费项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '自筹经费项目'").get(0));
		
		researchTypeMap.put("基础研究", (SystemOption) baseService.query("select so from SystemOption so where so.name = '基础研究'").get(0));
		researchTypeMap.put("实验与发展", (SystemOption) baseService.query("select so from SystemOption so where so.name = '实验与发展'").get(0));
		researchTypeMap.put("应用研究", (SystemOption) baseService.query("select so from SystemOption so where so.name = '应用研究'").get(0));
	}
	
	
	public void work() throws Exception {
		getContentFromExcel(0);
		
		while (next()) {
			if (univCodeChange.containsKey(row[4])) {
				row[4] = univCodeChange.get(row[4]);
			}
			String projectName = row[6].replaceAll("[　\\s]+", "");
			String applicantName = row[16].replaceAll("[　\\s]+", "");
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			String univName = row[5];
			String univCode = row[4];
			String departmentName = row[20].replaceAll("\\s+", "");
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
			if (departmentName.length() > 0 && !department.equals(univName)) {
				department = new Department();
				department.setName(departmentName);
				department.setUniversity(university);
				teacher.setDepartment(department);
			}

			String disc = row[8] + " " + row[10];
			
			//项目申请
			GeneralApplication ga = new GeneralApplication();
			ga.setIsImported(1);
			ga.setDepartment(department);
			ga.setDivisionName(department.getName());
			ga.setYear(2009);
			ga.setUniversity(university);
			ga.setAgencyName(university.getName());
			ga.setSubtype(subTypeMap.get(row[7].replaceAll("\\s+", "")));
			ga.setName(projectName);
			ga.setApplicant(applicant);
			ga.setApplicantName(applicantName);
			ga.setDiscipline(tools.transformDisc(disc));
			ga.setPlanEndDate(tools.getDate(row[12]));
			ga.setProductType(row[15]);
			ga.setApplyFee(tools.getFee(row[13]));
			ga.setOtherFee(tools.getFee(row[14]));
			ga.setFile(row[3]);
			ga.setDisciplineType(row[8]);
			ga.setApplicantSubmitDate(tools.getDate(row[9]));
			ga.setResearchType(researchTypeMap.get(row[11]));

			//申请人
			applicant.setGender(row[17]);
			applicant.setBirthday(tools.getDate(row[18]));
			applicant.setOfficeAddress(row[25]);
			applicant.setOfficePostcode(row[26]);
			applicant.setOfficePhone(row[27]);
			applicant.setMobilePhone(row[28]);
			applicant.setEmail(row[29]);
			applicant.setIdcardNumber(row[30].replaceAll("^\\D+", ""));
			
			//教师
			teacher.setPosition(row[21]);
			

			//学术信息
			academic.setDiscipline(tools.transformDisc(disc + " " + academic.getDiscipline()));
			academic.setSpecialityTitle(row[19]);
			academic.setResearchField(tools.toDBC(row[10]).replaceAll("\\([\\s\\S]*?\\)", ""));
			academic.setLastEducation(row[22]);
			academic.setLastDegree(row[23]);
			academic.setLanguage(row[24]);
			
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

			//其他成员
			List<GeneralMember> otherMembers = tools.getGeneralMemberList(ga, row[31]);

			if (department.getId() == null) {
				saveOrUpdate(department);
			}
			saveOrUpdate(applicant);
			saveOrUpdate(teacher);
			saveOrUpdate(academic);
			saveOrUpdate(ga);
			saveOrUpdate(gm);	//负责人
			dataList.addAll(otherMembers);	//同伙
		}
		if (invalidUniv.size() > 0) {
			throw new Exception("以下学校不存在:\n" + invalidUniv.toString().replaceAll(",", "\n"));
		}
		
		finish();
	}

}
