package csdc.action.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import oracle.net.aso.e;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.action.security.account.AccountAction;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Mail;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Right;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.service.IAccountService;
import csdc.tool.MD5;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.AccountInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.RightInfo;
import csdc.tool.mail.MailController;
/**
 * 通行证管理
 * @author yangfq
 * @version 2013.07.20
 */
public class PassportAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	protected IAccountService accountService;// 通行证、账号管理接口
	private static final String HQL = "select p.id, p.name, p.maxSession, p.allowedIp, p.refusedIp, p.lastLoginIp,p.startDate, p.expireDate, p.status, p.lastLoginDate from Passport p where 1=1 ";
	private static final String GROUP_BY = "group by p.id, p.name, p.maxSession, p.allowedIp, p.refusedIp, p.lastLoginIp, p.startDate, p.expireDate, p.status, p.lastLoginDate";
	private static final String PAGE_NAME = "passportPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "p.id";// 上下条查看时用于查找缓存的字段
	protected static final String TMP_ACCOUNT_ID = "accountId";// 缓存与session中，备用的账号ID变量名称
	private Passport passport;
	protected Account account;// 账号对象
	protected boolean passwordWarning;// 是否提示修改密码
	protected String newPassword, rePassword;// 管理者修改所辖账号密码
	protected Mail mail;
	private String accountname;// 账号所有者名称，机构或人的名称用于显示，也用于修改账号名称
	private String password, repassword;// 重置密码
	@Autowired
	private MailController mailController;
	/**
	 * 使用PROPAGATION_REQUIRES_NEW传播特性的编程式事务模板
	 */
	@Autowired
	private TransactionTemplate txTemplateRequiresNew;
	private static final String[] COLUMN = {
		"p.name",
		"p.maxSession",
		"p.allowedIp",
		"p.refusedIp",
		"p.lastLoginIp",
		"p.startDate",
		"p.expireDate", 
		"p.status", 
		"p.lastLoginDate"
	};// 用于拼接的排序列
	
	
	public String HQL() {
		return HQL;
	}
	public String[] column(){
		return COLUMN;
	}
	@Override
	public String pageName() {
		return PAGE_NAME;
	}
	public String dateFormat() {
		return PassportAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return PassportAction.PAGE_BUFFER_ID;
	}
	/**
	 * @author yangfq
	 * 处理初级检索条件，拼装查询语句。
	 */
	@Override
	public Object[] simpleSearchCondition() {
		
		// 拼接检索条件
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL());
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
			hql.append(" and ");
			hql.append(" LOWER(p.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		}
		hql.append(GROUP_BY);
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	/**
	 * 进入查看
	 */
	public String toView() {
		return SUCCESS;
	}

	/**
	 * 进入查看校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {// ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_PASSPORT_NULL);
		}
	}
	
	/**
	 * 查看详情
	 */
	public String view() {
		passport = dao.query(Passport.class, entityId);//获取通行证信息
		List<String[]> userList = new ArrayList<String[]>();
		if (passport == null) {// 通行证不存在，返回错误提示
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_PASSPORT_NULL);
			return INPUT;
		} else {
			userList = accountService.userInfo(passport.getName());
			jsonMap.put("passport", passport);//通行证信息
			//账号所属的相关信息，分别为：账号级别；主子账号（1：主；0子）；账号ID；账号所属的名称；账号类型；账号状态（0：停用；1：启用）；账号所属ID;院系、基地所属高校的名称;院系、基地所属高校的ID
			jsonMap.put("userList", userList);
			return SUCCESS;
		} 
	}
	
	/**
	 * 进入修改密码页面。
	 */
	public String toModifyPassword() {
		ActionContext.getContext().getSession().put(TMP_ACCOUNT_ID, entityId);// 缓存通行证ID到session
		return SUCCESS;
	}

	/**
	 * 进入修改密码校验
	 */
	public void validateToModifyPassword() {
		if (entityId == null || entityId.isEmpty()) {// 账号ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_MODIFYP_NULL);
		}
	}
	
	/**
	 * 修改通行证密码
	 */
	@Transactional
	public String modifyPassword() {
		/**
		 * 1、根据参数获取账号。
		 * 2、设置是否登录提示。
		 * 3、修改密码。
		 * 4、清除session中缓存的账号ID
		 */
		Map session = ActionContext.getContext().getSession();
		passport = (Passport) dao.query(Passport.class, (String) session.get(TMP_ACCOUNT_ID));//获取指定的通行证
		if (passwordWarning) {// 将是否提示登录修改密码由boolean改为int
			passport.setPasswordWarning(1);
		} else {
			passport.setPasswordWarning(0);
		}
		accountService.modifyPassword(passport, newPassword);// 修改密码
		session.remove(TMP_ACCOUNT_ID);// 清除缓存的账号ID
		pageInfo = AccountInfo.ERROR_MODIFY_PASSWORD_SUCCESS;// 跳转到查看页面时的提示信息
		return SUCCESS;
	}

	
	/**
	 * 生成密码重置码。
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String retrieveCode() {
		/**
		 * 1、根据参数获取账号。
		 * 2、判断账号是否存在，级别及类别是否正确。
		 * 3、判断账号所属是否在当前账号管辖范围。
		 * 4、生成密码重置码，并以邮件形式通知用户。
		 */
		List<String> accountIds = dao.query("select a.id from Account a where a.passport.id = ?", entityId);
		for (String accountId : accountIds) {
			account = (Account) dao.query(Account.class, accountId);// 获取指定账号
		}
		txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					// 生成重置链接的主体部分，包括域名、端口、应用名
					String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
					mail = accountService.retrieveCode(account, loginer, path);// 生成重置密码的邮件
				} catch (Exception e) {
					status.setRollbackOnly();
				} 
			}
		});
		mailController.send(mail.getId());// 发送邮件
		return SUCCESS;
	}
	

	/**
	 * 密码重置校验
	 */
	public void validateRetrieveCode() {
		if (entityId == null || entityId.isEmpty()) {// 账号ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_RETRIEVE_NULL);
		}
	}
	
	public String toModify() {
		passport = dao.query(Passport.class, entityId);//获取通行证信息
		List<String> accountIds = dao.query("select a.id from Account a where a.passport.id = ?", entityId);
		for (String accountId : accountIds) {
			account = dao.query(Account.class, accountId);//获取账号信息
		}
		if (passport == null) {// 权限不存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, "通行证不存在");
			return INPUT;
		} else {// 权限存在，备用权限ID
			ActionContext.getContext().getSession().put(TMP_ACCOUNT_ID, entityId);
			return SUCCESS;
		}
	}

	/**
	 * 修改通行证信息：用户名、密码
	 */
	public String modify() {
		// 从session中获取当前账号对象
		Map session = ActionContext.getContext().getSession();
		Passport oldPassport = (Passport) dao.query(Passport.class, (String) session.get(TMP_ACCOUNT_ID));//获取原始的通行证
		// 若账号名进行了修改则检测新的账号名是否存在
		if (!passport.getName().equals(oldPassport.getName())) {// 账号名修改了，则进行重名判断
			if (accountService.checkAccountName(passport.getName())) {// 账号重名，返回错误提示
				this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NAME_EXIST);
				return INPUT;
			}
		}
		if (passwordWarning) {// 将是否提示登录修改密码由boolean改为int
			passport.setPasswordWarning(1);
		} else {
			passport.setPasswordWarning(0);
		}
		entityId = accountService.modifyPassport(oldPassport, passport, loginer);// 修改账号
		session.remove(TMP_ACCOUNT_ID);// 清除缓存的账号ID
		return SUCCESS;
	}

	/**
	 * 删除通行证
	 */
	@Transactional
	public String delete() {
		accountService.deleteAccount(entityIds);// 删除账号
		return SUCCESS;
	}

	/**
	 * 删除账号校验
	 */
	public String validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {// 账号ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_DELETE_NULL);
			return INPUT;
		} else {
			return null;
		}
	}
	
	
	@Override
	public Object[] advSearchCondition() {
		return null;
	}


	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Passport getPassport() {
		return passport;
	}
	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public boolean isPasswordWarning() {
		return passwordWarning;
	}
	public void setPasswordWarning(boolean passwordWarning) {
		this.passwordWarning = passwordWarning;
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
	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public MailController getMailController() {
		return mailController;
	}
	public void setMailController(MailController mailController) {
		this.mailController = mailController;
	}
	public TransactionTemplate getTxTemplateRequiresNew() {
		return txTemplateRequiresNew;
	}
	public void setTxTemplateRequiresNew(TransactionTemplate txTemplateRequiresNew) {
		this.txTemplateRequiresNew = txTemplateRequiresNew;
	}

}
