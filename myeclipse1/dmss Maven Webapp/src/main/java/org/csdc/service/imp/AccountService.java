package org.csdc.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.captcha.Captcha;

import org.csdc.bean.GlobalInfo;
import org.csdc.domain.sm.SelfspaceModifyForm;
import org.csdc.domain.sm.security.AccountForm;
import org.csdc.domain.system.RegisterForm;
import org.csdc.model.Account;
import org.csdc.model.Category;
import org.csdc.model.Mail;
import org.csdc.model.Person;
import org.csdc.model.Right;
import org.csdc.model.Role;
import org.csdc.tool.DatetimeTool;
import org.csdc.tool.MD5;
import org.csdc.tool.Mailer;
import org.csdc.tool.RequestIP;
import org.csdc.tool.SessionContext;
import org.csdc.tool.SignID;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账号管理业务类
 * @author jintf
 * @date 2014-6-15
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class AccountService extends BaseService   {
	
	/**
	 * 根据账号名获得账号权限
	 * @param accountName 账号名
	 * @return 如果账号不存在，则返回null；
	 * 返回拥有角色所对应的权限。返回结果均无重复项。
	 */
	public List<String> getRightByAccountName(String accountName) {
		if (accountName == null || accountName.isEmpty()) {
			return null;
		} else {
			List<String>  rights=  baseDao.query("select distinct r.code from Account a left join a.roles ro left join ro.rights r  where a.name=? and r.code is not null",accountName);
			return rights;
		}
	}
	
	/**
	 * 根据账号名获得账号权限MAP
	 * @param accountName 账号名
	 * @return 如果账号不存在，则返回null；
	 */
	public Map<String, Boolean> getRightMapByAccountName(String accountName) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		List<String> allRights = baseDao.query("select r.code from Right r");
		List<String> myRights = getRightByAccountName(accountName);
		for(String right:allRights){
			map.put(right, myRights.contains(right));
		}
		return map;
	}
	
	/**
	 * 检测账号名是否存在
	 * @param accountName 账号名
	 * @return true 存在，false 不存在
	 */
	public boolean checkAccountName(String accountName) {
		/**
		 * 如果账号名为空，则直接返回true，因为不允许使用空账号名。
		 * 如果账号名非空，则查询数据库，判断此账号名是否存在。
		 */
		if (accountName == null || accountName.isEmpty()) {
			return true;
		} else {
			Map map = new HashMap();
			map.put("accountName", accountName);
			List<String> re = baseDao.query("select a.id from Account a where a.name = :accountName", map);
			return !re.isEmpty();
		}
	}


	/**
	 * 根据账号名查找账号
	 * @param accountName账号名
	 * @return 账号对象
	 */
	public Account getAccountByAccountName(String accountName) {
		if (accountName == null || accountName.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("accountName", accountName);
			List<Account> re = baseDao.query("from Account a where a.name = :accountName", map);
			return re.isEmpty() ? null : re.get(0);
		}
	}

	
	/**
	 * 根据账号名获取该账号的所有权限
	 * @param accountName 账号名
	 * @return 权限集合
	 */
	public  Set<Right> getAllRights(String accountName){
		Account account = (Account) baseDao.queryUnique("select a from Account a where a.name=?",accountName);
		Set<Right> rights = new HashSet<Right>();
		for (Role role:account.getRoles()) {
			rights.addAll(role.getRights());
		}
		return rights;
	}
	


	/**
	 * 修改账号信息
	 * @param oldAccount原始账号
	 * @param account更新信息
	 * @param passwordWarning 是否登录提示修改密码
	 * @param isAdmin 是否系统管理员
	 * @return 账号ID
	 */
	public String modifyAccount(Account oldAccount, Account account, boolean passwordWarning, int isAdmin) {
		if (oldAccount == null || account == null) {
			return null;
		} else {
			oldAccount.setName(account.getName());// 修改账号名
			oldAccount.setStatus(account.getStatus());// 修改账号状态
			oldAccount.setExpireDate(account.getExpireDate());// 修改有效期
			if (passwordWarning) {// 将是否提示登录修改密码由boolean改为int
				oldAccount.setPasswordWarning(true);
			} else {
				oldAccount.setPasswordWarning(false);
			}
			if (isAdmin == 1) {// 系统管理员账号才能修改以下三个属性
				oldAccount.setAllowedIp(account.getAllowedIp());// 允许登录ip
				oldAccount.setRefusedIp(account.getRefusedIp());// 拒绝登录ip
				oldAccount.setMaxSession(account.getMaxSession());// 最大连接数
			}
			baseDao.modify(oldAccount);
			return oldAccount.getId();
		}
	}

	/**
	 * 查看账号信息
	 * @param account 待查看的账号
	 * @param jsonMap 返回前端的数据
	 * @param isAdmin 当前账号级别
	 * @return jsonMap包含相关数据的map对象
	 */
	public Map viewAccount(Account account, Map jsonMap, int isAdmin) {
		
		// 获得账号角色名称
		String rolename = getRoleName(account.getId());
		if (isAdmin != 1) {// 如果非系统管理员，清除不需要显示的信息
			account.setAllowedIp(null);
			account.setRefusedIp(null);
			account.setLastLoginIp(null);
			account.setLastLoginDate(null);
			account.setLoginCount(0);
			account.setMaxSession(0);
		}
		String[] belongIdName =getAccountBelong(account);
		String belongId = belongIdName[0];
		String belongName = belongIdName[1];
		jsonMap.put("account", account);
		jsonMap.put("belongId", belongId);// 所属人员id
		jsonMap.put("belongName", belongName);// 所属人员姓名
		jsonMap.put("rolename", rolename);
		return jsonMap;
	}
	/**
	 * 根据账号找到人员的id
	 * @param account 账号
	 * @return 人员的id和name
	 * @author 周中坚
	 * @version 2011.04.21
	 */
	public String[] getAccountBelong(Account account) {
		String[] belongIdName = {"", ""};
		Map map = new HashMap();
		map.put("belongId", account.getPerson().getId());
		Person person = (Person) baseDao.query("select p from Person p where p.id = :belongId", map).get(0);
		belongIdName[0] = person.getId();
		belongIdName[1] = person.getName();
		return belongIdName;
	}
	
	/**
	 * 根据账号id获得其角色名称，并组成一个以中文逗号隔开的字符串
	 * @param accountId 账号ID
	 * @return 角色名称字符串
	 */
	public String getRoleName(String accountId) {
		String rolename = "";
		if (accountId != null && !accountId.isEmpty()) {
			Map map = new HashMap();
			map.put("accountId", accountId);
			List<String> roleList = baseDao.query("select r.name from Role r, AccountRole ar where r.id = ar.role.id and ar.account.id = :accountId order by r.name asc", map);
			for (int i = 0; i < roleList.size(); i++) {
				rolename += roleList.get(i) + "，";
			}
			if (!rolename.equals("")) {
				rolename = rolename.substring(0, rolename.length() - 1);
			}
		}
		return rolename;
	}

	/**
	 * 删除账号
	 * @param accountids 待删除账号ID集合
	 */
	public void deleteAccount(List<String> accountIds) {
		if (accountIds != null && !accountIds.isEmpty()) {
			Account account;// 账号对象
			
			for (String id : accountIds) {// 遍历账号ID集合
				account = (Account) baseDao.query(Account.class, id);
				baseDao.delete(account);
			}
		}
	}

	/**
	 * 启用账号
	 * @param ids账号ID集合
	 */
	public void enable(List<String> ids, Date date) {
		Account account;
		for (int i = 0; i < ids.size(); i++) {
			account = (Account) baseDao.query(Account.class, ids.get(i));
			account.setExpireDate(date);
			account.setStatus(1);
			baseDao.modify(account);
		}
	}

	/**
	 * 停用账号
	 * @param ids账号ID集合
	 */
	public void disable(List<String> ids) {
		Account account;
		for (int i = 0; i < ids.size(); i++) {
			account = (Account) baseDao.query(Account.class, ids.get(i));
			account.setStatus(0);
			baseDao.modify(account);
		}
	}
	
	/**
	 * 根据账号ID获取账号的所有权限
	 * @param accountId 账号ID
	 * @return 权限集合
	 */
	public Set<Role> getAccountRoles(String accountId){
		Account account = baseDao.query(Account.class,accountId);
		Hibernate.initialize(account.getRoles());
		return account.getRoles();
	}
	
	/**
	 * 分配角色
	 */
	public void assignRole(Account account,String roleIdString) {
		Set<Role> roles = new HashSet<Role>();
		String [] roleIds = roleIdString.split(",");
		for (String id:roleIds) {
			roles.add( baseDao.query(Role.class,id));
		}
		account.setRoles(roles);
	}

	/**
	 * 根据传过来的account对象，查找相应子表中的email
	 * @param account 待查找email的account对象
	 * @return email(若为空，则返回"")
	 */
	public String getEmailByAccount(Account account) {
		if (account == null) {
			return "";
		} else {
			Person person = (Person) baseDao.query(Person.class, account.getPerson().getId());
			if(person.getEmail() != null && !person.getEmail().equals("")){
				return person.getEmail();
			} else {
				return "";
			}
		}
	}
	
	/**
	 * 邮件通知
	 * @param account 待发邮件账号
	 * @param currentId 当前账号ID
	 * @param subject 邮件主题
	 * @param body 邮件正文
	 * @param isHtml 是否页面
	 */
	private void email(Account account, String currentId, String subject, String body, int isHtml) {
		String email = getEmailByAccount(account);
		Mail mail = new Mail();
		mail.setSubject(subject);
		mail.setBody(body);
		mail.setIsHtml(true);
		mail.setCreatedDate(new Date());
		mail.setSendto(email);
		mail.setFinishedDate(null);// 完成时间
		mail.setSended(null);
		mail.setSendTimes(0);
		mail.setStatus(0);
		/*if (currentId != null) {
			mail.setAccountId(currentId);
		}*/
		baseDao.add(mail);// 添加邮件
		try {
			Mailer.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重置密码，并邮件通知
	 * @param account 待处理账号
	 * @param currentId 当前账号ID
	 * @param path 邮件链接地址
	 */
	public void retrieveCode(Account account, String currentId, String path) {
		String passwordRetrieveCode = MD5.getMD5(SignID.getRandomString(4));// 生成重置码,保存到数据库
		account.setRetrivePasswordStartDate(new Date());// 记录重置码生成时间
		baseDao.modify(account);
		String url = path + "selfspace/toResetPassword.action?entityId=" + account.getId() + "&pwRetrieveCode=" + passwordRetrieveCode;
		String body = "欢迎您访问激光二维码质量跟踪系统！" + 
					  "<br /><br />" +
					  "请点击以下链接进行密码重置，该链接24小时之内有效：" +
					  "<br />" +
					  "<a href=\"" + url + "\">" + url + "</a>" +
					  "<br /><br />" +
					  "如果该链接无法点击，请复制前面的链接地址到浏览器直接访问。" +
					  "<br />" +
					  "如果您没有进行过找密码操作，请忽略此邮件。";
		email(account, currentId, "重置密码" + account.getName(), body, 1);
	}
	
	/**
	 * 修改账号密码
	 * @param account 待处理账号
	 * @param password 新密码
	 */
	public void modifyPassword(Account account, String password) {
		account.setPassword(MD5.getMD5(password));// 将密码加密后存储
		baseDao.modify(account);
	}
	
	/**
	 * 获取账号登陆状态
	 * @param request
	 * @return
	 */
	public int getAccountAuthenticationStatus(HttpServletRequest request) {
		// 获得请求的参数
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		String code = request.getParameter("j_code");
				
		if (username == null || username.trim().isEmpty()) {
			return 1;// 用户名为空
		}
		
		if (password == null || password.isEmpty()) {
			return 2;// 密码为空
		}
		
		/*if (code == null || code.trim().isEmpty()) {
			return 3;// 验证码为空
		}
		
		String random = (String) request.getSession().getAttribute(Captcha.NAME);
		if (random == null || random.isEmpty() || !code.trim().equals(random)) {
			return 4;// 验证码错误
		}	*/
			
		Account account = this.getAccountByAccountName(username);
		request.getSession().setAttribute(GlobalInfo.ACCOUNT_BUFFER, account);//将账户存入会话中
		String md5Password = MD5.getMD5(password);
				
		if (account == null || !md5Password.equals(account.getPassword())) {
			return 5;// 用户名或者密码错误
		}
		
		if (account.getStatus() == 0) {
			return 6;// 该用户已停用
		} else {
			Date currenttime = new Date();
						
			if (currenttime.after(account.getExpireDate())) {
				account.setStatus(0);
				baseDao.modify(account);
				return 6;// 该用户已停用
			}
		}
		
		String allowedIpStr = account.getAllowedIp();
		String refusedIpStr = account.getRefusedIp();
		
		boolean allowedPass = true;// 允许登录IP是否通过
		boolean refusedPass = true;// 阻止登录IP是否通过
		
		String judgeIp = RequestIP.getRequestIp(request);// 待判定的IP
		
		if (allowedIpStr != null && !allowedIpStr.isEmpty()) {// 如果存在允许登录IP限制，则进行允许判定
			allowedPass = RequestIP.checkIp(allowedIpStr.split("; "), judgeIp);
		}
		
		if (refusedIpStr != null && !refusedIpStr.isEmpty()) {// 如果存在拒绝登录IP限制，则进行拒绝判定
			refusedPass = !RequestIP.checkIp(refusedIpStr.split("; "), judgeIp);
		}
							
		if (!(allowedPass && refusedPass)) {
			return 7;// 此账号限制了本地IP登录
		}
		
		int account_session = account.getMaxSession();
		//int config_session = Integer.valueOf( Application.getApp().getParameter("MAX_SESSION"));
		int config_session = 5;
		int max_session = (account_session > config_session ? config_session : account_session);// 取系统配置和账号设置中较小的一个
		
		if (max_session == 0) {
			return 8;// 此账号已达到同时在线最大数，无法继续登录
		} else {
			SessionContext myc = SessionContext.getInstance();
			int session_number = myc.getSessionNumber(username);
			if (session_number == max_session) {
				return 8;
			}
		}
		return 0;
	}

	/**
	 * 账号注册
	 * @param registerForm
	 */
	public void register(RegisterForm registerForm){
		Account account = new Account();
		Person person = new Person();
		account.setName(registerForm.getAccountName());
		account.setPassword(MD5.getMD5(registerForm.getPassword()));
		account.setStartDate(new Date());
		Date expire = new Date();
		expire.setYear(expire.getYear()+10);
		account.setExpireDate(expire);
		account.setStatus(1);
		account.setMaxSession(5);
		person.setEmail(registerForm.getEmail());
		person.setName(registerForm.getName());
		person.setMobilePhone(registerForm.getMobilePhone());
		person.setQq(registerForm.getQq());
		person.setIdCard(registerForm.getIdCard());
		String personId =(String)baseDao.add(person);
		//account.setPersonId(personId);
		baseDao.add(account);
	}
	
	/**
	 * 修改个人信息
	 * @param account
	 * @param selfspaceModifyForm
	 */
	public void modifySelfspace(Account account,SelfspaceModifyForm selfspaceModifyForm)	{
		Map map = new HashMap();
		Person person = account.getPerson();
		person.setName(selfspaceModifyForm.getName());
		person.setMobilePhone(selfspaceModifyForm.getMobilePhone());
		person.setEmail(selfspaceModifyForm.getEmail());
		person.setQq(selfspaceModifyForm.getQq());
		person.setAgency(selfspaceModifyForm.getAgency());
		person.setDuty(selfspaceModifyForm.getDuty());
		person.setIdCard(selfspaceModifyForm.getIdCard());
		person.setLastModifiedDate(new Date());
		account.setSecurityQuestion(selfspaceModifyForm.getSecurityQuestion());
		account.setSecurityAnswer(selfspaceModifyForm.getSecurityAnswer());
		baseDao.modify(person);
		baseDao.modify(account);
	}
	
	
	
	/**
	 * 检测原始密码是否错误 
	 * @param  account 当前账户
	 * @param  oldPassword 当前账户原始密码
	 * @return true 正确，false 错误
	 */
	public boolean checkPassword(Account account,String oldPassword){
		return account.getPassword().equals(MD5.getMD5(oldPassword));
	}
	/**
	 * 检测邮箱名是否存在
	 * @param email 邮箱
	 * @return true 存在，false 不存在
	 */
	public boolean checkEmail(String email) {
		/**
		 * 如果邮箱名为空，则直接返回true，因为不允许使用空邮箱名。
		 * 如果账号名非空，则查询数据库，判断此邮箱名是否存在。
		 */
		if (email == null || email.isEmpty()) {
			return true;
		} else {
			Map map = new HashMap();
			map.put("email", email);
			List<String> re = baseDao.query("select a.id from Person a where a.email = :email", map);
			return !re.isEmpty();
		}
	}
	
	/**
	 * 检测身份证号是否可用
	 * @param idCard 身份证号
	 * @return
	 */
	public boolean checkIdCard(String idCard){
		if (idCard == null || idCard.isEmpty()) {
			return false;
		} else {
			Map map = new HashMap();
			map.put("idCard", idCard);
			List<String> re = baseDao.query("select a.id from Person a where a.idCard = :idCard", map);
			return !re.isEmpty();
		}		
	}
	
	/**
	 * 如果用户名为空，则直接返回true，因为不允许使用空用户名。
	 * 如果账号名非空，则查询数据库，判断此用户是否存在，
	 * 若存在，则判断用户是否与当前用户相同，
	 * 若不相同，则返回true。
	 */	
    public boolean checkModifyAccount(Account account,String username){
		if(username == null || username.isEmpty()) {
			return true;
		} else {
			Map map = new HashMap();
			map.put("username", username);
			List<String> re = baseDao.query("select a.name from Account a where a.name = :username", map);
			if(!re.isEmpty())
				if(re.get(0).equals(account.getName())){
					return false;
				}
				else 
					return true;
			else 
				return false;
		}    	
    }
    
	/**
	 * 如果邮箱名为空，则直接返回true，因为不允许使用空邮箱名。
	 * 如果账号名非空，则查询数据库，判断此邮箱名是否存在，
	 * 若存在，则判断邮箱名是否与当前邮箱名相同，
	 * 若不相同，则返回true。
	 */	
	public boolean checkModifyEmail(Person person,String email) {
		if(email == null || email.isEmpty()) 
			return true;
		List<String> re = baseDao.query("select a.email from Person a where a.email =? and a.id !=?", email,person.getId());
		if(!re.isEmpty())
				return true;
		return false;		
	}

	/**
	 * 如果身份证为空，则直接返回true，因为不允许使用空身份证。
	 * 如果身份证非空，则查询数据库，判断此身份证是否存在，
	 * 若存在，则判断身份证是否与当前身份证相同，
	 * 若不相同，则返回true。
	 */	
	public boolean checkModifyIdCard(Person person,String idCard) {
		if(idCard == null || idCard.isEmpty()) 
			return true;
		List<String> re = baseDao.query("select a.idCard from Person a where a.idCard = ? and a.id!=?", idCard,person.getId());
		if(!re.isEmpty()) 
				return true;
		return false;	
	}
	
	/**
	 * 验证授权码是否正确
	 * @param request
	 * @return
	 */
	public boolean getCodeAuthentication(HttpServletRequest request) {
		String code = request.getParameter("code");
		String random = (String) request.getSession().getAttribute(Captcha.NAME);
		if (random == null || random.isEmpty() || !code.trim().equals(random)) {
			return true ;
		}
		else {
			return false ;
		}
	}
	
	/**
	 * 修改账号信息
	 * @param form
	 */
	public void addOrModifyAccount(AccountForm form){
		Account account = new Account();		
		account.setPassword(MD5.getMD5("123456"));
		Person person = new Person();
		if(null !=form.getId() && !form.getId().isEmpty()){ //修改时id不为空
			account = baseDao.query(Account.class,form.getId());
			person = account.getPerson();
		}else{  //添加时要添加我的空间
			Category category = new Category();
			category.setName("我的工作区");
			category.setDescription("我的工作区");
			category.setLastModifiedDate(new Date());
			category.setCreator(account);
			category.setDocCount(0);
			category.setParent(baseDao.query(Category.class,"root"));
			category.setWeight(0);
			category.setAccount(account);
			account.setCategory(category);
		}
		person.setName(form.getName());
		person.setMobilePhone(form.getMobilePhone());
		person.setEmail(form.getEmail());
		person.setQq(form.getQq());
		person.setAgency(form.getAgency());
		person.setDuty(form.getDuty());
		person.setIdCard(form.getIdCard());
		account.setPerson(person);		
		account.setName(form.getAccountName());
		account.setStatus(form.getStatus());	
		account.setStartDate(DatetimeTool.parseExtjsDate(form.getStartDate())); //日期取回数据暂时出现问题，先用默认值代替
		account.setExpireDate(DatetimeTool.parseExtjsDate(form.getExpireDate()));		
		account.setMaxSession(Integer.valueOf(form.getMaxSession()));
		account.setLastLoginIp(form.getLastLoginIp());
		if(null != form.getLastLoginDate())
			account.setLastLoginDate(DatetimeTool.parseExtjsDate(form.getLastLoginDate()));
		if(null != form.getLastModifiedDate())
			account.setLastLoginDate(DatetimeTool.parseExtjsDate(form.getLastModifiedDate()));
		account.setAllowedIp(form.getAllowedIp());
		account.setRefusedIp(form.getRefusedIp());
		account.setTheme(form.getTheme());
		account.setLoginCount(form.getLoginCount());
		account.setPasswordWarning(form.getPasswordWarning());
		account.setSecurityQuestion(form.getSecurityQuestion());
		account.setSecurityAnswer(form.getSecurityAnswer());
		//处理角色
		if(!form.getRoleIds().isEmpty()){
			String[] roleIds = form.getRoleIds().split(",");	
			Set<Role> roles = baseDao.query(Role.class,roleIds);
			account.setRoles(roles);
		}
		person.setLastModifiedDate(new Date());
		baseDao.addOrModify(account);
	}
	
	/**
	 * 删除账号
	 * @param id
	 */
	public void deleteAccount(String id){
		baseDao.delete(Account.class,id);
	}
	
	/**
	 * 获取账号表单
	 * @param id 账号ID
	 * @return
	 */
	public AccountForm getAccountForm(String id){
		AccountForm form = new AccountForm();
		Account account = baseDao.query(Account.class,id);
		Person person = account.getPerson();
		form.setId(account.getId());
		form.setAccountName(account.getName());
		form.setAgency(person.getAgency());
		form.setAllowedIp(account.getAllowedIp());
		form.setDuty(person.getDuty());
		form.setEmail(person.getEmail());
		form.setStartDate(DatetimeTool.getDatetimeString(account.getStartDate(),"yyyy-MM-dd"));
		form.setExpireDate(DatetimeTool.getDatetimeString(account.getExpireDate(),"yyyy-MM-dd"));
		form.setLastLoginDate(DatetimeTool.getDatetimeString(account.getLastLoginDate(),"yyyy-MM-dd"));
		form.setLastLoginIp(account.getLastLoginIp());
		form.setLastModifiedDate(DatetimeTool.getDatetimeString(person.getLastModifiedDate(),"yyyy-MM-dd HH:mm:ss"));
		form.setIdCard(person.getIdCard());
		form.setLoginCount(account.getLoginCount());
		form.setMaxSession(account.getMaxSession());
		form.setMobilePhone(person.getMobilePhone());
		form.setName(person.getName());
		form.setPasswordWarning(account.getPasswordWarning());
		form.setPersonId(person.getId());
		form.setQq(person.getQq());
		form.setRefusedIp(account.getRefusedIp());
		form.setSecurityAnswer(account.getSecurityAnswer());
		form.setSecurityQuestion(account.getSecurityQuestion());
		form.setStatus(account.getStatus());
		form.setTheme(account.getTheme());
		return form;
	}
	
	/**
	 * 获取账号拥有角色
	 * @param accountId 账号ID
	 * @return
	 */
	public List getSelectedRoles(String accountId){
		List<Role> roles = baseDao.query("from Role");
    	Set<Role> myRoles = getAccountRoles(accountId);
    	List<Object[]> list = new ArrayList<Object[]>();
    	for(Role role:roles){
    		Object[] o = new Object[4];
    		o[0] = role.getId();
    		o[1] = role.getName();
    		o[2] = role.getDescription();
    		if(myRoles.contains(role)){
    			o[3] = true;
    		}else{
    			o[3] = false;
    		}
    		list.add(o);
    	}
    	return list;
	}
	
}