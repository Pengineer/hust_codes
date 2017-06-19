package csdc.action.project.general;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import csdc.action.DbListAction;
import csdc.bean.Agency;
import csdc.bean.Mail;
import csdc.bean.ProjectApplicationResult;
import csdc.bean.SystemConfig;
import csdc.service.IGeneralService;
import csdc.tool.ApplicationContainer;
import csdc.tool.HSSFExport;
import csdc.tool.mail.MailController;
/**
 * 限项核算
 * @author yangfq
 *
 */
public class ApplicationApplyStrictAction extends DbListAction {

	private static final long serialVersionUID = 1L;
	
	private static final String projectType ="general";//项目类别
	private IGeneralService generalService;
	private static final String PAGE_NAME = "generalApplicationCheckPage";// 列表页面名称
	private String fileFileName;//导出的文件名
	private double grweight;//立项率权重
	private double moeTilt;//部属高校倾斜
	private double westTilt;//西部高校倾斜
	private double moweight;//按期中检权重
	private double eoweight;//按期结项权重
	private double asweight;//奖励得分权重
	private int annStrictTarget;//年度限项目标
	private int univStrictLB;//高校限项下界
	private int graYearScope;//申请立项项目起止年度跨度
	private int midYearScope;//按期中检项目起止年度跨度
	private int endYearScope;//按期结项项目起止年度跨度
	private int graStartYear;//立项起始年度
	private int graEndYear;//立项终止年度，为待核实年度减1年
	private int midStartYear;//中检起始年度，间隔为yearInterval年
	private int midEndYear;//中检终止年度，为当前年减去2年
	private int endStartYear;//结项起始年度，间隔为yearInterval年
	private int endEndYear;//结项终止年度，为当前年减去3年
	private int strictAppYear;//限项申请年度
	private int avgAnnAppTotal;//平均年度申请总数
	private double avgGraRatio;//平均立项率
	private double avgMidRatio;//平均按期中检率
	private double avgEndRatio;//平均按期结项率
	private long annAppBaseTotal;//年度限项基数汇总
	private int priAppTotal;//初算：限项申请总数
	private int moeTiltTotal;//部属高校倾斜
	private int westTiltTotal;//西部高校倾
	private int pubAppTotal;//发布：限项申请总数
	private int forAppTotal;//预测：预期申请总数
	private int session1;//获奖届次
	private int session2;//获奖届次2
	//著作奖 一、二、三等奖的权值
	private double bookAwardFir;
	private double bookAwardSec;
	private double bookAwardThi;
	//论文奖 一、二、三等奖的权值
	private double paperAwardFir;
	private double paperAwardSec;
	private double paperAwardThi;
	//研究报告奖（出版）一、二、三等奖的权值
	private double ResPubAwardFir;
	private double ResPubAwardSec;
	private double ResPubAwardThi;
	//研究报告奖（采纳/批示）一、二、三等奖的权值
	private double ResAdoAwardFir;
	private double ResAdoAwardSec;
	private double ResAdoAwardThi;
	//成果普及奖
	private double achPopuAward;
	
	private int flag;//是否是第一次进入页面
	
	private TransactionTemplate txTemplateRequiresNew;
	@Autowired
	private MailController mailController;
	private Mail mail;


	/**
	 * 设置默认
	 */
	@Transactional
	public String setDefault(){
		setDefaultData();
		return SUCCESS;
	}
	/**
	 * 设置默认
	 */
	@Transactional
	public void setDefaultData(){
		SystemConfig systemConfig ;
		Map map = new HashMap();
		map.put("strictAppYear", strictAppYear);
		map.put("moeTilt", moeTilt);
		map.put("westTilt", westTilt);
		map.put("univStrictLB", univStrictLB);
		map.put("annStrictTarget", annStrictTarget);
		map.put("grweight", grweight);
		map.put("moweight", moweight);
		map.put("eoweight", eoweight);
		map.put("asweight", asweight);
		if (flag == 0) {//设置默认
			map.put("graYearScope", graYearScope);
			map.put("midYearScope", midYearScope);
			map.put("endYearScope", endYearScope);
		} else if (flag == 1) {
			map.put("graStartYear", graStartYear);
			map.put("graEndYear", graEndYear);
			map.put("midStartYear", midStartYear);
			map.put("midEndYear", midEndYear);
			map.put("endStartYear", endStartYear);
			map.put("endEndYear", endEndYear);
		}
			
		
		map.put("bookAwardFir", bookAwardFir);
		map.put("bookAwardSec", bookAwardSec);
		map.put("bookAwardThi", bookAwardThi);
		map.put("paperAwardFir", paperAwardFir);
		map.put("paperAwardSec", paperAwardSec);
		map.put("paperAwardThi", paperAwardThi);
		map.put("ResAdoAwardFir", ResAdoAwardFir);
		map.put("ResAdoAwardSec", ResAdoAwardSec);
		map.put("ResAdoAwardThi", ResAdoAwardThi);
		map.put("achPopuAward", achPopuAward);
		if (flag == 1) {
			systemConfig = new SystemConfig();
			String key = String.valueOf(strictAppYear);
			systemConfig.setKey(key);
			systemConfig.setValue(map.toString());
			systemConfig.setGroup("PROJECT");
			systemConfig.setDescription("限项申请核算");
			dao.addOrModify(systemConfig);
		} else if (flag == 0) {
			List systemConfigList = dao.query("from SystemConfig sys where sys.key = 'default' ");
			if (systemConfigList.size() != 0) {
				systemConfig = (SystemConfig) systemConfigList.get(0);
			} else {
				systemConfig = new SystemConfig();
			}
			systemConfig.setKey("default");
			systemConfig.setValue(map.toString());
			systemConfig.setGroup("PROJECT");
			systemConfig.setDescription("限项申请核算");
			dao.addOrModify(systemConfig);
		}
	}
	
