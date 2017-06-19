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
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;

/**
 * 导入《2005年一般项目立项一览表_修正导入.xls》
 * @author xuhan
 * 新增立项：人文社会科学研究成果评价的理论与方法研究
 * 新增立项：研究型大学图书馆服务能力与用户满意度研究
 * 新增立项：儿童心理理论与欺骗发展的关系研究
 * 新增立项：高校人文社会科学研究能力评价研究
 * 新增立项：网络信息技术在科研基金项目管理中的应用研究
 * 
 * 无申报数据的项目：人文社会科学研究成果评价的理论与方法研究 
 * 无申报数据的项目：高校人文社会科学研究能力评价研究 
 * 无申报数据的项目：网络信息技术在科研基金项目管理中的应用研究 
 * 
 */
@SuppressWarnings("unchecked")
public class GeneralProjectGranted2005ImporterV2 extends Importer {
	
	/**
	 * 项目子类名称到项目子类实体的映射
	 */
	private Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();

	/**
	 * [项目名称]或[学校名+申请人姓名]到项目申报的映射
	 */
	private Map<String, GeneralApplication> gaMap = new HashMap<String, GeneralApplication>();
	
	/**
	 * 项目名称    -> 立项实体 
	 */
	private Map<String, GeneralGranted> ggMap = new HashMap<String, GeneralGranted>();

	/**
	 * 项目名称    -> 一期拨款实体 
	 */
	private Map<String, GeneralFunding> gfMap = new HashMap<String, GeneralFunding>();

	
	@SuppressWarnings("deprecation")
	public GeneralProjectGranted2005ImporterV2(File file) {
		super(file);
		
		subTypeMap.put("青年基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("规划基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("专项任务项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '专项任务项目'").get(0));
		
		List<GeneralApplication> list = session.createQuery("select ga from GeneralApplication ga where ga.year = 2005 ").list();
		Set<String> existingProjectNames = new HashSet<String>();
		for (GeneralApplication generalApplication : list) {
			//只有在当年唯一的项目名才能作为寻找相应申报实体的依据
			String projectName = generalApplication.getName();
			if (gaMap.containsKey(projectName)) {
				gaMap.remove(projectName);
			} else if (!existingProjectNames.contains(projectName)) {
				gaMap.put(projectName, generalApplication);
			}
			existingProjectNames.add(projectName);
			
			//在根据项目名称找不到时，可根据学校名+申报人姓名寻找
			gaMap.put(generalApplication.getAgencyName() + generalApplication.getApplicantName(), generalApplication);
		}
		
		List<GeneralGranted> ggs = session.createQuery("select gg from GeneralGranted gg left join fetch gg.application where gg.application.year = 2005").list();
		for (GeneralGranted gg : ggs) {
			ggMap.put(tools.toDBC(gg.getName()).replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""), gg);
		}

		List<GeneralFunding> gfs = session.createQuery("select gf from GeneralFunding gf left join fetch gf.granted where gf.granted.application.year = 2005").list();
		for (GeneralFunding gf : gfs) {
			if (gf.getDate().getYear() + 1900 == 2005) {
				gfMap.put(tools.toDBC(gf.getGranted().getName()).replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""), gf);
			}
		}

	}
	
	public void work() throws Exception {
		getContentFromExcel(0);
		StringBuffer exceptionMsg = new StringBuffer();
		StringBuffer missingApplicationName = new StringBuffer();
		
		while (next()) {
			System.out.println(curRowIndex);
			String projectName = D.replaceAll("[　\\s]+", "");
			String applicantName = F.replaceAll("[　\\s]+", "");
			String univName = tools.getNewestUnivName(B);
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			GeneralApplication ga = gaMap.get(projectName);
			GeneralMember gm = null;
			
			if (ga == null) {
				ga = gaMap.get(univName + applicantName);
			}
			if (ga == null) {
				//申报数据找不到，新建一个
				missingApplicationName.append(" * 无申报数据的项目：" + projectName + " \n");
				Agency university = tools.getUnivByName(univName);
				Teacher teacher = tools.getTeacher(applicantName, university);
				Person applicant = teacher.getPerson();
				Academic academic = tools.getAcademic(applicant);
				Department department = teacher.getDepartment();

				if (university == null) {
					exceptionMsg.append("学校 -- "+ univName + " 找不到\n");
					continue;
				}
				ga = new GeneralApplication();
				ga.setSubtype(subTypeMap.get(C));
				ga.setIsImported(1);
				ga.setName(projectName);
				ga.setAgencyName(univName);
				ga.setUniversity(university);
				ga.setDepartment(department);
				ga.setDivisionName(department.getName());
				ga.setApplicant(applicant);
				ga.setApplicantName(applicantName);
				ga.setYear(2005);

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

				if (department.getId() == null) {
					saveOrUpdate(department);
				}
				saveOrUpdate(applicant);
				saveOrUpdate(teacher);
			}
			if (exceptionMsg.length() > 0) {
				continue;
			}
			ga.setIsGranted(2);
			if (E.length() > 0) {
				ga.setDisciplineType(E);
				ga.setDiscipline(tools.transformDisc(ga.getDiscipline() + " " + E));
			}

			GeneralGranted gg = ggMap.get(tools.toDBC(projectName).replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""));
			if (gg == null) {
				System.out.println(" * 新增立项：" + projectName);
				gg = new GeneralGranted();
			}
			gg.setName(projectName);
			gg.setIsImported(1);
			gg.setSubtype(ga.getSubtype());
			gg.setApplication(ga);
			gg.setNumber(G);
			gg.setApproveFee(tools.getFee(H));

			GeneralFunding gf = gfMap.get(tools.toDBC(projectName).replaceAll("[^\\w\\u4e00-\\u9fa5]+", ""));
			if (gf == null) {
				gf = new GeneralFunding();
			}
			gf.setGranted(gg);
			gf.setDate(tools.getDate(2005, 9, 1));
			gf.setFee(tools.getFee(I));
			
			if (J.length() > 0) {
				ga.setProductType(I);
			}

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
