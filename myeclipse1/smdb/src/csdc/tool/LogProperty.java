package csdc.tool;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于存储系统日志需要的元数据，主要用户行为、
 * 行为描述以及行为代码，其中行为代码仿造 树的
 * 结构生成，主要为了统计功能的实现。
 * @author 龚凡
 * @version 2011.03.01
 */
public class LogProperty {

	// 模块代码常量定义
	// 登录
	public static final String LOGIN = "0101";
	
	public static final String LOGIN_SERVER = "010101";
	public static final String LOGIN_SERVER_SELECT = "01010101";
	public static final String LOGIN_SERVER_SWITCH = "01010102";
	public static final String LOGIN_ACCOUNT_SWITCH = "01010103";
	public static final String LOGIN_ACCOUNT_SELECT = "01010104";
	public static final String LOGIN_ACCOUNT_CKECK = "01010105";
	
	
	public static final String LOGIN_RIGHT = "010102";
	public static final String LOGIN_RIGHT_BASIS = "01010201";
	public static final String LOGIN_RIGHT_STATISTIC = "01010202";
	public static final String LOGIN_RIGHT_PROJECT = "01010203";
	public static final String LOGIN_RIGHT_EXPERT = "01010204";
	public static final String LOGIN_RIGHT_UCENTER = "01010205";
	public static final String LOGIN_RIGHT_SCENTER = "01010206";
	public static final String LOGIN_RIGHT_AWARD = "01010207";
	
	public static final String LOGOUT = "0102";
	// 个人空间
	public static final String SELF = "02";
	public static final String SELF_ACCOUNT_VIEW = "0201";
	public static final String SELF_ACCOUNT_MODIFY = "0202";
	public static final String SELF_ACCOUNT_MODIFYPASSWORD = "0203";
	public static final String SELF_ACCOUNT_BINDEMAIL = "0204";
	public static final String SELF_ACCOUNT_BINDPHONE = "0205";
	// 系统功能
	public static final String SYSTEM = "03";
	// 新闻
	public static final String SYSTEM_NEWS_INNER = "0301";
	public static final String SYSTEM_NEWS_INNER_ADD = "030101";
	public static final String SYSTEM_NEWS_INNER_DELETE = "030102";
	public static final String SYSTEM_NEWS_INNER_MODIFY = "030103";
	public static final String SYSTEM_NEWS_INNER_VIEW = "030104";
	public static final String SYSTEM_NEWS_INNER_SIMPLESEARCH = "030105";
	public static final String SYSTEM_NEWS_INNER_ADVSEARCH = "030106";
	public static final String SYSTEM_NEWS_INNER_DOWNLOAD = "030107";
	public static final String SYSTEM_NEWS_INNER_LIST = "030108";
	public static final String SYSTEM_NEWS_INNER_TOLIST = "030109";
	
	// 通知
	public static final String SYSTEM_NOTICE_INNER = "0302";
	public static final String SYSTEM_NOTICE_INNER_ADD = "030201";
	public static final String SYSTEM_NOTICE_INNER_DELETE = "030202";
	public static final String SYSTEM_NOTICE_INNER_MODIFY = "030203";
	public static final String SYSTEM_NOTICE_INNER_VIEW = "030204";
	public static final String SYSTEM_NOTICE_INNER_SIMPLESEARCH = "030205";
	public static final String SYSTEM_NOTICE_INNER_ADVSEARCH = "030206";
	public static final String SYSTEM_NOTICE_INNER_DOWNLOAD = "030207";
	public static final String SYSTEM_NOTICE_INNER_LIST = "030208";
	public static final String SYSTEM_NOTICE_INNER_TOLIST = "030209";
	// 留言
	public static final String SYSTEM_MESSAGE_INNER = "0303";
	public static final String SYSTEM_MESSAGE_INNER_ADD = "030301";
	public static final String SYSTEM_MESSAGE_INNER_DELETE = "030302";
	public static final String SYSTEM_MESSAGE_INNER_MODIFY = "030303";
	public static final String SYSTEM_MESSAGE_INNER_VIEW = "030304";
	public static final String SYSTEM_MESSAGE_INNER_SIMPLESEARCH = "030305";
	public static final String SYSTEM_MESSAGE_INNER_ADVSEARCH = "030306";
	public static final String SYSTEM_MESSAGE_INNER_TOGGLEOPEN = "030307";
	public static final String SYSTEM_MESSAGE_INNER_LIST = "030308";
	public static final String SYSTEM_MESSAGE_INNER_TOLIST = "030308";
	
	// 邮件
	public static final String SYSTEM_MAIL = "0304";
	public static final String SYSTEM_MAIL_ADD = "030401";
	public static final String SYSTEM_MAIL_DELETE = "030402";
	public static final String SYSTEM_MAIL_PAUSESEND = "030403";
	public static final String SYSTEM_MAIL_SEND = "030404";
	public static final String SYSTEM_MAIL_SENDAGAIN = "030405";
	public static final String SYSTEM_MAIL_VIEW = "030406";
	public static final String SYSTEM_MAIL_SIMPLESEARCH = "030407";
	public static final String SYSTEM_MAIL_ADVSEARCH = "030408";
	public static final String SYSTEM_MAIL_DOWNLOAD = "030409";
	public static final String SYSTEM_MAIL_LIST = "030410";
	public static final String SYSTEM_MAIL_CANCEL = "030411";
	// 日志
	public static final String SYSTEM_LOG = "0305";
	public static final String SYSTEM_LOG_DELETE = "030501";
	public static final String SYSTEM_LOG_VIEW = "030502";
	public static final String SYSTEM_LOG_SIMPLESEARCH = "030503";
	public static final String SYSTEM_LOG_ADVSEARCH = "030504";
	public static final String SYSTEM_LOG_LIST = "030505";
	public static final String SYSTEM_LOG_STATISTIC = "030506";	
	//监控
	public static final String SYSTEM_MONITOR = "0306";
	//监控访客
	public static final String SYSTEM_MONITOR_VISITOR= "030601";
	public static final String SYSTEM_MONITOR_VISITOR_EVICT= "03060101";
	public static final String SYSTEM_MONITOR_VISITOR_LIST= "03060102";	
	//站内信
	public static final String SYSTEM_INBOX= "0307";
	public static final String SYSTEM_INBOX_LIST= "030701";
	public static final String SYSTEM_INBOX_ADD= "030702";
	public static final String SYSTEM_INBOX_DELETE= "030703";
	public static final String SYSTEM_INBOX_REMIND= "030704";
	public static final String SYSTEM_INBOX_VIEW= "030705";
	public static final String SYSTEM_INBOX_TOLIST= "030706";
	
	
//	// 重点信息
//	public static final String SYSTEM_KEY = "0306";
//	public static final String SYSTEM_KEY_MODIFY = "030603";
//	public static final String SYSTEM_KEY_VIEW = "030604";
	// 安全
	public static final String SECURITY = "04";
	// 角色
	public static final String SECURITY_ROLE = "0401";
	public static final String SECURITY_ROLE_ADD = "040101";
	public static final String SECURITY_ROLE_DELETE = "040102";
	public static final String SECURITY_ROLE_MODIFY = "040103";
	public static final String SECURITY_ROLE_VIEW = "040104";
	public static final String SECURITY_ROLE_SIMPLESEARCH = "040105";
	public static final String SECURITY_ROLE_ADVSEARCH = "040106";
	public static final String SECURITY_ROLE_LIST = "040107";
	// 权限
	public static final String SECURITY_RIGHT = "0402";
	public static final String SECURITY_RIGHT_ADD = "040201";
	public static final String SECURITY_RIGHT_DELETE = "040202";
	public static final String SECURITY_RIGHT_MODIFY = "040203";
	public static final String SECURITY_RIGHT_VIEW = "040204";
	public static final String SECURITY_RIGHT_SIMPLESEARCH = "040205";
	public static final String SECURITY_RIGHT_ADVSEARCH = "040206";
	public static final String SECURITY_RIGHT_LIST = "040207";
	// 账号
	public static final String SECURITY_ACCOUNT = "0403";
	// 部级账号
	public static final String SECURITY_ACCOUNT_MINISTRY = "040301";
	// 主账号
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN = "04030101";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_ADD = "0403010101";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_DELETE = "0403010102";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_MODIFY = "0403010103";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_VIEW = "0403010104";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_SIMPLESEARCH = "0403010105";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_ADVSEARCH = "0403010106";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_ENABLE = "0403010107";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_DISABLE = "0403010108";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_ASSIGNROLE = "0403010109";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_RETRIEVECODE = "0403010110";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_MODIFYPASSWORD = "0403010111";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_EXTIFADD = "0403010112";
	public static final String SECURITY_ACCOUNT_MINISTRY_MAIN_LIST = "0403010113";
	// 子账号
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB = "04030102";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_ADD = "0403010201";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_DELETE = "0403010202";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_MODIFY = "0403010203";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_VIEW = "0403010204";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_SIMPLESEARCH = "0403010205";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_ADVSEARCH = "0403010206";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_ENABLE = "0403010207";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_DISABLE = "0403010208";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_ASSIGNROLE = "0403010209";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_RETRIEVECODE = "0403010210";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_MODIFYPASSWORD = "0403010211";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_EXTIFADD = "0403010212";
	public static final String SECURITY_ACCOUNT_MINISTRY_SUB_LIST = "0403010213";
	// 省级账号
	public static final String SECURITY_ACCOUNT_PROVINCE = "040302";
	// 主账号
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN = "04030201";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_ADD = "0403020101";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_DELETE = "0403020102";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_MODIFY = "0403020103";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_VIEW = "0403020104";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_SIMPLESEARCH = "0403020105";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_ADVSEARCH = "0403020106";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_ENABLE = "0403020107";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_DISABLE = "0403020108";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_ASSIGNROLE = "0403020109";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_RETRIEVECODE = "0403020110";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_MODIFYPASSWORD = "0403020111";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_EXTIFADD = "0403020112";
	public static final String SECURITY_ACCOUNT_PROVINCE_MAIN_LIST = "0403020113";
	// 子账号
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB = "04030202";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_ADD = "0403020201";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_DELETE = "0403020202";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_MODIFY = "0403020203";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_VIEW = "0403020204";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_SIMPLESEARCH = "0403020205";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_ADVSEARCH = "0403020206";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_ENABLE = "0403020207";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_DISABLE = "0403020208";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_ASSIGNROLE = "0403020209";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_RETRIEVECODE = "0403020210";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_MODIFYPASSWORD = "0403020211";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_EXTIFADD = "0403020212";
	public static final String SECURITY_ACCOUNT_PROVINCE_SUB_LIST = "0403020213";
	// 校级账号
	public static final String SECURITY_ACCOUNT_UNIVERSITY = "040303";
	// 主账号
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN = "04030301";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_ADD = "0403030101";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_DELETE = "0403030102";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_MODIFY = "0403030103";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_VIEW = "0403030104";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_SIMPLESEARCH = "0403030105";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_ADVSEARCH = "0403030106";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_ENABLE = "0403030107";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_DISABLE = "0403030108";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_ASSIGNROLE = "0403030109";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_RETRIEVECODE = "0403030110";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_MODIFYPASSWORD = "0403030111";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_EXTIFADD = "0403030112";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_MAIN_LIST = "0403030113";
	// 子账号
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB = "04030302";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_ADD = "0403030201";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_DELETE = "0403030202";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_MODIFY = "0403030203";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_VIEW = "0403030204";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_SIMPLESEARCH = "0403030205";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_ADVSEARCH = "0403030206";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_ENABLE = "0403030207";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_DISABLE = "0403030208";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_ASSIGNROLE = "0403030209";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_RETRIEVECODE = "0403030210";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_MODIFYPASSWORD = "0403030211";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_EXTIFADD = "0403030212";
	public static final String SECURITY_ACCOUNT_UNIVERSITY_SUB_LIST = "0403030213";
	// 院系账号
	public static final String SECURITY_ACCOUNT_DEPARTMENT = "040304";
	// 主账号
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN = "04030401";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_ADD = "0403040101";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_DELETE = "0403040102";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_MODIFY = "0403040103";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_VIEW = "0403040104";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_SIMPLESEARCH = "0403040105";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_ADVSEARCH = "0403040106";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_ENABLE = "0403040107";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_DISABLE = "0403040108";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_ASSIGNROLE = "0403040109";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_RETRIEVECODE = "0403040110";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_MODIFYPASSWORD = "0403040111";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_EXTIFADD = "0403040112";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_MAIN_LIST = "0403040113";
	// 子账号
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB = "04030402";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_ADD = "0403040201";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_DELETE = "0403040202";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_MODIFY = "0403040203";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_VIEW = "0403040204";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_SIMPLESEARCH = "0403040205";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_ADVSEARCH = "0403040206";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_ENABLE = "0403040207";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_DISABLE = "0403040208";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_ASSIGNROLE = "0403040209";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_RETRIEVECODE = "0403040210";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_MODIFYPASSWORD = "0403040211";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_EXTIFADD = "0403040212";
	public static final String SECURITY_ACCOUNT_DEPARTMENT_SUB_LIST = "0403040213";
	// 基地账号
	public static final String SECURITY_ACCOUNT_INSTITUTE = "040305";
	// 主账号
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN = "04030501";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_ADD = "0403050101";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_DELETE = "0403050102";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_MODIFY = "0403050103";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_VIEW = "0403050104";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_SIMPLESEARCH = "0403050105";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_ADVSEARCH = "0403050106";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_ENABLE = "0403050107";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_DISABLE = "0403050108";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_ASSIGNROLE = "0403050109";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_RETRIEVECODE = "0403050110";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_MODIFYPASSWORD = "0403050111";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_EXTIFADD = "0403050112";
	public static final String SECURITY_ACCOUNT_INSTITUTE_MAIN_LIST = "0403050113";
	// 子账号
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB = "04030502";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_ADD = "0403050201";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_DELETE = "0403050202";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_MODIFY = "0403050203";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_VIEW = "0403050204";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_SIMPLESEARCH = "0403050205";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_ADVSEARCH = "0403050206";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_ENABLE = "0403050207";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_DISABLE = "0403050208";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_ASSIGNROLE = "0403050209";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_RETRIEVECODE = "0403050210";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_MODIFYPASSWORD = "0403050211";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_EXTIFADD = "0403050212";
	public static final String SECURITY_ACCOUNT_INSTITUTE_SUB_LIST = "0403050213";
	// 专家账号
	public static final String SECURITY_ACCOUNT_EXPERT = "040306";
	public static final String SECURITY_ACCOUNT_EXPERT_ADD = "04030601";
	public static final String SECURITY_ACCOUNT_EXPERT_DELETE = "04030602";
	public static final String SECURITY_ACCOUNT_EXPERT_MODIFY = "04030603";
	public static final String SECURITY_ACCOUNT_EXPERT_VIEW = "04030604";
	public static final String SECURITY_ACCOUNT_EXPERT_SIMPLESEARCH = "04030605";
	public static final String SECURITY_ACCOUNT_EXPERT_ADVSEARCH = "04030606";
	public static final String SECURITY_ACCOUNT_EXPERT_ENABLE = "04030607";
	public static final String SECURITY_ACCOUNT_EXPERT_DISABLE = "04030608";
	public static final String SECURITY_ACCOUNT_EXPERT_ASSIGNROLE = "04030609";
	public static final String SECURITY_ACCOUNT_EXPERT_RETRIEVECODE = "04030610";
	public static final String SECURITY_ACCOUNT_EXPERT_MODIFYPASSWORD = "04030611";
	public static final String SECURITY_ACCOUNT_EXPERT_EXTIFADD = "04030612";
	public static final String SECURITY_ACCOUNT_EXPERT_LIST = "04030613";
	// 教师账号
	public static final String SECURITY_ACCOUNT_TEACHER = "040307";
	public static final String SECURITY_ACCOUNT_TEACHER_ADD = "04030701";
	public static final String SECURITY_ACCOUNT_TEACHER_DELETE = "04030702";
	public static final String SECURITY_ACCOUNT_TEACHER_MODIFY = "04030703";
	public static final String SECURITY_ACCOUNT_TEACHER_VIEW = "04030704";
	public static final String SECURITY_ACCOUNT_TEACHER_SIMPLESEARCH = "04030705";
	public static final String SECURITY_ACCOUNT_TEACHER_ADVSEARCH = "04030706";
	public static final String SECURITY_ACCOUNT_TEACHER_ENABLE = "04030707";
	public static final String SECURITY_ACCOUNT_TEACHER_DISABLE = "04030708";
	public static final String SECURITY_ACCOUNT_TEACHER_ASSIGNROLE = "04030709";
	public static final String SECURITY_ACCOUNT_TEACHER_RETRIEVECODE = "04030710";
	public static final String SECURITY_ACCOUNT_TEACHER_MODIFYPASSWORD = "04030711";
	public static final String SECURITY_ACCOUNT_TEACHER_EXTIFADD = "04030712";
	public static final String SECURITY_ACCOUNT_TEACHER_LIST = "04030713";
	// 学生账号
	public static final String SECURITY_ACCOUNT_STUDENT = "040308";
	public static final String SECURITY_ACCOUNT_STUDENT_ADD = "04030801";
	public static final String SECURITY_ACCOUNT_STUDENT_DELETE = "04030802";
	public static final String SECURITY_ACCOUNT_STUDENT_MODIFY = "04030803";
	public static final String SECURITY_ACCOUNT_STUDENT_VIEW = "04030804";
	public static final String SECURITY_ACCOUNT_STUDENT_SIMPLESEARCH = "04030805";
	public static final String SECURITY_ACCOUNT_STUDENT_ADVSEARCH = "04030806";
	public static final String SECURITY_ACCOUNT_STUDENT_ENABLE = "04030807";
	public static final String SECURITY_ACCOUNT_STUDENT_DISABLE = "04030808";
	public static final String SECURITY_ACCOUNT_STUDENT_ASSIGNROLE = "04030809";
	public static final String SECURITY_ACCOUNT_STUDENT_RETRIEVECODE = "04030810";
	public static final String SECURITY_ACCOUNT_STUDENT_MODIFYPASSWORD = "04030811";
	public static final String SECURITY_ACCOUNT_STUDENT_EXTIFADD = "04030812";
	public static final String SECURITY_ACCOUNT_STUDENT_LIST = "04030813";
	// 业务管理
	public static final String BUSINESS_MANAGEMENT = "0404";
	public static final String BUSINESS_ADD = "040401";
	public static final String BUSINESS_DELETE = "040402";
	public static final String BUSINESS_MODIFY = "040403";
	public static final String BUSINESS_VIEW = "040404";
	public static final String BUSINESS_SIMPLESEARCH = "040405";
	public static final String BUSINESS_ADVSEARCH = "040406";
	public static final String BUSINESS_LIST = "040407";
	//通行证管理
	public static final String SECURITY_PASSPORT = "0405";
	public static final String SECURITY_PASSPORT_LIST = "040501";
	public static final String SECURITY_PASSPORT_VIEW = "040502";
	public static final String SECURITY_PASSPORT_SIMPLESEARCH = "040503";
	public static final String SECURITY_PASSPORT_RETRIEVECODE = "040504";
	public static final String SECURITY_PASSPORT_MODIFYPASSWORD = "040505";
	public static final String SECURITY_PASSPORT_MODIFY = "040506";
	public static final String SECURITY_PASSPORT_DELETE = "040507";
	
	
	// 人员
	public static final String PERSON = "05";
	// 部级管理人员
	public static final String PERSON_OFFICER_MINISTRY = "0501";
	public static final String PERSON_OFFICER_MINISTRY_ADD = "050101";
	public static final String PERSON_OFFICER_MINISTRY_DELETE = "050102";
	public static final String PERSON_OFFICER_MINISTRY_MODIFY = "050103";
	public static final String PERSON_OFFICER_MINISTRY_VIEW = "050104";
	public static final String PERSON_OFFICER_MINISTRY_SIMPLESEARCH = "050105";
	public static final String PERSON_OFFICER_MINISTRY_ADVSEARCH = "050106";
	public static final String PERSON_OFFICER_MINISTRY_LIST = "050107";
	public static final String PERSON_OFFICER_MINISTRY_MERGE = "050108";
	public static final String PERSON_OFFICER_MINISTRY_TOLIST = "050109";
	// 省级管理人员
	public static final String PERSON_OFFICER_PROVINCE = "0502";
	public static final String PERSON_OFFICER_PROVINCE_ADD = "050201";
	public static final String PERSON_OFFICER_PROVINCE_DELETE = "050202";
	public static final String PERSON_OFFICER_PROVINCE_MODIFY = "050203";
	public static final String PERSON_OFFICER_PROVINCE_VIEW = "050204";
	public static final String PERSON_OFFICER_PROVINCE_SIMPLESEARCH = "050205";
	public static final String PERSON_OFFICER_PROVINCE_ADVSEARCH = "050206";
	public static final String PERSON_OFFICER_PROVINCE_LIST = "050207";
	public static final String PERSON_OFFICER_PROVINCE_MERGE = "050208";	
	// 校级管理人员
	public static final String PERSON_OFFICER_UNIVERSITY = "0503";
	public static final String PERSON_OFFICER_UNIVERSITY_ADD = "050301";
	public static final String PERSON_OFFICER_UNIVERSITY_DELETE = "050302";
	public static final String PERSON_OFFICER_UNIVERSITY_MODIFY = "050303";
	public static final String PERSON_OFFICER_UNIVERSITY_VIEW = "050304";
	public static final String PERSON_OFFICER_UNIVERSITY_SIMPLESEARCH = "050305";
	public static final String PERSON_OFFICER_UNIVERSITY_ADVSEARCH = "050306";
	public static final String PERSON_OFFICER_UNIVERSITY_LIST = "050307";
	public static final String PERSON_OFFICER_UNIVERSITY_MERGE = "050308";
	public static final String PERSON_OFFICER_UNIVERSITY_TOLIST = "050309";
	
	// 院系管理人员
	public static final String PERSON_OFFICER_DEPARTMENT = "0504";
	public static final String PERSON_OFFICER_DEPARTMENT_ADD = "050401";
	public static final String PERSON_OFFICER_DEPARTMENT_DELETE = "050402";
	public static final String PERSON_OFFICER_DEPARTMENT_MODIFY = "050403";
	public static final String PERSON_OFFICER_DEPARTMENT_VIEW = "050404";
	public static final String PERSON_OFFICER_DEPARTMENT_SIMPLESEARCH = "050405";
	public static final String PERSON_OFFICER_DEPARTMENT_ADVSEARCH = "050106";
	public static final String PERSON_OFFICER_DEPARTMENT_LIST = "050407";
	public static final String PERSON_OFFICER_DEPARTMENT_MERGE = "050408";
	public static final String PERSON_OFFICER_DEPARTMENT_TOLIST = "050409";
	// 基地管理人员
	public static final String PERSON_OFFICER_INSTITUTE = "0505";
	public static final String PERSON_OFFICER_INSTITUTE_ADD = "050501";
	public static final String PERSON_OFFICER_INSTITUTE_DELETE = "050502";
	public static final String PERSON_OFFICER_INSTITUTE_MODIFY = "050503";
	public static final String PERSON_OFFICER_INSTITUTE_VIEW = "050504";
	public static final String PERSON_OFFICER_INSTITUTE_SIMPLESEARCH = "050505";
	public static final String PERSON_OFFICER_INSTITUTE_ADVSEARCH = "050106";
	public static final String PERSON_OFFICER_INSTITUTE_LIST = "050507";
	public static final String PERSON_OFFICER_INSTITUTE_MERGE = "050508";
	public static final String PERSON_OFFICER_INSTITUTE_TOLIST = "050509";
	// 外部专家
	public static final String PERSON_EXPERT = "0506";
	public static final String PERSON_EXPERT_ADD = "050601";
	public static final String PERSON_EXPERT_DELETE = "050602";
	public static final String PERSON_EXPERT_MODIFY = "050603";
	public static final String PERSON_EXPERT_VIEW = "050604";
	public static final String PERSON_EXPERT_SIMPLESEARCH = "050605";
	public static final String PERSON_EXPERT_ADVSEARCH = "050606";
	public static final String PERSON_EXPERT_LIST = "050607";
	public static final String PERSON_EXPERT_TOLIST = "050609";
	public static final String PERSON_EXPERT_MERGE = "050608";
	// 教师
	public static final String PERSON_TEACHER = "0507";
	public static final String PERSON_TEACHER_ADD = "050701";
	public static final String PERSON_TEACHER_DELETE = "050702";
	public static final String PERSON_TEACHER_MODIFY = "050703";
	public static final String PERSON_TEACHER_VIEW = "050704";
	public static final String PERSON_TEACHER_SIMPLESEARCH = "050705";
	public static final String PERSON_TEACHER_ADVSEARCH = "050706";
	public static final String PERSON_TEACHER_LIST = "050707";
	public static final String PERSON_TEACHER_MERGE = "050708";	
	// 学生
	public static final String PERSON_STUDENT = "0508";
	public static final String PERSON_STUDENT_ADD = "050801";
	public static final String PERSON_STUDENT_DELETE = "050802";
	public static final String PERSON_STUDENT_MODIFY = "050803";
	public static final String PERSON_STUDENT_VIEW = "050804";
	public static final String PERSON_STUDENT_SIMPLESEARCH = "050805";
	public static final String PERSON_STUDENT_ADVSEARCH = "050806";
	public static final String PERSON_STUDENT_LIST = "050807";
	public static final String PERSON_STUDENT_MERGE = "050808";
	public static final String PERSON_STUDENT_TOLIST = "050807";
	// 机构
	public static final String UNIT = "06";
	// 部级机构
	public static final String UNIT_MINISTRY = "0601";
	public static final String UNIT_MINISTRY_ADD = "060101";
	public static final String UNIT_MINISTRY_DELETE = "060102";
	public static final String UNIT_MINISTRY_MODIFY = "060103";
	public static final String UNIT_MINISTRY_VIEW = "060104";
	public static final String UNIT_MINISTRY_SIMPLESEARCH = "060105";
	public static final String UNIT_MINISTRY_ADVSEARCH = "060106";
	public static final String UNIT_MINISTRY_LIST = "060107";
	public static final String UNIT_MINISTRY_TOLIST = "060108";
	// 省级机构
	public static final String UNIT_PROVINCE = "0602";
	public static final String UNIT_PROVINCE_ADD = "060201";
	public static final String UNIT_PROVINCE_DELETE = "060202";
	public static final String UNIT_PROVINCE_MODIFY = "060203";
	public static final String UNIT_PROVINCE_VIEW = "060204";
	public static final String UNIT_PROVINCE_SIMPLESEARCH = "060205";
	public static final String UNIT_PROVINCE_ADVSEARCH = "060206";
	public static final String UNIT_PROVINCE_LIST = "060207";
	public static final String UNIT_PROVINCE_TOLIST = "060208";
	// 校级机构
	public static final String UNIT_UNIVERSITY = "0603";
	public static final String UNIT_UNIVERSITY_ADD = "060301";
	public static final String UNIT_UNIVERSITY_DELETE = "060302";
	public static final String UNIT_UNIVERSITY_MODIFY = "060303";
	public static final String UNIT_UNIVERSITY_VIEW = "060304";
	public static final String UNIT_UNIVERSITY_SIMPLESEARCH = "060305";
	public static final String UNIT_UNIVERSITY_ADVSEARCH = "060306";
	public static final String UNIT_UNIVERSITY_LIST = "060307";
	public static final String UNIT_UNIVERSITY_TOLIST = "060308";
	// 院系
	public static final String UNIT_DEPARTMENT = "0604";
	public static final String UNIT_DEPARTMENT_ADD = "060401";
	public static final String UNIT_DEPARTMENT_DELETE = "060402";
	public static final String UNIT_DEPARTMENT_MODIFY = "060403";
	public static final String UNIT_DEPARTMENT_VIEW = "060404";
	public static final String UNIT_DEPARTMENT_SIMPLESEARCH = "060405";
	public static final String UNIT_DEPARTMENT_ADVSEARCH = "060406";
	public static final String UNIT_DEPARTMENT_LIST = "060407";
	public static final String UNIT_DEPARTMENT_MERGE = "060408";
	// 基地
	public static final String UNIT_INSTITUTE = "0605";
	public static final String UNIT_INSTITUTE_ADD = "060501";
	public static final String UNIT_INSTITUTE_DELETE = "060502";
	public static final String UNIT_INSTITUTE_MODIFY = "060503";
	public static final String UNIT_INSTITUTE_VIEW = "060504";
	public static final String UNIT_INSTITUTE_SIMPLESEARCH = "060505";
	public static final String UNIT_INSTITUTE_ADVSEARCH = "060506";
	public static final String UNIT_INSTITUTE_LIST = "060507";
	// 项目
	public static final String PROJECT = "07";
	// 个人项目
	public static final String PROJECT_SELF = "0701";
	public static final String PROJECT_SELF_SEARCH = "070101";
	public static final String PROJECT_SELF_VIEW = "070102";
	// 一般项目
	public static final String PROJECT_GENERAL = "0702";
	// 申请
	public static final String PROJECT_GENERAL_APPLICATION = "070201";
	// 申请申请
	public static final String PROJECT_GENERAL_APPLICATION_APPLY = "07020101";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_ADD = "0702010101";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_DELETE = "0702010102";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_MODIFY = "0702010103";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_VIEW = "0702010104";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_SIMPLESEARCH = "0702010105";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_ADVSEARCH = "0702010106";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_DOWNLOAD = "0702010107";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_SUBMIT = "0702010108";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_LIST = "0702010109";
	public static final String PROJECT_GENERAL_APPLICATION_APPLYSTRICT_LIST = "0702010110";
	public static final String PROJECT_GENERAL_APPLICATION_APPLYSTRICT_CONFIRMEXPORTOVERVIEW = "0702010111";
	public static final String PROJECT_GENERAL_APPLICATION_APPLYSTRICT_SAVE = "0702010112";
	public static final String PROJECT_GENERAL_APPLICATION_ISPENDING = "0702010113";
	public static final String PROJECT_GENERAL_APPLICATION_APPLY_TOLIST = "0702010114";

