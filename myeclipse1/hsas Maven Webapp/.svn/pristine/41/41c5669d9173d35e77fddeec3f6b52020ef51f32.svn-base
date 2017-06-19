package csdc.action.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import csdc.action.BaseAction;

import csdc.model.Account;
import csdc.model.Mail;
import csdc.service.IAccountService;
import csdc.tool.InputValidate;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.AccountInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.RightInfo;
import csdc.tool.mail.MailController;


@Component
@Scope(value="prototype")

public class AccountAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1883015192752770930L;
	private static final String TMP_ACCOUNT_ID = "accountId";// 缓存与session中，备用的账号ID变量名称
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";// 列表时间格式
	private final static String PAGE_NAME = "accountListPage";
	private static final String PAGE_BUFFER_ID = "a.id";// 上下条查看时用于查找缓存的字段名称
	private static final String HQL = "select a.id,a.username ,a.startDate ,a.expireDate,a.status,a.type from Account a";// 上下条查看时用于查找缓存的字段名称HQL
	private final static String[] SORT_COLUMNS = new String[] {
		"a.username",
		"a.startDate",
		"a.expireDate",
		"a.status",
		"a.type"
	};
	
	
	
	private Account account;// 账号对象
//	private String belongId;// 账号所属id
	private Date validity;// 有效期
	private String assignRoleIds;// 用于分配角色时，拼装账号ID的字符串
	private String roleIds;// 分配角色功能记录已分配角色的ID
	private String pwRetrieveCode;// 密码重置校验码
	private String newPassword, rePassword;// 管理者修改所辖账号密码
	private boolean passwordWarning;// 是否提示修改密码
