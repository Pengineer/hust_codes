package csdc.tool.execution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.ProjectApplication;
import csdc.dao.HibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.tool.StringTool;
import csdc.tool.execution.Execution;

/**
 * 一般项目初审
 * @author maowh
 * @redmine
 * 初审规则：<br>
 * （1）职称与年龄审核：规划基金项目申请者，应为具有高级职称（含副高）的在编在岗教师；青年基金项目申请者，应为具有博士学位或中级以上（含中级）职称的在编在岗教师，年龄不超过40周岁；<br>
 * （2）在研项目查重：项目申请人应不具有在研的国家自然科学基金、国家社会科学基金（含教育学、艺术学单列）、教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目、发展报告项目；
 * （3）撤项项目审核：项目申请人应不具有三年内撤项的教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目；
 * （4）青年基金限制：已获得过青年基金项目资助的申请人（不管结项与否）不得再次申报青年基金项目，即申请人只能获得一次青年基金项目资助；
 * （5）申请项目限制：申请国家社科基金年度、青年、后期资助、西部和单列学科项目的负责人同年度不能申请教育部一般项目。
 */
public class FirstAuditGeneralApplication2014 extends Execution{

	/**
	 * smdb的jdbcDao
	 */
	private JdbcDao jdbcDao;
	
	private ArrayList<String> status = new ArrayList<String>();;
	
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
	private List<String> 高级 = new ArrayList<String>();
	private List<String> 中级 = new ArrayList<String>();
	
	{
		高级.add("教授");
		高级.add("副教授");
		高级.add("研究员");
		高级.add("副研究员");
		高级.add("研究馆员");
		高级.add("副研究馆员");
		高级.add("编审");
		高级.add("副编审");
		高级.add("高级会计师");
		高级.add("高级统计师");
		高级.add("高级工程师");
		高级.add("高级兽医师");
		高级.add("高级实验师");
		高级.add("高级经济师");
		高级.add("主任医师");
		高级.add("主任药师");
		高级.add("主任护师");
		高级.add("副主任医师");
		高级.add("副主任技师");
		高级.add("副主任护师");
		高级.add("副主任药师");
		
		中级.add("讲师");
		中级.add("编辑");
		中级.add("馆员");
		中级.add("会计师");
		中级.add("经济师");
		中级.add("统计师");
		中级.add("工程师");
		中级.add("实验师");
		中级.add("助理研究员");
		中级.add("主治医师");
		中级.add("主管医师");
		中级.add("主管护师");
		中级.add("主管药师");
		中级.add("主管技师");

	}
	
	/**
	 * 未找到高校代码的[负责人+高校]集合
	 */
	private List<String> notFindUniversityCodes = new ArrayList<String>();
	
	/**
	 * 国家自科项目：[高校代码+项目负责人] 到 [项目编号]的映射
	 */
	private Map<String, List<String>> nsfcMap = null;
	
	/**
	 * 国家社科项目：[高校代码+项目负责人] 到 [项目编号]的映射
	 */
	private Map<String, List<String>> nssfMap = null;
	
	/**
	 * 国家社科项目教育学单列：[高校代码+项目负责人] 到 [项目编号]的映射
	 */
	private Map<String, List<String>> nssfEducationMap = null;
	
	/**
	 * 国家社科项目艺术学单列：[高校代码+项目负责人] 到 [项目编号]的映射
	 */
	private Map<String, List<String>> nssfArtMap = null;
	
	/**
	 * 教育部人文社会科学研究项目：[高校代码+项目负责人] 到 [项目编号]的映射
	 * 一般项目、重大攻关项目、基地项目、后期资助项目、专项任务项目、发展报告项目
	 */
	private Map<String, List<String>> generalCurrentMap = null;//一般项目在研
	private Map<String, List<String>> generalEndMap = null;//一般项目结项
	private Map<String, List<String>> generalStopMap = null;//一般项目中止
	private Map<String, List<String>> generalWithdrawMap = null;//一般项目撤项
	private Map<String, List<String>> generalSpecialMap = null;
	private Map<String, List<String>> keyMap = null;
	private Map<String, List<String>> instpMap = null;
	private Map<String, List<String>> postMap = null;
	private Map<String, List<String>> developmentReportMap = null;
	private Map<String, List<String>> nssfApplicationMap = null;
	
	
	/**
	 * 一般项目青年基金：[高校代码+项目负责人] 到 [项目编号]的映射
	 */
	private Map<String, List<String>> youthFoundMap = null;

	
	protected void work() throws Throwable {
		firstAudit();
	}

