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
 * 导入2006年一般项目立项信息
 * @author xuhan
 * 
 * 找不到的项目：中国高等教育改革与发展中的重大法律问题研究 
 * 找不到的项目：中国教育技术学科发展的反思与重建 
 * 找不到的项目：高等学校毕业生就业影响因素及其就业竞争力提升策略研究 
 * 找不到的项目：高校在区域创新系统构建中的功能与贡献途径研究 
 * 找不到的项目：中小学教师对基础教育新课程的适应性研究 
 * 找不到的项目：西部地区新农村青少年媒介素养教育研究 
 * 找不到的项目：民国以来东北地区民间宗教问题研究 
 * 找不到的项目：新时期国家助学贷款－困境与走向 
 *
 */
@SuppressWarnings("unchecked")
public class GeneralProjectGranted2006Importer extends Importer {
	
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
	
	public GeneralProjectGranted2006Importer(File file) {
		super(file);
		
		subTypeMap.put("青年项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("规划项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("专项项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '专项任务项目'").get(0));

		List<GeneralApplication> list = baseService.query("select ga from GeneralApplication ga where ga.year = 2006 ");
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
			String projectName = row[2].replaceAll("[　\\s]+", "");
			String applicantName = row[7].replaceAll("[　\\s]+", "");
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			String univName = tools.getNewestUnivName(row[1]);
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
				ga.setDiscipline(tools.transformDisc(row[6]));
				ga.setSubtype(subTypeMap.get(row[3]));
				ga.setIsImported(1);
				ga.setName(projectName);
				ga.setAgencyName(univName);
				ga.setUniversity(university);
				ga.setDepartment(department);
				ga.setDivisionName(department.getName());
				ga.setApplicant(applicant);
				ga.setApplicantName(applicantName);
				ga.setYear(2006);

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
			ga.setPlanEndDate(tools.getDate(row[11]));
			ga.setProductType(row[12]);

			GeneralGranted gg = new GeneralGranted();
			gg.setName(projectName);
			gg.setIsImported(1);
			gg.setSubtype(ga.getSubtype());
			gg.setApplication(ga);
			gg.setApproveFee(tools.getFee(row[13]));
			gg.setNumber(row[5]);

			GeneralFunding gf = new GeneralFunding();
			gf.setGranted(gg);
			gf.setDate(new Date(2006 - 1900, 9 - 1, 1));
			gf.setFee(tools.getFee(row[14]));
			
			Date birthday = tools.getDate(row[8]);
			if (birthday != null) {
				applicant.setBirthday(birthday);
			}
			//处理电话
			String phones[] = tools.getPhones(row[10] + " " + applicant.getMobilePhone() + " " + applicant.getOfficePhone() + "(o) " + applicant.getHomePhone() + "(h)");
			applicant.setMobilePhone(phones[0]);
			applicant.setOfficePhone(phones[1]);
			applicant.setHomePhone(phones[2]);
			
			if (!row[9].isEmpty()) {
				academic.setLastDegree(row[9]);
			}
			
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
