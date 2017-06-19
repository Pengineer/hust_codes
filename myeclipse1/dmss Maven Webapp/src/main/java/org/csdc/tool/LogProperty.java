package org.csdc.tool;

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
	public static final String LOGIN = "0101";
	public static final String LOGOUT = "0102";
	// 个人空间
	public static final String SELF = "02";
	public static final String SELF_ACCOUNT_VIEW = "0201";
	public static final String SELF_ACCOUNT_MODIFY = "0202";
	public static final String SELF_ACCOUNT_MODIFYPASSWORD = "0203";
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
	// 通知
	public static final String SYSTEM_NOTICE_INNER = "0302";
	public static final String SYSTEM_NOTICE_INNER_ADD = "030201";
	public static final String SYSTEM_NOTICE_INNER_DELETE = "030202";
	public static final String SYSTEM_NOTICE_INNER_MODIFY = "030203";
	public static final String SYSTEM_NOTICE_INNER_VIEW = "030204";
	public static final String SYSTEM_NOTICE_INNER_SIMPLESEARCH = "030205";
	public static final String SYSTEM_NOTICE_INNER_ADVSEARCH = "030206";
	public static final String SYSTEM_NOTICE_INNER_DOWNLOAD = "030207";
	// 留言
	public static final String SYSTEM_MESSAGE_INNER = "0303";
	public static final String SYSTEM_MESSAGE_INNER_ADD = "030301";
	public static final String SYSTEM_MESSAGE_INNER_DELETE = "030302";
	public static final String SYSTEM_MESSAGE_INNER_MODIFY = "030303";
	public static final String SYSTEM_MESSAGE_INNER_VIEW = "030304";
	public static final String SYSTEM_MESSAGE_INNER_SIMPLESEARCH = "030305";
	public static final String SYSTEM_MESSAGE_INNER_ADVSEARCH = "030306";
	public static final String SYSTEM_MESSAGE_INNER_TOGGLEOPEN = "030307";
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
	// 日志
	public static final String SYSTEM_LOG = "0305";
	public static final String SYSTEM_LOG_DELETE = "030501";
	public static final String SYSTEM_LOG_VIEW = "030502";
	public static final String SYSTEM_LOG_SIMPLESEARCH = "030503";
	public static final String SYSTEM_LOG_ADVSEARCH = "030504";
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
	// 权限
	public static final String SECURITY_RIGHT = "0402";
	public static final String SECURITY_RIGHT_ADD = "040201";
	public static final String SECURITY_RIGHT_DELETE = "040202";
	public static final String SECURITY_RIGHT_MODIFY = "040203";
	public static final String SECURITY_RIGHT_VIEW = "040204";
	public static final String SECURITY_RIGHT_SIMPLESEARCH = "040205";
	public static final String SECURITY_RIGHT_ADVSEARCH = "040206";
	// 账号
	public static final String SECURITY_ACCOUNT = "0403";
	public static final String SECURITY_ACCOUNT_ADD = "040301";
	public static final String SECURITY_ACCOUNT_DELETE = "040302";
	public static final String SECURITY_ACCOUNT_MODIFY = "040303";
	public static final String SECURITY_ACCOUNT_VIEW = "040304";
	public static final String SECURITY_ACCOUNT_SIMPLESEARCH = "040305";
	public static final String SECURITY_ACCOUNT_ADVSEARCH = "040306";
	public static final String SECURITY_ACCOUNT_ENABLE = "040307";
	public static final String SECURITY_ACCOUNT_DISABLE = "040308";
	public static final String SECURITY_ACCOUNT_ASSIGNROLE = "040309";
	public static final String SECURITY_ACCOUNT_RETRIEVECODE = "040310";
	public static final String SECURITY_ACCOUNT_MODIFYPASSWORD = "040311";
	//配件管理
	public static final String MATERIAL = "05";
	//备件库存
	public static final String MATERIAL_INVENTORY = "0501";
	public static final String MATERIAL_INVENTORY_ADD = "050101";
	public static final String MATERIAL_INVENTORY_DELETE = "050102";
	public static final String MATERIAL_INVENTORY_MODIFY = "050103";
	public static final String MATERIAL_INVENTORY_VIEW = "050104";
	public static final String MATERIAL_INVENTORY_SIMPLESEARCH = "050105";
	public static final String MATERIAL_INVENTORY_ADVSEARCH = "050106";
	//配件申请
	public static final String MATERIAL_APPLICATION = "0502";
	public static final String MATERIAL_APPLICATION_ADD = "050201";
	public static final String MATERIAL_APPLICATION_DELETE = "050202";
	public static final String MATERIAL_APPLICATION_MODIFY = "050203";
	public static final String MATERIAL_APPLICATION_SIMPLESEARCH = "050204";
	public static final String MATERIAL_APPLICATION_ADVSEARCH = "050205";
	public static final String MATERIAL_APPLICATION_VIEW = "050206";
	public static final String MATERIAL_APPLICATION_PRINT = "050207";
	public static final String MATERIAL_APPLICATION_SEND = "050208";
	public static final String MATERIAL_APPLICATION_GENERATE = "050209";
	//配件变更
	public static final String MATERIAL_VARIATION = "0503";
	public static final String MATERIAL_VARIATION_ADD = "050301";
	public static final String MATERIAL_VARIATION_DELETE = "050302";
	public static final String MATERIAL_VARIATION_MODIFY = "050303";
	public static final String MATERIAL_VARIATION_VIEW = "050304";
	public static final String MATERIAL_VARIATION_SIMPLESEARCH = "050305";
	public static final String MATERIAL_VARIATION_ADVSEARCH = "050306";
	public static final String MATERIAL_VARIATION_AUDIT = "050307";
	public static final String MATERIAL_VARIATION_SEND = "050308";
	//备件计划
	public static final String MATERIAL_PLAN = "0504";
	public static final String MATERIAL_PLAN_ADD = "050401";
	public static final String MATERIAL_PLAN_DELETE = "050402";
	public static final String MATERIAL_PLAN_MODIFY = "050403";
	public static final String MATERIAL_PLAN_VIEW = "050404";
	public static final String MATERIAL_PLAN_SIMPLESEARCH ="050405";
	public static final String MATERIAL_PLAN_ADVSEARCH = "050406";
	//备件退库
	public static final String MATERIAL_CANCELINVENTORY = "0505";
	public static final String MATERIAL_CANCELINVENTORY_ADD = "050501";
	public static final String MATERIAL_CANCELINVENTORY_DELETE = "050502";
	public static final String MATERIAL_CANCELINVENTORY_MODIFY = "050503";
	public static final String MATERIAL_CANCELINVENTORY_VIEW = "050504";
	public static final String MATERIAL_CANCELINVENTORYN_SIMPLESEARCH = "050505";
	public static final String MATERIAL_CANCELINVENTORY_ADVSEARCH = "050506";
	public static final String MATERIAL_CANCELINVENTORY_SEND = "050507";
	//库存预警
	public static final String MATERIAL_WARNING = "0506";
	public static final String MATERIAL_WARNING_ADD = "050601";
	public static final String MATERIAL_WARNING_DELETE = "050602";
	public static final String MATERIAL_WARNING_MODIFY = "050603";
	public static final String MATERIAL_WARNING_VIEW = "050604";
	public static final String MATERIAL_WARNING_SIMPLESEARCH = "050605";
	public static final String MATERIAL_WARNING_ADVSEARCH = "050606";
	//财务管理
	public static final String FINANCE = "06";
	//发票申请
	public static final String FINANCE_INVOICE_APPLY_= "0601";
	public static final String FINANCE_INVOICE_APPLY_ADD = "060101";
	public static final String FINANCE_INVOICE_APPLY_DELETE = "060102";
	public static final String FINANCE_INVOICE_APPLY_MODIFY = "060103";
	public static final String FINANCE_INVOICE_APPLY_VIEW = "060104";
	//发票管理
	public static final String FINANCE_INVOICE = "0602";
	public static final String FINANCE_INVOICE_ADD = "060201";
	public static final String FINANCE_INVOICE_DELETE = "06022";
	public static final String FINANCE_INVOICE_MODIFY = "06023";
	public static final String FINANCE_INVOICE_VIEW = "06024";
	public static final String FINANCE_INVOICE_AUDIT = "06025";
	//回款管理
	public static final String FINANCE_RETURNMONEY = "0603";
	public static final String FINANCE_RETURNMONEY_ADD = "060301";
	public static final String FINANCE_RETURNMONEY_VIEW = "060302";
	public static final String FINANCE_RETURNMONEY_CONFIRM = "060303";
	//销账管理
	public static final String FINANCE_CANCELACCOUNT = "0604";
	public static final String FINANCE_CANCELACCOUNT_VIEW = "060401";
	public static final String FINANCE_CANCELACCOUNT_CANCEL = "060402";
	//统计查询
	public static final String STATISTIC = "07";
	//常规统计
	public static final String STATISTIC_COMMON_EXPORTEXCEL = "0701";
	public static final String STATISTIC_COMMON_SIMPLESEARCH = "0702";
	//基础数据
	public static final String BASEDATA = "08";
	//办事处管理
	public static final String BASEDATA_AGENCY = "0801";
	public static final String BASEDATA_AGENCY_ADD = "080101";
	public static final String BASEDATA_AGENCY_DELETE = "080102";
	public static final String BASEDATA_AGENCY_MODIFY = "080103";
	public static final String BASEDATA_AGENCY_VIEW = "080104";
	public static final String BASEDATA_AGENCY_SIMPLESEARCH = "080105";
	public static final String BASEDATA_AGENCY_ADVSEARCH = "080106";
	//销售点管理
	public static final String BASEDATA_SALEPOINT = "0802";
	public static final String BASEDATA_SALEPOINT_ADD = "080201";
	public static final String BASEDATA_SALEPOINT_DELETE = "080202";
	public static final String BASEDATA_SALEPOINT_MODIFY = "080203";
	public static final String BASEDATA_SALEPOINT_VIEW = "080204";
	public static final String BASEDATA_SALEPOINT_SIMPLESEARCH = "080205";
	public static final String BASEDATA_SALEPOINT_ADVSEARCH = "080206";
	//人员管理
	public static final String BASEDATA_PERSON = "0803";
	public static final String BASEDATA_PERSON_ADD = "080301";
	public static final String BASEDATA_PERSON_DELETE = "080302";
	public static final String BASEDATA_PERSON_MODIFY = "080303";
	public static final String BASEDATA_PERSON_VIEW = "080304";
	public static final String BASEDATA_PERSON_SIMPLESEARCH = "080305";
	public static final String BASEDATA_PERSON_ADVSEARCH = "080306";
	//客户管理
	public static final String BASEDATA_CUSTOMER = "0804";
	public static final String BASEDATA_CUSTOMER_ADD = "080401";
	public static final String BASEDATA_CUSTOMER_DELETE = "080402";
	public static final String BASEDATA_CUSTOMER_MODIFY = "080403";
	public static final String BASEDATA_CUSTOMER_VIEW = "080404";
	public static final String BASEDATA_CUSTOMER_SIMPLESEARCH = "080405";
	public static final String BASEDATA_CUSTOMER_ADVSEARCH = "080406";
	//配件库管理
	public static final String BASEDATA_ACCESSORY = "0805";
	public static final String BASEDATA_ACCESSORY_ADD = "080501";
	public static final String BASEDATA_ACCESSORY_DELETE = "080502";
	public static final String BASEDATA_ACCESSORY_MODIFY = "080503";
	public static final String BASEDATA_ACCESSORY_VIEW = "080504";
	public static final String BASEDATA_ACCESSORY_SIMPLESEARCH = "080505";
	public static final String BASEDATA_ACCESSORY_ADVSEARCH = "080506";
	//物流管理
	public static final String TRANSPORT = "09";
	//总部发货
	public static final String TRANSPORT_HEADOFFICE = "0901";
	public static final String TRANSPORT_HEADOFFICE_ADD = "090101";
	public static final String TRANSPORT_HEADOFFICE_DELETE = "090102";
	public static final String TRANSPORT_HEADOFFICE_MODIFY = "090103";
	public static final String TRANSPORT_HEADOFFICE_VIEW = "090104";
	public static final String TRANSPORT_HEADOFFICE_SIMPLESEARCH = "090105";
	public static final String TRANSPORT_HEADOFFICE_ADVSEARCH = "090106";
	//办事处发货
	public static final String TRANSPORT_BRANCH = "0902";
	public static final String TRANSPORT_BRANCH_ADD = "090201";
	public static final String TRANSPORT_BRANCH_DELETE = "090202";
	public static final String TRANSPORT_BRANCH_MODIFY = "090203";
	public static final String TRANSPORT_BRANCH_VIEW = "090204";
	public static final String TRANSPORT_BRANCH_SIMPLESEARCH = "090205";
	public static final String TRANSPORT_BRANCH_ADVSEARCH = "090206";
	//收货管理
	public static final String TRANSPORT_RECEIVE = "0903";
	public static final String TRANSPORT_RECEIVE_VIEW = "090301";
	public static final String TRANSPORT_RECEIVE_SIMPLESEARCH = "090302";
	public static final String TRANSPORT_RECEIVE_ADVSEARCH = "090303";
	public static final String TRANSPORT_RECEIVE_CONFIRM = "090304";
	//工作周报
	public static final String REPORT = "10";
	public static final String REPORT_ADD = "1001";
	public static final String REPORT_DELETE = "1002";
	public static final String REPORT_MODIFY = "1003";
	public static final String REPORT_VIEW= "1004";
	public static final String REPORT_SIMPLESEARCH = "1005";
	public static final String REPORT_ADVSEARCH = "1006";
	public static final String REPORT_EXPORT = "1007";
	
	/**
	 * URL及其代码，URL指去除了上下文根"qts"及".action"后的字符串，
	 * 代码仿造树的结构生成，每两位一级。
	 */
	private static final String[][] logCodeUrl = {
		// 登录前暂无00
		// 登录相关01
		{LOGIN, "login/doLogin", "登录系统"},
		{LOGOUT, "login/logout", "退出系统"},// 退出系统实际上要在session销毁时记录
		// 个人相关02
		{SELF_ACCOUNT_VIEW, "selfspace/viewSelfAccount", "查看我的账号信息"}, 
		{SELF_ACCOUNT_MODIFY, "selfspace/modifySelfAccount", "修改我的账号信息"},
		{SELF_ACCOUNT_MODIFYPASSWORD, "selfspace/modifyPassword", "修改我的账号密码"},
		// 系统功能03
		// 新闻0301
		{SYSTEM_NEWS_INNER_ADD, "news/inner/add", "添加新闻"},
		{SYSTEM_NEWS_INNER_DELETE, "news/inner/delete", "删除新闻"},
		{SYSTEM_NEWS_INNER_MODIFY, "news/inner/modify", "修改新闻"},
		{SYSTEM_NEWS_INNER_VIEW, "news/inner/view", "查看新闻"},
		{SYSTEM_NEWS_INNER_SIMPLESEARCH, "news/inner/simpleSearch", "初级检索新闻"},
		{SYSTEM_NEWS_INNER_ADVSEARCH, "news/inner/advSearch", "高级检索新闻"},
		{SYSTEM_NEWS_INNER_DOWNLOAD, "news/inner/download", "下载新闻附件"},
		// 通知0302
		{SYSTEM_NOTICE_INNER_ADD, "notice/inner/add", "添加通知"},
		{SYSTEM_NOTICE_INNER_DELETE, "notice/inner/delete", "删除通知"},
		{SYSTEM_NOTICE_INNER_MODIFY, "notice/inner/modify", "修改通知"},
		{SYSTEM_NOTICE_INNER_VIEW, "notice/inner/view", "查看通知"},
		{SYSTEM_NOTICE_INNER_SIMPLESEARCH, "notice/inner/simpleSearch", "初级检索通知"},
		{SYSTEM_NOTICE_INNER_ADVSEARCH, "notice/inner/advSearch", "高级检索通知"},
		{SYSTEM_NOTICE_INNER_DOWNLOAD, "notice/inner/download", "下载通知附件"},
		// 留言0303
		{SYSTEM_MESSAGE_INNER_ADD, "message/inner/add", "添加留言"},
		{SYSTEM_MESSAGE_INNER_DELETE, "message/inner/delete", "删除留言"},
		{SYSTEM_MESSAGE_INNER_MODIFY, "message/inner/modify", "修改留言"},
		{SYSTEM_MESSAGE_INNER_VIEW, "message/inner/view", "查看留言"},
		{SYSTEM_MESSAGE_INNER_SIMPLESEARCH, "message/inner/simpleSearch", "初级检索留言"},
		{SYSTEM_MESSAGE_INNER_ADVSEARCH, "message/inner/advSearch", "高级检索留言"},
		{SYSTEM_MESSAGE_INNER_TOGGLEOPEN, "message/inner/toggleOpen", "切换留言内外网可见"},
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
		// 系统日志0305
		{SYSTEM_LOG_DELETE, "log/delete", "删除日志"},
		{SYSTEM_LOG_VIEW, "log/view", "查看日志"},
		{SYSTEM_LOG_SIMPLESEARCH, "log/simpleSearch", "初级检索日志"},
		{SYSTEM_LOG_ADVSEARCH, "log/advSearch", "高级检索日志"},
		// 系统统计0306
		// 安全认证04
		// 角色0401
		{SECURITY_ROLE_ADD, "role/add", "添加角色"},
		{SECURITY_ROLE_DELETE, "role/delete", "删除角色"},
		{SECURITY_ROLE_MODIFY, "role/modify", "修改角色"},
		{SECURITY_ROLE_VIEW, "role/view", "查看角色"},
		{SECURITY_ROLE_SIMPLESEARCH, "role/simpleSearch", "初级检索角色"},
		{SECURITY_ROLE_ADVSEARCH, "role/advSearch", "高级检索角色"},
		// 权限0402
		{SECURITY_RIGHT_ADD, "right/add", "添加权限"},
		{SECURITY_RIGHT_DELETE, "right/delete", "删除权限"},
		{SECURITY_RIGHT_MODIFY, "right/modify", "修改权限"},
		{SECURITY_RIGHT_VIEW, "right/view", "查看权限"},
		{SECURITY_RIGHT_SIMPLESEARCH, "right/simpleSearch", "初级检索权限"},
		{SECURITY_RIGHT_ADVSEARCH, "right/advSearch", "高级检索权限"},
		// 账号0403
		{SECURITY_ACCOUNT_ADD, "account/add", "添加账号"},
		{SECURITY_ACCOUNT_DELETE, "account/delete", "删除账号"},
		{SECURITY_ACCOUNT_MODIFY, "account/modify", "修改账号"},
		{SECURITY_ACCOUNT_VIEW, "account/view", "查看账号"},
		{SECURITY_ACCOUNT_SIMPLESEARCH, "account/simpleSearch", "账号初级检索"},
		{SECURITY_ACCOUNT_ADVSEARCH, "account/advSearch", "账号高级检索"},
		{SECURITY_ACCOUNT_ENABLE, "account/enable", "启用账号"},
		{SECURITY_ACCOUNT_DISABLE, "account/disable", "停用账号"},
		{SECURITY_ACCOUNT_ASSIGNROLE, "account/assignRole", "分配账号角色"},
		{SECURITY_ACCOUNT_RETRIEVECODE, "account/retrieveCode", "重置账号密码"},
		{SECURITY_ACCOUNT_MODIFYPASSWORD, "account/modifyPassword", "修改账号密码"},
		//配件管理05
		//配件库存0501
		{MATERIAL_INVENTORY_ADD, "material/inventory/add", "添加配件库存"},
		{MATERIAL_INVENTORY_DELETE, "material/inventory/delete", "删除配件库存"},
		{MATERIAL_INVENTORY_MODIFY, "material/inventory/modify", "修改配件库存"},
		{MATERIAL_INVENTORY_VIEW, "material/inventory/view", "查看配件库存"},
		{MATERIAL_INVENTORY_SIMPLESEARCH,"material/inventory/simpleSearch", "配件库存初级检索"},
		{MATERIAL_INVENTORY_ADVSEARCH,"material/inventory/advSearch", "配件库存高级检索"},
		//配件申请0502
		{MATERIAL_APPLICATION_ADD, "material/application/add", "添加配件申请"},
		{MATERIAL_APPLICATION_DELETE, "material/application/delete", "删除配件申请"},
		{MATERIAL_APPLICATION_MODIFY, "material/application/modify", "修改配件申请"},
		{MATERIAL_APPLICATION_VIEW, "material/application/view", "查看配件申请"},
		{MATERIAL_APPLICATION_SIMPLESEARCH, "material/application/simpleSearch", "配件申请初级检索"},
		{MATERIAL_APPLICATION_ADVSEARCH, "material/application/advSearch", "配件申请高级检索"},
		{MATERIAL_APPLICATION_PRINT, "material/application/print", "打印出门证"},
		{MATERIAL_APPLICATION_SEND, "material/application/send", "配件发货"},
		{MATERIAL_APPLICATION_GENERATE, "material/application/generate", "生成出门证"},
		//配件变更0503
		{MATERIAL_VARIATION_ADD, "material/variation/add", "添加配件变更"},
		{MATERIAL_VARIATION_DELETE, "material/variation/delete", "删除配件变更"},
		{MATERIAL_VARIATION_MODIFY, "material/variation/modify", "修改配件变更"},
		{MATERIAL_VARIATION_VIEW, "material/variation/view", "查看配件变更"},
		{MATERIAL_VARIATION_SIMPLESEARCH, "material/variation/simplySearch", "配件变更初级检索"},
		{MATERIAL_VARIATION_ADVSEARCH, "material/variation/advSearch", "配件变更高级检索"},
		{MATERIAL_VARIATION_AUDIT, "material/variation/audit", "审核配件变更"},
		{MATERIAL_VARIATION_SEND, "material/variation/send", "发送变更配件"},
		//备件计划0504
		{MATERIAL_PLAN_ADD, "material/plan/add", "添加配件变更"},
		{MATERIAL_PLAN_DELETE, "material/plan/delete", "删除配件变更"},
		{MATERIAL_PLAN_MODIFY, "material/plan/modify", "修改配件变更"},
		{MATERIAL_PLAN_VIEW, "material/plan/view", "查看配件变更"},
		{MATERIAL_PLAN_SIMPLESEARCH, "material/plan/simpleSearch", "配件变更初级检索"},
		{MATERIAL_PLAN_ADVSEARCH, "material/plan/advSearch", "配件变更高级检索"},
		//备件退库0505
		{MATERIAL_CANCELINVENTORY_ADD, "material/cancelInventory/add", "添加备件退库"},
		{MATERIAL_CANCELINVENTORY_DELETE, "material/cancelInventory/delete", "删除备件退库"},
		{MATERIAL_CANCELINVENTORY_MODIFY, "material/cancelInventory/modify", "修改备件退库"},
		{MATERIAL_CANCELINVENTORY_VIEW, "material/cancelInventory/view", "查看备件退库"},
		{MATERIAL_CANCELINVENTORYN_SIMPLESEARCH, "material/cancelInventory/simpleSearch", "备件退库初级检索"},
		{MATERIAL_CANCELINVENTORY_ADVSEARCH, "material/cancelInventory/advSearch", "备件退库高级检索"},
		{MATERIAL_CANCELINVENTORY_SEND, "material/cancelInventory/send", "备件退库发货"},
		//库存预警0506
		{MATERIAL_WARNING_ADD, "material/warning/add", "添加库存预警"},
		{MATERIAL_WARNING_DELETE, "material/warning/delete", "删除库存预警"},
		{MATERIAL_WARNING_MODIFY, "material/warning/modify", "修改库存预警"},
		{MATERIAL_WARNING_VIEW, "material/warning/view", "查看库存预警"},
		{MATERIAL_WARNING_SIMPLESEARCH, "material/warning/simpleSearch", "库存预警初级检索"},
		{MATERIAL_WARNING_ADVSEARCH, "material/warning/advSearch", "库存预警高级检索"},
		//财务管理06
		//发票申请0601
		{FINANCE_INVOICE_APPLY_ADD, "finance/invoiceApply/add", "添加发票申请"},
		{FINANCE_INVOICE_APPLY_DELETE, "finance/invoiceApply/delete", "删除发票申请"},
		{FINANCE_INVOICE_APPLY_MODIFY, "finance/invoiceApply/modify", "修改发票申请"},
		{FINANCE_INVOICE_APPLY_VIEW, "finance/invoiceApply/view", "查看发票申请"},
		//发票管理0602
		{FINANCE_INVOICE_ADD, "finance/invoice/add", "添加发票"},
		{FINANCE_INVOICE_DELETE, "finance/invoice/delete", "删除发票"},
		{FINANCE_INVOICE_MODIFY, "finance/invoice/modify", "添加发票"},
		{FINANCE_INVOICE_VIEW, "finance/invoice/view", "查看发票"},
		{FINANCE_INVOICE_AUDIT, "finance/invoice/audit", "审核发票"},
		//回款管理0603
		{FINANCE_RETURNMONEY_ADD, "finance/returnMoney/add", "添加回款"},
		{FINANCE_RETURNMONEY_VIEW, "finance/returnMoney/view", "查看回款"},
		{FINANCE_RETURNMONEY_CONFIRM, "finance/returnMoney/confirm", "确认回款"},
		//销账管理0604
		{FINANCE_CANCELACCOUNT_VIEW, "finance/cancelAccount/view", "销账查看"},
		{FINANCE_CANCELACCOUNT_CANCEL, "finance/cancelAccount/cancel", "销账管理销账"},
		//统计查询0701
		{STATISTIC_COMMON_EXPORTEXCEL, "statistic/common/exportExcel","导出统计数据"},
		{STATISTIC_COMMON_SIMPLESEARCH, "statistic/common/simpleSearch","初级检索统计数据"},
		//基础数据08
		//办事处管理0801
		{BASEDATA_AGENCY_ADD, "agency/add","添加办事处"},
		{BASEDATA_AGENCY_DELETE, "agency/delete","删除办事处"},
		{BASEDATA_AGENCY_MODIFY, "agency/modify","修改办事处"},
		{BASEDATA_AGENCY_VIEW, "agency/view","查看办事处"},
		{BASEDATA_AGENCY_SIMPLESEARCH, "agency/simpleSearch","初级检索办事处"},
		{BASEDATA_AGENCY_ADVSEARCH, "agency/advSearch","高级检索办事处"},
        //销售点管理0802
		{BASEDATA_SALEPOINT_ADD, "salePoint/add","添加销售点"},
		{BASEDATA_SALEPOINT_DELETE, "salePoint/delete","删除销售点"},
		{BASEDATA_SALEPOINT_MODIFY, "salePoint/modify","修改销售点"},
		{BASEDATA_SALEPOINT_VIEW, "salePoint/view","查看销售点"},
		{BASEDATA_SALEPOINT_SIMPLESEARCH, "salePoint/simpleSearch","初级检索销售点"},
		{BASEDATA_SALEPOINT_ADVSEARCH, "salePoint/advSearch","高级检索销售点"},
		//人员管理0803
		{BASEDATA_PERSON_ADD, "person/add","添加人员"},
		{BASEDATA_PERSON_DELETE, "person/delete","修改人员"},
		{BASEDATA_PERSON_MODIFY, "person/modify","修改人员"},
		{BASEDATA_PERSON_VIEW, "person/view","查看人员"},
		{BASEDATA_PERSON_SIMPLESEARCH, "person/simpleSearch","初级检索人员"},
		{BASEDATA_PERSON_ADVSEARCH, "person/advSearch","高级检索人员"},
	    //客户管理0804
		{BASEDATA_CUSTOMER_ADD, "customer/add","添加客户"},
		{BASEDATA_CUSTOMER_DELETE, "customer/delete","修改客户"},
		{BASEDATA_CUSTOMER_MODIFY, "customer/modify","修改客户"},
		{BASEDATA_CUSTOMER_VIEW, "customer/view","查看客户"},
		{BASEDATA_CUSTOMER_SIMPLESEARCH, "customer/simpleSearch","初级检索客户"},
		{BASEDATA_CUSTOMER_ADVSEARCH, "customer/advSearch","高级检索客户"},
		//配件库管理0805
		{BASEDATA_ACCESSORY_ADD,  "accessory/add","添加配件"},
		{BASEDATA_ACCESSORY_DELETE, "accessory/delete","修改配件"},
		{BASEDATA_ACCESSORY_MODIFY, "accessory/modify","修改配件"},
		{BASEDATA_ACCESSORY_VIEW, "accessory/view","查看配件"},
		{BASEDATA_ACCESSORY_SIMPLESEARCH, "person/simpleSearch","初级检索人员"},
		{BASEDATA_ACCESSORY_ADVSEARCH, "person/advSearch","高级检索人员"},
		//物流管理09
		//总部发货0901
		{TRANSPORT_HEADOFFICE_ADD, "transport/headOffice/add","添加总部发货"},
		{TRANSPORT_HEADOFFICE_VIEW, "transport/headOffice/view","查看总部发货"},
		{TRANSPORT_HEADOFFICE_SIMPLESEARCH, "transport/headOffice/simpleSearch","初级检索总部发货"},
		{TRANSPORT_HEADOFFICE_ADVSEARCH, "transport/headOffice/advSearch","高级检索总部发货"},
		//办事处发货0902
		{TRANSPORT_BRANCH_ADD, "transport/branch/add","添加办事处发货"},
		{TRANSPORT_BRANCH_VIEW, "transport/branch/view","查看办事处发货"},
		{TRANSPORT_BRANCH_SIMPLESEARCH, "transport/branch/simpleSearch","初级检索办事处发货"},
		{TRANSPORT_BRANCH_ADVSEARCH, "transport/branch/advSearch","高级检索办事处发货"},
		//收货管理0903
		{TRANSPORT_RECEIVE_VIEW, "transport/receive/view","查看收获列表"},
		{TRANSPORT_RECEIVE_CONFIRM, "transport/receive/confirm","确认收货列表"},
		{TRANSPORT_RECEIVE_SIMPLESEARCH, "transport/receive/simpleSearch","初级检索收获列表"},
		{TRANSPORT_RECEIVE_ADVSEARCH, "transport/receive/advSearch","高级检索收获列表"},
		//工作周报10
		{REPORT_ADD, "report/add","添加工作周报"},
		{REPORT_DELETE, "report/delete","删除工作周报"},
		{REPORT_MODIFY, "report/modify","修改工作周报"},
		{REPORT_VIEW, "report/view","查看工作周报"},
		{REPORT_SIMPLESEARCH, "report/simpleSearch","初级检索工作周报"},
		{REPORT_ADVSEARCH, "report/advSearch","高级检索工作周报"},
		{REPORT_EXPORT, "report/export","导出工作周报"},
	};
	private static final String[][] statisticLogCodeUrl={
		//常规统计
		//人员统计
	};

	/**
	 * 上述对象的map形式，主要为了提高日志记录的匹配查找速度
	 */
	public static final Map<String, String[]> logCodeUrlMap;
	public static final Map<String, Map<String, String>> statisticLogCodeUrlMap;
	
	static {
		logCodeUrlMap = new HashMap<String, String[]>();
		String[] value;
		for (String[] tmp : logCodeUrl) {// 遍历上述对象，将其封装为map对象
			value = new String[2];
			value[0] = tmp[0];
			value[1] = tmp[2];
			logCodeUrlMap.put(tmp[1], value);// 以URL为key，其代码及描述为value
		}
		
		statisticLogCodeUrlMap = new HashMap<String, Map<String,String>>();
		for (String[] tmp : statisticLogCodeUrl) {
			Map<String,String> map =new HashMap<String, String>();
			map.put("eventCode", tmp[0]);
			map.put("description", tmp[3]);
			statisticLogCodeUrlMap.put(tmp[2]+tmp[1], map);
		}
	}
}