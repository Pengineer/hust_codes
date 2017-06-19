package csdc.action.mobile.basis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.mobile.MobileAction;
import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.KeyTopic;
import csdc.bean.Nsfc;
import csdc.bean.Nssf;
import csdc.bean.NssfProjectApplication;
import csdc.bean.Person;
import csdc.bean.ProjectAnninspection;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.service.IKeyService;
import csdc.service.IMobileProjectService;
import csdc.service.IProductService;
import csdc.service.IProjectService;
import csdc.service.ext.IProjectExtService;
import csdc.tool.HqlTool;
import csdc.tool.bean.AccountType;

/**
 * mobile项目模块
 * @author fengcl
 */
@SuppressWarnings("unchecked")
public class MobileProjectAction extends MobileAction{

	private static final long serialVersionUID = -3402307435051238348L;
	private static final String PAGENAME1 = "mobileProjectPage";
	private static final String PAGENAME2 = "mobileProjectToDoPage";
	
	//公共部分（select）
	private static final String HQL4COMMON_APP = "select app.name, app.applicantName, app.id";//申报
	private static final String HQL4COMMON_GRA = "select gra.name, gra.applicantName, app.id";//立项
	private static final String HQL4COMMON_ANN = "select gra.name, gra.applicantName, app.id";//年检	
	private static final String HQL4COMMON_MID = "select gra.name, gra.applicantName, app.id";//中检
	private static final String HQL4COMMON_END = "select gra.name, gra.applicantName, app.id";//结项
	private static final String HQL4COMMON_VAR = "select gra.name, gra.applicantName, app.id";//变更
	
	//公共部分（参数）
		private static final String HQL4PARAM_APP = " so.name, app.year, app.agencyName, app.finalAuditResult, app.finalAuditDate,app.disciplineType";//申报
		private static final String HQL4PARAM_GRA = " so.name, app.year, uni.name, gra.status, gra.number, app.disciplineType";//立项
		private static final String HQL4PARAM_ANN = " so.name, app.year, uni.name, anni.finalAuditResult ,anni.finalAuditDate,app.disciplineType";//年检	
		private static final String HQL4PARAM_MID = " so.name, app.year, uni.name, midi.finalAuditResult, midi.finalAuditDate,app.disciplineType";//中检
		private static final String HQL4PARAM_END = " so.name, app.year, uni.name, endi.finalAuditResultEnd, endi.finalAuditDate,app.disciplineType";//结项
		private static final String HQL4PARAM_VAR = " so.name, app.year, uni.name, vari.finalAuditResult , vari.finalAuditDate,app.disciplineType";//变更
		
	
	//一般项目
	private static final String HQL4GENERAL_APP = " from GeneralApplication app left join app.university uni left join app.subtype so";
	private static final String HQL4GENERAL_GRA = " from GeneralGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4GENERAL_ANN = " from GeneralAnninspection anni, GeneralAnninspection all_anni, GeneralGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4GENERAL_MID = " from GeneralMidinspection midi, GeneralMidinspection all_midi, GeneralGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4GENERAL_END = " from GeneralEndinspection endi, GeneralEndinspection all_endi, GeneralGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4GENERAL_VAR = " from GeneralVariation vari, GeneralVariation all_vari, GeneralGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	//重大攻关项目
	private static final String HQL4KEY_SEL = "select tops.name, tops.applicantName, tops.id,uni.name, tops.topicSource,tops.year,uni.name, tops.finalAuditResult,tops.finalAuditDate,expUni.id from KeyTopic tops left join tops.university uni left join tops.expUniversity expUni where 1=1";//年度选题
	private static final String HQL4KEY_APP = " from KeyApplication app left join app.university uni left join app.researchType so";
	private static final String HQL4KEY_GRA = " from KeyGranted gra left join gra.application app left join gra.university uni left join app.researchType so";
	private static final String HQL4KEY_ANN = " from KeyAnninspection anni, KeyAnninspection all_anni, KeyGranted gra left join gra.application app left join gra.university uni left join app.researchType so";
	private static final String HQL4KEY_MID = " from KeyMidinspection midi, KeyMidinspection all_midi, KeyGranted gra left join gra.application app left join gra.university uni left join app.researchType so";
	private static final String HQL4KEY_END = " from KeyEndinspection endi, KeyEndinspection all_endi, KeyGranted gra left join gra.application app left join gra.university uni left join app.researchType so";
	private static final String HQL4KEY_VAR = " from KeyVariation vari, KeyVariation all_vari, KeyGranted gra left join gra.application app left join gra.university uni left join app.researchType so";
	//基地项目
	private static final String HQL4INSTP_APP = "  from InstpApplication app left join app.university uni left join app.subtype so";
	private static final String HQL4INSTP_GRA = "  from InstpGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4INSTP_ANN = "  from InstpAnninspection anni, InstpAnninspection all_anni, InstpGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4INSTP_MID = "  from InstpMidinspection midi, InstpMidinspection all_midi, InstpGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4INSTP_END = "  from InstpEndinspection endi, InstpEndinspection all_endi, InstpGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4INSTP_VAR = "  from InstpVariation vari, InstpVariation all_vari, InstpGranted gra left join gra.application app left join gra.university uni left outer join gra.subtype so";
	//后期资助项目
	private static final String HQL4POST_APP = "  from PostApplication app left join app.university uni left join app.subtype so";
	private static final String HQL4POST_GRA = "  from PostGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4POST_ANN = "  from PostAnninspection anni, PostAnninspection all_anni, PostGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4POST_END = "  from PostEndinspection endi, PostEndinspection all_endi, PostGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	private static final String HQL4POST_VAR = "  from PostVariation vari, PostVariation all_vari, PostGranted gra left join gra.application app left join gra.university uni left join gra.subtype so";
	//委托应急课题
	private static final String HQL4COMMITTEE_APP = " from EntrustApplication app left join app.university uni left join app.subtype so left join app.topic top ";//, EntrustMember mem
	private static final String HQL4COMMITTEE_GRA = " from EntrustGranted gra left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so left outer join app.topic top";
	private static final String HQL4COMMITTEE_END = " from EntrustEndinspection endi, EntrustEndinspection all_endi, EntrustGranted gra left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so";
	private static final String HQL4COMMITTEE_VAR = " from EntrustVariation all_vari, EntrustVariation vari left join vari.granted gra left join gra.application app left outer join app.subtype sub left join gra.university uni left outer join gra.subtype so ";
	
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IProjectExtService projectExtService;
	@Autowired
	private IMobileProjectService mobileProjectService;
	@Autowired
	private IKeyService keyService;
	@Autowired
	private IProductService productService;//成果管理接口
	private String projectType;//项目类型（General, Key, Institute, Post, Committee, Other）
	private String project_type; //项目类型（general, key, institute, post, committee）
	private String projectid;//项目id
	private String mainFlag;//首页进入列表参数
	private int type;//仅用于我的代办事项（1申报,2年检,3中检,4结项,5变更）
	
	//用于高级检索
	private String projName;//高项目名称
	private String projSubType;//项目类别
	private String projDirector;//项目申请人/负责人
	private String projUniversity;//项目依托高校
	
	//用于教师权限检索
	private String projYear;//项目年份
	
	private String projDiscipline;//项目年份
	
//	//用于其他项目高级检索
//	private String projNumber;//项目批准号
//	private String projApplyNumber;//项目申请代码
//	private String projUnit;//项目单位
//	private String projYear;//项目年份


	//隐藏类初始化法
	//GENERALITEMS：一般项目列表
	private static final ArrayList GENERALITEMS = new ArrayList();
	static{
		GENERALITEMS.add("申请数据#1");
		GENERALITEMS.add("立项数据#2");
		GENERALITEMS.add("年检数据#3");
		GENERALITEMS.add("中检数据#4");
		GENERALITEMS.add("结项数据#5");
		GENERALITEMS.add("变更数据#6");
	}
	//KEYITEMS：重大攻关项目列表
	private static final ArrayList KEYITEMS = new ArrayList();
	static{
		KEYITEMS.add("年度选题#1");
		KEYITEMS.add("投标数据#2");
		KEYITEMS.add("中标数据#3");
		KEYITEMS.add("年检数据#4");		
		KEYITEMS.add("中检数据#5");
		KEYITEMS.add("结项数据#6");
		KEYITEMS.add("变更数据#7");
	}
	//INSTPITEMS: 基地项目列表
	private static final ArrayList INSTITEMS = new ArrayList();
	static{
		INSTITEMS.add("申请数据#1");
		INSTITEMS.add("立项数据#2");
		INSTITEMS.add("年检数据#3");
		INSTITEMS.add("中检数据#4");
		INSTITEMS.add("结项数据#5");
		INSTITEMS.add("变更数据#6");
	}
	//POSTITEMS: 后期资助项目列表
	private static final ArrayList POSTITEMS = new ArrayList();
	static{
		POSTITEMS.add("申请数据#1");
		POSTITEMS.add("立项数据#2");
		POSTITEMS.add("年检数据#3");
		POSTITEMS.add("结项数据#4");
		POSTITEMS.add("变更数据#5");
	}
	//COMMITTEEITEMS: 委托应急课题列表
	private static final ArrayList COMMITTEEITEMS = new ArrayList();
	static{
		COMMITTEEITEMS.add("申请数据#1");
		COMMITTEEITEMS.add("立项数据#2");
		COMMITTEEITEMS.add("结项数据#3");
		COMMITTEEITEMS.add("变更数据#4");
	}
	//OTHERITEMS: 其他项目列表
	private static final ArrayList OTHERITEMS = new ArrayList();
	static{
		OTHERITEMS.add("国家自然科学基金项目#1");
		OTHERITEMS.add("国家社会科学基金项目#2");
	}
	private static final ArrayList GENERALTEACHERITEMS = new ArrayList();
	static{
		GENERALTEACHERITEMS.add("立项数据#2");
		
	}
	/**
	 * 客户端主列表条目
	 * @return
	 */
	public String fetchMenu(){
		AccountType accountType = loginer.getCurrentType();
		Map mainItems = new LinkedHashMap();
		mainItems.put("General", GENERALITEMS);
		mainItems.put("Key", KEYITEMS);
		mainItems.put("Inst", INSTITEMS);
		mainItems.put("Post", POSTITEMS);
		mainItems.put("Committee", COMMITTEEITEMS);							
		switch(accountType){
		case ADMINISTRATOR://管理员
			mainItems.put("Other", OTHERITEMS);
			break;
		case MINISTRY://部级管理人员
		case PROVINCE://省级管理人员
		case LOCAL_UNIVERSITY:
		case MINISTRY_UNIVERSITY://高校管理人员	
		case DEPARTMENT://院系管理人员	
		case INSTITUTE://基地管理人员	
		case EXPERT://外部专家	
		case STUDENT://学生
			break;
		case TEACHER://教师	
			mainItems.clear();
			mainItems.put("General", GENERALTEACHERITEMS);
			mainItems.put("Key", GENERALTEACHERITEMS);
			mainItems.put("Inst", GENERALTEACHERITEMS);
			mainItems.put("Post", GENERALTEACHERITEMS);
			mainItems.put("Committee", GENERALTEACHERITEMS);
			break;
		
		}
		jsonMap.put("listItem", mainItems);
		return SUCCESS;	
	}
	