	// 申请申请审核
	public static final String PROJECT_GENERAL_APPLICATION_APPLYAUDIT = "07020102";
	public static final String PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD = "0702010201";
	public static final String PROJECT_GENERAL_APPLICATION_APPLYAUDIT_DELETE = "0702010202";
	public static final String PROJECT_GENERAL_APPLICATION_APPLYAUDIT_MODIFY = "0702010203";
	public static final String PROJECT_GENERAL_APPLICATION_APPLYAUDIT_VIEW = "0702010204";
	public static final String PROJECT_GENERAL_APPLICATION_APPLYAUDIT_SUBMIT = "0702010205";
	public static final String PROJECT_GENERAL_APPLICATION_APPLYAUDIT_BACK = "0702010206";
	// 申请申请评审
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW = "07020103";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_ADD = "0702010301";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_DELETE = "0702010302";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_MODIFY = "0702010303";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_VIEW = "0702010304";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_SUBMIT = "0702010305";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_ADDGROUP = "07020103106";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_DELETEGROUP = "0702010307";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_MODIFYGROUP = "0702010308";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_VIEWGROUP = "0702010309";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_SUBMITGROUP = "0702010310";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_SIMPLESEARCH = "0702010311";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_ADVSEARCH = "0702010312";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_VIEWREVIEW = "0702010313";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_VIEWGROUPOPINION = "0702010314";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_LIST = "0702010315";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEW_TOLIST = "0702010316";
	// 申请申请评审审核
	public static final String PROJECT_GENERAL_APPLICATION_REVIEWAUDIT = "07020104";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_ADD = "0702010401";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_DELETE = "0702010402";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_MODIFY = "0702010403";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_VIEW = "0702010404";
	public static final String PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_SUBMIT = "0702010405";
	// 申请立项
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED = "07020103";
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED_ADD = "0702010301";
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED_DELETE = "0702010302";
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED_MODIFY = "0702010303";
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED_VIEW = "0702010304";
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED_SIMPLESEARCH = "0702010305";
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED_ADVSEARCH = "0702010306";
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED_SETUPPROJECTSTATUS = "0702010307";
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED_LIST = "0702010308";
	public static final String PROJECT_GENERAL_APPLICATION_GRANTED_TOLIST = "0702010309";
	// 中检
	public static final String PROJECT_GENERAL_MIDDINSPECTION = "070202";
	// 中检申请
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY = "07020201";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_ADD = "0702020101";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_DELETE = "0702020102";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_MODIFY = "0702020103";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_VIEW = "0702020104";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_SIMPLESEARCH = "0702020105";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_ADVSEARCH = "0702020106";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_DOWNLOAD = "0702020107";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_DOWNLOADTEMPLATE = "0702020108";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_SUBMIT = "0702020109";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_LIST = "0702020110";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_ISPENDING = "0702020111";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLY_TOLIST = "0702020112";
	// 中检申请审核
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT = "07020202";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_ADD = "0702020201";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_DELETE = "0702020202";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_MODIFY = "0702020203";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_VIEW = "0702020204";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_SUBMIT = "0702020205";
	public static final String PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_BACK = "0702020206";
	//中检结果管理
	public static final String PROJECT_GENERAL_MIDINSPECTION_DATA = "07020203";
	public static final String PROJECT_GENERAL_MIDINSPECTION_DATA_ADD = "0702020301";
	public static final String PROJECT_GENERAL_MIDINSPECTION_DATA_MODIFY = "0702020303";
	public static final String PROJECT_GENERAL_MIDINSPECTION_DATA_SUBMIT = "0702020305";
	// 结项
	public static final String PROJECT_GENERAL_ENDINSPECTION = "070203";
	// 结项申请
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY = "07020301";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_ADD = "0702030101";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_DELETE = "0702030102";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_MODIFY = "0702030103";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW = "0702030104";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_SIMPLESEARCH = "0702030105";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_ADVSEARCH = "0702030106";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_DOWNLOAD = "0702030107";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_DOWNLOADTEMPLATE = "0702030108";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_SUBMIT = "0702030109";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLY_LIST = "0702030110";
	// 结项申请审核
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLYAUDIT = "07020302";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLYAUDIT_ADD = "0702030201";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLYAUDIT_DELETE = "0702030202";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLYAUDIT_MODIFY = "0702030203";
	public static final String PROJECT_GENERAL_ENDINSPECTION_APPLYAUDIT_VIEW = "0702030204";
	public static final String PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_SUBMIT = "0702030205";
	public static final String PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_BACK = "0702030206";
	public static final String PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_ADDPRODREVIEW = "0702030207";
	public static final String PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_MODIFYPRODREVIEW = "0702030208";
	public static final String PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_SUBMITPRODREVIEW = "0702030209";
	// 结项评审
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW = "07020303";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_ADD = "0702030301";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_DELETE = "0702030302";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_MODIFY = "0702030303";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEW = "0702030304";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_SUBMIT = "0702030305";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_ADDGROUP = "0702030306";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_DELETEGROUP = "0702030307";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_MODIFYGROUP = "0702030308";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEWGROUP = "0702030309";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_SUBMITGROUP = "0702030310";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_SIMPLESEARCH = "0702030311";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_ADVSEARCH = "0702030312";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEWREVIEW = "0702030313";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEWGROUPOPINION = "0702030314";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEW_LIST = "0702030315";
	// 结项评审审核
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT = "07020304";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_ADD = "0702030401";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_DELETE = "0702030402";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_MODIFY = "0702030403";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_VIEW = "0702030404";
	public static final String PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_SUBMIT = "0702030405";
	//结项结果管理
	public static final String PROJECT_GENERAL_ENDINSPECTION_DATA = "07020305";
	public static final String PROJECT_GENERAL_ENDINSPECTION_DATA_ADD = "0702030501";
	public static final String PROJECT_GENERAL_ENDINSPECTION_DATA_MODIFY = "0702030503";
	public static final String PROJECT_GENERAL_ENDINSPECTION_DATA_SUBMIT = "0702030505";
	//结项打印
	public static final String PROJECT_GENERAL_ENDINSPECTION_PRINT = "07020306";
	public static final String PROJECT_GENERAL_ENDINSPECTION_PRINT_DO = "0702030601";
	public static final String PROJECT_GENERAL_ENDINSPECTION_PRINT_CONFIRM = "0702030602";
	// 变更
	public static final String PROJECT_GENERAL_VARIATION = "070204";
	// 变更申请
	public static final String PROJECT_GENERAL_VARIATION_APPLY = "07020401";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_ADD = "0702040101";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_SUBMIT = "0702040102";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_DELETE = "0702040103";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_MODIFY = "0702040104";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_VIEW = "0702040105";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_SIMPLESEARCH = "0702040106";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_ADVSEARCH = "0702040107";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_DOWNLOADTEMPLATE = "0702040108";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_DOWNLOAD = "0702040109";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_VIEWDIR = "0702040110";
	public static final String PROJECT_GENERAL_VARIATION_APPLY_LIST = "0702040111";
	// 变更申请审核
	public static final String PROJECT_GENERAL_VARIATION_APPLYAUDIT = "07020402";
	public static final String PROJECT_GENERAL_VARIATION_APPLYAUDIT_ADD = "0702040201";
	public static final String PROJECT_GENERAL_VARIATION_APPLYAUDIT_MODIFY = "0702040202";
	public static final String PROJECT_GENERAL_VARIATION_APPLYAUDIT_VIEW = "0702040203";
	public static final String PROJECT_GENERAL_VARIATION_APPLYAUDIT_SUBMIT = "0702040204";
	public static final String PROJECT_GENERAL_VARIATION_APPLYAUDIT_BACK = "0702040205";
	//变更结果管理
	public static final String PROJECT_GENERAL_VARIATION_DATA = "07020403";
	public static final String PROJECT_GENERAL_VARIATION_DATA_ADD = "0702040301";
	public static final String PROJECT_GENERAL_VARIATION_DATA_MODIFY = "0702040303";
	public static final String PROJECT_GENERAL_VARIATION_DATA_SUBMIT = "0702040305";
	// 年检
	public static final String PROJECT_GENERAL_ANNINSPECTION = "070205";
	// 年检申请
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY = "07020501";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_ADD = "0702050101";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_DELETE = "0702050102";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_MODIFY = "0702050103";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW = "0702050104";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_SIMPLESEARCH = "0702050105";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_ADVSEARCH = "0702050106";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_DOWNLOAD = "0702050107";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_DOWNLOADTEMPLATE = "0702050108";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_SUBMIT = "0702050109";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLY_LIST = "0702050110";
	//年检申请审核
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT = "07020502";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_ADD = "0702050201";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_DELETE = "0702050202";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_MODIFY = "0702050203";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_VIEW = "0702050204";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_SUBMIT = "0702050205";
	public static final String PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_BACK = "0702050206";
	//年检结果管理
	public static final String PROJECT_GENERAL_ANNINSPECTIONN_DATA = "07020503";
	public static final String PROJECT_GENERAL_ANNINSPECTION_DATA_ADD = "0702050301";
	public static final String PROJECT_GENERAL_ANNINSPECTION_DATA_MODIFY = "0702050303";
	public static final String PROJECT_GENERAL_ANNINSPECTION_DATA_SUBMIT = "0702050305";
	
	// 重大攻关项目
	public static final String PROJECT_KEY = "0703";
	// 选题
	public static final String PROJECT_KEY_TOPIC_SELECTION = "070306";
	// 选题申请
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY = "07030601";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_ADD = "0703060101";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_DELETE = "0703060102";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_MODIFY = "0703060103";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_VIEW = "0703060104";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_SIMPLESEARCH = "0703060105";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_ADVSEARCH = "0703060106";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_DOWNLOAD = "0703060107";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_DOWNLOADTEMPLATE = "0703060108";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_SUBMIT = "0703060109";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLY_LIST = "0703060110";
	// 选题申请审核
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT = "07030602";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD = "0703060201";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_DELETE = "0703060202";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_MODIFY = "0703060203";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_VIEW = "0703060204";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_SUBMIT = "0703060205";
	public static final String PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_BACK = "0703060206";
	//选题结果管理
	public static final String PROJECT_KEY_TOPIC_SELECTION_DATA = "07030603";
	public static final String PROJECT_KEY_TOPIC_SELECTION_DATA_ADD = "0703060301";
	public static final String PROJECT_KEY_TOPIC_SELECTION_DATA_MODIFY = "0703060303";
	public static final String PROJECT_KEY_TOPIC_SELECTION_DATA_SUBMIT = "0703060305";
	
	// 申请
	public static final String PROJECT_KEY_APPLICATION = "070301";
	// 申请申请
	public static final String PROJECT_KEY_APPLICATION_APPLY = "07030101";
	public static final String PROJECT_KEY_APPLICATION_APPLY_ADD = "0703010101";
	public static final String PROJECT_KEY_APPLICATION_APPLY_DELETE = "0703010102";
	public static final String PROJECT_KEY_APPLICATION_APPLY_MODIFY = "0703010103";
	public static final String PROJECT_KEY_APPLICATION_APPLY_VIEW = "0703010104";
	public static final String PROJECT_KEY_APPLICATION_APPLY_SIMPLESEARCH = "0703010105";
	public static final String PROJECT_KEY_APPLICATION_APPLY_ADVSEARCH = "0703010106";
	public static final String PROJECT_KEY_APPLICATION_APPLY_DOWNLOAD = "0703010107";
	public static final String PROJECT_KEY_APPLICATION_APPLY_SUBMIT = "0703010108";
	public static final String PROJECT_KEY_APPLICATION_APPLY_LIST = "0703010109";
	// 申请申请审核
	public static final String PROJECT_KEY_APPLICATION_APPLYAUDIT = "07030102";
	public static final String PROJECT_KEY_APPLICATION_APPLYAUDIT_ADD = "0703010201";
	public static final String PROJECT_KEY_APPLICATION_APPLYAUDIT_DELETE = "0703010202";
	public static final String PROJECT_KEY_APPLICATION_APPLYAUDIT_MODIFY = "0703010203";
	public static final String PROJECT_KEY_APPLICATION_APPLYAUDIT_VIEW = "0703010204";
	public static final String PROJECT_KEY_APPLICATION_APPLYAUDIT_SUBMIT = "0703010205";
	public static final String PROJECT_KEY_APPLICATION_APPLYAUDIT_BACK = "0703010206";
	// 申请申请评审
	public static final String PROJECT_KEY_APPLICATION_REVIEW = "07030103";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_ADD = "0703010301";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_DELETE = "0703010302";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_MODIFY = "0703010303";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_VIEW = "0703010304";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_SUBMIT = "0703010305";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_ADDGROUP = "07030103106";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_DELETEGROUP = "0703010307";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_MODIFYGROUP = "0703010308";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_VIEWGROUP = "0703010309";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_SUBMITGROUP = "0703010310";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_SIMPLESEARCH = "0703010311";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_ADVSEARCH = "0703010312";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_VIEWREVIEW = "0703010313";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_VIEWGROUPOPINION = "0703010314";
	public static final String PROJECT_KEY_APPLICATION_REVIEW_LIST = "0703010315";
	// 申请申请评审审核
	public static final String PROJECT_KEY_APPLICATION_REVIEWAUDIT = "07030104";
	public static final String PROJECT_KEY_APPLICATION_REVIEWAUDIT_ADD = "0703010401";
	public static final String PROJECT_KEY_APPLICATION_REVIEWAUDIT_DELETE = "0703010402";
	public static final String PROJECT_KEY_APPLICATION_REVIEWAUDIT_MODIFY = "0703010403";
	public static final String PROJECT_KEY_APPLICATION_REVIEWAUDIT_VIEW = "0703010404";
	public static final String PROJECT_KEY_APPLICATION_REVIEWAUDIT_SUBMIT = "0703010405";
	// 申请中标
	public static final String PROJECT_KEY_APPLICATION_GRANTED = "07030103";
	public static final String PROJECT_KEY_APPLICATION_GRANTED_ADD = "0703010301";
	public static final String PROJECT_KEY_APPLICATION_GRANTED_DELETE = "0703010302";
	public static final String PROJECT_KEY_APPLICATION_GRANTED_MODIFY = "0703010303";
	public static final String PROJECT_KEY_APPLICATION_GRANTED_VIEW = "0703010304";
	public static final String PROJECT_KEY_APPLICATION_GRANTED_SIMPLESEARCH = "0703010305";
	public static final String PROJECT_KEY_APPLICATION_GRANTED_ADVSEARCH = "0703010306";
	public static final String PROJECT_KEY_APPLICATION_GRANTED_SETUPPROJECTSTATUS = "0703010307";
	public static final String PROJECT_KEY_APPLICATION_GRANTED_LIST = "0703010308";
	// 中检
	public static final String PROJECT_KEY_MIDDINSPECTION = "070302";
	// 中检申请
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY = "07030201";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_ADD = "0703020101";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_DELETE = "0703020102";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_MODIFY = "0703020103";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_VIEW = "0703020104";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_SIMPLESEARCH = "0703020105";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_ADVSEARCH = "0703020106";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_DOWNLOAD = "0703020107";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_DOWNLOADTEMPLATE = "0703020108";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_SUBMIT = "0703020109";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLY_LIST = "0703020110";
	// 中检申请审核
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT = "07030202";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_ADD = "0703020201";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_DELETE = "0703020202";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_MODIFY = "0703020203";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_VIEW = "0703020204";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_SUBMIT = "0703020205";
	public static final String PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_BACK = "0703020206";
	//中检结果管理
	public static final String PROJECT_KEY_MIDINSPECTION_DATA = "07030203";
	public static final String PROJECT_KEY_MIDINSPECTION_DATA_ADD = "0703020301";
	public static final String PROJECT_KEY_MIDINSPECTION_DATA_MODIFY = "0703020303";
	public static final String PROJECT_KEY_MIDINSPECTION_DATA_SUBMIT = "0703020305";
	// 结项
	public static final String PROJECT_KEY_ENDINSPECTION = "070303";
	// 结项申请
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY = "07030301";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_ADD = "0703030101";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_DELETE = "0703030102";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_MODIFY = "0703030103";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_VIEW = "0703030104";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_SIMPLESEARCH = "0703030105";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_ADVSEARCH = "0703030106";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_DOWNLOAD = "0703030107";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_DOWNLOADTEMPLATE = "0703030108";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_SUBMIT = "0703030109";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLY_LIST = "0703030110";
	// 结项申请审核
	public static final String PROJECT_KEY_ENDINSPECTION_APPLYAUDIT = "07030302";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLYAUDIT_ADD = "0703030201";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLYAUDIT_DELETE = "0703030202";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLYAUDIT_MODIFY = "0703030203";
	public static final String PROJECT_KEY_ENDINSPECTION_APPLYAUDIT_VIEW = "0703030204";
	public static final String PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_SUBMIT = "0703030205";
	public static final String PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_BACK = "0703030206";
	public static final String PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_ADDPRODREVIEW = "0703030207";
	public static final String PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_MODIFYPRODREVIEW = "0703030208";
	public static final String PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_SUBMITPRODREVIEW = "0703030209";
	// 结项评审
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW = "07030303";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_ADD = "0703030301";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_DELETE = "0703030302";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_MODIFY = "0703030303";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_VIEW = "0703030304";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_SUBMIT = "0703030305";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_ADDGROUP = "0703030306";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_DELETEGROUP = "0703030307";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_MODIFYGROUP = "0703030308";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_VIEWGROUP = "0703030309";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_SUBMITGROUP = "0703030310";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_SIMPLESEARCH = "0703030311";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_ADVSEARCH = "0703030312";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_VIEWREVIEW = "0703030313";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_VIEWGROUPOPINION = "0703030314";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEW_LIST = "0703030315";
	// 结项评审审核
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT = "07030304";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_ADD = "0703030401";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_DELETE = "0703030402";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_MODIFY = "0703030403";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_VIEW = "0703030404";
	public static final String PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_SUBMIT = "0703030405";
	//结项结果管理
	public static final String PROJECT_KEY_ENDINSPECTION_DATA = "07030305";
	public static final String PROJECT_KEY_ENDINSPECTION_DATA_ADD = "0703030501";
	public static final String PROJECT_KEY_ENDINSPECTION_DATA_MODIFY = "0703030503";
	public static final String PROJECT_KEY_ENDINSPECTION_DATA_SUBMIT = "0703030505";
	//结项打印
	public static final String PROJECT_KEY_ENDINSPECTION_PRINT = "07030306";
	public static final String PROJECT_KEY_ENDINSPECTION_PRINT_DO = "0703030601";
	public static final String PROJECT_KEY_ENDINSPECTION_PRINT_CONFIRM = "0703030602";
	// 变更
	public static final String PROJECT_KEY_VARIATION = "070304";
	// 变更申请
	public static final String PROJECT_KEY_VARIATION_APPLY = "07030401";
	public static final String PROJECT_KEY_VARIATION_APPLY_ADD = "0703040101";
	public static final String PROJECT_KEY_VARIATION_APPLY_SUBMIT = "0703040102";
	public static final String PROJECT_KEY_VARIATION_APPLY_DELETE = "0703040103";
	public static final String PROJECT_KEY_VARIATION_APPLY_MODIFY = "0703040104";
	public static final String PROJECT_KEY_VARIATION_APPLY_VIEW = "0703040105";
	public static final String PROJECT_KEY_VARIATION_APPLY_SIMPLESEARCH = "0703040106";
	public static final String PROJECT_KEY_VARIATION_APPLY_ADVSEARCH = "0703040107";
	public static final String PROJECT_KEY_VARIATION_APPLY_DOWNLOADTEMPLATE = "0703040108";
	public static final String PROJECT_KEY_VARIATION_APPLY_DOWNLOAD = "0703040109";
	public static final String PROJECT_KEY_VARIATION_APPLY_VIEWDIR = "0703040110";
	public static final String PROJECT_KEY_VARIATION_APPLY_LIST = "0703040111";
	// 变更申请审核
	public static final String PROJECT_KEY_VARIATION_APPLYAUDIT = "07030402";
	public static final String PROJECT_KEY_VARIATION_APPLYAUDIT_ADD = "0703040201";
	public static final String PROJECT_KEY_VARIATION_APPLYAUDIT_MODIFY = "0703040202";
	public static final String PROJECT_KEY_VARIATION_APPLYAUDIT_VIEW = "0703040203";
	public static final String PROJECT_KEY_VARIATION_APPLYAUDIT_SUBMIT = "0703040204";
	public static final String PROJECT_KEY_VARIATION_APPLYAUDIT_BACK = "0703040205";
	//变更结果管理
	public static final String PROJECT_KEY_VARIATION_DATA = "07030403";
	public static final String PROJECT_KEY_VARIATION_DATA_ADD = "0703040301";
	public static final String PROJECT_KEY_VARIATION_DATA_MODIFY = "0703040303";
	public static final String PROJECT_KEY_VARIATION_DATA_SUBMIT = "0703040305";
	// 年检
	public static final String PROJECT_KEY_ANNINSPECTION = "070305";
	// 年检申请
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY = "07030501";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_ADD = "0703050101";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_DELETE = "0703050102";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_MODIFY = "0703050103";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_VIEW = "0703050104";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_SIMPLESEARCH = "0703050105";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_ADVSEARCH = "0703050106";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_DOWNLOAD = "0703050107";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_DOWNLOADTEMPLATE = "0703050108";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_SUBMIT = "0703050109";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLY_LIST = "0703050110";
	// 年检申请审核
	public static final String PROJECT_KEY_ANNINSPECTION_APPLYAUDIT = "07030502";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_ADD = "0703050201";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_DELETE = "0703050202";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_MODIFY = "0703050203";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_VIEW = "0703050204";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_SUBMIT = "0703050205";
	public static final String PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_BACK = "0703050206";
	//年检结果管理
	public static final String PROJECT_KEY_ANNINSPECTIONN_DATA = "07030503";
	public static final String PROJECT_KEY_ANNINSPECTION_DATA_ADD = "0703050301";
	public static final String PROJECT_KEY_ANNINSPECTION_DATA_MODIFY = "0703050303";
	public static final String PROJECT_KEY_ANNINSPECTION_DATA_SUBMIT = "0703050305";
	
	// 基地项目
	public static final String PROJECT_INSTP = "0704";
	// 申请
	public static final String PROJECT_INSTP_APPLICATION = "070401";
	// 申请申请
	public static final String PROJECT_INSTP_APPLICATION_APPLY = "07040101";
	public static final String PROJECT_INSTP_APPLICATION_APPLY_ADD = "0704010101";
	public static final String PROJECT_INSTP_APPLICATION_APPLY_DELETE = "0704010102";
	public static final String PROJECT_INSTP_APPLICATION_APPLY_MODIFY = "0704010103";
	public static final String PROJECT_INSTP_APPLICATION_APPLY_VIEW = "0704010104";
	public static final String PROJECT_INSTP_APPLICATION_APPLY_SIMPLESEARCH = "0704010105";
	public static final String PROJECT_INSTP_APPLICATION_APPLY_ADVSEARCH = "0704010106";
	public static final String PROJECT_INSTP_APPLICATION_APPLY_DOWNLOAD = "0704010107";
	public static final String PROJECT_INSTP_APPLICATION_APPLY_SUBMIT = "0704010108";
	public static final String PROJECT_INSTP_APPLICATION_APPLY_LIST = "0704010109";
	// 申请申请审核
	public static final String PROJECT_INSTP_APPLICATION_APPLYAUDIT = "07040102";
	public static final String PROJECT_INSTP_APPLICATION_APPLYAUDIT_ADD = "0704010201";
	public static final String PROJECT_INSTP_APPLICATION_APPLYAUDIT_DELETE = "0704010202";
	public static final String PROJECT_INSTP_APPLICATION_APPLYAUDIT_MODIFY = "0704010203";
	public static final String PROJECT_INSTP_APPLICATION_APPLYAUDIT_VIEW = "0704010204";
	public static final String PROJECT_INSTP_APPLICATION_APPLYAUDIT_SUBMIT = "0704010205";
	public static final String PROJECT_INSTP_APPLICATION_APPLYAUDIT_BACK = "0704010206";
	// 申请申请评审
	public static final String PROJECT_INSTP_APPLICATION_REVIEW = "07040103";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_ADD = "0704010301";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_DELETE = "0704010302";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_MODIFY = "0704010303";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_VIEW = "0704010304";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_SUBMIT = "0704010305";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_ADDGROUP = "07040103106";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_DELETEGROUP = "0704010307";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_MODIFYGROUP = "0704010308";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_VIEWGROUP = "0704010309";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_SUBMITGROUP = "0704010310";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_SIMPLESEARCH = "0704010311";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_ADVSEARCH = "0704010312";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_VIEWREVIEW = "0704010313";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_VIEWGROUPOPINION = "0704010314";
	public static final String PROJECT_INSTP_APPLICATION_REVIEW_LIST = "0704010315";
	// 申请申请评审审核
	public static final String PROJECT_INSTP_APPLICATION_REVIEWAUDIT = "07040104";
	public static final String PROJECT_INSTP_APPLICATION_REVIEWAUDIT_ADD = "0704010401";
	public static final String PROJECT_INSTP_APPLICATION_REVIEWAUDIT_DELETE = "0704010402";
	public static final String PROJECT_INSTP_APPLICATION_REVIEWAUDIT_MODIFY = "0704010403";
	public static final String PROJECT_INSTP_APPLICATION_REVIEWAUDIT_VIEW = "0704010404";
	public static final String PROJECT_INSTP_APPLICATION_REVIEWAUDIT_SUBMIT = "0704010405";
	// 申请立项
	public static final String PROJECT_INSTP_APPLICATION_GRANTED = "07040103";
	public static final String PROJECT_INSTP_APPLICATION_GRANTED_ADD = "0704010301";
	public static final String PROJECT_INSTP_APPLICATION_GRANTED_DELETE = "0704010302";
	public static final String PROJECT_INSTP_APPLICATION_GRANTED_MODIFY = "0704010303";
	public static final String PROJECT_INSTP_APPLICATION_GRANTED_VIEW = "0704010304";
	public static final String PROJECT_INSTP_APPLICATION_GRANTED_SIMPLESEARCH = "0704010305";
	public static final String PROJECT_INSTP_APPLICATION_GRANTED_ADVSEARCH = "0704010306";
	public static final String PROJECT_INSTP_APPLICATION_GRANTED_SETUPPROJECTSTATUS = "0704010307";
	public static final String PROJECT_INSTP_APPLICATION_GRANTED_LIST = "0704010308";
	// 中检
	public static final String PROJECT_INSTP_MIDDINSPECTION = "070402";
	// 中检申请
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY = "07040201";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_ADD = "0704020101";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_DELETE = "0704020102";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_MODIFY = "0704020103";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_VIEW = "0704020104";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_SIMPLESEARCH = "0704020105";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_ADVSEARCH = "0704020106";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_DOWNLOAD = "0704020107";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_DOWNLOADTEMPLATE = "0704020108";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_SUBMIT = "0704020109";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLY_LIST = "0704020110";
	// 中检申请审核
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT = "07040202";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_ADD = "0704020201";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_DELETE = "0704020202";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_MODIFY = "0704020203";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_VIEW = "0704020204";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_SUBMIT = "0704020205";
	public static final String PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_BACK = "0704020206";
	//中检结果管理
	public static final String PROJECT_INSTP_MIDINSPECTION_DATA = "07040203";
	public static final String PROJECT_INSTP_MIDINSPECTION_DATA_ADD = "0704020301";
	public static final String PROJECT_INSTP_MIDINSPECTION_DATA_MODIFY = "0704020303";
	public static final String PROJECT_INSTP_MIDINSPECTION_DATA_SUBMIT = "0704020305";
	// 结项
	public static final String PROJECT_INSTP_ENDINSPECTION = "070403";
	// 结项申请
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY = "07040301";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_ADD = "0704030101";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_DELETE = "0704030102";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_MODIFY = "0704030103";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW = "0704030104";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_SIMPLESEARCH = "0704030105";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_ADVSEARCH = "0704030106";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_DOWNLOAD = "0704030107";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_DOWNLOADTEMPLATE = "0704030108";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_SUBMIT = "0704030109";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLY_LIST = "0704030110";
	// 结项申请审核
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLYAUDIT = "07040302";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLYAUDIT_ADD = "0704030201";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLYAUDIT_DELETE = "0704030202";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLYAUDIT_MODIFY = "0704030203";
	public static final String PROJECT_INSTP_ENDINSPECTION_APPLYAUDIT_VIEW = "0704030204";
	public static final String PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_SUBMIT = "0704030205";
	public static final String PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_BACK = "0704030206";
	public static final String PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_ADDPRODREVIEW = "0704030207";
	public static final String PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_MODIFYPRODREVIEW = "0704030208";
	public static final String PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_SUBMITPRODREVIEW = "0704030209";
	// 结项评审
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW = "07040303";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_ADD = "0704030301";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_DELETE = "0704030302";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_MODIFY = "0704030303";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEW = "0704030304";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_SUBMIT = "0704030305";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_ADDGROUP = "0704030306";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_DELETEGROUP = "0704030307";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_MODIFYGROUP = "0704030308";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEWGROUP = "0704030309";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_SUBMITGROUP = "0704030310";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_SIMPLESEARCH = "0704030311";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_ADVSEARCH = "0704030312";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEWREVIEW = "0704030313";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEWGROUPOPINION = "0704030314";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEW_LIST = "0704030315";
	// 结项评审审核
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT = "07040304";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_ADD = "0704030401";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_DELETE = "0704030402";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_MODIFY = "0704030403";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_VIEW = "0704030404";
	public static final String PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_SUBMIT = "0704030405";
	//结项结果管理
	public static final String PROJECT_INSTP_ENDINSPECTION_DATA = "07040305";
	public static final String PROJECT_INSTP_ENDINSPECTION_DATA_ADD = "0704030501";
	public static final String PROJECT_INSTP_ENDINSPECTION_DATA_MODIFY = "0704030503";
	public static final String PROJECT_INSTP_ENDINSPECTION_DATA_SUBMIT = "0704030505";
	//结项打印
	public static final String PROJECT_INSTP_ENDINSPECTION_PRINT = "07040306";
	public static final String PROJECT_INSTP_ENDINSPECTION_PRINT_DO = "0704030601";
	public static final String PROJECT_INSTP_ENDINSPECTION_PRINT_CONFIRM = "0704030602";
	// 变更
	public static final String PROJECT_INSTP_VARIATION = "070404";
	// 变更申请
	public static final String PROJECT_INSTP_VARIATION_APPLY = "07040401";
	public static final String PROJECT_INSTP_VARIATION_APPLY_ADD = "0704040101";
	public static final String PROJECT_INSTP_VARIATION_APPLY_SUBMIT = "0704040102";
	public static final String PROJECT_INSTP_VARIATION_APPLY_DELETE = "0704040103";
	public static final String PROJECT_INSTP_VARIATION_APPLY_MODIFY = "0704040104";
	public static final String PROJECT_INSTP_VARIATION_APPLY_VIEW = "0704040105";
	public static final String PROJECT_INSTP_VARIATION_APPLY_SIMPLESEARCH = "0704040106";
	public static final String PROJECT_INSTP_VARIATION_APPLY_ADVSEARCH = "0704040107";
	public static final String PROJECT_INSTP_VARIATION_APPLY_DOWNLOADTEMPLATE = "0704040108";
	public static final String PROJECT_INSTP_VARIATION_APPLY_DOWNLOAD = "0704040109";
	public static final String PROJECT_INSTP_VARIATION_APPLY_VIEWDIR = "0704040110";
	public static final String PROJECT_INSTP_VARIATION_APPLY_LIST = "0704040111";
	// 变更申请审核
	public static final String PROJECT_INSTP_VARIATION_APPLYAUDIT = "07040402";
	public static final String PROJECT_INSTP_VARIATION_APPLYAUDIT_ADD = "0704040201";
	public static final String PROJECT_INSTP_VARIATION_APPLYAUDIT_MODIFY = "0704040202";
	public static final String PROJECT_INSTP_VARIATION_APPLYAUDIT_VIEW = "0704040203";
	public static final String PROJECT_INSTP_VARIATION_APPLYAUDIT_SUBMIT = "0704040204";
	public static final String PROJECT_INSTP_VARIATION_APPLYAUDIT_BACK = "0704040205";
	//变更结果管理
	public static final String PROJECT_INSTP_VARIATION_DATA = "07040403";
	public static final String PROJECT_INSTP_VARIATION_DATA_ADD = "0704040301";
	public static final String PROJECT_INSTP_VARIATION_DATA_MODIFY = "0704040303";
	public static final String PROJECT_INSTP_VARIATION_DATA_SUBMIT = "0704040305";
	// 年检
	public static final String PROJECT_INSTP_ANNINSPECTION = "070405";
	// 年检申请
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY = "07040501";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_ADD = "0704050101";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_DELETE = "0704050102";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_MODIFY = "0704050103";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW = "0704050104";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_SIMPLESEARCH = "0704050105";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_ADVSEARCH = "0704050106";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_DOWNLOAD = "0704050107";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_DOWNLOADTEMPLATE = "0704050108";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_SUBMIT = "0704050109";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLY_LIST = "0704050110";
	//年检申请审核
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT = "07040502";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD = "0704050201";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_DELETE = "0704050202";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_MODIFY = "0704050203";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_VIEW = "0704050204";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_SUBMIT = "0704050205";
	public static final String PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_BACK = "0704050206";
	//年检结果管理
	public static final String PROJECT_INSTP_ANNINSPECTIONN_DATA = "07040503";
	public static final String PROJECT_INSTP_ANNINSPECTION_DATA_ADD = "0704050301";
	public static final String PROJECT_INSTP_ANNINSPECTION_DATA_MODIFY = "0704050303";
	public static final String PROJECT_INSTP_ANNINSPECTION_DATA_SUBMIT = "0704050305";
	
