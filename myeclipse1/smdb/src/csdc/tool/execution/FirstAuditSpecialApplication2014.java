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

import csdc.bean.SinossProjectApplication;
import csdc.dao.HibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 一般项目专项任务初审
 * @author maowh
 * @redmine
 * 初审规则：<br>
 * （1）在研项目查重：项目申请人应不具有在研的国家自然科学基金、国家社会科学基金（含教育学、艺术学单列）、教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目、发展报告项目；
 * （2）撤项项目审核：项目申请人应不具有三年内撤项的教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目；
 * （3）申请项目限制：申请国家社科基金年度、青年、后期资助、西部和单列学科项目、教育部人文社会科学研究一般项目的负责人同年度不能申请教育部一般项目；
 * （4）职称审核：申请者应为上述高校具有副高级以上（含副高）专业技术职务的在编在岗教师（仅针对工程科技人才培养研究专项、科研诚信和学风建设专项）。
 */
public class FirstAuditSpecialApplication2014 extends Execution{

	
	private ArrayList<String> status = new ArrayList<String>();;
	
	@Autowired
	HibernateBaseDao dao;
	
	
	
	/**
	 * 项目年度
	 */
	private String year;
	
	/**
	 * 高校名称 -> 高校代码
	 */
	private Map<String, String> univNameCodeMap;
	
	/**
	 * 高级、中级职称
	 */
	private List<String> 高级 = new ArrayList<String>();
	
