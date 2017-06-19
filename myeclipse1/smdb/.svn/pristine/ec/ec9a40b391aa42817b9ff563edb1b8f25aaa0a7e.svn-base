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

import csdc.bean.GeneralApplication;
import csdc.bean.SpecialApplication;
import csdc.bean.SpecialMember;
import csdc.dao.HibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.dao.SystemOptionDao;
import csdc.tool.DatetimeTool;
import csdc.tool.StringTool;

/**
 * 一般项目初审
 * @author fengcl
 * @redmine
 *  初审规则：<br>
 * （1）职称与年龄审核：规划基金项目申请者，应为具有高级职称（含副高）的在编在岗教师；青年基金项目申请者，应为具有博士学位或中级以上（含中级）职称的在编在岗教师，年龄不超过40周岁；<br>
 * （2）在研项目查重：项目申请人应不具有在研的国家自然科学基金、国家社会科学基金（含教育学、艺术学单列）、教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目、发展报告项目；
 * （3）撤项项目审核：项目申请人应不具有三年内撤项的教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目；
 * （4）青年基金限制：已获得过青年基金项目资助的申请人（不管结项与否）不得再次申请青年基金项目，即申请人只能获得一次青年基金项目资助；
 * （5）申请项目限制：申请国家社科基金年度、青年、后期资助、西部和单列学科项目的负责人同年度不能申请教育部一般项目。
 * （6）普及读物限制：普及读物在研限制，即项目负责人不能具有在研的普及读物项目；
 * 
 *    备注：国自科和国社科项目爬虫结束后，需要根据unit设置university。
 * 
 * */
public class FirstAuditSpecialApplication extends Execution{

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
	
	String title = "";
	
	/**
	 * 高校名称 -> 高校代码
	 */
	private Map<String, String> univNameCodeMap;
	
	/**
	 * 为找到高校代码的[负责人+高校]集合
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
	 * 一般项目、重大攻关项目、基地项目、后期资助项目、专项任务项目、发展报告项目、教育部委托项目
	 */
	private Map<String, List<String>> generalCurrentMap = null;//一般项目在研
	private Map<String, List<String>> generalEndMap = null;//一般项目结项
	private Map<String, List<String>> generalStopMap = null;//一般项目中止
	private Map<String, List<String>> generalWithdrawMap = null;//一般项目撤项
	
	private Map<String, List<String>> keyMap = null;
	private Map<String, List<String>> instpMap = null;
	private Map<String, List<String>> postMap = null;
	private Map<String, List<String>> generalSpecialMap = null;
	private Map<String, List<String>> developmentReportMap = null;
	private Map<String, List<String>> entrustMap = null; //教育部委托项目
	
	/**
	 * 国社科申请项目：[高校代码+项目负责人] 到 [项目名称]的映射
	 */
	private Map<String, List<String>> nssfApplicationMap = null;
	
	/**
	 * 一般项目青年基金：[高校代码+项目负责人] 到 [项目编号]的映射
	 */
	private Map<String, List<String>> youthFoundMap = null;
	
	/**
	 * 普及读物：[高校代码+负责人] 到 [项目编号]的映射
	 */
	private Map<String, List<String>> popuBookMap = null;
	
	private Map<String, SpecialMember> specialMember = null;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	/**
	 * 高级、中级职称
	 */
	private List<String> 高级 = null;
	private List<String> 中级 = null;
	
	private String 规划 = null;
	private String 青年 = null;
	
	@SuppressWarnings("unchecked")
	public void initMember() {
		Date begin = new Date();
		specialMember = new HashMap<String, SpecialMember>();
		List<SpecialMember> specialMemberList = dao.query("select gm from SpecialMember gm left join gm.application app where gm.isDirector = 1 and app.year=2015");
		for(SpecialMember gm: specialMemberList) {
			specialMember.put(gm.getApplicationId() + gm.getMemberName(), gm);
		}
		System.out.println("init SpecialMember complete! use time " + (new Date().getTime() - begin.getTime()));
	}

	
	protected void work() throws Throwable {
		firstAudit();
	}