	// 后期资助项目
	public static final String PROJECT_POST = "0705";
	// 申请
	public static final String PROJECT_POST_APPLICATION = "070501";
	// 申请申请
	public static final String PROJECT_POST_APPLICATION_APPLY = "07050101";
	public static final String PROJECT_POST_APPLICATION_APPLY_ADD = "0705010101";
	public static final String PROJECT_POST_APPLICATION_APPLY_DELETE = "0705010102";
	public static final String PROJECT_POST_APPLICATION_APPLY_MODIFY = "0705010103";
	public static final String PROJECT_POST_APPLICATION_APPLY_VIEW = "0705010104";
	public static final String PROJECT_POST_APPLICATION_APPLY_SIMPLESEARCH = "0705010105";
	public static final String PROJECT_POST_APPLICATION_APPLY_ADVSEARCH = "0705010106";
	public static final String PROJECT_POST_APPLICATION_APPLY_DOWNLOAD = "0705010107";
	public static final String PROJECT_POST_APPLICATION_APPLY_SUBMIT = "0705010108";
	public static final String PROJECT_POST_APPLICATION_APPLY_LIST = "0705010109";
	// 申请申请审核
	public static final String PROJECT_POST_APPLICATION_APPLYAUDIT = "07050102";
	public static final String PROJECT_POST_APPLICATION_APPLYAUDIT_ADD = "0705010201";
	public static final String PROJECT_POST_APPLICATION_APPLYAUDIT_DELETE = "0705010202";
	public static final String PROJECT_POST_APPLICATION_APPLYAUDIT_MODIFY = "0705010203";
	public static final String PROJECT_POST_APPLICATION_APPLYAUDIT_VIEW = "0705010204";
	public static final String PROJECT_POST_APPLICATION_APPLYAUDIT_SUBMIT = "0705010205";
	public static final String PROJECT_POST_APPLICATION_APPLYAUDIT_BACK = "0705010206";
	// 申请申请评审
	public static final String PROJECT_POST_APPLICATION_REVIEW = "07050103";
	public static final String PROJECT_POST_APPLICATION_REVIEW_ADD = "0705010301";
	public static final String PROJECT_POST_APPLICATION_REVIEW_DELETE = "0705010302";
	public static final String PROJECT_POST_APPLICATION_REVIEW_MODIFY = "0705010303";
	public static final String PROJECT_POST_APPLICATION_REVIEW_VIEW = "0705010304";
	public static final String PROJECT_POST_APPLICATION_REVIEW_SUBMIT = "0705010305";
	public static final String PROJECT_POST_APPLICATION_REVIEW_ADDGROUP = "07050103106";
	public static final String PROJECT_POST_APPLICATION_REVIEW_DELETEGROUP = "0705010307";
	public static final String PROJECT_POST_APPLICATION_REVIEW_MODIFYGROUP = "0705010308";
	public static final String PROJECT_POST_APPLICATION_REVIEW_VIEWGROUP = "0705010309";
	public static final String PROJECT_POST_APPLICATION_REVIEW_SUBMITGROUP = "0705010310";
	public static final String PROJECT_POST_APPLICATION_REVIEW_SIMPLESEARCH = "0705010311";
	public static final String PROJECT_POST_APPLICATION_REVIEW_ADVSEARCH = "0705010312";
	public static final String PROJECT_POST_APPLICATION_REVIEW_VIEWREVIEW = "0705010313";
	public static final String PROJECT_POST_APPLICATION_REVIEW_VIEWGROUPOPINION = "0705010314";
	public static final String PROJECT_POST_APPLICATION_REVIEW_LIST = "0705010315";
	// 申请申请评审审核
	public static final String PROJECT_POST_APPLICATION_REVIEWAUDIT = "07050104";
	public static final String PROJECT_POST_APPLICATION_REVIEWAUDIT_ADD = "0705010401";
	public static final String PROJECT_POST_APPLICATION_REVIEWAUDIT_DELETE = "0705010402";
	public static final String PROJECT_POST_APPLICATION_REVIEWAUDIT_MODIFY = "0705010403";
	public static final String PROJECT_POST_APPLICATION_REVIEWAUDIT_VIEW = "0705010404";
	public static final String PROJECT_POST_APPLICATION_REVIEWAUDIT_SUBMIT = "0705010405";
	// 立项
	public static final String PROJECT_POST_APPLICATION_GRANTED = "07050103";
	public static final String PROJECT_POST_APPLICATION_GRANTED_ADD = "0705010301";
	public static final String PROJECT_POST_APPLICATION_GRANTED_DELETE = "0705010302";
	public static final String PROJECT_POST_APPLICATION_GRANTED_MODIFY = "0705010303";
	public static final String PROJECT_POST_APPLICATION_GRANTED_VIEW = "0705010304";
	public static final String PROJECT_POST_APPLICATION_GRANTED_SIMPLESEARCH = "0705010305";
	public static final String PROJECT_POST_APPLICATION_GRANTED_ADVSEARCH = "0705010306";
	public static final String PROJECT_POST_APPLICATION_GRANTED_SETUPPROJECTSTATUS = "0705010307";
	public static final String PROJECT_POST_APPLICATION_GRANTED_LIST = "0705010308";
	// 结项
	public static final String PROJECT_POST_ENDINSPECTION = "070502";
	// 结项申请
	public static final String PROJECT_POST_ENDINSPECTION_APPLY = "07050201";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_ADD = "0705020101";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_DELETE = "0705020102";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_MODIFY = "0705020103";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_VIEW = "0705020104";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_SIMPLESEARCH = "0705020105";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_ADVSEARCH = "0705020106";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_DOWNLOAD = "0705020107";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_DOWNLOADTEMPLATE = "0705020108";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_SUBMIT = "0705020109";
	public static final String PROJECT_POST_ENDINSPECTION_APPLY_LIST = "0705020110";
	// 结项申请审核
	public static final String PROJECT_POST_ENDINSPECTION_APPLYAUDIT = "07050202";
	public static final String PROJECT_POST_ENDINSPECTION_APPLYAUDIT_ADD = "0705020201";
	public static final String PROJECT_POST_ENDINSPECTION_APPLYAUDIT_DELETE = "0705020202";
	public static final String PROJECT_POST_ENDINSPECTION_APPLYAUDIT_MODIFY = "0705020203";
	public static final String PROJECT_POST_ENDINSPECTION_APPLYAUDIT_VIEW = "0705020204";
	public static final String PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_SUBMIT = "0705020205";
	public static final String PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_BACK = "0705020206";
	public static final String PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_ADDPRODREVIEW = "0705020207";
	public static final String PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_MODIFYPRODREVIEW = "0705020208";
	public static final String PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_SUBMITPRODREVIEW = "0705020209";
	// 结项评审
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW = "07050203";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_ADD = "0705020301";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_DELETE = "0705020302";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_MODIFY = "0705020303";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_VIEW = "0705020304";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_SUBMIT = "0705020305";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_ADDGROUP = "0705020306";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_DELETEGROUP = "0705020307";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_MODIFYGROUP = "0705020308";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_VIEWGROUP = "0705020309";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_SUBMITGROUP = "0705020310";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_SIMPLESEARCH = "0705020311";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_ADVSEARCH = "0705020312";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_VIEWREVIEW = "0705020313";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_VIEWGROUPOPINION = "0705020314";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEW_LIST = "0705020315";
	// 结项评审审核
	public static final String PROJECT_POST_ENDINSPECTION_REVIEWAUDIT = "07050204";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_ADD = "0705020401";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_DELETE = "0705020402";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_MODIFY = "0705020403";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_VIEW = "0705020404";
	public static final String PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_SUBMIT = "0705020405";
	//结项结果管理
	public static final String PROJECT_POST_ENDINSPECTION_DATA = "07050205";
	public static final String PROJECT_POST_ENDINSPECTION_DATA_ADD = "0705020501";
	public static final String PROJECT_POST_ENDINSPECTION_DATA_MODIFY = "0705020503";
	public static final String PROJECT_POST_ENDINSPECTION_DATA_SUBMIT = "0705020505";
	//结项打印
	public static final String PROJECT_POST_ENDINSPECTION_PRINT = "07050206";
	public static final String PROJECT_POST_ENDINSPECTION_PRINT_DO = "0705020601";
	public static final String PROJECT_POST_ENDINSPECTION_PRINT_CONFIRM = "0705020602";
	// 变更
	public static final String PROJECT_POST_VARIATION = "070503";
	// 变更申请
	public static final String PROJECT_POST_VARIATION_APPLY = "07050301";
	public static final String PROJECT_POST_VARIATION_APPLY_ADD = "0705030101";
	public static final String PROJECT_POST_VARIATION_APPLY_SUBMIT = "0705030102";
	public static final String PROJECT_POST_VARIATION_APPLY_DELETE = "0705030103";
	public static final String PROJECT_POST_VARIATION_APPLY_MODIFY = "0705030104";
	public static final String PROJECT_POST_VARIATION_APPLY_VIEW = "0705030105";
	public static final String PROJECT_POST_VARIATION_APPLY_SIMPLESEARCH = "0705030106";
	public static final String PROJECT_POST_VARIATION_APPLY_ADVSEARCH = "0705030107";
	public static final String PROJECT_POST_VARIATION_APPLY_DOWNLOADTEMPLATE = "0705030108";
	public static final String PROJECT_POST_VARIATION_APPLY_DOWNLOAD = "0705030109";
	public static final String PROJECT_POST_VARIATION_APPLY_VIEWDIR = "0705030110";
	public static final String PROJECT_POST_VARIATION_APPLY_LIST = "0705030111";
	// 变更申请审核
	public static final String PROJECT_POST_VARIATION_APPLYAUDIT = "07050302";
	public static final String PROJECT_POST_VARIATION_APPLYAUDIT_ADD = "0705030201";
	public static final String PROJECT_POST_VARIATION_APPLYAUDIT_MODIFY = "0705030202";
	public static final String PROJECT_POST_VARIATION_APPLYAUDIT_VIEW = "0705030203";
	public static final String PROJECT_POST_VARIATION_APPLYAUDIT_SUBMIT = "0705030204";
	public static final String PROJECT_POST_VARIATION_APPLYAUDIT_BACK = "0705030205";
	//变更结果管理
	public static final String PROJECT_POST_VARIATION_DATA = "07050303";
	public static final String PROJECT_POST_VARIATION_DATA_ADD = "0705030301";
	public static final String PROJECT_POST_VARIATION_DATA_MODIFY = "0705030303";
	public static final String PROJECT_POST_VARIATION_DATA_SUBMIT = "0705030305";
	// 年检
	public static final String PROJECT_POST_ANNINSPECTION = "070505";
	// 年检申请
	public static final String PROJECT_POST_ANNINSPECTION_APPLY = "07050501";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_ADD = "0705050101";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_DELETE = "0705050102";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_MODIFY = "0705050103";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_VIEW = "0705050104";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_SIMPLESEARCH = "0705050105";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_ADVSEARCH = "0705050106";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_DOWNLOAD = "0705050107";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_DOWNLOADTEMPLATE = "0705050108";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_SUBMIT = "0705050109";
	public static final String PROJECT_POST_ANNINSPECTION_APPLY_LIST = "0705050110";
	//年检申请审核
	public static final String PROJECT_POST_ANNINSPECTION_APPLYAUDIT = "07050502";
	public static final String PROJECT_POST_ANNINSPECTION_APPLYAUDIT_ADD = "0705050201";
	public static final String PROJECT_POST_ANNINSPECTION_APPLYAUDIT_DELETE = "0705050202";
	public static final String PROJECT_POST_ANNINSPECTION_APPLYAUDIT_MODIFY = "0705050203";
	public static final String PROJECT_POST_ANNINSPECTION_APPLYAUDIT_VIEW = "0705050204";
	public static final String PROJECT_POST_ANNINSPECTION_APPLYAUDIT_SUBMIT = "0705050205";
	public static final String PROJECT_POST_ANNINSPECTION_APPLYAUDIT_BACK = "0705050206";
	//年检结果管理
	public static final String PROJECT_POST_ANNINSPECTIONN_DATA = "07050503";
	public static final String PROJECT_POST_ANNINSPECTION_DATA_ADD = "0705050301";
	public static final String PROJECT_POST_ANNINSPECTION_DATA_MODIFY = "0705050303";
	public static final String PROJECT_POST_ANNINSPECTION_DATA_SUBMIT = "0705050305";
	
	// 委托应急课题
	public static final String PROJECT_ENTRUST_APPLICATION_APPLY_TOLIST = "0706010114";
	public static final String PROJECT_ENTRUST_APPLICATION_GRANTED_TOLIST = "0706010308";
	public static final String PROJECT_ENTRUST_ENDINSPECTION_APPLY_TOLIST = "0706030110";
	public static final String PROJECT_ENTRUST_VARIATION_APPLY_TOLIST = "0706040111";
	
	// 成果
	public static final String PRODUCT = "08";
	// 个人成果
	public static final String PRODUCT_SELF = "0801";
	public static final String PRODUCT_SELF_DELETE = "080101";
	public static final String PRODUCT_SELF_VIEW = "080102";
	public static final String PRODUCT_SELF_SEARCH = "080103";
	public static final String PRODUCT_SELF_AUDIT = "080104";
	// 论文
	public static final String PRODUCT_PAPER = "0802";
	public static final String PRODUCT_PAPER_ADD = "080201";
	public static final String PRODUCT_PAPER_DELETE = "080202";
	public static final String PRODUCT_PAPER_MODIFY = "080203";
	public static final String PRODUCT_PAPER_VIEW = "080204";
	public static final String PRODUCT_PAPER_SIMPLESEARCH = "080205";
	public static final String PRODUCT_PAPER_ADVSEARCH = "080206";
	public static final String PRODUCT_PAPER_AUDIT = "080207";
	public static final String PRODUCT_PAPER_VIEWAUDIT = "080208";
	public static final String PRODUCT_PAPER_DOWNLOAD = "080209";
	public static final String PRODUCT_PAPER_EXADD = "080210";
	public static final String PRODUCT_PAPER_LIST = "080211";
	// 书籍
	public static final String PRODUCT_BOOK = "0803";
	public static final String PRODUCT_BOOK_ADD = "080301";
	public static final String PRODUCT_BOOK_DELETE = "080302";
	public static final String PRODUCT_BOOK_MODIFY = "080303";
	public static final String PRODUCT_BOOK_VIEW = "080304";
	public static final String PRODUCT_BOOK_SIMPLESEARCH = "080305";
	public static final String PRODUCT_BOOK_ADVSEARCH = "080306";
	public static final String PRODUCT_BOOK_AUDIT = "080307";
	public static final String PRODUCT_BOOK_VIEWAUDIT = "080308";
	public static final String PRODUCT_BOOK_DOWNLOAD = "080309";
	public static final String PRODUCT_BOOK_EXADD = "080310";
	public static final String PRODUCT_BOOK_LIST = "080311";
	// 研究咨询报告
	public static final String PRODUCT_CONSULTATION = "0804";
	public static final String PRODUCT_CONSULTATION_ADD = "080401";
	public static final String PRODUCT_CONSULTATION_DELETE = "080402";
	public static final String PRODUCT_CONSULTATION_MODIFY = "080403";
	public static final String PRODUCT_CONSULTATION_VIEW = "080404";
	public static final String PRODUCT_CONSULTATION_SIMPLESEARCH = "080405";
	public static final String PRODUCT_CONSULTATION_ADVSEARCH = "080406";
	public static final String PRODUCT_CONSULTATION_AUDIT = "080407";
	public static final String PRODUCT_CONSULTATION_VIEWAUDIT = "080408";
	public static final String PRODUCT_CONSULTATION_DOWNLOAD = "080409";
	public static final String PRODUCT_CONSULTATION_EXADD = "080410";
	public static final String PRODUCT_CONSULTATION_LIST = "080411";
	// 奖励
	public static final String AWARD = "09";
	// 个人奖励
	public static final String AWARD_SELF = "0901";
	public static final String AWARD_SELF_SEARCH = "090101";
	public static final String AWARD_SELF_VIEW = "090102";
	// 人文社科奖
	public static final String AWARD_MOESOCIAL = "0902";
	// 申请
	public static final String AWARD_MOESOCIAL_APPLY = "090201";
	public static final String AWARD_MOESOCIAL_APPLY_ADD = "09020101";
	public static final String AWARD_MOESOCIAL_APPLY_DELETE = "09020102";
	public static final String AWARD_MOESOCIAL_APPLY_MODIFY = "09020103";
	public static final String AWARD_MOESOCIAL_APPLY_SUBMIT = "09020104";
	public static final String AWARD_MOESOCIAL_APPLY_VIEW = "09020105";
	public static final String AWARD_MOESOCIAL_APPLY_SIMPLESEARCH = "09020106";
	public static final String AWARD_MOESOCIAL_APPLY_ADVSEARCH = "09020107";
	public static final String AWARD_MOESOCIAL_APPLY_DOWNLOADMODEL = "09020108";
	public static final String AWARD_MOESOCIAL_APPLY_DOWNLOAD = "09020109";
	public static final String AWARD_MOESOCIAL_APPLY_LIST = "09020110";
	// 申请审核
	public static final String AWARD_MOESOCIAL_APPLYAUDIT = "090202";
	public static final String AWARD_MOESOCIAL_APPLYAUDIT_ADD = "09020201";
	public static final String AWARD_MOESOCIAL_APPLYAUDIT_MODIFY = "09020202";
	public static final String AWARD_MOESOCIAL_APPLYAUDIT_VIEW = "09020203";
	public static final String AWARD_MOESOCIAL_APPLYAUDIT_SUBMIT = "09020204";
	public static final String AWARD_MOESOCIAL_APPLYAUDIT_BACK = "09020205";
	// 评审
	public static final String AWARD_MOESOCIAL_REVIEW = "090203";
	public static final String AWARD_MOESOCIAL_REVIEW_ADD = "09020301";
	public static final String AWARD_MOESOCIAL_REVIEW_MODIFY = "09020302";
	public static final String AWARD_MOESOCIAL_REVIEW_VIEW = "09020303";
	public static final String AWARD_MOESOCIAL_REVIEW_SUBMIT = "09020304";
	public static final String AWARD_MOESOCIAL_REVIEW_ADDGROUP = "09020305";
	public static final String AWARD_MOESOCIAL_REVIEW_MODIFYGROUP = "09020306";
	public static final String AWARD_MOESOCIAL_REVIEW_VIEWGROUP = "09020307";
	public static final String AWARD_MOESOCIAL_REVIEW_SUBMITGROUP = "09020308";
	public static final String AWARD_MOESOCIAL_REVIEW_SIMPLESEARCH = "09020309";
	public static final String AWARD_MOESOCIAL_REVIEW_ADVSEARCH = "09020310";
	public static final String AWARD_MOESOCIAL_REVIEW_VIEWREVIEW = "09020311";
	public static final String AWARD_MOESOCIAL_REVIEW_VIEWGROUPOPINION = "09020312";
	public static final String AWARD_MOESOCIAL_REVIEW_LIST = "09020313";
	// 评审审核
	public static final String AWARD_MOESOCIAL_REVIEWAUDIT = "090204";
	public static final String AWARD_MOESOCIAL_REVIEWAUDIT_ADD = "09020401";
	public static final String AWARD_MOESOCIAL_REVIEWAUDIT_MODIFY = "09020402";
	public static final String AWARD_MOESOCIAL_REVIEWAUDIT_VIEW = "09020403";
	public static final String AWARD_MOESOCIAL_REVIEWAUDIT_SUBMIT = "09020404";
	// 公示
	public static final String AWARD_MOESOCIAL_PUBLICITY = "090205";
	public static final String AWARD_MOESOCIAL_PUBLICITY_VIEW = "09020501";
	public static final String AWARD_MOESOCIAL_PUBLICITY_SIMPLESEARCH = "09020502";
	public static final String AWARD_MOESOCIAL_PUBLICITY_ADVSEARCH = "09020503";
	public static final String AWARD_MOESOCIAL_PUBLICITY_LIST = "09020504";
	// 公示审核
	public static final String AWARD_MOESOCIAL_PUBLICITYAUDIT = "090206";
	public static final String AWARD_MOESOCIAL_PUBLICITYAUDIT_ADD = "09020601";
	public static final String AWARD_MOESOCIAL_PUBLICITYAUDIT_MODIFY = "09020602";
	public static final String AWARD_MOESOCIAL_PUBLICITYAUDIT_VIEW = "09020603";
	public static final String AWARD_MOESOCIAL_PUBLICITYAUDIT_SUBMIT = "09020604";
	// 获奖
	public static final String AWARD_MOESOCIAL_AWARDED = "090207";
	public static final String AWARD_MOESOCIAL_AWARDED_VIEW = "09020701";
	public static final String AWARD_MOESOCIAL_AWARDED_SIMPLESEARCH = "09020702";
	public static final String AWARD_MOESOCIAL_AWARDED_ADVSEARCH = "09020703";
	public static final String AWARD_MOESOCIAL_AWARDED_LIST = "09020704";
	
	public static final String OTHER = "10";
	public static final String OTHER_NSFC_LIST = "1001";
	public static final String OTHER_NSSF_LIST = "1002";
	
	//统计
	private static final String STATISTIC_PERSON= "person";
	private static final String STATISTIC_UNIT= "unit";
	private static final String STATISTIC_PROJECT= "project";
	private static final String STATISTIC_PRODUCT= "product";
	private static final String STATISTIC_AWARD= "award";
	
	public static final String STATISTIC= "10";
	
	//常规统计
	public static final String STATISTIC_COMMON ="1001";
	//人员统计
	public static final String STATISTIC_COMMON_PERSON ="100101";
	public static final String STATISTIC_COMMON_PERSON_ADD ="10010101";
	public static final String STATISTIC_COMMON_PERSON_MODIFY ="10010102";
	public static final String STATISTIC_COMMON_PERSON_VIEW ="10010103";
	public static final String STATISTIC_COMMON_PERSON_DELETE ="10010104";
	public static final String STATISTIC_COMMON_PERSON_SIMPLESEARCH ="10010105";
	public static final String STATISTIC_COMMON_PERSON_LIST ="10010106";
	//机构统计
	public static final String STATISTIC_COMMON_UNIT ="100102";
	public static final String STATISTIC_COMMON_UNIT_ADD ="10010201";
	public static final String STATISTIC_COMMON_UNIT_MODIFY ="10010202";
	public static final String STATISTIC_COMMON_UNIT_VIEW ="10010203";
	public static final String STATISTIC_COMMON_UNIT_DELETE ="10010204";
	public static final String STATISTIC_COMMON_UNIT_SIMPLESEARCH ="10010205";
	public static final String STATISTIC_COMMON_UNIT_LIST ="10010206";
	//项目统计
	public static final String STATISTIC_COMMON_PROJECT ="100103";
	public static final String STATISTIC_COMMON_PROJECT_ADD ="10010301";
	public static final String STATISTIC_COMMON_PROJECT_MODIFY ="10010302";
	public static final String STATISTIC_COMMON_PROJECT_VIEW ="10010303";
	public static final String STATISTIC_COMMON_PROJECT_DELETE ="10010304";
	public static final String STATISTIC_COMMON_PROJECT_SIMPLESEARCH ="10010305";
	public static final String STATISTIC_COMMON_PROJECT_LIST ="10010306";
	//成果统计
	public static final String STATISTIC_COMMON_PRODUCT ="100104";
	public static final String STATISTIC_COMMON_PRODUCT_ADD ="10010401";
	public static final String STATISTIC_COMMON_PRODUCT_MODIFY ="10010402";
	public static final String STATISTIC_COMMON_PRODUCT_VIEW ="10010403";
	public static final String STATISTIC_COMMON_PRODUCT_DELETE ="10010404";
	public static final String STATISTIC_COMMON_PRODUCT_SIMPLESEARCH ="10010405";
	public static final String STATISTIC_COMMON_PRODUCT_LIST ="10010406";
	//奖励统计
	public static final String STATISTIC_COMMON_AWARD ="100105";
	public static final String STATISTIC_COMMON_AWARD_ADD ="10010501";
	public static final String STATISTIC_COMMON_AWARD_MODIFY ="10010502";
	public static final String STATISTIC_COMMON_AWARD_VIEW ="10010503";
	public static final String STATISTIC_COMMON_AWARD_DELETE ="10010504";
	public static final String STATISTIC_COMMON_AWARD_SIMPLESEARCH ="10010505";
	public static final String STATISTIC_COMMON_AWARD_LIST ="10010506";
	
	//定制统计
	public static final String STATISTIC_CUSTOM ="1002";
	public static final String STATISTIC_CUSTOM_PERSON ="100201";
	public static final String STATISTIC_CUSTOM_UNIT ="100202";
	public static final String STATISTIC_CUSTOM_PROJECT ="100203";
	public static final String STATISTIC_CUSTOM_PRODUCT ="100204";
	public static final String STATISTIC_CUSTOM_AWARD ="100205";
	
	// 弹出层
	public static final String POP ="11";
	// 弹出层查看
	public static final String POP_VIEW ="1101";
	
	public static final String POP_VIEW_ACCOUNT ="110101";
	public static final String POP_VIEW_PERSON ="110102";
	public static final String POP_VIEW_AGENCY ="110103";
	public static final String POP_VIEW_PROJECT ="110104";
	public static final String POP_VIEW_LOG ="110105";
	public static final String POP_VIEW_MEMO ="110106";
	
	// 弹出层选择
	public static final String POP_SELECT ="1102";
	// 机构
	public static final String POP_SELECT_MINISTRY ="110201";
	public static final String POP_SELECT_MINISTRY_LIST ="11020101";
	public static final String POP_SELECT_MINISTRY_SIMPLESEARCH ="11020102";
	public static final String POP_SELECT_PROVINCE ="110202";
	public static final String POP_SELECT_PROVINCE_LIST ="11020201";
	public static final String POP_SELECT_PROVINCE_SIMPLESEARCH ="11020202";
	public static final String POP_SELECT_UNIVERSITY ="110203";
	public static final String POP_SELECT_UNIVERSITY_LIST ="11020301";
	public static final String POP_SELECT_UNIVERSITY_SIMPLESEARCH ="11020302";
	public static final String POP_SELECT_MINISTRYGROUP ="110204";
	public static final String POP_SELECT_MINISTRYGROUP_LIST ="11020401";
	public static final String POP_SELECT_MINISTRYGROUP_SIMPLESEARCH ="11020402";
	public static final String POP_SELECT_PROVINCEGROUP ="110205";
	public static final String POP_SELECT_PROVINCEGROUP_LIST ="11020501";
	public static final String POP_SELECT_PROVINCEGROUP_SIMPLESEARCH ="11020502";
	public static final String POP_SELECT_UNIVERSITYGROUP ="110206";
	public static final String POP_SELECT_UNIVERSITYGROUP_LIST ="11020601";
	public static final String POP_SELECT_UNIVERSITYGROUP_SIMPLESEARCH ="11020602";
	public static final String POP_SELECT_DEPARTMENT ="110207";
	public static final String POP_SELECT_DEPARTMENT_LIST ="11020701";
	public static final String POP_SELECT_DEPARTMENT_SIMPLESEARCH ="11020702";
	public static final String POP_SELECT_INSTITUTE ="110208";
	public static final String POP_SELECT_INSTITUTE_LIST ="11020801";
	public static final String POP_SELECT_INSTITUTE_SIMPLESEARCH ="11020802";
	// 人员
	public static final String POP_SELECT_OFFICER ="110209";
	public static final String POP_SELECT_OFFICER_LIST ="11020901";
	public static final String POP_SELECT_OFFICER_SIMPLESEARCH ="11020902";
	public static final String POP_SELECT_EXPERT ="110210";
	public static final String POP_SELECT_EXPERT_LIST ="11021001";
	public static final String POP_SELECT_EXPERT_SIMPLESEARCH ="11021002";
	public static final String POP_SELECT_TEACHER ="110211";
	public static final String POP_SELECT_TEACHER_LIST ="11021101";
	public static final String POP_SELECT_TEACHER_SIMPLESEARCH ="11021102";
	public static final String POP_SELECT_STUDENT ="110212";
	public static final String POP_SELECT_STUDENT_LIST ="11021201";
	public static final String POP_SELECT_STUDENT_SIMPLESEARCH ="11021202";
	// 项目成果奖励
	public static final String POP_SELECT_PROJECT ="110213";
	public static final String POP_SELECT_PROJECT_LIST ="11021301";
	public static final String POP_SELECT_PROJECT_SIMPLESEARCH ="11021302";
	public static final String POP_SELECT_PUBLICATION ="110214";
	public static final String POP_SELECT_PUBLICATION_LIST ="11021401";
	public static final String POP_SELECT_PUBLICATION_SIMPLESEARCH ="11021402";
	public static final String POP_SELECT_UNGRANTEDPROJECT ="110221";
	public static final String POP_SELECT_UNGRANTEDPROJECT_LIST ="11022101";
	public static final String POP_SELECT_UNGRANTEDPROJECT_SIMPLESEARCH ="11022102";
	// 其他信息
	public static final String POP_SELECT_DISCIPLINETYPE ="110215";
	public static final String POP_SELECT_DISCIPLINETYPE_SIMPLESEARCH ="11021501";
	public static final String POP_SELECT_RELYDISCIPLINES ="110216";
	public static final String POP_SELECT_RELYDISCIPLINES_TOSELECT ="11021601";
	public static final String POP_SELECT_ETHNICLANGUAGE ="1102017";
	public static final String POP_SELECT_ETHNICLANGUAGE_TOSELECT ="110201701";
	public static final String POP_SELECT_FOREIGNLANGUAGE ="110218";
	public static final String POP_SELECT_FOREIGNLANGUAGE_TOSELECT ="11021801";
	public static final String POP_SELECT_INDEXTYPE ="110219";
	public static final String POP_SELECT_INDEXTYPE_TOSELECT ="11021901";
	public static final String POP_SELECT_PRODUCTTYPE ="110220";
	public static final String POP_SELECT_PRODUCTTYPE_TOSELECT ="11022001";
	
	//终端12
	public static final String MOBILE ="12";
	//终端登陆相关1201
	public static final String MOBILE_LOG ="1201";
	public static final String MOBILE_LOGIN ="120101";
	public static final String MOBILE_LOGOUT ="120102";
	public static final String MOBILE_ACCOUNT_SWITCH ="120103";
	public static final String MOBILE_ACCOUNT_SELECT ="120104";
	public static final String MOBILE_CURRENT_ACCOUNT ="120105";
	public static final String MOBILE_VIEW_MYSELF ="120106";
	public static final String MOBILE_MODIFY_MYSELF ="120107";
	public static final String MOBILE_VIEW_TODO ="120108";
	public static final String MOBILE_VIEW_PROJECT ="120109";
	public static final String MOBILE_VIEW_PRODUCT ="120110";
	public static final String MOBILE_VIEW_AWARD ="120111";
	public static final String MOBILE_RESET_PASSWORD ="120112";
	public static final String MOBILE_CHECK_UPDATE ="120113";
	public static final String MOBILE_GET_FEEDBACK ="120114";
	public static final String MOBILE_GET_HELP ="120115";
	public static final String MOBILE_GET_ABOUT ="120116";
	// 终端消息系统1202
	public static final String MOBILE_INFO ="1202";
	public static final String MOBILE_MESSAGE_SIMPLESEARCH ="120201";
	public static final String MOBILE_MESSAGE_VIEW ="120202";
	public static final String MOBILE_MESSAGE_TOPAGE ="120203";
	public static final String MOBILE_MESSAGE_ADD ="120204";
	public static final String MOBILE_MESSAGE_MODIFY ="120205";
	public static final String MOBILE_MESSAGE_DELETE ="120206";
	public static final String MOBILE_NEWS_SIMPLESEARCH ="120207";
	public static final String MOBILE_NEWS_VIEW ="120208";
	public static final String MOBILE_NEWS_TOPAGE ="120209";
	public static final String MOBILE_NOTICE_SIMPLESEARCH ="120210";
	public static final String MOBILE_NOTICE_VIEW ="120211";
	public static final String MOBILE_NOTICE_TOPAGE ="120212";
	//终端统计分析1203
	public static final String MOBILE_STATISTIC ="1203";
	public static final String MOBILE_STATISTIC_COMMON_SIMPLESEARCH ="120301";
	public static final String MOBILE_STATISTIC_COMMON_ADVSEARCH ="120302";
	public static final String MOBILE_STATISTIC_COMMON_VIEW ="120303";
	public static final String MOBILE_STATISTIC_COMMON_TOPAGE ="120304";
	public static final String MOBILE_STATISTIC_COMMON_FETCHMENU ="120305";
	public static final String MOBILE_STATISTIC_COMMON_ADD ="120306";
	public static final String MOBILE_STATISTIC_COMMON_MODIFY ="120307";
	public static final String MOBILE_STATISTIC_CUSTOM_VIEW ="120308";
	public static final String MOBILE_STATISTIC_CUSTOM_FETCHMENU ="120309";
	public static final String MOBILE_STATISTIC_CUSTOM_SELECTUNIVERSITY ="120310";
	public static final String MOBILE_STATISTIC_CUSTOM_SELECTUNIVERSITY_TOPAGE ="120311";
	public static final String MOBILE_STATISTIC_CUSTOM_GETMDX ="120312";
	//终端基础数据库1204
	public static final String MOBILE_BASIS ="1204";
	public static final String MOBILE_PERSON_SIMPLESEARCH ="120401";
	public static final String MOBILE_PERSON_ADVSEARCH ="120402";
	public static final String MOBILE_PERSON_VIEW ="120403";
	public static final String MOBILE_PERSON_TOPAGE ="120404";
	public static final String MOBILE_PERSON_FETCHMENU ="120405";
	public static final String MOBILE_UNIT_SIMPLESEARCH ="120406";
	public static final String MOBILE_UNIT_ADVSEARCH ="120407";
	public static final String MOBILE_UNIT_VIEW ="120408";
	public static final String MOBILE_UNIT_TOPAGE ="120409";
	public static final String MOBILE_UNIT_FETCHMENU ="120410";
	public static final String MOBILE_PROJECT_SIMPLESEARCH ="120411";
	public static final String MOBILE_PROJECT_ADVSEARCH ="120412";
	public static final String MOBILE_PROJECT_VIEW ="120413";
	public static final String MOBILE_PROJECT_TOPAGE ="120414";
	public static final String MOBILE_PROJECT_FETCHMENU ="120415";
	public static final String MOBILE_PROJECT_VIEWAUDIT ="120416";
	public static final String MOBILE_PROJECT_AUDIT ="120417";
	public static final String MOBILE_PROJECT_TODO ="120418";
	public static final String MOBILE_PROJECTTODO_TOPAGE ="120419";
	public static final String MOBILE_PRODUCT_SIMPLESEARCH ="120420";
	public static final String MOBILE_PRODUCT_ADVSEARCH ="120421";
	public static final String MOBILE_PRODUCT_VIEW ="120422";
	public static final String MOBILE_PRODUCT_TOPAGE ="120423";
	public static final String MOBILE_PRODUCT_FETCHMENU ="120424";
	public static final String MOBILE_PRODUCT_VIEWAUDIT ="120425";
	public static final String MOBILE_PRODUCT_AUDIT ="120426";
	public static final String MOBILE_AWARD_SIMPLESEARCH ="120427";
	public static final String MOBILE_AWARD_ADVSEARCH ="120428";
	public static final String MOBILE_AWARD_VIEW ="120429";
	public static final String MOBILE_AWARD_TOPAGE ="120430";
	public static final String MOBILE_AWARD_FETCHMENU ="120431";
	public static final String MOBILE_AWARD_VIEWAUDIT ="120432";
	public static final String MOBILE_AWARD_AUDIT ="120433";
	public static final String MOBILE_AWARD_MODIFY ="120434";
	
