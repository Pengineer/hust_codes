package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;

/**
 * 导入2009年一般项目立项信息
 * @author xuhan
 * 
 * 找不到的项目：中国大学人文社会科学科研组织模式创新研究——以教育部人文社会科学重点研究基地为例 
 * 找不到的项目：教育系统职务犯罪案例数据库应用研究 
 * 找不到的项目：中国近代地理学的发展研究——以中国地学会及《地学杂志》为研究对象 
 * 找不到的项目：新中国60年的历史选择 
 *
 */
@SuppressWarnings("unchecked")
public class GeneralProjectGranted2009Importer extends Importer {
	
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
	
	
	public GeneralProjectGranted2009Importer(File file) {
		super(file);
		
		subTypeMap.put("青年基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("规划基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("自筹经费项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '自筹经费项目'").get(0));

		List<GeneralApplication> list = baseService.query("select ga from GeneralApplication ga where ga.year = 2009 ");
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
	
	
	public void work() throws Exception {
		getContentFromExcel(0);
		StringBuffer exceptionMsg = new StringBuffer();
		StringBuffer missingApplicationName = new StringBuffer();
		
		while (next()) {
			String projectName = row[3].replaceAll("[　\\s]+", "");
			String applicantName = row[6].replaceAll("[　\\s]+", "");
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
				ga.setDiscipline(tools.transformDisc(row[5]));
				ga.setDisciplineType(row[5]);
				ga.setSubtype(subTypeMap.get(row[2]));
				ga.setIsImported(1);
				ga.setName(projectName);
				ga.setAgencyName(univName);
				ga.setUniversity(university);
				ga.setDepartment(department);
				ga.setDivisionName(department.getName());
				ga.setApplicant(applicant);
				ga.setApplicantName(applicantName);
				ga.setYear(2009);

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

			GeneralGranted gg = new GeneralGranted();
			gg.setName(projectName);
			gg.setIsImported(1);
			gg.setSubtype(ga.getSubtype());
			gg.setApplication(ga);
			gg.setApproveFee(tools.getFee(row[7]));
			gg.setNumber(row[4]);

			academic.setDiscipline(tools.transformDisc(academic.getDiscipline() + " " + row[5]));
			
			if (department.getId() == null) {
				saveOrUpdate(department);
			}
			saveOrUpdate(applicant);
			saveOrUpdate(teacher);
			saveOrUpdate(academic);
			saveOrUpdate(ga);
			saveOrUpdate(gg);
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
