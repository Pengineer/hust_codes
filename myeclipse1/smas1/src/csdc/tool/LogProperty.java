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
	public static final String LOGOUT = "0102";	
	// 专家管理 02
	public static final String EXPERT_ADD = "0201";
	public static final String EXPERT_DELETE = "0202";
	public static final String EXPERT_MODIFY = "0203";
	public static final String EXPERT_VIEW = "0204";
	public static final String EXPERT_SIMPLESEARCH = "0205";
	public static final String EXPERT_DISABLEREVIEW = "0206";
	public static final String EXPERT_ENABLEREVIEW = "0207";
	public static final String EXPERT_TOGGLEKEY = "0208";
	public static final String EXPERT_LIST = "0209";
	// 项目管理 03
	// 一般项目管理  0301
	// 一般项目申请管理  030101
	// 一般项目参评\退评列表管理 03010101
	public static final String PROJECT_GENERAL_ADD = "0301010101";
	public static final String PROJECT_GENERAL_DELETE = "0301010102";
	public static final String PROJECT_GENERAL_MODIFY = "0301010103";
	public static final String PROJECT_GENERAL_VIEW = "0301010104";
	public static final String PROJECT_GENERAL_SIMPLESEARCH = "0301010105";
	public static final String PROJECT_GENERAL_DISABLEREVIEW = "0301010106";
	public static final String PROJECT_GENERAL_ENABLEREVIEW = "0301010107";
	public static final String PROJECT_GENERAL_GRANTING = "0301010108";
	public static final String PROJECT_GENERAL_LIST = "0301010109";
	// 初审结果（包括专项初审） 03010102
	public static final String PROJECT_GENERAL_FIRSTAUDIT_LIST = "0301010201";
	public static final String PROJECT_GENERAL_FIRSTAUDIT_UPDATEFIRSTAUDIT = "0301010202";
	// 公示项目 03010103
	public static final String PROJECT_GENERAL_PUBLICITY_LIST = "0301010301";
	public static final String PROJECT_GENERAL_PUBLICITY_CONFIRMGRANT = "0301010302";
	// 专家匹配相关 03010104
	public static final String PROJECT_GENERAL_MATCHEXPERT = "0301010401";
	public static final String PROJECT_GENERAL_REMATCHEXPERT = "0301010402";
	public static final String PROJECT_GENERAL_DELETEMATCHES = "0301010403";
	public static final String PROJECT_GENERAL_BATCHREPLACEEXPERT = "0301010404";
	public static final String PROJECT_GENERAL_TRANSFERMATCH = "0301010405";
	public static final String PROJECT_GENERAL_DELETEEXPERTPROJECTS = "0301010406";	
	// 一般项目立项管理  030102
	public static final String PROJECT_GENERAL_GRANTED_LIST = "03010201";	
	// 一般项目中检管理  030103
	// 一般项目变更管理  030104
	// 一般项目结项管理  030105
	// 基地项目管理  0302
	// 基地项目申请管理  030201
	// 基地项目参评\退评列表管理 03020101
	public static final String PROJECT_INSTP_ADD = "0302010101";
	public static final String PROJECT_INSTP_DELETE = "0302010102";
	public static final String PROJECT_INSTP_MODIFY = "0302010103";
	public static final String PROJECT_INSTP_VIEW = "0302010104";
	public static final String PROJECT_INSTP_SIMPLESEARCH = "0302010105";
	public static final String PROJECT_INSTP_DISABLEREVIEW = "0302010106";
	public static final String PROJECT_INSTP_ENABLEREVIEW = "0302010107";
	public static final String PROJECT_INSTP_LIST = "0302010108";
	// 初审结果  03020102
	public static final String PROJECT_INSTP_FIRSTAUDIT_LIST = "0302010201";
	public static final String PROJECT_INSTP_FIRSTAUDIT_UPDATEFIRSTAUDIT = "0302010202";
	// 专家匹配相关 03020103 
	public static final String PROJECT_INSTP_MATCHEXPERT = "0302010301";
	public static final String PROJECT_INSTP_REMATCHEXPERT = "0302010302";
	public static final String PROJECT_INSTP_DELETEMATCHES = "0302010303";
	public static final String PROJECT_INSTP_BATCHREPLACEEXPERT = "0302010304";
	public static final String PROJECT_INSTP_TRANSFERMATCH = "0302010305";
	public static final String PROJECT_INSTP_DELETEEXPERTPROJECTS = "0302010306";	
	// 基地项目立项管理  030202
	// 基地项目中检管理  030203
	// 基地项目变更管理  030204
	// 基地项目结项管理  030205
	// 专项任务项目  0303
	public static final String PROJECT_GENERAL_SPECIAL_ADD = "030301";
	public static final String PROJECT_GENERAL_SPECIAL_DELETE = "030302";
	public static final String PROJECT_GENERAL_SPECIAL_MODIFY = "030303";
	public static final String PROJECT_GENERAL_SPECIAL_VIEW = "030304";
	public static final String PROJECT_GENERAL_SPECIAL_SIMPLESEARCH = "030305";
	public static final String PROJECT_GENERAL_SPECIAL_LIST = "030306";
	// 统计分析 04
	// 一般项目或基地项目统计分析 0401
	public static final String STATISTIC_REVIEW_UNIVERSITY_LIST = "040101";
	public static final String STATISTIC_REVIEW_EXPERT_LIST = "040102";
	public static final String STATISTIC_REVIEW_DISCIPLINEMATRIX = "040103";
	public static final String STATISTIC_REVIEW_WARNSTATISTIC = "040104";
	public static final String STATISTIC_REVIEW_UNIVERSITYUSESTATISTIC = "040105";
	//专家跨类信息统计
	public static final String STATISTIC_REVIEW_EXPERTREVIEWER = "040106";
	public static final String STATISTIC_REVIEW_UNIVERSITYREVIEWER = "040107";
	// 业务设置 05
	// 一般项目业务设置 0501
	public static final String MAIN_VIEWCONFIGGENERAL = "050101";
	public static final String MAIN_CONFIGGENERAL = "050102";
	// 基地项目业务设置 0502
	public static final String MAIN_VIEWCONFIGINSTP = "050201";
	public static final String MAIN_CONFIGINSTP = "050202";
	// 个人空间  06
	public static final String USER_VIEW = "0601";
	public static final String USER_MODIFYUSER = "0602";
	public static final String SELFSPACE_MODIFYPASSWORD = "0603";
	public static final String USER_RETRIEVECODE = "0604";
	// 系统管理 07
	// 用户管理 0701
	public static final String USER_LIST = "070101";
	public static final String USER_SIMPLESEARCH = "070102";
	public static final String USER_DELETEUSER = "070103";
	public static final String USER_GROUPDELETEUSER = "070104";
	public static final String USER_GROUPDISABLEACCOUNT = "070105";
	public static final String USER_GROUPOPERATION = "070106";
	// 角色权限管理 0702
	// 角色管理 070201
	public static final String ROLE_LIST = "07020101";
	public static final String ROLE_ADD = "07020102";
	public static final String ROLE_DELETE = "07020103";
	public static final String ROLE_MODIFY = "07020104";
	public static final String ROLE_VIEW = "07020105";
	public static final String ROLE_SIMPLESEARCH = "07020106";
	// 权限管理 070202
	public static final String RIGHT_LIST = "07020201";
	public static final String RIGHT_ADD = "07020202";
	public static final String RIGHT_DELETE = "07020203";
	public static final String RIGHT_MODIFY = "07020204";
	public static final String RIGHT_VIEW = "07020205";
	public static final String RIGHT_SIMPLESEARCH = "07020206";
	// 邮件管理 0703
	public static final String MAIL_LIST = "070301";
	public static final String MAIL_ADD = "070302";
	public static final String MAIL_DELETE = "070303";
	public static final String MAIL_VIEW = "070304";
	public static final String MAIL_SIMPLESEARCH = "070305";
	public static final String MAIL_SENDALL = "070306";
	public static final String MAIL_CANCELALL = "070307";
	public static final String MAIL_SENDAGAIN = "070308";
	public static final String MAIL_SEND = "070309";
	public static final String MAIL_CANCEL = "070310";
	// 系统设置 0704
	public static final String MAIN_CONFIGPAGESIZE = "070401";
	public static final String MAIN_CONFIGYEAR = "070402";
	public static final String MAIN_CONFIGMAIL = "070403";
	public static final String MAIN_CONFIGSYSTEMSTATUS = "070404";
	
	/**
	 * URL及其代码，URL指去除了上下文根"smas"及".action"后的字符串，
	 * 代码仿造树的结构生成，每两位一级。
	 */	
	private static final String[][] LOG_CODE_URL = {
		// 登录前暂无00
		// 登录相关0101
		{LOGIN, "user/login", "登录系统"},

		// 登出 0102
		{LOGOUT, "login/logout", "退出系统"},// 退出系统实际上要在session销毁时记录
		// 专家管理 02
		{EXPERT_ADD, "expert/add", "添加专家"},
		{EXPERT_DELETE, "expert/delete", "删除专家"},
		{EXPERT_MODIFY, "expert/modify", "修改专家"},
		{EXPERT_VIEW, "expert/view", "查看专家"},
		{EXPERT_SIMPLESEARCH, "expert/simpleSearch", "专家初级检索"},
		{EXPERT_DISABLEREVIEW, "expert/disableReview", "设为退评"},
		{EXPERT_ENABLEREVIEW, "expert/enableReview", "设为参评"},
		{EXPERT_TOGGLEKEY, "expert/toggleKey", "切换是否重点人"},
		{EXPERT_LIST, "expert/list", "查看专家列表"},		
		// 项目管理 03
		// 一般项目管理  0301
		// 一般项目申请管理  030101
		// 一般项目参评\退评列表管理 03010101	
		{PROJECT_GENERAL_ADD, "project/general/add", "添加一般项目"},
		{PROJECT_GENERAL_DELETE, "project/general/delete", "删除一般项目"},
		{PROJECT_GENERAL_MODIFY, "project/general/modify", "修改一般项目"},
		{PROJECT_GENERAL_VIEW, "project/general/view", "查看一般项目详情"},
		{PROJECT_GENERAL_SIMPLESEARCH, "project/general/simpleSearch", "一般项目列表初级检索"},
		{PROJECT_GENERAL_DISABLEREVIEW, "project/general/disableReview", "一般项目退出评审"},
		{PROJECT_GENERAL_ENABLEREVIEW, "project/general/enableReview", "一般项目参与评审"},
		{PROJECT_GENERAL_GRANTING, "project/general/granting", "一般项目设置立项状态"},
		{PROJECT_GENERAL_LIST, "project/general/list", "查看一般项目列表"},	
		// 初审结果（包括专项初审） 03010102
		{PROJECT_GENERAL_FIRSTAUDIT_LIST, "project/general/firstAudit/list", "查看一般项目项目初审列表"},
		{PROJECT_GENERAL_FIRSTAUDIT_UPDATEFIRSTAUDIT, "project/general/firstAudit/updateFirstAudit", "一般项目更新查重情况"},	
		// 公示项目 03010103
		{PROJECT_GENERAL_PUBLICITY_LIST, "project/general/publicity/list", "查看一般项目公示列表"},
		{PROJECT_GENERAL_PUBLICITY_CONFIRMGRANT, "project/general/publicity/confirmGrant", "一般项目确定立项"},
		// 专家匹配相关 03010104
		{PROJECT_GENERAL_MATCHEXPERT, "project/general/matchExpert", "一般项目自动匹配"},
		{PROJECT_GENERAL_REMATCHEXPERT, "project/general/reMatchExpert", "一般项目手动匹配"},
		{PROJECT_GENERAL_DELETEMATCHES, "project/general/deleteMatches", "删除一般项目已有匹配对所有信息"},
		{PROJECT_GENERAL_BATCHREPLACEEXPERT, "project/general/batchReplaceExpert", "一般项目批量替换专家"},
		{PROJECT_GENERAL_TRANSFERMATCH, "project/general/transferMatch", "一般项目指定由其他专家无条件接管"},
		{PROJECT_GENERAL_DELETEEXPERTPROJECTS, "project/general/deleteExpertProjects", "一般项目删除专家项目"},		
		// 一般项目立项管理  030102	
		{PROJECT_GENERAL_GRANTED_LIST, "project/general/granted/list", "查看一般项目立项项目列表"},
		// 一般项目中检管理  030103
		// 一般项目变更管理  030104
		// 一般项目结项管理  030105
		// 基地项目管理  0302
		// 基地项目申请管理  030201
		// 基地项目参评\退评列表管理 03020101
		{PROJECT_INSTP_ADD, "project/instp/add", "添加基地项目"},
		{PROJECT_INSTP_DELETE, "project/instp/delete", "删除基地项目"},
		{PROJECT_INSTP_MODIFY, "project/instp/modify", "修改基地项目"},
		{PROJECT_INSTP_VIEW, "project/instp/view", "查看基地项目详情"},
		{PROJECT_INSTP_SIMPLESEARCH, "project/instp/simpleSearch", "基地项目列表初级检索"},
		{PROJECT_INSTP_DISABLEREVIEW, "project/instp/disableReview", "基地项目退出评审"},
		{PROJECT_INSTP_ENABLEREVIEW, "project/instp/enableReview", "基地项目参与评审"},
		{PROJECT_INSTP_LIST, "project/instp/list", "查看基地项目列表"},
		// 初审结果  03020102
		{PROJECT_INSTP_FIRSTAUDIT_LIST, "project/instp/firstAudit/list", "查看基地项目项目初审列表"},
		{PROJECT_INSTP_FIRSTAUDIT_UPDATEFIRSTAUDIT, "project/instp/firstAudit/updateFirstAudit", "基地项目更新查重情况"},		
		// 专家匹配相关 03020103
		{PROJECT_INSTP_MATCHEXPERT, "project/instp/matchExpert", "基地项目自动匹配"},
		{PROJECT_INSTP_REMATCHEXPERT, "project/instp/reMatchExpert", "基地项目手动匹配"},
		{PROJECT_INSTP_DELETEMATCHES, "project/instp/deleteMatches", "删除基地项目已有匹配对所有信息"},
		{PROJECT_INSTP_BATCHREPLACEEXPERT, "project/instp/batchReplaceExpert", "基地项目批量替换专家"},
		{PROJECT_INSTP_TRANSFERMATCH, "project/instp/transferMatch", "基地项目指定由其他专家无条件接管"},
		{PROJECT_INSTP_DELETEEXPERTPROJECTS, "project/instp/deleteExpertProjects", "基地项目删除专家项目"},
		// 基地项目立项管理  030202
		// 基地项目中检管理  030203
		// 基地项目变更管理  030204
		// 基地项目结项管理  030205
		// 专项任务项目  0303	
		{PROJECT_GENERAL_SPECIAL_ADD, "project/general/special/add", "添加专项任务项目"},
		{PROJECT_GENERAL_SPECIAL_DELETE, "project/general/special/delete", "删除专项任务项目"},
		{PROJECT_GENERAL_SPECIAL_MODIFY, "project/general/special/modify", "修改专项任务项目"},
		{PROJECT_GENERAL_SPECIAL_VIEW, "project/general/special/view", "查看专项任务项目"},
		{PROJECT_GENERAL_SPECIAL_SIMPLESEARCH, "project/general/special/simpleSearch", "专项任务项目列表初级检索"},
		{PROJECT_GENERAL_SPECIAL_LIST, "project/general/special/list", "查看专项任务项目列表"},	
		// 统计分析 04
		// 一般项目或基地项目统计分析 0401	
		{STATISTIC_REVIEW_UNIVERSITY_LIST, "statistic/review/university/list", "查看高校参评专家数列表"},
		{STATISTIC_REVIEW_EXPERT_LIST, "statistic/review/expert/list", "查看专家参评项目数列表"},
		{STATISTIC_REVIEW_DISCIPLINEMATRIX, "statistic/review/disciplineMatrix", "查看学科退避矩阵"},
		{STATISTIC_REVIEW_WARNSTATISTIC, "statistic/review/warnStatistic", "查看匹配警告信息"},
		{STATISTIC_REVIEW_UNIVERSITYUSESTATISTIC, "statistic/review/universityUseStatistic", "查看高校使用情况统计"},
		{STATISTIC_REVIEW_EXPERTREVIEWER, "statistic/review/expertReviewer/list", "专家跨类信息专家评审统计"},
		{STATISTIC_REVIEW_UNIVERSITYREVIEWER, "statistic/review/univReviewer/list", "专家跨类信息高校评审专家统计"},
		// 业务设置 05
		// 一般项目业务设置 0501	
		{MAIN_VIEWCONFIGGENERAL, "main/viewConfigGeneral", "查看一般项目业务设置"},
		{MAIN_CONFIGGENERAL, "main/configGeneral", "修改一般项目业务设置"},
		// 基地项目业务设置 0502
		{MAIN_VIEWCONFIGINSTP, "main/viewConfigInstp", "查看基地项目业务设置"},
		{MAIN_CONFIGINSTP, "main/configInstp", "修改基地项目业务设置"},		
		// 个人空间 06
		{USER_VIEW, "user/view", "查看个人信息"},
		{USER_MODIFYUSER, "user/modifyUser", "修改个人信息"},
		{SELFSPACE_MODIFYPASSWORD, "selfspace/modifyPassword", "修改密码"},
		{USER_RETRIEVECODE, "user/retrieveCode", "重置密码"},	
		// 系统管理 07
		// 用户管理 0701
		{USER_LIST, "user/list", "查看用户列表"},
		{USER_SIMPLESEARCH, "user/simpleSearch", "用户列表初级检索"},
		{USER_DELETEUSER, "user/deleteUser", "删除用户"},
		{USER_GROUPDISABLEACCOUNT, "user/groupDisableAccount", "批量禁用用户"},
		{USER_GROUPOPERATION, "user/groupOperation", "批量审批或启用用户"},	
		// 角色权限管理 0702
		// 角色管理 070201
		{ROLE_LIST, "role/list", "查看角色列表"},
		{ROLE_ADD, "role/add", "添加角色"},
		{ROLE_DELETE, "role/delete", "删除角色"},
		{ROLE_MODIFY, "role/modify", "修改角色"},
		{ROLE_VIEW, "role/view", "查看角色"},
		{ROLE_SIMPLESEARCH, "role/simpleSearch", "角色列表初级检索"},		
		// 权限管理 070202
		{RIGHT_LIST, "right/list", "查看权限列表"},
		{RIGHT_ADD, "right/add", "添加权限"},
		{RIGHT_DELETE, "right/delete", "删除权限"},
		{RIGHT_MODIFY, "right/modify", "修改权限"},
		{RIGHT_VIEW, "right/view", "查看权限"},
		{RIGHT_SIMPLESEARCH, "right/simpleSearch", "权限列表初级检索"},
		// 邮件管理 0703
		{MAIL_LIST, "mail/list", "查看邮件列表"},
		{MAIL_ADD, "mail/add", "添加邮件"},
		{MAIL_DELETE, "mail/delete", "删除邮件"},
		{MAIL_VIEW, "mail/view", "查看邮件"},
		{MAIL_SIMPLESEARCH, "mail/simpleSearch", "邮件列表初级检索"},
		{MAIL_SENDALL, "mail/sendAll", "发送所有未完成邮件"},
		{MAIL_CANCELALL, "mail/cancelAll", "暂停发送所有邮件"},
		{MAIL_SENDAGAIN, "mail/sendAgain", "重新发送邮件(只能对发送成功的邮件进行操作)"},
		{MAIL_SEND, "mail/send", "发送邮件"},
		{MAIL_CANCEL, "mail/cancel", "暂停发送邮件"},		
		//系统设置 0704
		{MAIN_CONFIGPAGESIZE, "main/configPageSize", "配置每页显示记录数"},
		{MAIN_CONFIGYEAR, "main/configYear", "配置年份"},
		{MAIN_CONFIGMAIL, "main/configMail", "配置邮件"},
		{MAIN_CONFIGSYSTEMSTATUS, "main/configSystemStatus", "配置系统状态"}	
	
	};
	
	/**
	 * 上述对象的map形式，主要为了提高日志记录的匹配查找速度
	 */
	public static final Map<String, String[]> LOG_CODE_URL_MAP;
	static {
		LOG_CODE_URL_MAP = new HashMap<String, String[]>();
		String[] value;
		for (String[] tmp : LOG_CODE_URL) {// 遍历上述对象，将其封装为map对象
				value = new String[2];
				value[0] = tmp[0];
				value[1] = tmp[2];//描述
				LOG_CODE_URL_MAP.put(tmp[1], value);// 以URL为key，其代码及描述为value
			}
		}
}