	/**
	 * 项目初审
	 */
	@SuppressWarnings("unchecked")
	private void firstAudit(){
		
		initMap();
		initMember();
		
		//当前年份2015年
		year = Integer.parseInt(DatetimeTool.getDatetimeString("yyyy"));
		Calendar calendar = new GregorianCalendar(year-40, 0, 1);//当前年之前40年的1月1日
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String limitDate = sdf.format(calendar.getTime());
		//初审当前年一般项目任务
		List<SpecialApplication> specialProjects = dao.query("select speApp from SpecialApplication speApp where speApp.year = ? " +
				" and ((speApp.provinceAuditStatus = 3 and speApp.provinceAuditResult = 2) " +
				" or ( speApp.provinceAuditStatus = 0 and speApp.provinceAuditResult = 0 and speApp.universityAuditStatus = 3 and speApp.universityAuditResult = 2 ))", year);
//		List<SpecialApplication> specialProjects = dao.query("select speApp from SpecialApplication speApp where speApp.year = ? ", year);
		int currentNum=0;
		int totalNum=specialProjects.size();
		for (SpecialApplication project : specialProjects) {
			System.out.println(++currentNum + "/" + totalNum);
			//1、预处理：查重前先清空原有初审结果
			if (null != project.getFirstAuditDate()) {
				project.setFirstAuditResult(null);	//初审结果
				project.setFirstAuditDate(null);	//初审时间
			}
			
			String firstAuditResult = ""; //初审结果 
			//2、项目查重
			/*if (null == project.getUniversityCode()) {
				notFindUniversityCodes.add(project.getDirector() + project.getUniversityName());
				continue;
			}*/
			SpecialMember director = specialMember.get(project.getId() + project.getApplicantName());
			if (null == project.getUniversity().getCode()) {
				notFindUniversityCodes.add(project.getApplicantName() + project.getUniversity().getName());
				continue;
			}
			//去掉姓名中的数字,字母等
			String key = project.getUniversity().getCode() + StringTool.chineseCharacterFix(project.getApplicantName());//[高校代码+项目负责人]
			firstAuditResult = checkDuplicate(key);//查重结果
			
			//3、判断职称、年龄等
			if (规划.equals(project.getSubtype().getId())) {
				//高级职称（含副高）的在编在岗教师
				if(!isSpecialityTitleQualified(project)){
					firstAuditResult += (firstAuditResult.isEmpty()) ? "职称不符合申请条件" : "; " + "职称不符合申请条件";
				}
			} else if (青年.equals(project.getSubtype().getId())) {
				//博士学位或中级以上（含中级）职称的在编在岗教师
				if(!isSpecialityTitleQualified(project)){
					firstAuditResult += (firstAuditResult.isEmpty()) ? "职称不符合申请条件" : "; " + "职称不符合申请条件";
				}
				//年龄不超过40周岁
				if(director.getBirthday() != null && !isAgeQualified(sdf.format(director.getBirthday()), limitDate)){
					firstAuditResult += (firstAuditResult.isEmpty()) ? "年龄不符合申请条件" : "; " + "年龄不符合申请条件";
				}
			}
			
			//4.判断一般项目申请青年基金的申请人是否已有立项的青年基金（暂时不做此项初审检查）
//					if (!isYouthFoundGranted(project).isEmpty()) {
//						firstAuditResult += (firstAuditResult.isEmpty()) ? isYouthFoundGranted(project): "; " + isYouthFoundGranted(project);
//					}			
			//5、更新数据：reason不为空，表示存在未结项的项目
			if (!firstAuditResult.isEmpty()) {
				project.setFirstAuditResult(firstAuditResult);	//查重结果
				project.setFirstAuditDate(new Date());		//查重时间
				dao.modify(project);
			}
		}

		System.out.println("当前年申请的专项任务项目负责人所属高校未找到高校代码的共：" + notFindUniversityCodes.size() + "条");
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
		entrustMap = new HashMap<String, List<String>>();
		nssfApplicationMap = new HashMap<String, List<String>>();
		
		popuBookMap = new HashMap<String, List<String>>();
		
		规划 = systemOptionDao.query("projectType", "0101").getId();
		青年 = systemOptionDao.query("projectType", "0102").getId();
		
		高级 = dao.query("select h.name from SystemOption h where h.standard='GBT8561-2001' and h.description='%高级'");
		中级 = dao.query("select h.name from SystemOption h where h.standard='GBT8561-2001' and h.description='中级'");
		高级.add("中学高级教师");
		高级.add("研究员");
		高级.add("副研究员");
		高级.add("研究馆员");
		高级.add("副研究馆员");
		中级.add("讲师（高校）");
		中级.add("讲师");
		中级.add("馆员");
		中级.add("会计师");
		中级.add("经济师");
		中级.add("助理研究员");
		中级.add("主管医师");
		
		if (univNameCodeMap == null) {
			initUnivNameCodeMap();
		}				
		//自科项目
		List<String[]> nsfcs = jdbcDao.query("select c_applicant, c_university, c_number, c_name from T_NSFC where c_university is not null and C_IS_DUP_CHECK_GENERAL = 1");
		for (String[] nsfc : nsfcs) {
			String key = univNameCodeMap.get(nsfc[1])+StringTool.fix(nsfc[0]);
			String value = (nsfc[2] == null) ? nsfc[3] : nsfc[2] + "/" + nsfc[3];
			List<String> projects = nsfcMap.get(key);
			if (null == projects) {
				projects = new ArrayList<String>();
				nsfcMap.put(key, projects);
			}
			projects.add(value);
		}
		
		//社科项目
		List<String[]> nssfs = jdbcDao.query("select c_applicant_new, c_university, c_number, c_name, C_SINGLE_SUBJECT, c_applicant from T_NSSF where c_university is not null and C_IS_DUP_CHECK_GENERAL = 1");
		for (String[] nssf : nssfs) {
			String singleSubject = nssf[3];//项目xxx单列
			String value = (nssf[2] == null) ? nssf[3] : nssf[2] + "/" + nssf[3];
			String directors = null;
			if (nssf[0] != null && !nssf[0].equals("")) {
				directors = nssf[0];
			} else {
				directors = nssf[5];
			}
			String[] keys = getKeys(nssf[1], directors, false);//[高校代码+项目负责人]
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
		
		//一般项目专项任务
		List<String[]> generalSpecials = jdbcDao.query("select c_applicant, c_unit, c_number, c_name from T_GENERAL_SPECIAL where c_unit is not null and C_IS_DUP_CHECK_GENERAL = 1");
		for (String[] generalSpecial : generalSpecials) {
			String value = (generalSpecial[2] == null) ? generalSpecial[3] : generalSpecial[2] + "/" + generalSpecial[3];
			String[] keys = getKeys(generalSpecial[1], generalSpecial[0], false);//[高校代码+项目负责人]
			for (String key : keys) {
				fillMap(key, value, generalSpecialMap, 0);
			}
		}
		
		//教育部人文社会科学研究项目（一般、重大攻关、基地、后期资助、委托）
		List<String[]> projects = jdbcDao.query("select ag.c_code, pm.C_MEMBER_NAME, p.c_number, p.c_name, p.c_type, p.c_status " +
				"from T_PROJECT_GRANTED p,T_PROJECT_MEMBER pm, T_AGENCY ag where pm.c_university_id = ag.c_id and pm.C_IS_DIRECTOR = 1 and pm.C_GROUP_NUMBER = p.C_MEMBER_GROUP_NUMBER and pm.C_APPLICATION_ID = p.C_APPLICATION_ID " +
				"and (p.c_type = 'general' or p.c_type = 'key' or p.c_type = 'instp' or p.c_type = 'post' or p.c_type = 'entrust' ) and p.C_IS_DUP_CHECK_GENERAL = 1");
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
			}else if ("entrust".endsWith(project[4])) {
				fillMap(key, value, entrustMap, status);
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
		List<String[]> drProjects = jdbcDao.query("select c_applicant, c_university, c_number, c_name from t_devrpt where c_university is not null and c_is_dup_check_general = 1");
		for (String[] drProject : drProjects) {
			String value = (drProject[2] == null) ? drProject[3] : drProject[2] + "/" + drProject[3];
			String[] keys = getKeys(drProject[1], drProject[0], false);//[高校代码+项目负责人]
			for (String key : keys) {
				fillMap(key, value, developmentReportMap, 0);
			}
		}
		
		//国社科项目申请数据
		List<String[]> nssfApplications = jdbcDao.query("select c_applicant, c_university, c_name from t_nssf_project_application where c_university is not null and c_is_dup_check_general = 1");
		for (String[] nssfApplication : nssfApplications) {
			String value = nssfApplication[2];
			String[] keys = getKeys(nssfApplication[1], nssfApplication[0], false);
			for (String key : keys) {
				fillMap(key, value, nssfApplicationMap, 0);
			}
		}
		
		//普及读物数据 popuBookMap
		List<String[]> popuBookProjects = jdbcDao.query("select c_applicant, c_university, c_number, c_name from t_popular_book where c_university is not null and c_is_dup_check_general = 1");
		for (String[] popuBookProject : popuBookProjects) {
			String value = (popuBookProject[2] == null) ? popuBookProject[3] : popuBookProject[2] + "/" + popuBookProject[3];
			String[] keys = getKeys(popuBookProject[1], popuBookProject[0], false);
			for (String key : keys) {
				fillMap(key, value, popuBookMap, 0);
			}
		}
				
		System.out.println("[NSFC] list大小：" + nsfcs.size() + " map大小：" + nsfcMap.size());
		System.out.println("[NSSF] list大小：" + nssfs.size() + " map大小：" + (nssfMap.size() + nssfEducationMap.size() + nssfArtMap.size()));
		System.out.println("[YouthFoundProject] list大小：" + youthFoundProjects.size() + " map大小：" + youthFoundMap.size() );
		System.out.println("[COMMON] list大小：" + (generalSpecials.size() + projects.size()) + " map大小：" + ( + generalSpecialMap.size() + keyMap.size() + instpMap.size() + postMap.size()));
		
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
		popuBookMap = null;
		entrustMap = null;
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
			String reason_developmentReport = "发展报告项目在研（" + StringTool.join(developmentReportMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_developmentReport : "; " + reason_developmentReport;
		}
		if (entrustMap.containsKey(key)) {
			String reason_entrust = "教育部委托项目在研（" + StringTool.join(entrustMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_entrust : "; " + reason_entrust;
		}
		//普及读物在研初审
		if (popuBookMap.containsKey(key)) {
			String reason_popuBook = "普及读物项目在研（" + StringTool.join(popuBookMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_popuBook : "; " + reason_popuBook;
		}
		if (nssfApplicationMap.containsKey(key)) {
			String reason_post = "国家社科基金项目申请（" + StringTool.join(nssfApplicationMap.get(key), "; ") + "）";
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
			keys[i] = univCodes[i] + StringTool.chineseCharacterFix(directors[i]);
		}
		
		return keys;
	}
	
	/**
	 * 检查职称是否符合项目申请资格：
	 * @param project			当前项目
	 * @param firstAuditResult	初审结果
	 * @return
	 */
	private boolean isSpecialityTitleQualified(SpecialApplication project){
		boolean isQualified = false;
		SpecialMember director = specialMember.get(project.getId() + project.getApplicantName());
		title = director.getSpecialistTitle();
		String degree = director.getMember().getAcademic().getLastDegree();
		if(title != null && title.contains("/")){
			title = title.split("/")[1];
		}
		if (规划.equals(project.getSubtype().getId()) && 高级.contains(title)) {
			isQualified = true;
		}else if (青年.equals(project.getSubtype().getId()) && (高级.contains(title) || 中级.contains(title) || "博士".equals(degree))) {
			isQualified = true;
		}
		return isQualified;
	}
	
	/**
	 * 检查年龄是否符合项目申请资格：年龄不超过40周岁
	 * @param birthday			负责人生日
	 * @param limitDate			年龄限制，比如1973年1月1日以后的
	 * @param firstAuditResult	初审结果
	 * @return
	 */
	private boolean isAgeQualified(String birthday, String limitDate){
		return (birthday.compareTo(limitDate) >= 0);
	}
	
	public FirstAuditSpecialApplication() {
	}
	
	public FirstAuditSpecialApplication(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	/**
	 * 判断一般项目申请青年基金的申请人是否已有立项的青年基金
	 * @return
	 */
	private String isYouthFoundGranted(GeneralApplication project){
		String firstAuditResult = "";
		if(青年.equals(project.getSubtype().getId())){
			String key = project.getUniversity().getCode() + StringTool.chineseCharacterFix(project.getApplicantName());//[高校代码+项目负责人]
			if(youthFoundMap.containsKey(key)){
				firstAuditResult = "一般项目青年基金已立项（" + StringTool.join(youthFoundMap.get(key), "; ") + "）";	
			}
		}
		return firstAuditResult;
	}
	
	private void initUnivNameCodeMap() {

		Date begin = new Date();
		
		univNameCodeMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select a.name, a.code from Agency a");
		for (Object[] o : list) {
			String univName = StringTool.fix((String) o[0]);
			String univCode = (String) o[1];
			
			univNameCodeMap.put(univName, univCode);
		}
		
		System.out.println("initUnivNameCodeMap complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	
		
	}
}
