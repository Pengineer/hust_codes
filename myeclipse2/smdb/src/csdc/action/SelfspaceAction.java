package csdc.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.e;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Mail;
import csdc.bean.Passport;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IAccountService;
import csdc.service.IPassportService;
import csdc.tool.InputValidate;
import csdc.tool.MD5;
import csdc.tool.SignID;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.mail.MailController;

/**
 * 当前账号管理，账号信息的查看、账号名修改、密码修改、密码重置、绑定邮箱/手机
 * @author 龚凡
 * @version 2011.04.08
 */
@SuppressWarnings("unchecked")
public class SelfspaceAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	protected IHibernateBaseDao dao;
	private InputValidate inputValidate = new InputValidate();//校验工具类
	private IAccountService accountService;// 账号管理接口
	private IPassportService passportService;
	private String entityId;// 实体ID
	private String accountname;// 账号所有者名称，机构或人的名称用于显示，也用于修改账号名称
	private String pwRetrieveCode;// 密码重置校验码
	private String emailVerifyCode;// 密码重置校验码
	private String bindEmailVerifyCode;//自动生成的邮箱校验码
	private String password, repassword, origpassword;// 重置密码
	private String redir;// 查看、修改当前账号所属机构或人员信息时用于跳转的URL
	private String email;// 找密码时需要提供邮箱
	private static final String TMP_SELF_ID = "selfId";
	private Map jsonMap;
	private List<Object> accounts;
	private List<String> passportId;
	protected Account account;// 账号对象
	protected Passport passport;// 通行证对象
	protected Mail mail;
	private Integer flag;//1:绑定邮箱；2绑定手机号
	private String bindEmail, bindPhone;// 绑定的邮箱/手机
	protected LoginInfo loginer;// 当前登录账号信息对象
	@Autowired
	private MailController mailController;
	/**
	 * 使用PROPAGATION_REQUIRES_NEW传播特性的编程式事务模板
	 */
	@Autowired
	private TransactionTemplate txTemplateRequiresNew;
	
	/**
	 * 进入找回密码页面
	 */
	public String toRetrievePassword() {
		return SUCCESS;
	}

	/**
	 * 找回密码最后会生成密码重置链接发送到用户邮箱，
	 * 重置链接会携带账号ID和重置码，以便重置密码时进行校验。
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String retrievePassword() throws Exception {
		// 根据账号名查找通行证ID
		try {
			passport = (Passport) dao.queryUnique("select p from Passport p where p.name = ? ", accountname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (passport == null) {// 如果通行证不存在，则返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, "用户名错误！");
			return INPUT;
		} else {// 如果账号存在，则校验邮箱是否正确
			// 根据账号获取账号所属机构或人员的邮箱
			List accList = dao.query("select a from Account a where a.passport.id = ?", passport.getId());
			Boolean flag = false;
			for (int i = 0; i < accList.size(); i++) {
				account = (Account) accList.get(i);
				String orgEmail = accountService.getEmailByAccount(account);
				if (orgEmail.contains(email)) {
					flag = true;
				}
			}
			if (flag) {// 如果邮箱填写正确，则生成重置密码链接，并调用重置密码功能
				txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						try {
							// 生成重置链接的主体部分，包括域名、端口、应用名
							HttpServletRequest request = ServletActionContext.getRequest();
							String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
							mail = accountService.retrieveCode(account, null, path);
						} catch (Exception e) {
							status.setRollbackOnly();
						} 
					}
				});
				mailController.send(mail.getId());// 发送邮件
				return SUCCESS;
			} else {// 如果邮箱填写不正确，则返回错误提示
				this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱错误！");
				return INPUT;
			}
		}
	}

	/**
	 * 找回密码校验
	 */
	public void validateRetrievePassword() {
		if (accountname == null || accountname.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请输入账号");
		}
		if (email == null || email.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请输入邮箱");
		}
	}

	/**
	 * 进入重置密码
	 */
	public String toResetPassword() {
		// 根据账号ID查找账号对象
		Account account = (Account) dao.query(Account.class, entityId);
		Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		
		if (passport == null) {// 如果账号不存在，则返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, "此通行证已被删除");
			return INPUT;
		} else {// 如果账号存在，则校验密码重置码是否正确、重置链接是否有效
			Date currentDate = new Date();// 获取系统当前时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(passport.getPasswordRetrieveCodeStartDate());// 重置码生成时间
			calendar.add(Calendar.DATE, 1);
			Date date = calendar.getTime();// 重置码有效时间，1天
			if (pwRetrieveCode.equals(passport.getPasswordRetrieveCode()) && date.after(currentDate)) {// 如果重置码正确且链接有效，则账号ID缓存到session中以便重密码时使用
				ActionContext.getContext().getSession().put(TMP_SELF_ID, entityId);
				return SUCCESS;
			} else {// 如果重置码错误或链接已过有效期，则返回错误提示
				this.addFieldError(GlobalInfo.ERROR_INFO, "该链接已失效");
				return INPUT;
			}
		}
	}

	/**
	 * 密码重置校验
	 */
	public void validateToResetPassword() {
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的账号");
		}
		if (pwRetrieveCode == null || pwRetrieveCode.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的密码重置码");
		}
	}

	/**
	 * 密码重置
	 */
	public String resetPassword() {
		// 读取session中缓存的账号ID
		entityId = (String) ActionContext.getContext().getSession().get(TMP_SELF_ID);
		
		// 根据ID读取账号对象
		Account account = (Account) dao.query(Account.class, entityId);
		Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		
		if (passport == null) {// 如果通行证不存在，则返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, "此通行证已被删除");
			return INPUT;
		} else {// 如果账号存在，则校验密码重置码
			if (pwRetrieveCode.equals(passport.getPasswordRetrieveCode())) {// 如果密码重置码正确，则重置密码
				// 将密码加密存储
				password = MD5.getMD5(password);
				passport.setPassword(password);
				
				// 再随机生成一个重置码，使原重置码失效
				passport.setPasswordRetrieveCode(MD5.getMD5(SignID.getRandomString(8)));
				dao.modify(passport);
				return SUCCESS;
			} else {// 如果密码重置不正确，则返回错误提示
				this.addFieldError(GlobalInfo.ERROR_INFO, "密码重置码不正确");
				return INPUT;
			}
		}

	}

	/**
	 * 密码重置校验
	 */
	public void validateResetPassword() {
		if (password == null || password.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "密码不得为空");
		} else {
			if (password.length() < 3 || password.length() > 20) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "密码长度限制为3-20位");
			}
		}
		if (repassword == null || repassword.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "重复密码不得为空");
		} else {
			if (!password.equals(repassword)) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "两次输入的密码不一致");
			}
		}
	}

	/**
	 * 查看当前账号信息，信息直接从session中缓存
	 * 的loginer中读取，此处不做任何处理。
	 */
	public String viewSelfAccount() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Passport passport = loginer.getPassport();
		Map parMap = new HashMap();
		parMap.put("passportId", passport.getId());
		accounts = dao.query("select a.type, a.status, a.expireDate from Account a left join a.passport p where p.id = :passportId order by a.type asc", parMap);
		return SUCCESS;
	}

	/**
	 * 进入修改当前账号信息页面，当前账号信息从
	 * session中缓存的loginer中读取，此处不做任何处理。
	 */
	public String toModifySelfAccount() {
		return SUCCESS;
	}
	
	/**
	 * 修改账号信息：账号名、密码
	 */
	public String modifySelfAccount() {
		// 从session中获取当前账号对象
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Passport passport = loginer.getPassport();
		
		if (accountname != null && !accountname.isEmpty() && !passport.getName().equals(accountname)) {// 如果修改了账号名，则校验新的账号名是否可用
			if (accountService.checkAccountName(accountname)) {// 如果账号名不可用，则返回错误提示
				this.addFieldError(GlobalInfo.ERROR_INFO, "该账号名称已存在");
				return INPUT;
			} else {// 如果账号名可用，则更新账号名
				passport.setName(accountname);
			}
		}
		
		// 修改密码
		if (passport.getPassword().equals(MD5.getMD5(origpassword))) {// 如果原密码正确，则更新密码
			// 将新密码加密后存储
			passport.setPassword(MD5.getMD5(password));
			// 修改数据库
			dao.modify(passport);
			// 修改session中缓存的账号对象
			loginer.setPassport(passport);
			session.put(GlobalInfo.LOGINER, loginer);
			return SUCCESS;
		} else {// 如果原密码错误，则返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, "原始密码错误");
			return INPUT;
		}
	}

	/**
	 * 修改校验
	 */
	public void validateModifySelfAccount() {
		if (accountname == null || accountname.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "账号名不得为空");
		} else if (!Pattern.matches("\\w{3,20}", accountname.trim())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "用户名必须是字母和数字，长度为4到20之间");
		}
		if (origpassword != null || password != null || repassword != null) {
			if (origpassword == null || origpassword.isEmpty()) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "请输入原密码");
			}
			if (password == null || password.isEmpty()) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "请输入新密码");
			}
			if (repassword == null || repassword.isEmpty()) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "重复密码不得为空");
			} else {
				if (!repassword.equals(repassword)) {
					this.addFieldError(GlobalInfo.ERROR_INFO, "两次输入的密码不一致");
				}
				if (password.length() < 3 || password.length() > 20) {
					this.addFieldError(GlobalInfo.ERROR_INFO, "密码长度限制为3-20位");
				}
			}
		}
	}

	/**
	 * 给当前账号绑定其他的登录方式（邮箱、手机登）
	 */
	public String toBindEmail() {
		return SUCCESS;
	}
	public String toBindPhone() {
		return SUCCESS;
	}
	
	/**
	 * 添加绑定
	 * 需要当前账号的密码才能绑定
	 */
	public String bindEmail() {
		// 从session中获取当前账号对象
		Map session = ActionContext.getContext().getSession();
		loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Passport passport = loginer.getPassport();
		account = loginer.getAccount();
		bindEmailVerifyCode = MD5.getMD5(SignID.getRandomString(4));// 生成重置码
		// 根据密码来进行校验，只有知道账号的密码才能添加绑定
		if (passport.getPassword().equals(MD5.getMD5(password))) {// 如果原密码正确，则可以进行绑定
			if (null != bindEmail ) {//绑定邮箱
				List<String> re = dao.query("select p.id from Passport p where p.bindEmail=? ", bindEmail);// 获取指定名称的账号
				if (re.size() != 0 && !bindEmail.equals(passport.getBindEmail())) {//邮箱已经被绑定了
					this.addFieldError(GlobalInfo.ERROR_INFO, "该邮箱已经被绑定过了！");
					return INPUT;
				} else {
					txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
						protected void doInTransactionWithoutResult(TransactionStatus status) {
							try {
								// 生成重置链接的主体部分，包括域名、端口、应用名
								HttpServletRequest request = ServletActionContext.getRequest();
								String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
								mail = accountService.bindEmailCode(account, loginer, path, bindEmail, bindEmailVerifyCode);// 生成重置密码的邮件
							} catch (Exception e) {
								status.setRollbackOnly();
							} 
						}
					});
					mailController.send(mail.getId());// 发送邮件
					passport.setEmailVerified(0);
					passport.setEmailVerifyCode(bindEmailVerifyCode);
					passport.setBindEmail(bindEmail);
				}
			} 
			// 修改数据库
			dao.modify(passport);
			// 修改session中缓存的账号对象
			loginer.setPassport(passport);
			session.put(GlobalInfo.LOGINER, loginer);
			return SUCCESS;
		} else {// 如果原密码错误，则返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, "原始密码错误");
			return INPUT;
		}
	}
	/**
	 * 邮箱绑定校验
	 */
	public void validateBindEmail(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Passport passport = loginer.getPassport();
		if (null != bindEmail) {//绑定邮箱
			List<String> re = dao.query("select p.id from Passport p where p.bindEmail=? ", bindEmail);// 获取指定名称的账号
			if (re.size() != 0 && !bindEmail.equals(passport.getBindEmail())) {//邮箱已经被绑定了
				this.addFieldError(GlobalInfo.ERROR_INFO, "该邮箱已经被绑定过了！");
			}else {
				String[] mail = bindEmail.split(";");
				for (int i = 0; i < mail.length; i++) {
					String	email = mail[i];
					if(!inputValidate.checkEmail(email.trim())){
						this.addFieldError(GlobalInfo.ERROR_INFO, "邮箱不合法");
					}
				}
			}
		} else {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请输入要绑定的邮箱！");
		}
	}
	
	/**
	 * 邮箱绑定确认
	 * emailVerified设为1表示激活
	 */
	public String verifyEmail(){
		// 根据账号ID查找账号对象
		Account account = (Account) dao.query(Account.class, entityId);
		Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		if (passport == null) {// 如果账号不存在，则返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, "此通行证已被删除");
			return INPUT;
		} else {// 如果账号存在，则校验密码重置码是否正确
			if (emailVerifyCode.equals(passport.getEmailVerifyCode()) ) {// 如果重置码正确且则emailVerified设为1表示激活
				passport.setEmailVerified(1);
				// 修改数据库
				dao.modify(passport);
				return SUCCESS;
			} else {// 如果重置码错误或链接已过有效期，则返回错误提示
				this.addFieldError(GlobalInfo.ERROR_INFO, "该链接已失效");
				return INPUT;
			}
		}
	}
	/**
	 * 手机绑定校验
	 */
	public void validateBindPhone(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Passport passport = loginer.getPassport();
		if (null != bindPhone) {//绑定手机
			List<String> re = dao.query("select p.id from Passport p where p.bindPhone=? ", bindPhone);// 获取指定名称的账号
			if (re.size() != 0 && !bindPhone.equals(passport.getBindPhone())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "该手机已经被绑定了");
			} else {
				if (!inputValidate.checkCellphone(bindPhone.trim())){
					this.addFieldError(GlobalInfo.ERROR_INFO, "手机号不合法");
				}
			}
		} else {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请输入手机号码");
		}
	}
	
	public String bindPhone() {
		// 从session中获取当前账号对象
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Passport passport = loginer.getPassport();

		// 根据密码来进行校验，只有知道账号的密码才能添加绑定
		if (passport.getPassword().equals(MD5.getMD5(password))) {// 如果原密码正确，则可以进行绑定
			if (null != bindPhone) {//绑定手机
				List<String> re = dao.query("select p.id from Passport p where p.bindPhone=? ", bindPhone);// 获取指定名称的账号
				if (re.size() != 0 && !bindPhone.equals(passport.getBindPhone())) {
					this.addFieldError(GlobalInfo.ERROR_INFO, "该手机已经被绑定了");
					return INPUT;
				} else {
					passport.setBindPhone(bindPhone);
				}
			} 
			// 修改数据库
			dao.modify(passport);
			// 修改session中缓存的账号对象
			loginer.setPassport(passport);
			session.put(GlobalInfo.LOGINER, loginer);
			return SUCCESS;
		} else {// 如果原密码错误，则返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, "原始密码错误");
			return INPUT;
		}
	}
	
	
	/**
	 * 判断是否需要修改密码
	 */
	public String checkIfNeedModifyPassword() {
		// 从session中读取当前登录账号
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		if (loginer == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "账号信息错误");
		} 
		Passport passport = loginer.getPassport();
		return getDirect(passport);
	}
	
	/**
	 * 进入修改密码页面
	 */
	public String toModifyPassword() {
		return SUCCESS;
	}
	
	/**
	 * 登录修改密码，此为登录提示修改密码功能
	 */
	public String modifyPassword() {
		// 从session中读取当前登录账号
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
//		Account account = loginer.getAccount();
		if (loginer == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "账号信息错误");
		} 
		Passport passport = loginer.getPassport();
		// 修改密码
		if (passport.getPassword().equals(MD5.getMD5(origpassword))) {// 如果原密码正确，则修改密码
			// 将新密码加密后存储
			passport.setPassword(MD5.getMD5(password));
			passport.setPasswordWarning(0);
			// 修改数据库
			dao.modify(passport);
			// 修改session中缓存的账号对象
			loginer.setPassport(passport);
			session.put(GlobalInfo.LOGINER, loginer);
			return getDirect(passport);
		} else {// 如果原密码错误，则返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, "原密码错误");
			return INPUT;
		}
	}
	/**
	 * 获得页面跳转，只有一个账号，且未停用在有效期内，才返回选系统，否则返回选账号
	 * @param passport
	 * @return account返回选账号 system返回选系统
	 */
	private String getDirect(Passport passport) {
		List<Account> accountList = accountService.getAccountListByName(accountService.securityUsername());
		if(passport.getPasswordWarning() == 1) {
			return SUCCESS;
		} else if (accountList.size() == 1) {// 只有一个账号
			Account account = accountList.get(0);
			if (account.getStatus() == 0 || passport.getStatus() == 0) {// 该用户已停用，则阻止登录
				return "account";
			} else {// 该用户未停用，则判断该账号是否有效
				Date currenttime = new Date();
				if (currenttime.after(account.getExpireDate())) {// 如果超过有效期，则阻止登录，并更新账号状态为停用
					account.setStatus(0);
					dao.modify(account);
					passportService.updatePassport(account.getPassport().getId());
					return "account";
				} else {// 
					return "system";
				}
			}
		} else {
			return "account";
		}
	}
	/**
	 * 修改密码校验，由于页面特点，一次只返回一个错误信息
	 */
	public void validateModifyPassword() {
		if (origpassword == null || origpassword.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请输入原密码");
		} else if (password == null || password.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请输入新密码");
		} else if (repassword == null || repassword.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "重复密码不得为空");
		} else {
			if (!repassword.equals(password)) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "两次输入的密码不一致");
			} else if (password.length() < 3 || password.length() > 20) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "密码长度限制为3-20位");
			}
		}
	}
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getBindEmailVerifyCode() {
		return bindEmailVerifyCode;
	}

	public void setBindEmailVerifyCode(String bindEmailVerifyCode) {
		this.bindEmailVerifyCode = bindEmailVerifyCode;
	}

	public String getPwRetrieveCode() {
		return pwRetrieveCode;
	}
	public void setPwRetrieveCode(String pwRetrieveCode) {
		this.pwRetrieveCode = pwRetrieveCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getBindEmail() {
		return bindEmail;
	}

	public void setBindEmail(String bindEmail) {
		this.bindEmail = bindEmail;
	}



	public String getBindPhone() {
		return bindPhone;
	}

	public void setBindPhone(String bindPhone) {
		this.bindPhone = bindPhone;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getOrigpassword() {
		return origpassword;
	}
	public void setOrigpassword(String origpassword) {
		this.origpassword = origpassword;
	}
	public String getRedir() {
		return redir;
	}
	public void setRedir(String redir) {
		this.redir = redir;
	}
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public InputValidate getInputValidate() {
		return inputValidate;
	}

	public void setInputValidate(InputValidate inputValidate) {
		this.inputValidate = inputValidate;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public List<Object> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Object> accounts) {
		this.accounts = accounts;
	}
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public String getEmailVerifyCode() {
		return emailVerifyCode;
	}

	public void setEmailVerifyCode(String emailVerifyCode) {
		this.emailVerifyCode = emailVerifyCode;
	}

	public List<String> getPassportId() {
		return passportId;
	}

	public void setPassportId(List<String> passportId) {
		this.passportId = passportId;
	}
	public void setPassportService(IPassportService passportService){
		this.passportService = passportService;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

}