	{
		高级.add("1A");
		高级.add("2A");
		高级.add("1BA");
		高级.add("2BB");
		高级.add("2BC");
		高级.add("2BD");
		高级.add("1D");
		高级.add("2D");
		高级.add("1E");
		高级.add("1B");
		高级.add("2BE");
		高级.add("2BF");
		高级.add("2BG");
		高级.add("1F");
		高级.add("1H");
		高级.add("1I");
		高级.add("1J");
		高级.add("2J");
		高级.add("1L");
		高级.add("2L");
		高级.add("1K");
		高级.add("2K");
		高级.add("1BJ");
		高级.add("2BJ");
		高级.add("1M");
		高级.add("2M");
		高级.add("1BK");
		高级.add("2BK");
		高级.add("1O");
		高级.add("2O");
		高级.add("1BL");
		高级.add("2BL");
		高级.add("1BM");
		高级.add("2BM");
		高级.add("1BN");
		高级.add("2BN");
		高级.add("2BO");
		高级.add("1BP");
		高级.add("2BP");
		高级.add("1BQ");
		高级.add("2BQ");
		高级.add("1BR");
		高级.add("2BR");
		高级.add("1BS");
		高级.add("2BS");
		高级.add("1BT");
		高级.add("2BT");
		高级.add("1BU");
		高级.add("2BU");
		高级.add("1BV");
		高级.add("2BV");
		高级.add("1BW");
		高级.add("2BW");
		高级.add("2BX");
		高级.add("2N");
		高级.add("2BY");
		高级.add("1G");
		高级.add("2G");
		高级.add("1P");
		高级.add("2P");
		高级.add("2U");
		高级.add("2W");
		高级.add("2X");
		高级.add("2Y");
		高级.add("2Z");
		高级.add("2AB");
		高级.add("2AC");
		高级.add("2AD");
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
	private Map<String, List<String>> generalApplicationMap = null;//2014年一般项目的申请
	
	
	/**
	 * 一般项目青年基金：[高校代码+项目负责人] 到 [项目编号]的映射
	 */
	private Map<String, List<String>> youthFoundMap = null;
	
	/**
	 * 2014年教育部一般项目
	 */

	
	protected void work() throws Throwable {
		firstAudit();
	}

	/**
	 * 项目初审
	 */
	private void firstAudit(){
		
		initMap();
		
		//当前年份
		year = "2014";
		Calendar calendar = new GregorianCalendar(Integer.parseInt(year)-40, 0, 1);//当前年之前40年的1月1日
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String limitDate = sdf.format(calendar.getTime());
		//初审当前年一般项目专项任务
		List<SinossProjectApplication> projects = dao.query(" from SinossProjectApplication spa where spa.typeCode = 'special' and spa.year = ? and (spa.checkStatus = '2' or spa.checkStatus = '4')", year); 
		for (SinossProjectApplication project : projects) {
			//1、预处理：查重前先清空原有初审结果
			if (null != project.getFirstAuditDate()) {
				project.setFirstAuditResult(null);	//初审结果
				project.setFirstAuditDate(null);	//初审时间
			}
			
			String firstAuditResult = ""; //初审结果 
			//2、项目查重
			if (null == project.getUnitCode()) {
				notFindUniversityCodes.add(project.getApplyer() + project.getUnitName());
				continue;
			}
			
			//去掉姓名中的数字,字母等
			String key = project.getUnitCode() + StringTool.chineseCharacterFix(project.getApplyer());//[高校代码+项目负责人]
			firstAuditResult = checkDuplicate(key);//查重结果
			
			//判断职称（工程科技人才培养研究专项、科研诚信和学风建设专项）
			if (project.getProjectType() == "402882f34292f75401429339a4890001" || project.getProjectType() == "402882f34292f7540142933a8f0c0005" ) {
				if(!isSpecialityTitleQualified(project)){
					firstAuditResult += (firstAuditResult.isEmpty()) ? "职称不符合申请条件" : "; " + "职称不符合申请条件";
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

		System.out.println("当前年申请的一般项目专项任务负责人所属高校未找到高校代码的共：" + notFindUniversityCodes.size() + "条");
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
		generalCurrentMap =  new HashMap<String, List<String>>();//一般项目在研
		generalEndMap = new HashMap<String, List<String>>();//一般项目结项
		generalStopMap = new HashMap<String, List<String>>();//一般项目中止
		generalWithdrawMap = new HashMap<String, List<String>>();//一般项目撤项
		generalSpecialMap = new HashMap<String, List<String>>();
		keyMap = new HashMap<String, List<String>>();
		instpMap = new HashMap<String, List<String>>();
		postMap = new HashMap<String, List<String>>();		
		youthFoundMap = new HashMap<String, List<String>>();
		developmentReportMap = new HashMap<String, List<String>>();
		nssfApplicationMap = new HashMap<String, List<String>>();
		generalApplicationMap = new HashMap<String, List<String>>();
		
		if (univNameCodeMap == null) {
			initUnivNameCodeMap();
		}
				
		//自科项目
		List<Object[]> nsfcs = dao.query("select nsfc.applicant, nsfc.university, nsfc.number, nsfc.name from Nsfc nsfc where nsfc.university is not null and nsfc.isDupCheckGeneral = 1");
		for (Object[] nsfc : nsfcs) {
			String key = univNameCodeMap.get((String)nsfc[1])+StringTool.fix((String)nsfc[0]);//高校名加申请人
			String value = ((String)nsfc[2] == null) ? (String)nsfc[3] : (String)nsfc[2] + "/" + (String)nsfc[3];//项目编号加项目名称（项目编号为空时，项目名称加项目）
			List<String> projects = nsfcMap.get(key);
			if (null == projects) {
				projects = new ArrayList<String>();
				nsfcMap.put(key, projects);
			}
			projects.add(value);
		}
		
		//社科项目
		List<Object[]> nssfs = dao.query("select nssf.applicant, nssf.applicantNew, nssf.university, nssf.number, nssf.name, nssf.singleSubject from Nssf nssf where nssf.university is not null and nssf.isDupCheckGeneral = 1");
		for (Object[] nssf : nssfs) {
			String[] keys = null;
			String singleSubject = (String)nssf[5];//项目xxx单列
			String value = ((String)nssf[3] == null) ? (String)nssf[4] : (String)nssf[3] + "/" + (String)nssf[4];
			if ((String)nssf[1] != null) {
				keys = getKeys((String)nssf[2], (String)nssf[1], false);//[高校代码+项目负责人]
			} else {
				keys = getKeys((String)nssf[2], (String)nssf[0], false);//[高校代码+项目负责人]
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
		
		//一般项目专项任务
		List<Object[]> generalSpecials = dao.query("select gs.applicant, gs.unit, gs.number, gs.name from GeneralSpecial gs where gs.unit is not null and gs.isDupCheckGeneral = 1");
		for (Object[] generalSpecial : generalSpecials) {
			String value = ((String)generalSpecial[2] == null) ? (String)generalSpecial[3] : (String)generalSpecial[2] + "/" + (String)generalSpecial[3];
			String[] keys = getKeys((String)generalSpecial[1], (String)generalSpecial[0], false);//[高校代码+项目负责人]
			for (String key : keys) {
				fillMap(key, value, generalSpecialMap, 0);
			}
		}	
		
		//教育部人文社会科学研究项目（一般、重大攻关、基地、后期资助）
		List<Object[]> generalProjects = dao.query("select ag.code, pm.memberName, gg.number, gg.name, gg.status " +
				"from GeneralGranted gg,ProjectMember pm left join pm.university ag" +
				" where pm.isDirector = 1 and pm.groupNumber = gg.memberGroupNumber and pm.applicationId = gg.applicationId and gg.isDupCheckGeneral = 1");
		List<Object[]> keyProjects = dao.query("select ag.code, pm.memberName, kg.number, kg.name, kg.status " +
				"from KeyGranted kg,ProjectMember pm left join pm.university ag" +
				" where pm.isDirector = 1 and pm.groupNumber = kg.memberGroupNumber and pm.applicationId = kg.applicationId and kg.isDupCheckGeneral = 1");
		List<Object[]> instpProjects = dao.query("select ag.code, pm.memberName, ig.number, ig.name, ig.status " +
				"from InstpGranted ig,ProjectMember pm left join pm.university ag" +
				" where pm.isDirector = 1 and pm.groupNumber = ig.memberGroupNumber and pm.applicationId = ig.applicationId and ig.isDupCheckGeneral = 1");
		List<Object[]> postProjects = dao.query("select ag.code, pm.memberName, pg.number, pg.name, pg.status " +
				"from PostGranted pg,ProjectMember pm left join pm.university ag" +
				" where pm.isDirector = 1 and pm.groupNumber = pg.memberGroupNumber and pm.applicationId = pg.applicationId and pg.isDupCheckGeneral = 1");
		for (Object[] generalProject : generalProjects) {
			String key = (String)generalProject[0] + StringTool.chineseCharacterFix((String)generalProject[1]);
			String value = ((String)generalProject[2] == null) ? (String)generalProject[3] : (String)generalProject[2] + "/" + (String)generalProject[3];
			int status = (Integer) generalProject[4];
			if (status == 1) {
				fillMap(key, value, generalCurrentMap, status);
			} else if (status == 2) {
				fillMap(key, value, generalEndMap, status);
			} else if (status == 3) {
				fillMap(key, value, generalStopMap, status);
			} else if (status == 4) {
				fillMap(key, value, generalWithdrawMap, status);
			}							
		}
		for (Object[] keyProject : keyProjects) {
			String key = (String)keyProject[0] + StringTool.chineseCharacterFix((String)keyProject[1]);
			String value = ((String)keyProject[2] == null) ? (String)keyProject[3] : (String)keyProject[2] + "/" + (String)keyProject[3];
			int status = (Integer) keyProject[4];
			fillMap(key, value, keyMap, status);
		}
		for (Object[] instpProject : instpProjects) {
			String key = (String)instpProject[0] + StringTool.chineseCharacterFix((String)instpProject[1]);
			String value = ((String)instpProject[2] == null) ? (String)instpProject[3] : (String)instpProject[2] + "/" + (String)instpProject[3];
			int status = (Integer) instpProject[4];
			fillMap(key, value, instpMap, status);
		}
		for (Object[] postProject : postProjects) {
			String key = (String)postProject[0] + StringTool.chineseCharacterFix((String)postProject[1]);
			String value = ((String)postProject[2] == null) ? (String)postProject[3] : (String)postProject[2] + "/" + (String)postProject[3];
			int status = (Integer) postProject[4];
			fillMap(key, value, postMap, status);
		}
		
				
		//教育部人文社会科学青年基金研究项目
		List<Object[]> youthFoundProjects = dao.query("select ag.code, pm.memberName, p.number, p.name" + 
		" from GeneralGranted p left join p.subtype so, ProjectMember pm left join pm.university ag" +
		" where pm.isDirector = 1 and pm.groupNumber = p.memberGroupNumber and pm.applicationId = p.applicationId  and so.name='青年基金项目' and p.status !=4");
		for (Object[] project : youthFoundProjects) {
			String key = (String)project[0] + StringTool.chineseCharacterFix((String)project[1]);
			String value = ((String)project[2] == null) ? (String)project[3] : (String)project[2] + "/" + (String)project[3];
			fillMap(key, value, youthFoundMap, 1);
		}
		
		//教育部发展报告项目
		List<Object[]> drProjects = dao.query("select dr.applicant, dr.university, dr.number, dr.name from DevelopmentReport dr where dr.university is not null and dr.isDupCheckGeneral = 1");
		for (Object[] drProject : drProjects) {
			String value = ((String)drProject[2] == null) ? (String)drProject[3] : (String)drProject[2] + "/" + (String)drProject[3];
			String[] keys = getKeys((String)drProject[1], (String)drProject[0], false);//[高校代码+项目负责人]
			for (String key : keys) {
				fillMap(key, value, developmentReportMap, 0);
			}
		}
		
		//国社科项目申请数据
		List<Object[]> nssfApplications = dao.query("select npa.applicant, npa.university, npa.name from NssfProjectApplication npa where npa.university is not null and npa.isDupCheckGeneral = 1");
		for (Object[] nssfApplication : nssfApplications) {
			String value = (String)nssfApplication[2];
			String[] keys = getKeys((String)nssfApplication[1], (String)nssfApplication[0], false);
			for (String key : keys) {
				fillMap(key, value, nssfApplicationMap, 0);
			}
		}
		
		//2014年一般项目申请数据
		List<Object[]> generalApplications = dao.query("select spa.applyer, spa.unitName, spa.projectName from SinossProjectApplication spa where spa.typeCode = 'gener' and (spa.checkStatus = '2' or spa.checkStatus = '4')");
		for (Object[] generalApplication : generalApplications) {
			String value = (String)generalApplication[2];
			String[] keys = getKeys((String)generalApplication[1], (String)generalApplication[0], false);
			for (String key: keys) {
				fillMap(key, value, generalApplicationMap, 0);
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
		System.out.println("[COMMON] list大小：" + (generalSpecials.size() + generalProjects.size() + keyProjects.size() + instpProjects.size() + postProjects.size()) + " map大小：" + (generalCurrentMap.size() + generalEndMap.size() + generalStopMap.size() + generalWithdrawMap.size() + generalSpecialMap.size() + keyMap.size() + instpMap.size() + postMap.size()));
		System.out.println("init Project complete! Used time: " + (System.currentTimeMillis() - begin) + "ms");
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
			String reason_general = "一般项目专项任务在研（" + StringTool.join(generalSpecialMap.get(key), "; ") + "）";
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
			String reason_post = "国家社科基金项目申请（" + StringTool.join(nssfApplicationMap.get(key), "; ") + "）";
			firstAuditResult += (firstAuditResult.isEmpty()) ? reason_post : "; " + reason_post; 
		}
		if (generalApplicationMap.containsKey(key)) {
			String reason_post = "2014年一般项目申请（" + StringTool.join(generalApplicationMap.get(key), "; ") + "）";
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
	 * 检查职称是否符合项目申请资格：
	 * @param project			当前项目
	 * @param firstAuditResult	初审结果
	 * @return
	 */
	private boolean isSpecialityTitleQualified(SinossProjectApplication project){
		boolean isQualified = false;
		if (高级.contains(project.getTitle())) {
			isQualified = true;
		}
		return isQualified;
	}
	
	public FirstAuditSpecialApplication2014() {
	}	
}

