package csdc.action.security.account;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.bean.Mail;
import csdc.bean.Passport;
import csdc.bean.Role;
import csdc.service.IAccountService;
import csdc.service.IPassportService;
import csdc.tool.InputValidate;
import csdc.tool.bean.AccountType;
import csdc.tool.info.AccountInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.mail.MailController;

/**
 * 账号模块action父类，定义公共成员变量及成员方法。
 * 账号共分为以下几类：
 *     部级主子账号、省级主子账号、部属高校主子账号、地方高校主子账号、
 *     院系主子账号、基地主子账号、外部专家账号、教师账号、学生账号。
 * 账号表字段说明详见csdc.bean.Account.java中字段注释。
 * 本类实现的账号模块功能包括：
 *     添加、删除、修改、查看、启用、停用、分配角色、重置密码、修改密码、
 *     分配账号等功能。
 * 由于账号列表要显示每个账号已分配的角色，所以关于列表数据的处理部分需要重写。
 * @author 龚凡
 * @version 2011.04.07
 */
public abstract class AccountAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	protected static final String TMP_ACCOUNT_ID = "accountId";// 缓存与session中，备用的账号ID变量名称
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";// 列表时间格式
	protected static final String PAGE_BUFFER_ID = "a.id";// 上下条查看时用于查找缓存的字段名称
	protected IAccountService accountService;// 账号管理接口
	protected IPassportService passportService;
	protected Account account;// 账号对象
	protected Passport passport;// 账号对象
	protected String belongId;// 账号所属id
	protected Date validity;// 有效期
	protected String assignRoleIds;// 用于分配角色时，拼装账号ID的字符串
	protected String roleIds;// 分配角色功能记录已分配角色的ID
	protected String pwRetrieveCode;// 密码重置校验码
	protected String newPassword, rePassword;// 管理者修改所辖账号密码
	protected boolean passwordWarning;// 是否提示修改密码
	protected String accountName, belongUnitName,belongPersonName;// 高级检索，账号名称，所属人员或所属单位
	protected Date createDate1, createDate2, expireDate1, expireDate2;// 创建时间起始，有效期起始
	protected int status;// 账号状态，用于高级检索
	protected int type;
	protected String belongEntityId, belongEntityName;// 添加、修改账号信息时回显，防止返回编辑页面后，该信息丢失
	protected InputValidate inputValidate = new InputValidate();//校验工具类
	protected String term;//自动补全的接收变量
	protected Mail mail;
	private List<String[]> userList;// 登录可选的账号信息
	private int flag;
	
	@Autowired
	private MailController mailController;
	/**
	 * 使用PROPAGATION_REQUIRES_NEW传播特性的编程式事务模板
	 */
	@Autowired
	private TransactionTemplate txTemplateRequiresNew;
	
	
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	/**
	 * 为了将各类账号子类的功能抽象到账号父类实现，父类需要知道账号子类的类型，以进行相应的逻辑控制。
	 * 于是选取了以下三个量，利用抽象方法的特性，获取子类相关信息。
	 */
	public abstract AccountType getSubClassType();// 获取账号子类对应处理的账号级别
	public abstract int getSubClassPrincipal();// 获取账号子类对应处理的账号类别
	public abstract String groupBy();// 获取查询用的分组语句，主要是专家、教师、学生账号查询出的列表要进行去重

	public String dateFormat() {
		return AccountAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return AccountAction.PAGE_BUFFER_ID;
	}

	/**
	 * 跳转到账号添加页面。
	 */
	public String toAdd() {
		return SUCCESS;
	}

	public String getUserName() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();
		if(accountPrincipal == 1) {// 主账号通行证一对一，不需要校验账号名
			jsonMap.put("passport", "");
			return SUCCESS;
		} else {
			passport = accountService.getPassportByBelongId(belongId, accountType, accountPrincipal);
			if(passport != null){
				if(!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)){// 非系统管理员
					passport.setPassword(null);
					passport.setId(null);
					passport.setLastLoginIp(null);
					passport.setPasswordAnswer(null);
					passport.setPasswordQuestion(null);
					passport.setPasswordRetrieveCode(null);
					passport.setPasswordRetrieveCodeStartDate(null);
				}
				jsonMap.put("passport", passport.getName());
				return SUCCESS;
			} else {
				jsonMap.put("passport", "");
				return SUCCESS;
			}
		}
	}
	/**
	 * 添加账号
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String add() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		/**
		 * 1、判断账号所属是否在当前账号管辖范围内，当前所属包括：
		 * agencyId、departmentId、instituteId、expertId、teacherId、studentId。
		 * 2、判断该实体是否已有账号。
		 * 3、判断该账号名称是否已被使用。
		 * 4、添加账号
		 */
		if (accountService.checkIfUnderControl(loginer, belongEntityId,
				accountService.getEntityIdType(accountType, accountPrincipal), false)) {// 账号所属在管理范围，进行是否已有账号判断
			if (accountService.checkOwnAccount(belongEntityId, accountType,accountPrincipal)) {// 该所属已有账号，返回错误提示
				this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_EXIST);
				return INPUT;
			} else {// 该所属尚无账号，进行账号名唯一性判断
				Passport oriPassport = accountService.getPassportByBelongId(belongEntityId, accountType, accountPrincipal);
				if (oriPassport != null) {
					passport.setName(oriPassport.getName());
					if (passwordWarning) {// 将是否提示登录修改密码由boolean改为int
						passport.setPasswordWarning(1);
					} else {
						passport.setPasswordWarning(0);
					}
					account.setType(accountType);// 设置级别
					account.setIsPrincipal(accountPrincipal);// 设置类别
					txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
						protected void doInTransactionWithoutResult(TransactionStatus status) {
							try {
								jsonMap = accountService.addAccount(belongEntityId, account, passport, loginer);// 添加账号
								entityId = (String) jsonMap.get("id");
								mail = (Mail) jsonMap.get("mail");
							} catch (Exception e) {
								status.setRollbackOnly();
							} 
						}
					});
					pageInfo = "添加账号成功，账号名和密码将以邮件的形式发送给该用户";// 跳转到查看页面时，弹出才提示信息
					mailController.send(mail.getId());// 发送邮件
					return SUCCESS;
				} else {
					if (accountService.checkAccountName(passport.getName())) {// 账号名已存在，返回错误提示
						this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NAME_EXIST);
						return INPUT;
					} else {
						if (passwordWarning) {// 将是否提示登录修改密码由boolean改为int
							passport.setPasswordWarning(1);
						} else {
							passport.setPasswordWarning(0);
						}
						account.setType(accountType);// 设置级别
						account.setIsPrincipal(accountPrincipal);// 设置类别
//						jsonMap = accountService.addAccount(account, passport, loginer);// 添加账号
//						entityId = (String) jsonMap.get("id");
//						mail = (Mail) jsonMap.get("mail");
//						entityId = accountService.addAccount(account, passport, loginer);// 添加账号
						txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
							protected void doInTransactionWithoutResult(TransactionStatus status) {
								try {
									jsonMap = accountService.addAccount(belongEntityId, account, passport, loginer);// 添加账号
									entityId = (String) jsonMap.get("id");
									mail = (Mail) jsonMap.get("mail");
								} catch (Exception e) {
									status.setRollbackOnly();
								} 
							}
						});
						pageInfo = "添加账号成功，账号名和密码将以邮件的形式发送给该用户";// 跳转到查看页面时，弹出才提示信息
						mailController.send(mail.getId());// 发送邮件
						return SUCCESS;
					}
				}
			}
		} else {// 账号所属不在关系范围，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_BELONG_ILLEGAL);
			return INPUT;
		}
	}

	/**
	 * 创建账号校验
	 */
	public void validateAdd() {
		this.editValidate();// 调用编辑校验方法
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号，校验最大连接数，允许登录IP，拒绝登录IP
			if(passport.getMaxSession() > 100){passport.setMaxSession(5);}// 最大连接数不得超过100，超过则置为5
			if (passport.getAllowedIp() != null && !passport.getAllowedIp().isEmpty()) {
				if (!inputValidate.checkIp(passport.getAllowedIp())){
					this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_IP_ILLEGAL);
				}
			}
			if (passport.getRefusedIp() != null && !passport.getRefusedIp().isEmpty()) {
				if (!inputValidate.checkIp(passport.getRefusedIp())){
					this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_IP_ILLEGAL);
				}
			}
		} else {// 非系统管理员，最大连接数置为默认值，允许登录IP、拒绝登录IP置为null
			passport.setMaxSession(Integer.parseInt((String)ActionContext.getContext().getApplication().get("maxSession")));
			passport.setAllowedIp(null);
			passport.setRefusedIp(null);
		}
		if (belongEntityId == null || belongEntityId.isEmpty()) {// 账号所属不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_BELONG_NULL);
		}
	}

	/**
	 * 根据账号类别，获取最终用于范围判断的实体ID
	 * 主要是因为专家、教师、学生账号存储的是人员ID，
	 * 而判断其是否在管理范围确需要对应子表的ID
	 * @param belongId 账号所属ID
	 * @param accountType 账号类别
	 * @return rangeId 处理后的用于范围判断的实体ID
	 */
	private String rangeId (String belongId, AccountType accountType) {
		String tmpId = belongId;
		if(accountType.equals(AccountType.EXPERT)) {// 专家
			tmpId = accountService.getExpertIdByPersonId(belongId);
		} else if(accountType.equals(AccountType.TEACHER)) {// 教师
			tmpId = accountService.getTeacherIdByPersonId(belongId);
		} else if (accountType.equals(AccountType.STUDENT)) {// 学生
			tmpId = accountService.getStudentIdByPersonId(belongId);
		}
		return tmpId;
	}
	
	/**
	 * 进入修改页面，加载待修改账号的信息。
	 */
	public String toModify() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		/**
		 * 1、根据参数查找指定账号。
		 * 2、判断账号是否存在及级别和类别是否正确。由于校级账号action只有一类，
		 *    而数据却有两类，所以该类账号的判断有点特殊。
		 * 3、判断该账号所属是否在管辖范围。
		 * 4、获取账号所属机构或人员名称，用于页面回显。
		 * 5、缓存待处理账号的ID到session，以免修改账号信息时，再次判断管理范围。
		 */
		account = (Account) dao.query(Account.class, entityId);// 查找指定账号
		passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		if (account == null || (account.getType() != accountType && account.getType().equals(AccountType.LOCAL_UNIVERSITY) && !accountType.equals(AccountType.MINISTRY_UNIVERSITY)) || account.getIsPrincipal() != accountPrincipal) {// 查询出的数据异常，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
			return INPUT;
		} else {// 该账号数据正常，则判断该账号是否属于管辖范围
			if (accountService.checkIfUnderControl(loginer, rangeId(accountService.getBelongIdByAccount(account), accountType),
					accountService.getEntityIdType(accountType, accountPrincipal), false)) {// 该账号在管辖范围，则加载其它相关信息
				// 获取账号所属实体的ID、Name以便修改页面查看
				String[] belongIdName = accountService.getAccountBelong(account);
				belongEntityId = belongIdName[0];
				belongEntityName = belongIdName[1];
				ActionContext.getContext().getSession().put(TMP_ACCOUNT_ID, entityId);
				return SUCCESS;
			} else {// 该账号不在管辖范围，返回错误提示
				this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_MODIFY_DENY);
				return INPUT;
			}
		}
	}

	/**
	 * 进入修改校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {// 账号ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_MODIFY_NULL);
		}
	}

	/**
	 * 修改账号信息
	 */
	@Transactional
	public String modify() {
		/**
		 * 1、从session中获取缓存的待修改账号的ID，然后查询得到该账号对象及通行证对象。
		 * 2、检测是否修改了用户名，如果修改了，则进行判重。
		 * 3、修改账号及通行证
		 * 4、清除缓存的账号ID
		 */
		Map session = ActionContext.getContext().getSession();
		Account oldAccount = (Account) dao.query(Account.class, (String) session.get(TMP_ACCOUNT_ID));// 获得原账号对象
		Passport oldPassport = (Passport) dao.query(Passport.class, oldAccount.getPassport().getId());// 获得原通行证
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
		entityId = accountService.modifyAccount(oldAccount, account, oldPassport, passport, loginer);// 修改账号
		session.remove(TMP_ACCOUNT_ID);// 清除缓存的账号ID
		return SUCCESS;
	}

	/**
	 * 修改账号校验
	 */
	public void validateModify() {
		this.editValidate();// 调用编辑校验方法
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号，校验最大连接数，允许登录IP，拒绝登录IP
			if(passport.getMaxSession() > 100){passport.setMaxSession(5);}
			if (passport.getAllowedIp() != null && !passport.getAllowedIp().isEmpty()) {
				if (!inputValidate.checkIp(passport.getAllowedIp())){
					this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_IP_ILLEGAL);
				}
			}
			if (passport.getRefusedIp() != null && !passport.getRefusedIp().isEmpty()) {
				if (!inputValidate.checkIp(passport.getRefusedIp())){
					this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_IP_ILLEGAL);
				}
			}
		}
	}

	/**
	 * 添加和修改公共校验部分
	 */
	public void editValidate() {
		if (passport.getName() == null || passport.getName().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NAME_NULL);
		} else if (!Pattern.matches("\\w{3,40}", passport.getName())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NAME_ILLEGAL);
		}
		if (account.getStatus() != 0 && account.getStatus() != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_STATUS_ILLEGAL);
		}
		if (account.getExpireDate() == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_EXPIREDATE_NULL);
		} else if (account.getExpireDate().compareTo(new Date()) <= 0) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_EXPIREDATE_ILLEGAL);
		}
	}
	
	/**
	 * 进入查看页面
	 */
	public String toView() {
		return SUCCESS;
	}

	/**
	 * 进入查看页面校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {// 账号ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_VIEW_NULL);
		}
	}

	/**
	 * 查看账号信息
	 */
	public String view() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		/**
		 * 1、根据参数查找指定账号。
		 * 2、判断账号是否存在、级别以及类型是否正确等。
		 * 3、判断账号所属是否在管辖范围。
		 * 4、获取账号所属机构及人员姓名等信息。
		 * 5、获取账号拥有的角色信息。
		 * 6、更新缓存的page信息。
		 */
