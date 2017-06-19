package csdc.tool.execution;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.ProjectApplication;
import csdc.dao.HibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.tool.StringTool;

/**
 * 基地项目初审
 * @author maowh
 * @redmine
 * 初审规则：<br>
 * （1）在研项目查重：项目申请人应不具有在研的国家社科基金重大项目、教育部哲学社会科学研究重大课题攻关项目、基地重大项目、后期资助项目；
 * （2）撤项项目审核：项目申请人应不具有三年内撤项的教育部人文社会科学研究一般项目、专项任务项目、重大攻关项目、后期资助项目、基地项目；
 * （3）职称审核：项目申请者必须是全国普通高等学校具有正高级专业技术职务的在编在岗教师。
 */
public class FirstAuditInstpApplication2014 extends Execution{

	/**
	 * smdb的jdbcDao
	 */
	private JdbcDao jdbcDao;
	
	@Autowired
	HibernateBaseDao dao;
	
	/**
	 * 项目年度
	 */
	private Integer year;
	
	/**
	 * 高校名称 -> 高校代码
	 */
	private Map<String, String> univNameCodeMap;
	
	/**
	 * 高级、中级职称
	 */
	private List<String> 正高级 = new ArrayList<String>();
	
	{
		正高级.add("教授");
		正高级.add("研究员");
		正高级.add("研究馆员");
		正高级.add("编审");
		正高级.add("高级会计师");
		正高级.add("高级统计师");
		正高级.add("高级工程师");
		正高级.add("高级兽医师");
		正高级.add("高级实验师");
		正高级.add("高级经济师");
		正高级.add("主任医师");
		正高级.add("主任药师");
		正高级.add("主任护师");
	}
	
	/**
	 * 未找到高校代码的[负责人+高校]集合
	 */
	private List<String> notFindUniversityCodes = new ArrayList<String>();
	
	/**
	 * 国家社科重大项目：[高校代码+项目负责人] 到 [项目编号]的映射
	 */
	private Map<String, List<String>> nssfMap = null;
	
	/**
	 * 教育部人文社会科学研究项目：[高校代码+项目负责人] 到 [项目编号]的映射
	 * 重大攻关项目、基地项目、后期资助项目
	 */
	private Map<String, List<String>> keyMap = null;
	private Map<String, List<String>> instpMap = null;
	private Map<String, List<String>> postMap = null;

	
	protected void work() throws Throwable {
		firstAudit();
	}

	/**
	 * 项目初审
	 */
	private void firstAudit(){
		
		initMap();
		
		//当前年份
		year = (Integer) ActionContext.getContext().getSession().get("defaultYear");	
		//查出当前年的项目
		List<ProjectApplication> projects = dao.query(" from ProjectApplication ip where ip.type = 'instp' and ip.year = ?", year); 
		for (ProjectApplication project : projects) {
			//1、预处理：查重前先清空原有初审结果
			if (null != project.getFirstAuditDate()) {
				project.setFirstAuditResult(null);	//初审结果
				project.setFirstAuditDate(null);	//初审时间
			}
			
			String firstAuditResult = ""; //初审结果 
			//2、项目查重
			String[] keys = getKeys(project.getDirectorUniversity(), project.getDirector(), true);//[高校代码+项目负责人]
			for (String key : keys) {
				String result = checkDuplicate(key);
				if (result.isEmpty()) {
					continue;
				}
				firstAuditResult += (firstAuditResult.isEmpty()) ? result : "; " + result;//查重结果
			}
			
			//3、判断职称
			if(!isSpecialityTitleQualified(project)){
				firstAuditResult += (firstAuditResult.isEmpty()) ? "职称不符合申报条件" : "; " + "职称不符合申报条件";
			}
			
			
			//4、更新数据：reason不为空，表示存在未结项的项目
			if (!firstAuditResult.isEmpty()) {
				project.setFirstAuditResult(firstAuditResult);	//查重结果
				project.setFirstAuditDate(new Date());		//查重时间
			}
		}
		
		System.out.println("当前年申报的基地项目负责人所属高校未找到高校代码的共：" + notFindUniversityCodes.size() + "条");
		for (String directorUniv : notFindUniversityCodes) {
			System.out.println(directorUniv);
		}
	}
	
	/**
	 * 根据项目负责人所属学校和项目负责人获取每个负责人的[高校代码+项目负责人]组合
	 * @param directorUniversity	项目负责人所属学校
	 * @param director				项目负责人
	 * @return
	 */
	private String[] getKeys(String directorUniversity, String director, boolean needPrint) {
		String[] universities = directorUniversity.split("[\\s;,、；]+");
		String[] directors = director.split("[\\s;,、；]+");
		int univCnt = universities.length;
		int directorCnt = directors.length;
		//根据名称获取高校代码
		String[] univCodes = new String[directorCnt];
		for (int i = 0; i < univCnt; i++) {
			String code = univNameCodeMap.get(universities[i]);
			if (null == code) {
				if (needPrint) {
					notFindUniversityCodes.add(directors[i] + universities[i]);
				}
				continue; 
			}
			universities[i] = code;
			univCodes[i] = universities[i];
		}
		//补齐负责人所属学校代码数组
		for (int i = univCnt; i < directorCnt; i++) {
			univCodes[i] = universities[univCnt - 1];
		}
		//组合keys
		String[] keys = new String[directorCnt]; 
		for (int i = 0; i < directorCnt; i++) {
			keys[i] = univCodes[i] + StringTool.chineseCharacterFix(directors[i]);
		}
		
		return keys;
	}
	