	/**
	 * 项目初审
	 */
	private void firstAudit(){
		
		initMap();
		
		//当前年份
		year = 2014;
		Calendar calendar = new GregorianCalendar(year-40, 0, 1);//当前年之前40年的1月1日
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String limitDate = sdf.format(calendar.getTime());
		//初审当前年一般项目任务
		List<ProjectApplication> projects = dao.query(" from ProjectApplication gp where gp.type = 'general' and gp.year = ? and (gp.auditStatus = '主管部门审核通过' or gp.auditStatus = '学校审核通过')", year);
		for (ProjectApplication project : projects) {
			//1、预处理：查重前先清空原有初审结果
			if (null != project.getFirstAuditDate()) {
				project.setFirstAuditResult(null);	//初审结果
				project.setFirstAuditDate(null);	//初审时间
			}
			
			String firstAuditResult = ""; //初审结果 
			//2、项目查重
			if (null == project.getUniversityCode()) {
				notFindUniversityCodes.add(project.getDirector() + project.getUniversityName());
				continue;
			}
			
			//去掉姓名中的数字,字母等
			String key = project.getUniversityCode() + StringTool.chineseCharacterFix(project.getDirector());//[高校代码+项目负责人]
			firstAuditResult = checkDuplicate(key);//查重结果
			
			//3、判断职称、年龄等
			if ("规划基金项目".equals(project.getProjectType())) {
				//高级职称（含副高）的在编在岗教师
				if(!isSpecialityTitleQualified(project)){
					firstAuditResult += (firstAuditResult.isEmpty()) ? "职称不符合申报条件" : "; " + "职称不符合申报条件";
				}
			}else if ("青年基金项目".equals(project.getProjectType())) {
				//博士学位或中级以上（含中级）职称的在编在岗教师
				if(!isSpecialityTitleQualified(project)){
					firstAuditResult += (firstAuditResult.isEmpty()) ? "职称不符合申报条件" : "; " + "职称不符合申报条件";
				}
				//年龄不超过40周岁
				if(!isAgeQualified(sdf.format(project.getBirthday()), limitDate)){
					firstAuditResult += (firstAuditResult.isEmpty()) ? "年龄不符合申报条件" : "; " + "年龄不符合申报条件";
				}
			}
			
			//4.判断一般项目申请青年基金的申请人是否已有立项的青年基金（暂时不做此项初审检查）
//			if (!isYouthFoundGranted(project).isEmpty()) {
//				firstAuditResult += (firstAuditResult.isEmpty()) ? isYouthFoundGranted(project): "; " + isYouthFoundGranted(project);
//			}			
			//5、更新数据：reason不为空，表示存在未结项的项目
			if (!firstAuditResult.isEmpty()) {
				project.setFirstAuditResult(firstAuditResult);	//查重结果
				project.setFirstAuditDate(new Date());		//查重时间
			}
		}

		System.out.println("当前年申报的一般项目负责人所属高校未找到高校代码的共：" + notFindUniversityCodes.size() + "条");
		for (String directorUniv : notFindUniversityCodes) {
			System.out.println(directorUniv);
		}
		
		clearMap();
	}
	
