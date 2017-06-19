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
 * 导入2010年一般项目立项信息
 * @author xuhan
 * 
 */
@SuppressWarnings("unchecked")
public class GeneralProjectGranted2010Importer extends Importer {
	
	/**
	 * 项目子类名称到项目子类实体的映射
	 */
	private Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();

	/**
	 * 项目名称到项目申报的映射
	 */
	private Map<String, GeneralApplication> gaMap1 = new HashMap<String, GeneralApplication>();
	
	/**
	 * 学校名+申请人姓名 到 项目申报的映射
	 */
	private Map<String, GeneralApplication> gaMap2 = new HashMap<String, GeneralApplication>();
	
	/**
	 * 项目名称    -> 立项实体 
	 */
	private Map<String, GeneralGranted> ggMap = new HashMap<String, GeneralGranted>();


	public GeneralProjectGranted2010Importer(File file) {
		super(file);
		
		subTypeMap.put("青年基金项目", (SystemOption) session.createQuery("select so from SystemOption so where so.name = '青年基金项目'").list().get(0));
		subTypeMap.put("规划基金项目", (SystemOption) session.createQuery("select so from SystemOption so where so.name = '规划基金项目'").list().get(0));
		subTypeMap.put("自筹经费项目", (SystemOption) session.createQuery("select so from SystemOption so where so.name = '自筹经费项目'").list().get(0));

		List<GeneralApplication> list = session.createQuery("select ga from GeneralApplication ga where ga.year = 2010 ").list();
		Set<String> existingProjectNames = new HashSet<String>();
		for (GeneralApplication generalApplication : list) {
			//只有在当年唯一的项目名才能作为寻找相应申报实体的依据
			String projectName = generalApplication.getName();
			if (gaMap1.containsKey(projectName)) {
				gaMap1.remove(projectName);
			} else if (!existingProjectNames.contains(projectName)) {
				gaMap1.put(projectName, generalApplication);
			}
			existingProjectNames.add(projectName);
			
			//在根据项目名称找不到时，可根据学校名+申报人姓名寻找
			gaMap2.put(generalApplication.getAgencyName() + generalApplication.getApplicantName(), generalApplication);
		}
		
		List<GeneralGranted> ggs = session.createQuery("select gg from GeneralGranted gg left join fetch gg.application where gg.application.year = 2010").list();
		for (GeneralGranted gg : ggs) {
			ggMap.put(tools.toDBC(gg.getName()).replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""), gg);
		}

	}
	
	
	public void work() throws Throwable {
		getContentFromExcel(0);
		StringBuffer exceptionMsg = new StringBuffer();
		StringBuffer missingApplicationName = new StringBuffer();
		
		while (next()) {
			String projectName = F.replaceAll("[　\\s]+", "");
			String applicantName = H.replaceAll("[　\\s]+", "");
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			String univName = tools.getNewestUnivName(C);
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
				//申报数据找不到，新建一个
				missingApplicationName.append(" * 找不到的项目：" + projectName + " \n");

				ga = new GeneralApplication();
				ga.setDiscipline(tools.transformDisc(B));
				ga.setDisciplineType(B);
				ga.setSubtype(subTypeMap.get(D));
				ga.setIsImported(1);
				ga.setName(projectName);
				ga.setAgencyName(univName);
				ga.setUniversity(university);
				ga.setDepartment(department);
				ga.setDivisionName(department.getName());
				ga.setApplicant(applicant);
				ga.setApplicantName(applicantName);
				ga.setYear(2010);

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

			GeneralGranted gg = ggMap.get(projectName.replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""));
			if (gg == null) {
				gg = new GeneralGranted();
			}
			gg.setName(projectName);
			gg.setIsImported(1);
			gg.setSubtype(ga.getSubtype());
			gg.setApplication(ga);
			gg.setNumber(G);

			academic.setDiscipline(tools.transformDisc(academic.getDiscipline() + " " + B));
			
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
