package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.InstpApplication;
import csdc.bean.InstpMember;
import csdc.bean.ProjectApplication;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossProjectApplication;
import csdc.bean.SpecialApplication;
import csdc.bean.SpecialMember;
import csdc.bean.SystemOption;
import csdc.dao.SystemOptionDao;
import csdc.tool.ApplicationContainer;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.SpecialProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

public class ProjectApplicationAutoImporter extends Importer {
	/**
	 * 《附件1：20140314_教育部社会科学研究管理平台数据交换职称代码.xls》
	 */
	protected ExcelReader reader;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;

	@Autowired
	protected UniversityFinder universityFinder;
	
	@Autowired
	protected UnivPersonFinder univPersonFinder;
	
	@Autowired
	protected InstituteFinder instituteFinder;
	
	@Autowired
	protected DepartmentFinder departmentFinder;

	@Autowired
	protected GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	protected SpecialProjectFinder specialProjectFinder;

	@Autowired
	protected AcademicFinder academicFinder;
	
	@Autowired
	protected InstpProjectFinder instpProjectFinder;

	@Autowired
	protected ProductTypeNormalizer productTypeNormalizer;
	
	@Autowired
	protected ExpertFinder expertFinder;
	
	@Autowired
	protected Tool tool;
	
	@Autowired
	protected SystemOptionDao systemOptionDao;
	
	/**
	 * 初始化专项项目子类的子类
	 */
	Map<String, SystemOption> specialSubSubMap;
	
	/**
	 * 从初始化的map集合中获取指定项目的成员
	 */
	protected List<SinossMembers> currentSinossMembers;
	
	/**
	 * 初始化职称代码 -> 职称代码/职称名
	 */
	protected Map<String, String> codeTocodetitle;
	
	/**
	 * 初始化职称代-> 职称代码/职称名
	 */
	protected Map<String, String> titleTocodetitle;
	
	/**
	 * 一般项目成员序号(申请人是1，成员从2开始)
	 */
	protected Map<GeneralMember, String> generalMemberOrder;
	
	/**
	 * 专项项目成员序号(申请人是1，成员从2开始)
	 */
	protected Map<SpecialMember, String> specialMemberOrder;
	
	/**
	 * 基地项目成员序号(申请人是1，成员从2开始)
	 */
	protected Map<InstpMember, String> instpMemberOrder;
	
	protected Map<String,String> addressParaMap;
	
	protected Map<String,String> projectAppParaMap;
	
	/**
	 * 成员序号
	 */
	protected int memberCount = 2;
	
	/**
	 * 最小成员序号
	 */
	protected int minMemberOrder = 0;
	
	/**
	 * 当前导入项目
	 */
	protected SinossProjectApplication spa;
	
	//总项目数
	protected int totalNum = 0;
	
	// 当前导入项目条数		  
	protected int currentNum = 0;
	
	// 总共导入项目条数		  
	protected int totalImportNum = 0;

	//是否取消导入（1：是；0：否）
	protected int status;
	
	
	//从社科网同步的到中间表的时间
	protected String dumpDate;
	
	//异常数据
	protected Map<String,List<String[]>> illegalException;
	
	protected List<SinossProjectApplication> templist;	
	
	//记录当前项目的审核数据
	protected List<SinossChecklogs> sinossTempChecklogs;
	
	//初始化成员数据
	protected Map<String, List<SinossMembers>> projectIdToSMembersMap = null;
	
	//初始化审核数据
	protected Map<String, List<SinossChecklogs>> projectIdToSChecklogMap = null;
	
	protected Map<String, Agency> agencyMap;
	
	//是否导入完毕 （1：是；0：否）
	protected int isFinished;
	
	@Override
	protected void work() throws Throwable {
	
	}
	
	public Agency getAgencyByName(String agencyName) {
		if (agencyMap == null) {
			initUnivMap();
		}
		return agencyMap.get(agencyName);
	}
	
