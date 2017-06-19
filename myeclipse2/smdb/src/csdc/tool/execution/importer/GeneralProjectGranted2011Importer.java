package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.SystemOption;

/**
 * 导入2011年一般项目立项信息
 * 找不到的项目：中国民间对日索赔中的国际法问题研究—以重庆大轰炸受害者索赔为典型的实证分析 
 * 找不到的项目：清代官箴及其在清代地方司法中的影响——以清代四川为例 
 * 找不到的项目：专利添附制度研究——以中国高铁技术创新与发展为例 
 * 找不到的项目：基于行为经济学的会计舞弊形成机理及控制策略研究 
 * 找不到的项目：中国东北地区农作物秸秆利用模式及其效率研究 
 * 找不到的项目：基于协同控制的随机混流装配过程重构优化算法研究 
 * 找不到的项目：思想与社会转型期文学嬗变考--中日近代文学起源比较研究 
 * 找不到的项目：时空视野下人类流感疫情的全球性起源、传播与迁移（1918—2010） 
 * 找不到的项目：高等教育与区域经济共生发展实证研究——基于产业结构调整的视角 
 * 找不到的项目：明清地域商帮兴衰及借鉴研究——基于浙江三地商帮的比较 
 * 找不到的项目：“宋学”成就及其现代意义的史学观照——从陈寅恪、钱穆到余英时 
 * 找不到的项目：汉口钱业公会与地方社会(1919-1938) 
 * 找不到的项目：当代大学生政治参与现状及对策研究——以江浙部分高校为例 
 * 找不到的项目：经济景气消费函数模型及应用----基于参数、非参数的计量分析 
 * 找不到的项目：玛丽-劳尔瑞安(Marie-LaureRyan)叙事理论研究 
 * 找不到的项目：戏剧及影视艺术视角下的哈罗德品特研究 
 * 找不到的项目：中英文化交流语境中的阿瑟韦利（ArthurWaley）研究 
 * 找不到的项目：元末至民初汉语口语句式研究——以古代朝鲜、琉球、日本系列汉语会话教科书为中心 
 * 找不到的项目：中国古典文学四大名著汉法平行语料库的创建及应用研究 
 * 找不到的项目：翻译对现代汉语发展的影响--基于历时类比语料库（1900-1949)的研究 
 * 
 * 以上项目全是由《2011年一般项目申报一览表_修正导入.xls》中项目名称有多余空格所致，
 * 已将该xls中的这些项目名称改为《2011年一般项目立项数据（含立项编号）_修正导入.xls》的名称，并把行底色设为黄色
 *
 * @author xuhan
 */
@SuppressWarnings("unchecked")
public class GeneralProjectGranted2011Importer extends Importer {
	
	/**
	 * 项目子类名称到项目子类实体的映射
	 */
	private Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();

	/**
	 * 项目名称+申报人 -> 申报实体 
	 */
	private Map<String, GeneralApplication> gaMap = new HashMap<String, GeneralApplication>();
	
	/**
	 * 项目名称+申报人 -> 立项实体 
	 */
	private Map<String, GeneralGranted> ggMap = new HashMap<String, GeneralGranted>();


	public GeneralProjectGranted2011Importer(File file) {
		super(file);
	}
	
	@Override
	public void work() throws Exception {
		init();
		importData();
	}

	private void importData() throws Exception {
		getContentFromExcel(0);
		StringBuffer exeptionMessage = new StringBuffer();
		
		while (next()) {
			String projectName = E;
			String applicantName = G;
			GeneralApplication ga = gaMap.get(projectName + applicantName);

			if (ga == null) {
				//申报数据找不到
				exeptionMessage.append(" * 找不到的项目：" + projectName + " \n");
				continue;
			}
			if (!ga.getAgencyName().equals(C)) {
				exeptionMessage.append(" * 所在学校和申报数据不一致：" + projectName + " \n");
			}
			if (!ga.getSubtype().getName().equals(D)) {
				exeptionMessage.append(" * 项目子类和申报数据不一致：" + projectName + " \n");
			}
			
			ga.setFinalAuditResult(2);
			ga.setFinalAuditStatus(3);

			GeneralGranted gg = ggMap.get(projectName + applicantName);
			if (gg == null) {
				gg = new GeneralGranted();
				ggMap.put(projectName + applicantName, gg);
			}
			gg.setNumber(F);
			gg.setName(projectName);
			gg.setApplication(ga);
			gg.setStatus(1);
			gg.setSubtype(ga.getSubtype());
			gg.setIsImported(1);

			saveOrUpdate(ga);
			saveOrUpdate(gg);
		}
		
		System.out.println(exeptionMessage);
	}

	
	private void init() {
		subTypeMap.put("青年基金项目", (SystemOption) session.createQuery("select so from SystemOption so where so.name = '青年基金项目'").list().get(0));
		subTypeMap.put("规划基金项目", (SystemOption) session.createQuery("select so from SystemOption so where so.name = '规划基金项目'").list().get(0));
		subTypeMap.put("自筹经费项目", (SystemOption) session.createQuery("select so from SystemOption so where so.name = '自筹经费项目'").list().get(0));

		List<GeneralApplication> list = session.createQuery("select ga from GeneralApplication ga where ga.year = 2011 ").list();
		Set<String> existingProjectNames = new HashSet<String>();
		for (GeneralApplication generalApplication : list) {
			//只有在当年唯一的[项目名+申报人名]才能作为寻找相应申报实体的依据
			String projectName = generalApplication.getName();
			String applicantName = generalApplication.getApplicantName();
			String key = projectName + applicantName;
			if (gaMap.containsKey(key)) {
				gaMap.remove(key);
			} else if (!existingProjectNames.contains(key)) {
				gaMap.put(key, generalApplication);
			}
			existingProjectNames.add(key);
		}
		
		List<GeneralGranted> ggs = session.createQuery("select gg from GeneralGranted gg left join fetch gg.application where gg.application.year = 2011").list();
		for (GeneralGranted gg : ggs) {
			ggMap.put(gg.getName() + gg.getApplication().getApplicantName(), gg);
		}
	}


}
