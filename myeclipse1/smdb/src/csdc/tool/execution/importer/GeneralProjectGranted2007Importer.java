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
 * 导入2007年一般项目立项信息
 * @author xuhan
 * 
 * 找不到的项目：价值链节点企业的价值评估及其价值管理研究 
 * 找不到的项目：免疫优化理论及其在物流配送调度中的应用研究 
 * 找不到的项目：我国东中西部地区专利创新效率差异及其成因研究 
 * 找不到的项目：大学生就业流向区域差异、动因及对策研究 
 * 找不到的项目：购买力平价学说动态表述及其应用研究 
 * 找不到的项目：现代组合预测与综合评价的智能化算法研究 
 * 找不到的项目：反马克思主义思潮：新自由主义的意识形态分析 
 * 找不到的项目：新教伦理的后现代走向研究 
 * 找不到的项目：大学生创业教育模式研究 
 * 找不到的项目：全国高校英语专业高级英语写作测试的校度检修研究 
 * 找不到的项目：近代以来中国大学与政府关系研究 
 * 找不到的项目：中国大学文化百年研究 
 * 找不到的项目：大学文化生态与和谐校园建设 
 * 找不到的项目：师专发展战略研究 
 * 找不到的项目：FDI集中与企业家精神当地化研究 
 * 找不到的项目：晋、陕、蒙资源富集区农村基础设施民间资本介入与目标模式选择研究 
 * 找不到的项目：弱势农户融资中的信用风险度量与控制研究 
 * 找不到的项目：我国研究型大学发展规划研究 
 * 找不到的项目：清代火耗归公与养廉银制度 
 *
 */
@SuppressWarnings("unchecked")
public class GeneralProjectGranted2007Importer extends Importer {
	
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

	
	public GeneralProjectGranted2007Importer(File file) {
		super(file);
		
		subTypeMap.put("青年基金", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("规划基金", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("专项任务", (SystemOption) baseService.query("select so from SystemOption so where so.name = '专项任务项目'").get(0));

		List<GeneralApplication> list = baseService.query("select ga from GeneralApplication ga where ga.year = 2007 ");
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
			String applicantName = row[5].replaceAll("[　\\s]+", "");
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
				ga.setDiscipline("000/未知学科");
				ga.setSubtype(subTypeMap.get(row[3]));
				ga.setIsImported(1);
				ga.setName(projectName);
				ga.setAgencyName(univName);
				ga.setUniversity(university);
				ga.setDepartment(department);
				ga.setDivisionName(department.getName());
				ga.setApplicant(applicant);
				ga.setApplicantName(applicantName);
				ga.setYear(2007);

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
			ga.setProductType(row[6]);

			GeneralGranted gg = new GeneralGranted();
			gg.setName(projectName);
			gg.setIsImported(1);
			gg.setSubtype(ga.getSubtype());
			gg.setApplication(ga);
			gg.setApproveFee(tools.getFee(row[7]));
			gg.setNumber(row[4]);

			GeneralFunding gf = new GeneralFunding();
			gf.setGranted(gg);
			gf.setDate(new Date(2007 - 1900, 9 - 1, 1));
			gf.setFee(tools.getFee(row[8]));
			
			if (department.getId() == null) {
				saveOrUpdate(department);
			}
			saveOrUpdate(applicant);
			saveOrUpdate(teacher);
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