	public static final String DM = "13";
	public static final String DM_UNIVERSITY_LIST = "1301";
	
	//经费管理
	public static final String FEE = "13";
	public static final String PROJECTFUND_GENERAL_LIST = "130101";
	public static final String FUNDLIST_GENERAL_GRANTED_LIST = "130102";
	public static final String FUNDLIST_GENERAL_MID_LIST = "130103";
	public static final String FUNDLIST_GENERAL_END_LIST = "130104";
	
	public static final String PROJECTFUND_KEY__LIST = "130201";
	public static final String FUNDLIST_KEY_GRANTED_LIST = "130202";
	public static final String FUNDLIST_KEY_MID_LIST = "130203";
	public static final String FUNDLIST_KEY_END_LIST = "130204";
	
	public static final String PROJECTFUND_POST__LIST = "130301";
	public static final String FUNDLIST_POST_GRANTED_LIST = "130302";
	public static final String FUNDLIST_POST_END_LIST = "130303";
	
	public static final String PROJECTFUND_INSTP_LIST = "130101";
	public static final String FUNDLIST_INSTP_GRANTED_LIST = "130102";
	public static final String FUNDLIST_INSTP_MID_LIST = "130103";
	public static final String FUNDLIST_INSTP_END_LIST = "130104";
	
	public static final String PROJECTFUND_ENTRUSTT__LIST = "130301";
	public static final String FUNDLIST_ENTRUST_GRANTED_LIST = "130302";
	public static final String FUNDLIST_ENTRUST_END_LIST = "130303";
	
	
	/**
	 * URL及其代码，URL指去除了上下文根"smdb"及".action"后的字符串，
	 * 代码仿造树的结构生成，每两位一级。
	 */
	private static final String[][] LOG_CODE_URL = {
		// 登录前暂无00
		// 登录相关0101
		{LOGIN, "login/doLogin", "登录系统"},
		// 子系统010101
		{LOGIN_SERVER_SELECT, "login/toSelectServer", "选择子系统"},
		{LOGIN_SERVER_SWITCH, "login/toSwitchServer", "切换子系统"},
		{LOGIN_ACCOUNT_SWITCH, "login/switchAccount", "切换账号"},
		{LOGIN_ACCOUNT_SELECT, "login/selectAccount", "选择账号"},
		{LOGIN_ACCOUNT_CKECK, "login/ckeckAccount", "切换账号"},
		// 选择子系统010102
		{LOGIN_RIGHT_BASIS, "login/basisRight", "进入基础数据库系统"},
		{LOGIN_RIGHT_STATISTIC, "login/statisticRight", "进入统计分析与决策系统"},
		{LOGIN_RIGHT_PROJECT, "login/projectRight", "进入项目管理系统"},
		{LOGIN_RIGHT_EXPERT, "login/expertRight", "进入专家数据库系统"},
		{LOGIN_RIGHT_UCENTER, "login/ucenterRight", "进入用户信息中心"},
		{LOGIN_RIGHT_SCENTER, "login/scenterRight", "进入系统管控中心"},
		{LOGIN_RIGHT_AWARD, "login/awardRight", "进入奖励管理系统"},
		// 登出 0102
		{LOGOUT, "login/logout", "退出系统"},// 退出系统实际上要在session销毁时记录
		// 个人相关02
		{SELF_ACCOUNT_VIEW, "selfspace/viewSelfAccount", "查看我的账号信息"}, 
		{SELF_ACCOUNT_MODIFY, "selfspace/modifySelfAccount", "修改我的账号信息"},
		{SELF_ACCOUNT_MODIFYPASSWORD, "selfspace/modifyPassword", "修改我的账号密码"},
		{SELF_ACCOUNT_BINDEMAIL, "selfspace/bindEmail", "绑定我的邮件"},
		{SELF_ACCOUNT_BINDPHONE, "selfspace/bindPhone", "绑定我的电话"},
		// 系统功能03
		// 新闻0301
		{SYSTEM_NEWS_INNER_ADD, "news/inner/add", "添加新闻"},
		{SYSTEM_NEWS_INNER_DELETE, "news/inner/delete", "删除新闻"},
		{SYSTEM_NEWS_INNER_MODIFY, "news/inner/modify", "修改新闻"},
		{SYSTEM_NEWS_INNER_VIEW, "news/inner/view", "查看新闻"},
		{SYSTEM_NEWS_INNER_SIMPLESEARCH, "news/inner/simpleSearch", "初级检索新闻"},
		{SYSTEM_NEWS_INNER_ADVSEARCH, "news/inner/advSearch", "高级检索新闻"},
		{SYSTEM_NEWS_INNER_DOWNLOAD, "news/inner/download", "下载新闻附件"},
		{SYSTEM_NEWS_INNER_LIST, "news/inner/list", "进入新闻列表"},
		{SYSTEM_NEWS_INNER_TOLIST,"news/inner/toList", "news/inner/toList.action?update=1", "新闻列表"},
		
		// 通知0302
		{SYSTEM_NOTICE_INNER_ADD, "notice/inner/add", "添加通知"},
		{SYSTEM_NOTICE_INNER_DELETE, "notice/inner/delete", "删除通知"},
		{SYSTEM_NOTICE_INNER_MODIFY, "notice/inner/modify", "修改通知"},
		{SYSTEM_NOTICE_INNER_VIEW, "notice/inner/view", "查看通知"},
		{SYSTEM_NOTICE_INNER_SIMPLESEARCH, "notice/inner/simpleSearch", "初级检索通知"},
		{SYSTEM_NOTICE_INNER_ADVSEARCH, "notice/inner/advSearch", "高级检索通知"},
		{SYSTEM_NOTICE_INNER_DOWNLOAD, "notice/inner/download", "下载通知附件"},
		{SYSTEM_NOTICE_INNER_LIST, "notice/inner/list", "进入通知列表"},
		{SYSTEM_NOTICE_INNER_TOLIST, "notice/inner/toList", "notice/inner/toList.action?update=1","通知列表"},
		
		
		// 留言0303
		{SYSTEM_MESSAGE_INNER_ADD, "message/inner/add", "添加留言"},
		{SYSTEM_MESSAGE_INNER_DELETE, "message/inner/delete", "删除留言"},
		{SYSTEM_MESSAGE_INNER_MODIFY, "message/inner/modify", "修改留言"},
		{SYSTEM_MESSAGE_INNER_VIEW, "message/inner/view", "查看留言"},
		{SYSTEM_MESSAGE_INNER_SIMPLESEARCH, "message/inner/simpleSearch", "初级检索留言"},
		{SYSTEM_MESSAGE_INNER_ADVSEARCH, "message/inner/advSearch", "高级检索留言"},
		{SYSTEM_MESSAGE_INNER_TOGGLEOPEN, "message/inner/toggleOpen", "切换留言内外网可见"},
		{SYSTEM_MESSAGE_INNER_LIST, "message/inner/list", "进入留言列表"},
		{SYSTEM_MESSAGE_INNER_TOLIST, "message/inner/toList","message/inner/toList.action?update=1", "留言列表"},
		// 邮件0304
		{SYSTEM_MAIL_ADD, "mail/add", "添加邮件"},
		{SYSTEM_MAIL_DELETE, "mail/delete", "删除邮件"},
		{SYSTEM_MAIL_PAUSESEND, "mail/pauseSend", "暂停发送邮件"},
		{SYSTEM_MAIL_SEND, "mail/send", "手动发送邮件"},
		{SYSTEM_MAIL_SENDAGAIN, "mail/sendAgain", "重新发送邮件"},
		{SYSTEM_MAIL_VIEW, "mail/view", "查看邮件"},
		{SYSTEM_MAIL_SIMPLESEARCH, "mail/simpleSearch", "初级检索邮件"},
		{SYSTEM_MAIL_ADVSEARCH, "mail/advSearch", "高级检索邮件"},
		{SYSTEM_MAIL_DOWNLOAD, "mail/download", "下载留言附件"},
		{SYSTEM_MAIL_LIST, "mail/list", "进入邮件列表"},
		{SYSTEM_MAIL_CANCEL, "mail/cancel", "取消邮件发送"},
		{SYSTEM_MAIL_LIST, "mail/toList", "mail/toList.action?update=1","系统邮件"},
		
		// 系统日志0305
		{SYSTEM_LOG_DELETE, "log/delete", "删除日志"},
		{SYSTEM_LOG_VIEW, "log/view", "查看日志"},
		{SYSTEM_LOG_SIMPLESEARCH, "log/simpleSearch", "初级检索日志"},
		{SYSTEM_LOG_ADVSEARCH, "log/advSearch", "高级检索日志"},
		{SYSTEM_LOG_LIST, "log/list", "进入日志列表"},
		{SYSTEM_LOG_STATISTIC, "log/statistic", "统计日志数量"},
		{SYSTEM_LOG_LIST, "log/toList","log/toList.action?update=1", "系统日志"},
		
		//访客监控0306
		{SYSTEM_MONITOR_VISITOR_EVICT,"system/monitor/visitor/evict","踢出访客"},
		{SYSTEM_MONITOR_VISITOR_LIST,"system/monitor/visitor/list","查看监控访客"},
		{SYSTEM_MONITOR_VISITOR_LIST,"system/monitor/visitor/toList","system/monitor/visitor/toList.action?update=1","访客监控"},
	
		//站内信0307
		{SYSTEM_INBOX_LIST, "inBox/list", "进入站内信列表"},
		{SYSTEM_INBOX_TOLIST, "inBox/list","inBox/toList.action?update=1", "站内信"},
		{SYSTEM_INBOX_ADD, "inBox/add", "添加站内信"},
		{SYSTEM_INBOX_DELETE, "inBox/delete", "删除站内信"},
		{SYSTEM_INBOX_REMIND, "inBox/remind", "提醒站内信"},
		{SYSTEM_INBOX_VIEW, "inBox/view", "查看站内信"},
		// 安全认证04
		// 角色0401
		{SECURITY_ROLE_ADD, "role/add", "添加角色"},
		{SECURITY_ROLE_DELETE, "role/delete", "删除角色"},
		{SECURITY_ROLE_MODIFY, "role/modify", "修改角色"},
		{SECURITY_ROLE_VIEW, "role/view", "查看角色"},
		{SECURITY_ROLE_SIMPLESEARCH, "role/simpleSearch", "初级检索角色"},
		{SECURITY_ROLE_ADVSEARCH, "role/advSearch", "高级检索角色"},
		{SECURITY_ROLE_LIST, "role/list", "进入角色列表"},
		{SECURITY_ROLE_LIST, "role/toList","role/toList.action?update=1", "系统角色"},
		
		// 权限0402
		{SECURITY_RIGHT_ADD, "right/add", "添加权限"},
		{SECURITY_RIGHT_DELETE, "right/delete", "删除权限"},
		{SECURITY_RIGHT_MODIFY, "right/modify", "修改权限"},
		{SECURITY_RIGHT_VIEW, "right/view", "查看权限"},
		{SECURITY_RIGHT_SIMPLESEARCH, "right/simpleSearch", "初级检索权限"},
		{SECURITY_RIGHT_ADVSEARCH, "right/advSearch", "高级检索权限"},
		{SECURITY_RIGHT_LIST, "right/list", "进入权限列表"},
		{SECURITY_RIGHT_LIST, "right/toList","right/toList.action?update=1", "系统权限"},
		
		// 账号0403
		// 部级账号040301
		// 主账号04030101
		{SECURITY_ACCOUNT_MINISTRY_MAIN_ADD, "account/ministry/main/add", "添加部级主账号"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_DELETE, "account/ministry/main/delete", "删除部级主账号"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_MODIFY, "account/ministry/main/modify", "修改部级主账号"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_VIEW, "account/ministry/main/view", "查看部级主账号"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_SIMPLESEARCH, "account/ministry/main/simpleSearch", "初级检索部级主账号"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_ADVSEARCH, "account/ministry/main/advSearch", "高级检索部级主账号"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_ENABLE, "account/ministry/main/enable", "启用部级主账号"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_DISABLE, "account/ministry/main/disable", "停用部级主账号"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_ASSIGNROLE, "account/ministry/main/assignRole", "分配部级主账号角色"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_RETRIEVECODE, "account/ministry/main/retrieveCode", "重置部级主账号密码"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_MODIFYPASSWORD, "account/ministry/main/modifyPassword", "修改部级主账号密码"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_EXTIFADD, "account/ministry/main/extIfAdd", "分配部级主账号"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_LIST, "account/ministry/main/list", "进入部级主账号列表"},
		{SECURITY_ACCOUNT_MINISTRY_MAIN_LIST, "account/ministry/main/toList", "account/ministry/main/toList.action?update=1","部级主账号"},
		
		// 子账号04030102
		{SECURITY_ACCOUNT_MINISTRY_SUB_ADD, "account/ministry/sub/add", "添加部级子账号"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_DELETE, "account/ministry/sub/delete", "删除部级子账号"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_MODIFY, "account/ministry/sub/modify", "修改部级子账号"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_VIEW, "account/ministry/sub/view", "查看部级子账号"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_SIMPLESEARCH, "account/ministry/sub/simpleSearch", "初级检索部级子账号"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_ADVSEARCH, "account/ministry/sub/advSearch", "高级检索部级子账号"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_ENABLE, "account/ministry/sub/enable", "启用部级子账号"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_DISABLE, "account/ministry/sub/disable", "停用部级子账号"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_ASSIGNROLE, "account/ministry/sub/assignRole", "分配部级子账号角色"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_RETRIEVECODE, "account/ministry/sub/retrieveCode", "重置部级子账号密码"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_MODIFYPASSWORD, "account/ministry/sub/modifyPassword", "修改部级子账号密码"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_EXTIFADD, "account/ministry/sub/extIfAdd", "分配部级子账号"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_LIST, "account/ministry/sub/list", "进入部级子账号列表"},
		{SECURITY_ACCOUNT_MINISTRY_SUB_LIST, "account/ministry/sub/toList", "account/ministry/sub/toList.action?update=1","部级子账号"},
		
		// 省级账号040302
		// 主账号04030201
		{SECURITY_ACCOUNT_PROVINCE_MAIN_ADD, "account/province/main/add", "添加省级主账号"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_DELETE, "account/province/main/delete", "删除省级主账号"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_MODIFY, "account/province/main/modify", "修改省级主账号"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_VIEW, "account/province/main/view", "查看省级主账号"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_SIMPLESEARCH, "account/province/main/simpleSearch", "初级检索省级主账号"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_ADVSEARCH, "account/province/main/advSearch", "高级检索省级主账号"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_ENABLE, "account/province/main/enable", "启用省级主账号"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_DISABLE, "account/province/main/disable", "停用省级主账号"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_ASSIGNROLE, "account/province/main/assignRole", "分配省级主账号角色"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_RETRIEVECODE, "account/province/main/retrieveCode", "重置省级主账号密码"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_MODIFYPASSWORD, "account/province/main/modifyPassword", "修改省级主账号密码"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_EXTIFADD, "account/province/main/extIfAdd", "分配省级主账号"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_LIST, "account/province/main/list", "进入省级主账号列表"},
		{SECURITY_ACCOUNT_PROVINCE_MAIN_LIST, "account/province/main/toList","account/province/main/toList.action?update=1", "省级主账号"},
		
		// 子账号04030202
		{SECURITY_ACCOUNT_PROVINCE_SUB_ADD, "account/province/sub/add", "添加省级子账号"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_DELETE, "account/province/sub/delete", "删除省级子账号"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_MODIFY, "account/province/sub/modify", "修改省级子账号"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_VIEW, "account/province/sub/view", "查看省级子账号"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_SIMPLESEARCH, "account/province/sub/simpleSearch", "初级检索省级子账号"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_ADVSEARCH, "account/province/sub/advSearch", "高级检索省级子账号"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_ENABLE, "account/province/sub/enable", "启用省级子账号"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_DISABLE, "account/province/sub/disable", "停用省级子账号"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_ASSIGNROLE, "account/province/sub/assignRole", "分配省级子账号角色"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_RETRIEVECODE, "account/province/sub/retrieveCode", "重置省级子账号密码"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_MODIFYPASSWORD, "account/province/sub/modifyPassword", "修改省级子账号密码"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_EXTIFADD, "account/province/sub/extIfAdd", "分配省级子账号"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_LIST, "account/province/sub/list", "进入省级子账号列表"},
		{SECURITY_ACCOUNT_PROVINCE_SUB_LIST, "account/province/sub/toList","account/province/sub/toList.action?update=1", "省级子账号"},
		
		// 校级账号040303
		// 主账号04030301
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_ADD, "account/university/main/add", "添加校级主账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_DELETE, "account/university/main/delete", "删除校级主账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_MODIFY, "account/university/main/modify", "修改校级主账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_VIEW, "account/university/main/view", "查看校级主账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_SIMPLESEARCH, "account/university/main/simpleSearch", "初级检索校级主账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_ADVSEARCH, "account/university/main/advSearch", "高级检索校级主账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_ENABLE, "account/university/main/enable", "启用校级主账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_DISABLE, "account/university/main/disable", "停用校级主账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_ASSIGNROLE, "account/university/main/assignRole", "分配校级主账号角色"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_RETRIEVECODE, "account/university/main/retrieveCode", "重置校级主账号密码"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_MODIFYPASSWORD, "account/university/main/modifyPassword", "修改校级主账号密码"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_EXTIFADD, "account/university/main/extIfAdd", "分配校级主账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_LIST, "account/university/main/list", "进入校级主账号列表"},
		{SECURITY_ACCOUNT_UNIVERSITY_MAIN_LIST, "account/university/main/toList","account/university/main/toList.action?update=1", "校级主账号"},
		
		
		// 子账号04030302
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_ADD, "account/university/sub/add", "添加校级子账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_DELETE, "account/university/sub/delete", "删除校级子账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_MODIFY, "account/university/sub/modify", "修改校级子账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_VIEW, "account/university/sub/view", "查看校级子账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_SIMPLESEARCH, "account/university/sub/simpleSearch", "初级检索校级子账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_ADVSEARCH, "account/university/sub/advSearch", "高级检索校级子账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_ENABLE, "account/university/sub/enable", "启用校级子账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_DISABLE, "account/university/sub/disable", "停用校级子账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_ASSIGNROLE, "account/university/sub/assignRole", "分配校级子账号角色"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_RETRIEVECODE, "account/university/sub/retrieveCode", "重置校级子账号密码"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_MODIFYPASSWORD, "account/university/sub/modifyPassword", "修改校级子账号密码"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_EXTIFADD, "account/university/sub/extIfAdd", "分配校级子账号"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_LIST, "account/university/sub/list", "进入校级子账号列表"},
		{SECURITY_ACCOUNT_UNIVERSITY_SUB_LIST, "account/university/sub/toList","account/university/sub/toList.action?update=1", "校级子账号"},
		
		// 院系账号040304
		// 主账号04030401
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_ADD, "account/department/main/add", "添加院系主账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_DELETE, "account/department/main/delete", "删除院系主账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_MODIFY, "account/department/main/modify", "修改院系主账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_VIEW, "account/department/main/view", "查看院系主账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_SIMPLESEARCH, "account/department/main/simpleSearch", "初级检索院系主账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_ADVSEARCH, "account/department/main/advSearch", "高级检索院系主账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_ENABLE, "account/department/main/enable", "启用院系主账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_DISABLE, "account/department/main/disable", "停用院系主账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_ASSIGNROLE, "account/department/main/assignRole", "分配院系主账号角色"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_RETRIEVECODE, "account/department/main/retrieveCode", "重置院系主账号密码"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_MODIFYPASSWORD, "account/department/main/modifyPassword", "修改院系主账号密码"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_EXTIFADD, "account/department/main/extIfAdd", "分配院系主账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_LIST, "account/department/main/list", "进入院系主账号列表"},
		{SECURITY_ACCOUNT_DEPARTMENT_MAIN_LIST, "account/department/main/toList", "account/department/main/toList.action?update=1","院系主账号"},
		
