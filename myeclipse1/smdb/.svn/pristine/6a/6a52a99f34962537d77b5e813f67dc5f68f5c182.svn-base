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
 * 导入2005年一般项目立项信息
 * @author xuhan
 * 
 * 找不到的项目：网络经济时代中间性组织的成长及运行机制研究 
 * 找不到的项目：中国上市公司担保行为问题研究 
 * 找不到的项目：现代经济学的语言与修辞：一个马克思主义经济学与西方经济学的综合比较视角 
 * 找不到的项目：东北老工业基地振兴中的外部资源利用及市场开拓研究 
 * 找不到的项目：科技型创业企业集群生成机制研究 
 * 找不到的项目：中国高等院校高级管理人员绩效测度研究 
 * 找不到的项目：课程标准与评价一致性研究 
 * 找不到的项目：新课程背景下中小学生活教育的理论研究与实践探索 
 * 找不到的项目：西北贫困地区少数民族社会情绪调查研究 
 * 找不到的项目：日伪统治下华北沦陷区的奴化教育 
 * 找不到的项目：英国十九世纪现实主义小说中人物的阶级意识与价值取向研究 
 * 找不到的项目：符号、身份与认同：来华留学之海外华裔青少年构成、特点与追求 
 * 找不到的项目：城市第一代独生子女的家庭结构对养老保障的影响 
 * 找不到的项目：两汉时期民族关系思想研究 
 * 找不到的项目：图书馆资源的公平利用：基本理论、现状与对策研究 
 * 找不到的项目：青少年的中华民族认同及其发展 
 * 找不到的项目：大学生自我价值定位及其与职业选择意向关系的研究 
 * 找不到的项目：小学生数学思维的认知诊断：规则空间模型 
 * 找不到的项目：教师发展的自我内在机制研究 
 * 找不到的项目：人文社会科学集刊调查研究 
 *
 */
@SuppressWarnings("unchecked")
public class GeneralProjectGranted2005Importer extends Importer {
	
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
	
	public GeneralProjectGranted2005Importer(File file) {
		super(file);
		
		subTypeMap.put("青年基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("规划基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("专项任务项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '专项任务项目'").get(0));
		
		List<GeneralApplication> list = baseService.query("select ga from GeneralApplication ga where ga.year = 2005 ");
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
			String applicantName = row[5].replaceAll("[　\\s]+", "");
			String univName = tools.getNewestUnivName(row[2]);
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			GeneralApplication ga = gaMap1.get(projectName);
			GeneralMember gm = null;
			
			if (ga == null) {
				ga = gaMap2.get(univName + applicantName);
			}
			if (ga == null) {
				//申请数据找不到，新建一个
				missingApplicationName.append("找不到的项目：" + projectName + " \n");
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
				ga.setDisciplineType(row[1]);
				ga.setDiscipline(tools.transformDisc(row[1]));
				ga.setSubtype(subTypeMap.get(row[3]));
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

			GeneralGranted gg = new GeneralGranted();
			gg.setName(projectName);
			gg.setIsImported(1);
			gg.setSubtype(ga.getSubtype());
			gg.setApplication(ga);
			gg.setApproveFee(tools.getFee(row[6]));

			GeneralFunding gf = new GeneralFunding();
			gf.setGranted(gg);
			gf.setDate(new Date(2005 - 1900, 9 - 1, 1));
			gf.setFee(tools.getFee(row[7]));

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