//		Map session = ActionContext.getContext().getSession();
//		session.put("accountId", entityId);// 将查看的账号id放入session中，供日志模块使用
		account = (Account) dao.query(Account.class, entityId);// 获取指定账号
		passport = (Passport) dao.query(Passport.class, account.getPassport().getId());//获取通行证信息
		if (account == null || (account.getType() != accountType && account.getType().equals(AccountType.LOCAL_UNIVERSITY) && !accountType.equals(AccountType.MINISTRY_UNIVERSITY)) || account.getIsPrincipal() != accountPrincipal) {// 查询出的数据异常，返回错误提示
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
			return INPUT;
		} else {// 该账号数据正常，判断该账号是否属于管辖范围
			if (accountService.checkIfUnderControl(loginer, rangeId(accountService.getBelongIdByAccount(account), accountType),
				accountService.getEntityIdType(accountType, accountPrincipal), false)) {// 该账号在管辖范围，检索账号相关信息
				jsonMap = accountService.viewAccount(account, jsonMap, loginer);// 调用查看方法
				return SUCCESS;
			} else {// 该账号不在管辖范围，返回错误提示
				jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_VIEW_DENY);
				return INPUT;
			}
		}
	}

	/**
	 * 查看账号信息校验
	 */
	public String validateView() {
		if (entityId == null || entityId.isEmpty()) {// 账号ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_VIEW_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 删除账号
	 */
	@Transactional
	public String delete() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		/**
		 * 1、根据参数获取指定账号
		 * 2、判断账号是否存在、级别以及类别是否正确。
		 * 3、判断账号是否在当前账号的管辖范围。
		 * 4、删除账号。
		 * 5、更新缓存的page信息。
		 */
		for (int i = 0; i < entityIds.size(); i++) {// 校验账号合法性
			account = (Account) dao.query(Account.class, entityIds.get(i));// 获取指定账号
			passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
			if (account == null || (!account.getType().equals(accountType) && account.getType().equals(AccountType.LOCAL_UNIVERSITY) && !accountType.equals(AccountType.MINISTRY_UNIVERSITY)) || account.getIsPrincipal() != accountPrincipal) {// 查询出的数据异常，返回错误提示
				jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
				return INPUT;
			} else {// 该账号数据正常，判断该账号是否属于管辖范围
				if (!accountService.checkIfUnderControl(loginer, rangeId(accountService.getBelongIdByAccount(account), accountType),
						accountService.getEntityIdType(accountType, accountPrincipal), false)) {// 该账号不在管辖范围，返回错误提示
					jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_DELETE_DENY + passport.getName());
					return INPUT;
				}
			}
		}
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

	/**
	 * 处理列表数据，账号模块添加账号角色信息，并处理时间格式。
	 */
	public void pageListDealWith() {
		laData = new ArrayList();// 处理之后的列表数据
		Object[] o;// 缓存查询结果的一行
		String[] item;// 缓存查询结果一行中的每一字段
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串，并将角色信息拼装到列表中
		for (Object p : pageList) {
			o = (Object[]) p;
			item = new String[o.length + 1];
			for (int i = 0; i < o.length; i++) {
				if (o[i] == null) {// 如果字段值为空，则以""替换
					item[i] = "";
				} else {// 如果字段值非空，则做进一步判断
					if (o[i] instanceof Date) {// 如果字段为时间对象，则按照子类定义的时间格式格式化
						datestr = dateformat.format((Date) o[i]);
						item[i] = datestr;
					} else {// 如果字段非时间对象，则直接转化为字符串
						item[i] = o[i].toString();
					}
				}
			}
			item[o.length] = accountService.getRoleName(item[0]);// 获取账号角色信息，并将其拼到列表中
			laData.add(item);// 将处理好的数据存入laData
		}
	}

	/**
	 * 账号模块处理主账号高级检索关键字。
	 */
	protected void getMainAdvSearchHql(StringBuffer hql, Map map) {
		if (accountName != null && !accountName.isEmpty()) {// 按账号名检索
			accountName = accountName.toLowerCase();
			hql.append(" and LOWER(pp.name) like :accountName");
			map.put("accountName", "%" + accountName + "%");
		}
		if (createDate1 != null) {// 账号创建时间查询起点
			hql.append(" and a.startDate > :createDate1");
			map.put("createDate1", createDate1);
		}
		if (createDate2 != null) {// 账号创建时间查询终点
			hql.append(" and a.startDate < :createDate2");
			map.put("createDate2", createDate2);
		}
		if (expireDate1 != null) {// 账号有效期查询起点
			hql.append(" and a.expireDate > :expireDate1");
			map.put("expireDate1", expireDate1);
		}
		if (expireDate2 != null) {// 账号有效期查询终点
			hql.append(" and a.expireDate < :expireDate2");
			map.put("expireDate2", expireDate2);
		}
		if (status == 1) {// 按账号状态检索
			hql.append(" and a.status = 1");
		} else if (status == 0) {
			hql.append(" and a.status = 0");
		}
	}
	
	/**
	 * 账号模块处理子账号高级检索关键字。
	 */
	protected void getSubAdvSearchHql(StringBuffer hql, Map map) {
		if (accountName != null && !accountName.isEmpty()) {// 按账号名检索
			accountName = accountName.toLowerCase();
			hql.append(" and LOWER(pp.name) like :accountName");
			map.put("accountName", "%" + accountName + "%");
		}
		if (belongPersonName != null && !belongPersonName.isEmpty()) {// 按账号名检索
			belongPersonName =belongPersonName.toLowerCase();
			hql.append(" and LOWER(p.name) like :belongPersonName");
			map.put("belongPersonName", "%" + belongPersonName + "%");
		}
		if (createDate1 != null) {// 账号创建时间查询起点
			hql.append(" and a.startDate > :createDate1");
			map.put("createDate1", createDate1);
		}
		if (createDate2 != null) {// 账号创建时间查询终点
			hql.append(" and a.startDate < :createDate2");
			map.put("createDate2", createDate2);
		}
		if (expireDate1 != null) {// 账号有效期查询起点
			hql.append(" and a.expireDate > :expireDate1");
			map.put("expireDate1", expireDate1);
		}
		if (expireDate2 != null) {// 账号有效期查询终点
			hql.append(" and a.expireDate < :expireDate2");
			map.put("expireDate2", expireDate2);
		}
		if (status == 1) {// 按账号状态检索
			hql.append(" and a.status = 1");
		} else if (status == 0) {
			hql.append(" and a.status = 0");
		}
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		if (null != accountName && !accountName.isEmpty()) {
			searchQuery.put("accountName", accountName);
		}
		if (null != belongUnitName && !belongUnitName.isEmpty()) {
			searchQuery.put("belongUnitName", belongUnitName);
		}
		if (null != belongPersonName && !belongPersonName.isEmpty()) {
			searchQuery.put("belongPersonName", belongPersonName);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != createDate1) {
			searchQuery.put("createDate1", df.format(createDate1));
		}
		if (null != createDate2) {
			searchQuery.put("createDate2", df.format(createDate2));
		}
		if (null != expireDate1) {
			searchQuery.put("expireDate1", df.format(expireDate1));
		}
		if (null != expireDate2) {
			searchQuery.put("expireDate2", df.format(expireDate2));
		}
		if (status != -1 ) {
			searchQuery.put("status", status);
		}
	}
	/**
	 * 进入启用页面
	 */
	public String toEnable() {
		return SUCCESS;
	}

	/**
	 * 启用账号
	 */
	@Transactional
	public String enable() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		/**
		 * 1、根据参数获取账号。
		 * 2、判断账号是否存在，级别以及类别是否正确。
		 * 3、判断账号所属是否在当前账号管辖范围。
		 * 4、启用账号。
		 */
		for (int i = 0; i < entityIds.size(); i++) {// 校验账号合法性
			account = (Account) dao.query(Account.class, entityIds.get(i));// 获取指定账号
			passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
			if (account == null || (account.getType() != accountType && account.getType().equals(AccountType.LOCAL_UNIVERSITY) && !accountType.equals(AccountType.MINISTRY_UNIVERSITY)) || account.getIsPrincipal() != accountPrincipal) {// 查询出的数据异常，返回错误提示
				jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
				return INPUT;
			} else {// 该账号数据正常，判断该账号是否属于管辖范围
				if (!accountService.checkIfUnderControl(loginer, rangeId(accountService.getBelongIdByAccount(account), accountType),
						accountService.getEntityIdType(accountType, accountPrincipal), false)) {// 该账号不在管辖范围，返回错误提示
					jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ENABLE_DENY + passport.getName());
					return INPUT;
				}
			}
		}
		accountService.enable(entityIds, validity);// 调用启用账号方法
		return SUCCESS;
	}

	/**
	 * 启用账号校验
	 */
	public String validateEnable() {
		boolean flag = false;
		if (entityIds == null || entityIds.isEmpty()) {// 账号ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ENABLE_NULL);
			flag = true;
		}
		if (validity == null) {// 账号有效期不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_EXPIREDATE_NULL);
			flag = true;
		} else if (validity.compareTo(new Date()) <= 0) {// 有效期必须在当前系统时间之后
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_EXPIREDATE_ILLEGAL);
			flag = true;
		}
		return (flag == true ? INPUT : null);
	}

	/**
	 * 停用账号
	 */
	@Transactional
	public String disable() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		/**
		 * 1、根据参数获取账号。
		 * 2、判断账号是否存在，级别以及类别是否正确。
		 * 3、判断账号所属是否在当前账号管辖范围。
		 * 4、停用账号。
		 */
		for (int i = 0; i < entityIds.size(); i++) {// 校验账号合法性
			account = (Account) dao.query(Account.class, entityIds.get(i));// 获取指定账号
			passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
			if (account == null || (account.getType() != accountType && account.getType().equals(AccountType.LOCAL_UNIVERSITY) && !accountType.equals(AccountType.MINISTRY_UNIVERSITY)) || account.getIsPrincipal() != accountPrincipal) {// 查询出的数据异常，返回错误提示
				jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
				return INPUT;
			} else {// 该账号数据正常，判断该账号是否属于管辖范围
				if (!accountService.checkIfUnderControl(loginer, rangeId(accountService.getBelongIdByAccount(account), accountType),
						accountService.getEntityIdType(accountType, accountPrincipal), false)) {// 该账号不在管辖范围，返回错误提示
					jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_DISABLE_DENY + passport.getName());
					return INPUT;
				}
			}
		}
		accountService.disable(entityIds);// 调用停用账号方法
		return SUCCESS;
	}

	/**
	 * 停用账号校验
	 */
	public String validateDisable() {
		if (entityIds == null || entityIds.isEmpty()) {// 账号ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_DISABLE_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 进入分配角色页面，并加载可分配角色信息。
	 */
	public String toAssignRole() {
		/**
		 * 1、根据参数获取第一个账号。
		 * 2、获取该账号的级别和类别。
		 * 3、遍历所有账号，判断级别和类别是否一致。
		 * 4、查询可分配角色信息。
		 */
		//List<String> toAssignAccountIds = new ArrayList(Arrays.asList(assignRoleIds.split(",")));
		String[] toAssignAccountIds = assignRoleIds.split(",");// 批量分配角色时，待分配角色的ID以","分隔
		List<String> accountIds =  Arrays.asList(toAssignAccountIds);
		account = (Account) dao.query(Account.class, toAssignAccountIds[0]);// 获取指定账号
		AccountType tmpType;// 用于判断账号级别是否一致
		int tmpMain = -1;// 用于判断账号类别是否一致
		if (account == null) {// 账号不存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
			return INPUT;
		} else {// 账号存在，记录该账号的级别和类别
			tmpType = account.getType();
			tmpMain = account.getIsPrincipal();
		}
		// 遍历待分配角色的所有账号，判断账号级别和类别是否一致
		for (int i = 1; i < toAssignAccountIds.length; i++) {
			account = (Account) dao.query(Account.class, toAssignAccountIds[i]);// 查询指定账号
			if (account == null) {// 账号不存在，返回错误提示
				this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
				return INPUT;
			} else {// 账户存在，判断级别和类别是否一致
				if (account.getType() != tmpType || account.getIsPrincipal() != tmpMain) {// 如果类型不符，返回错误提示
					this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NOT_CONSISTENT);
					return INPUT;
				}
			}
		}
		List<Role>[] tmpRole = accountService.getAssignRole(account, type, loginer, accountIds);// 获取待分配角色和已分配角色列表
		request.setAttribute("disroles", tmpRole[0]);
		request.setAttribute("roles", tmpRole[1]);
		return SUCCESS;
	}

	/**
	 * 进入角色分配页面校验(页面传参type)
	 */
	public String validateToAssignRole() {
		// 无论从查看页面，还是列表页面进行角色分配，都要传账号ID过来，
		// 后台需要根据此账号类别判断此时进行何种账号的角色分配，以便加载相应类别的角色
		if (assignRoleIds == null || assignRoleIds.isEmpty() || assignRoleIds.split(",") == null) {
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_DISTRIC_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 分配角色。
	 */
	@Transactional
	public String assignRole() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		/**
		 * 1、根据参数获取账号。
		 * 2、判断账号是否存在，级别类别是否正确。
		 * 3、判断账号所属是否在当前账号管辖范围。
		 * 4、分配角色。
		 */
		String[] tmpids = null;// 待分配的角色
		if (!roleIds.isEmpty()) {// 待分配角色ID以","分隔，此处将其转化为数组
			tmpids = roleIds.split(",");
		}
		
		for (int i = 0; i < entityIds.size(); i++) {// 校验账号合法性
			account = (Account) dao.query(Account.class, entityIds.get(i));// 获取指定账号
			passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
			if (account == null || (!account.getType().equals(accountType) && account.getType().equals(AccountType.LOCAL_UNIVERSITY) && !accountType.equals(AccountType.MINISTRY_UNIVERSITY)) || account.getIsPrincipal() != accountPrincipal) {// 查询出的数据异常，返回错误提示
				jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
				return INPUT;
			} else {// 该账号数据正常，判断该账号是否属于管辖范围
				if (!accountService.checkIfUnderControl(loginer, rangeId(accountService.getBelongIdByAccount(account), accountType),
						accountService.getEntityIdType(accountType, accountPrincipal), false)) {// 该账号不在管辖范围，返回错误提示
					jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_DISABLE_DENY + passport.getName());
					return INPUT;
				}
			}
		}
		accountService.assignRole(loginer, entityIds, tmpids, type);// 分配角色
		return SUCCESS;
	}

	/**
	 * 分配角色校验，账号角色可以为空
	 */
	public String validateAssignRole() {
		if (entityIds == null || entityIds.isEmpty()) {// 账号ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_DISTRIC_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 生成密码重置码。
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String retrieveCode() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		/**
		 * 1、根据参数获取账号。
		 * 2、判断账号是否存在，级别及类别是否正确。
		 * 3、判断账号所属是否在当前账号管辖范围。
		 * 4、生成密码重置码，并以邮件形式通知用户。
		 */
		account = (Account) dao.query(Account.class, entityId);// 获取指定账号
//		final String mailId = null;
		if (account == null || (account.getType() != accountType && account.getType().equals(AccountType.LOCAL_UNIVERSITY) && !accountType.equals(AccountType.MINISTRY_UNIVERSITY)) || account.getIsPrincipal() != accountPrincipal) {// 查询出的数据异常，返回错误提示
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
			return INPUT;
		} else {// 该账号数据正常，判断该账号是否属于管辖范围
			if (!accountService.checkIfUnderControl(loginer, rangeId(accountService.getBelongIdByAccount(account), accountType),
				accountService.getEntityIdType(accountType, accountPrincipal), false)) {// 该账号不在管辖范围，返回错误提示
				jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_RETRIEVE_DENY);
				return INPUT;
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
	}
	

	/**
	 * 密码重置校验
	 */
	public void validateRetrieveCode() {
		if (entityId == null || entityId.isEmpty()) {// 账号ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_RETRIEVE_NULL);
		}
	}

	/**
	 * 进入修改密码页面。
	 */
	public String toModifyPassword() {
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		/**
		 * 1、根据参数获取账号。
		 * 2、判断账号是否存在，级别及类别是否正确。
		 * 3、判断账号所属是否在当前账号管辖范围。
		 * 4、将待修改密码的账号ID放入session缓存。
		 */
		account = (Account) dao.query(Account.class, entityId);// 获取指定账号
		if (account == null || (account.getType() != accountType && account.getType().equals(AccountType.LOCAL_UNIVERSITY) && !accountType.equals(AccountType.MINISTRY_UNIVERSITY)) || account.getIsPrincipal() != accountPrincipal) {// 查询出的数据异常，返回错误提示
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_NULL);
			return INPUT;
		} else {// 该账号数据正常，判断该账号是否属于管辖范围
			if (!accountService.checkIfUnderControl(loginer, rangeId(accountService.getBelongIdByAccount(account), accountType),
					accountService.getEntityIdType(accountType, accountPrincipal), false)) {// 该账号不在管辖范围，返回错误提示
				jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_MODIFY_DENY);
				return INPUT;
			}
			ActionContext.getContext().getSession().put(TMP_ACCOUNT_ID, entityId);// 缓存账号ID到session
			return SUCCESS;
		}
	}
	
	/**
	 * 进入修改密码校验
	 */
	public void validateToModifyPassword() {
		if (entityId == null || entityId.isEmpty()) {// 账号ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_MODIFY_NULL);
		}
	}

	/**
	 * 修改密码。
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
		account = (Account) dao.query(Account.class, (String) session.get(TMP_ACCOUNT_ID));// 获取指定账号
		passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
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
	 * 修改密码校验
	 */
	public void validateModifyPassword() {
		if (newPassword == null || newPassword.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NEW_PASSWORD_NULL);
		} else if (rePassword == null || rePassword.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_REPASSWORD_NULL);
		} else if (!newPassword.equals(rePassword)) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_REPASSWORD_ILLEGAL);
		}
	}
	
	/**
	 * 弹出层添加账号
	 */
	public String extIfToAdd(){
		AccountType accountType = getSubClassType();// 账号级别
		int accountPrincipal = getSubClassPrincipal();// 账号类别
		passport = accountService.getPassportByBelongId(belongId, accountType, accountPrincipal);
		if (passport != null) {
			flag = 1;
		}
		return SUCCESS;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String extIfAdd(){
		//后台校验，方便获取返回信息
		if (passport.getName() == null || passport.getName().equals("")) {
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NAME_NULL);
			return INPUT;
			
		} else if (!Pattern.matches("\\w{3,40}", passport.getName())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NAME_ILLEGAL);
			return INPUT;
		}
		if (account.getStatus() != 0 && account.getStatus() != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_STATUS_ILLEGAL);
			return INPUT;
		}
		if (account.getExpireDate() == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_EXPIREDATE_NULL);
			return INPUT;
		} else if (account.getExpireDate().compareTo(new Date()) <= 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_EXPIREDATE_ILLEGAL);
			return INPUT;
		}
		if (belongEntityId == null || belongEntityId.isEmpty()) {
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_BELONG_NULL);
			return INPUT;
		}
		
		AccountType accountType = getSubClassType();// 账号级别
		int isPrincipal = getSubClassPrincipal();// 账号类别
		account.setType(accountType);
		account.setIsPrincipal(isPrincipal);
		passport.setPasswordWarning(1);
		passport.setMaxSession(Integer.parseInt((String)ActionContext.getContext().getApplication().get("maxSession")));
		if (accountService.checkIfUnderControl(loginer, belongEntityId,
				accountService.getEntityIdType(accountType, isPrincipal), false)) {// 判断实体是否存在或在管理范围
			if (accountService.checkOwnAccount(belongEntityId, accountType,isPrincipal)) {// 判断此实体是否已有账号
				jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_ACCOUNT_EXIST);
				return INPUT;
			} else {
				if (flag != 1 && accountService.checkAccountName(passport.getName())) {// 检测账号名是否存在
					jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_NAME_EXIST);
					return INPUT;
				} else {
					txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
						protected void doInTransactionWithoutResult(TransactionStatus status) {
							try {
								jsonMap = accountService.addAccount(belongEntityId, account, passport, loginer);// 添加账号
								entityId = (String) jsonMap.get("id");
								mail = (Mail) jsonMap.get("mail");
							} catch (Exception e) {
								status.setRollbackOnly();
							} 
						}
					});