	/**
	 * 初始化Map
	 */
	private void initMap() {
		long begin = System.currentTimeMillis();
		
		nssfMap = new HashMap<String, List<String>>();
		keyMap = new HashMap<String, List<String>>();
		instpMap = new HashMap<String, List<String>>();
		postMap = new HashMap<String, List<String>>();
		
		univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		
		//国家社科重大项目
		List<String[]> nssfs = jdbcDao.query("select c_applicant, c_applicant_new, c_university, c_number, c_name from T_NSSF where c_university is not null and C_IS_DUP_CHECK_INSTP = 1");
		for (String[] nssf : nssfs) {
			String[] keys = null;
//			String key = univNameCodeMap.get(nssf[1])+StringTool.fix(nssf[0]);
//			String value = (nssf[2] == null) ? nssf[3] : nssf[2] + "/" + nssf[3];
//			fillMap(key, value, nssfMap, 0);
			
			String value = (nssf[3] == null) ? nssf[4] : nssf[3] + "/" + nssf[4];
			if (nssf[1] != null) {
				keys = getKeys(nssf[2], nssf[1], false);//[高校代码+项目负责人]
			} else {
				keys = getKeys(nssf[2], nssf[0], false);//[高校代码+项目负责人]
			}
			
			for (String key : keys) {
				fillMap(key, value, nssfMap, 0);
			}
		}	

		//教育部人文社会科学研究项目（重大攻关、基地、后期资助）
		List<String[]> projects = jdbcDao.query("select ag.c_code, pm.C_MEMBER_NAME, p.c_number, p.c_name, p.c_type, p.c_status " +
				"from T_PROJECT_GRANTED p,T_PROJECT_MEMBER pm, T_AGENCY ag where pm.c_university_id = ag.c_id and pm.C_IS_DIRECTOR = 1 and pm.C_GROUP_NUMBER = p.C_MEMBER_GROUP_NUMBER and pm.C_APPLICATION_ID = p.C_APPLICATION_ID " +
				"and (p.c_type = 'key' or p.c_type = 'instp' or p.c_type = 'post') and p.C_IS_DUP_CHECK_INSTP = 1");
		for (String[] project : projects) {
			String key = project[0] + StringTool.chineseCharacterFix(project[1]);
			String value = (project[2] == null) ? project[3] : project[2] + "/" + project[3];
			int status = Integer.parseInt(project[5]);
			if ("key".endsWith(project[4])) {
				fillMap(key, value, keyMap, status);
			}else if ("instp".endsWith(project[4])) {
				fillMap(key, value, instpMap, status);
			}else if ("post".endsWith(project[4])) {
				fillMap(key, value, postMap, status);
			}
		}
		System.out.println("[NSSF] list大小：" + nssfs.size() + " map大小：" + (nssfMap.size()));
		System.out.println("[COMMON] list大小：" + projects.size() + " map大小：" + (keyMap.size() + instpMap.size() + postMap.size()));
		System.out.println("init Project complete! Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 根据不同类型项目填充各类项目map
	 * @param key		索引key
	 * @param value		key对应的值
	 * @param map		各类项目map
	 * @param status	项目状态[0默认，1在研，2已结项，3已中止，4已撤项]
	 */
	private void fillMap(String key, String value, Map<String, List<String>> map, int status){
		List<String> projects = map.get(key);
		if (null == projects) {
			projects = new ArrayList<String>();
			map.put(key, projects);
		}
//		if (status == 1) {
//			projects.add(value + "/在研");
//		}
//		if (status == 3) {
//			projects.add(value + "/中止");
//		} 
		if (status == 4) {
			projects.add(value + "/撤项");
		}else {
			projects.add(value);
		}
	}
	
	/**
	 * 根据key进行查重
	 * @param key	[高校代码+项目负责人]
	 * @param firstAuditResult	初审结果
	 * @return
	 */
	private String checkDuplicate(String key){
		String firstAuditResult = "";
		if (nssfMap.containsKey(key)) {
			String reason_nssf = "国家社科基金重大项目在研（" + StringTool.join(nssfMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_nssf : "; " + reason_nssf;
		}
		if (keyMap.containsKey(key)) {
			String reason_key = "重大攻关项目在研（" + StringTool.join(keyMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_key : "; " + reason_key;
		}
		if (instpMap.containsKey(key)) {
			String reason_instp = "基地项目在研（" + StringTool.join(instpMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_instp : "; " + reason_instp;
		}
		if (postMap.containsKey(key)) {
			String reason_post = "后期资助项目在研（" + StringTool.join(postMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_post : "; " + reason_post;
		}
		return firstAuditResult;
	}
	
	/**
	 * 检查职称是否符合项目申报资格：
	 * @param project			当前项目
	 * @param firstAuditResult	初审结果
	 * @return
	 */
	private boolean isSpecialityTitleQualified(ProjectApplication project){
		boolean isQualified = false;
		if (正高级.contains(project.getTitle().replaceAll("/", "").replaceAll("[0-9]", ""))) {
			isQualified = true;
		}
		return isQualified;
	}
	
	public FirstAuditInstpApplication2014() {
	}
	
	public FirstAuditInstpApplication2014(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
}