	private void initUnivMap() {
		long beginTime  = new Date().getTime();

		agencyMap = new HashMap<String, Agency>();
		List<Agency> agencyList = dao.query("select agency from Agency agency");
		for (Agency agency : agencyList) {
			agencyMap.put(agency.getName().trim(), agency);
		}
		
		System.out.println("initUnivMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	/**
	 * 初始化中间表中申请项目的成员信息
	 * @param dumpDate
	 */
	@SuppressWarnings("unchecked")
	public void initSinossMembers(String dumpDate){
		Long begin = System.currentTimeMillis();
		Map<String, String> smParameters = new HashMap<String, String>();
		smParameters.put("dumpDate", dumpDate);
		List<SinossMembers> sinossMembers = dao.query("select o from SinossMembers o where to_char(o.dumpDate,'yyyy-mm-dd') = :dumpDate and o.projectApplication.isAdded=1", smParameters);
		String projectIdString = null;
		for (SinossMembers sm : sinossMembers) {
			if (projectIdToSMembersMap == null) {
				projectIdToSMembersMap = new HashMap<String, List<SinossMembers>>();
			}
			projectIdString = sm.getProjectApplication().getId();
			List<SinossMembers> tempSMembers = projectIdToSMembersMap.get(projectIdString);
			if (tempSMembers == null) {
				tempSMembers = new ArrayList<SinossMembers>();
			}
			tempSMembers.add(sm);
			projectIdToSMembersMap.put(projectIdString, tempSMembers);
		}
		System.out.println("initSinossMembers completed! Use time : " + (System.currentTimeMillis() - begin) + "ms");		
	}
	
	/**
	 * 初始化中间表中申请项目的审核信息
	 */
	@SuppressWarnings("unchecked")
	public void initSinossCheckData(){
		Long begin = System.currentTimeMillis();
		List<SinossChecklogs> sinossCheckData = dao.query("select scl from SinossChecklogs scl where scl.isAdded=1 and scl.projectApplication is not null");
		String sProjectId = null;
		for (SinossChecklogs scl : sinossCheckData) {
			if (projectIdToSChecklogMap == null) {
				projectIdToSChecklogMap = new HashMap<String, List<SinossChecklogs>>();
			}
			sProjectId = scl.getProjectApplication().getId();
			List<SinossChecklogs> tempSinossChecklogs = projectIdToSChecklogMap.get(sProjectId);
			if (tempSinossChecklogs == null) {
				tempSinossChecklogs = new ArrayList<SinossChecklogs>();
			}
			tempSinossChecklogs.add(scl);
			projectIdToSChecklogMap.put(sProjectId, tempSinossChecklogs);
		}
		System.out.println("initSinossCheckData completed! Use time : " + (System.currentTimeMillis() - begin) + "ms");		
	}
	
	/**
	 * 从混乱的ID号中获取ID类型（咨询boss，要不要再项目成员表的证件类型里面添加一个台胞证）
	 * @author 2014-8-30
	 */
	public String checkIDType(String id){
		if (id == null) {
			return id;
		}
		if(id.matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")){
			return "身份证";
		} else if(id.contains("护照")){
			return "护照";
		}else if(id.contains("军官")){
			return "军官";
		}else {
			return "其它";
		}
	}
	
	/**
	 * 从混乱的称号里面判定是否是学生  (尽量匹配原则：)
	 * @author 2014-8-30
	 */
	public boolean checkStudentTitle(String str) throws Exception{	//"科长博士生  讲师（研究生）讲师/研究生  学生   学生/学生   大学生   学生/研究生  在读大学生 在校学生   在校学生/辅导员  MSW学生   讲师/学生办副主任  学生办副主任  五年制学生   法学研究生   博士/讲师......."	
		String str1 = str.replaceAll("\\s+", "");  
		String str2 = str1.replaceAll("[;；,，、。\\./~]+", "/"); 
		String str3 = str2.replaceAll("\\((.*?)\\)", "（$1）"); 
		String str4 = str3.replaceAll("\\（(.*?)\\）", ""); 
		
		if(str4.matches("^(.*)(学生|在读|高中生)$")){
			return true;
		}
		if(str4.matches("^博士生?|硕士生?|研究生|本科生$")){
			return true;
		}
		
		String[] strs = str4.split("/");
		for(String s :strs){
			if(s.matches("^学生$")){      //学生/研究生
				return true;
			}
			if(!s.matches("^(.*)(学生|在读|高中生)$")){    
				return false;
			}
		}
		
		return false;	
	}
	
	/**
	 * 通过一级学科获取学科门类  
	 * @author 2014-8-30 
	 */
	public String getSubjectType(String subject){
		if(subject.startsWith ("170")){
			return "地球科学";
		}else if(subject.startsWith("190")){
			return "心理学";
		}else if(subject.startsWith("630")){
			return "管理学";
		}else if(subject.startsWith("710")){
			return "马克思主义/思想政治教育";
		}else if(subject.startsWith("720")){
			if(subject.startsWith("72040")){
				return "逻辑学";
			}else return "哲学";
		}else if(subject.startsWith("730")){
			return "宗教学";
		}else if(subject.startsWith("740")){
			return "语言学";
		}else if (subject.startsWith("75011-44")){
			return "中国文学";
		}else if(subject.startsWith("75047-99")){
			return "外国文学";	
		}else if(subject.startsWith("760")){
			return "艺术学";
		}else if(subject.startsWith("770")){
			return "历史学";
		}else if(subject.startsWith("780")){
			return "考古学";
		}else if(subject.startsWith("790")){
			return "经济学";
		}else if(subject.startsWith("810")){
			return "政治学";
		}else if(subject.startsWith("820")){
			return "法学";
		}else if(subject.startsWith("840")){
			return "社会学";
		}else if(subject.startsWith("850")){
			return "民族学与文化学";
		}else if(subject.startsWith("860")){
			return "新闻学与传播学";
		}else if(subject.startsWith("870")){
			return "图书馆、情报与文献学";
		}else if(subject.startsWith("880")){
			return "教育学";
		}else if(subject.startsWith("890")){
			return "体育学";
		}else if(subject.startsWith("910")){
			return "统计学";
		}else if(subject.startsWith("GAT")){
			return "港澳台问题研究";
		}else if(subject.startsWith("GJW")){
			return "国际问题研究";
		}else if(subject.startsWith("zjxxk")){
			return "交叉学科/综合研究";
		}else 
			return "未知";
	}
	
	/**
	 * 规范化职称名:code/title
	 * @author 2014-8-30 
	 */
	public String regulateTitle(String memberTitle){
		if(memberTitle != null){
			memberTitle = memberTitle.replaceAll("\\s+", "").replaceAll("\\((.*?)\\)", "（$1）");
			String[] strs = memberTitle.split("/");
			String regTitle = "";
			for (int i=0; i<strs.length; i++) {
				if(strs[i].equals("讲师")) {
					regTitle = regTitle + ";" + "013/讲师（高校）";
				} else if(strs[i].matches("[0-9][0-9a-zA-Z]*")){//职称代码
					regTitle = regTitle + "; " + codeTocodetitle.get(strs[i]);
				} else if (titleTocodetitle.get(strs[i]) != null) { //职称名
					regTitle = regTitle + "; " + titleTocodetitle.get(strs[i]);
				} else {//不规范的职称
					regTitle = regTitle + "; " + strs[i];
				}
			}
			return regTitle = regTitle.substring(1).trim();
		}		
		return null;
	}
	
	/**
	 * 初始化职称代码 -> 职称名
	 * @throws Exception 
	 * @author 2014-8-30 
	 */
	public void initTitle() throws Exception{
		Date begin = new Date();
		
		if(reader == null){
//			String realPath = ServletActionContext.getServletContext().getRealPath("file");
			String realPath = ApplicationContainer.sc.getRealPath("file");
			realPath = realPath.replace('\\', '/');
			String destFolder = "/template/import/20140314_教育部社会科学研究管理平台数据交换职称代码.xls";
			String resultPath = realPath + destFolder;
			reader = new ExcelReader(resultPath);
		}
		reader.readSheet(0);	
		if (codeTocodetitle == null){
			codeTocodetitle = new HashMap<String,String>();
		}
		if (titleTocodetitle == null){
			titleTocodetitle = new HashMap<String,String>();
		}
		while (next(reader)) {
			codeTocodetitle.put(A, E);
			if (!(B.contains("讲师"))) {
				titleTocodetitle.put(B, E);
			} else {
				titleTocodetitle.put("讲师","013/讲师（高校）");
			}
		}
		reader = null;
		System.out.println("initTitle complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	/**
	 * 学位代码 -> 学位名  
	 * @author 2014-9-8 
	 */
	public String eduDegreeCodeTrans(String eduDegree){
		if(eduDegree == null) {
			return eduDegree;
		}
		eduDegree = eduDegree.trim();
		if(eduDegree.equals("1")){
			return "名誉博士";
		}else if(eduDegree.equals("2")){
			return "博士";
		}else if(eduDegree.equals("3")){
			return "硕士";
		}else if(eduDegree.equals("4")){
			return "学士";
		}else if(eduDegree.equals("999")){
			return "其他";
		}else{
			return "未知";
		} 		
	}
	
	/**
	 * 学历代码 -> 学历名  
	 * @author 2014-9-8 
	 */
	public String eduLevelCodeTrans(String eduLevel){		
		if (eduLevel == null) {
			return eduLevel;
		}
		if(eduLevel.equals("0")){
			return "博士研究生";
		}else if(eduLevel.equals("1")){
			return "硕士研究生";
		}else if(eduLevel.equals("2")){
			return "本科生";
		}else if(eduLevel.equals("3")){
			return "大专生";
		}else if(eduLevel.equals("4")){
			return "中专生";
		}else if(eduLevel.equals("5")){
			return "其他";
		}else return "未知";

	}
	
	/**
	 * 在数据自动入库过程中，根据以前的警告信息，以及当前遇到的警告情况，更新警告信息
	 * @param originExchangeNoteInfo
	 * @param exceptionName
	 * @param projectName
	 * @param errorInfo
	 * @return
	 */
	public String exchangeNoteInfo(String originExchangeNoteInfo, String exceptionName ,String projectName , String errorInfo){
		if (originExchangeNoteInfo == null) {
			return exceptionName + "_" + projectName + "_" + errorInfo;
		}else {
			return originExchangeNoteInfo + "; " + exceptionName + "_" + projectName + "_" + errorInfo;
		}
	}
	
	/**
	 * 申请项目审核状态 
	 * @param application
	 * @param finalStatus:依据自t_SinossProjectApplication表中的c_check_status字段
	 */
	public void AddProjectApplicationCheckLogs(SinossProjectApplication spa, ProjectApplication application, int finalStatus){
		if (application instanceof GeneralApplication) {
			application = (GeneralApplication)application;
		} else if (application instanceof InstpApplication) {
			application = (InstpApplication)application;
		} else if (application instanceof SpecialApplication) {
			application = (SpecialApplication)application;
		}
		SinossChecklogs sic = null;
		//部级以下审核不通过(1,3,5)
		int statusNumber = 0;
		int lastCheckStatus = 0;
		boolean hasCheckInfo = false;  //是否有审核记录
		SinossChecklogs[] lastSChecklogs = {null, null, null};//[0]：校级审核记录；[1]:主管部门(省级)审核记录；[2]:社科司(部级)审核记录
		//分别获取校级和省级最终审核记录（有可能先通过，后来又不通过）
		sinossTempChecklogs = projectIdToSChecklogMap.get(spa.getId());
		projectIdToSChecklogMap.remove(spa.getId());
		if (sinossTempChecklogs != null){
			for(SinossChecklogs sicheck : sinossTempChecklogs){
				statusNumber = sicheck.getCheckStatus();
				if (statusNumber == 2 || statusNumber ==3 ){ //校级审核
					lastSChecklogs[0] = (lastSChecklogs[0] != null && sicheck.getCheckDate().before(lastSChecklogs[0].getCheckDate())) ? lastSChecklogs[0]: sicheck;
					hasCheckInfo = true;
				}else if (statusNumber == 4 || statusNumber ==5) { //省级审核
					lastSChecklogs[1] = (lastSChecklogs[1] != null && sicheck.getCheckDate().before(lastSChecklogs[1].getCheckDate())) ? lastSChecklogs[1]: sicheck;
					hasCheckInfo = true;
				}else if (statusNumber == 6 || statusNumber ==7) { //省级审核
					lastSChecklogs[2] = (lastSChecklogs[2] != null && sicheck.getCheckDate().before(lastSChecklogs[2].getCheckDate())) ? lastSChecklogs[2]: sicheck;
					hasCheckInfo = true;
				}								
			}
		}
			
		application.setDeptInstAuditResult(2);//院系审核都通过
		application.setDeptInstAuditStatus(3);
		if (finalStatus < 3){
			application.setStatus(3);
		}
		
		if(!hasCheckInfo){
			return;
		}
		
		//获取最终审核状态
		if(lastSChecklogs[2] !=null){
			lastCheckStatus = lastSChecklogs[2].getCheckStatus();
		}else if(lastSChecklogs[1] != null){ //校级必然通过
			lastCheckStatus = lastSChecklogs[1].getCheckStatus();
		}else{
			lastCheckStatus = lastSChecklogs[0].getCheckStatus();
		}
		
		if(lastCheckStatus%2 ==1){  
			if(lastCheckStatus == 7 && finalStatus >=5){//社科司不通过，省级和校级通过
				sic = lastSChecklogs[2];
				application.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
				application.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
				application.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				application.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				application.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
				
				application.setProvinceAuditResult(2);
				application.setProvinceAuditStatus(3);
				application.setProvinceAuditDate(lastSChecklogs[1].getCheckDate());
				application.setProvinceAuditorAgency(universityFinder.getAgencyByName(lastSChecklogs[1].getChecker()));
				application.setProvinceAuditOpinion(lastSChecklogs[1].getCheckInfo());
				
				application.setMinistryAuditResult(1);
				application.setMinistryAuditStatus(3);
				application.setMinistryAuditDate(lastSChecklogs[2].getCheckDate());
				application.setMinistryAuditorAgency(universityFinder.getAgencyByName(lastSChecklogs[2].getChecker()));
				application.setMinistryAuditOpinion(lastSChecklogs[2].getCheckInfo());
			}
			if(lastCheckStatus == 5 && finalStatus >=4){//省级审核不通过，说明校级通过
				sic = lastSChecklogs[1];
				application.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
				application.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
				application.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				application.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				application.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
				
				if(finalStatus == 5){//省级不同意 -> 退回 -> 最终同意
					return;
				}
				application.setProvinceAuditResult(1);
				application.setProvinceAuditStatus(3);
				application.setProvinceAuditDate(lastSChecklogs[1].getCheckDate());
				application.setProvinceAuditorAgency(universityFinder.getAgencyByName(lastSChecklogs[1].getChecker()));
				application.setProvinceAuditOpinion(lastSChecklogs[1].getCheckInfo());				
			}
			if(lastCheckStatus ==3 && finalStatus >=3){//校级审核不通过
				sic = lastSChecklogs[0];
				if(finalStatus == 4){
					return;
				}
				if (finalStatus < 3){
					application.setStatus(3);
				}
				application.setUniversityAuditResult(1);
				application.setUniversityAuditStatus(3);
				application.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				application.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				application.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
			}
			application.setFinalAuditResult(1);
			application.setFinalAuditStatus(3);
			application.setFinalAuditDate(sic.getCheckDate());
			application.setFinalAuditorAgency(universityFinder.getUnivByName(sic.getChecker()));
			application.setFinalAuditOpinion(sic.getCheckInfo());
			return ;
		}
		
		//部级以下审核通过，查看部级审核结果
		if(lastCheckStatus%2 == 0){
			//校级必通过
			application.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
			application.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
			if(lastSChecklogs[0] != null){
				application.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				application.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				application.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
			}
			//省级审核通过
			if(lastCheckStatus == 4  && finalStatus >=4){
				application.setProvinceAuditResult(2);
				application.setProvinceAuditStatus(3);
				if(lastSChecklogs[1] != null) {
					application.setProvinceAuditDate(lastSChecklogs[1].getCheckDate());
					application.setProvinceAuditorAgency(universityFinder.getAgencyByName(lastSChecklogs[1].getChecker()));
					application.setProvinceAuditOpinion(lastSChecklogs[1].getCheckInfo());
				}
				return;
			}
			//社科司审核通过
			if(lastCheckStatus == 6 && finalStatus >=5){
				application.setProvinceAuditResult(2);
				application.setProvinceAuditStatus(3);
				if(lastSChecklogs[1] != null) {
					application.setProvinceAuditDate(lastSChecklogs[1].getCheckDate());
					application.setProvinceAuditorAgency(universityFinder.getAgencyByName(lastSChecklogs[1].getChecker()));
					application.setProvinceAuditOpinion(lastSChecklogs[1].getCheckInfo());
				}
				
				application.setMinistryAuditResult(2);
				application.setMinistryAuditStatus(3);
				if(lastSChecklogs[2] != null) {
					application.setMinistryAuditDate(lastSChecklogs[2].getCheckDate());
					application.setMinistryAuditorAgency(universityFinder.getAgencyByName(lastSChecklogs[2].getChecker()));
					application.setMinistryAuditOpinion(lastSChecklogs[2].getCheckInfo());
				}
			}
		}
		
		for(SinossChecklogs sicheck : sinossTempChecklogs){
			sicheck.setIsAdded(0);
		}
	}
	
	//判断是否只有高校这一类异常
	protected boolean onlyUnivException(Map<String, List<String[]>> exceptions){
		boolean result = false;
		if (exceptions.size() == 1 && exceptions.containsKey("找不到该项目对应的高校")) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 释放初始化时占据的内存
	 * @author 2014-8-30 
	 */
	public void freeMemory(){
		specialSubSubMap = null;
		sinossTempChecklogs = null;
		currentSinossMembers = null;
		projectIdToSChecklogMap = null;
		projectIdToSMembersMap = null;
		spa = null;
		generalMemberOrder = null;
		specialMemberOrder = null;
		instpMemberOrder = null;
		templist = null;
		codeTocodetitle = null;
		titleTocodetitle = null;
		tool.reset();
		generalProjectFinder.reset();  
		specialProjectFinder.reset();
		universityFinder.reset();
		departmentFinder.reset();
		univPersonFinder.reset();
		academicFinder.reset();
		productTypeNormalizer.reset();
		expertFinder.reset();
	}
		
	public int getTotalNum() {
		return totalNum;
	}
	
	public int getCurrentNum() {
		return currentNum;
	}
	
	public Map<String,List<String[]>> getIllegalException() {
		return illegalException;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsFinished() {
		return isFinished;
	}

	public int getTotalImportNum() {
		return totalImportNum;
	}
	
}