	/**
	 * 初级检索列表
	 */
	public String simpleSearch(){
//		String userAgent = (String) session.get("user_agent");
//		if((userAgent.toLowerCase()).contains("darwin")){
//			System.out.println("iPhone或Mac机访问");
//		}
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		String hqlDiff4Manage = null, hqlDiff4Research = null;
		StringBuffer hql = new StringBuffer();
		HashMap parMap = new HashMap();
		AccountType account = loginer.getCurrentType();
		if(account.equals(AccountType.TEACHER)){
			if(projName.isEmpty()){ //校验用户名是否为空
				jsonMap.put("result", 0);
				return SUCCESS;
			}
		if(projectType.isEmpty()){//校验项目类型是否为空
				jsonMap.put("result", 1);
				return SUCCESS;
			}if(projYear.isEmpty()){//校验年份是否为空
				jsonMap.put("result", 2);
				return SUCCESS;
			}
			hql.append(HQL4COMMON_GRA).append(", app.disciplineType,").append(" uni.name");
			if("General".equals(projectType)){
				hql.append(HQL4GENERAL_GRA);
			}else if("Key".equals(projectType)){
				hql.append(HQL4KEY_GRA);
			}else if("Inst".equals(projectType)){
				hql.append(HQL4INSTP_GRA);
			}else if("Post".equals(projectType)){
				hql.append(HQL4POST_GRA);
			}else if("Committee".equals(projectType)){
				hql.append(HQL4COMMITTEE_GRA);
			}
			//hql.append(HQL4COMMON_GRA).append(", app.disciplineType,").append(HQL4GENERAL_GRA);
			hqlDiff4Manage = " where 1 = 1";
			//hqlDiff4Research = ", GeneralMember mem where mem.application.id = app.id";
			mobileProjectService.getTeacherSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, session, projName, projDirector,  projUniversity, projYear, projDiscipline);
		}else{
		if("General".equals(projectType)){//一般项目
			switch(listType){
			case 1://申报
				hql.append(HQL4COMMON_APP).append(", app.disciplineType,").append(HQL4PARAM_APP).append(HQL4GENERAL_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", GeneralMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(app.name) like :keyword or LOWER(app.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by app.name asc");//默认按照名称排序
				break;
			case 2://立项
				hql.append(HQL4COMMON_GRA).append(", app.disciplineType,").append(HQL4PARAM_GRA).append(HQL4GENERAL_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", GeneralMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 3://年检
				hql.append(HQL4COMMON_ANN).append(", app.disciplineType,").append(HQL4PARAM_ANN).append(HQL4GENERAL_ANN);
				hqlDiff4Manage = " where anni.granted.id = gra.id and all_anni.granted.id = gra.id";
				hqlDiff4Research = ", GeneralMember mem where anni.granted.id = gra.id and all_anni.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfAnn(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 4://中检
				hql.append(HQL4COMMON_MID).append(", app.disciplineType,").append(HQL4PARAM_MID).append(HQL4GENERAL_MID);
				hqlDiff4Manage = " where midi.granted.id = gra.id and all_midi.granted.id = gra.id";
				hqlDiff4Research = ", GeneralMember mem where midi.granted.id = gra.id and all_midi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfMid(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, midi.status, midi.file, midi.id, midi.finalAuditStatus, midi.finalAuditResult, midi.finalAuditDate, midi.finalAuditResult, midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 5://结项
				hql.append(HQL4COMMON_END).append(", app.disciplineType,").append(HQL4PARAM_END).append(HQL4GENERAL_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", GeneralMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, endi.status, endi.file, endi.id, endi.finalAuditStatus, endi.finalAuditResultEnd, endi.ministryAuditStatus, endi.ministryResultEnd, endi.reviewStatus, endi.reviewResult, endi.finalAuditDate, endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 6://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", GeneralMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", app.disciplineType,"+HQL4PARAM_VAR, HQL4GENERAL_VAR, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, gra.applicantId, gra.applicantName, uni.id, uni.name, so.name, app.disciplineType, app.year, vari.status, vari.file, vari.id, vari.finalAuditStatus,vari.finalAuditResult , vari.finalAuditDate, vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			}
		}else if("Key".equals(projectType)){//重大课题攻关项目
			switch(listType){
			case 1://年度选题
				AccountType accountType = loginer.getCurrentType();
				hql.append(HQL4KEY_SEL);
				hql.append(keyService.getKeyTopicSimpleSearchHQLAdd(accountType));
				//处理查询范围
				session.put("topicSelectionMap", parMap);
				hql.append(keyService.topicSelectionInSearch(loginer.getAccount()));
				parMap = (HashMap) session.get("topicSelectionMap");
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(tops.name) like :keyword or LOWER(tops.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by tops.name asc");//默认按照名称排序
				break;
			case 2://招标
				hql.append(HQL4COMMON_APP).append(", uni.name,").append(HQL4PARAM_APP).append(HQL4KEY_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", KeyMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(app.name) like :keyword or LOWER(app.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by app.name asc");//默认按照名称排序
				break;
			case 3://中标
				hql.append(HQL4COMMON_GRA).append(", uni.name,").append(HQL4PARAM_GRA).append(HQL4KEY_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", KeyMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 4://年检
            	hql.append(HQL4COMMON_ANN).append(", uni.name,").append(HQL4PARAM_ANN).append(HQL4KEY_ANN);
            	hqlDiff4Manage = " where anni.granted.id = gra.id and all_anni.granted.id = gra.id";
            	hqlDiff4Research = ", KeyMember mem where anni.granted.id = gra.id and all_anni.granted.id = gra.id and mem.application.id = app.id";
            	mobileProjectService.getSimpleSearchHqlAndMapOfAnn(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
            	if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
            	hql.append(" order by gra.name asc");//默认按照名称排序
            	break;	            	
			case 5://中检
				hql.append(HQL4COMMON_MID).append(", uni.name,").append(HQL4PARAM_MID).append(HQL4KEY_MID);
				hqlDiff4Manage = " where midi.granted.id = gra.id and all_midi.granted.id = gra.id";
				hqlDiff4Research = ", KeyMember mem where midi.granted.id = gra.id and all_midi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfMid(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, midi.status, midi.file, midi.id, midi.finalAuditStatus, midi.finalAuditResult, midi.finalAuditDate, midi.finalAuditResult, midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 6://结项
				hql.append(HQL4COMMON_END).append(", uni.name,").append(HQL4PARAM_END).append(HQL4KEY_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", KeyMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, endi.status, endi.file, endi.id, endi.finalAuditStatus, endi.finalAuditResultEnd, endi.ministryAuditStatus, endi.ministryResultEnd, endi.reviewStatus, endi.reviewResult, endi.finalAuditDate, endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 7://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", KeyMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", uni.name,"+HQL4PARAM_VAR, HQL4KEY_VAR, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, gra.applicantId, gra.applicantName, uni.id, uni.name, so.name, app.disciplineType, app.year, vari.status, vari.file, vari.id, vari.finalAuditStatus,vari.finalAuditResult , vari.finalAuditDate, vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			}
		}else if("Inst".equals(projectType)){//基地重大项目
			switch(listType){
			case 1://申报
				hql.append(HQL4COMMON_APP).append(", uni.name,").append(HQL4PARAM_APP).append(HQL4INSTP_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", InstpMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(app.name) like :keyword or LOWER(app.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by app.name asc");//默认按照名称排序
				break;
			case 2://立项
				hql.append(HQL4COMMON_GRA).append(", uni.name,").append(HQL4PARAM_GRA).append(HQL4INSTP_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", InstpMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 3://年检
				hql.append(HQL4COMMON_ANN).append(", uni.name,").append(HQL4PARAM_ANN).append(HQL4INSTP_ANN);
				hqlDiff4Manage = " where anni.granted.id = gra.id and all_anni.granted.id = gra.id";
				hqlDiff4Research = ", InstpMember mem where anni.granted.id = gra.id and all_anni.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfAnn(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 4://中检
				hql.append(HQL4COMMON_MID).append(", uni.name,").append(HQL4PARAM_MID).append(HQL4INSTP_MID);
				hqlDiff4Manage = " where midi.granted.id = gra.id and all_midi.granted.id = gra.id";
				hqlDiff4Research = ", InstpMember mem where midi.granted.id = gra.id and all_midi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfMid(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, midi.status, midi.file, midi.id, midi.finalAuditStatus, midi.finalAuditResult, midi.finalAuditDate, midi.finalAuditResult, midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 5://结项
				hql.append(HQL4COMMON_END).append(", uni.name,").append(HQL4PARAM_END).append(HQL4INSTP_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", InstpMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, endi.status, endi.file, endi.id, endi.finalAuditStatus, endi.finalAuditResultEnd, endi.ministryAuditStatus, endi.ministryResultEnd, endi.reviewStatus, endi.reviewResult, endi.finalAuditDate, endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 6://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", InstpMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", uni.name,"+HQL4PARAM_VAR, HQL4INSTP_VAR, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, gra.applicantId, gra.applicantName, uni.id, uni.name, so.name, app.disciplineType, app.year, vari.status, vari.file, vari.id, vari.finalAuditStatus,vari.finalAuditResult , vari.finalAuditDate, vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			}
		}else if("Post".equals(projectType)){//后期资助项目
			switch(listType){
			case 1://申报
				hql.append(HQL4COMMON_APP).append(", app.disciplineType,").append(HQL4PARAM_APP).append(HQL4POST_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", PostMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(app.name) like :keyword or LOWER(app.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by app.name asc");//默认按照名称排序
				break;
			case 2://立项
				hql.append(HQL4COMMON_GRA).append(", app.disciplineType,").append(HQL4PARAM_GRA).append(HQL4POST_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", PostMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 3://年检
				hql.append(HQL4COMMON_ANN).append(", app.disciplineType,").append(HQL4PARAM_ANN).append(HQL4POST_ANN);
				hqlDiff4Manage = " where anni.granted.id = gra.id and all_anni.granted.id = gra.id";
				hqlDiff4Research = ", PostMember mem where anni.granted.id = gra.id and all_anni.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfAnn(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 4://结项
				hql.append(HQL4COMMON_END).append(", app.disciplineType,").append(HQL4PARAM_END).append(HQL4POST_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", PostMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, endi.status, endi.file, endi.id, endi.finalAuditStatus, endi.finalAuditResultEnd, endi.ministryAuditStatus, endi.ministryResultEnd, endi.reviewStatus, endi.reviewResult, endi.finalAuditDate, endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 5://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", PostMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", app.disciplineType,"+HQL4PARAM_VAR, HQL4POST_VAR, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(app.disciplineType) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, gra.applicantId, gra.applicantName, uni.id, uni.name, so.name, app.disciplineType, app.year, vari.status, vari.file, vari.id, vari.finalAuditStatus,vari.finalAuditResult , vari.finalAuditDate, vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			}	
		}else if("Committee".equals(projectType)){
			switch(listType){
			case 1://申报
				hql.append(HQL4COMMON_APP).append(", so.name,").append(HQL4PARAM_APP).append(HQL4COMMITTEE_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", CommitteeMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(app.name) like :keyword or LOWER(app.applicantName) like :keyword or LOWER(so.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by app.name asc");//默认按照名称排序
				break;
			case 2://立项
				hql.append(HQL4COMMON_GRA).append(", so.name,").append(HQL4PARAM_GRA).append(HQL4COMMITTEE_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", CommitteeMember mem where mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(so.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 3://结项
				hql.append(HQL4COMMON_END).append(", so.name,").append(HQL4PARAM_END).append(HQL4COMMITTEE_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", CommitteeMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(so.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, endi.status, endi.file, endi.id, endi.finalAuditStatus, endi.finalAuditResultEnd, endi.ministryAuditStatus, endi.ministryResultEnd, endi.reviewStatus, endi.reviewResult, endi.finalAuditDate, endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			case 4://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", CommitteeMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getSimpleSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", so.name,"+HQL4PARAM_VAR, HQL4COMMITTEE_VAR, hqlDiff4Manage, hqlDiff4Research, session, keyword);
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword or LOWER(so.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" group by app.id, gra.id, gra.name, gra.applicantId, gra.applicantName, uni.id, uni.name, so.name, app.disciplineType, app.year, vari.status, vari.file, vari.id, vari.finalAuditStatus,vari.finalAuditResult , vari.finalAuditDate, vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
				hql.append(" order by gra.name asc");//默认按照名称排序
				break;
			}
		}else if("Other".equals(projectType)){
			AccountType accountType = loginer.getCurrentType();
			switch(listType){
			case 1://国家自然科学基金项目
				hql.append("select ns.name, ns.applicant, ns.id, ns.unit,ns.number, ns.year,ns.unit,ns.grantType,ns.startDate,ns.approvedFee from Nsfc ns ");
				if(accountType.equals(AccountType.ADMINISTRATOR)){
					hql.append(" where 1 = 1");
				}
				else hql.append(" where 1 = 0");
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(ns.name) like :keyword or LOWER(ns.applicant) like :keyword or LOWER(ns.unit) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			case 21://国家社会科学基金项目申报数据
				hql.append("select ns.name, ns.applicant, ns.id, ns.unit,ns.province,ns.year,ns.unit,ns.type,ns.firstAuditDate,ns.discipline from NssfProjectApplication ns ");
				if(accountType.equals(AccountType.ADMINISTRATOR)){
					hql.append(" where 1 = 1");
				}
				else hql.append(" where 1 = 0");
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(ns.name) like :keyword or LOWER(ns.applicant) like :keyword or LOWER(ns.unit) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			case 2://国家社会科学基金项目
				hql.append("select ns.name, ns.applicant, ns.id, ns.unit from Nssf ns ");
				if(accountType.equals(AccountType.ADMINISTRATOR)){
					hql.append(" where 1 = 1");
				}
				else hql.append(" where 1 = 0");
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(ns.name) like :keyword or LOWER(ns.applicant) like :keyword or LOWER(ns.unit) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			case 22://国家社会科学基金项目立项数据
				hql.append("select ns.name, ns.applicant, ns.id, ns.unit ,ns.type,ns.productLevel,ns.unit,ns.status,ns.startDate,ns.disciplineType from Nssf ns ");
				if(accountType.equals(AccountType.ADMINISTRATOR)){
					hql.append(" where 1 = 1");
				}
				else hql.append(" where 1 = 0");
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(ns.name) like :keyword or LOWER(ns.applicant) like :keyword or LOWER(ns.unit) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			}
			hql.append(" order by ns.name asc");//默认按照姓名排序
		}
		}
		//调用公共接口
		search(hql.toString(), parMap);
		return SUCCESS;
	}
	
	
	/**
	 * 高级检索列表
	 */
	public String advSearch(){
		String hqlDiff4Manage = null, hqlDiff4Research = null;
		StringBuffer hql = new StringBuffer();
		HashMap parMap = new HashMap();
		if("General".equals(projectType)){//一般项目
			switch(listType){
			case 1://申报
				hql.append(HQL4COMMON_APP).append(", app.disciplineType,").append(HQL4PARAM_APP).append(HQL4GENERAL_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", GeneralMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 2://立项
				hql.append(HQL4COMMON_GRA).append(", app.disciplineType,").append(HQL4PARAM_GRA).append(HQL4GENERAL_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", GeneralMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 3://年检
				hql.append(HQL4COMMON_ANN).append(", app.disciplineType,").append(HQL4PARAM_ANN).append(HQL4GENERAL_ANN);
				hqlDiff4Manage = " where anni.granted.id = gra.id and all_anni.granted.id = gra.id";
				hqlDiff4Research = ", GeneralMember mem where anni.granted.id = gra.id and all_anni.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfAnn(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 4://中检
				hql.append(HQL4COMMON_MID).append(", app.disciplineType,").append(HQL4PARAM_MID).append(HQL4GENERAL_MID);
				hqlDiff4Manage = " where midi.granted.id = gra.id and all_midi.granted.id = gra.id";
				hqlDiff4Research = ", GeneralMember mem where midi.granted.id = gra.id and all_midi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfMid(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 5://结项
				hql.append(HQL4COMMON_END).append(", app.disciplineType,").append(HQL4PARAM_END).append(HQL4GENERAL_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", GeneralMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 6://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", GeneralMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", app.disciplineType,"+HQL4PARAM_VAR, HQL4GENERAL_VAR, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			}
		}else if("Key".equals(projectType)){//重大攻关
			switch(listType){
			case 1://年度选题
				AccountType accountType = loginer.getCurrentType();
				hql.append(HQL4KEY_SEL);
				hql.append(keyService.getKeyTopicSimpleSearchHQLAdd(accountType));
				//处理查询范围
				session.put("topicSelectionMap", parMap);
				hql.append(keyService.topicSelectionInSearch(loginer.getAccount()));
				parMap = (HashMap) session.get("topicSelectionMap");
				//高级检索条件
				if(null != projName && !projName.isEmpty()){
					hql.append(" and LOWER(tops.name) like :topsName");
					parMap.put("topsName", "%" + projName.toLowerCase() + "%");
				}
				if(null != projUniversity && !projUniversity.isEmpty()){
					hql.append(" and LOWER(uni.name) like :university");
					parMap.put("university", "%" + projUniversity.toLowerCase() + "%");
				}
				hql.append(" order by tops.name ");//默认按照一般名称排序
				break;
			case 2://招标
				hql.append(HQL4COMMON_APP).append(", uni.name,").append(HQL4PARAM_APP).append(HQL4KEY_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", KeyMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 3://中标
				hql.append(HQL4COMMON_GRA).append(", uni.name,").append(HQL4PARAM_GRA).append(HQL4KEY_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", KeyMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 4://年检
				hql.append(HQL4COMMON_ANN).append(", uni.name,").append(HQL4PARAM_ANN).append(HQL4KEY_ANN);
				hqlDiff4Manage = " where anni.granted.id = gra.id and all_anni.granted.id = gra.id";
				hqlDiff4Research = ", KeyMember mem where anni.granted.id = gra.id and all_anni.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfAnn(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 5://中检
				hql.append(HQL4COMMON_MID).append(", uni.name,").append(HQL4PARAM_MID).append(HQL4KEY_MID);
				hqlDiff4Manage = " where midi.granted.id = gra.id and all_midi.granted.id = gra.id";
				hqlDiff4Research = ", KeyMember mem where midi.granted.id = gra.id and all_midi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfMid(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 6://结项
				hql.append(HQL4COMMON_END).append(", uni.name,").append(HQL4PARAM_END).append(HQL4KEY_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", KeyMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 7://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", KeyMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", uni.name,"+HQL4PARAM_VAR,  HQL4KEY_VAR, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			}
		}else if("Inst".equals(projectType)){//基地
			switch(listType){
			case 1://申报
				hql.append(HQL4COMMON_APP).append(", uni.name,").append(HQL4PARAM_APP).append(HQL4INSTP_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", InstpMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 2://立项
				hql.append(HQL4COMMON_GRA).append(", uni.name,").append(HQL4PARAM_GRA).append(HQL4INSTP_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", InstpMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 3://年检
				hql.append(HQL4COMMON_ANN).append(", uni.name,").append(HQL4PARAM_ANN).append(HQL4INSTP_ANN);
				hqlDiff4Manage = " where anni.granted.id = gra.id and all_anni.granted.id = gra.id";
				hqlDiff4Research = ", InstpMember mem where anni.granted.id = gra.id and all_anni.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfAnn(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 4://中检
				hql.append(HQL4COMMON_MID).append(", uni.name,").append(HQL4PARAM_MID).append(HQL4INSTP_MID);
				hqlDiff4Manage = " where midi.granted.id = gra.id and all_midi.granted.id = gra.id";
				hqlDiff4Research = ", InstpMember mem where midi.granted.id = gra.id and all_midi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfMid(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 5://结项
				hql.append(HQL4COMMON_END).append(", uni.name,").append(HQL4PARAM_END).append(HQL4INSTP_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", InstpMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 6://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", InstpMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", uni.name,"+HQL4PARAM_VAR, HQL4INSTP_VAR, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			}
		}else if("Post".equals(projectType)){//后期资助
			switch(listType){
			case 1://申报
				hql.append(HQL4COMMON_APP).append(", app.disciplineType,").append(HQL4PARAM_APP).append(HQL4POST_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", PostMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 2://立项
				hql.append(HQL4COMMON_GRA).append(", app.disciplineType,").append(HQL4PARAM_GRA).append(HQL4POST_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", PostMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 3://年检
				hql.append(HQL4COMMON_ANN).append(", app.disciplineType,").append(HQL4PARAM_ANN).append(HQL4POST_ANN);
				hqlDiff4Manage = " where anni.granted.id = gra.id and all_anni.granted.id = gra.id";
				hqlDiff4Research = ", PostMember mem where anni.granted.id = gra.id and all_anni.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfAnn(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 4://结项
				hql.append(HQL4COMMON_END).append(", app.disciplineType,").append(HQL4PARAM_END).append(HQL4POST_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", PostMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 5://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", PostMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", app.disciplineType,"+HQL4PARAM_VAR, HQL4POST_VAR, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			}
		}else if("Committee".equals(projectType)){//委托应急课题
			switch(listType){
			case 1://申报
				hql.append(HQL4COMMON_APP).append(", so.name,").append(HQL4PARAM_APP).append(HQL4COMMITTEE_APP);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", CommitteeMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfApp(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 2://立项
				hql.append(HQL4COMMON_GRA).append(", so.name,").append(HQL4PARAM_GRA).append(HQL4COMMITTEE_GRA);
				hqlDiff4Manage = " where 1 = 1";
				hqlDiff4Research = ", CommitteeMember mem where mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfGra(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 3://结项
				hql.append(HQL4COMMON_END).append(", so.name,").append(HQL4PARAM_END).append(HQL4COMMITTEE_END);
				hqlDiff4Manage = " where endi.granted.id = gra.id and all_endi.granted.id = gra.id";
				hqlDiff4Research = ", CommitteeMember mem where endi.granted.id = gra.id and all_endi.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfEnd(hql, parMap, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			case 4://变更
				hqlDiff4Manage = " where vari.granted.id = gra.id and all_vari.granted.id = gra.id";
				hqlDiff4Research = ", CommitteeMember mem where vari.granted.id = gra.id and all_vari.granted.id = gra.id and mem.application.id = app.id";
				mobileProjectService.getAdvSearchHqlAndMapOfVar(hql, parMap, HQL4COMMON_VAR+", so.name,"+HQL4PARAM_VAR, HQL4COMMITTEE_VAR, hqlDiff4Manage, hqlDiff4Research, session, projName, projSubType, projDirector, projUniversity);
				break;
			}
		}else if("Other".equals(projectType)){
			AccountType accountType = loginer.getCurrentType();
			switch(listType){
			case 1://国家自然科学基金项目
				hql.append("select ns.name, ns.applicant, ns.id, ns.unit,ns.number, ns.year,ns.approvedFee,ns.grantType,ns.startDate,ns.university from Nsfc ns ");
				if(accountType.equals(AccountType.ADMINISTRATOR)){
					hql.append(" where 1 = 1");
				}
				else hql.append(" where 1 = 0");
				break;
			case 2://国家社会科学基金项目
				hql.append("select ns.name, ns.applicant, ns.id, ns.unit from Nssf ns ");
				if(accountType.equals(AccountType.ADMINISTRATOR)){
					hql.append(" where 1 = 1");
				}
				else hql.append(" where 1 = 0");
				break;
			case 21://国家社会科学基金项目申报项目
				hql.append("select ns.name, ns.applicant, ns.id, ns.unit,ns.university,ns.year,ns.province,ns.type,ns.firstAuditDate,ns.discipline from NssfProjectApplication ns ");
				if(accountType.equals(AccountType.ADMINISTRATOR)){
					hql.append(" where 1 = 1");
				}
				else hql.append(" where 1 = 0");
				break;
			case 22://国家社会科学基金项目立项项目
				hql.append("select ns.name, ns.applicant, ns.id, ns.unit,ns.number,ns.productLevel,ns.type,ns.status,ns.startDate,ns.disciplineType from Nssf ns ");
				if(accountType.equals(AccountType.ADMINISTRATOR)){
					hql.append(" where 1 = 1");
				}
				else hql.append(" where 1 = 0");
				break;
			}
			//处理高级检索条件
			if(null != projName && !projName.isEmpty()){
				hql.append(" and LOWER(ns.name) like :projectName ");
				parMap.put("projectName", "%" + projName.toLowerCase() + "%");
			}
//			if(null != projSubType && !projSubType.equals("--请选择--")){
//				hql.append(" ");
//			}
			if(null != projDirector && !projDirector.isEmpty()){
				hql.append(" and LOWER(ns.applicant) like :applicant ");
				parMap.put("applicant", "%" + projDirector.toLowerCase() + "%");
			}
			if(null != projUniversity && !projUniversity.isEmpty()){
				hql.append(" and LOWER(ns.university) like :university ");
				parMap.put("university", "%" + projUniversity.toLowerCase() + "%");
			}
			hql.append(" order by ns.name asc");//默认按照姓名排序
		}
		//调用公共接口
		search(hql.toString(), parMap);
		return SUCCESS;
	}
	
	/**
	 * 详情查看
	 */
	public String view(){
		if("Key".equals(projectType) && listType == 1){//重大攻关项目的选题数据列表的查看
			Map topicSelectDataMap = new HashMap();//选题数据
			KeyTopic topicSelection = (KeyTopic) dao.query(KeyTopic.class, entityId);
			if (null != topicSelection) {
				String topicSource = "";
				if (null != topicSelection.getTopicSource()) {
					if(topicSelection.getTopicSource() == 0){
						topicSource = "教育部";
					}else if(topicSelection.getTopicSource() == 1){
						topicSource = "高校";
						String universityId = (null != topicSelection.getUniversity()) ? topicSelection.getUniversity().getId() : "";//选题所属高校id
						String projectId = (null != topicSelection.getProject()) ? topicSelection.getProject().getId() : "";//选题所属项目id
						Agency university = (Agency) dao.query(Agency.class, universityId);
						ProjectGranted project= (ProjectGranted) dao.query(ProjectGranted.class, projectId);
						topicSelectDataMap.put("sourceProject", project.getName());//来源项目
						topicSelectDataMap.put("sourceProjectType", (project.getProjectType().equals("general")) ? "一般项目" : ((project.getProjectType().equals("instp")) ? "基地项目" : ""));//项目类型
						topicSelectDataMap.put("universityName", (null != university) ? university.getName() : "");//选题所属高校，依托高校
						
					}else {
						topicSource = "专家";
						topicSelectDataMap.put("applicantName", (null != topicSelection.getApplicantName()) ? topicSelection.getApplicantName() : "");//申请人
						
						//申请人类型
						int applicantType = (null != topicSelection.getApplicantType()) ? topicSelection.getApplicantType() : 0;
						if(applicantType == 1){
							topicSelectDataMap.put("applicantType", "教师");
						}else if(applicantType == 2){
							topicSelectDataMap.put("applicantType", "专家");
						}else{
							topicSelectDataMap.put("applicantType", "未知");
						}
					}
				}
				topicSelectDataMap.put("topicSource", topicSource);//课题来源
				
				topicSelectDataMap.put("projectStatus",(null!=topicSelection)? endSwitch(topicSelection.getFinalAuditResult()):"");//选题结果
				topicSelectDataMap.put("topicName", (null != topicSelection.getName()) ? topicSelection.getName() : "");//课题名称
				topicSelectDataMap.put("topicYear", (null != topicSelection.getYear()) ? topicSelection.getYear().toString() : "");//选题年份
				topicSelectDataMap.put("summary", (null != topicSelection.getSummary()) ? topicSelection.getSummary() : "");//简要论证
				
				topicSelectDataMap.put("deptResult", (null != topicSelection) ? endSwitch(topicSelection.getDeptInstAuditResult()): "");
				topicSelectDataMap.put("uniResult", (null != topicSelection) ? endSwitch(topicSelection.getUniversityAuditResult()): "");
				topicSelectDataMap.put("deptName", (null != topicSelection.getDeptInstAuditorName()) ? topicSelection.getDeptInstAuditorName(): "");
				topicSelectDataMap.put("uniName", (null != topicSelection.getUniversityAuditorName()) ? topicSelection.getUniversityAuditorName(): "");
				topicSelectDataMap.put("finalAuditDate", (null != topicSelection.getFinalAuditDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(topicSelection.getFinalAuditDate()): "");
				topicSelectDataMap.put("finalAuditResult", (null != topicSelection) ? topicSelection.getFinalAuditResult(): "");

			}
			jsonMap.put("topicSelectList", topicSelectDataMap);	
			return SUCCESS;
		}else if("Other".equals(projectType)){
			Map otherProjectDataMap = new HashMap();//其他项目数据
			switch(listType){
			case 1:// 国家自然科学基金项目
				Nsfc nsfc = dao.query(Nsfc.class , entityId);			
				otherProjectDataMap.put("number" , (null != nsfc && null != nsfc.getNumber()) ? nsfc.getNumber() : "");//项目批准号
				otherProjectDataMap.put("applyNumber" , (null != nsfc && null != nsfc.getApplyNumber()) ? nsfc.getApplyNumber() : "");///申请代码
				otherProjectDataMap.put("projectName" , (null != nsfc && null != nsfc.getName()) ? nsfc.getName() : "");//项目名称
				otherProjectDataMap.put("directorName" , (null != nsfc && null != nsfc.getApplicant()) ? nsfc.getApplicant() : "");//项目负责人
				otherProjectDataMap.put("unit" , (null != nsfc && null != nsfc.getUnit()) ? nsfc.getUnit() : "");//依托单位
				otherProjectDataMap.put("approvedFee" , (null != nsfc && null != nsfc.getApprovedFee()) ? nsfc.getApprovedFee().toString() : "");//批准金额
				otherProjectDataMap.put("startDate" , (null != nsfc && null != nsfc.getStartDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(nsfc.getStartDate()).toString() : "");//项目开始年月
				otherProjectDataMap.put("endDate" , (null != nsfc && null != nsfc.getEndDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(nsfc.getEndDate()).toString() : "");//项目结束年月
				otherProjectDataMap.put("year" , (null != nsfc && null != nsfc.getYear()) ? nsfc.getYear().toString() : "");//所属年份
				otherProjectDataMap.put("grantType" , (null != nsfc && null != nsfc.getGrantType()) ? nsfc.getGrantType().toString() : "");//资助类别
				int isDupCheckGeneral = nsfc.getIsDupCheckGeneral();//是否进行一般项目重项检查：1是，0否
				if(isDupCheckGeneral == 1){
					otherProjectDataMap.put("isDupCheckGeneral" , "是");
				}
				else if(isDupCheckGeneral == 0){
					otherProjectDataMap.put("isDupCheckGeneral" , "否");
				}
				else otherProjectDataMap.put("isDupCheckGeneral" , "");
				break;
				case 2://国家社会科学基金项目
				Nssf nssf1 = dao.query(Nssf.class, entityId);
				otherProjectDataMap.put("number" , (null != nssf1 && null != nssf1.getNumber()) ? nssf1.getNumber() : "");//项目批准号				
				otherProjectDataMap.put("projectName" , (null != nssf1 && null != nssf1.getName()) ? nssf1.getName() : "");//项目名称
				otherProjectDataMap.put("directorName" , (null != nssf1 && null != nssf1.getApplicant()) ? nssf1.getApplicant() : "");//项目负责人
				otherProjectDataMap.put("unit" , (null != nssf1 && null != nssf1.getUnit()) ? nssf1.getUnit() : "");//工作单位				
				otherProjectDataMap.put("startDate" , (null != nssf1 && null != nssf1.getStartDate()) ? nssf1.getStartDate().toString() : "");//项目开始年月
				otherProjectDataMap.put("endDate" , (null != nssf1 && null != nssf1.getEndDate()) ? nssf1.getEndDate().toString() : "");//项目结束年月
				otherProjectDataMap.put("type" , (null != nssf1 && null != nssf1.getType()) ? nssf1.getType() : "");//项目类别
				otherProjectDataMap.put("disciplineType" , (null != nssf1 && null != nssf1.getDisciplineType()) ? nssf1.getDisciplineType() : "");//学科分类
				otherProjectDataMap.put("specialityTitle" , (null != nssf1 && null != nssf1.getSpecialityTitle()) ? nssf1.getSpecialityTitle() : "");//专业职务
				otherProjectDataMap.put("province" , (null != nssf1 && null != nssf1.getProvince()) ? nssf1.getProvince() : "");//所在省区市
				otherProjectDataMap.put("productName" , (null != nssf1 && null != nssf1.getProductName()) ? nssf1.getProductName() : "");//成果名称
				otherProjectDataMap.put("productType" , (null != nssf1 && null != nssf1.getProductType()) ? nssf1.getProductType() : "");//成果形式
				otherProjectDataMap.put("productLevel" , (null != nssf1 && null != nssf1.getProductLevel()) ? nssf1.getProductLevel() : "");//成果等级
				otherProjectDataMap.put("certificate" , (null != nssf1 && null != nssf1.getCertificate()) ? nssf1.getCertificate() : "");//结项证书号
				otherProjectDataMap.put("press" , (null != nssf1 && null != nssf1.getPress()) ? nssf1.getPress() : "");//出版社
				otherProjectDataMap.put("publishDate" , (null != nssf1 && null != nssf1.getPublishDate()) ? nssf1.getPublishDate().toString() : "");//出版时间
				otherProjectDataMap.put("author" , (null != nssf1 && null != nssf1.getAuthor()) ? nssf1.getAuthor() : "");//作者
				otherProjectDataMap.put("prizeObtained" , (null != nssf1 && null != nssf1.getPrizeObtained()) ? nssf1.getPrizeObtained() : "");//获奖情况
				Integer status1 = nssf1.getStatus();//项目状态：0默认，1在研，2已结项，3已中止，4已撤项
				status1 += 1;
				if(status1<6&&status1>0){
					switch(status1){
					case 1:
						otherProjectDataMap.put("status" , "默认");
						break;
					case 2:
						otherProjectDataMap.put("status" , "在研");
						break;
					case 3:
						otherProjectDataMap.put("status" , "已结项");
						break;
					case 4:
						otherProjectDataMap.put("status" , "已中止");
						break;
					case 5:
						otherProjectDataMap.put("status" , "已撤项");
						break;
					}					
				}
				else otherProjectDataMap.put("status" , "");break;
			case 21://国家社会科学基金项目
				NssfProjectApplication nssfp = dao.query(NssfProjectApplication.class, entityId);
				otherProjectDataMap.put("projectName" , (null != nssfp && null != nssfp.getName()) ? nssfp.getName() : "");//项目名称
				otherProjectDataMap.put("directorName" , (null != nssfp && null != nssfp.getApplicant()) ? nssfp.getApplicant() : "");//项目负责人
				otherProjectDataMap.put("unit" , (null != nssfp && null != nssfp.getUnit()) ? nssfp.getUnit() : "");//工作单位				
				otherProjectDataMap.put("startDate" , (null != nssfp && null != nssfp.getFirstAuditDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(nssfp.getFirstAuditDate()).toString() : "");//项目开始年月
				otherProjectDataMap.put("type" , (null != nssfp && null != nssfp.getType()) ? nssfp.getType() : "");//项目类别
				otherProjectDataMap.put("disciplineType" , (null != nssfp && null != nssfp.getDiscipline()) ? nssfp.getDiscipline() : "");//学科分类
				otherProjectDataMap.put("province" , (null != nssfp && null != nssfp.getProvince()) ? nssfp.getProvince() : "");//所在省区市
				otherProjectDataMap.put("productName" , (null != nssfp && null != nssfp.getProductName()) ? nssfp.getProductName() : "");//成果名称
				otherProjectDataMap.put("university" , (null != nssfp && null != nssfp.getUniversity()) ? nssfp.getUniversity() : "");//依托高校
				otherProjectDataMap.put("year" , (null != nssfp && null != nssfp.getYear()) ? nssfp.getYear() : "");	
				String projectStatus = "";
				int isDupCheck = nssfp.getIsDupCheckGeneral();//是否进行一般项目重项检查：1是，0否
				if(isDupCheck == 1){
					otherProjectDataMap.put("isDupCheckGeneral" , "是");
				}
				else if(isDupCheck == 0){
					otherProjectDataMap.put("isDupCheckGeneral" , "否");
				}
				else otherProjectDataMap.put("isDupCheckGeneral" , "");
				break;
			case 22:
				Nssf nssf = dao.query(Nssf.class, entityId);
				otherProjectDataMap.put("number" , (null != nssf && null != nssf.getNumber()) ? nssf.getNumber() : "");//项目批准号				
				otherProjectDataMap.put("projectName" , (null != nssf && null != nssf.getName()) ? nssf.getName() : "");//项目名称
				otherProjectDataMap.put("directorName" , (null != nssf && null != nssf.getApplicant()) ? nssf.getApplicant() : "");//项目负责人
				otherProjectDataMap.put("unit" , (null != nssf && null != nssf.getUnit()) ? nssf.getUnit() : "");//工作单位				
				otherProjectDataMap.put("startDate" , (null != nssf && null != nssf.getStartDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(nssf.getStartDate()).toString() : "");//项目开始年月
				otherProjectDataMap.put("endDate" , (null != nssf && null != nssf.getEndDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(nssf.getEndDate()).toString() : "");//项目结束年月
				otherProjectDataMap.put("type" , (null != nssf && null != nssf.getType()) ? nssf.getType() : "");//项目类别
				otherProjectDataMap.put("disciplineType" , (null != nssf && null != nssf.getDisciplineType()) ? nssf.getDisciplineType() : "");//学科分类
				otherProjectDataMap.put("specialityTitle" , (null != nssf && null != nssf.getSpecialityTitle()) ? nssf.getSpecialityTitle() : "");//专业职务
				otherProjectDataMap.put("province" , (null != nssf && null != nssf.getProvince()) ? nssf.getProvince() : "");//所在省区市
				otherProjectDataMap.put("productName" , (null != nssf && null != nssf.getProductName()) ? nssf.getProductName() : "");//成果名称
				otherProjectDataMap.put("productType" , (null != nssf && null != nssf.getProductType()) ? nssf.getProductType() : "");//成果形式
				otherProjectDataMap.put("productLevel" , (null != nssf && null != nssf.getProductLevel()) ? nssf.getProductLevel() : "");//成果等级
				otherProjectDataMap.put("certificate" , (null != nssf && null != nssf.getCertificate()) ? nssf.getCertificate() : "");//结项证书号
				otherProjectDataMap.put("press" , (null != nssf && null != nssf.getPress()) ? nssf.getPress() : "");//出版社
				otherProjectDataMap.put("publishDate" , (null != nssf && null != nssf.getPublishDate()) ? nssf.getPublishDate().toString() : "");//出版时间
				otherProjectDataMap.put("author" , (null != nssf && null != nssf.getAuthor()) ? nssf.getAuthor() : "");//作者
				otherProjectDataMap.put("prizeObtained" , (null != nssf && null != nssf.getPrizeObtained()) ? nssf.getPrizeObtained() : "");//获奖情况
				otherProjectDataMap.put("year" , (null != nssf && null != nssf.getYear()) ? nssf.getYear() : "");//获奖情况
				Integer status = nssf.getStatus();//项目状态：0默认，1在研，2已结项，3已中止，4已撤项
				status += 1;
				if(status<6&&status>0){
					switch(status){
					case 1:
						otherProjectDataMap.put("status" , "默认");
						break;
					case 2:
						otherProjectDataMap.put("status" , "在研");
						break;
					case 3:
						otherProjectDataMap.put("status" , "已结项");
						break;
					case 4:
						otherProjectDataMap.put("status" , "已中止");
						break;
					case 5:
						otherProjectDataMap.put("status" , "已撤项");
						break;
					}					
				}
				else otherProjectDataMap.put("status" , "");
			}
			jsonMap.put("otherProjectList" , otherProjectDataMap);
			return SUCCESS;
		}else{
			//[基本信息]:项目名称，项目类别，项目主题，项目年度，负责人，依托高校，依托院系或研究基地，项目状态
			//[申报信息]:研究类别，学科门类，学科代码，相关学科代码，申请时间，计划完成时间，申请经费(万)
			//[立项信息]:批准号，批准经费(万)，批准时间，项目状态
			//[中检信息]:院系审核意见，高校审核意见，省厅审核意见，审核时间
			//[相关成员]:姓名，职称，性别，出生日期，最后学位，最后学历，联系电话，电子邮箱，其他成员（直接列名字）
			//[拨款信息]:拨款时间，拨款金额（万），经办人
			//[年检信息][中检信息][结项信息][变更信息]
			//[结项信息]:是否申请免鉴定，是否申请优秀成果，院系审核意见，高校审核意见，省厅审核意见，最终审核，审核时间
			//[变更信息]:变更申请原因，院系审核意见，高校审核意见，最终变更结果，审核时间
			Map baseDataMap = new HashMap();//基本信息
			Map applicationDataMap = new HashMap();//申报信息
			
			Map memberDataMap = new HashMap();//相关成员
			Map grantedDataMap = new HashMap();//立项信息
			Map fundDataMap = new HashMap();//拨款信息
			Map MidinspectionMap = new HashMap(); //中检信息
			Map MidiMainMap = new HashMap(); //中检信息
			Map VariationMap = new HashMap(); //变更信息
			Map EndspectionMap = new HashMap(); //结项信息
			HashMap endMainMap = new HashMap();//结项信息
			Map PublishDataMap = new HashMap();//拨款信息
			List<Map> relProd = new ArrayList<Map>();//相关信息
		
			AccountType accountType = loginer.getCurrentType();		
			ProjectGranted granted = null;
			ProjectMidinspection midinsepction = null;
			
			List<String> dirPersonIds = null;//第一负责人
			List<Person> dirPersons = null;// 项目负责人人员信息
			List<Academic> dirAcademics = null;// 项目负责人学术信息
			List<Object[]> memberList = null;//项目主要成员
			//[拨款信息]
			List<Object[]> fundList = null;
			//[年检信息]
			List<ProjectAnninspection> annList = null;
			//[中检信息]:
			List<ProjectMidinspection> midList = null;
			//[结项信息]:
			List<ProjectEndinspection> endList = null;
			//[变更信息]:
			List<ProjectVariation> varList = null;
			
			ProjectApplication application = projectExtService.getApplicationFetchDetailByAppId(entityId);
			if(null != application){
				if(application.getFinalAuditStatus() == 3 ){//已立项(&& application.getFinalAuditResult() == 2)
					//立项信息
					this.projectid = this.projectExtService.getGrantedIdByAppId(entityId);
					granted = (ProjectGranted)this.projectExtService.getGrantedFetchDetailByGrantedId(projectid);
					if (null != granted) {//存在立项信息
						dirPersonIds = this.projectExtService.getDireIdByAppId(entityId, granted.getMemberGroupNumber());
						//成员信息
						memberList = this.projectService.getMemberListByAppId(entityId, granted.getMemberGroupNumber());
						//结项信息
						endList = this.projectService.getAllEndinspectionByGrantedIdInScope(projectid, accountType);
						//jsonMap.put("endList", (endList.size() == 0) ? "" : endList.get(0));
						//变更信息
						varList = this.projectService.getAllVariationByGrantedIdInScope(projectid, accountType);
						//jsonMap.put("varList", (varList.size() == 0) ? "" : varList.get(0));
						//年检信息
						//annList = this.projectService.getAllAnninspectionByGrantedIdInScope(projectid, accountType);
						//jsonMap.put("annList", (annList.size() == 0) ? "" : annList.get(0));						
						//部分项目才有中检信息
						if("General".equals(projectType) || "Inst".equals(projectType) || "Key".equals(projectType)){//一般项目或基地项目或重大攻关
							//中检信息		
							midList = this.projectService.getAllMidinspectionByGrantedIdInScope(projectid, accountType);
							//jsonMap.put("midList", (midList.size() == 0) ? "" : midList.get(0));
						}
					}else{//未立项
						dirPersonIds = this.projectExtService.getDireIdByAppId(entityId, 1);
						//成员信息(默认申请时的成员)
						memberList = this.projectExtService.getMemberListByAppId(entityId, 1);
					}
					/*midinsepction = (ProjectMidinspection)this.projectExtService.getAllMidinspectionByGrantedIdInScope(projectid, accountType);
					if(midinsepction != null){
						
					}*/
				}else{
					dirPersonIds = this.projectExtService.getDireIdByAppId(entityId, 1);
					//成员信息(默认申请时的成员)
					memberList = this.projectExtService.getMemberListByAppId(entityId, 1);
				}			
				/**-----------------------------------------------返回结果放入baseDataMap--------------------------------------------- */
				//[基本信息]:项目名称，项目类别，项目主题，项目年度，负责人，依托高校，依托院系或研究基地
			/*	Map applicationMainMap = new HashMap();//申报信息中的主信息
				Map applicationStatusMap = new HashMap();//申报信息中的各级审核信息
				Map applicationExpertMap = new HashMap();//申报信息中的各级审核信息
				Map applicationFinalMap = new HashMap();//申报信息中最终信息成果
				Map applicationProductMap = new HashMap();//申报信息中成果
*/				String projectName  = (null != granted && null != granted.getName()) ? granted.getName() : (null != application && null != application.getName()) ? application.getName() : "";
				baseDataMap.put("projectName", projectName);//项目名称
				String projectSubType = (null != granted && null != granted.getSubtype()) ? granted.getSubtype().getName() : (null != application && null != application.getSubtype()) ? application.getSubtype().getName() : "";//项目子类名称
				baseDataMap.put("projectSubType", projectSubType);//项目子类
				String year = (null != application && application.getYear() != 0) ? String.valueOf(application.getYear()) : "";
				baseDataMap.put("year", year);//项目年度
				String topicName = projectType.equals("entrust") ? this.projectExtService.getProjectTopicNameByAppId(entityId) : "";
				baseDataMap.put("topic", topicName);//项目主题
				String directorName = (null != granted && null != granted.getApplicantName()) ? granted.getApplicantName() : (null != application && null != application.getApplicantName()) ? application.getApplicantName() : "";
				baseDataMap.put("directorName", directorName);//负责人
				String university = (null != granted && null != granted.getUniversity()) ? granted.getUniversity().getName() : (null != application && null != application.getAgencyName()) ? application.getAgencyName() : "";
				baseDataMap.put("university", university);//依托高校
				String divisionName = (null != granted && null != granted.getDivisionName()) ? granted.getDivisionName() : (null != application && null != application.getDivisionName()) ? application.getDivisionName() : "";
				baseDataMap.put("divisionName", divisionName);//依托院系或研究基地
				String researchType = (null != application && null != application.getResearchType() &&  null != application.getResearchType().getName()) ? application.getResearchType().getName() : "";
				baseDataMap.put("researchType", researchType);//研究类别
				String disciplineType = (null != application && null != application.getDisciplineType()) ? application.getDisciplineType() : "";
				baseDataMap.put("disciplineType", disciplineType);//学科门类
				String discipline = (null != application && null != application.getDiscipline())? application.getDiscipline() : "";
				baseDataMap.put("discipline", discipline);//学科代码
				String relativeDiscipline = (null != application && null != application.getRelativeDiscipline()) ? application.getRelativeDiscipline() : "";
				baseDataMap.put("relativeDiscipline", relativeDiscipline);//相关学科代码
				//baseDataMap.put("projectStatus", Status);//项目状态
				/**-----------------------------------------------返回结果放入applicationDataMap--------------------------------------------- */
				//[申报信息]:研究类别，学科门类，学科代码，相关学科代码，申请时间，计划完成时间，申请经费(万),最终成果形式
				//String researchType = (null != application && null != application.getResearchType() &&  null != application.getResearchType().getName()) ? application.getResearchType().getName() : "";
				applicationDataMap.put("researchType", researchType);//研究类别
				//String disciplineType = (null != application && null != application.getDisciplineType()) ? application.getDisciplineType() : "";
				applicationDataMap.put("disciplineType", disciplineType);//学科门类
				//String discipline = (null != application && null != application.getDiscipline())? application.getDiscipline() : "";
				applicationDataMap.put("discipline", discipline);//学科代码
				//String relativeDiscipline = (null != application && null != application.getRelativeDiscipline()) ? application.getRelativeDiscipline() : "";
				applicationDataMap.put("relativeDiscipline", relativeDiscipline);//相关学科代码
				String applicantSubmitDate = (null != application && null != application.getApplicantSubmitDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(application.getApplicantSubmitDate()) : "";
				applicationDataMap.put("applicantSubmitDate", applicantSubmitDate);//申请时间
				String planEndDate = (null != application && null != application.getPlanEndDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(application.getPlanEndDate()) : "";
				applicationDataMap.put("planEndDate", planEndDate);//计划完成时间
				String applyFee = (null != application && null != application.getApplyFee()) ? application.getApplyFee().toString()+" 万元" : "";
				applicationDataMap.put("applyFee", applyFee);//申请经费(万)
				String productType = (null != application && null != application.getProductType()) ? application.getProductType() : "";
				applicationDataMap.put("productType", productType);//最终成果形式		
				String applicationStatus = "";
				if (null != application) {
					if(application.getFinalAuditResult()==0){ applicationStatus = "未审核";}
					else if(application.getFinalAuditResult()==1){ applicationStatus = "不同意";}
					else if(application.getFinalAuditResult()==2){ applicationStatus = "同意";}
				}
				applicationDataMap.put("applicationStatus", applicationStatus);
				String otherFee = (null != application && null != application.getOtherFee()) ? application.getOtherFee().toString()+"万元" : "";
				applicationDataMap.put("otherFee", otherFee);
				String keyWord = (null != application && null != application.getKeywords()) ? application.getKeywords() : "";
				applicationDataMap.put("keyWord", keyWord);
				String summary = (null != application && null != application.getSummary()) ? application.getSummary() : "";
				applicationDataMap.put("summary", summary);
				String note = (null != application && null != application.getNote()) ? application.getNote() : "";
				applicationDataMap.put("note", note);
				//applicationDataMap.put("applicationMainMap", applicationMainMap);
				//[申报信息各级审核状态]
				String deptInstAuditResult = ""; //院系审核
				if (null != application) {
					if(application.getDeptInstAuditResult()==0){ deptInstAuditResult = "未审核";}
					else if(application.getDeptInstAuditResult()==1){ deptInstAuditResult = "不同意";}
					else if(application.getDeptInstAuditResult()==2){ deptInstAuditResult = "同意";}
				}
				applicationDataMap.put("deptInstAuditResult", deptInstAuditResult);
				String universityAuditResult = ""; //高校审核
				if (null != application) {
					if(application.getUniversityAuditResult()==0){ universityAuditResult = "未审核";}
					else if(application.getUniversityAuditResult()==1){ universityAuditResult = "不同意";}
					else if(application.getUniversityAuditResult()==2){ universityAuditResult = "同意";}
				}
				applicationDataMap.put("universityAuditResult", universityAuditResult);
				String provinceAuditResult = ""; //省厅审核
				if (null != application) {
					if(application.getProvinceAuditResult()==0){ provinceAuditResult = "未审核";}
					else if(application.getProvinceAuditResult()==1){ provinceAuditResult = "不同意";}
					else if(application.getProvinceAuditResult()==2){ provinceAuditResult = "同意";}
				}
				applicationDataMap.put("provinceAuditResult", provinceAuditResult);
				String ministryAuditResult = ""; //教育部审核
				if (null != application) {
					if(application.getMinistryAuditResult()==0){ ministryAuditResult = "未审核";}
					else if(application.getMinistryAuditResult()==1){ ministryAuditResult = "不同意";}
					else if(application.getMinistryAuditResult()==2){ ministryAuditResult = "同意";}
				}
				applicationDataMap.put("ministryAuditResult", ministryAuditResult);
				//applicationDataMap.put("applicationStatusMap", applicationStatusMap);
				//[申报信息专家评审]
				String reviewerName = (null != application && null != application.getReviewerName()) ? application.getReviewerName() : "";
				applicationDataMap.put("reviewerName", reviewerName);
				String reviewTotalScore = (null != application && null != application.getReviewTotalScore()) ? application.getReviewTotalScore().toString()+"分" : "";
				applicationDataMap.put("reviewTotalScore", reviewTotalScore);
				/*String reviewGrade = (null != application && null != application.getReviewGrade().getName()) ? application.getReviewGrade().getName() : "";
				applicationDataMap.put("reviewGrade", reviewGrade);*/
				String finalAppAuditOpinion = (null != application && null != application.getFinalAuditOpinion()) ? application.getFinalAuditOpinion() : "";
				applicationDataMap.put("finalAppAuditOpinion", finalAppAuditOpinion);

				String reviewResult = "";
				switch(application.getReviewResult()){ //提交状态
				case 0:reviewResult = "退回";break;
				case 1:reviewResult = "暂存";break;
				case 2:reviewResult = "提交";break;
				}
				applicationDataMap.put("reviewResult", reviewResult);
				
				//applicationDataMap.put("applicationExpertMap", applicationExpertMap);
				//[申报信息最终审核状态]
				String finalAuditResult = ""; //最终审核结果
				if (null != application) {
					if(application.getFinalAuditResult()==0){ finalAuditResult = "未审核";}
					else if(application.getFinalAuditResult()==1){ finalAuditResult = "不同意";}
					else if(application.getFinalAuditResult()==2){ finalAuditResult = "同意";}
				}
				applicationDataMap.put("finalAuditResult", finalAuditResult);
				String finalAuditStatus = ""; //最终审核结果
				if (null != application) {
					if(application.getFinalAuditStatus()==0){ finalAuditStatus = "退回";}
					else if(application.getFinalAuditStatus()==1){ finalAuditStatus = "暂存";}
					else if(application.getFinalAuditStatus()==2){ finalAuditStatus = "提交";}
				}
				applicationDataMap.put("finalAuditStatus", finalAuditStatus);
				//applicationDataMap.put("applicationFinalMap", applicationFinalMap);
				
					/**-----------------------------------------------返回结果放入memberDataMap--------------------------------------------- */
				//[相关人员]:负责人姓名，职称，性别，出生日期，最后学位，最后学历，联系电话，电子邮箱，其他成员（直接列名字）
				//负责人信息
				if(null != dirPersonIds && dirPersonIds.size() > 0){
					dirPersons = new LinkedList();// 项目负责人人员信息
					dirAcademics = new LinkedList();// 项目负责人学术信息
					for(int i = 0; i < dirPersonIds.size(); i++){
						if(dirPersonIds.get(i) != null && dirPersonIds.get(i) != ""){
							Person dirPerson = (Person) dao.query(Person.class, dirPersonIds.get(i));
							Academic dirAcademic = this.projectExtService.getAcademicByPersonId(dirPersonIds.get(i));
							dirPersons.add(i, dirPerson);
							dirAcademics.add(i, dirAcademic);
						}
					}
				}
			/*	HashMap memberMainMap = new HashMap();
				HashMap memberOtherMap = new HashMap();*/
				String firstDirectorName = "";
				String specialistTitle = "";
				String gender = "";
				String birthday = "";
				String lastDegree = "";
				String lastEducation = "";
				String officePhone = "";
				String email = "";
				HashMap otherMemberMap = new HashMap();
				if(null != dirPersons && dirPersons.size() > 0){
					firstDirectorName = (null != dirPersons.get(0).getName()) ? dirPersons.get(0).getName() : "";
					gender = (null != dirPersons.get(0).getGender()) ? dirPersons.get(0).getGender() : "";
				    birthday = (null != dirPersons.get(0).getBirthday()) ? new SimpleDateFormat("yyyy-MM-dd").format(dirPersons.get(0).getBirthday()) : "";
					officePhone = (null != dirPersons.get(0).getOfficePhone()) ? dirPersons.get(0).getOfficePhone() : "";
					email = (null != dirPersons.get(0).getEmail()) ? dirPersons.get(0).getEmail() : "";
				}
				if(null != dirAcademics && dirAcademics.size() > 0){
					specialistTitle = (null != dirAcademics.get(0)) ? dirAcademics.get(0).getSpecialityTitle() : "";
					lastDegree = (null != dirAcademics.get(0)) ? dirAcademics.get(0).getLastDegree() : "";
					lastEducation = (null != dirAcademics.get(0)) ? dirAcademics.get(0).getLastEducation() : "";
				}
				memberDataMap.put("directorName", firstDirectorName);//第一负责人姓名
				memberDataMap.put("specialistTitle", specialistTitle);//职称
				memberDataMap.put("gender", gender);//性别
				memberDataMap.put("birthday", birthday);//出生日期
				memberDataMap.put("lastDegree", lastDegree);//最后学位
				memberDataMap.put("lastEducation", lastEducation);//最后学历
				memberDataMap.put("officePhone", officePhone);//联系电话
				memberDataMap.put("email", email);//电子邮箱
				//memberDataMap.put("memberMainMap", memberMainMap);
				StringBuffer otherMembers = new StringBuffer();
				int count = 0;
				//memberList = this.projectService.getMemberListByAppId(entityId, granted.getMemberGroupNumber());
				
				for(Object[] midMember : memberList){//去掉首尾
					
					otherMemberMap.put("MemberId"+count, midMember[2]);
					otherMemberMap.put("MemberName"+count, midMember[3]);
					String otherMember = (null != midMember[3]) ? midMember[3].toString() : "";
					otherMembers.append(otherMember).append(" ");//以空格隔开
					//String otherMemberId = (null != midMember[3]) ? midMember[3].toString() : "";
					memberDataMap.put("otherMemberMap", otherMemberMap);
					count++;
				}
				
				//memberOtherMap.put("otherMembers", otherMembers.toString());//其他成员
				memberDataMap.put("otherMembers", otherMembers.toString());
				//memberDataMap.put("otherMemberMap", otherMemberMap);
				/**-----------------------------------------------返回结果放入grantedDataMap--------------------------------------------- */
				//[立项信息]:批准号，批准经费(万)，批准时间，项目状态
				String number = (null != granted && null != granted.getNumber()) ? granted.getNumber() : "";
				grantedDataMap.put("number", number);//批准号
				String approveFee = (null != granted && null != granted.getApproveFee()) ? granted.getApproveFee().toString()+" 万元" : "";
				grantedDataMap.put("approveFee", approveFee);//批准经费(万)
				String approveDate = (null != granted && null != granted.getApproveDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(granted.getApproveDate()) : "";
				grantedDataMap.put("approveDate", approveDate);//批准时间
				String product = (null != granted && null!= granted.getProductType()?granted.getProductType():"");
				grantedDataMap.put("product", product);
				String auditDate = (null != granted && null!=granted.getFinalAuditDate()? new SimpleDateFormat("yyyy-MM-dd").format(granted.getFinalAuditDate()):"");
						grantedDataMap.put("auditDate", auditDate);
				String projectStatus = "";
				if (null != granted) {
					if(granted.getStatus()==1){ projectStatus = "在研";}
					else if(granted.getStatus()==2){ projectStatus = "结项";}
					else if(granted.getStatus()==3){ projectStatus = "中止";}
					else if(granted.getStatus()==4){ projectStatus = "撤项";}
				}		
				grantedDataMap.put("projectStatus", projectStatus);//项目状态
				String finalAuditor = (null != granted && null!=granted.getFinalAuditorName()? granted.getFinalAuditorName():"");
				grantedDataMap.put("finalAuditor", finalAuditor);
				String finalAuditOpinionFeedback = (null != granted && null!=granted.getFinalAuditOpinionFeedback()? granted.getFinalAuditOpinionFeedback():"");
				grantedDataMap.put("finalAuditOpinionFeedback", finalAuditOpinionFeedback);
				String finalAuditOpinion = (null != granted && null!=granted.getFinalAuditOpinion()? granted.getFinalAuditOpinion():"");
				grantedDataMap.put("finalAuditOpinion", finalAuditOpinion);
				String grantProvinceOpinion = (null != granted && null!=granted.getProvinceAuditResult()? endSwitch(granted.getProvinceAuditResult()):"");
				grantedDataMap.put("grantProvinceOpinion", grantProvinceOpinion);
				String grantUniOpinion = (null != granted && null!=granted.getUniversityAuditResult()? endSwitch(granted.getUniversityAuditResult()):"");
				grantedDataMap.put("grantUniOpinion", grantUniOpinion);
				String grantDeptOpinion = (null != granted && null!=granted.getDeptInstAuditResult()? endSwitch(granted.getDeptInstAuditResult()):"");
				grantedDataMap.put("grantDeptOpinion", grantDeptOpinion);
				
			if(application.getFinalAuditStatus() == 3&&granted != null ){ //未立项，不做一下处理
							
				/**-----------------------------------------------返回结果放入MidinspectionMap--------------------------------------------- */
				//[中检信息]:院系审核意见，高校审核意见，省厅审核意见，审核时间,审核意见
				if("General".equals(projectType) || "Inst".equals(projectType) || "Key".equals(projectType)){//一般项目或基地项目或重大攻关
				String midiuniResult = "";
				String midiprovinceResult ="";
				String mididepResult="";
				String midiauditDate="";
				String midifinalResult="";
				String finalMidAuditOpinionFeedback="";				
				if(midList.size()==0){
					if("General".equals(projectType) || "Inst".equals(projectType) || "Key".equals(projectType)){//一般项目或基地项目或重大攻关
					midList =  this.projectService.getAllMidinspectionByGrantedIdInScope(projectid, accountType);
					}
				}
				List<Map> midProd = null ;
				if(midList.size()>0){
					midiuniResult = endSwitch(midList.get(0).getUniversityAuditResult());
					mididepResult = endSwitch(midList.get(0).getDeptInstAuditResult());
					midiprovinceResult = endSwitch(midList.get(0).getProvinceAuditResult());
					midifinalResult = endSwitch(midList.get(0).getFinalAuditResult());
					/*switch(midList.get(0).getUniversityAuditResult()){ //判断高校审核意见
					case 0:midiuniResult = "未审核";break;
					case 1:midiuniResult = "不同意";break;
					case 2:midiuniResult = "同意";break;
					}
					switch(midList.get(0).getDeptInstAuditResult()){ //判断院系审核意见
					case 0:mididepResult = "未审核";break;
					case 1:mididepResult = "不同意";break;
					case 2:mididepResult = "同意";break;
					}
					switch(midList.get(0).getProvinceAuditResult()){ //判断省厅审核意见
					case 0:midiprovinceResult = "未审核";break;
					case 1:midiprovinceResult = "不同意";break;
					case 2:midiprovinceResult = "同意";break;
					}
					switch(midList.get(0).getFinalAuditResult()){ //判断省厅审核意见
					case 0:midifinalResult = "未审核";break;
					case 1:midifinalResult = "不同意";break;
					case 2:midifinalResult = "同意";break;
					}*/
					MidinspectionMap.put("sumitDate", null!=midList.get(0).getApplicantSubmitDate()?new SimpleDateFormat("yyyy-MM-dd").format(midList.get(00).getApplicantSubmitDate()):"");
					MidinspectionMap.put("note", null != midList.get(0).getNote()?midList.get(0).getNote():"");

					midiauditDate = (null!=midList.get(0).getFinalAuditDate())?new SimpleDateFormat("yyyy-MM-dd").format(midList.get(0).getFinalAuditDate()):"";
					MidinspectionMap.put("midiauditDate", midiauditDate);
					finalMidAuditOpinionFeedback = (null!=midList.get(0).getFinalAuditOpinionFeedback())?midList.get(0).getFinalAuditOpinionFeedback():"";
					MidinspectionMap.put("finalMidAuditOpinionFeedback", finalMidAuditOpinionFeedback);
					//MidinspectionMap.put("MidiFinalMap", MidiFinalMap);
					List<String> midIds = new ArrayList<String>();
					midIds.add(midList.get(0).getId());
					midProd = productService.getMidProducts(projectid, midIds);
					MidinspectionMap.put("midiDepInstAuditResult", mididepResult);
					MidinspectionMap.put("midiUniAuditResult", midiuniResult);
					MidinspectionMap.put("midiProvinceAuditResult", midiprovinceResult);
					//MidinspectionMap.put("MidiStatusMap", MidiStatusMap);
					MidinspectionMap.put("midiFinalAuditResult", midifinalResult);
					//midiauditDate = (null!=midList.get(0).getFinalAuditDate())?new SimpleDateFormat("yyyy-MM-dd").format(midList.get(0).getFinalAuditDate()):"";
					//MidinspectionMap.put("midiauditDate", midiauditDate);
					MidinspectionMap.put("midProdInfo", midProd);
					MidiMainMap.put("MidinspectionMap", MidinspectionMap);
					
				}else{
					MidiMainMap.put("MidinspectionMap", MidinspectionMap);
				}
				}
				/**-----------------------------------------------返回结果放入EndspectionMap--------------------------------------------- */
				//[结项信息]:是否申请免鉴定，是否申请优秀成果，院系审核意见，高校审核意见，省厅审核意见，最终审核，审核时间
				String isNoevalu=""; //是否申请免鉴定,(1:是，0：否)
				String isExcellent = "";
				String endFinalStatus = "";
				String endauditDate="";
				String productNum = "";
				String mainPerson = "";
				String endNote = "";
				String bookNum = "";
				List<Map> endProd = null ;
				/*HashMap endStatusMap = new HashMap();
				HashMap endFinalMap = new HashMap();
				HashMap endProductMap = new HashMap();
				HashMap endExpertMap = new HashMap();*/
				if(endList == null){
					endList =  this.projectService.getAllEndinspectionByGrantedIdInScope(projectid, accountType);
				}
				if(endList.size()>0){
					for(int i = 0;i < endList.size();i++){
					String enduniResult = endSwitch(endList.get(0).getUniversityResultEnd());
					String enduniNoevalu= endSwitch(endList.get(0).getUniversityResultNoevaluation());
					String enddepResult = endSwitch(endList.get(0).getDeptInstResultEnd());
					String enddepNoevalu = endSwitch(endList.get(0).getDeptInstResultNoevaluation());
					String endprovinceResult = endSwitch(endList.get(0).getProvinceResultEnd());
					String endprovinceNoevalu =endSwitch(endList.get(0).getProvinceResultNoevaluation());
					String endminiResult = endSwitch(endList.get(0).getMinistryResultEnd());
					String endminiNoevalu = endSwitch(endList.get(0).getMinistryResultNoevaluation());
					String endFinalResult= endSwitch(endList.get(0).getFinalAuditResultEnd());
					String NoevaluStatus = endSwitch(endList.get(0).getFinalAuditResultNoevaluation());
					String ExcellentStatus = endSwitch(endList.get(0).getFinalAuditResultExcellent());
					String enduniExcellent = endSwitch(endList.get(0).getUniversityResultExcellent());
					String enddepExcellent =  endSwitch(endList.get(0).getDeptInstResultExcellent());
					String endprovinceExcellent =  endSwitch(endList.get(0).getProvinceResultExcellent());
					String endminiExcellent = endSwitch(endList.get(0).getMinistryResultExcellent());
					
					switch(endList.get(0).getFinalAuditStatus()){ //判断最终审核提交状态
					case 0:endFinalStatus = "退回";break;
					case 1:endFinalStatus = "暂存";break;
					case 2:endFinalStatus = "提交";break;
					}
					
					switch(endList.get(0).getIsApplyNoevaluation()){ //是否申请免鉴定
					case 0:isNoevalu = "是";break;
					case 1:isNoevalu = "否";break;
					}
					switch(endList.get(0).getIsApplyExcellent()){ //是否申请优秀成果
					case 0:isExcellent="是";break;
					case 1:isExcellent = "否";break;
					}	
					switch(endList.get(0).getFinalAuditStatus()){ //提交状态
					case 0:endFinalResult = "退回";break;
					case 1:endFinalResult = "暂存";break;
					case 2:endFinalResult = "提交";break;
					}
					endauditDate = (null!=endList.get(0).getFinalAuditDate())?new SimpleDateFormat("yyyy-MM-dd").format(endList.get(0).getFinalAuditDate()):"";
					productNum = (null!=endList.get(0).getImportedProductInfo())?endList.get(0).getImportedProductInfo():""; //成果数量
					mainPerson = (null!=endList.get(0).getMemberName())?endList.get(0).getMemberName():""; //主要成员
					endNote = (null!=endList.get(0).getNote())?endList.get(0).getNote():""; //备注
					bookNum = (null!=endList.get(0).getCertificate())?endList.get(0).getCertificate():""; //证书编号
					List<String> endIds = new ArrayList<String>();
					endIds.add(endList.get(0).getId());
					endProd = productService.getEndProducts(projectid, endIds);

					EndspectionMap.put("isapplyNoeval",isNoevalu );
					EndspectionMap.put("isapplyExcellent",isExcellent );
					EndspectionMap.put("endDepInstResult",enddepResult );
					EndspectionMap.put("endUniversityResult",enduniResult );
					EndspectionMap.put("endProvinceResult",endprovinceResult );
					EndspectionMap.put("endMiniResult",endminiResult );
					
					EndspectionMap.put("endUniNoevalu",enduniNoevalu );
					EndspectionMap.put("endProvinceNoevalu",endprovinceNoevalu );
					EndspectionMap.put("endDepNoevalu",enddepNoevalu );
					EndspectionMap.put("endMiniNoevalu",endminiNoevalu );
					
					EndspectionMap.put("endUniExcellent",enduniExcellent );
					EndspectionMap.put("endProvinceExcellent",endprovinceExcellent );
					EndspectionMap.put("endDepExcellent",enddepExcellent );
					EndspectionMap.put("endMiniExcellent",endminiExcellent );
					
					EndspectionMap.put("endFinalResult",endFinalResult );
					EndspectionMap.put("NoevaluStatus",NoevaluStatus );
					EndspectionMap.put("ExcellentStatus",ExcellentStatus );
					
					EndspectionMap.put("endFinalStatus",endFinalStatus );					
					EndspectionMap.put("endFinalDate",endauditDate );
					EndspectionMap.put("productNum",productNum );
					EndspectionMap.put("mainPerson",mainPerson );
					EndspectionMap.put("endNote",endNote );
					EndspectionMap.put("bookNum",bookNum );
					EndspectionMap.put("endProdInfo", endProd);
					endMainMap.put("EndspectionMap"+i, EndspectionMap);
				}
				}
				endMainMap.put("EndspectionMap"+0, EndspectionMap);
				
					/**-----------------------------------------------返回结果放入VariationMap--------------------------------------------- */
					//[变更信息]:变更申请原因，院系审核意见，高校审核意见，最终变更结果，审核时间
					String variReason = "";
					String variuniResult = ""; 
					String variprovinceResult ="";
					String varidepResult="";
					String variFinalResult="";
					String variauditDate="";
					String variauditType = "";
					String variNote = "";
					HashMap variMainMap = new HashMap();
					if(varList == null){
						varList = this.projectService.getAllVariationByGrantedIdInScope(projectid, accountType);
					}
					if(varList.size()>0){ //存在变更项
						for(int i = 0;i < varList.size();i++){
							variMainMap = new HashMap();
						variReason = varList.get(i).getVariationReason(); //获取变更原因
						variauditDate = (null!=varList.get(i).getFinalAuditDate())?new SimpleDateFormat("yyyy-MM-dd").format(varList.get(i).getFinalAuditDate()):"";
						variuniResult = endSwitch(varList.get(i).getUniversityAuditResult());
						varidepResult = endSwitch(varList.get(i).getDeptInstAuditResult());
						variprovinceResult = endSwitch(varList.get(i).getProvinceAuditResult());
						variFinalResult = endSwitch(varList.get(i).getFinalAuditResult());
						/*switch(varList.get(i).getUniversityAuditResult()){ //判断高校审核意见
						case 0:variuniResult = "未审核";break;
						case 1:variuniResult = "不同意";break;
						case 2:variuniResult = "同意";break;
						}
						switch(varList.get(i).getDeptInstAuditResult()){ //判断院系审核意见
						case 0:varidepResult = "未审核";break;
						case 1:varidepResult = "不同意";break;
						case 2:varidepResult = "同意";break;
						}
						switch(varList.get(i).getProvinceAuditResult()){ //判断省厅审核意见
						case 0:variprovinceResult = "未审核";break;
						case 1:variprovinceResult = "不同意";break;
						case 2:variprovinceResult = "同意";break;
						}
						switch(varList.get(i).getFinalAuditResult()){ //判断最终审核意见
						case 0:variFinalResult = "未审核";break;
						case 1:variFinalResult = "不同意";break;
						case 2:variFinalResult = "同意";break;
						}*/
						
						variauditType = (null!=varList.get(i).getFinalAuditResultDetail())?varList.get(i).getFinalAuditResultDetail():"";
						variNote = (null!=varList.get(i).getNote())?varList.get(i).getNote():"";
						variMainMap.put("variReason","null".equals(variReason)?"":variReason );
						variMainMap.put("variDepInstResult",varidepResult );
						variMainMap.put("variUniversityResult",variuniResult );
						variMainMap.put("variProvinceResult",variprovinceResult );
						variMainMap.put("variFinalResult",variFinalResult );
						variMainMap.put("variFinalDate",variauditDate );
						variMainMap.put("variNote", variNote);
						variMainMap.put("variauditType", variauditType);				
						variMainMap.put("changeName", varList.get(i).getChangeName());
						variMainMap.put("oldName", varList.get(i).getOldName()==null?"":varList.get(i).getOldName());
						variMainMap.put("newName", varList.get(i).getNewName()==null?"":varList.get(i).getNewName());
						variMainMap.put("changeProduct", varList.get(i).getChangeProductType());
						variMainMap.put("oldProductType", varList.get(i).getOldProductType()==null?"":varList.get(i).getOldProductType());
						variMainMap.put("newProductType", varList.get(i).getNewProductType()==null?"":varList.get(i).getNewProductType());
						variMainMap.put("changeAgency", varList.get(i).getChangeAgency());
						/*variMainMap.put("oldAgency", varList.get(i).getOldAgency().getName()==null?"":varList.get(i).getOldAgency().getName());
						variMainMap.put("newAgency", varList.get(i).getNewAgency().getName()==null?"":varList.get(i).getNewAgency().getName());*/
						variMainMap.put("changeMember", varList.get(i).getChangeMember());
						variMainMap.put("changeContent", varList.get(i).getChangeContent());
						variMainMap.put("post", varList.get(i).getPostponement());				
						variMainMap.put("oldPost", varList.get(i).getOldOnceDate()==null?"":new SimpleDateFormat("yyyy-MM-dd").format(varList.get(i).getOldOnceDate()).toString());
						variMainMap.put("newPost", varList.get(i).getNewOnceDate()==null?"":new SimpleDateFormat("yyyy-MM-dd").format(varList.get(i).getNewOnceDate()).toString());
						variMainMap.put("stop", varList.get(i).getStop());
						variMainMap.put("withdraw", varList.get(i).getWithdraw());
						variMainMap.put("changeFee", varList.get(i).getChangeFee());
						variMainMap.put("oldFee", varList.get(i).getOldProjectFee()==null?"":varList.get(i).getOldProjectFee());
						variMainMap.put("newFee", varList.get(i).getNewProjectFee()==null?"":varList.get(i).getNewProjectFee());
						variMainMap.put("other", varList.get(i).getOther());
						
						VariationMap.put("variMainMap"+i,variMainMap);
						}	
					}else{
						VariationMap.put("variMainMap0",variMainMap );
					}
			}
					/*VariationMap.put("variReason","null".equals(variReason)?"":variReason );
					VariationMap.put("variDepInstResult",varidepResult );
					VariationMap.put("variUniversityResult",variuniResult );
					VariationMap.put("variProvinceResult",variprovinceResult );
					VariationMap.put("variFinalResult",variFinalResult );
					VariationMap.put("variFinalDate",variauditDate );*/
					
					/**-----------------------------------------------返回结果放入fundDataMap--------------------------------------------- */
					//[数据发布情况]:申报审核结果，中检审核结果，结项审核结果，项目需中检数据，中检延期审核结果
/*					PublishDataMap.put("finalAuditResult", finalAuditResult);
					PublishDataMap.put("midifinalResult", midifinalResult);
					PublishDataMap.put("endFinalResult", endFinalResult);
					PublishDataMap.put("finalAuditResult", finalAuditResult);
					PublishDataMap.put("finalAuditResult", finalAuditResult);*/
					
					/**-----------------------------------------------返回结果放入fundDataMap--------------------------------------------- */
				//[拨款信息]:拨款时间，拨款金额（万），经办人
				StringBuffer fundTimes = new StringBuffer();
				StringBuffer funds = new StringBuffer();
				StringBuffer fundPersons = new StringBuffer();
				HashMap fundMainList = new HashMap();
				fundList = this.projectService.getFundListByGrantedId(projectid);
				count = 0;
				if (null != fundList && fundList.size() > 0) {
					for(Object[] newfund : fundList){
						fundMainList = new HashMap();
						String fundTime = (null != newfund[2]) ? newfund[2].toString() : "";//拨款时间
						String fund = (null != newfund[3]) ? newfund[3].toString() : "";//拨款金额（万）
						String fundPerson = (null != newfund[4]) ? newfund[4].toString() : "";//经办人
						
						fundMainList.put("fundTime", fundTime);
						fundMainList.put("fund" , fund);
						fundMainList.put("fundPerson" , fundPerson);	
						/*fundTimes.append(fundTime).append("#");//以#隔开
						funds.append(fund).append("#");
						fundPersons.append(fundPerson).append("#");*/
						fundDataMap.put("fundMainList"+count, fundMainList);
						count++; 
					}
				}else{
					fundDataMap.put("fundMainList"+count, fundMainList);
				}
				/**-----------------------------------------------返回结果放入fundDataMap--------------------------------------------- */
				//[相关成果]
				 relProd = productService.getRelProducts(projectid);		
				//relProd = relProd.put("productList", relProdMap.get(0).get("productList"));
						
			}
			jsonMap.put("baseList", baseDataMap);
			jsonMap.put("applicationList", applicationDataMap);
			jsonMap.put("memberList", memberDataMap);
			jsonMap.put("grantedList", grantedDataMap);
			jsonMap.put("midiList", MidiMainMap);
			jsonMap.put("endList", endMainMap);
			jsonMap.put("varList", VariationMap);
			jsonMap.put("fundList", fundDataMap);
			jsonMap.put("relProd", relProd);
			return SUCCESS;
		}
	}
	
	/**
	 * 代办事项的查询
	 * @return
	 */
	public String projectToDo(){
		Map session = ActionContext.getContext().getSession();
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		if(type == 1){//申报
			String HQL1 = "select app.id, app.name, app.applicantId, app.applicantName, " +
					"uni.id, app.agencyName, so.name, app.disciplineType, app.year, app.finalAuditResult, app.status, app.file, app.finalAuditStatus ";
			hql.append(HQL1);
			hql.append(this.projectService.getAppSimpleSearchHQLWordAdd(accountType));
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				String HQL3 = null;
				if(project_type.equals("general")){
					HQL3 = "from GeneralApplication app left outer join app.university uni " +
							"left outer join app.subtype so, GeneralMember mem " +
							"where mem.application.id=app.id ";
				}else if(project_type.equals("key")){
					HQL3 = "from KeyApplication app left outer join app.university uni " +
							"left outer join app.researchType so, KeyMember mem " +
							"where mem.application.id=app.id ";
				}else if(project_type.equals("instp")){
					HQL3 = "from InstpApplication app left outer join app.university uni " +
							"left outer join app.subtype so, InstpMember mem where mem.application.id=app.id ";
				}else if(project_type.equals("post")){
					HQL3 = "from PostApplication app left outer join app.university uni " +
							"left outer join app.subtype so, PostMember mem " +
							"where mem.application.id=app.id ";
				}else if(project_type.equals("entrust")){
					HQL3 = "from EntrustApplication app left outer join app.university uni " +
							"left outer join app.topic top left outer join app.subtype so, EntrustMember mem " +
							"where mem.application.id=app.id ";
				}
				hql.append(HQL3);
			}else{//管理人员
				String HQL2 = null;
				if(project_type.equals("general")){
					HQL2 = "from GeneralApplication app left join app.university uni " +
							"left join app.subtype so where 1=1 ";
				}else if(project_type.equals("key")){
					HQL2 = "from KeyApplication app left join app.university uni " +
							"left join app.researchType so where 1=1 ";
				}else if(project_type.equals("instp")){
					HQL2 = "from InstpApplication app left join app.university uni " +
							"left join app.subtype so where 1=1 ";
				}else if(project_type.equals("post")){
					HQL2 = "from PostApplication app left join app.university uni " +
							"left join app.subtype so where 1=1 ";
				}else if(project_type.equals("entrust")){
					HQL2 = "from EntrustApplication app left join app.university uni " +
							"left join app.subtype so left join app.topic top where 1=1 ";
				}
				hql.append(HQL2);
			}
			hql.append(this.projectService.getAppSimpleSearchHQLAdd(accountType));
			session.put("applicationMap", map);
			String searchHql = this.projectService.mainSearch(account, mainFlag, project_type);
			hql.append(searchHql);
			//处理查询范围
			String addHql = this.projectService.applicationInSearch(account);
			hql.append(addHql);
			map = (HashMap) session.get("applicationMap");
			search(hql.toString(), map);
		}else if(type == 2){//年检
			String HQL1 = "select app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, " +
					"so.name, app.disciplineType, app.year, ann.status, ann.file, ann.id, ann.finalAuditStatus, ann.finalAuditResult ";
			hql.append(HQL1);
			hql.append(this.projectService.getAnnSearchHQLWordAdd(accountType));
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				String HQL3 = null;
				if(project_type.equals("general")){
					HQL3 = "from GeneralAnninspection ann, GeneralAnninspection all_ann, GeneralMember mem, GeneralGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and ann.granted.id = gra.id and all_ann.granted.id = gra.id "; 
				}else if(project_type.equals("key")){
					HQL3 = "from KeyAnninspection ann, KeyAnninspection all_ann, KeyMember mem, KeyGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and ann.granted.id = gra.id and all_ann.granted.id = gra.id "; 	
				}else if(project_type.equals("instp")){
					HQL3 = "from InstpAnninspection ann, InstpAnninspection all_ann, InstpMember mem, InstpGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and ann.granted.id = gra.id and all_ann.granted.id = gra.id "; 
				}else if(project_type.equals("post")){
					HQL3 = "from PostAnninspection ann, PostAnninspection all_ann, PostMember mem, PostGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and ann.granted.id = gra.id and all_ann.granted.id = gra.id "; 
				}
				hql.append(HQL3);
			}else{//管理人员
				String HQL2 = null;
				if(project_type.equals("general")){
					HQL2 = "from GeneralAnninspection all_ann, GeneralAnninspection ann left join ann.granted gra " +
							"left join gra.application app left join gra.university uni left outer join gra.subtype so " +
							"where ann.granted.id = gra.id and all_ann.granted.id = gra.id ";
				}else if(project_type.equals("key")){
					HQL2 = "from KeyAnninspection ann, KeyAnninspection all_ann left join ann.granted gra " +
							"left join gra.application app left join gra.university uni left outer join gra.subtype so " +
							"where ann.granted.id = gra.id and all_ann.granted.id = gra.id ";
				}else if(project_type.equals("instp")){
					HQL2 = "from InstpAnninspection all_ann, InstpAnninspection ann left join ann.granted gra " +
							"left join gra.application app left join gra.university uni left outer join gra.subtype so " +
							"where ann.granted.id = gra.id and all_ann.granted.id = gra.id ";
				}else if(project_type.equals("post")){
					HQL2 = "from PostAnninspection all_ann, PostAnninspection ann left join ann.granted gra " +
							"left join gra.application app left join gra.university uni left outer join gra.subtype so " +
							"where ann.granted.id = gra.id and all_ann.granted.id = gra.id ";
				}
				hql.append(HQL2);
			}
			hql.append(this.projectService.getAnnSimpleSearchHQLAdd(accountType));
			session.put("grantedMap", map);
			String searchHql = this.projectService.mainSearch(account, mainFlag, project_type);
			hql.append(searchHql);
			String addHql = this.projectService.grantedInSearch(account);
			hql.append(addHql);
			map = (HashMap) session.get("grantedMap");
			HqlTool hqlTool = new HqlTool(hql.toString());
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
				hql.append(" group by " + hqlTool.getSelectClause() + " having ann.applicantSubmitDate = max(all_ann.applicantSubmitDate)");
			} else {
				hql.append(" group by " + hqlTool.getSelectClause() + ", ann.applicantSubmitDate having ann.applicantSubmitDate = max(all_ann.applicantSubmitDate)");
			}			
			search(hql.toString(), map);
		}else if(type == 3){//中检
			String HQL1 = "select app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, " +
					"so.name, app.disciplineType, app.year, midi.status, midi.file, midi.id, midi.finalAuditStatus, midi.finalAuditResult";
			hql.append(HQL1);
			hql.append(this.projectService.getMidSearchHQLWordAdd(accountType));
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				String HQL3 = null;
				if(project_type.equals("general")){
					HQL3 = "from GeneralMidinspection midi, GeneralMidinspection all_midi, GeneralMember mem, GeneralGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id "; 
				}else if(project_type.equals("key")){
					HQL3 = "from KeyMidinspection midi, KeyMidinspection all_midi, KeyMember mem, KeyGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join app.researchType so " +
							"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id "; 
				}else if(project_type.equals("instp")){
					HQL3 = "from InstpMidinspection midi, InstpMidinspection all_midi, InstpMember mem, InstpGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id "; 
				}
				hql.append(HQL3);
			}else{//管理人员
				String HQL2 = null;
				if(project_type.equals("general")){
					HQL2 = "from GeneralMidinspection all_midi, GeneralMidinspection midi left join midi.granted gra " +
							"left join gra.application app left join gra.university uni left outer join gra.subtype so " +
							"where midi.granted.id = gra.id and all_midi.granted.id = gra.id ";
				}else if(project_type.equals("key")){
					HQL2 = "from KeyMidinspection all_midi, KeyMidinspection midi left join midi.granted gra " +
							"left join gra.application app left join gra.university uni left outer join app.researchType so " +
							"where midi.granted.id = gra.id and all_midi.granted.id = gra.id ";
				}else if(project_type.equals("instp")){
					HQL2 = "from InstpMidinspection midi, InstpMidinspection all_midi, InstpGranted gra " +
							"left join gra.application app left join gra.university uni left outer join gra.subtype so " +
							"where midi.granted.id = gra.id and all_midi.granted.id = gra.id ";
				}
				hql.append(HQL2);
			}
			hql.append(this.projectService.getMidSimpleSearchHQLAdd(accountType));
			session.put("grantedMap", map);
			String searchHql = this.projectService.mainSearch(account, mainFlag, project_type);
			hql.append(searchHql);
			String addHql = this.projectService.grantedInSearch(account);
			hql.append(addHql);
			map = (HashMap) session.get("grantedMap");
			HqlTool hqlTool = new HqlTool(hql.toString());
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
				hql.append(" group by " + hqlTool.getSelectClause() + " having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");
			} else {
				hql.append(" group by " + hqlTool.getSelectClause() + ", midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");	
			}
			search(hql.toString(), map);
		}else if(type ==4){//结项
			String HQL1 = "select app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, " +
					"so.name, gra.status, app.year, endi.status, endi.file, endi.id, endi.finalAuditStatus, endi.finalAuditResultEnd";
			hql.append(HQL1);
			hql.append(this.projectService.getEndSimpleSearchHQLWordAdd(accountType));
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				String HQL3 = null;
				if(project_type.equals("general")){
					HQL3 = "from GeneralEndinspection endi, GeneralEndinspection all_endi, GeneralGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so, GeneralMember mem " +
							"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}else if(project_type.equals("key")){
					HQL3 = "from KeyEndinspection endi, KeyEndinspection all_endi, KeyGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join app.researchType so, KeyMember mem " +
							"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}else if(project_type.equals("instp")){
					HQL3 = "from InstpEndinspection endi, InstpEndinspection all_endi, InstpGranted gra, InstpMember mem " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}else if(project_type.equals("post")){
					HQL3 = "from PostEndinspection endi, PostEndinspection all_endi, PostGranted gra, PostMember mem " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}else if(project_type.equals("entrust")){
					HQL3 = "from EntrustEndinspection endi, EntrustEndinspection all_endi, EntrustGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so, EntrustMember mem " +
							"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}
				hql.append(HQL3);
			}else{//管理人员
				String HQL2 = null;
				if(project_type.equals("general")){
					HQL2 = "from GeneralEndinspection endi, GeneralEndinspection all_endi, GeneralGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}else if(project_type.equals("key")){
					HQL2 = "from KeyEndinspection endi, KeyEndinspection all_endi, KeyGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join app.researchType so " +
							"where endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}else if(project_type.equals("instp")){
					HQL2 = "from InstpEndinspection endi, InstpEndinspection all_endi, InstpGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}else if(project_type.equals("post")){
					HQL2 = "from PostEndinspection endi, PostEndinspection all_endi, PostGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}else if(project_type.equals("entrust")){
					HQL2 = "from EntrustEndinspection endi, EntrustEndinspection all_endi, EntrustGranted gra " +
							"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
							"where endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
				}
				hql.append(HQL2);
			}
			hql.append(this.projectService.getEndSimpleSearchHQLAdd(accountType));
			session.put("grantedMap", map);
			String searchHql = this.projectService.mainSearch(account, mainFlag, project_type);
			hql.append(searchHql);
			String addHql = this.projectService.grantedInSearch(account);
			hql.append(addHql);
			map = (HashMap) session.get("grantedMap");
			HqlTool hqlTool = new HqlTool(hql.toString());
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
				hql.append(" group by " + hqlTool.getSelectClause() + " having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
			}else {
				hql.append(" group by " + hqlTool.getSelectClause() + ", endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");		
			}
			search(hql.toString(), map);
		}else if(type ==5){//变更
			String HQL1 = "select app.id, gra.id, gra.name, gra.applicantId, gra.applicantName, " +
					"uni.id, uni.name, so.name, app.disciplineType, app.year, vari.status, vari.file, vari.id, vari.finalAuditStatus,vari.finalAuditResult ";
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				map.put("belongId", baseService.getBelongIdByAccount(account));
				String HQL3 = null;
				if(project_type.equals("general")){
					HQL3 = "from GeneralVariation vari, GeneralVariation all_vari, GeneralMember mem, GeneralGranted gra " +
							"left outer join gra.application app left outer join app.subtype sub left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and vari.granted.id = gra.id and all_vari.granted.id = gra.id ";
				}else if(project_type.equals("key")){
					HQL3 = "from KeyVariation vari, KeyVariation all_vari, KeyMember mem, KeyGranted gra " +
							"left outer join gra.application app left outer join app.subtype sub left outer join gra.university uni left outer join app.researchType so " +
							"where app.id = mem.application.id and vari.granted.id = gra.id and all_vari.granted.id = gra.id "; 
				}else if(project_type.equals("instp")){
					HQL3 = "from InstpVariation vari, InstpVariation all_vari, InstpMember mem, InstpGranted gra " +
							"left outer join gra.application app left outer join app.subtype sub left outer join gra.university uni left outer join gra.institute ins left outer join gra.subtype so " +
							"where app.id = mem.application.id and vari.granted.id = gra.id and all_vari.granted.id = gra.id "; 
				}else if(project_type.equals("post")){
					HQL3 = "from PostVariation vari, PostVariation all_vari, PostMember mem, PostGranted gra " +
							"left outer join gra.application app left outer join app.subtype sub left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and vari.granted.id = gra.id and all_vari.granted.id = gra.id "; 
				}else if(project_type.equals("entrust")){
					HQL3 = "from EntrustVariation vari, EntrustVariation all_vari, EntrustMember mem, EntrustGranted gra " +
							"left outer join gra.application app left outer join app.subtype sub left outer join gra.university uni left outer join gra.subtype so " +
							"where app.id = mem.application.id and vari.granted.id = gra.id and all_vari.granted.id = gra.id "; 
				}
				hql = this.projectService.getVarHql(HQL1, HQL3, accountType);
			}else{
				if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下管理人员
					map.put("belongId", loginer.getCurrentBelongUnitId());
				}else{}//教育部及系统管理员
				String HQL2 = null;
				if(project_type.equals("general")){
					HQL2 = "from GeneralVariation all_vari, GeneralVariation vari left join vari.granted gra " +
							"left join gra.application app left outer join app.subtype sub left join gra.university uni left outer join gra.subtype so " +
							"where vari.granted.id = gra.id and all_vari.granted.id = gra.id ";
				}else if(project_type.equals("key")){
					HQL2 = "from KeyVariation all_vari, KeyVariation vari left join vari.granted gra " +
							"left join gra.application app left outer join app.subtype sub left join gra.university uni left outer join app.researchType so " +
							"where vari.granted.id = gra.id and all_vari.granted.id = gra.id ";
				}else if(project_type.equals("instp")){
					HQL2 = "from InstpVariation all_vari, InstpVariation vari left join vari.granted gra " +
							"left join gra.application app left outer join app.subtype sub left join gra.university uni left outer join gra.institute ins left outer join gra.subtype so " +
							"where vari.granted.id = gra.id and all_vari.granted.id = gra.id ";
				}else if(project_type.equals("post")){
					HQL2 = "from PostVariation all_vari, PostVariation vari left join vari.granted gra " +
							"left join gra.application app left outer join app.subtype sub left join gra.university uni left outer join gra.subtype so " +
							"where vari.granted.id = gra.id and all_vari.granted.id = gra.id ";
				}else if(project_type.equals("entrust")){
					HQL2 = "from EntrustVariation all_vari, EntrustVariation vari left join vari.granted gra " +
							"left join gra.application app left outer join app.subtype sub left join gra.university uni left outer join gra.subtype so " +
							"where vari.granted.id = gra.id and all_vari.granted.id = gra.id ";
				}
				hql = this.projectService.getVarHql(HQL1, HQL2, accountType);
			}
			session.put("grantedMap", map);
			String searchHql = this.projectService.mainSearch(account, mainFlag, project_type);
			hql.append(searchHql);
			map = (HashMap) session.get("grantedMap");
			HqlTool hqlTool = new HqlTool(hql.toString());
			hql.append(" group by " + hqlTool.getSelectClause() + ", vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
			search(hql.toString(), map);
		}
		return SUCCESS;
	}

	public String endSwitch(int i){
		String str ="";
		switch(i){ //判断高校审核意见
		case 0:str = "未审核";break;
		case 1:str = "不同意";break;
		case 2:str = "同意";break;
		}
		return str;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	public String getProjectType() {
		return projectType;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getProjectid() {
		return projectid;
	}
	
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}

	public String getMainFlag() {
		return mainFlag;
	}

	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}

	public String getProjName() {
		return projName;
	}
	
	public void setProjName(String projName) {
		this.projName = projName;
	}
	
	public String getProjSubType() {
		return projSubType;
	}
	
	public void setProjSubType(String projSubType) {
		this.projSubType = projSubType;
	}
	
	public String getProjDirector() {
		return projDirector;
	}
	
	public void setProjDirector(String projDirector) {
		this.projDirector = projDirector;
	}
	
	public String getProjUniversity() {
		return projUniversity;
	}
	
	public void setProjUniversity(String projUniversity) {
		this.projUniversity = projUniversity;
	}
	public String getProjYear() {
		return projYear;
	}

	public void setProjYear(String projYear) {
		this.projYear = projYear;
	}
	public String getProjDiscipline() {
		return projDiscipline;
	}

	public void setProjDiscipline(String projDiscipline) {
		this.projDiscipline = projDiscipline;
	}
/*	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}*/
	@Override
	public String pageName() {
		String relativeUrl = request.getRequestURI();/* 以根开头的URL */
		if(relativeUrl.contains("/project/anotherPage")||relativeUrl.contains("/project/projectToDo")) {
			return PAGENAME2;
		}
		else return PAGENAME1;
	}
}