	/**
	 * 初始化Map
	 */
	private void initMap() {
		long begin = System.currentTimeMillis();
		
		nsfcMap = new HashMap<String, List<String>>();
		nssfMap = new HashMap<String, List<String>>();
		nssfEducationMap = new HashMap<String, List<String>>();
		nssfArtMap = new HashMap<String, List<String>>();
//		generalMap = new HashMap<String, List<String>>();
		generalCurrentMap =  new HashMap<String, List<String>>();
		generalEndMap = new HashMap<String, List<String>>();
		generalStopMap = new HashMap<String, List<String>>();
		generalWithdrawMap = new HashMap<String, List<String>>();
		generalSpecialMap = new HashMap<String, List<String>>();
		keyMap = new HashMap<String, List<String>>();
		instpMap = new HashMap<String, List<String>>();
		postMap = new HashMap<String, List<String>>();		
		youthFoundMap = new HashMap<String, List<String>>();
		developmentReportMap = new HashMap<String, List<String>>();
		nssfApplicationMap = new HashMap<String, List<String>>();
		
		univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
				
		//自科项目
		List<String[]> nsfcs = jdbcDao.query("select c_applicant, c_university, c_number, c_name from T_NSFC where c_university is not null and C_IS_DUP_CHECK_GENERAL = 1");
		for (String[] nsfc : nsfcs) {
			String key = univNameCodeMap.get(nsfc[1])+StringTool.fix(nsfc[0]);//高校名加申请人
			String value = (nsfc[2] == null) ? nsfc[3] : nsfc[2] + "/" + nsfc[3];//项目编号加项目名称（项目编号为空时，项目名称加项目）
			List<String> projects = nsfcMap.get(key);
			if (null == projects) {
				projects = new ArrayList<String>();
				nsfcMap.put(key, projects);
			}
			projects.add(value);
		}
		
		//社科项目
		List<String[]> nssfs = jdbcDao.query("select c_applicant, c_applicant_new, c_university, c_number, c_name, C_SINGLE_SUBJECT from T_NSSF where c_university is not null and C_IS_DUP_CHECK_GENERAL = 1");
		for (String[] nssf : nssfs) {
			String[] keys = null;
			String singleSubject = nssf[5];//项目xxx单列
			String value = (nssf[3] == null) ? nssf[4] : nssf[3] + "/" + nssf[4];
			if (nssf[1] != null) {
				keys = getKeys(nssf[2], nssf[1], false);//[高校代码+项目负责人]
			} else {
				keys = getKeys(nssf[2], nssf[0], false);//[高校代码+项目负责人]
			}
			
			if ("教育学".equals(singleSubject)) {
				for (String key : keys) {
					fillMap(key, value, nssfEducationMap, 0);
				}
			}else if ("艺术学".equals(singleSubject)) {
				for (String key : keys) {
					fillMap(key, value, nssfArtMap, 0);
				}
			}else {
				for (String key : keys) {
					fillMap(key, value, nssfMap, 0);
				}
			}
		}	
		
		//专项任务项目
		List<String[]> generalSpecials = jdbcDao.query("select c_applicant, c_unit, c_number, c_name from T_GENERAL_SPECIAL where c_unit is not null and C_IS_DUP_CHECK_GENERAL = 1");
		for (String[] generalSpecial : generalSpecials) {
			String value = (generalSpecial[2] == null) ? generalSpecial[3] : generalSpecial[2] + "/" + generalSpecial[3];
			String[] keys = getKeys(generalSpecial[1], generalSpecial[0], false);//[高校代码+项目负责人]
			for (String key : keys) {
				fillMap(key, value, generalSpecialMap, 0);
			}
		}	
		
		//教育部人文社会科学研究项目（一般、重大攻关、基地、后期资助）
		List<String[]> projects = jdbcDao.query("select ag.c_code, pm.C_MEMBER_NAME, p.c_number, p.c_name, p.c_type, p.c_status " +
				"from T_PROJECT_GRANTED p,T_PROJECT_MEMBER pm, T_AGENCY ag where pm.c_university_id = ag.c_id and pm.C_IS_DIRECTOR = 1 and pm.C_GROUP_NUMBER = p.C_MEMBER_GROUP_NUMBER and pm.C_APPLICATION_ID = p.C_APPLICATION_ID " +
				"and (p.c_type = 'general' or p.c_type = 'key' or p.c_type = 'instp' or p.c_type = 'post') and p.C_IS_DUP_CHECK_GENERAL = 1");
		for (String[] project : projects) {
			String key = project[0] + StringTool.chineseCharacterFix(project[1]);
			String value = (project[2] == null) ? project[3] : project[2] + "/" + project[3];
			int status = Integer.parseInt(project[5]);
			if ("general".endsWith(project[4])) {
				if (status == 1) {
					fillMap(key, value, generalCurrentMap, status);
				} else if (status == 2) {
					fillMap(key, value, generalEndMap, status);
				} else if (status == 3) {
					fillMap(key, value, generalStopMap, status);
				} else if (status == 4) {
					fillMap(key, value, generalWithdrawMap, status);
				}							
			}else if ("key".endsWith(project[4])) {
				fillMap(key, value, keyMap, status);
			}else if ("instp".endsWith(project[4])) {
				fillMap(key, value, instpMap, status);
			}else if ("post".endsWith(project[4])) {
				fillMap(key, value, postMap, status);
			}
		}
				
		//教育部人文社会科学青年基金研究项目
		List<String[]> youthFoundProjects = jdbcDao.query("select ag.c_code, pm.C_MEMBER_NAME, p.c_number, p.c_name " +
				"from T_PROJECT_GRANTED p,T_PROJECT_MEMBER pm, T_AGENCY ag,T_SYSTEM_OPTION so where pm.c_university_id = ag.c_id and pm.C_IS_DIRECTOR = 1 and pm.C_GROUP_NUMBER = p.C_MEMBER_GROUP_NUMBER and pm.C_APPLICATION_ID = p.C_APPLICATION_ID and p.C_SUBTYPE_ID=so.c_id " +
				"and p.c_type = 'general' and so.c_name='青年基金项目' and p.C_STATUS !=4 ");
		for (String[] project : youthFoundProjects) {
			String key = project[0] + StringTool.chineseCharacterFix(project[1]);
			String value = (project[2] == null) ? project[3] : project[2] + "/" + project[3];
			fillMap(key, value, youthFoundMap, 1);
		}
		
		//教育部发展报告项目
		List<String[]> drProjects = jdbcDao.query("select c_applicant, c_university, c_number, c_name from t_development_report where c_university is not null and c_is_dup_check_general = 1");
		for (String[] drProject : drProjects) {
			String value = (drProject[2] == null) ? drProject[3] : drProject[2] + "/" + drProject[3];
			String[] keys = getKeys(drProject[1], drProject[0], false);//[高校代码+项目负责人]
			for (String key : keys) {
				fillMap(key, value, developmentReportMap, 0);
			}
		}
		
		//国社科项目申报数据
		List<String[]> nssfApplications = jdbcDao.query("select c_applicant, c_university, c_name from t_nssf_project_application where c_university is not null and c_is_dup_check_general = 1");
		for (String[] nssfApplication : nssfApplications) {
			String value = nssfApplication[2];
			String[] keys = getKeys(nssfApplication[1], nssfApplication[0], false);
			for (String key : keys) {
				fillMap(key, value, nssfApplicationMap, 0);
			}
		}
		
		
//		List<String[]> projects = jdbcDao.query("select p.C_APPLICANT_ID, p.c_number, p.c_name, p.c_type, p.c_status " +
//				"from T_PROJECT_GRANTED p where (p.c_type = 'general' or p.c_type = 'key' or p.c_type = 'instp' or p.c_type = 'post') and p.C_IS_DUP_CHECK_GENERAL = 1");
//		
//		for (String[] project : projects) {
//			String value = (project[1] == null) ? project[2] : project[1] + "/" + project[2];
//			int status = Integer.parseInt(project[4]);
//			String[] applicantIds = project[0].split("; ");
//			for (String applicantId : applicantIds) {
//				List<String[]> list = jdbcDao.query("select ag.C_CODE, pm.C_MEMBER_NAME from T_PROJECT_MEMBER pm, T_AGENCY ag where pm.C_UNIVERSITY_ID = ag.C_ID and pm.C_MEMBER_ID = '"+ applicantId +"'");
//				if (list.size()<=0) {
//					System.out.println(applicantId);
//					continue;
//				}
//				String[] applicantInfo = list.get(0);
//				
//				String key = applicantInfo[0] + StringTool.fix(applicantInfo[1]).replaceAll("[0-9]+", "");
//				if ("general".endsWith(project[3])) {
//					fillMap(key, value, generalMap, status);
//				}else if ("key".endsWith(project[3])) {
//					fillMap(key, value, keyMap, status);
//				}else if ("instp".endsWith(project[3])) {
//					fillMap(key, value, instpMap, status);
//				}else if ("post".endsWith(project[3])) {
//					fillMap(key, value, postMap, status);
//				}
//			}
//		}
		
		System.out.println("[NSFC] list大小：" + nsfcs.size() + " map大小：" + nsfcMap.size());
		System.out.println("[NSSF] list大小：" + nssfs.size() + " map大小：" + (nssfMap.size() + nssfEducationMap.size() + nssfArtMap.size()));
		System.out.println("[YouthFoundProject] list大小：" + youthFoundProjects.size() + " map大小：" + youthFoundMap.size() );
		System.out.println("[COMMON] list大小：" + (generalSpecials.size() + projects.size()) + " map大小：" + (generalCurrentMap.size() + generalEndMap.size() + generalStopMap.size() + generalWithdrawMap.size() + generalSpecialMap.size() + keyMap.size() + instpMap.size() + postMap.size()));
		System.out.println("init Project complete! Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 清除Map
	 */
	private void clearMap() {
		nsfcMap = null;
		nssfMap = null;
		nssfEducationMap = null;
		nssfArtMap = null;
//		generalMap = null;
		generalCurrentMap = null;
		generalEndMap = null;
		generalStopMap = null;
		generalWithdrawMap = null;
		generalSpecialMap = null;
		keyMap = null;
		instpMap = null;
		postMap = null;
		youthFoundMap = null;
		developmentReportMap = null;
		nssfApplicationMap = null;
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
		projects.add(value);	
	}
	
	
	/**
	 * 根据key进行查重
	 * @param key	[高校代码+项目负责人]
	 * @param firstAuditResult	初审结果
	 * @return
	 */
	private String checkDuplicate(String key){
		String firstAuditResult = "";
		if (nsfcMap.containsKey(key)) {
			String reason_nsfc = "国家自科基金项目在研（" + StringTool.join(nsfcMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_nsfc : "; " + reason_nsfc;
		}
		if (nssfMap.containsKey(key)) {
			String reason_nssf = "国家社科基金项目在研（" + StringTool.join(nssfMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_nssf : "; " + reason_nssf;
		}
		if (nssfEducationMap.containsKey(key)) {
			String reason_nssf = "国家社科基金项目教育学单列在研（" + StringTool.join(nssfEducationMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_nssf : "; " + reason_nssf;
		}
		if (nssfArtMap.containsKey(key)) {
			String reason_nssf = "国家社科基金项目艺术学单列在研（" + StringTool.join(nssfArtMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_nssf : "; " + reason_nssf;
		}
		if (generalCurrentMap.containsKey(key)) {
			String reason_general = "一般项目在研（" + StringTool.join(generalCurrentMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_general : "; " + reason_general;
		}
		if (generalEndMap.containsKey(key)) {
			String reason_general = "一般项目结项（" + StringTool.join(generalEndMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_general : "; " + reason_general;
		}
		if (generalStopMap.containsKey(key)) {
			String reason_general = "一般项目中止（" + StringTool.join(generalStopMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_general : "; " + reason_general;
		}
		if (generalWithdrawMap.containsKey(key)) {
			String reason_general = "一般项目撤项（" + StringTool.join(generalWithdrawMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_general : "; " + reason_general;
		}
		if (generalSpecialMap.containsKey(key)) {
			String reason_general = "专项任务项目在研（" + StringTool.join(generalSpecialMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_general : "; " + reason_general;
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
		if (developmentReportMap.containsKey(key)) {
			String reason_post = "发展报告项目在研（" + StringTool.join(developmentReportMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_post : "; " + reason_post;
		}
		if (nssfApplicationMap.containsKey(key)) {
			String reason_post = "国家社科基金项目申报（" + StringTool.join(nssfApplicationMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_post : "; " + reason_post; 
		}
		return firstAuditResult;
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
			keys[i] = univCodes[i] + StringTool.chineseCharacterFix(directors[i]);;
		}
		
		return keys;
	}
	
	/**
	 * 检查职称是否符合项目申报资格：
	 * @param project			当前项目
	 * @param firstAuditResult	初审结果
	 * @return
	 */
	private boolean isSpecialityTitleQualified(ProjectApplication project){
		boolean isQualified = false;
		if ("规划基金项目".equals(project.getProjectType()) && 高级.contains(project.getTitle())) {
			isQualified = true;
		}else if ("青年基金项目".equals(project.getProjectType()) && (高级.contains(project.getTitle()) || 中级.contains(project.getTitle()) || "博士".equals(project.getDegree()))) {
			isQualified = true;
		}
		return isQualified;
	}
	
	/**
	 * 检查年龄是否符合项目申报资格：年龄不超过40周岁
	 * @param birthday			负责人生日
	 * @param limitDate			年龄限制，比如1973年1月1日以后的
	 * @param firstAuditResult	初审结果
	 * @return
	 */
	private boolean isAgeQualified(String birthday, String limitDate){
		return (birthday.compareTo(limitDate) >= 0);
	}
	
	public FirstAuditGeneralApplication2014() {
	}
	
	public FirstAuditGeneralApplication2014(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	/**
	 * 判断一般项目申请青年基金的申请人是否已有立项的青年基金
	 * @return
	 */
	private String isYouthFoundGranted(ProjectApplication project){
		String firstAuditResult = "";		
		if("青年基金项目".equals(project.getProjectType())){
			String key = project.getUniversityCode() + StringTool.chineseCharacterFix(project.getDirector());//[高校代码+项目负责人]
			if(youthFoundMap.containsKey(key)){
				firstAuditResult = "一般项目青年基金已立项（" + StringTool.join(youthFoundMap.get(key), "; ") + "）";	
			}
		}
		return firstAuditResult;
	}
}
