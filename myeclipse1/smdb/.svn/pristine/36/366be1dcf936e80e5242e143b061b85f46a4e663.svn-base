package csdc.tool.execution.importer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;

/**
 * 导入2008年一般项目立项信息
 * @author xuhan
 * 
 * 找不到的项目：北京大学文化百年研究 
 * 找不到的项目：高校人文社会科学研究与境外基金利用 
 * 找不到的项目：特色型大学发展的政策环境研究 
 * 找不到的项目：研究型大学的科研特色是与定位 
 * 找不到的项目：特色型大学的形成与发展道路研究 
 * 找不到的项目：中美研究型大学战略规划制定研究——对985工程三期高校战略规划制定的政策建议 
 *
 */
@SuppressWarnings("unchecked")
public class GeneralProjectGranted2008Importer extends Importer {
	
	/**
	 * 项目子类名称到项目子类实体的映射
	 */
	private Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();

	/**
	 * 项目名称到项目申请的映射
	 */
	private Map<String, GeneralApplication> gaMap1 = new HashMap<String, GeneralApplication>();
	
	/**
	 * 学校名+申请人姓名 到 项目申请的映射
	 */
	private Map<String, GeneralApplication> gaMap2 = new HashMap<String, GeneralApplication>();
	
	public GeneralProjectGranted2008Importer(File file) {
		super(file);
		
		subTypeMap.put("青年基金", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("规划基金", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("专项任务", (SystemOption) baseService.query("select so from SystemOption so where so.name = '专项任务项目'").get(0));

		List<GeneralApplication> list = baseService.query("select ga from GeneralApplication ga where ga.year = 2008 ");
		Set<String> existingProjectNames = new HashSet<String>();
		for (GeneralApplication generalApplication : list) {
			//只有在当年唯一的项目名才能作为寻找相应申请实体的依据
			String projectName = generalApplication.getName();
			if (gaMap1.containsKey(projectName)) {
				gaMap1.remove(projectName);
			} else if (!existingProjectNames.contains(projectName)) {
				gaMap1.put(projectName, generalApplication);
			}
			existingProjectNames.add(projectName);
			
			//在根据项目名称找不到时，可根据学校名+申请人姓名寻找
			gaMap2.put(generalApplication.getAgencyName() + generalApplication.getApplicantName(), generalApplication);
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public void work() throws Exception {
		getContentFromExcel(0);
		StringBuffer exceptionMsg = new StringBuffer();
		StringBuffer missingApplicationName = new StringBuffer();
		
		while (next()) {
			String projectName = row[4].replaceAll("[　\\s]+", "");
			String applicantName = row[9].replaceAll("[　\\s]+", "");
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			String univName = tools.getNewestUnivName(row[2]);
			GeneralApplication ga = gaMap1.get(projectName);
			GeneralMember gm = null;

			if (ga == null) {
				ga = gaMap2.get(univName + applicantName);
			}

			Agency university = tools.getUnivByName(univName);
			if (university == null) {
				exceptionMsg.append("学校 -- "+ univName + " 找不到\n");
			}
			if (exceptionMsg.length() > 0) {
				continue;
			}
			Teacher teacher = tools.getTeacher(applicantName, university);
			Person applicant = teacher.getPerson();
			Academic academic = tools.getAcademic(applicant);
			Department department = teacher.getDepartment();

			if (ga == null) {
				//申请数据找不到，新建一个
				missingApplicationName.append("找不到的项目：" + projectName + " \n");

				ga = new GeneralApplication();
				ga.setDisciplineType(row[6]);
				ga.setDiscipline(tools.transformDisc(row[6] + " " + row[7] + " " + row[8]));
				ga.setSubtype(subTypeMap.get(row[3]));
				ga.setIsImported(1);
				ga.setName(projectName);
				ga.setAgencyName(univName);
				ga.setUniversity(university);
				ga.setDepartment(department);
				ga.setDivisionName(department.getName());
				ga.setApplicant(applicant);
				ga.setApplicantName(applicantName);
				ga.setYear(2008);

				gm = new GeneralMember();
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

			}
			ga.setIsGranted(2);
			ga.setPlanEndDate(tools.getDate(row[30]));
			ga.setProductType(row[20]);

			GeneralGranted gg = new GeneralGranted();
			gg.setName(projectName);
			gg.setIsImported(1);
			gg.setSubtype(ga.getSubtype());
			gg.setApplication(ga);
			gg.setApproveFee(tools.getFee(row[21]));
			gg.setNumber(row[5]);

			GeneralFunding gf = new GeneralFunding();
			gf.setGranted(gg);
			gf.setDate(new Date(2008 - 1900, 9 - 1, 1));
			gf.setFee(tools.getFee(row[22]));
			
			Date birthday = tools.getDate(row[25]);
			if (birthday != null) {
				applicant.setBirthday(birthday);
			}
			//处理电话
			String phones[] = tools.getPhones(row[26] + " " + applicant.getMobilePhone() + " " + applicant.getOfficePhone() + "(o) " + applicant.getHomePhone() + "(h)");
			applicant.setMobilePhone(phones[0]);
			applicant.setOfficePhone(phones[1]);
			applicant.setHomePhone(phones[2]);
			
			if (!row[24].isEmpty()) {
				academic.setLastDegree(row[24]);
			}
			academic.setSpecialityTitle(row[23]);
			academic.setDiscipline(tools.transformDisc(academic.getDiscipline() + " " + row[27] + " " + row[28] + " " + row[29]));
			
			if (department.getId() == null) {
				saveOrUpdate(department);
			}
			saveOrUpdate(applicant);
			saveOrUpdate(teacher);
			saveOrUpdate(academic);
			saveOrUpdate(ga);
			saveOrUpdate(gg);
			saveOrUpdate(gf);
			if (gm != null) {
				saveOrUpdate(gm);
			}
		}
		if (exceptionMsg.length() > 0) {
			throw new Exception(exceptionMsg.toString());
		}
		
		finish();

		System.out.println(missingApplicationName);
	}


}