		// 子账号04030402
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_ADD, "account/department/sub/add", "添加院系子账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_DELETE, "account/department/sub/delete", "删除院系子账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_MODIFY, "account/department/sub/modify", "修改院系子账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_VIEW, "account/department/sub/view", "查看院系子账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_SIMPLESEARCH, "account/department/sub/simpleSearch", "初级检索院系子账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_ADVSEARCH, "account/department/sub/advSearch", "高级检索院系子账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_ENABLE, "account/department/sub/enable", "启用院系子账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_DISABLE, "account/department/sub/disable", "停用院系子账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_ASSIGNROLE, "account/department/sub/assignRole", "分配院系子账号角色"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_RETRIEVECODE, "account/department/sub/retrieveCode", "重置院系子账号密码"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_MODIFYPASSWORD, "account/department/sub/modifyPassword", "修改院系子账号密码"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_EXTIFADD, "account/department/sub/extIfAdd", "分配院系子账号"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_LIST, "account/department/sub/list", "进入院系子账号列表"},
		{SECURITY_ACCOUNT_DEPARTMENT_SUB_LIST, "account/department/sub/toList", "account/department/sub/toList.action?update=1","院系子账号"},
		
		// 基地账号040305
		// 主账号04030501
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_ADD, "account/institute/main/add", "添加基地主账号"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_DELETE, "account/institute/main/delete", "删除基地主账号"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_MODIFY, "account/institute/main/modify", "修改基地主账号"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_VIEW, "account/institute/main/view", "查看基地主账号"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_SIMPLESEARCH, "account/institute/main/simpleSearch", "初级检索基地主账号"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_ADVSEARCH, "account/institute/main/advSearch", "高级检索基地主账号"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_ENABLE, "account/institute/main/enable", "启用基地主账号"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_DISABLE, "account/institute/main/disable", "停用基地主账号"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_ASSIGNROLE, "account/institute/main/assignRole", "分配基地主账号角色"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_RETRIEVECODE, "account/institute/main/retrieveCode", "重置基地主账号密码"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_MODIFYPASSWORD, "account/institute/main/modifyPassword", "修改基地主账号密码"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_EXTIFADD, "account/institute/main/extIfAdd", "分配基地主账号"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_LIST, "account/institute/main/list", "进入基地主账号列表"},
		{SECURITY_ACCOUNT_INSTITUTE_MAIN_LIST, "account/institute/main/toList", "account/institute/main/toList.action?update=1","基地主账号"},
		
		// 子账号04030502
		{SECURITY_ACCOUNT_INSTITUTE_SUB_ADD, "account/institute/sub/add", "添加基地子账号"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_DELETE, "account/institute/sub/delete", "删除基地子账号"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_MODIFY, "account/institute/sub/modify", "修改基地子账号"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_VIEW, "account/institute/sub/view", "查看基地子账号"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_SIMPLESEARCH, "account/institute/sub/simpleSearch", "初级检索基地子账号"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_ADVSEARCH, "account/institute/sub/advSearch", "高级检索基地子账号"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_ENABLE, "account/institute/sub/enable", "启用基地子账号"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_DISABLE, "account/institute/sub/disable", "停用基地子账号"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_ASSIGNROLE, "account/institute/sub/assignRole", "分配基地子账号角色"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_RETRIEVECODE, "account/institute/sub/retrieveCode", "重置基地子账号密码"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_MODIFYPASSWORD, "account/institute/sub/modifyPassword", "修改基地子账号密码"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_EXTIFADD, "account/institute/sub/extIfAdd", "分配基地子账号"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_LIST, "account/institute/sub/list", "进入基地子账号列表"},
		{SECURITY_ACCOUNT_INSTITUTE_SUB_LIST, "account/institute/sub/toList", "account/institute/sub/toList.action?update=1","基地子账号"},
	
		// 外部专家账号040306
		{SECURITY_ACCOUNT_EXPERT_ADD, "account/expert/add", "添加外部专家账号"},
		{SECURITY_ACCOUNT_EXPERT_DELETE, "account/expert/delete", "删除外部专家账号"},
		{SECURITY_ACCOUNT_EXPERT_MODIFY, "account/expert/modify", "修改外部专家账号"},
		{SECURITY_ACCOUNT_EXPERT_VIEW, "account/expert/view", "查看外部专家账号"},
		{SECURITY_ACCOUNT_EXPERT_SIMPLESEARCH, "account/expert/simpleSearch", "初级检索外部专家账号"},
		{SECURITY_ACCOUNT_EXPERT_ADVSEARCH, "account/expert/advSearch", "高级检索外部专家账号"},
		{SECURITY_ACCOUNT_EXPERT_ENABLE, "account/expert/enable", "启用外部专家账号"},
		{SECURITY_ACCOUNT_EXPERT_DISABLE, "account/expert/disable", "停用外部专家账号"},
		{SECURITY_ACCOUNT_EXPERT_ASSIGNROLE, "account/expert/assignRole", "分配外部专家账号角色"},
		{SECURITY_ACCOUNT_EXPERT_RETRIEVECODE, "account/expert/retrieveCode", "重置外部专家账号密码"},
		{SECURITY_ACCOUNT_EXPERT_MODIFYPASSWORD, "account/expert/modifyPassword", "修改外部专家账号密码"},
		{SECURITY_ACCOUNT_EXPERT_EXTIFADD, "account/expert/extIfAdd", "分配外部专家账号"},
		{SECURITY_ACCOUNT_EXPERT_LIST, "account/expert/list", "进入外部专家账号列表"},
		{SECURITY_ACCOUNT_EXPERT_LIST, "account/expert/toList","account/expert/toList.action?update=1", "外部专家账号"},
		
		// 教师账号040307
		{SECURITY_ACCOUNT_TEACHER_ADD, "account/teacher/add", "添加教师账号"},
		{SECURITY_ACCOUNT_TEACHER_DELETE, "account/teacher/delete", "删除教师账号"},
		{SECURITY_ACCOUNT_TEACHER_MODIFY, "account/teacher/modify", "修改教师账号"},
		{SECURITY_ACCOUNT_TEACHER_VIEW, "account/teacher/view", "查看教师账号"},
		{SECURITY_ACCOUNT_TEACHER_SIMPLESEARCH, "account/teacher/simpleSearch", "初级检索教师账号"},
		{SECURITY_ACCOUNT_TEACHER_ADVSEARCH, "account/teacher/advSearch", "高级检索教师账号"},
		{SECURITY_ACCOUNT_TEACHER_ENABLE, "account/teacher/enable", "启用教师账号"},
		{SECURITY_ACCOUNT_TEACHER_DISABLE, "account/teacher/disable", "停用教师账号"},
		{SECURITY_ACCOUNT_TEACHER_ASSIGNROLE, "account/teacher/assignRole", "分配教师账号角色"},
		{SECURITY_ACCOUNT_TEACHER_RETRIEVECODE, "account/teacher/retrieveCode", "重置教师账号密码"},
		{SECURITY_ACCOUNT_TEACHER_MODIFYPASSWORD, "account/teacher/modifyPassword", "修改教师账号密码"},
		{SECURITY_ACCOUNT_TEACHER_EXTIFADD, "account/teacher/extIfAdd", "分配教师账号"},
		{SECURITY_ACCOUNT_TEACHER_LIST, "account/teacher/list", "进入教师账号列表"},
		{SECURITY_ACCOUNT_TEACHER_LIST, "account/teacher/toList", "account/teacher/toList.action?update=1","教师账号"},
		
		// 学生账号040308
		{SECURITY_ACCOUNT_STUDENT_ADD, "account/student/add", "添加学生账号"},
		{SECURITY_ACCOUNT_STUDENT_DELETE, "account/student/delete", "删除学生账号"},
		{SECURITY_ACCOUNT_STUDENT_MODIFY, "account/student/modify", "修改学生账号"},
		{SECURITY_ACCOUNT_STUDENT_VIEW, "account/student/view", "查看学生账号"},
		{SECURITY_ACCOUNT_STUDENT_SIMPLESEARCH, "account/student/simpleSearch", "初级检索学生账号"},
		{SECURITY_ACCOUNT_STUDENT_ADVSEARCH, "account/student/advSearch", "高级检索学生账号"},
		{SECURITY_ACCOUNT_STUDENT_ENABLE, "account/student/enable", "启用学生账号"},
		{SECURITY_ACCOUNT_STUDENT_DISABLE, "account/student/disable", "停用学生账号"},
		{SECURITY_ACCOUNT_STUDENT_ASSIGNROLE, "account/student/assignRole", "分配学生账号角色"},
		{SECURITY_ACCOUNT_STUDENT_RETRIEVECODE, "account/student/retrieveCode", "重置学生账号密码"},
		{SECURITY_ACCOUNT_STUDENT_MODIFYPASSWORD, "account/student/modifyPassword", "修改学生账号密码"},
		{SECURITY_ACCOUNT_STUDENT_EXTIFADD, "account/student/extIfAdd", "分配学生账号"},
		{SECURITY_ACCOUNT_STUDENT_LIST, "account/student/list", "进入学生账号列表"},
		{SECURITY_ACCOUNT_STUDENT_LIST, "account/student/toList", "account/student/toList.action?update=1","学生账号"},
		
		//通行证管理0405
		{SECURITY_PASSPORT_LIST, "passport/list", "进入通行证列表"},
		{SECURITY_PASSPORT_VIEW, "passport/view", "查看通行证详情"},
		{SECURITY_PASSPORT_SIMPLESEARCH, "passport/simplesearch", "简单检索通行证"},
		{SECURITY_PASSPORT_RETRIEVECODE, "passport/retrievecode", "生成通行证密码重置码"},
		{SECURITY_PASSPORT_MODIFYPASSWORD, "passport/modifypassword", "修改通行证密码"},
		{SECURITY_PASSPORT_MODIFY, "passport/modify", "修改通行证信息"},
		{SECURITY_PASSPORT_DELETE, "passport/modify", "删除通行证"},
		{SECURITY_PASSPORT_LIST, "passport/toList", "passport/toList.action?update=1","通行证管理"},
		
		{DM_UNIVERSITY_LIST, "dm/universityVariation/toList", "dm/universityVariation/toList.action?update=1","高校变更"},
		
		{DM_UNIVERSITY_LIST, "system/option/toView", "system/option/toView.action?update=1","系统选项表"},
		
		
		// 人员05
		// 部级管理人员0501
		{PERSON_OFFICER_MINISTRY_ADD, "person/ministryOfficer/add", "添加部级管理人员"},
		{PERSON_OFFICER_MINISTRY_DELETE, "person/ministryOfficer/delete", "删除部级管理人员"},
		{PERSON_OFFICER_MINISTRY_MODIFY, "person/ministryOfficer/modify", "修改部级管理人员"},
		{PERSON_OFFICER_MINISTRY_VIEW, "person/ministryOfficer/view", "查看部级管理人员"},
		{PERSON_OFFICER_MINISTRY_SIMPLESEARCH, "person/ministryOfficer/simpleSearch", "初级检索部级管理人员"},
		{PERSON_OFFICER_MINISTRY_ADVSEARCH, "person/ministryOfficer/advSearch", "高级检索部级管理人员"},
		{PERSON_OFFICER_MINISTRY_LIST, "person/ministryOfficer/list", "进入部级管理人员列表"},
		{PERSON_OFFICER_MINISTRY_MERGE, "person/ministryOfficer/merge", "合并部级管理人员"},
		{PERSON_OFFICER_MINISTRY_TOLIST, "person/ministryOfficer/toList","person/ministryOfficer/toList.action?update=1&unitType=0", "部级管理人员"},
		
		
		// 省级管理人员0502
		{PERSON_OFFICER_PROVINCE_ADD, "person/provinceOfficer/add", "添加省级管理人员"},
		{PERSON_OFFICER_PROVINCE_DELETE, "person/provinceOfficer/delete", "删除省级管理人员"},
		{PERSON_OFFICER_PROVINCE_MODIFY, "person/provinceOfficer/modify", "修改省级管理人员"},
		{PERSON_OFFICER_PROVINCE_VIEW, "person/provinceOfficer/view", "查看省级管理人员"},
		{PERSON_OFFICER_PROVINCE_SIMPLESEARCH, "person/provinceOfficer/simpleSearch", "初级检索省级管理人员"},
		{PERSON_OFFICER_PROVINCE_ADVSEARCH, "person/provinceOfficer/advSearch", "高级检索省级管理人员"},
		{PERSON_OFFICER_PROVINCE_LIST, "person/provinceOfficer/list", "进入省级管理人员列表"},
		{PERSON_OFFICER_PROVINCE_MERGE, "person/provinceOfficer/merge", "合并省级管理人员"},
		{PERSON_OFFICER_PROVINCE_LIST, "person/provinceOfficer/toList","person/provinceOfficer/toList.action?update=1&unitType=1", "省级管理人员"},
		
		
		// 校级管理人员0503
		{PERSON_OFFICER_UNIVERSITY_ADD, "person/universityOfficer/add", "添加校级管理人员"},
		{PERSON_OFFICER_UNIVERSITY_DELETE, "person/universityOfficer/delete", "删除校级管理人员"},
		{PERSON_OFFICER_UNIVERSITY_MODIFY, "person/universityOfficer/modify", "修改校级管理人员"},
		{PERSON_OFFICER_UNIVERSITY_VIEW, "person/universityOfficer/view", "查看校级管理人员"},
		{PERSON_OFFICER_UNIVERSITY_SIMPLESEARCH, "person/universityOfficer/simpleSearch", "初级检索校级管理人员"},
		{PERSON_OFFICER_UNIVERSITY_ADVSEARCH, "person/universityOfficer/advSearch", "高级检索校级管理人员"},
		{PERSON_OFFICER_UNIVERSITY_LIST, "person/universityOfficer/list", "进入校级管理人员列表"},
		{PERSON_OFFICER_UNIVERSITY_MERGE, "person/universityOfficer/merge", "合并校级管理人员"},
		{PERSON_OFFICER_UNIVERSITY_TOLIST, "person/universityOfficer/toList","person/universityOfficer/toList.action?update=1&unitType=2", "校级管理人员"},
		

		// 院系管理人员0504
		{PERSON_OFFICER_DEPARTMENT_ADD, "person/departmentOfficer/add", "添加院系管理人员"},
		{PERSON_OFFICER_DEPARTMENT_DELETE, "person/departmentOfficer/delete", "删除院系管理人员"},
		{PERSON_OFFICER_DEPARTMENT_MODIFY, "person/departmentOfficer/modify", "修改院系管理人员"},
		{PERSON_OFFICER_DEPARTMENT_VIEW, "person/departmentOfficer/view", "查看院系管理人员"},
		{PERSON_OFFICER_DEPARTMENT_SIMPLESEARCH, "person/departmentOfficer/simpleSearch", "初级检索院系管理人员"},
		{PERSON_OFFICER_DEPARTMENT_ADVSEARCH, "person/departmentOfficer/advSearch", "高级检索院系管理人员"},
		{PERSON_OFFICER_DEPARTMENT_LIST, "person/departmentOfficer/list", "进入院系管理人员列表"},
		{PERSON_OFFICER_DEPARTMENT_MERGE, "person/departmentOfficer/merge", "合并院系管理人员"},
		{PERSON_OFFICER_DEPARTMENT_TOLIST, "person/departmentOfficer/toList", "person/departmentOfficer/toList.action?update=1&unitType=3","院系管理人员"},

		// 基地管理人员0505
		{PERSON_OFFICER_INSTITUTE_ADD, "person/instituteOfficer/add", "添加基地管理人员"},
		{PERSON_OFFICER_INSTITUTE_DELETE, "person/instituteOfficer/delete", "删除基地管理人员"},
		{PERSON_OFFICER_INSTITUTE_MODIFY, "person/instituteOfficer/modify", "修改基地管理人员"},
		{PERSON_OFFICER_INSTITUTE_VIEW, "person/instituteOfficer/view", "查看基地管理人员"},
		{PERSON_OFFICER_INSTITUTE_SIMPLESEARCH, "person/instituteOfficer/simpleSearch", "初级检索基地管理人员"},
		{PERSON_OFFICER_INSTITUTE_ADVSEARCH, "person/instituteOfficer/advSearch", "高级检索基地管理人员"},
		{PERSON_OFFICER_INSTITUTE_LIST, "person/instituteOfficer/list", "进入基地管理人员列表"},
		{PERSON_OFFICER_INSTITUTE_MERGE, "person/instituteOfficer/merge", "合并基地管理人员"},
		{PERSON_OFFICER_INSTITUTE_TOLIST, "person/instituteOfficer/toList", "person/instituteOfficer/toList.action?update=1&unitType=4","基地管理人员"},

		// 外部专家0506
		{PERSON_EXPERT_ADD, "person/expert/add", "添加外部专家"},
		{PERSON_EXPERT_DELETE, "person/expert/delete", "删除外部专家"},
		{PERSON_EXPERT_MODIFY, "person/expert/modify", "修改外部专家"},
		{PERSON_EXPERT_VIEW, "person/expert/view", "查看外部专家"},
		{PERSON_EXPERT_SIMPLESEARCH, "person/expert/simpleSearch", "初级检索外部专家"},
		{PERSON_EXPERT_ADVSEARCH, "person/expert/advSearch", "高级检索外部专家"},
		{PERSON_EXPERT_LIST, "person/expert/list", "进入外部专家列表"},
		{PERSON_EXPERT_MERGE, "person/expert/merge", "合并外部专家"},
		{PERSON_EXPERT_LIST, "person/expert/toList", "person/expert/toList.action?update=1","外部专家"},
		
		// 教师0507
		{PERSON_TEACHER_ADD, "person/teacher/add", "添加教师"},
		{PERSON_TEACHER_DELETE, "person/teacher/delete", "删除教师"},
		{PERSON_TEACHER_MODIFY, "person/teacher/modify", "修改教师"},
		{PERSON_TEACHER_VIEW, "person/teacher/view", "查看教师"},
		{PERSON_TEACHER_SIMPLESEARCH, "person/teacher/simpleSearch", "初级检索教师"},
		{PERSON_TEACHER_ADVSEARCH, "person/teacher/advSearch", "高级检索教师"},
		{PERSON_TEACHER_LIST, "person/teacher/list", "进入教师列表"},
		{PERSON_TEACHER_MERGE, "person/teacher/merge", "合并教师"},
		{PERSON_TEACHER_LIST, "person/teacher/toList", "person/teacher/toList.action?update=1" ,"教师列表"},
		

		// 学生0508
		{PERSON_STUDENT_ADD, "person/student/add", "添加学生"},
		{PERSON_STUDENT_DELETE, "person/student/delete", "删除学生"},
		{PERSON_STUDENT_MODIFY, "person/student/modify", "修改学生"},
		{PERSON_STUDENT_VIEW, "person/student/view", "查看学生"},
		{PERSON_STUDENT_SIMPLESEARCH, "person/student/simpleSearch", "初级检索学生"},
		{PERSON_STUDENT_ADVSEARCH, "person/student/advSearch", "高级检索学生"},
		{PERSON_STUDENT_LIST, "person/student/list", "进入学生列表"},
		{PERSON_STUDENT_MERGE, "person/student/merge", "合并学生"},
		{PERSON_STUDENT_TOLIST, "person/student/toList","person/student/toList.action?update=1", "学生列表"},
		
		// 机构06
		// 部级0601
		{UNIT_MINISTRY_ADD, "unit/agency/ministry/add", "添加部级机构"},
		{UNIT_MINISTRY_DELETE, "unit/agency/ministry/delete", "删除部级机构"},
		{UNIT_MINISTRY_MODIFY, "unit/agency/ministry/modify", "修改部级机构"},
		{UNIT_MINISTRY_VIEW, "unit/agency/ministry/view", "查看部级机构"},
		{UNIT_MINISTRY_SIMPLESEARCH, "unit/agency/ministry/simpleSearch", "初级检索部级机构"},
		{UNIT_MINISTRY_ADVSEARCH, "unit/agency/ministry/advSearch", "高级检索部级机构"},
		{UNIT_MINISTRY_LIST, "unit/agency/ministry/list", "进入部级机构列表"},
		{UNIT_MINISTRY_TOLIST, "unit/agency/ministry/toList","unit/agency/ministry/toList.action?update=1", "部级管理机构"},
		
		// 省级0602
		{UNIT_PROVINCE_ADD, "unit/agency/province/add", "添加省级机构"},
		{UNIT_PROVINCE_DELETE, "unit/agency/province/delete", "删除省级机构"},
		{UNIT_PROVINCE_MODIFY, "unit/agency/province/modify", "修改省级机构"},
		{UNIT_PROVINCE_VIEW, "unit/agency/province/view", "查看省级机构"},
		{UNIT_PROVINCE_SIMPLESEARCH, "unit/agency/province/simpleSearch", "初级检索省级机构"},
		{UNIT_PROVINCE_ADVSEARCH, "unit/agency/province/advSearch", "高级检索省级机构"},
		{UNIT_PROVINCE_LIST, "unit/agency/province/list", "进入省级机构列表"},
		{UNIT_PROVINCE_TOLIST, "unit/agency/province/toList", "unit/agency/province/toList.action?update=1" ,"省级管理机构"},

		// 校级0603
		{UNIT_UNIVERSITY_ADD, "unit/agency/university/add", "添加高校"},
		{UNIT_UNIVERSITY_DELETE, "unit/agency/university/delete", "删除高校"},
		{UNIT_UNIVERSITY_MODIFY, "unit/agency/university/modify", "修改高校"},
		{UNIT_UNIVERSITY_VIEW, "unit/agency/university/view", "查看高校"},
		{UNIT_UNIVERSITY_SIMPLESEARCH, "unit/agency/university/simpleSearch", "初级检索高校"},
		{UNIT_UNIVERSITY_ADVSEARCH, "unit/agency/university/advSearch", "高级检索高校"},
		{UNIT_UNIVERSITY_LIST, "unit/agency/university/list", "进入高校列表"},
		{UNIT_UNIVERSITY_TOLIST, "unit/agency/university/toList", "unit/agency/university/toList.action?update=1","校级管理机构"},
		
		// 院系0604
		{UNIT_DEPARTMENT_ADD, "unit/department/add", "添加院系"},
		{UNIT_DEPARTMENT_DELETE, "unit/department/delete", "删除院系"},
		{UNIT_DEPARTMENT_MODIFY, "unit/department/modify", "修改院系"},
		{UNIT_DEPARTMENT_VIEW, "unit/department/view", "查看院系"},
		{UNIT_DEPARTMENT_SIMPLESEARCH, "unit/department/simpleSearch", "初级检索院系"},
		{UNIT_DEPARTMENT_ADVSEARCH, "unit/department/advSearch", "高级检索院系"},
		{UNIT_DEPARTMENT_LIST, "unit/department/list", "进入院系列表"},
		{UNIT_DEPARTMENT_MERGE, "unit/department/merge", "合并院系"},
		{UNIT_DEPARTMENT_LIST, "unit/department/toList","unit/department/toList.action?update=1", "院系管理机构"},
		
		// 基地0605
		{UNIT_INSTITUTE_ADD, "unit/institute/add", "添加基地"},
		{UNIT_INSTITUTE_DELETE, "unit/institute/delete", "删除基地"},
		{UNIT_INSTITUTE_MODIFY, "unit/institute/modify", "修改基地"},
		{UNIT_INSTITUTE_VIEW, "unit/institute/view", "查看基地"},
		{UNIT_INSTITUTE_SIMPLESEARCH, "unit/institute/simpleSearch", "初级检索基地"},
		{UNIT_INSTITUTE_ADVSEARCH, "unit/institute/advSearch", "高级检索基地"},
		{UNIT_INSTITUTE_LIST, "unit/institute/list", "进入基地列表"},
		{UNIT_INSTITUTE_LIST, "unit/institute/toList", "unit/institute/toList.action?update=1&instType=1" ,"部级重点研究基地"},
		// 项目07
		// 个人项目0701
		{PROJECT_SELF_SEARCH, "project/searchMyProject", "查询个人项目"},
		{PROJECT_SELF_VIEW, "project/viewMyProject", "查看个人项目"},
		
		// 一般项目0702
		// 申请070201
		// 申请07020101
		{PROJECT_GENERAL_APPLICATION_APPLY_ADD, "project/general/application/apply/add", "添加一般项目申请申请"},
		{PROJECT_GENERAL_APPLICATION_APPLY_DELETE, "project/general/application/apply/delete", "删除一般项目申请申请"},
		{PROJECT_GENERAL_APPLICATION_APPLY_MODIFY, "project/general/application/apply/modify", "修改一般项目申请申请"},
		{PROJECT_GENERAL_APPLICATION_APPLY_VIEW, "project/general/application/apply/view", "查看一般项目申请申请"},
		{PROJECT_GENERAL_APPLICATION_APPLY_SIMPLESEARCH, "project/general/application/apply/simpleSearch", "初级检索一般项目申请申请"},
		{PROJECT_GENERAL_APPLICATION_APPLY_ADVSEARCH, "project/general/application/apply/advSearch", "高级检索一般项目申请申请"},
		{PROJECT_GENERAL_APPLICATION_APPLY_DOWNLOAD, "project/general/application/apply/downloadApply", "下载一般项目申请申请书"},
		{PROJECT_GENERAL_APPLICATION_APPLY_SUBMIT, "project/general/application/apply/submit", "提交一般项目申请申请"},
		{PROJECT_GENERAL_APPLICATION_APPLY_LIST, "project/general/application/apply/list", "进入一般项目申请申请列表"},
		{PROJECT_GENERAL_APPLICATION_APPLYSTRICT_LIST, "project/general/application/applyStrict/list", "进入限项申请"},
		{PROJECT_GENERAL_APPLICATION_APPLYSTRICT_CONFIRMEXPORTOVERVIEW, "project/general/application/confirmExportOverView", "导出限项申请结果"},
		{PROJECT_GENERAL_APPLICATION_APPLYSTRICT_SAVE, "project/general/application/save", "保存限项申请结果"},
		{PROJECT_GENERAL_APPLICATION_APPLY_TOLIST,"project/general/application/apply/toList", "project/general/application/apply/toList.action?update=1", "一般项目申请数据"},
		
		// 审核07020102
		{PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD, "project/general/application/applyAudit/add", "添加一般项目申请审核"},
		{PROJECT_GENERAL_APPLICATION_APPLYAUDIT_DELETE, "project/general/application/applyAudit/delete", "删除一般项目申请审核"},
		{PROJECT_GENERAL_APPLICATION_APPLYAUDIT_MODIFY, "project/general/application/applyAudit/modify", "修改一般项目申请审核"},
		{PROJECT_GENERAL_APPLICATION_APPLYAUDIT_VIEW, "project/general/application/applyAudit/view", "查看一般项目申请审核"},
		{PROJECT_GENERAL_APPLICATION_APPLYAUDIT_SUBMIT, "project/general/application/applyAudit/submit", "提交一般项目申请审核"},
		{PROJECT_GENERAL_APPLICATION_APPLYAUDIT_BACK, "project/general/application/applyAudit/back", "退回一般项目申请申请"},
		// 评审07020103
		{PROJECT_GENERAL_APPLICATION_REVIEW_ADD, "project/general/application/review/add", "添加一般项目申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_DELETE, "project/general/application/review/delete", "删除一般项目申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_MODIFY, "project/general/application/review/modify", "修改一般项目申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_VIEW, "project/general/application/review/view", "查看一般项目申请专家评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_VIEWREVIEW, "project/general/application/review/viewReview", "查看一般项目申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_SUBMIT, "project/general/application/review/submit", "提交一般项目申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_ADDGROUP, "project/general/application/review/addGroup", "添加一般项目小组申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_DELETEGROUP, "project/general/application/review/deleteGroup", "删除一般项目小组申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_MODIFYGROUP, "project/general/application/review/modifyGroup", "修改一般项目小组申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_VIEWGROUP, "project/general/application/review/viewGroup", "查看一般项目申请小组评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_VIEWGROUPOPINION, "project/general/application/review/viewGroupOpinion", "查看一般项目申请所有专家评审意见"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_SUBMITGROUP, "project/general/application/review/submitGroup", "提交一般项目小组申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_SIMPLESEARCH, "project/general/application/review/simpleSearch", "初级检索一般项目申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_ADVSEARCH, "project/general/application/review/advSearch", "高级检索一般项目申请评审"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_LIST, "project/general/application/review/list", "进入一般项目申请评审列表"},
		{PROJECT_GENERAL_APPLICATION_REVIEW_TOLIST, "project/general/application/review/toList", "project/general/application/review/toList.action?update=1", "一般项目申请评审"},
		// 评审审核07020104
		{PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_ADD, "project/general/application/reviewAudit/add", "添加一般项目申请评审审核"},
		{PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_DELETE, "project/general/application/reviewAudit/delete", "删除一般项目申请评审审核"},
		{PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_MODIFY, "project/general/application/reviewAudit/modify", "修改一般项目申请评审审核"},
		{PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_VIEW, "project/general/application/reviewAudit/view", "查看一般项目申请评审审核"},
		{PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_SUBMIT, "project/general/application/reviewAudit/submit", "提交一般项目申请评审审核"},
		// 立项07020103
		{PROJECT_GENERAL_APPLICATION_GRANTED_ADD, "project/general/application/granted/add", "添加一般项目立项"},
		{PROJECT_GENERAL_APPLICATION_GRANTED_DELETE, "project/general/application/granted/delete", "删除一般项目立项"},
		{PROJECT_GENERAL_APPLICATION_GRANTED_MODIFY, "project/general/application/granted/modify", "修改一般项目立项"},
		{PROJECT_GENERAL_APPLICATION_GRANTED_VIEW, "project/general/application/granted/view", "查看一般项目立项"},
		{PROJECT_GENERAL_APPLICATION_GRANTED_SIMPLESEARCH, "project/general/application/granted/simpleSearch", "初级检索一般项目立项"},
		{PROJECT_GENERAL_APPLICATION_GRANTED_ADVSEARCH, "project/general/application/granted/advSearch", "高级检索一般项目立项"},
		{PROJECT_GENERAL_APPLICATION_GRANTED_SETUPPROJECTSTATUS, "project/general/application/granted/setUpProjectStatus", "设置项目状态"},
		{PROJECT_GENERAL_APPLICATION_GRANTED_LIST, "project/general/application/granted/list", "进入一般项目立项列表"},
		{PROJECT_GENERAL_APPLICATION_GRANTED_TOLIST, "project/general/application/granted/toList","project/general/application/granted/toList.action?update=1", "一般项目立项数据"},
		
		// 中检070202
		// 申请07020201
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_ADD, "project/general/midinspection/apply/add", "添加一般项目中检申请"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_DELETE, "project/general/midinspection/apply/delete", "删除一般项目中检申请"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_MODIFY, "project/general/midinspection/apply/modify", "修改一般项目中检申请"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_VIEW, "project/general/midinspection/apply/view", "查看一般项目中检申请"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_SIMPLESEARCH, "project/general/midinspection/apply/simpleSearch", "初级检索一般项目中检申请"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_ADVSEARCH, "project/general/midinspection/apply/advSearch", "高级检索一般项目中检申请"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_DOWNLOAD, "project/general/midinspection/apply/downloadApply", "下载一般项目中检申请书"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/general/midinspection/apply/downloadMidFile", "下载一般项目中检申请书摸板"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_SUBMIT, "project/general/midinspection/apply/submit", "提交一般项目中检申请"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_LIST, "project/general/midinspection/apply/list", "进入一般项目中检申请列表"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLY_TOLIST,"project/general/midinspection/apply/toList", "project/general/midinspection/apply/toList.action?update=1", "一般项目中检数据"},
		
		
		// 审核07020202
		{PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_ADD, "project/general/midinspection/applyAudit/add", "添加一般项目中检审核"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_DELETE, "project/general/midinspection/applyAudit/delete", "删除一般项目中检审核"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_MODIFY, "project/general/midinspection/applyAudit/modify", "修改一般项目中检审核"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_VIEW, "project/general/midinspection/applyAudit/view", "查看一般项目中检审核"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_SUBMIT, "project/general/midinspection/applyAudit/submit", "提交一般项目中检审核"},
		{PROJECT_GENERAL_MIDDINSPECTION_APPLYAUDIT_BACK, "project/general/midinspection/applyAudit/back", "退回一般项目中检申请"},
		// 结果管理07020203
		{PROJECT_GENERAL_MIDINSPECTION_DATA_ADD, "project/general/midinspection/apply/addResult", "添加一般项目中检结果"},
		{PROJECT_GENERAL_MIDINSPECTION_DATA_MODIFY, "project/general/midinspection/apply/modifyResult", "修改一般项目中检结果"},
		{PROJECT_GENERAL_MIDINSPECTION_DATA_SUBMIT, "project/general/midinspection/apply/submitResult", "提交一般项目中检结果"},
		// 结项070203
		// 申请07020301
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_ADD, "project/general/endinspection/apply/add", "添加一般项目结项申请"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_DELETE, "project/general/endinspection/apply/delete", "删除一般项目结项申请"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_MODIFY, "project/general/endinspection/apply/modify", "修改一般项目结项申请"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_VIEW, "project/general/endinspection/apply/view", "查看一般项目结项申请"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_SIMPLESEARCH, "project/general/endinspection/apply/simpleSearch", "初级检索一般项目结项申请"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_ADVSEARCH, "project/general/endinspection/apply/advSearch", "高级检索一般项目结项申请"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_DOWNLOAD, "project/general/endinspection/apply/downloadApply", "下载一般项目结项申请书"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/general/endinspection/apply/downloadEndFile", "下载一般项目结项申请书摸板"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_SUBMIT, "project/general/endinspection/apply/submit", "提交一般项目结项申请"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_LIST, "project/general/endinspection/apply/list", "进入一般项目结项申请列表"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLY_LIST, "project/general/endinspection/apply/toList", "project/general/endinspection/apply/toList.action?update=1" ,"一般项目结项数据"},
		
		// 审核07020302
		{PROJECT_GENERAL_ENDINSPECTION_APPLYAUDIT_ADD, "project/general/endinspection/applyAudit/add", "添加一般项目结项审核"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLYAUDIT_DELETE, "project/general/endinspection/applyAudit/delete", "删除一般项目结项审核"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLYAUDIT_MODIFY, "project/general/endinspection/applyAudit/modify", "修改一般项目结项审核"},
		{PROJECT_GENERAL_ENDINSPECTION_APPLYAUDIT_VIEW, "project/general/endinspection/applyAudit/view", "查看一般项目结项审核"},
		{PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_SUBMIT, "project/general/endinspection/applyAudit/submit", "提交一般项目结项审核"},
		{PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_BACK, "project/general/endinspection/applyAudit/back", "退回一般项目结项申请"},
		{PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_ADDPRODREVIEW, "project/general/endinspection/applyAudit/addProdReview", "添加一般项目结项成果审核"},
		{PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_MODIFYPRODREVIEW, "project/general/endinspection/applyAudit/modifyProdReview", "修改一般项目结项成果审核"},
		{PROJECT_GENERAL_ENDDINSPECTION_APPLYAUDIT_SUBMITPRODREVIEW, "project/general/endinspection/applyAudit/submitProdReview", "提交一般项目结项成果审核"},
		// 评审07020303
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_ADD, "project/general/endinspection/review/add", "添加一般项目结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_DELETE, "project/general/endinspection/review/delete", "删除一般项目结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_MODIFY, "project/general/endinspection/review/modify", "修改一般项目结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEW, "project/general/endinspection/review/view", "查看一般项目结项专家评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEWREVIEW, "project/general/endinspection/review/viewReview", "查看一般项目结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_SUBMIT, "project/general/endinspection/review/submit", "提交一般项目结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_ADDGROUP, "project/general/endinspection/review/addGroup", "添加一般项目小组结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_DELETEGROUP, "project/general/endinspection/review/deleteGroup", "删除一般项目小组结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_MODIFYGROUP, "project/general/endinspection/review/modifyGroup", "修改一般项目小组结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEWGROUP, "project/general/endinspection/review/viewGroup", "查看一般项目结项小组评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_VIEWGROUPOPINION, "project/general/endinspection/review/viewGroupOpinion", "查看一般项目结项所有专家评审意见"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_SUBMITGROUP, "project/general/endinspection/review/submitGroup", "提交一般项目小组结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_SIMPLESEARCH, "project/general/endinspection/review/simpleSearch", "初级检索一般项目结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_ADVSEARCH, "project/general/endinspection/review/advSearch", "高级检索一般项目结项评审"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_LIST, "project/general/endinspection/review/list", "进入一般项目结项评审列表"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEW_LIST, "project/general/endinspection/review/toList","project/general/endinspection/review/toList.action?update=1", "一般项目结项评审"},
		
		// 评审审核07020304
		{PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_ADD, "project/general/endinspection/reviewAudit/add", "添加一般项目评审审核"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_DELETE, "project/general/endinspection/reviewAudit/delete", "删除一般项目评审审核"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_MODIFY, "project/general/endinspection/reviewAudit/modify", "修改一般项目评审审核"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_VIEW, "project/general/endinspection/reviewAudit/view", "查看一般项目评审审核"},
		{PROJECT_GENERAL_ENDINSPECTION_REVIEWAUDIT_SUBMIT, "project/general/endinspection/reviewAudit/submit", "提交一般项目评审审核"},
		// 结果管理07020305
		{PROJECT_GENERAL_ENDINSPECTION_DATA_ADD, "project/general/endinspection/apply/addResult", "添加一般项目结项结果"},
		{PROJECT_GENERAL_ENDINSPECTION_DATA_MODIFY, "project/general/endinspection/apply/modifyResult", "修改一般项目结项结果"},
		{PROJECT_GENERAL_ENDINSPECTION_DATA_SUBMIT, "project/general/endinspection/apply/submitResult", "提交一般项目结项结果"},
		//打印
		{PROJECT_GENERAL_ENDINSPECTION_PRINT_DO, "project/general/endinspection/apply/printCertificate", "打印结项证书"},
		{PROJECT_GENERAL_ENDINSPECTION_PRINT_CONFIRM, "project/general/endinspection/apply/confirmPrintCertificate", "确认打印结项证书"},
		// 变更070204
		// 申请07020401
		{PROJECT_GENERAL_VARIATION_APPLY_ADD, "project/general/variation/apply/add", "添加一般项目变更申请"},
		{PROJECT_GENERAL_VARIATION_APPLY_SUBMIT, "project/general/variation/apply/submit", "提交一般项目变更申请"},
		{PROJECT_GENERAL_VARIATION_APPLY_DELETE, "project/general/variation/apply/delete", "删除一般项目变更申请"},
		{PROJECT_GENERAL_VARIATION_APPLY_MODIFY, "project/general/variation/apply/modify", "修改一般项目变更申请"},
		{PROJECT_GENERAL_VARIATION_APPLY_VIEW, "project/general/variation/apply/view", "查看一般项目变更申请"},
		{PROJECT_GENERAL_VARIATION_APPLY_SIMPLESEARCH, "project/general/variation/apply/simpleSearch", "初级检索一般项目变更申请"},
		{PROJECT_GENERAL_VARIATION_APPLY_ADVSEARCH, "project/general/variation/apply/advSearch", "高级检索一般项目变更申请"},
		{PROJECT_GENERAL_VARIATION_APPLY_DOWNLOADTEMPLATE, "project/general/variation/apply/downloadTemplate", "下载一般项目变更申请书模板"},
		{PROJECT_GENERAL_VARIATION_APPLY_DOWNLOAD, "project/general/variation/apply/downloadApply", "下载一般项目变更申请书"},
		{PROJECT_GENERAL_VARIATION_APPLY_VIEWDIR, "project/general/variation/apply/viewDir", "查看一般项目变更责任人"},
		{PROJECT_GENERAL_VARIATION_APPLY_LIST, "project/general/variation/apply/list", "进入一般项目变更申请列表"},
		{PROJECT_GENERAL_VARIATION_APPLY_LIST, "project/general/variation/apply/toList", "project/general/variation/apply/toList.action?update=1" , "一般项目变更数据"},
		// 审核07020402
		{PROJECT_GENERAL_VARIATION_APPLYAUDIT_ADD, "project/general/variation/applyAudit/add", "添加一般项目变更审核"},
		{PROJECT_GENERAL_VARIATION_APPLYAUDIT_MODIFY, "project/general/variation/applyAudit/modify", "修改一般项目变更审核"},
		{PROJECT_GENERAL_VARIATION_APPLYAUDIT_VIEW, "project/general/variation/applyAudit/view", "查看一般项目变更审核"},
		{PROJECT_GENERAL_VARIATION_APPLYAUDIT_SUBMIT, "project/general/variation/applyAudit/submit", "提交一般项目变更审核"},
		{PROJECT_GENERAL_VARIATION_APPLYAUDIT_BACK, "project/general/variation/applyAudit/back", "退回一般项目变更审核"},
		// 变更结果管理07020403
		{PROJECT_GENERAL_VARIATION_DATA_ADD, "project/general/variation/apply/addResult", "添加一般项目变更结果"},
		{PROJECT_GENERAL_VARIATION_DATA_MODIFY, "project/general/variation/apply/modifyResult", "修改一般项目变更结果"},
		{PROJECT_GENERAL_VARIATION_DATA_SUBMIT, "project/general/variation/apply/submitResult", "提交一般项目变更结果"},
		// 年检070205
		// 申请07020501
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_ADD, "project/general/anninspection/apply/add", "添加一般项目年检申请"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_DELETE, "project/general/anninspection/apply/delete", "删除一般项目年检申请"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_MODIFY, "project/general/anninspection/apply/modify", "修改一般项目年检申请"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_VIEW, "project/general/anninspection/apply/view", "查看一般项目年检申请"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_SIMPLESEARCH, "project/general/anninspection/apply/simpleSearch", "初级检索一般项目年检申请"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_ADVSEARCH, "project/general/anninspection/apply/advSearch", "高级检索一般项目年检申请"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_DOWNLOAD, "project/general/anninspection/apply/downloadApply", "下载一般项目年检申请书"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/general/anninspection/apply/downloadAnnFile", "下载一般项目年检申请书摸板"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_SUBMIT, "project/general/anninspection/apply/submit", "提交一般项目年检申请"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_LIST, "project/general/anninspection/apply/list", "进入一般项目年检申请列表"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLY_LIST, "project/general/anninspection/apply/toList", "project/general/anninspection/apply/toList.action?update=1", "一般项目年检数据"},
		// 审核07020502
		{PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_ADD, "project/general/anninspection/applyAudit/add", "添加一般项目年检审核"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_DELETE, "project/general/anninspection/applyAudit/delete", "删除一般项目年检审核"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_MODIFY, "project/general/anninspection/applyAudit/modify", "修改一般项目年检审核"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_VIEW, "project/general/anninspection/applyAudit/view", "查看一般项目年检审核"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_SUBMIT, "project/general/anninspection/applyAudit/submit", "提交一般项目年检审核"},
		{PROJECT_GENERAL_ANNINSPECTION_APPLYAUDIT_BACK, "project/general/anninspection/applyAudit/back", "退回一般项目年检申请"},
		// 结果管理07020503
		{PROJECT_GENERAL_ANNINSPECTION_DATA_ADD, "project/general/anninspection/apply/addResult", "添加一般项目年检结果"},
		{PROJECT_GENERAL_ANNINSPECTION_DATA_MODIFY, "project/general/anninspection/apply/modifyResult", "修改一般项目年检结果"},
		{PROJECT_GENERAL_ANNINSPECTION_DATA_SUBMIT, "project/general/anninspection/apply/submitResult", "提交一般项目年检结果"},
		
		// 重大攻关项目0703
		// 招标070301
		// 投标申请07030101
		{PROJECT_KEY_APPLICATION_APPLY_ADD, "project/key/application/apply/add", "添加重大攻关项目申请申请"},
		{PROJECT_KEY_APPLICATION_APPLY_DELETE, "project/key/application/apply/delete", "删除重大攻关项目申请申请"},
		{PROJECT_KEY_APPLICATION_APPLY_MODIFY, "project/key/application/apply/modify", "修改重大攻关项目申请申请"},
		{PROJECT_KEY_APPLICATION_APPLY_VIEW, "project/key/application/apply/view", "查看重大攻关项目申请申请"},
		{PROJECT_KEY_APPLICATION_APPLY_SIMPLESEARCH, "project/key/application/apply/simpleSearch", "初级检索重大攻关项目申请申请"},
		{PROJECT_KEY_APPLICATION_APPLY_ADVSEARCH, "project/key/application/apply/advSearch", "高级检索重大攻关项目申请申请"},
		{PROJECT_KEY_APPLICATION_APPLY_DOWNLOAD, "project/key/application/apply/downloadApply", "下载重大攻关项目申请申请书"},
		{PROJECT_KEY_APPLICATION_APPLY_SUBMIT, "project/key/application/apply/submit", "提交重大攻关项目申请申请"},
		{PROJECT_KEY_APPLICATION_APPLY_LIST, "project/key/application/apply/list", "进入重大攻关项目申请申请列表"},
		{PROJECT_KEY_APPLICATION_APPLY_LIST, "project/key/application/apply/toList", "project/key/application/apply/toList.action?update=1" , "重大攻关项目投标数据"},
		// 审核07030102
		{PROJECT_KEY_APPLICATION_APPLYAUDIT_ADD, "project/key/application/applyAudit/add", "添加重大攻关项目申请审核"},
		{PROJECT_KEY_APPLICATION_APPLYAUDIT_DELETE, "project/key/application/applyAudit/delete", "删除重大攻关项目申请审核"},
		{PROJECT_KEY_APPLICATION_APPLYAUDIT_MODIFY, "project/key/application/applyAudit/modify", "修改重大攻关项目申请审核"},
		{PROJECT_KEY_APPLICATION_APPLYAUDIT_VIEW, "project/key/application/applyAudit/view", "查看重大攻关项目申请审核"},
		{PROJECT_KEY_APPLICATION_APPLYAUDIT_SUBMIT, "project/key/application/applyAudit/submit", "提交重大攻关项目申请审核"},
		{PROJECT_KEY_APPLICATION_APPLYAUDIT_BACK, "project/key/application/applyAudit/back", "退回重大攻关项目申请申请"},
		// 评审07030103
		{PROJECT_KEY_APPLICATION_REVIEW_ADD, "project/key/application/review/add", "添加重大攻关项目申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_DELETE, "project/key/application/review/delete", "删除重大攻关项目申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_MODIFY, "project/key/application/review/modify", "修改重大攻关项目申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_VIEW, "project/key/application/review/view", "查看重大攻关项目申请专家评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_VIEWREVIEW, "project/key/application/review/viewReview", "查看重大攻关项目申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_SUBMIT, "project/key/application/review/submit", "提交重大攻关项目申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_ADDGROUP, "project/key/application/review/addGroup", "添加重大攻关项目小组申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_DELETEGROUP, "project/key/application/review/deleteGroup", "删除重大攻关项目小组申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_MODIFYGROUP, "project/key/application/review/modifyGroup", "修改重大攻关项目小组申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_VIEWGROUP, "project/key/application/review/viewGroup", "查看重大攻关项目申请小组评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_VIEWGROUPOPINION, "project/key/application/review/viewGroupOpinion", "查看重大攻关项目申请所有专家评审意见"},
		{PROJECT_KEY_APPLICATION_REVIEW_SUBMITGROUP, "project/key/application/review/submitGroup", "提交重大攻关项目小组申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_SIMPLESEARCH, "project/key/application/review/simpleSearch", "初级检索重大攻关项目申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_ADVSEARCH, "project/key/application/review/advSearch", "高级检索重大攻关项目申请评审"},
		{PROJECT_KEY_APPLICATION_REVIEW_LIST, "project/key/application/review/list", "进入重大攻关项目申请评审列表"},
		{PROJECT_KEY_APPLICATION_REVIEW_LIST, "project/key/application/review/toList", "project/key/application/review/toList.action?update=1","重大攻关项目申请评审"},
		
		// 评审审核07030104
		{PROJECT_KEY_APPLICATION_REVIEWAUDIT_ADD, "project/key/application/reviewAudit/add", "添加重大攻关项目申请评审审核"},
		{PROJECT_KEY_APPLICATION_REVIEWAUDIT_DELETE, "project/key/application/reviewAudit/delete", "删除重大攻关项目申请评审审核"},
		{PROJECT_KEY_APPLICATION_REVIEWAUDIT_MODIFY, "project/key/application/reviewAudit/modify", "修改重大攻关项目申请评审审核"},
		{PROJECT_KEY_APPLICATION_REVIEWAUDIT_VIEW, "project/key/application/reviewAudit/view", "查看重大攻关项目申请评审审核"},
		{PROJECT_KEY_APPLICATION_REVIEWAUDIT_SUBMIT, "project/key/application/reviewAudit/submit", "提交重大攻关项目申请评审审核"},
		// 中标07030103
		{PROJECT_KEY_APPLICATION_GRANTED_ADD, "project/key/application/granted/add", "添加重大攻关项目立项"},
		{PROJECT_KEY_APPLICATION_GRANTED_DELETE, "project/key/application/granted/delete", "删除重大攻关项目立项"},
		{PROJECT_KEY_APPLICATION_GRANTED_MODIFY, "project/key/application/granted/modify", "修改重大攻关项目立项"},
		{PROJECT_KEY_APPLICATION_GRANTED_VIEW, "project/key/application/granted/view", "查看重大攻关项目立项"},
		{PROJECT_KEY_APPLICATION_GRANTED_SIMPLESEARCH, "project/key/application/granted/simpleSearch", "初级检索重大攻关项目立项"},
		{PROJECT_KEY_APPLICATION_GRANTED_ADVSEARCH, "project/key/application/granted/advSearch", "高级检索重大攻关项目立项"},
		{PROJECT_KEY_APPLICATION_GRANTED_SETUPPROJECTSTATUS, "project/key/application/granted/setUpProjectStatus", "设置项目状态"},
		{PROJECT_KEY_APPLICATION_GRANTED_LIST, "project/key/application/granted/list", "进入重大攻关项目立项列表"},
		{PROJECT_KEY_APPLICATION_GRANTED_LIST, "project/key/application/granted/toList", "project/key/application/granted/toList.action?update=1" ,  "重大攻关项目中标数据"},
		// 中检070302
		// 申请07030201
		{PROJECT_KEY_MIDDINSPECTION_APPLY_ADD, "project/key/midinspection/apply/add", "添加重大攻关项目中检申请"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_DELETE, "project/key/midinspection/apply/delete", "删除重大攻关项目中检申请"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_MODIFY, "project/key/midinspection/apply/modify", "修改重大攻关项目中检申请"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_VIEW, "project/key/midinspection/apply/view", "查看重大攻关项目中检申请"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_SIMPLESEARCH, "project/key/midinspection/apply/simpleSearch", "初级检索重大攻关项目中检申请"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_ADVSEARCH, "project/key/midinspection/apply/advSearch", "高级检索重大攻关项目中检申请"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_DOWNLOAD, "project/key/midinspection/apply/downloadApply", "下载重大攻关项目中检申请书"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/key/midinspection/apply/downloadMidFile", "下载重大攻关项目中检申请书摸板"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_SUBMIT, "project/key/midinspection/apply/submit", "提交重大攻关项目中检申请"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_LIST, "project/key/midinspection/apply/list", "进入重大攻关项目中检申请列表"},
		{PROJECT_KEY_MIDDINSPECTION_APPLY_LIST, "project/key/midinspection/apply/toList", "project/key/midinspection/apply/toList.action?update=1" , "重大攻关项目中检数据"},
		// 审核07030202
		{PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_ADD, "project/key/midinspection/applyAudit/add", "添加重大攻关项目中检审核"},
		{PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_DELETE, "project/key/midinspection/applyAudit/delete", "删除重大攻关项目中检审核"},
		{PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_MODIFY, "project/key/midinspection/applyAudit/modify", "修改重大攻关项目中检审核"},
		{PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_VIEW, "project/key/midinspection/applyAudit/view", "查看重大攻关项目中检审核"},
		{PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_SUBMIT, "project/key/midinspection/applyAudit/submit", "提交重大攻关项目中检审核"},
		{PROJECT_KEY_MIDDINSPECTION_APPLYAUDIT_BACK, "project/key/midinspection/applyAudit/back", "退回重大攻关项目中检申请"},
		// 结果管理07030203
		{PROJECT_KEY_MIDINSPECTION_DATA_ADD, "project/key/midinspection/apply/addResult", "添加重大攻关项目中检结果"},
		{PROJECT_KEY_MIDINSPECTION_DATA_MODIFY, "project/key/midinspection/apply/modifyResult", "修改重大攻关项目中检结果"},
		{PROJECT_KEY_MIDINSPECTION_DATA_SUBMIT, "project/key/midinspection/apply/submitResult", "提交重大攻关项目中检结果"},
		// 结项070303
		// 申请07030301
		{PROJECT_KEY_ENDINSPECTION_APPLY_ADD, "project/key/endinspection/apply/add", "添加重大攻关项目结项申请"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_DELETE, "project/key/endinspection/apply/delete", "删除重大攻关项目结项申请"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_MODIFY, "project/key/endinspection/apply/modify", "修改重大攻关项目结项申请"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_VIEW, "project/key/endinspection/apply/view", "查看重大攻关项目结项申请"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_SIMPLESEARCH, "project/key/endinspection/apply/simpleSearch", "初级检索重大攻关项目结项申请"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_ADVSEARCH, "project/key/endinspection/apply/advSearch", "高级检索重大攻关项目结项申请"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_DOWNLOAD, "project/key/endinspection/apply/downloadApply", "下载重大攻关项目结项申请书"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/key/endinspection/apply/downloadEndFile", "下载重大攻关项目结项申请书摸板"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_SUBMIT, "project/key/endinspection/apply/submit", "提交重大攻关项目结项申请"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_LIST, "project/key/endinspection/apply/list", "进入重大攻关项目结项申请列表"},
		{PROJECT_KEY_ENDINSPECTION_APPLY_LIST, "project/key/endinspection/apply/toList", "project/key/endinspection/apply/toList.action?update=1" , "重大攻关项目结项数据"},
		
		// 审核07030302
		{PROJECT_KEY_ENDINSPECTION_APPLYAUDIT_ADD, "project/key/endinspection/applyAudit/add", "添加重大攻关项目结项审核"},
		{PROJECT_KEY_ENDINSPECTION_APPLYAUDIT_DELETE, "project/key/endinspection/applyAudit/delete", "删除重大攻关项目结项审核"},
		{PROJECT_KEY_ENDINSPECTION_APPLYAUDIT_MODIFY, "project/key/endinspection/applyAudit/modify", "修改重大攻关项目结项审核"},
		{PROJECT_KEY_ENDINSPECTION_APPLYAUDIT_VIEW, "project/key/endinspection/applyAudit/view", "查看重大攻关项目结项审核"},
		{PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_SUBMIT, "project/key/endinspection/applyAudit/submit", "提交重大攻关项目结项审核"},
		{PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_BACK, "project/key/endinspection/applyAudit/back", "退回重大攻关项目结项申请"},
		{PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_ADDPRODREVIEW, "project/key/endinspection/applyAudit/addProdReview", "添加重大攻关项目结项成果审核"},
		{PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_MODIFYPRODREVIEW, "project/key/endinspection/applyAudit/modifyProdReview", "修改重大攻关项目结项成果审核"},
		{PROJECT_KEY_ENDDINSPECTION_APPLYAUDIT_SUBMITPRODREVIEW, "project/key/endinspection/applyAudit/submitProdReview", "提交重大攻关项目结项成果审核"},
		// 评审07030303
		{PROJECT_KEY_ENDINSPECTION_REVIEW_ADD, "project/key/endinspection/review/add", "添加重大攻关项目结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_DELETE, "project/key/endinspection/review/delete", "删除重大攻关项目结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_MODIFY, "project/key/endinspection/review/modify", "修改重大攻关项目结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_VIEW, "project/key/endinspection/review/view", "查看重大攻关项目结项专家评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_VIEWREVIEW, "project/key/endinspection/review/viewReview", "查看重大攻关项目结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_SUBMIT, "project/key/endinspection/review/submit", "提交重大攻关项目结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_ADDGROUP, "project/key/endinspection/review/addGroup", "添加重大攻关项目小组结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_DELETEGROUP, "project/key/endinspection/review/deleteGroup", "删除重大攻关项目小组结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_MODIFYGROUP, "project/key/endinspection/review/modifyGroup", "修改重大攻关项目小组结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_VIEWGROUP, "project/key/endinspection/review/viewGroup", "查看重大攻关项目结项小组评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_VIEWGROUPOPINION, "project/key/endinspection/review/viewGroupOpinion", "查看重大攻关项目结项所有专家评审意见"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_SUBMITGROUP, "project/key/endinspection/review/submitGroup", "提交重大攻关项目小组结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_SIMPLESEARCH, "project/key/endinspection/review/simpleSearch", "初级检索重大攻关项目结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_ADVSEARCH, "project/key/endinspection/review/advSearch", "高级检索重大攻关项目结项评审"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_LIST, "project/key/endinspection/review/list", "进入重大攻关项目结项评审列表"},
		{PROJECT_KEY_ENDINSPECTION_REVIEW_LIST, "project/key/endinspection/review/toList", "project/key/endinspection/review/toList.action?update=1","重大攻关项目结项评审"},
		// 评审审核07030304
		{PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_ADD, "project/key/endinspection/reviewAudit/add", "添加重大攻关项目评审审核"},
		{PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_DELETE, "project/key/endinspection/reviewAudit/delete", "删除重大攻关项目评审审核"},
		{PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_MODIFY, "project/key/endinspection/reviewAudit/modify", "修改重大攻关项目评审审核"},
		{PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_VIEW, "project/key/endinspection/reviewAudit/view", "查看重大攻关项目评审审核"},
		{PROJECT_KEY_ENDINSPECTION_REVIEWAUDIT_SUBMIT, "project/key/endinspection/reviewAudit/submit", "提交重大攻关项目评审审核"},
		// 结果管理07030305
		{PROJECT_KEY_ENDINSPECTION_DATA_ADD, "project/key/endinspection/apply/addResult", "添加重大攻关项目结项结果"},
		{PROJECT_KEY_ENDINSPECTION_DATA_MODIFY, "project/key/endinspection/apply/modifyResult", "修改重大攻关项目结项结果"},
		{PROJECT_KEY_ENDINSPECTION_DATA_SUBMIT, "project/key/endinspection/apply/submitResult", "提交重大攻关项目结项结果"},
		//打印
		{PROJECT_KEY_ENDINSPECTION_PRINT_DO, "project/key/endinspection/apply/printCertificate", "打印结项证书"},
		{PROJECT_KEY_ENDINSPECTION_PRINT_CONFIRM, "project/key/endinspection/apply/confirmPrintCertificate", "确认打印结项证书"},
		// 变更070304
		// 申请07030401
		{PROJECT_KEY_VARIATION_APPLY_ADD, "project/key/variation/apply/add", "添加重大攻关项目变更申请"},
		{PROJECT_KEY_VARIATION_APPLY_SUBMIT, "project/key/variation/apply/submit", "提交重大攻关项目变更申请"},
		{PROJECT_KEY_VARIATION_APPLY_DELETE, "project/key/variation/apply/delete", "删除重大攻关项目变更申请"},
		{PROJECT_KEY_VARIATION_APPLY_MODIFY, "project/key/variation/apply/modify", "修改重大攻关项目变更申请"},
		{PROJECT_KEY_VARIATION_APPLY_VIEW, "project/key/variation/apply/view", "查看重大攻关项目变更申请"},
		{PROJECT_KEY_VARIATION_APPLY_SIMPLESEARCH, "project/key/variation/apply/simpleSearch", "初级检索重大攻关项目变更申请"},
		{PROJECT_KEY_VARIATION_APPLY_ADVSEARCH, "project/key/variation/apply/advSearch", "高级检索重大攻关项目变更申请"},
		{PROJECT_KEY_VARIATION_APPLY_DOWNLOADTEMPLATE, "project/key/variation/apply/downloadTemplate", "下载重大攻关项目变更申请书模板"},
		{PROJECT_KEY_VARIATION_APPLY_DOWNLOAD, "project/key/variation/apply/downloadApply", "下载重大攻关项目变更申请书"},
		{PROJECT_KEY_VARIATION_APPLY_VIEWDIR, "project/key/variation/apply/viewDir", "查看重大攻关项目变更责任人"},
		{PROJECT_KEY_VARIATION_APPLY_LIST, "project/key/variation/apply/list", "进入重大攻关项目变更申请列表"},
		{PROJECT_KEY_VARIATION_APPLY_LIST, "project/key/variation/apply/toList","project/key/variation/apply/toList.action?update=1", "重大攻关项目变更数据"},
		// 审核07030402
		{PROJECT_KEY_VARIATION_APPLYAUDIT_ADD, "project/key/variation/applyAudit/add", "添加重大攻关项目变更审核"},
		{PROJECT_KEY_VARIATION_APPLYAUDIT_MODIFY, "project/key/variation/applyAudit/modify", "修改重大攻关项目变更审核"},
		{PROJECT_KEY_VARIATION_APPLYAUDIT_VIEW, "project/key/variation/applyAudit/view", "查看重大攻关项目变更审核"},
		{PROJECT_KEY_VARIATION_APPLYAUDIT_SUBMIT, "project/key/variation/applyAudit/submit", "提交重大攻关项目变更审核"},
		{PROJECT_KEY_VARIATION_APPLYAUDIT_BACK, "project/key/variation/applyAudit/back", "退回重大攻关项目变更审核"},
		// 变更结果管理07030403
		{PROJECT_KEY_VARIATION_DATA_ADD, "project/key/variation/apply/addResult", "添加重大攻关项目变更结果"},
		{PROJECT_KEY_VARIATION_DATA_MODIFY, "project/key/variation/apply/modifyResult", "修改重大攻关项目变更结果"},
		{PROJECT_KEY_VARIATION_DATA_SUBMIT, "project/key/variation/apply/submitResult", "提交重大攻关项目变更结果"},
		// 年检070305
		// 申请07030501
		{PROJECT_KEY_ANNINSPECTION_APPLY_ADD, "project/key/anninspection/apply/add", "添加重大攻关项目年检申请"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_DELETE, "project/key/anninspection/apply/delete", "删除重大攻关项目年检申请"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_MODIFY, "project/key/anninspection/apply/modify", "修改重大攻关项目年检申请"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_VIEW, "project/key/anninspection/apply/view", "查看重大攻关项目年检申请"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_SIMPLESEARCH, "project/key/anninspection/apply/simpleSearch", "初级检索重大攻关项目年检申请"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_ADVSEARCH, "project/key/anninspection/apply/advSearch", "高级检索重大攻关项目年检申请"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_DOWNLOAD, "project/key/anninspection/apply/downloadApply", "下载重大攻关项目年检申请书"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/key/anninspection/apply/downloadAnnFile", "下载重大攻关项目年检申请书摸板"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_SUBMIT, "project/key/anninspection/apply/submit", "提交重大攻关项目年检申请"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_LIST, "project/key/anninspection/apply/list", "进入重大攻关项目年检申请列表"},
		{PROJECT_KEY_ANNINSPECTION_APPLY_LIST, "project/key/anninspection/apply/toList", "project/key/anninspection/apply/toList.action?update=1" , "重大攻关项目年检数据"},
		// 审核07030502
		{PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_ADD, "project/key/anninspection/applyAudit/add", "添加重大攻关项目年检审核"},
		{PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_DELETE, "project/key/anninspection/applyAudit/delete", "删除重大攻关项目年检审核"},
		{PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_MODIFY, "project/key/anninspection/applyAudit/modify", "修改重大攻关项目年检审核"},
		{PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_VIEW, "project/key/anninspection/applyAudit/view", "查看重大攻关项目年检审核"},
		{PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_SUBMIT, "project/key/anninspection/applyAudit/submit", "提交重大攻关项目年检审核"},
		{PROJECT_KEY_ANNINSPECTION_APPLYAUDIT_BACK, "project/key/anninspection/applyAudit/back", "退回重大攻关项目年检申请"},
		// 选题管理070306
		// 申请07030601
		{PROJECT_KEY_TOPIC_SELECTION_APPLY_ADD, "project/key/topicSelection/apply/add", "添加重大攻关项目选题申请"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLY_DELETE, "project/key/topicSelection/apply/delete", "删除重大攻关项目选题申请"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLY_MODIFY, "project/key/topicSelection/apply/modify", "修改重大攻关项目选题申请"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLY_VIEW, "project/key/topicSelection/apply/view", "查看重大攻关项目选题申请"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLY_SIMPLESEARCH, "project/key/topicSelection/apply/simpleSearch", "初级检索重大攻关项目选题申请"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLY_ADVSEARCH, "project/key/topicSelection/apply/advSearch", "高级检索重大攻关项目选题申请"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLY_SUBMIT, "project/key/topicSelection/apply/submit", "提交重大攻关项目选题申请"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLY_LIST, "project/key/topicSelection/apply/list", "进入重大攻关项目选题申请列表"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLY_LIST, "project/key/topicSelection/apply/toList", "project/key/topicSelection/apply/toList.action?update=1" , "重大攻关项目年度选题"},
		// 审核07030602
		{PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD, "project/key/topicSelection/applyAudit/add", "添加重大攻关项目选题审核"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_DELETE, "project/key/topicSelection/applyAudit/delete", "删除重大攻关项目选题审核"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_MODIFY, "project/key/topicSelection/applyAudit/modify", "修改重大攻关项目选题审核"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_VIEW, "project/key/topicSelection/applyAudit/view", "查看重大攻关项目选题审核"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_SUBMIT, "project/key/topicSelection/applyAudit/submit", "提交重大攻关项目选题审核"},
		{PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_BACK, "project/key/topicSelection/applyAudit/back", "退回重大攻关项目选题申请"},
		// 结果管理07030603
		{PROJECT_KEY_TOPIC_SELECTION_DATA_ADD, "project/key/topicSelection/apply/addResult", "添加重大攻关项目选题结果"},
		{PROJECT_KEY_TOPIC_SELECTION_DATA_MODIFY, "project/key/topicSelection/apply/modifyResult", "修改重大攻关项目选题结果"},
		{PROJECT_KEY_TOPIC_SELECTION_DATA_SUBMIT, "project/key/topicSelection/apply/submitResult", "提交重大攻关项目选题结果"},
		
		// 基地项目0704
		// 申请070401
		// 申请07040101
		{PROJECT_INSTP_APPLICATION_APPLY_ADD, "project/instp/application/apply/add", "添加基地项目申请申请"},
		{PROJECT_INSTP_APPLICATION_APPLY_DELETE, "project/instp/application/apply/delete", "删除基地项目申请申请"},
		{PROJECT_INSTP_APPLICATION_APPLY_MODIFY, "project/instp/application/apply/modify", "修改基地项目申请申请"},
		{PROJECT_INSTP_APPLICATION_APPLY_VIEW, "project/instp/application/apply/view", "查看基地项目申请申请"},
		{PROJECT_INSTP_APPLICATION_APPLY_SIMPLESEARCH, "project/instp/application/apply/simpleSearch", "初级检索基地项目申请申请"},
		{PROJECT_INSTP_APPLICATION_APPLY_ADVSEARCH, "project/instp/application/apply/advSearch", "高级检索基地项目申请申请"},
		{PROJECT_INSTP_APPLICATION_APPLY_DOWNLOAD, "project/instp/application/apply/downloadApply", "下载基地项目申请申请书"},
		{PROJECT_INSTP_APPLICATION_APPLY_SUBMIT, "project/instp/application/apply/submit", "提交基地项目申请申请"},
		{PROJECT_INSTP_APPLICATION_APPLY_LIST, "project/instp/application/apply/list", "进入基地项目申请申请列表"},
		{PROJECT_INSTP_APPLICATION_APPLY_LIST, "project/instp/application/apply/toList","project/instp/application/apply/toList.action?update=1", "基地项目申请数据"},
		// 审核07040102
		{PROJECT_INSTP_APPLICATION_APPLYAUDIT_ADD, "project/instp/application/applyAudit/add", "添加基地项目申请审核"},
		{PROJECT_INSTP_APPLICATION_APPLYAUDIT_DELETE, "project/instp/application/applyAudit/delete", "删除基地项目申请审核"},
		{PROJECT_INSTP_APPLICATION_APPLYAUDIT_MODIFY, "project/instp/application/applyAudit/modify", "修改基地项目申请审核"},
		{PROJECT_INSTP_APPLICATION_APPLYAUDIT_VIEW, "project/instp/application/applyAudit/view", "查看基地项目申请审核"},
		{PROJECT_INSTP_APPLICATION_APPLYAUDIT_SUBMIT, "project/instp/application/applyAudit/submit", "提交基地项目申请审核"},
		{PROJECT_INSTP_APPLICATION_APPLYAUDIT_BACK, "project/instp/application/applyAudit/back", "退回基地项目申请申请"},
		// 评审07040103
		{PROJECT_INSTP_APPLICATION_REVIEW_ADD, "project/instp/application/review/add", "添加基地项目申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_DELETE, "project/instp/application/review/delete", "删除基地项目申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_MODIFY, "project/instp/application/review/modify", "修改基地项目申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_VIEW, "project/instp/application/review/view", "查看基地项目申请专家评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_VIEWREVIEW, "project/instp/application/review/viewReview", "查看基地项目申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_SUBMIT, "project/instp/application/review/submit", "提交基地项目申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_ADDGROUP, "project/instp/application/review/addGroup", "添加基地项目小组申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_DELETEGROUP, "project/instp/application/review/deleteGroup", "删除基地项目小组申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_MODIFYGROUP, "project/instp/application/review/modifyGroup", "修改基地项目小组申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_VIEWGROUP, "project/instp/application/review/viewGroup", "查看基地项目申请小组评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_VIEWGROUPOPINION, "project/instp/application/review/viewGroupOpinion", "查看基地项目申请所有专家评审意见"},
		{PROJECT_INSTP_APPLICATION_REVIEW_SUBMITGROUP, "project/instp/application/review/submitGroup", "提交基地项目小组申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_SIMPLESEARCH, "project/instp/application/review/simpleSearch", "初级检索基地项目申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_ADVSEARCH, "project/instp/application/review/advSearch", "高级检索基地项目申请评审"},
		{PROJECT_INSTP_APPLICATION_REVIEW_LIST, "project/instp/application/review/list", "进入基地项目申请评审列表"},
		{PROJECT_INSTP_APPLICATION_REVIEW_LIST, "project/instp/application/review/toList", "project/instp/application/review/toList.action?update=1","基地项目申请评审"},
		
		// 评审审核07040104
		{PROJECT_INSTP_APPLICATION_REVIEWAUDIT_ADD, "project/instp/application/reviewAudit/add", "添加基地项目申请评审审核"},
		{PROJECT_INSTP_APPLICATION_REVIEWAUDIT_DELETE, "project/instp/application/reviewAudit/delete", "删除基地项目申请评审审核"},
		{PROJECT_INSTP_APPLICATION_REVIEWAUDIT_MODIFY, "project/instp/application/reviewAudit/modify", "修改基地项目申请评审审核"},
		{PROJECT_INSTP_APPLICATION_REVIEWAUDIT_VIEW, "project/instp/application/reviewAudit/view", "查看基地项目申请评审审核"},
		{PROJECT_INSTP_APPLICATION_REVIEWAUDIT_SUBMIT, "project/instp/application/reviewAudit/submit", "提交基地项目申请评审审核"},
		// 立项07040103
		{PROJECT_INSTP_APPLICATION_GRANTED_ADD, "project/instp/application/granted/add", "添加基地项目立项"},
		{PROJECT_INSTP_APPLICATION_GRANTED_DELETE, "project/instp/application/granted/delete", "删除基地项目立项"},
		{PROJECT_INSTP_APPLICATION_GRANTED_MODIFY, "project/instp/application/granted/modify", "修改基地项目立项"},
		{PROJECT_INSTP_APPLICATION_GRANTED_VIEW, "project/instp/application/granted/view", "查看基地项目立项"},
		{PROJECT_INSTP_APPLICATION_GRANTED_SIMPLESEARCH, "project/instp/application/granted/simpleSearch", "初级检索基地项目立项"},
		{PROJECT_INSTP_APPLICATION_GRANTED_ADVSEARCH, "project/instp/application/granted/advSearch", "高级检索基地项目立项"},
		{PROJECT_INSTP_APPLICATION_GRANTED_SETUPPROJECTSTATUS, "project/instp/application/granted/setUpProjectStatus", "设置项目状态"},
		{PROJECT_INSTP_APPLICATION_GRANTED_LIST, "project/instp/application/granted/list", "进入基地项目立项列表"},
		{PROJECT_INSTP_APPLICATION_GRANTED_LIST, "project/instp/application/granted/toList","project/instp/application/granted/toList.action?update=1" , "基地项目立项数据"},
		
		// 中检070402
		// 申请07040201
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_ADD, "project/instp/midinspection/apply/add", "添加基地项目中检申请"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_DELETE, "project/instp/midinspection/apply/delete", "删除基地项目中检申请"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_MODIFY, "project/instp/midinspection/apply/modify", "修改基地项目中检申请"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_VIEW, "project/instp/midinspection/apply/view", "查看基地项目中检申请"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_SIMPLESEARCH, "project/instp/midinspection/apply/simpleSearch", "初级检索基地项目中检申请"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_ADVSEARCH, "project/instp/midinspection/apply/advSearch", "高级检索基地项目中检申请"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_DOWNLOAD, "project/instp/midinspection/apply/downloadApply", "下载基地项目中检申请书"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/instp/midinspection/apply/downloadMidFile", "下载基地项目中检申请书摸板"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_SUBMIT, "project/instp/midinspection/apply/submit", "提交基地项目中检申请"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_LIST, "project/instp/midinspection/apply/list", "进入基地项目中检申请列表"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLY_LIST, "project/instp/midinspection/apply/toList","project/instp/midinspection/apply/toList.action?update=1", "基地项目中检数据"},
		
		// 审核07040202
		{PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_ADD, "project/instp/midinspection/applyAudit/add", "添加基地项目中检审核"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_DELETE, "project/instp/midinspection/applyAudit/delete", "删除基地项目中检审核"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_MODIFY, "project/instp/midinspection/applyAudit/modify", "修改基地项目中检审核"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_VIEW, "project/instp/midinspection/applyAudit/view", "查看基地项目中检审核"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_SUBMIT, "project/instp/midinspection/applyAudit/submit", "提交基地项目中检审核"},
		{PROJECT_INSTP_MIDDINSPECTION_APPLYAUDIT_BACK, "project/instp/midinspection/applyAudit/back", "退回基地项目中检申请"},
		// 结果管理07040203
		{PROJECT_INSTP_MIDINSPECTION_DATA_ADD, "project/instp/midinspection/apply/addResult", "添加基地项目中检结果"},
		{PROJECT_INSTP_MIDINSPECTION_DATA_MODIFY, "project/instp/midinspection/apply/modifyResult", "修改基地项目中检结果"},
		{PROJECT_INSTP_MIDINSPECTION_DATA_SUBMIT, "project/instp/midinspection/apply/submitResult", "提交基地项目中检结果"},
		// 结项070403
		// 申请07040301
		{PROJECT_INSTP_ENDINSPECTION_APPLY_ADD, "project/instp/endinspection/apply/add", "添加基地项目结项申请"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_DELETE, "project/instp/endinspection/apply/delete", "删除基地项目结项申请"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_MODIFY, "project/instp/endinspection/apply/modify", "修改基地项目结项申请"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_VIEW, "project/instp/endinspection/apply/view", "查看基地项目结项申请"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_SIMPLESEARCH, "project/instp/endinspection/apply/simpleSearch", "初级检索基地项目结项申请"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_ADVSEARCH, "project/instp/endinspection/apply/advSearch", "高级检索基地项目结项申请"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_DOWNLOAD, "project/instp/endinspection/apply/downloadApply", "下载基地项目结项申请书"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/instp/endinspection/apply/downloadEndFile", "下载基地项目结项申请书摸板"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_SUBMIT, "project/instp/endinspection/apply/submit", "提交基地项目结项申请"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_LIST, "project/instp/endinspection/apply/list", "进入基地项目结项申请列表"},
		{PROJECT_INSTP_ENDINSPECTION_APPLY_LIST, "project/instp/endinspection/apply/toList","project/instp/endinspection/apply/toList.action?update=1", "基地项目结项数据"},
		
		// 审核07040302
		{PROJECT_INSTP_ENDINSPECTION_APPLYAUDIT_ADD, "project/instp/endinspection/applyAudit/add", "添加基地项目结项审核"},
		{PROJECT_INSTP_ENDINSPECTION_APPLYAUDIT_DELETE, "project/instp/endinspection/applyAudit/delete", "删除基地项目结项审核"},
		{PROJECT_INSTP_ENDINSPECTION_APPLYAUDIT_MODIFY, "project/instp/endinspection/applyAudit/modify", "修改基地项目结项审核"},
		{PROJECT_INSTP_ENDINSPECTION_APPLYAUDIT_VIEW, "project/instp/endinspection/applyAudit/view", "查看基地项目结项审核"},
		{PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_SUBMIT, "project/instp/endinspection/applyAudit/submit", "提交基地项目结项审核"},
		{PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_BACK, "project/instp/endinspection/applyAudit/back", "退回基地项目结项申请"},
		{PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_ADDPRODREVIEW, "project/instp/endinspection/applyAudit/addProdReview", "添加基地项目结项成果审核"},
		{PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_MODIFYPRODREVIEW, "project/instp/endinspection/applyAudit/modifyProdReview", "修改基地项目结项成果审核"},
		{PROJECT_INSTP_ENDDINSPECTION_APPLYAUDIT_SUBMITPRODREVIEW, "project/instp/endinspection/applyAudit/submitProdReview", "提交基地项目结项成果审核"},
		// 评审07040303
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_ADD, "project/instp/endinspection/review/add", "添加基地项目结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_DELETE, "project/instp/endinspection/review/delete", "删除基地项目结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_MODIFY, "project/instp/endinspection/review/modify", "修改基地项目结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEW, "project/instp/endinspection/review/view", "查看基地项目结项专家评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEWREVIEW, "project/instp/endinspection/review/viewReview", "查看基地项目结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_SUBMIT, "project/instp/endinspection/review/submit", "提交基地项目结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_ADDGROUP, "project/instp/endinspection/review/addGroup", "添加基地项目小组结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_DELETEGROUP, "project/instp/endinspection/review/deleteGroup", "删除基地项目小组结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_MODIFYGROUP, "project/instp/endinspection/review/modifyGroup", "修改基地项目小组结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEWGROUP, "project/instp/endinspection/review/viewGroup", "查看基地项目结项小组评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_VIEWGROUPOPINION, "project/instp/endinspection/review/viewGroupOpinion", "查看基地项目结项所有专家评审意见"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_SUBMITGROUP, "project/instp/endinspection/review/submitGroup", "提交基地项目小组结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_SIMPLESEARCH, "project/instp/endinspection/review/simpleSearch", "初级检索基地项目结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_ADVSEARCH, "project/instp/endinspection/review/advSearch", "高级检索基地项目结项评审"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_LIST, "project/instp/endinspection/review/list", "进入基地项目结项评审列表"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEW_LIST, "project/instp/endinspection/review/toList","project/instp/endinspection/review/toList.action?update=1", "基地项目结项评审"},
		
		// 评审审核07040304
		{PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_ADD, "project/instp/endinspection/reviewAudit/add", "添加基地项目评审审核"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_DELETE, "project/instp/endinspection/reviewAudit/delete", "删除基地项目评审审核"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_MODIFY, "project/instp/endinspection/reviewAudit/modify", "修改基地项目评审审核"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_VIEW, "project/instp/endinspection/reviewAudit/view", "查看基地项目评审审核"},
		{PROJECT_INSTP_ENDINSPECTION_REVIEWAUDIT_SUBMIT, "project/instp/endinspection/reviewAudit/submit", "提交基地项目评审审核"},
		// 结果管理07040305
		{PROJECT_INSTP_ENDINSPECTION_DATA_ADD, "project/instp/endinspection/apply/addResult", "添加基地项目结项结果"},
		{PROJECT_INSTP_ENDINSPECTION_DATA_MODIFY, "project/instp/endinspection/apply/modifyResult", "修改基地项目结项结果"},
		{PROJECT_INSTP_ENDINSPECTION_DATA_SUBMIT, "project/instp/endinspection/apply/submitResult", "提交基地项目结项结果"},
		//打印
		{PROJECT_INSTP_ENDINSPECTION_PRINT_DO, "project/instp/endinspection/apply/printCertificate", "打印结项证书"},
		{PROJECT_INSTP_ENDINSPECTION_PRINT_CONFIRM, "project/instp/endinspection/apply/confirmPrintCertificate", "确认打印结项证书"},
		// 变更070404
		// 申请07040401
		{PROJECT_INSTP_VARIATION_APPLY_ADD, "project/instp/variation/apply/add", "添加基地项目变更申请"},
		{PROJECT_INSTP_VARIATION_APPLY_SUBMIT, "project/instp/variation/apply/submit", "提交基地项目变更申请"},
		{PROJECT_INSTP_VARIATION_APPLY_DELETE, "project/instp/variation/apply/delete", "删除基地项目变更申请"},
		{PROJECT_INSTP_VARIATION_APPLY_MODIFY, "project/instp/variation/apply/modify", "修改基地项目变更申请"},
		{PROJECT_INSTP_VARIATION_APPLY_VIEW, "project/instp/variation/apply/view", "查看基地项目变更申请"},
		{PROJECT_INSTP_VARIATION_APPLY_SIMPLESEARCH, "project/instp/variation/apply/simpleSearch", "初级检索基地项目变更申请"},
		{PROJECT_INSTP_VARIATION_APPLY_ADVSEARCH, "project/instp/variation/apply/advSearch", "高级检索基地项目变更申请"},
		{PROJECT_INSTP_VARIATION_APPLY_DOWNLOADTEMPLATE, "project/instp/variation/apply/downloadTemplate", "下载基地项目变更申请书模板"},
		{PROJECT_INSTP_VARIATION_APPLY_DOWNLOAD, "project/instp/variation/apply/downloadApply", "下载基地项目变更申请书"},
		{PROJECT_INSTP_VARIATION_APPLY_VIEWDIR, "project/instp/variation/apply/viewDir", "查看基地项目变更责任人"},
		{PROJECT_INSTP_VARIATION_APPLY_LIST, "project/instp/variation/apply/list", "进入基地项目变更申请列表"},
		{PROJECT_INSTP_VARIATION_APPLY_LIST, "project/instp/variation/apply/toList", "project/instp/variation/apply/toList.action?update=1","基地项目变更数据"},
		
		// 审核07040402
		{PROJECT_INSTP_VARIATION_APPLYAUDIT_ADD, "project/instp/variation/applyAudit/add", "添加基地项目变更审核"},
		{PROJECT_INSTP_VARIATION_APPLYAUDIT_MODIFY, "project/instp/variation/applyAudit/modify", "修改基地项目变更审核"},
		{PROJECT_INSTP_VARIATION_APPLYAUDIT_VIEW, "project/instp/variation/applyAudit/view", "查看基地项目变更审核"},
		{PROJECT_INSTP_VARIATION_APPLYAUDIT_SUBMIT, "project/instp/variation/applyAudit/submit", "提交基地项目变更审核"},
		{PROJECT_INSTP_VARIATION_APPLYAUDIT_BACK, "project/instp/variation/applyAudit/back", "退回基地项目变更审核"},
		// 变更结果管理07040403
		{PROJECT_INSTP_VARIATION_DATA_ADD, "project/instp/variation/apply/addResult", "添加基地项目变更结果"},
		{PROJECT_INSTP_VARIATION_DATA_MODIFY, "project/instp/variation/apply/modifyResult", "修改基地项目变更结果"},
		{PROJECT_INSTP_VARIATION_DATA_SUBMIT, "project/instp/variation/apply/submitResult", "提交基地项目变更结果"},
		// 年检070405
		// 申请07040501
		{PROJECT_INSTP_ANNINSPECTION_APPLY_ADD, "project/instp/anninspection/apply/add", "添加基地项目年检申请"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_DELETE, "project/instp/anninspection/apply/delete", "删除基地项目年检申请"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_MODIFY, "project/instp/anninspection/apply/modify", "修改基地项目年检申请"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW, "project/instp/anninspection/apply/view", "查看基地项目年检申请"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_SIMPLESEARCH, "project/instp/anninspection/apply/simpleSearch", "初级检索基地项目年检申请"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_ADVSEARCH, "project/instp/anninspection/apply/advSearch", "高级检索基地项目年检申请"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_DOWNLOAD, "project/instp/anninspection/apply/downloadApply", "下载基地项目年检申请书"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/instp/anninspection/apply/downloadAnnFile", "下载基地项目年检申请书摸板"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_SUBMIT, "project/instp/anninspection/apply/submit", "提交基地项目年检申请"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_LIST, "project/instp/anninspection/apply/list", "进入基地项目年检申请列表"},
		{PROJECT_INSTP_ANNINSPECTION_APPLY_LIST, "project/instp/anninspection/apply/toList","project/instp/anninspection/apply/toList.action?update=1", "基地项目年检数据"},
		
		// 审核07040502
		{PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD, "project/instp/anninspection/applyAudit/add", "添加基地项目年检审核"},
		{PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_DELETE, "project/instp/anninspection/applyAudit/delete", "删除基地项目年检审核"},
		{PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_MODIFY, "project/instp/anninspection/applyAudit/modify", "修改基地项目年检审核"},
		{PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_VIEW, "project/instp/anninspection/applyAudit/view", "查看基地项目年检审核"},
		{PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_SUBMIT, "project/instp/anninspection/applyAudit/submit", "提交基地项目年检审核"},
		{PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_BACK, "project/instp/anninspection/applyAudit/back", "退回基地项目年检申请"},
		// 结果管理07040503
		{PROJECT_INSTP_ANNINSPECTION_DATA_ADD, "project/instp/anninspection/apply/addResult", "添加基地项目年检结果"},
		{PROJECT_INSTP_ANNINSPECTION_DATA_MODIFY, "project/instp/anninspection/apply/modifyResult", "修改基地项目年检结果"},
		{PROJECT_INSTP_ANNINSPECTION_DATA_SUBMIT, "project/instp/anninspection/apply/submitResult", "提交基地项目年检结果"},
		
		// 后期资助项目0705
		// 申请070501
		// 申请07050101
		{PROJECT_POST_APPLICATION_APPLY_ADD, "project/post/application/apply/add", "添加后期资助项目申请申请"},
		{PROJECT_POST_APPLICATION_APPLY_DELETE, "project/post/application/apply/delete", "删除后期资助项目申请申请"},
		{PROJECT_POST_APPLICATION_APPLY_MODIFY, "project/post/application/apply/modify", "修改后期资助项目申请申请"},
		{PROJECT_POST_APPLICATION_APPLY_VIEW, "project/post/application/apply/view", "查看后期资助项目申请申请"},
		{PROJECT_POST_APPLICATION_APPLY_SIMPLESEARCH, "project/post/application/apply/simpleSearch", "初级检索后期资助项目申请申请"},
		{PROJECT_POST_APPLICATION_APPLY_ADVSEARCH, "project/post/application/apply/advSearch", "高级检索后期资助项目申请申请"},
		{PROJECT_POST_APPLICATION_APPLY_DOWNLOAD, "project/post/application/apply/downloadApply", "下载后期资助项目申请申请书"},
		{PROJECT_POST_APPLICATION_APPLY_SUBMIT, "project/post/application/apply/submit", "提交后期资助项目申请申请"},
		{PROJECT_POST_APPLICATION_APPLY_LIST, "project/post/application/apply/list", "进入后期资助项目申请申请列表"},
		{PROJECT_POST_APPLICATION_APPLY_LIST, "project/post/application/apply/toList","project/post/application/apply/toList.action?update=1" , "后期资助项目申请数据"},
		
		// 审核07050102
		{PROJECT_POST_APPLICATION_APPLYAUDIT_ADD, "project/post/application/applyAudit/add", "添加后期资助项目申请审核"},
		{PROJECT_POST_APPLICATION_APPLYAUDIT_DELETE, "project/post/application/applyAudit/delete", "删除后期资助项目申请审核"},
		{PROJECT_POST_APPLICATION_APPLYAUDIT_MODIFY, "project/post/application/applyAudit/modify", "修改后期资助项目申请审核"},
		{PROJECT_POST_APPLICATION_APPLYAUDIT_VIEW, "project/post/application/applyAudit/view", "查看后期资助项目申请审核"},
		{PROJECT_POST_APPLICATION_APPLYAUDIT_SUBMIT, "project/post/application/applyAudit/submit", "提交后期资助项目申请审核"},
		{PROJECT_POST_APPLICATION_APPLYAUDIT_BACK, "project/post/application/applyAudit/back", "退回后期资助项目申请申请"},
		// 评审07050103
		{PROJECT_POST_APPLICATION_REVIEW_ADD, "project/post/application/review/add", "添加后期资助项目申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_DELETE, "project/post/application/review/delete", "删除后期资助项目申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_MODIFY, "project/post/application/review/modify", "修改后期资助项目申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_VIEW, "project/post/application/review/view", "查看后期资助项目申请专家评审"},
		{PROJECT_POST_APPLICATION_REVIEW_VIEWREVIEW, "project/post/application/review/viewReview", "查看后期资助项目申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_SUBMIT, "project/post/application/review/submit", "提交后期资助项目申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_ADDGROUP, "project/post/application/review/addGroup", "添加后期资助项目小组申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_DELETEGROUP, "project/post/application/review/deleteGroup", "删除后期资助项目小组申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_MODIFYGROUP, "project/post/application/review/modifyGroup", "修改后期资助项目小组申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_VIEWGROUP, "project/post/application/review/viewGroup", "查看后期资助项目申请小组评审"},
		{PROJECT_POST_APPLICATION_REVIEW_VIEWGROUPOPINION, "project/post/application/review/viewGroupOpinion", "查看后期资助项目申请所有专家评审意见"},
		{PROJECT_POST_APPLICATION_REVIEW_SUBMITGROUP, "project/post/application/review/submitGroup", "提交后期资助项目小组申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_SIMPLESEARCH, "project/post/application/review/simpleSearch", "初级检索后期资助项目申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_ADVSEARCH, "project/post/application/review/advSearch", "高级检索后期资助项目申请评审"},
		{PROJECT_POST_APPLICATION_REVIEW_LIST, "project/post/application/review/list", "进入后期资助项目申请评审列表"},
		{PROJECT_POST_APPLICATION_REVIEW_LIST, "project/post/application/review/toList", "project/post/application/review/toList.action?update=1","后期资助项目申请评审"},
		
		// 评审审核07050104
		{PROJECT_POST_APPLICATION_REVIEWAUDIT_ADD, "project/post/application/reviewAudit/add", "添加后期资助项目申请评审审核"},
		{PROJECT_POST_APPLICATION_REVIEWAUDIT_DELETE, "project/post/application/reviewAudit/delete", "删除后期资助项目申请评审审核"},
		{PROJECT_POST_APPLICATION_REVIEWAUDIT_MODIFY, "project/post/application/reviewAudit/modify", "修改后期资助项目申请评审审核"},
		{PROJECT_POST_APPLICATION_REVIEWAUDIT_VIEW, "project/post/application/reviewAudit/view", "查看后期资助项目申请评审审核"},
		{PROJECT_POST_APPLICATION_REVIEWAUDIT_SUBMIT, "project/post/application/reviewAudit/submit", "提交后期资助项目申请评审审核"},
		// 立项07050103
		{PROJECT_POST_APPLICATION_GRANTED_ADD, "project/post/application/granted/add", "添加后期资助项目立项"},
		{PROJECT_POST_APPLICATION_GRANTED_DELETE, "project/post/application/granted/delete", "删除后期资助项目立项"},
		{PROJECT_POST_APPLICATION_GRANTED_MODIFY, "project/post/application/granted/modify", "修改后期资助项目立项"},
		{PROJECT_POST_APPLICATION_GRANTED_VIEW, "project/post/application/granted/view", "查看后期资助项目立项"},
		{PROJECT_POST_APPLICATION_GRANTED_SIMPLESEARCH, "project/post/application/granted/simpleSearch", "初级检索后期资助项目立项"},
		{PROJECT_POST_APPLICATION_GRANTED_ADVSEARCH, "project/post/application/granted/advSearch", "高级检索后期资助项目立项"},
		{PROJECT_POST_APPLICATION_GRANTED_SETUPPROJECTSTATUS, "project/post/application/granted/setUpProjectStatus", "设置项目状态"},
		{PROJECT_POST_APPLICATION_GRANTED_LIST, "project/post/application/granted/list", "进入后期资助项目立项列表"},
		{PROJECT_POST_APPLICATION_GRANTED_LIST, "project/post/application/granted/toList", "project/post/application/granted/toList.action?update=1","后期资助项目立项数据"},
		
		// 结项070503
		// 申请07050301
		{PROJECT_POST_ENDINSPECTION_APPLY_ADD, "project/post/endinspection/apply/add", "添加后期资助项目结项申请"},
		{PROJECT_POST_ENDINSPECTION_APPLY_DELETE, "project/post/endinspection/apply/delete", "删除后期资助项目结项申请"},
		{PROJECT_POST_ENDINSPECTION_APPLY_MODIFY, "project/post/endinspection/apply/modify", "修改后期资助项目结项申请"},
		{PROJECT_POST_ENDINSPECTION_APPLY_VIEW, "project/post/endinspection/apply/view", "查看后期资助项目结项申请"},
		{PROJECT_POST_ENDINSPECTION_APPLY_SIMPLESEARCH, "project/post/endinspection/apply/simpleSearch", "初级检索后期资助项目结项申请"},
		{PROJECT_POST_ENDINSPECTION_APPLY_ADVSEARCH, "project/post/endinspection/apply/advSearch", "高级检索后期资助项目结项申请"},
		{PROJECT_POST_ENDINSPECTION_APPLY_DOWNLOAD, "project/post/endinspection/apply/downloadApply", "下载后期资助项目结项申请书"},
		{PROJECT_POST_ENDINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/post/endinspection/apply/downloadEndFile", "下载后期资助项目结项申请书摸板"},
		{PROJECT_POST_ENDINSPECTION_APPLY_SUBMIT, "project/post/endinspection/apply/submit", "提交后期资助项目结项申请"},
		{PROJECT_POST_ENDINSPECTION_APPLY_LIST, "project/post/endinspection/apply/list", "进入后期资助项目结项申请列表"},
		{PROJECT_POST_ENDINSPECTION_APPLY_LIST, "project/post/endinspection/apply/toList","project/post/endinspection/apply/toList.action?update=1", "后期资助项目结项数据"},
		
		// 审核07050302
		{PROJECT_POST_ENDINSPECTION_APPLYAUDIT_ADD, "project/post/endinspection/applyAudit/add", "添加后期资助项目结项审核"},
		{PROJECT_POST_ENDINSPECTION_APPLYAUDIT_DELETE, "project/post/endinspection/applyAudit/delete", "删除后期资助项目结项审核"},
		{PROJECT_POST_ENDINSPECTION_APPLYAUDIT_MODIFY, "project/post/endinspection/applyAudit/modify", "修改后期资助项目结项审核"},
		{PROJECT_POST_ENDINSPECTION_APPLYAUDIT_VIEW, "project/post/endinspection/applyAudit/view", "查看后期资助项目结项审核"},
		{PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_SUBMIT, "project/post/endinspection/applyAudit/submit", "提交后期资助项目结项审核"},
		{PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_BACK, "project/post/endinspection/applyAudit/back", "退回后期资助项目结项申请"},
		{PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_ADDPRODREVIEW, "project/post/endinspection/applyAudit/addProdReview", "添加后期资助项目结项成果审核"},
		{PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_MODIFYPRODREVIEW, "project/post/endinspection/applyAudit/modifyProdReview", "修改后期资助项目结项成果审核"},
		{PROJECT_POST_ENDDINSPECTION_APPLYAUDIT_SUBMITPRODREVIEW, "project/post/endinspection/applyAudit/submitProdReview", "提交后期资助项目结项成果审核"},
		// 评审07050303
		{PROJECT_POST_ENDINSPECTION_REVIEW_ADD, "project/post/endinspection/review/add", "添加后期资助项目结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_DELETE, "project/post/endinspection/review/delete", "删除后期资助项目结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_MODIFY, "project/post/endinspection/review/modify", "修改后期资助项目结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_VIEW, "project/post/endinspection/review/view", "查看后期资助项目结项专家评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_VIEWREVIEW, "project/post/endinspection/review/viewReview", "查看后期资助项目结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_SUBMIT, "project/post/endinspection/review/submit", "提交后期资助项目结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_ADDGROUP, "project/post/endinspection/review/addGroup", "添加后期资助项目小组结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_DELETEGROUP, "project/post/endinspection/review/deleteGroup", "删除后期资助项目小组结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_MODIFYGROUP, "project/post/endinspection/review/modifyGroup", "修改后期资助项目小组结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_VIEWGROUP, "project/post/endinspection/review/viewGroup", "查看后期资助项目结项小组评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_VIEWGROUPOPINION, "project/post/endinspection/review/viewGroupOpinion", "查看后期资助项目结项所有专家评审意见"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_SUBMITGROUP, "project/post/endinspection/review/submitGroup", "提交后期资助项目小组结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_SIMPLESEARCH, "project/post/endinspection/review/simpleSearch", "初级检索后期资助项目结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_ADVSEARCH, "project/post/endinspection/review/advSearch", "高级检索后期资助项目结项评审"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_LIST, "project/post/endinspection/review/list", "进入后期资助项目结项评审列表"},
		{PROJECT_POST_ENDINSPECTION_REVIEW_LIST, "project/post/endinspection/review/toList","project/post/endinspection/review/toList.action?update=1", "后期资助项目结项评审"},
		
		// 评审审核07050304
		{PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_ADD, "project/post/endinspection/reviewAudit/add", "添加后期资助项目评审审核"},
		{PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_DELETE, "project/post/endinspection/reviewAudit/delete", "删除后期资助项目评审审核"},
		{PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_MODIFY, "project/post/endinspection/reviewAudit/modify", "修改后期资助项目评审审核"},
		{PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_VIEW, "project/post/endinspection/reviewAudit/view", "查看后期资助项目评审审核"},
		{PROJECT_POST_ENDINSPECTION_REVIEWAUDIT_SUBMIT, "project/post/endinspection/reviewAudit/submit", "提交后期资助项目评审审核"},
		// 结果管理07050305
		{PROJECT_POST_ENDINSPECTION_DATA_ADD, "project/post/endinspection/apply/addResult", "添加后期资助项目结项结果"},
		{PROJECT_POST_ENDINSPECTION_DATA_MODIFY, "project/post/endinspection/apply/modifyResult", "修改后期资助项目结项结果"},
		{PROJECT_POST_ENDINSPECTION_DATA_SUBMIT, "project/post/endinspection/apply/submitResult", "提交后期资助项目结项结果"},
		//打印
		{PROJECT_POST_ENDINSPECTION_PRINT_DO, "project/post/endinspection/apply/printCertificate", "打印结项证书"},
		{PROJECT_POST_ENDINSPECTION_PRINT_CONFIRM, "project/post/endinspection/apply/confirmPrintCertificate", "确认打印结项证书"},
		// 变更070504
		// 申请07050401
		{PROJECT_POST_VARIATION_APPLY_ADD, "project/post/variation/apply/add", "添加后期资助项目变更申请"},
		{PROJECT_POST_VARIATION_APPLY_SUBMIT, "project/post/variation/apply/submit", "提交后期资助项目变更申请"},
		{PROJECT_POST_VARIATION_APPLY_DELETE, "project/post/variation/apply/delete", "删除后期资助项目变更申请"},
		{PROJECT_POST_VARIATION_APPLY_MODIFY, "project/post/variation/apply/modify", "修改后期资助项目变更申请"},
		{PROJECT_POST_VARIATION_APPLY_VIEW, "project/post/variation/apply/view", "查看后期资助项目变更申请"},
		{PROJECT_POST_VARIATION_APPLY_SIMPLESEARCH, "project/post/variation/apply/simpleSearch", "初级检索后期资助项目变更申请"},
		{PROJECT_POST_VARIATION_APPLY_ADVSEARCH, "project/post/variation/apply/advSearch", "高级检索后期资助项目变更申请"},
		{PROJECT_POST_VARIATION_APPLY_DOWNLOADTEMPLATE, "project/post/variation/apply/downloadTemplate", "下载后期资助项目变更申请书模板"},
		{PROJECT_POST_VARIATION_APPLY_DOWNLOAD, "project/post/variation/apply/downloadApply", "下载后期资助项目变更申请书"},
		{PROJECT_POST_VARIATION_APPLY_VIEWDIR, "project/post/variation/apply/viewDir", "查看后期资助项目变更责任人"},
		{PROJECT_POST_VARIATION_APPLY_LIST, "project/post/variation/apply/list", "进入后期资助项目变更申请列表"},
		{PROJECT_POST_VARIATION_APPLY_LIST, "project/post/variation/apply/toList","project/post/variation/apply/toList.action?update=1", "后期资助项目变更数据"},
		
		// 审核07050402
		{PROJECT_POST_VARIATION_APPLYAUDIT_ADD, "project/post/variation/applyAudit/add", "添加后期资助项目变更审核"},
		{PROJECT_POST_VARIATION_APPLYAUDIT_MODIFY, "project/post/variation/applyAudit/modify", "修改后期资助项目变更审核"},
		{PROJECT_POST_VARIATION_APPLYAUDIT_VIEW, "project/post/variation/applyAudit/view", "查看后期资助项目变更审核"},
		{PROJECT_POST_VARIATION_APPLYAUDIT_SUBMIT, "project/post/variation/applyAudit/submit", "提交后期资助项目变更审核"},
		{PROJECT_POST_VARIATION_APPLYAUDIT_BACK, "project/post/variation/applyAudit/back", "退回后期资助项目变更审核"},
		// 变更结果管理07050403
		{PROJECT_POST_VARIATION_DATA_ADD, "project/post/variation/apply/addResult", "添加后期资助项目变更结果"},
		{PROJECT_POST_VARIATION_DATA_MODIFY, "project/post/variation/apply/modifyResult", "修改后期资助项目变更结果"},
		{PROJECT_POST_VARIATION_DATA_SUBMIT, "project/post/variation/apply/submitResult", "提交后期资助项目变更结果"},
		// 年检070505
		// 申请07050501
		{PROJECT_POST_ANNINSPECTION_APPLY_ADD, "project/post/anninspection/apply/add", "添加后期资助项目年检申请"},
		{PROJECT_POST_ANNINSPECTION_APPLY_DELETE, "project/post/anninspection/apply/delete", "删除后期资助项目年检申请"},
		{PROJECT_POST_ANNINSPECTION_APPLY_MODIFY, "project/post/anninspection/apply/modify", "修改后期资助项目年检申请"},
		{PROJECT_POST_ANNINSPECTION_APPLY_VIEW, "project/post/anninspection/apply/view", "查看后期资助项目年检申请"},
		{PROJECT_POST_ANNINSPECTION_APPLY_SIMPLESEARCH, "project/post/anninspection/apply/simpleSearch", "初级检索后期资助项目年检申请"},
		{PROJECT_POST_ANNINSPECTION_APPLY_ADVSEARCH, "project/post/anninspection/apply/advSearch", "高级检索后期资助项目年检申请"},
		{PROJECT_POST_ANNINSPECTION_APPLY_DOWNLOAD, "project/post/anninspection/apply/downloadApply", "下载后期资助项目年检申请书"},
		{PROJECT_POST_ANNINSPECTION_APPLY_DOWNLOADTEMPLATE, "project/post/anninspection/apply/downloadAnnFile", "下载后期资助项目年检申请书摸板"},
		{PROJECT_POST_ANNINSPECTION_APPLY_SUBMIT, "project/post/anninspection/apply/submit", "提交后期资助项目年检申请"},
		{PROJECT_POST_ANNINSPECTION_APPLY_LIST, "project/post/anninspection/apply/list", "进入后期资助项目年检申请列表"},
		{PROJECT_POST_ANNINSPECTION_APPLY_LIST, "project/post/anninspection/apply/toList", "project/post/anninspection/apply/toList.action?update=1","后期资助项目年检数据"},
		
		// 审核07050502
		{PROJECT_POST_ANNINSPECTION_APPLYAUDIT_ADD, "project/post/anninspection/applyAudit/add", "添加后期资助项目年检审核"},
		{PROJECT_POST_ANNINSPECTION_APPLYAUDIT_DELETE, "project/post/anninspection/applyAudit/delete", "删除后期资助项目年检审核"},
		{PROJECT_POST_ANNINSPECTION_APPLYAUDIT_MODIFY, "project/post/anninspection/applyAudit/modify", "修改后期资助项目年检审核"},
		{PROJECT_POST_ANNINSPECTION_APPLYAUDIT_VIEW, "project/post/anninspection/applyAudit/view", "查看后期资助项目年检审核"},
		{PROJECT_POST_ANNINSPECTION_APPLYAUDIT_SUBMIT, "project/post/anninspection/applyAudit/submit", "提交后期资助项目年检审核"},
		{PROJECT_POST_ANNINSPECTION_APPLYAUDIT_BACK, "project/post/anninspection/applyAudit/back", "退回后期资助项目年检申请"},
		// 结果管理07050503
		{PROJECT_POST_ANNINSPECTION_DATA_ADD, "project/post/anninspection/apply/addResult", "添加后期资助项目年检结果"},
		{PROJECT_POST_ANNINSPECTION_DATA_MODIFY, "project/post/anninspection/apply/modifyResult", "修改后期资助项目年检结果"},
		{PROJECT_POST_ANNINSPECTION_DATA_SUBMIT, "project/post/anninspection/apply/submitResult", "提交后期资助项目年检结果"},
		
		{PROJECT_ENTRUST_APPLICATION_APPLY_TOLIST, "project/entrust/application/apply/toList", "project/entrust/application/apply/toList.action?update=1","委托应急课题申请数据"},
		{PROJECT_ENTRUST_APPLICATION_GRANTED_TOLIST, "project/entrust/application/granted/toList", "project/entrust/application/granted/toList.action?update=1","委托应急课题立项数据"},
		{PROJECT_ENTRUST_ENDINSPECTION_APPLY_TOLIST, "project/entrust/endinspection/apply/toList", "project/entrust/endinspection/apply/toList.action?update=1","委托应急课题结项数据"},
		{PROJECT_ENTRUST_VARIATION_APPLY_TOLIST, "project/entrust/variation/apply/toList", "project/entrust/variation/apply/toList.action?update=1","委托应急课题变更数据"},
		{PROJECT_ENTRUST_VARIATION_APPLY_TOLIST, "project/entrust/application/review/toList", "project/entrust/application/review/toList.action?update=1","委托应急课题申请评审"},
		{PROJECT_ENTRUST_VARIATION_APPLY_TOLIST, "project/entrust/endinspection/review/toList", "project/entrust/endinspection/review/toList.action?update=1","委托应急课题结项评审"},
		
		
		// 成果08
		// 个人负责成果0801
		{PRODUCT_SELF_DELETE, "product/delete", "删除个人成果"},
		{PRODUCT_SELF_VIEW, "product/viewDirectProduct", "查看个人成果"},
		{PRODUCT_SELF_SEARCH, "product/searchDirectProduct", "检索个人成果"},
		{PRODUCT_SELF_AUDIT, "product/audit", "审核个人成果"},
		// 论文0802
		{PRODUCT_PAPER_ADD, "product/paper/add", "添加论文成果"},
		{PRODUCT_PAPER_DELETE, "product/paper/delete", "删除论文成果"},
		{PRODUCT_PAPER_MODIFY, "product/paper/modify", "修改论文成果"},
		{PRODUCT_PAPER_VIEW, "product/paper/view", "查看论文成果"},
		{PRODUCT_PAPER_SIMPLESEARCH, "product/paper/simpleSearch", "初级检索论文成果"},
		{PRODUCT_PAPER_ADVSEARCH, "product/paper/advSearch", "高级检索论文成果"},
		{PRODUCT_PAPER_AUDIT, "product/paper/audit", "审核论文成果"},
		{PRODUCT_PAPER_VIEWAUDIT, "product/paper/viewAudit", "查看论文成果审核信息"},
		{PRODUCT_PAPER_DOWNLOAD, "product/paper/download", "下载论文成果"},
		{PRODUCT_PAPER_EXADD, "product/paper/exAdd", "项目添加已有论文成果"},
		{PRODUCT_PAPER_LIST, "product/paper/list", "进入论文成果列表"},
		{PRODUCT_PAPER_LIST, "product/paper/toList", "product/paper/toList.action?update=1" ,"论文成果数据"},
		// 书籍0803
		{PRODUCT_BOOK_ADD, "product/book/add", "添加书籍成果"},
		{PRODUCT_BOOK_DELETE, "product/book/delete", "删除书籍成果"},
		{PRODUCT_BOOK_MODIFY, "product/book/modify", "修改书籍成果"},
		{PRODUCT_BOOK_VIEW, "product/book/view", "查看书籍成果"},
		{PRODUCT_BOOK_SIMPLESEARCH, "product/book/simpleSearch", "初级检索书籍成果"},
		{PRODUCT_BOOK_ADVSEARCH, "product/book/advSearch", "高级检索书籍成果"},
		{PRODUCT_BOOK_AUDIT, "product/book/audit", "审核书籍成果"},
		{PRODUCT_BOOK_VIEWAUDIT, "product/book/viewAudit", "查看书籍成果审核信息"},
		{PRODUCT_BOOK_DOWNLOAD, "product/book/download", "下载书籍成果"},
		{PRODUCT_BOOK_EXADD, "product/book/exAdd", "项目添加已有书籍成果"},
		{PRODUCT_BOOK_LIST, "product/book/list", "进入书籍成果列表"},
		{PRODUCT_BOOK_LIST, "product/book/toList", "product/book/toList.action?update=1" ,"书籍成果数据"},
		
		// 研究咨询报告0804
		{PRODUCT_CONSULTATION_ADD, "product/consultation/add", "添加研究咨询报告成果"},
		{PRODUCT_CONSULTATION_DELETE, "product/consultation/delete", "删除研究咨询报告成果"},
		{PRODUCT_CONSULTATION_MODIFY, "product/consultation/modify", "修改研究咨询报告成果"},
		{PRODUCT_CONSULTATION_VIEW, "product/consultation/view", "查看研究咨询报告成果"},
		{PRODUCT_CONSULTATION_SIMPLESEARCH, "product/consultation/simpleSearch", "初级检索研究咨询报告成果"},
		{PRODUCT_CONSULTATION_ADVSEARCH, "product/consultation/advSearch", "高级检索研究咨询报告成果"},
		{PRODUCT_CONSULTATION_AUDIT, "product/consultation/audit", "审核研究咨询报告成果"},
		{PRODUCT_CONSULTATION_VIEWAUDIT, "product/consultation/viewAudit", "查看研究咨询报告成果审核信息"},
		{PRODUCT_CONSULTATION_DOWNLOAD, "product/consultation/download", "下载研究咨询报告成果"},
		{PRODUCT_CONSULTATION_EXADD, "product/consultation/exAdd", "项目添加已有研究咨询报告成果"},
		{PRODUCT_CONSULTATION_LIST, "product/consultation/list", "进入研究咨询报告成果列表"},
		{PRODUCT_CONSULTATION_LIST, "product/consultation/toList", "product/consultation/toList.action?update=1","研究咨询报告成果数据"},
		{PRODUCT_CONSULTATION_LIST, "product/electronic/toList", "product/electronic/toList.action?update=1","电子出版物成果数据"},
		{PRODUCT_CONSULTATION_LIST, "product/patent/toList", "product/patent/toList.action?update=1","专刊成果数据"},
		{PRODUCT_CONSULTATION_LIST, "product/otherProduct/toList", "product/otherProduct/toList.action?update=1","其他成果数据"},
		
		// 奖励09
		//个人奖励0901
		{AWARD_SELF_SEARCH,"award/searchMyAward","查询个人奖励"},
		{AWARD_SELF_VIEW,"award/viewMyAward","查看个人奖励"},
		// 人文社科奖0902
		// 申请090201
		{AWARD_MOESOCIAL_APPLY_ADD, "award/moesocial/application/apply/add", "添加人文社科奖申请"},
		{AWARD_MOESOCIAL_APPLY_DELETE, "award/moesocial/application/apply/delete", "删除人文社科奖申请"},
		{AWARD_MOESOCIAL_APPLY_MODIFY, "award/moesocial/application/apply/modify", "修改人文社科奖申请"},
		{AWARD_MOESOCIAL_APPLY_SUBMIT, "award/moesocial/application/apply/submit", "提交人文社科奖申请"},
		{AWARD_MOESOCIAL_APPLY_VIEW, "award/moesocial/application/apply/view", "查看人文社科奖申请"},
		{AWARD_MOESOCIAL_APPLY_SIMPLESEARCH, "award/moesocial/application/apply/simpleSearch", "初级检索人文社科奖申请"},
		{AWARD_MOESOCIAL_APPLY_ADVSEARCH, "award/moesocial/application/apply/advSearch", "高级检索人文社科奖申请"},
		{AWARD_MOESOCIAL_APPLY_DOWNLOADMODEL, "award/moesocial/application/apply/downloadModel", "下载人文社科奖申请书模板"},
		{AWARD_MOESOCIAL_APPLY_DOWNLOAD, "award/moesocial/application/apply/download", "下载人文社科奖申请书"},
		{AWARD_MOESOCIAL_APPLY_LIST, "award/moesocial/application/apply/list", "进入人文社科奖申请列表"},
		{AWARD_MOESOCIAL_APPLY_LIST, "award/moesocial/application/apply/toList","award/moesocial/application/apply/toList.action?update=1&listflag=1" , "人文社科奖申请数据"},
		
		// 审核090202
		{AWARD_MOESOCIAL_APPLYAUDIT_ADD, "award/moesocial/application/applyAudit/add", "添加人文社科奖审核"},
		{AWARD_MOESOCIAL_APPLYAUDIT_MODIFY, "award/moesocial/application/applyAudit/modify", "修改人文社科奖审核"},
		{AWARD_MOESOCIAL_APPLYAUDIT_VIEW, "award/moesocial/application/applyAudit/view", "查看人文社科奖审核"},
		{AWARD_MOESOCIAL_APPLYAUDIT_SUBMIT, "award/moesocial/application/applyAudit/submit", "提交人文社科奖审核"},
		{AWARD_MOESOCIAL_APPLYAUDIT_BACK, "award/moesocial/application/applyAudit/back", "退回人文社科奖审核"},
		// 评审090203
		{AWARD_MOESOCIAL_REVIEW_ADD, "award/moesocial/application/review/add", "添加人文社科奖评审"},
		{AWARD_MOESOCIAL_REVIEW_MODIFY, "award/moesocial/application/review/modify", "修改人文社科奖评审"},
		{AWARD_MOESOCIAL_REVIEW_VIEW, "award/moesocial/application/review/view", "查看人文社科奖评审"},
		{AWARD_MOESOCIAL_REVIEW_SUBMIT, "award/moesocial/application/review/submit", "提交人文社科奖评审"},
		{AWARD_MOESOCIAL_REVIEW_ADDGROUP, "award/moesocial/application/review/addGroup", "添加人文社科奖小组评审"},
		{AWARD_MOESOCIAL_REVIEW_MODIFYGROUP, "award/moesocial/application/review/modifyGroup", "修改人文社科奖小组评审"},
		{AWARD_MOESOCIAL_REVIEW_VIEWGROUP, "award/moesocial/application/review/viewGroup", "查看人文社科奖小组评审"},
		{AWARD_MOESOCIAL_REVIEW_SUBMITGROUP, "award/moesocial/application/review/submitGroup", "提交人文社科奖小组评审"},
		{AWARD_MOESOCIAL_REVIEW_SIMPLESEARCH, "award/moesocial/application/review/simpleSearch", "初级检索人文社科奖评审"},
		{AWARD_MOESOCIAL_REVIEW_ADVSEARCH, "award/moesocial/application/review/advSearch", "高级检索人文社科奖评审"},
		{AWARD_MOESOCIAL_REVIEW_VIEWREVIEW, "award/moesocial/application/review/viewReview", "查看人文社科奖专家评审"},
		{AWARD_MOESOCIAL_REVIEW_VIEWGROUPOPINION, "award/moesocial/application/review/viewGroupOpinion", "查看人文社科奖所有专家评审意见"},
		{AWARD_MOESOCIAL_REVIEW_LIST, "award/moesocial/application/review/list", "进入人文社科奖评审列表"},
		{AWARD_MOESOCIAL_REVIEW_LIST, "award/moesocial/application/review/toList", "award/moesocial/application/review/toList.action?update=1&listflag=4","人文社科奖评审数据"},
		
		// 评审审核090204
		{AWARD_MOESOCIAL_REVIEWAUDIT_ADD, "award/moesocial/application/reviewAudit/add", "添加人文社科奖评审审核"},
		{AWARD_MOESOCIAL_REVIEWAUDIT_MODIFY, "award/moesocial/application/reviewAudit/modify", "修改人文社科奖评审审核"},
		{AWARD_MOESOCIAL_REVIEWAUDIT_VIEW, "award/moesocial/application/reviewAudit/view", "查看人文社科奖评审审核"},
		{AWARD_MOESOCIAL_REVIEWAUDIT_SUBMIT, "award/moesocial/application/reviewAudit/submit", "提交人文社科奖评审审核"},
		// 公示090205
		{AWARD_MOESOCIAL_PUBLICITY_VIEW, "award/moesocial/application/publicity/view", "查看人文社科奖公示数据"},
		{AWARD_MOESOCIAL_PUBLICITY_SIMPLESEARCH, "award/moesocial/application/publicity/simpleSearch", "初级检索人文社科奖公示数据"},
		{AWARD_MOESOCIAL_PUBLICITY_ADVSEARCH, "award/moesocial/application/publicity/advSearch", "高级检索人文社科奖公示数据"},
		{AWARD_MOESOCIAL_PUBLICITY_LIST, "award/moesocial/application/publicity/list", "进入人文社科奖公示数据列表"},
		{AWARD_MOESOCIAL_PUBLICITY_LIST, "award/moesocial/application/publicity/toList","award/moesocial/application/publicity/toList.action?update=1&listflag=2", "人文社科奖公示数据"},
		
		// 公示审核090206
		{AWARD_MOESOCIAL_PUBLICITYAUDIT_ADD, "award/moesocial/application/publicityAudit/add", "添加人文社科奖公示审核"},
		{AWARD_MOESOCIAL_PUBLICITYAUDIT_MODIFY, "award/moesocial/application/publicityAudit/modify", "修改人文社科奖公示审核"},
		{AWARD_MOESOCIAL_PUBLICITYAUDIT_VIEW, "award/moesocial/application/publicityAudit/view", "查看人文社科奖公示审核"},
		{AWARD_MOESOCIAL_PUBLICITYAUDIT_SUBMIT, "award/moesocial/application/publicityAudit/submit", "提交人文社科奖公示审核"},
		// 获奖090207
		{AWARD_MOESOCIAL_AWARDED_VIEW, "award/moesocial/application/awarded/view", "查看人文社科奖奖励数据"},
		{AWARD_MOESOCIAL_AWARDED_SIMPLESEARCH, "award/moesocial/application/awarded/simpleSearch", "初级检索人文社科奖奖励数据"},
		{AWARD_MOESOCIAL_AWARDED_ADVSEARCH, "award/moesocial/application/awarded/advSearch", "高级检索人文社科奖奖励数据"},
		{AWARD_MOESOCIAL_AWARDED_LIST, "award/moesocial/application/awarded/list", "进入人文社科奖奖励数据列表"},
		{AWARD_MOESOCIAL_AWARDED_LIST, "award/moesocial/application/awarded/toList","award/moesocial/application/awarded/toList.action?update=1&listflag=3", "人文社科奖获奖数据"},
		
		
		{OTHER_NSFC_LIST, "other/nsfc/toList","other/nsfc/toList.action?update=1", "国家自然科学基金项目"},
		{OTHER_NSSF_LIST, "other/nssf/toList","other/nssf/toList.action?update=1", "国家社会科学基金项目"},
		
		{BUSINESS_LIST, "business/toList","business/toList.action?update=1", "社科业务日程"},
		
		// 弹层11
		// 弹出查看1101
		{POP_VIEW_ACCOUNT, "view/viewAccount", "弹出层查看账号"},
		{POP_VIEW_PERSON, "view/viewPerson", "弹出层查看人员"},
		{POP_VIEW_AGENCY, "view/viewAgency", "弹出层查看机构"},
		{POP_VIEW_PROJECT, "view/viewProject", "弹出层查看项目"},
		{POP_VIEW_LOG, "view/viewLog", "弹出层查看日志"},
		{POP_VIEW_MEMO, "view/viewMemo", "弹出层查看备忘录"},
		
		// 弹出选择1102
		{POP_SELECT_MINISTRY_LIST, "selectMinistry/list", "弹出层进入选择部级机构列表"},
		{POP_SELECT_MINISTRY_SIMPLESEARCH, "selectMinistry/simpleSearch", "弹出层初级检索部级机构数据"},
		{POP_SELECT_PROVINCE_LIST, "selectProvince/list", "弹出层进入选择省级机构列表"},
		{POP_SELECT_PROVINCE_SIMPLESEARCH, "selectProvince/simpleSearch", "弹出层初级检索省级机构数据"},
		{POP_SELECT_UNIVERSITY_LIST, "selectUniversity/list", "弹出层进入选择校级机构列表"},
		{POP_SELECT_UNIVERSITY_SIMPLESEARCH, "selectUniversity/simpleSearch", "弹出层初级检索校级机构数据"},
		{POP_SELECT_MINISTRYGROUP_LIST, "selectMinistryGroup/list", "弹出层进入选择多个部级机构列表"},
		{POP_SELECT_MINISTRYGROUP_SIMPLESEARCH, "selectMinistryGroup/simpleSearch", "弹出层初级检索多个部级机构数据"},
		{POP_SELECT_PROVINCEGROUP_LIST, "selectProvinceGroup/list", "弹出层进入选择多个省级机构列表"},
		{POP_SELECT_PROVINCEGROUP_SIMPLESEARCH, "selectProvinceGroup/simpleSearch", "弹出层初级检索多个省级机构数据"},
		{POP_SELECT_UNIVERSITYGROUP_LIST, "selectUniversityGroup/list", "弹出层进入选择多个校级机构列表"},
		{POP_SELECT_UNIVERSITYGROUP_SIMPLESEARCH, "selectUniversityGroup/simpleSearch", "弹出层初级检索多个校级机构数据"},
		{POP_SELECT_DEPARTMENT_LIST, "selectDepartment/list", "弹出层进入选择院系列表"},
		{POP_SELECT_DEPARTMENT_SIMPLESEARCH, "selectDepartment/simpleSearch", "弹出层初级检索院系数据"},
		{POP_SELECT_INSTITUTE_LIST, "selectInstitute/list", "弹出层进入选择研究基地列表"},
		{POP_SELECT_INSTITUTE_SIMPLESEARCH, "selectInstitute/simpleSearch", "弹出层初级检索研究基地数据"},

		{POP_SELECT_OFFICER_LIST, "selectOfficer/list", "弹出层进入选择管理人员列表"},
		{POP_SELECT_OFFICER_SIMPLESEARCH, "selectOfficer/simpleSearch", "弹出层初级检索管理人员数据"},
		{POP_SELECT_EXPERT_LIST, "selectExpert/list", "弹出层进入选择专家列表"},
		{POP_SELECT_EXPERT_SIMPLESEARCH, "selectExpert/simpleSearch", "弹出层初级检索专家数据"},
		{POP_SELECT_TEACHER_LIST, "selectTeacher/list", "弹出层进入选择教师列表"},
		{POP_SELECT_TEACHER_SIMPLESEARCH, "selectTeacher/simpleSearch", "弹出层初级检索教师数据"},
		{POP_SELECT_STUDENT_LIST, "selectStudent/list", "弹出层进入选择学生列表"},
		{POP_SELECT_STUDENT_SIMPLESEARCH, "selectStudent/simpleSearch", "弹出层初级检索学生数据"},
		
		{POP_SELECT_PROJECT_LIST, "selectProject/list", "弹出层进入选择项目列表"},
		{POP_SELECT_PROJECT_SIMPLESEARCH, "selectProject/simpleSearch", "弹出层初级检索项目数据"},
		{POP_SELECT_PROJECT_LIST, "selectTopic/list", "弹出层进入选择课题列表"},
		{POP_SELECT_PROJECT_SIMPLESEARCH, "selectTopic/simpleSearch", "弹出层初级检索课题数据"},
		{POP_SELECT_PUBLICATION_LIST, "selectPublication/list", "弹出层进入选择刊物列表"},
		{POP_SELECT_PUBLICATION_SIMPLESEARCH, "selectPublication/simpleSearch", "弹出层初级检索刊物数据"},
		{POP_SELECT_UNGRANTEDPROJECT_LIST, "selectUngrantedProject/list", "弹出层进入选择已申请未立项项目列表"},
		{POP_SELECT_UNGRANTEDPROJECT_SIMPLESEARCH, "selectUngrantedProject/simpleSearch", "弹出层初级检索已申请未立项项目数据"},
		
		{POP_SELECT_DISCIPLINETYPE_SIMPLESEARCH, "selectDisciplineType/simpleSearch", "弹出层选择学科类型"},
		{POP_SELECT_RELYDISCIPLINES_TOSELECT, "selectRelyDisciplines/toSelect", "弹出层选择相关学科"},
		{POP_SELECT_ETHNICLANGUAGE_TOSELECT, "selectEthnicLanguage/toSelect", "弹出层选择名族语言"},
		{POP_SELECT_FOREIGNLANGUAGE_TOSELECT, "selectForeignLanguage/toSelect", "弹出层选择外语语种"},
		{POP_SELECT_INDEXTYPE_TOSELECT, "selectIndexType/toSelect", "弹出层选择索引类别"},
		{POP_SELECT_PRODUCTTYPE_TOSELECT, "selectProductType/toSelect", "弹出层初选择成果形式"},
		
		// 终端12
		// 终端登陆相关1201
		{MOBILE_LOGIN, "mobile/login/login", "mobile登陆"},
		{MOBILE_LOGOUT, "mobile/login/logout", "mobile退出"},
		{MOBILE_ACCOUNT_SWITCH, "mobile/login/switchAccount", "mobile切换账号"},
		{MOBILE_ACCOUNT_SELECT, "mobile/login/selectAccount", "mobile选择账号"},
		{MOBILE_CURRENT_ACCOUNT, "mobile/login/getCurrentAccount", "mobile获取当前登陆账号"},
		{MOBILE_VIEW_MYSELF, "mobile/selfspace/viewMyself", "mobile查看我的资料"},
		{MOBILE_MODIFY_MYSELF, "mobile/selfspace/modifyMyself", "mobile编辑我的资料"},
		{MOBILE_VIEW_TODO, "mobile/selfspace/viewMyToDo", "mobile查看待办事宜"},
		{MOBILE_VIEW_PROJECT, "mobile/selfspace/viewMyProject", "mobile查看我的项目"},
		{MOBILE_VIEW_PRODUCT, "mobile/selfspace/viewMyProduct", "mobile查看我的成果"},
		{MOBILE_VIEW_AWARD, "mobile/selfspace/viewMyAward", "mobile查看我的奖励"},
		{MOBILE_RESET_PASSWORD, "mobile/setting/resetPassword", "mobile重置密码"},
		{MOBILE_CHECK_UPDATE, "mobile/setting/checkUpdate", "mobile检测新版本"},
		{MOBILE_GET_FEEDBACK, "mobile/setting/getFeedback", "mobile用户反馈"},
		{MOBILE_GET_HELP, "mobile/setting/getHelp", "mobile查看帮助"},
		{MOBILE_GET_ABOUT, "mobile/setting/getAbout", "mobile查看关于"},
		// 终端消息系统1202
		{MOBILE_MESSAGE_SIMPLESEARCH, "mobile/info/message/simpleSearch", "mobile留言簿列表"},
		{MOBILE_MESSAGE_VIEW, "mobile/info/message/view", "mobile留言簿详情查看"},
		{MOBILE_MESSAGE_TOPAGE, "mobile/info/message/toPage", "mobile留言簿翻页"},
		{MOBILE_MESSAGE_ADD, "mobile/info/message/add", "mobile留言簿添加"},
		{MOBILE_MESSAGE_MODIFY, "mobile/info/message/modify", "mobile留言簿修改"},
		{MOBILE_MESSAGE_DELETE, "mobile/info/message/deleteMessage", "mobile留言簿删除"},
		{MOBILE_NEWS_SIMPLESEARCH, "mobile/info/news/simpleSearch", "mobile新闻列表"},
		{MOBILE_NEWS_VIEW, "mobile/info/news/view", "mobile新闻详情查看"},
		{MOBILE_NEWS_TOPAGE, "mobile/info/news/toPage", "mobile新闻翻页"},
		{MOBILE_NOTICE_SIMPLESEARCH, "mobile/info/notice/simpleSearch", "mobile通知列表"},
		{MOBILE_NOTICE_VIEW, "mobile/info/notice/view", "mobile通知详情查看"},
		{MOBILE_NOTICE_TOPAGE, "mobile/info/notice/toPage", "mobile通知翻页"},
		//终端统计分析1203
		{MOBILE_STATISTIC_COMMON_SIMPLESEARCH, "mobile/statistic/common/simpleSearch", "mobile常规统计列表和初级检索"},
		{MOBILE_STATISTIC_COMMON_ADVSEARCH, "mobile/statistic/common/advSearch", "mobile常规统计高级检索"},
		{MOBILE_STATISTIC_COMMON_VIEW, "mobile/statistic/common/view", "mobile常规统计详情查看"},
		{MOBILE_STATISTIC_COMMON_TOPAGE, "mobile/statistic/common/toPage", "mobile常规统计翻页"},
		{MOBILE_STATISTIC_COMMON_FETCHMENU, "mobile/statistic/common/fetchMenu", "mobile常规统计菜单选择"},
		{MOBILE_STATISTIC_COMMON_ADD, "mobile/statistic/common/add", "mobile添加常规统计"},
		{MOBILE_STATISTIC_COMMON_MODIFY, "mobile/statistic/common/modify", "mobile修改常规统计"},
		{MOBILE_STATISTIC_CUSTOM_VIEW, "mobile/statistic/custom/view", "mobile定制统计详情查看"},
		{MOBILE_STATISTIC_CUSTOM_FETCHMENU, "mobile/statistic/custom/fetchMenu", "mobile定制统计菜单选择"},
		{MOBILE_STATISTIC_CUSTOM_SELECTUNIVERSITY, "mobile/statistic/custom/selectUniversity", "mobile定制统计选择高校"},
		{MOBILE_STATISTIC_CUSTOM_SELECTUNIVERSITY_TOPAGE, "mobile/statistic/custom/toPage", "mobile定制统计选择高校翻页"},
		{MOBILE_STATISTIC_CUSTOM_GETMDX, "mobile/statistic/custom/getMdxToView", "mobile定制统计详情查看"},
		//终端基础数据库1204		
		{MOBILE_PERSON_SIMPLESEARCH, "mobile/basis/person/simpleSearch", "mobile人员列表和初级检索"},
		{MOBILE_PERSON_ADVSEARCH, "mobile/basis/person/advSearch", "mobile人员高级检索"},
		{MOBILE_PERSON_VIEW, "mobile/basis/person/view", "mobile人员详情查看"},
		{MOBILE_PERSON_TOPAGE, "mobile/basis/person/toPage", "mobile人员翻页"},
		{MOBILE_PERSON_FETCHMENU, "mobile/basis/person/fetchMenu", "mobile人员菜单选择"},
		{MOBILE_UNIT_SIMPLESEARCH, "mobile/basis/unit/simpleSearch", "mobile机构列表和初级检索"},
		{MOBILE_UNIT_ADVSEARCH, "mobile/basis/unit/advSearch", "mobile机构高级检索"},
		{MOBILE_UNIT_VIEW, "mobile/basis/unit/view", "mobile机构详情查看"},
		{MOBILE_UNIT_TOPAGE, "mobile/basis/unit/toPage", "mobile机构翻页"},
		{MOBILE_UNIT_FETCHMENU, "mobile/basis/unit/fetchMenu", "mobile机构菜单选择"},
		{MOBILE_PROJECT_SIMPLESEARCH, "mobile/basis/project/simpleSearch", "mobile项目列表和初级检索"},
		{MOBILE_PROJECT_ADVSEARCH, "mobile/basis/project/advSearch", "mobile项目高级检索"},
		{MOBILE_PROJECT_VIEW, "mobile/basis/project/view", "mobile项目详情查看"},
		{MOBILE_PROJECT_TOPAGE, "mobile/basis/project/toPage", "mobile项目翻页"},
		{MOBILE_PROJECT_FETCHMENU, "mobile/basis/project/fetchMenu", "mobile项目菜单选择"},
		{MOBILE_PROJECT_VIEWAUDIT, "mobile/basis/project/viewAudit", "mobile项目审核查看"},
		{MOBILE_PROJECT_AUDIT, "mobile/basis/project/audit", "mobile项目审核"},
		{MOBILE_PROJECT_TODO, "mobile/basis/project/projectToDo", "mobile项目代办事宜查看"},
		{MOBILE_PROJECTTODO_TOPAGE, "mobile/basis/project/anotherPage", "mobile项目代办事宜翻页"},
		{MOBILE_PRODUCT_SIMPLESEARCH, "mobile/basis/product/simpleSearch", "mobile成果列表和初级检索"},
		{MOBILE_PRODUCT_ADVSEARCH, "mobile/basis/product/advSearch", "mobile成果高级检索"},
		{MOBILE_PRODUCT_VIEW, "mobile/basis/product/view", "mobile成果详情查看"},
		{MOBILE_PRODUCT_TOPAGE, "mobile/basis/product/toPage", "mobile成果翻页"},
		{MOBILE_PRODUCT_FETCHMENU, "mobile/basis/product/fetchMenu", "mobile成果菜单选择"},
		{MOBILE_PRODUCT_VIEWAUDIT, "mobile/basis/product/viewAudit", "mobile成果审核查看"},
		{MOBILE_PRODUCT_AUDIT, "mobile/basis/product/audit", "mobile成果审核"},
		{MOBILE_AWARD_SIMPLESEARCH, "mobile/basis/award/simpleSearch", "mobile奖励列表和初级检索"},
		{MOBILE_AWARD_ADVSEARCH, "mobile/basis/award/advSearch", "mobile奖励高级检索"},
		{MOBILE_AWARD_VIEW, "mobile/basis/award/view", "mobile奖励详情查看"},
		{MOBILE_AWARD_TOPAGE, "mobile/basis/award/toPage", "mobile奖励翻页"},
		{MOBILE_AWARD_FETCHMENU, "mobile/basis/award/fetchMenu", "mobile奖励菜单选择"},
		{MOBILE_AWARD_VIEWAUDIT, "mobile/basis/award/viewAudit", "mobile奖励审核查看"},
		{MOBILE_AWARD_AUDIT, "mobile/basis/award/audit", "mobile奖励审核"},
		{MOBILE_AWARD_MODIFY, "mobile/basis/award/modify", "mobile奖励修改"},
		
		//经费
		{PROJECTFUND_GENERAL_LIST, "projectFund/general/toList","projectFund/general/toList.action?update=1", "一般项目拨款概况"},
		{FUNDLIST_GENERAL_GRANTED_LIST, "fundList/general/granted/toList","fundList/general/granted/toList.action?update=1", "一般项目立项拨款"},
		{FUNDLIST_GENERAL_MID_LIST, "fundList/general/mid/toList","fundList/general/mid/toList.action?update=1", "一般项目中检拨款"},
		{FUNDLIST_GENERAL_END_LIST, "fundList/general/end/toList","fundList/general/end/toList.action?update=1", "一般项目结项拨款"},
		
		{PROJECTFUND_KEY__LIST, "projectFund/key/toList","projectFund/key/toList.action?update=1", "重大攻关项目拨款概况"},
		{FUNDLIST_KEY_GRANTED_LIST, "fundList/key/granted/toList","fundList/key/granted/toList.action?update=1", "重大攻关项目立项拨款"},
		{FUNDLIST_KEY_MID_LIST, "fundList/key/mid/toList","fundList/key/mid/toList.action?update=1", "重大攻关项目中检拨款"},
		{FUNDLIST_KEY_END_LIST, "fundList/key/end/toList","fundList/key/end/toList.action?update=1", "重大攻关项目结项拨款"},
		
		{PROJECTFUND_POST__LIST, "projectFund/post/toList","projectFund/post/toList.action?update=1", "后期资助项目拨款概况"},
		{FUNDLIST_POST_GRANTED_LIST, "fundList/post/granted/toList","fundList/post/granted/toList.action?update=1", "后期资助项目立项拨款"},
		{FUNDLIST_POST_END_LIST, "fundList/post/end/toList","fundList/post/end/toList.action?update=1", "后期资助项目结项拨款"},
		
		{PROJECTFUND_ENTRUSTT__LIST, "projectFund/entrust/toList","projectFund/entrust/toList.action?update=1", "委托应急课题拨款概况"},
		{FUNDLIST_ENTRUST_GRANTED_LIST, "fundList/entrust/granted/toList","fundList/entrust/granted/toList.action?update=1", "委托应急课题立项拨款"},
		{FUNDLIST_ENTRUST_END_LIST, "fundList/entrust/end/toList","fundList/entrust/end/toList.action?update=1", "委托应急课题结项拨款"},
		
		{PROJECTFUND_INSTP_LIST, "projectFund/instp/toList","projectFund/instp/toList.action?update=1", "基地项目拨款概况"},
		{FUNDLIST_INSTP_GRANTED_LIST, "fundList/instp/granted/toList","fundList/instp/granted/toList.action?update=1", "基地项目立项拨款"},
		{FUNDLIST_INSTP_MID_LIST, "fundList/key/instp/toList","fundList/instp/mid/toList.action?update=1", "基地项目中检拨款"},
		{FUNDLIST_INSTP_END_LIST, "fundList/key/instp/toList","fundList/instp/end/toList.action?update=1", "基地项目结项拨款"},
//		
	};
	
	private static final String[][] STATISTIC_LOG_CODE_URL={
		//常规统计10
		//人员统计
		{STATISTIC_COMMON_PERSON_ADD, STATISTIC_PERSON, "statistic/common/add", "添加人员常规统计"},
		{STATISTIC_COMMON_PERSON_MODIFY, STATISTIC_PERSON, "statistic/common/modify","修改人员常规统计"},
		{STATISTIC_COMMON_PERSON_VIEW, STATISTIC_PERSON, "statistic/common/toView","查看人员常规统计"},
		{STATISTIC_COMMON_PERSON_DELETE, STATISTIC_PERSON, "statistic/common/delete","删除人员常规统计"},
		{STATISTIC_COMMON_PERSON_SIMPLESEARCH, STATISTIC_PERSON, "statistic/common/simpleSearch", "初级检索人员常规统计"},
		{STATISTIC_COMMON_PERSON_LIST, STATISTIC_PERSON, "statistic/common/list", "进入人员常规统计列表"},
		//机构统计
		{STATISTIC_COMMON_UNIT_ADD, STATISTIC_UNIT, "statistic/common/add","添加机构常规统计"},
		{STATISTIC_COMMON_UNIT_MODIFY, STATISTIC_UNIT, "statistic/common/modify","修改机构常规统计"},
		{STATISTIC_COMMON_UNIT_VIEW, STATISTIC_UNIT, "statistic/common/toView","查看机构常规统计"},
		{STATISTIC_COMMON_UNIT_DELETE, STATISTIC_UNIT, "statistic/common/delete","删除机构常规统计"},
		{STATISTIC_COMMON_UNIT_SIMPLESEARCH, STATISTIC_UNIT, "statistic/common/simpleSearch","初级检索机构常规统计"},
		{STATISTIC_COMMON_UNIT_LIST, STATISTIC_UNIT, "statistic/common/list","进入机构常规统计列表"},
		//项目统计
		{STATISTIC_COMMON_PROJECT_ADD, STATISTIC_PROJECT, "statistic/common/add", "添加项目常规统计"},
		{STATISTIC_COMMON_PROJECT_MODIFY, STATISTIC_PROJECT, "statistic/common/modify", "修改项目常规统计"},
		{STATISTIC_COMMON_PROJECT_VIEW, STATISTIC_PROJECT, "statistic/common/toView", "查看项目常规统计"},
		{STATISTIC_COMMON_PROJECT_DELETE, STATISTIC_PROJECT, "statistic/common/delete", "删除项目常规统计"},
		{STATISTIC_COMMON_PROJECT_SIMPLESEARCH, STATISTIC_PROJECT, "statistic/common/simpleSearch", "初级检索项目常规统计"},
		{STATISTIC_COMMON_PROJECT_LIST, STATISTIC_PROJECT, "statistic/common/list", "进入项目常规统计列表"},
		//成果统计
		{STATISTIC_COMMON_PRODUCT_ADD, STATISTIC_PRODUCT, "statistic/common/add", "添加成果常规统计"},
		{STATISTIC_COMMON_PRODUCT_MODIFY, STATISTIC_PRODUCT, "statistic/common/modify", "修改成果常规统计"},
		{STATISTIC_COMMON_PRODUCT_VIEW, STATISTIC_PRODUCT, "statistic/common/toView", "查看成果常规统计"},
		{STATISTIC_COMMON_PRODUCT_DELETE, STATISTIC_PRODUCT, "statistic/common/delete", "删除成果常规统计"},
		{STATISTIC_COMMON_PRODUCT_SIMPLESEARCH, STATISTIC_PRODUCT, "statistic/common/simpleSearch", "初级检索成果常规统计"},
		{STATISTIC_COMMON_PRODUCT_LIST, STATISTIC_PRODUCT, "statistic/common/list", "进入成果常规统计列表"},
		//奖励统计
		{STATISTIC_COMMON_AWARD_ADD, STATISTIC_AWARD, "statistic/common/add", "添加奖励常规统计"},
		{STATISTIC_COMMON_AWARD_MODIFY, STATISTIC_AWARD, "statistic/common/modify", "修改奖励常规统计"},
		{STATISTIC_COMMON_AWARD_VIEW, STATISTIC_AWARD, "statistic/common/toView", "查看奖励常规统计"},
		{STATISTIC_COMMON_AWARD_DELETE, STATISTIC_AWARD, "statistic/common/delete", "删除奖励常规统计"},
		{STATISTIC_COMMON_AWARD_SIMPLESEARCH, STATISTIC_AWARD, "statistic/common/simpleSearch", "初级奖励人员常规统计"},
		{STATISTIC_COMMON_AWARD_LIST, STATISTIC_AWARD, "statistic/common/list", "进入人员常规统计列表"},
		
		//定制统计
		{STATISTIC_CUSTOM_PERSON, STATISTIC_PERSON, "statistic/custom/person/toView", "人员定制统计"},
		{STATISTIC_CUSTOM_UNIT, STATISTIC_UNIT, "statistic/custom/unit/toView", "机构定制统计"},
		{STATISTIC_CUSTOM_PROJECT, STATISTIC_PROJECT, "statistic/custom/project/toView", "项目定制统计"},
		{STATISTIC_CUSTOM_PRODUCT, STATISTIC_PRODUCT, "statistic/custom/product/toView", "成果定制统计"},
		{STATISTIC_CUSTOM_AWARD, STATISTIC_AWARD, "statistic/custom/award/toView", "奖励定制统计"},
	};

	/**
	 * 上述对象的map形式，主要为了提高日志记录的匹配查找速度
	 */
	public static final Map<String, String[]> LOG_CODE_URL_MAP;
	public static final Map<String, Map<String, String>> STATISTIC_LOG_CODE_URL_MAP;
	static {
		LOG_CODE_URL_MAP = new HashMap<String, String[]>();
		String[] value;
		for (String[] tmp : LOG_CODE_URL) {// 遍历上述对象，将其封装为map对象
			if (tmp.length == 4) {
				value = new String[3];
				value[0] = tmp[0];//代码
				value[1] = tmp[3];//描述
				value[2] = tmp[2];//列表入口
				LOG_CODE_URL_MAP.put(tmp[1], value);// 以URL为key，其代码及描述为value
			} else {
				value = new String[3];
				value[0] = tmp[0];
				value[1] = tmp[2];//描述
				LOG_CODE_URL_MAP.put(tmp[1], value);// 以URL为key，其代码及描述为value
			}
		}
		
		STATISTIC_LOG_CODE_URL_MAP = new HashMap<String, Map<String,String>>();
		for (String[] tmp : STATISTIC_LOG_CODE_URL) {
			Map<String,String> map =new HashMap<String, String>();
			map.put("eventCode", tmp[0]);
			map.put("description", tmp[3]);
			STATISTIC_LOG_CODE_URL_MAP.put(tmp[2]+tmp[1], map);
		}
	}

}