//					entityId = accountService.addAccount(account, passport, loginer);// 添加账号
					mailController.send(mail.getId());
					jsonMap.put("accountId",entityId);
					jsonMap.put("accountName",accountName);
					return SUCCESS;
				}
			}
		} else {
			jsonMap.put(GlobalInfo.ERROR_INFO, AccountInfo.ERROR_BELONG_ILLEGAL);
			return INPUT;
		}
	}
	
	public String universityJSON() {
		term = term.trim().toLowerCase();
		laData = new ArrayList();
		Map parMap = new HashMap();
		parMap.put("term", "%" + term + "%");
		List list = dao.query("select a.id, a.name, a.acronym from Agency a where (a.type = 3 or a.type = 4) and (LOWER(a.acronym) like :term or a.name like :term)", parMap);
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tmpMap = new HashMap();
				tmpMap.put("id",((Object[])list.get(i))[0].toString());
				tmpMap.put("label",((Object[])list.get(i))[1].toString());
				tmpMap.put("value",((Object[])list.get(i))[1].toString());
				laData.add(tmpMap);
			}
		}
		return SUCCESS;
	}
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Passport getPassport() {
		return passport;
	}
	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	public String getBelongId() {
		return belongId;
	}
	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
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
	public IAccountService getAccountService() {
		return accountService;
	}
	public String getPwRetrieveCode() {
		return pwRetrieveCode;
	}
	public void setPwRetrieveCode(String pwRetrieveCode) {
		this.pwRetrieveCode = pwRetrieveCode;
	}
	public Date getValidity() {
		return validity;
	}
	public void setValidity(Date validity) {
		this.validity = validity;
	}
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBelongEntityId() {
		return belongEntityId;
	}
	public void setBelongEntityId(String belongEntityId) {
		this.belongEntityId = belongEntityId;
	}
	public String getBelongUnitName() {
		return belongUnitName;
	}
	public void setBelongUnitName(String belongUnitName) {
		this.belongUnitName = belongUnitName;
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
	public String getBelongEntityName() {
		return belongEntityName;
	}
	public void setBelongEntityName(String belongEntityName) {
		this.belongEntityName = belongEntityName;
	}
	public String getAssignRoleIds() {
		return assignRoleIds;
	}
	public void setAssignRoleIds(String assignRoleIds) {
		this.assignRoleIds = assignRoleIds;
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
	public InputValidate getInputValidate() {
		return inputValidate;
	}
	public void setInputValidate(InputValidate inputValidate) {
		this.inputValidate = inputValidate;
	}
	public String getBelongPersonName() {
		return belongPersonName;
	}
	public void setBelongPersonName(String belongPersonName) {
		this.belongPersonName = belongPersonName;
	}
	public void setPassportService(IPassportService passportService){
		this.passportService = passportService;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