	/**
	 * 进入一般项目限项核算
	 *  @param 	moeTilt;//部属高校倾斜
		@param 	westTilt;//西部高校倾斜
	 	@param 	mOWeight;//按期中检权重
	 	@param 	eOWeight;//按期结项权重
	 	@param 	annStrictTarget;//年度限项目标
	 	@param 	univStrictLB;//高校限项下界
	 */
	@Transactional
	public void toListCondition(){
		List systemConfigList = dao.query("from SystemConfig sys where sys.key = 'default' ");
		if (systemConfigList.size() != 0) {
			SystemConfig systemConfig = (SystemConfig) systemConfigList.get(0);
			String value = systemConfig.getValue();
			JSONObject  jasonObject = JSONObject.fromObject(value);
			Map map = (Map)jasonObject;
			asweight = (Double) map.get("asweight");
			moeTilt = (Double) map.get("moeTilt");
			westTilt = (Double) map.get("westTilt");
			grweight = (Double) map.get("grweight");
			moweight = (Double) map.get("moweight");
			eoweight = (Double) map.get("eoweight");
			univStrictLB = (Integer) map.get("univStrictLB");
			annStrictTarget = (Integer) map.get("annStrictTarget");
			
			graYearScope = (Integer) map.get("graYearScope");
			midYearScope = (Integer) map.get("midYearScope");
			endYearScope = (Integer) map.get("endYearScope");
			strictAppYear = (Integer) map.get("strictAppYear");
			
			//著作奖 一、二、三等奖的权值
			bookAwardFir = (Double) map.get("bookAwardFir");
			bookAwardSec = (Double) map.get("bookAwardSec");
			bookAwardThi = (Double) map.get("bookAwardThi");
			//论文奖 一、二、三等奖的权值
			paperAwardFir = (Double) map.get("paperAwardFir");
			paperAwardSec = (Double) map.get("paperAwardSec");
			paperAwardThi = (Double) map.get("paperAwardThi");
			//研究报告奖（采纳/批示）一、二、三等奖的权值
			ResAdoAwardFir = (Double) map.get("ResAdoAwardFir");
			ResAdoAwardSec = (Double) map.get("ResAdoAwardSec");
			ResAdoAwardThi = (Double) map.get("ResAdoAwardThi");
			//成果普及奖
			achPopuAward = (Double) map.get("achPopuAward");
			
		} else {
			asweight = 1.0;
			moeTilt =  1.10;
			westTilt = 1.15;
			grweight = 0.20;
			moweight = 0.20;
			eoweight = 0.40;
			univStrictLB = 5;
			annStrictTarget = 25000;
			
			graYearScope = 5;//申请立项项目起止年度跨度
			midYearScope = 5;//按期中检项目起止年度跨度
			endYearScope = 5;//按期结项项目起止年度跨度
			strictAppYear = 2014;	//立项终止年度
			
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); 	
//		int nowYear = Integer.valueOf(sdf.format(new Date()));	//当前年（限项申请年度）
//		graStartYear = strictAppYear - graYearScope;			//立项起始年度，间隔为graYearScope年
//		graEndYear = nowYear-1;									//立项终止年度，当前年-1
//		midEndYear = nowYear - 1 - 2;							//中检终止年度，为当前年减去3年
//		midStartYear = midEndYear - midYearScope + 1;			//中检起始年度，间隔为midYearScope年
//		endEndYear = nowYear - 1 -5;							//结项终止年度，为当前年减去6年
//		if (endEndYear - endYearScope + 1 < 2005) {
//			endStartYear = 2005;
//		} else {
//			endStartYear = endEndYear - endYearScope + 1;		//结项起始年度，间隔为endYearScope年
//		}
			
			//著作奖 一、二、三等奖的权值
			bookAwardFir = 2;
			bookAwardSec = 1;
			bookAwardThi = 0.5;
			//论文奖 一、二、三等奖的权值
			paperAwardFir = 1;
			paperAwardSec = 0.5;
			paperAwardThi = 0.3;
			//研究报告奖（出版）一、二、三等奖的权值
			ResPubAwardFir = 2;
			ResPubAwardSec = 1;
			ResPubAwardThi = 0.5;
			//研究报告奖（采纳/批示）一、二、三等奖的权值
			ResAdoAwardFir = 1;
			ResAdoAwardSec = 0.5;
			ResAdoAwardThi = 0.3;
			//成果普及奖
			achPopuAward = 1;
		}
	}
	
	/**
	 * 一般项目限项核算
	 * 可调变量（各个奖项的权值）
	 * @param 著作奖 一、二、三等奖的权值  bookAwardFir、bookAwardSec、bookAwardThi
	 * @param 论文奖 一、二、三等奖的权值 paperAwardFir、paperAwardSec、paperAwardThi
	 * @param 研究报告奖（出版）一、二、三等奖的权值  ResPubAwardFir、ResPubAwardSec、ResPubAwardThi
	 * @param 研究报告奖（采纳/批示）一、二、三等奖的权值  ResAdoAwardFir、 ResAdoAwardSec、 ResAdoAwardThi
	 * @return dataList.add(datas[24]) 
	 */
	@Transactional
	public List<Object[]> check(){
		//西部地区（不包含新疆，西藏）
		long a = System.currentTimeMillis();
		int appSum = 0;
		int graSum = 0;
		int avgMid = 0;
		int avgEnd = 0;
		int gra4End= 0;
		int end = 0;
		String[] xbAreas = new String[]{
			"重庆市", "四川省", "贵州省", "云南省", "陕西省", "甘肃省", "宁夏回族自治区", "青海省", "内蒙古自治区", "广西壮族自治区"
		};	
		List<String> xbAreaList = Arrays.asList(xbAreas);
		Map queryMap = new HashMap();
		queryMap.put("projectType", projectType);
		//所有高校
		StringBuffer hqlUniv = new StringBuffer("select ag.id, ag.name, ag.type, pr.name from Agency ag left join ag.province pr where 1 = 1 ");
		Map map = new HashMap();
		if (null == keyword || keyword.equals("")) {//没有检索
			hqlUniv.append(" and (ag.type=3 and ag.organizer is not null) or ag.type=4");
		} else {
			if (searchType == 0) {//高校名称
				map.put("keyword", "%" + keyword + "%");
				hqlUniv.append("and LOWER(ag.name) like :keyword ");
			} else if (searchType == 1) {//高校类别
				if (keyword.contains("部")) {
					hqlUniv.append(" and (ag.type=3 and ag.organizer is not null) ");
				} else if (keyword.contains("地方")) {
					hqlUniv.append(" and ag.type =4 ");
				}
			} else if (searchType == 3) {//所在省份
				map.put("keyword", "%" + keyword + "%");
				hqlUniv.append(" and LOWER(pr.name) like :keyword ");
			} else if (searchType == -1) {
				if (keyword.contains("部")) {
					hqlUniv.append(" and (ag.type=3 and ag.organizer is not null) ");
				} else if (keyword.contains("地方")) {
					hqlUniv.append(" and ag.type =4 ");
				} else {
					map.put("keyword", "%" + keyword + "%");
					hqlUniv.append(" and (LOWER(ag.name) like :keyword or LOWER(pr.name) like :keyword) ");
				}
			}
		}
		hqlUniv.append("order by ag.name asc");
		List<Object[]> univs = dao.query(hqlUniv.toString(),map);
		session.put("univs", univs);
		//专职社科活动人员数量
		String hqlPerson = "select t.university.id, count(t.id) from Teacher t where t.type = '专职人员' group by t.university.id";
		List<Object[]> persons = dao.query(hqlPerson);
		Map personMap = new HashMap();
		for (Object[] objs : persons) {
			personMap.put(objs[0], objs[1]);
		}
		 
		//申请数
		String hqlApp = "select pa.university.id, count(pa.id) from ProjectApplication pa where pa.type = :projectType and pa.year between " + graStartYear + " and " + graEndYear + " group by pa.university.id"; 
		List<Object[]> apps = dao.query(hqlApp,queryMap);
		Map appMap = new HashMap();
		for (Object[] objs : apps) {
			appSum += ((Long) objs[1]);
			appMap.put(objs[0], objs[1]);
		}
		//立项数
		String hqlGra = "select pa.university.id, count(pa.id) from ProjectApplication pa where pa.finalAuditStatus = 3 and pa.finalAuditResult = 2 and pa.type = :projectType and pa.year between " + graStartYear + " and " + graEndYear + " group by pa.university.id";
		List<Object[]> gras = dao.query(hqlGra,queryMap);
		Map graMap = new HashMap();
		for (Object[] objs : gras) {
			graSum += ((Long) objs[1]);
			graMap.put(objs[0], objs[1]);
		}
		
		//中检年度区间的立项数
		String hqlGra4Mid = "select pg.university.id, count(pg.id) from ProjectGranted pg, ProjectApplication pa where pg.applicationId = pa.id and pa.type = :projectType and pa.year between " + midStartYear + " and " + midEndYear + " group by pg.university.id"; 
		List<Object[]> gra4Mids = dao.query(hqlGra4Mid,queryMap);
		Map gra4MidMap = new HashMap();
		for (Object[] objs : gra4Mids) {
			avgMid  += ((Long) objs[1]);
			gra4MidMap.put(objs[0], objs[1]);
		}
		//按期中检
		String hqlMid = "select pg.university.id, count(pm.id) from ProjectMidinspection pm, ProjectGranted pg, ProjectApplication pa where pm.grantedId = pg.id and pg.applicationId = pa.id and pa.type = :projectType and " +
				"pm.finalAuditResult = 2 and pm.finalAuditStatus = 3 and (to_char(pm.finalAuditDate, 'yyyy') - pa.year) <= 2 and pa.year between " + midStartYear + " and " + midEndYear + " group by pg.university.id"; 
		List<Object[]> mids = dao.query(hqlMid,queryMap);
		Map midMap = new HashMap();
		for (Object[] objs : mids) {
			avgEnd += ((Long) objs[1]);
			midMap.put(objs[0], objs[1]);
		}
		
		//结项年度区间的立项数
		String hqlGra4End = "select pg.university.id, count(pg.id) from ProjectGranted pg, ProjectApplication pa where pg.applicationId = pa.id and pa.type = :projectType and pa.year between " + endStartYear + " and " + endEndYear + " group by pg.university.id"; 
		List<Object[]> gra4Ends = dao.query(hqlGra4End,queryMap);
		Map gra4EndMap = new HashMap();
		for (Object[] objs : gra4Ends) {
			gra4End += ((Long)objs[1]);
			gra4EndMap.put(objs[0], objs[1]);
		}
		//按期结项
		String hqlEnd = "select pg.university.id, count(pe.id) from ProjectEndinspection pe, ProjectGranted pg, ProjectApplication pa where pe.grantedId = pg.id and pg.applicationId = pa.id and pa.type = :projectType and " +
				"pe.finalAuditResultEnd = 2 and pe.finalAuditStatus = 3 and (to_char(pe.finalAuditDate, 'yyyy') - pa.year) <= 5 and pa.year between " + endStartYear + " and " + endEndYear + " group by pg.university.id"; 
		List<Object[]> ends = dao.query(hqlEnd,queryMap);
		Map endMap = new HashMap();
		for (Object[] objs : ends) {
			end += ((Long) objs[1]);
			endMap.put(objs[0], objs[1]);
		}
		avgMidRatio = (Double) generalService.calculate(avgEnd, avgMid);
		avgEndRatio = (Double) generalService.calculate(end, gra4End);
		avgAnnAppTotal = Math.round(appSum/5);
		if (null == keyword || keyword.equals("")) {
			if (graSum !=0 || appSum!=0) {
				avgGraRatio = (Double) generalService.calculate(graSum, appSum);//平均立项率
			} else {
				avgGraRatio = 0.0;
			}
			session.put("graRatio", avgGraRatio);
		} else {
			avgGraRatio = (Double) session.get("graRatio");
		}
		session1 = (Integer) dao.query("select MAX(a.session) from AwardGranted a").get(0);
		//著作奖 一、二、三等奖
		Map bookAwardFirst = generalService.getAwardScore(session1, generalService.getSystemId("014", "awardType"), generalService.getSystemId("02", "awardGrade"), bookAwardFir);
		Map bookAwardSecond = generalService.getAwardScore(session1, generalService.getSystemId("014", "awardType"), generalService.getSystemId("03", "awardGrade"), bookAwardSec);
		Map bookAwardThird = generalService.getAwardScore(session1, generalService.getSystemId("014", "awardType"), generalService.getSystemId("04", "awardGrade"), bookAwardThi);
		//论文奖 一、二、三等奖
		Map paperAwardFirst = generalService.getAwardScore(session1, generalService.getSystemId("012", "awardType"), generalService.getSystemId("02", "awardGrade"), paperAwardFir);
		Map paperAwardSecond = generalService.getAwardScore(session1, generalService.getSystemId("012", "awardType"), generalService.getSystemId("03", "awardGrade"), paperAwardSec);
		Map paperAwardThird = generalService.getAwardScore(session1, generalService.getSystemId("012", "awardType"), generalService.getSystemId("04", "awardGrade"), paperAwardThi);
		//研究报告奖（出版）一、二、三等奖的权值
//		Map ResPubAwardFirst = generalService.getAwardScore(session1, generalService.getSystemId("013", "awardType"), generalService.getSystemId("02", "awardGrade"), ResPubAwardFir);
//		Map ResPubAwardSecond = generalService.getAwardScore(session1, generalService.getSystemId("013", "awardType"), generalService.getSystemId("03", "awardGrade"), ResPubAwardSec);
//		Map ResPubAwardThird = generalService.getAwardScore(session1, generalService.getSystemId("013", "awardType"), generalService.getSystemId("04", "awardGrade"), ResPubAwardThi);
		//研究报告奖（采纳/批示） 一、二、三等奖的权值
		Map ResAdoAwardFirst = generalService.getAwardScore(session1, generalService.getSystemId("013", "awardType"), generalService.getSystemId("02", "awardGrade"), ResAdoAwardFir);
		Map ResAdoAwardSecond = generalService.getAwardScore(session1, generalService.getSystemId("013", "awardType"), generalService.getSystemId("03", "awardGrade"), ResAdoAwardSec);
		Map ResAdoAwardThird = generalService.getAwardScore(session1, generalService.getSystemId("013", "awardType"), generalService.getSystemId("04", "awardGrade"), ResAdoAwardThi);
		//成果普及奖
		Map PopuAward = generalService.getAwardScore(session1, generalService.getSystemId("011", "awardType"), null, achPopuAward);
		System.out.println(System.currentTimeMillis()-a +"进入循环之前用时..................................");
		//组装导出数据
		long b = System.currentTimeMillis();
		List dataList = new ArrayList();
		for (Object[] objs : univs) {
			Object[] datas = new Object[24];
			String univId = (String) objs[0];
			ProjectApplicationResult projectApplicationResult;
			List ProjectApplicationResultList = dao.query("from ProjectApplicationResult pa where pa.university.id =?", univId);
			if (ProjectApplicationResultList.size() != 0) {
				projectApplicationResult = (ProjectApplicationResult) ProjectApplicationResultList.get(0);
			} else {
				projectApplicationResult = new ProjectApplicationResult();
			}
			Agency agency = dao.query(Agency.class, univId);
			projectApplicationResult.setUniversity(agency);
			projectApplicationResult.setUniversityName(agency.getName());
			projectApplicationResult.setYear(strictAppYear);
			projectApplicationResult.setProjrctType("general");
			datas[0] = objs[1];//高校名称
			datas[1] = ((Integer)objs[2] == 3) ? "部属" : null;//高校类型（部属）
			projectApplicationResult.setUniversityType((String) datas[1]);
			datas[2] = xbAreaList.contains(objs[3]) ? "西部" : null;//所在地区（西部）
			projectApplicationResult.setUniversityArea((String) datas[2]);
			datas[3] = objs[3];//所在省份（新疆/西藏）
			projectApplicationResult.setProvince((String) objs[3]);
			
			datas[4] = (appMap.containsKey(univId)) ? ((Long)appMap.get(univId)).intValue() : 0;//申请数量
			projectApplicationResult.setApplicationNumber((Integer) datas[4]);
			datas[5] = (graMap.containsKey(univId)) ? ((Long)graMap.get(univId)).intValue() : 0;//立项数量
			projectApplicationResult.setGrantedNumber1((Integer) datas[5]);
			datas[6] = generalService.calculate(datas[5], datas[4]);//立项率
			if (datas[6] instanceof Long) {
				Long ratio = (Long) datas[6];
				Double endinspectionRatio = ratio.doubleValue();
				projectApplicationResult.setGrantedRatio(endinspectionRatio);
			} else {
				projectApplicationResult.setGrantedRatio((Double) datas[6]);
			}
			
			datas[7] = (gra4MidMap.containsKey(univId)) ? gra4MidMap.get(univId) : 0;//立项数量
			if (datas[7] instanceof Long) {
				projectApplicationResult.setGrantedNumber2((Long) datas[7]);
			} else {
				Integer num =  (Integer) datas[7];
				Long endinspectionNumber = num.longValue();
				projectApplicationResult.setGrantedNumber2(endinspectionNumber);
			}
			datas[8] = (midMap.containsKey(univId)) ? midMap.get(univId) : 0;//按期中检数量
			if (datas[8] instanceof Long) {
				projectApplicationResult.setMidinspectionNumber((Long) datas[8]);
			} else {
				Integer num =  (Integer) datas[8];
				Long endinspectionNumber = num.longValue();
				projectApplicationResult.setMidinspectionNumber(endinspectionNumber);
			}
			datas[9] = generalService.calculate(datas[8], datas[7]);//按期中检率
			if (datas[9] instanceof Long) {
				Long ratio = (Long) datas[9];
				Double midinspectionRatio = ratio.doubleValue();
				projectApplicationResult.setMidinspectionRatio(midinspectionRatio);
			} else {
				projectApplicationResult.setMidinspectionRatio((Double) datas[9]);
			}
			datas[10] = (gra4EndMap.containsKey(univId)) ? gra4EndMap.get(univId) : 0;//立项数量
			if (datas[10] instanceof Long) {
				projectApplicationResult.setGrantedNumber3((Long) datas[10]);
			} else {
				Integer num =  (Integer) datas[10];
				Long endinspectionNumber = num.longValue();
				projectApplicationResult.setGrantedNumber3(endinspectionNumber);
			}
			datas[11] = (endMap.containsKey(univId)) ? endMap.get(univId) : 0;//按期结项数量
			if (datas[11] instanceof Long) {
				projectApplicationResult.setEndinspectionNumber((Long) datas[11]);
			} else {
				Integer num =  (Integer) datas[11];
				Long endinspectionNumber = num.longValue();
				projectApplicationResult.setEndinspectionNumber(endinspectionNumber);
			}
			datas[12] = generalService.calculate(datas[11], datas[10]);//按期结项率
			if (datas[12] instanceof Long) {
				Long ratio = (Long) datas[12];
				Double endinspectionRatio = ratio.doubleValue();
				projectApplicationResult.setEndinspectionRatio(endinspectionRatio);
			} else {
				projectApplicationResult.setEndinspectionRatio((Double) datas[12]);
			}
			Double bAwardFirst = (bookAwardFirst.get(univId) != null) ? (Double)bookAwardFirst.get(univId) : 0.0;
			Double bAwardSecond = (bookAwardSecond.get(univId) != null) ? (Double)bookAwardSecond.get(univId) : 0.0;
			Double bAwardThird = (bookAwardThird.get(univId) != null) ? (Double)bookAwardThird.get(univId) : 0.0;
			
			Double pAwardFirst = (paperAwardFirst.get(univId) != null) ? (Double)paperAwardFirst.get(univId) : 0.0;
			Double pAwardSecond = (paperAwardSecond.get(univId) != null) ? (Double)paperAwardSecond.get(univId) : 0.0;
			Double pAwardThird = (paperAwardThird.get(univId) != null) ? (Double)paperAwardThird.get(univId) : 0.0;
			
//			Double RPubAwardFirst = (ResPubAwardFirst.get(univId) != null) ? (Double)ResPubAwardFirst.get(univId) : 0.0;
//			Double RPubAwardSecond = (ResPubAwardSecond.get(univId) != null) ? (Double)ResPubAwardSecond.get(univId) : 0.0;
//			Double RPubAwardThird = (ResPubAwardThird.get(univId) != null) ? (Double)ResPubAwardThird.get(univId) : 0.0;
			
			Double RAdoAwardFirst = (ResAdoAwardFirst.get(univId) != null) ? (Double)ResAdoAwardFirst.get(univId) : 0.0;
			Double RAdoAwardSecond = (ResAdoAwardSecond.get(univId) != null) ? (Double)ResAdoAwardSecond.get(univId) : 0.0;
			Double RAdoAwardThird = (ResAdoAwardThird.get(univId) != null) ? (Double)ResAdoAwardThird.get(univId) : 0.0;
			
			Double PAward = (PopuAward.get(univId) != null) ? (Double)PopuAward.get(univId) : 0.0;
//			datas[13] = bAwardFirst + bAwardSecond + bAwardThird + pAwardFirst + pAwardSecond + pAwardThird
//					+ RPubAwardFirst + RPubAwardSecond + RPubAwardThird + RAdoAwardFirst + RAdoAwardSecond + RAdoAwardThird
//					+ PAward;//最近一届获奖得分
			Double score =  bAwardFirst + bAwardSecond + bAwardThird + pAwardFirst + pAwardSecond + pAwardThird
					+ RAdoAwardFirst + RAdoAwardSecond + RAdoAwardThird
					+ PAward;
			Double socreDouble = Math.round(score*10)/10.0;//保留一位小数
			datas[13] = socreDouble;//最近一届获奖得分
			Double aa = (Double) datas[13];
			if (aa != 0.0) {
				System.out.println(objs[1]);
				System.out.println(aa);
			}
			datas[14] = Math.round((((Integer) datas[4]).doubleValue())/5.0);//年度限项申请基数（往年平均申请数
			annAppBaseTotal += (Long)datas[14];
			if (datas[6] instanceof Long) {
				datas[15] = Math.round(((((Long)datas[6]).doubleValue())-avgGraRatio)*grweight*(((Long)datas[14]).doubleValue()));//立项率奖励数
			} else {
				datas[15] = Math.round(((((Double)datas[6]))-avgGraRatio)*grweight*(((Long)datas[14]).doubleValue()));//立项率奖励数
			}
			if (datas[9] instanceof Long) {
				datas[16] = Math.round(((((Long)datas[9]).doubleValue()) - 1)*(((Long)datas[14]).doubleValue())*moweight);//逾期中检惩罚数
			} else {
				datas[16] = Math.round(((((Double)datas[9])) - 1)*(((Long)datas[14]).doubleValue())*moweight);//逾期中检惩罚数
			}
			if (datas[12] instanceof Long) {
				datas[17] = Math.round((((Long)datas[12]).doubleValue() - 1.0)*(((Long)datas[14]).doubleValue())*eoweight);//逾期结项惩罚数
			} else {
				datas[17] = Math.round((((Double)datas[12]) - 1.0)*(((Long)datas[14]).doubleValue())*eoweight);//逾期结项惩罚数
			}
			datas[18] = Math.round((Double)datas[13] * asweight);//奖励得分奖惩数
			int app = Math.round(Math.round(((Long)datas[14]).doubleValue())+Math.round(((Long) datas[15]).doubleValue())+Math.round(((Long) datas[16]).doubleValue())+Math.round(((Long) datas[17]).doubleValue())+Math.round(((Long) datas[18]).doubleValue()));
			if (app < 0) {
				datas[19] = 0;//初算：限项申请数量
			} else {
				datas[19] = app;//初算：限项申请数量
			}
			if (datas[1] != null && datas[1].equals("部属")) {
				datas[20] = Math.round(((Integer)datas[19]).doubleValue()*moeTilt);//部属高校倾斜
			} else {
				datas[20] = Math.round(((Integer) datas[19]).doubleValue());//部属高校倾斜
			}
			if (datas[2] != null && datas[2].equals("西部")) {
				datas[21] = Math.round(((Long)datas[20]).doubleValue()*westTilt);//西部高校倾斜
			} else {
				datas[21] = Math.round(((Long)datas[20]).doubleValue());//部属高校倾斜
			}
			projectApplicationResult.setAwardScore((Double) datas[13]);
			projectApplicationResult.setBaseApplicationNumber((Long) datas[14]);
			projectApplicationResult.setGrantedRateWeight((Long) datas[15]);
			projectApplicationResult.setOverdueMidinspectionWeight((Long) datas[16]);
			projectApplicationResult.setOverdueEndinspectionWeight((Long) datas[17]);
			projectApplicationResult.setAwardScoreWeight((Long) datas[18]);
			projectApplicationResult.setPriApplicationTotal((Integer) datas[19]);
			projectApplicationResult.setMoeTiltTotal((Long) datas[20]);
			projectApplicationResult.setWestTiltTotal((Long) datas[21]);
			if (datas[3] != null && (datas[3].equals("新疆维吾尔自治区") || datas[3].equals("西藏自治区"))) {
				datas[22] = "不限";//发布：限项申请数量
			} else {
				datas[22] = 0;//发布：限项申请数量
			}
			datas[23] = 0;//预测：预期申请数量
			if (!(datas[22] instanceof String)) {
				Long westTiltTotal = (Long) datas[21];
				Long titlr = 0L;
				if (generalService.calculate(annStrictTarget,priAppTotal) instanceof Double) {
					Double test = (Double) generalService.calculate(annStrictTarget,priAppTotal);
					titlr = (long) (westTiltTotal * test);
					if (titlr<univStrictLB) {
						long data  =  Math.round(univStrictLB);
						datas[22] = data;//发布：限项申请数量
					} else {
						datas[22] = Math.round(((Long) datas[21])*(test));
					}
				} else {
					Long test = (Long) generalService.calculate(annStrictTarget,priAppTotal);
					titlr = (long) (westTiltTotal * test);
					if (titlr<univStrictLB) {
						long data  =  Math.round(univStrictLB);
						datas[22] = data;//发布：限项申请数量
					} else {
						datas[22] = Math.round(((Long) datas[21])*(test));
					}
				}
				if (datas[22] instanceof Integer) {
					if ((Long) datas[14] < (Integer) datas[22]) {
						datas[23] = (Long) datas[14];//预测：预期申请数量
					} else {
						datas[23] = (Integer) datas[22];//预测：预期申请数量
					}
				} else {
					if ((Long) datas[14] < (Long) datas[22]) {
						datas[23] = (Long) datas[14];//预测：预期申请数量
					} else {
						datas[23] = (Long) datas[22];//预测：预期申请数量
					}
				}
			} else {
				datas[23] = (Long) datas[14];//预测：预期申请数量
			}
			if (datas[22] instanceof String) {
				projectApplicationResult.setPubApplicationTotal((String) datas[22]);
			} else {
				projectApplicationResult.setPubApplicationTotal((String) datas[22].toString());
			}
			if (datas[23] instanceof Long) {
				projectApplicationResult.setForApplicationTotal((Long) datas[23]);
			} else {
				projectApplicationResult.setForApplicationTotal(((Integer) datas[23]).longValue());
			}
			if (null == keyword || keyword.equals("")) {
				priAppTotal += (Integer)datas[19];
				session.put("priAppTotal", priAppTotal);
			} else {
				priAppTotal = (Integer) session.get("priAppTotal");
			}
			dao.addOrModify(projectApplicationResult);
			dataList.add(datas);
		}
		long c = System.currentTimeMillis();
		System.out.println(c-b + "循环1用时..............................................");
		for (int i = 0; i < dataList.size(); i++) {
			if (null == keyword || keyword.equals("")) {
				if(!(((Object[]) dataList.get(i))[22] instanceof String)) {
					if (((Object[]) dataList.get(i))[22] instanceof Long) {
						pubAppTotal += (Long) ((Object[]) dataList.get(i))[22];
					} else {
						pubAppTotal += (Integer) ((Object[]) dataList.get(i))[22];
					}
					if (((Object[]) dataList.get(i))[23] instanceof Long) {
						forAppTotal += (Long) ((Object[]) dataList.get(i))[23];
					} else {
						forAppTotal += (Integer) ((Object[]) dataList.get(i))[23];
					}
				}
				session.put("pubAppTotal", pubAppTotal);
				session.put("forAppTotal", forAppTotal);
			} else {
				pubAppTotal += (Integer) session.get("pubAppTotal");
				forAppTotal += (Integer) session.get("forAppTotal");
			}
		}
		
		//按照申请数量降序排列
		Comparator<Object[]> appCountComparator = new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				Integer cnt1 = Integer.parseInt(String.valueOf(o1[4]));
				Integer cnt2 = Integer.parseInt(String.valueOf(o2[4]));
				return cnt2.compareTo(cnt1);
			}
		};
		List variableList = new ArrayList();
		variableList.add(moeTilt);
		variableList.add(westTilt);
		variableList.add(grweight);
		variableList.add(moweight);
		variableList.add(eoweight);
		variableList.add(asweight);
		variableList.add(univStrictLB);
		variableList.add(annStrictTarget);
		
		variableList.add(avgAnnAppTotal);
		variableList.add(avgGraRatio);
		variableList.add(avgMidRatio);
		variableList.add(avgEndRatio);
		variableList.add(annAppBaseTotal);//
		variableList.add(priAppTotal);
		variableList.add(moeTiltTotal);
		variableList.add(westTiltTotal);
		variableList.add(pubAppTotal);
		variableList.add(forAppTotal);
		
		Collections.sort(dataList, appCountComparator);
		session.put("dataList", dataList);
		session.put("strictAppYear", strictAppYear);
		session.put("variableList", variableList);
		jsonMap.put("avgAnnAppTotal", avgAnnAppTotal);
		jsonMap.put("avgGraRatio", avgGraRatio);
		jsonMap.put("avgMidRatio", avgMidRatio);
		jsonMap.put("avgEndRatio", avgEndRatio);
		System.out.println(System.currentTimeMillis()-c + "循环2用时..............................................");
		System.out.println(System.currentTimeMillis() - a + "总耗时...............................................");
		return dataList;
	}
	
	/**
	 * 确认导出限项申请核算
	 * @return
	 * @author yangfq
	 */
	public String confirmExportOverView(){
		return SUCCESS;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String sendMail() throws Exception{
		Map jsonMap = new HashMap();
		List<ProjectApplicationResult> projectApplicationResults = dao.query("select pa from ProjectApplicationResult pa where pa.year = ? order by pa.applicationNumber desc ", strictAppYear);
		int qq=0;
		for (ProjectApplicationResult projectApplicationResult : projectApplicationResults) {
			if (qq<1) {
				qq++;
			}else {
				break;
			}
			mail = null;
			mail = new Mail();
			String univName =projectApplicationResult.getUniversityName();
			String num = projectApplicationResult.getPubApplicationTotal();
			mail.setSendTo(projectApplicationResult.getUniversity().getEmail());
			mail.setReplyTo("serv@csdc.info");
			mail.setAccount(loginer.getAccount());// 发件者
			String accountBelong = "";
			if (loginer.getCurrentBelongUnitName() != null) {
				accountBelong = loginer.getCurrentBelongUnitName();
			}
			if (loginer.getCurrentPersonName() != null) {
				accountBelong = loginer.getCurrentPersonName();
			}
			Date createDate = new Date(System.currentTimeMillis());
			mail.setCreateDate(createDate);
			mail.setSubject("限项申请");
			mail.setAccountBelong(accountBelong);
			StringBuffer body = new StringBuffer(univName+"，你好！<br/>"+ strictAppYear +
					"年度，贵校年度项目限项申请总数为"+num+"。");
			mail.setBody(body.toString());
			mail.setIsHtml(1);
			txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {// 获取收件人地址
						
						dao.add(mail);
					} catch (Exception e) {
						status.setRollbackOnly();
					}
					
				}
			});
			mailController.send(mail.getId());
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public InputStream getDownloadFile() throws UnsupportedEncodingException{
		List<List> arrayList = new ArrayList<List>();
		List dataList = (List)  session.get("dataList");
		List variableList = (List)  session.get("variableList");
		arrayList.add(dataList);
		arrayList.add(variableList);
		String header = "教育部人文社会科学研究一般项目限项申请核算表";
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes("UTF-8"), "ISO8859-1");
		String realPath = ApplicationContainer.sc.getRealPath("file");
		realPath = realPath.replace('\\', '/');
		String modelFilePath = realPath + "/template/general/applyStrictAction/20131114_2014年度教育部人文社会科学研究一般项目限项申报核算表.xls"; 
		//调用导出公共方法
		return HSSFExport.exportFromModel(arrayList, modelFilePath, 8);
	}
	
	@Transactional
	public List<Object[]> listData() {
		List<Object[]> dataList = new ArrayList<Object[]>();
		if (flag == 1) {//点击开始核算，计算结果并且保存结果，同时保存配置
			dataList = check();
			setDefaultData();//保存设置
		} else if (flag == 2) {//清空核算
			
		} else {//初次进入核算页面时，数据库中存在结果则直接读取结果
			List projectApplicationResultList = dao.query("select pa.universityName, pa.universityType, pa.universityArea, pa.province, pa.applicationNumber, pa.grantedNumber1, " +
					"pa.grantedRatio, pa.grantedNumber2, pa.midinspectionNumber, pa.midinspectionRatio, pa.grantedNumber3, pa.endinspectionNumber, pa.endinspectionRatio, pa.awardScore, " +
					"pa.baseApplicationNumber, pa.grantedRateWeight, pa.overdueMidinspectionWeight, pa.overdueEndinspectionWeight, pa.awardScoreWeight, pa.priApplicationTotal, " +
					"pa.moeTiltTotal, pa.westTiltTotal,  pa.pubApplicationTotal, pa.forApplicationTotal from ProjectApplicationResult pa where pa.year = ? order by pa.applicationNumber desc ", strictAppYear);
			if (projectApplicationResultList.size() != 0) {
				dataList = projectApplicationResultList;
			} else {
				dataList = check();
			}
		}
		return dataList;
	}
	public String pageName() {
		return PAGE_NAME;
	}

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}

	public double getGrweight() {
		return grweight;
	}

	public void setGrweight(double grweight) {
		this.grweight = grweight;
	}

	public double getMoeTilt() {
		return moeTilt;
	}

	public void setMoeTilt(double moeTilt) {
		this.moeTilt = moeTilt;
	}

	public double getWestTilt() {
		return westTilt;
	}

	public void setWestTilt(double westTilt) {
		this.westTilt = westTilt;
	}

	public double getMoweight() {
		return moweight;
	}

	public void setMoweight(double moweight) {
		this.moweight = moweight;
	}

	public double getEoweight() {
		return eoweight;
	}

	public void setEoweight(double eoweight) {
		this.eoweight = eoweight;
	}

	public double getAsweight() {
		return asweight;
	}

	public void setAsweight(double asweight) {
		this.asweight = asweight;
	}

	public int getAnnStrictTarget() {
		return annStrictTarget;
	}

	public void setAnnStrictTarget(int annStrictTarget) {
		this.annStrictTarget = annStrictTarget;
	}

	public int getUnivStrictLB() {
		return univStrictLB;
	}

	public void setUnivStrictLB(int univStrictLB) {
		this.univStrictLB = univStrictLB;
	}

	public int getGraYearScope() {
		return graYearScope;
	}

	public void setGraYearScope(int graYearScope) {
		this.graYearScope = graYearScope;
	}

	public int getMidYearScope() {
		return midYearScope;
	}

	public void setMidYearScope(int midYearScope) {
		this.midYearScope = midYearScope;
	}

	public int getEndYearScope() {
		return endYearScope;
	}

	public void setEndYearScope(int endYearScope) {
		this.endYearScope = endYearScope;
	}

	public int getGraStartYear() {
		return graStartYear;
	}

	public void setGraStartYear(int graStartYear) {
		this.graStartYear = graStartYear;
	}

	public int getGraEndYear() {
		return graEndYear;
	}

	public void setGraEndYear(int graEndYear) {
		this.graEndYear = graEndYear;
	}

	public int getMidStartYear() {
		return midStartYear;
	}

	public void setMidStartYear(int midStartYear) {
		this.midStartYear = midStartYear;
	}

	public int getMidEndYear() {
		return midEndYear;
	}

	public void setMidEndYear(int midEndYear) {
		this.midEndYear = midEndYear;
	}

	public int getEndStartYear() {
		return endStartYear;
	}

	public void setEndStartYear(int endStartYear) {
		this.endStartYear = endStartYear;
	}

	public int getEndEndYear() {
		return endEndYear;
	}

	public void setEndEndYear(int endEndYear) {
		this.endEndYear = endEndYear;
	}

	public int getAvgAnnAppTotal() {
		return avgAnnAppTotal;
	}

	public void setAvgAnnAppTotal(int avgAnnAppTotal) {
		this.avgAnnAppTotal = avgAnnAppTotal;
	}

	public double getAvgGraRatio() {
		return avgGraRatio;
	}

	public void setAvgGraRatio(double avgGraRatio) {
		this.avgGraRatio = avgGraRatio;
	}

	public double getAvgMidRatio() {
		return avgMidRatio;
	}

	public void setAvgMidRatio(double avgMidRatio) {
		this.avgMidRatio = avgMidRatio;
	}

	public double getAvgEndRatio() {
		return avgEndRatio;
	}

	public void setAvgEndRatio(double avgEndRatio) {
		this.avgEndRatio = avgEndRatio;
	}

	public long getAnnAppBaseTotal() {
		return annAppBaseTotal;
	}

	public void setAnnAppBaseTotal(long annAppBaseTotal) {
		this.annAppBaseTotal = annAppBaseTotal;
	}

	public int getPriAppTotal() {
		return priAppTotal;
	}

	public void setPriAppTotal(int priAppTotal) {
		this.priAppTotal = priAppTotal;
	}

	public int getMoeTiltTotal() {
		return moeTiltTotal;
	}

	public void setMoeTiltTotal(int moeTiltTotal) {
		this.moeTiltTotal = moeTiltTotal;
	}

	public int getWestTiltTotal() {
		return westTiltTotal;
	}

	public void setWestTiltTotal(int westTiltTotal) {
		this.westTiltTotal = westTiltTotal;
	}

	public int getPubAppTotal() {
		return pubAppTotal;
	}

	public void setPubAppTotal(int pubAppTotal) {
		this.pubAppTotal = pubAppTotal;
	}

	public int getForAppTotal() {
		return forAppTotal;
	}

	public void setForAppTotal(int forAppTotal) {
		this.forAppTotal = forAppTotal;
	}

	public int getSession1() {
		return session1;
	}

	public void setSession1(int session1) {
		this.session1 = session1;
	}

	public int getSession2() {
		return session2;
	}

	public void setSession2(int session2) {
		this.session2 = session2;
	}

	public double getBookAwardFir() {
		return bookAwardFir;
	}

	public void setBookAwardFir(double bookAwardFir) {
		this.bookAwardFir = bookAwardFir;
	}

	public double getBookAwardSec() {
		return bookAwardSec;
	}

	public void setBookAwardSec(double bookAwardSec) {
		this.bookAwardSec = bookAwardSec;
	}

	public double getBookAwardThi() {
		return bookAwardThi;
	}

	public void setBookAwardThi(double bookAwardThi) {
		this.bookAwardThi = bookAwardThi;
	}

	public double getPaperAwardFir() {
		return paperAwardFir;
	}

	public void setPaperAwardFir(double paperAwardFir) {
		this.paperAwardFir = paperAwardFir;
	}

	public double getPaperAwardSec() {
		return paperAwardSec;
	}

	public void setPaperAwardSec(double paperAwardSec) {
		this.paperAwardSec = paperAwardSec;
	}

	public double getPaperAwardThi() {
		return paperAwardThi;
	}

	public void setPaperAwardThi(double paperAwardThi) {
		this.paperAwardThi = paperAwardThi;
	}

	public double getResPubAwardFir() {
		return ResPubAwardFir;
	}

	public void setResPubAwardFir(double resPubAwardFir) {
		ResPubAwardFir = resPubAwardFir;
	}

	public double getResPubAwardSec() {
		return ResPubAwardSec;
	}

	public void setResPubAwardSec(double resPubAwardSec) {
		ResPubAwardSec = resPubAwardSec;
	}

	public double getResPubAwardThi() {
		return ResPubAwardThi;
	}

	public void setResPubAwardThi(double resPubAwardThi) {
		ResPubAwardThi = resPubAwardThi;
	}

	public double getResAdoAwardFir() {
		return ResAdoAwardFir;
	}

	public void setResAdoAwardFir(double resAdoAwardFir) {
		ResAdoAwardFir = resAdoAwardFir;
	}

	public double getResAdoAwardSec() {
		return ResAdoAwardSec;
	}

	public void setResAdoAwardSec(double resAdoAwardSec) {
		ResAdoAwardSec = resAdoAwardSec;
	}

	public double getResAdoAwardThi() {
		return ResAdoAwardThi;
	}

	public void setResAdoAwardThi(double resAdoAwardThi) {
		ResAdoAwardThi = resAdoAwardThi;
	}

	public double getAchPopuAward() {
		return achPopuAward;
	}

	public void setAchPopuAward(double achPopuAward) {
		this.achPopuAward = achPopuAward;
	}

	public int getStrictAppYear() {
		return strictAppYear;
	}

	public void setStrictAppYear(int strictAppYear) {
		this.strictAppYear = strictAppYear;
	}

	public double getGraRatio() {
		return avgGraRatio;
	}

	public void setGraRatio(double graRatio) {
		this.avgGraRatio = graRatio;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	public TransactionTemplate getTxTemplateRequiresNew() {
		return txTemplateRequiresNew;
	}
	public void setTxTemplateRequiresNew(TransactionTemplate txTemplateRequiresNew) {
		this.txTemplateRequiresNew = txTemplateRequiresNew;
	}
	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	
}