//	private String accountName, belongUnitName,belongPersonName;// 高级检索，账号名称，所属人员或所属单位
	private Date createDate1, createDate2, expireDate1, expireDate2;// 创建时间起始，有效期起始
	private int status;// 账号状态，用于高级检索
	private int type;
	private String belongEntityId, belongEntityName;// 添加、修改账号信息时回显，防止返回编辑页面后，该信息丢失
	private InputValidate inputValidate = new InputValidate();//校验工具类
	private String term;//自动补全的接收变量
	private Mail mail;
	private List<String[]> userList;// 登录可选的账号信息
	private int flag;
	
	@Autowired
	private IAccountService accountService;// 账号管理接口
	/*
	@Autowired
	private MailController mailController;*/
	/**
	 * 使用PROPAGATION_REQUIRES_NEW传播特性的编程式事务模板
	 */
	@Autowired
	private TransactionTemplate txTemplateRequiresNew;
	
	
	
	
	@Override
	public String view() {
		
		jsonMap.clear();
		 account = accountService.getAccountById(entityId);
		 if(account==null){
			 jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_EXIST);
			 return INPUT;
			 
		 } else{
			 
			 jsonMap.put("account",account);
				return SUCCESS;
		 }
	}

	@Override
	public String toView() {

		return SUCCESS;
		
	}

	
	
	@Override
	public String add() {
		accountService.addAccount(account);
		
		return SUCCESS;
	}

	@Override
	public String toAdd() {
		
		return SUCCESS;
	}

	@Override
	public String delete() {
		
		accountService.delete(entityIds);
		
		return SUCCESS;
	}

	@Override
	public String modify() {
		
		account = dao.query(Account.class, entityId);
		return null;
	}

	@Override
	public String toModify() {
	
		return SUCCESS;
	}

	public String toEnable(){
		
		return SUCCESS;
	}
	
	public  String enable(){
		
		/**
		 * 获取账号
		 * 判断账号级别（是否在当前管理范围）
		 * 决定是否启用
		 * 
		 */
		accountService.enable(entityIds, validity);
		
		
		return SUCCESS;
	}
	
	
	
	public String disable(){
		
		/**
		 * 获取账号
		 * 判断账号是否在当前管理范围
		 * 决定是否停用
		 * 
		 */
		accountService.disable(entityIds);
		return SUCCESS;
	}
	
	
	
	@Override
	public String toList() {
		
		return defaultToList();
	}

	@Override
	public String list() {
		
		return defaultList();
	}

	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if (keyword != null && !keyword.isEmpty()) {
			hql.append(" where ");
			if (searchType == 1) {
				hql.append("LOWER(a.username) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				hql.append("LOWER(a.description) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else {
				hql.append("(LOWER(a.username) like :keyword or LOWER(a.description) like :keyword)");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			0,
			null,
			null
		};
	}

	@Override
	public String simpleSearch() {
		
		return defaultSimpleSearch();
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advSearch() {
		
		return  defaultAdvSearch();
	}

	@Override
	public String changePageSize() {
		
		return defaultChangePageSize();
	}

	@Override
	public String toPage() {
		
		return defaultToPage();
	}

	@Override
	public String toEntity() {
		
		return defaultToEntity();
	}

	@Override
	public String sort() {
		
		return defaultSort();
	}

	@Override
	public String pageName() {
		
		return this.PAGE_NAME;
	}

	@Override
	public String pageBufferId() {
		
		return this.PAGE_BUFFER_ID;
	}

	@Override
	public String[] sortColumn() {
		
		return this.SORT_COLUMNS;
	}

	@Override
	public String dateFormat() {
		
		return this.DATE_FORMAT;
	}

	
	
	public void assignRole(LoginInfo loginer, List<String> accountIds,
			String[] roleIds, int type) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
	

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public String getAssignRoleIds() {
		return assignRoleIds;
	}

	public void setAssignRoleIds(String assignRoleIds) {
		this.assignRoleIds = assignRoleIds;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getPwRetrieveCode() {
		return pwRetrieveCode;
	}

	public void setPwRetrieveCode(String pwRetrieveCode) {
		this.pwRetrieveCode = pwRetrieveCode;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public boolean isPasswordWarning() {
		return passwordWarning;
	}

	public void setPasswordWarning(boolean passwordWarning) {
		this.passwordWarning = passwordWarning;
	}

	public Date getCreateDate1() {
		return createDate1;
	}

	public void setCreateDate1(Date createDate1) {
		this.createDate1 = createDate1;
	}

	public Date getCreateDate2() {
		return createDate2;
	}

	public void setCreateDate2(Date createDate2) {
		this.createDate2 = createDate2;
	}

	public Date getExpireDate1() {
		return expireDate1;
	}

	public void setExpireDate1(Date expireDate1) {
		this.expireDate1 = expireDate1;
	}

	public Date getExpireDate2() {
		return expireDate2;
	}

	public void setExpireDate2(Date expireDate2) {
		this.expireDate2 = expireDate2;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBelongEntityId() {
		return belongEntityId;
	}

	public void setBelongEntityId(String belongEntityId) {
		this.belongEntityId = belongEntityId;
	}

	public String getBelongEntityName() {
		return belongEntityName;
	}

	public void setBelongEntityName(String belongEntityName) {
		this.belongEntityName = belongEntityName;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public List<String[]> getUserList() {
		return userList;
	}

	public void setUserList(List<String[]> userList) {
		this.userList = userList;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	/*public MailController getMailController() {
		return mailController;
	}

	public void setMailController(MailController mailController) {
		this.mailController = mailController;
	}*/

	public TransactionTemplate getTxTemplateRequiresNew() {
		return txTemplateRequiresNew;
	}

	public void setTxTemplateRequiresNew(TransactionTemplate txTemplateRequiresNew) {
		this.txTemplateRequiresNew = txTemplateRequiresNew;
	}

	public InputValidate getInputValidate() {
		return inputValidate;
	}

	public void setInputValidate(InputValidate inputValidate) {
		this.inputValidate = inputValidate;
	}

	
	
}
