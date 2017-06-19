package csdc.action.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.SwfuploadAction;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.service.IAccountService;
import csdc.service.IPassportService;
import csdc.tool.RequestIP;
import csdc.tool.SessionContext;
import csdc.tool.SignID;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

/**
 * mobile登陆相关模块
 * @author suwb
 *
 */
public class MobileLoginAction extends MobileAction{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "mobileLoginPage";
	
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IPassportService passportService;
	
	private List<String[]> userList;// 可选的账号信息
	private String accountId;// 账号所属id, 最近一次登录的账号ID；
	private String username;// 用户名

	/**
	 * 描述：登录认证的处理
	 * @return status 认证结果
	 */
	public String login(){
		Map session = ActionContext.getContext().getSession();
		// 获取从mobile端登录的标识，并返回登录认证结果status
		Integer mobileStatus =  (Integer) session.get("mobileStatus");
		//loginStatus登录验证结果和账号类型放入jsonMap传回手机客户端
		int loginStatus = (null != mobileStatus) ? mobileStatus + 1 : 0; //loginStatus = 1 表示成功登录;
		jsonMap.put("loginStatus", loginStatus);
		if(loginStatus!=1){
			return INPUT;
		}else {
			LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
			if (loginer == null) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "账号信息错误");
			}
			Passport passport = loginer.getPassport();
			List<Account> accountList = accountService.getAccountListByName(username);
			if (accountList.size() == 1) {// 只有一个账号
				Account account = accountList.get(0);
				if (account.getStatus() == 0 || passport.getStatus() == 0) {// 该用户已停用，则阻止登录
					jsonMap.put("ifNeedSelectAccount", 1);//是否需要选择账号[1:是;0:否]
				} else {// 该用户未停用，则判断该账号是否有效
					Date currenttime = new Date();
					if (currenttime.after(account.getExpireDate())) {// 如果超过有效期，则阻止登录，并更新账号状态为停用
						account.setStatus(0);
						dao.modify(account);
						passportService.updatePassport(account.getPassport().getId());
						jsonMap.put("ifNeedSelectAccount", 1);//是否需要选择账号[1:是;0:否]
					} else {// 
						jsonMap.put("ifNeedSelectAccount", 0);//是否需要选择账号[1:是;0:否]
						AccountType accountType = loginer.getCurrentType();
						generateAuth(accountType);
					}
				}
			} else {
				jsonMap.put("ifNeedSelectAccount", 1);//是否需要选择账号[1:是;0:否]
			}
		}
		return SUCCESS;
	}

	/**
	 * 通行证验证成功后进入选账号
	 * @return
	 */
	public String toSelectAccount() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Passport passport = null;
//		String username = accountService.securityUsername();
		if(loginer.getIsSuperUser() == 1 && username != null && !username.isEmpty()) {
			passport = accountService.getPassportByName(username);
			// 更新loginer(将此username的passport set进loginer)
			HttpServletRequest request = ServletActionContext.getRequest();
			passport.setLastLoginIp(RequestIP.getRequestIp(request));
			passport.setLastLoginDate(new Date());
			if (null != passport.getLoginCount()) {
				passport.setLoginCount(passport.getLoginCount()+1);
			} else {
				passport.setLoginCount(1);
			}
			dao.modify(passport);
			// new登录信息对象loginer，并设置相应的信息
			loginer.setPassport(passport);
//			// 更新security里的用户上下文对象
//			generateAuth(null, passport);
		} else {
			// 获取当前通过认证的账号名
			username = accountService.securityUsername();
			passport = accountService.getPassportByName(username);
		}
		// 获取当前用户可选账号
		userList = accountService.userInfo(username);//userInfo是List数组，数组三列，分别记录系统中文名称、是否可用、用于选择匹配的字符串
		if(userList.size() == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, "该通行证没有账号可选");
			return INPUT;
		} else {
			boolean flag = false;// 检测是否有可用账号
			for (String[] user : userList) {
				if (user[5].equals("1") && !user[9].equals("0")) {flag = true;}
			}
			if (!flag) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "该通行证没有账号可选");
				return INPUT;
			}
			accountId = passport.getLastLoginAccount();
			List<String[]> accounts = new ArrayList<String[]>();
			for (String[] user : userList) {
				String[] account = new String[4];
				if(user[5].equals("1")){
					for(int i=0;i<4;i++){
						account[i]=user[i+2];
					}
					accounts.add(account);
				}
			}
			jsonMap.put("accounts", accounts);
			jsonMap.put("accountId", accountId);
			return SUCCESS;
		}
	}
	
	public String selectAccount() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		// accountId校验
		List<Account> accountList = accountService.getAccountListByName(accountService.securityUsername());
		
		String newAccountId = "";
		for (Account account : accountList) {// 找到选择的account，设置loginer
			if(account.getId().trim().equals(accountId.trim())) {
				newAccountId = account.getId();
				break;
			}
		}
		Account account = (Account) dao.query(Account.class, newAccountId);// 获取当前登录账号对象
		Passport passport = dao.query(Passport.class, account.getPassport().getId());// 获取当前登录通行证对象
		
		Date currenttime = new Date();
		if (currenttime.after(account.getExpireDate())) {// 如果超过有效期，则阻止登录，并更新账号状态为停用
			account.setStatus(0);
			dao.modify(account);
			passportService.updatePassport(account.getPassport().getId());
		}
		if(account.getStatus() == 0) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "您选择的账号已停用");
			return INPUT;
		} else {
			passport.setLastLoginAccount(newAccountId);//设置最近一次登录选择的账号ID
			dao.modify(passport);
			this.setLoginInfo(loginer, account);
			AccountType accountType =  account.getType();
			generateAuth(accountType);
			return SUCCESS;
		}
	}
	
	/**
	 * 进入切换账号页面
	 * @return
	 */
	public String toSwitchAccount() {
		// 获取当前通过认证的账号名
		String username = accountService.securityUsername();
		List<String[]> accounts = new ArrayList<String[]>();
		userList = accountService.userInfo(username);// 获取当前通行证可选择账号的相关信息
		for (String[] user : userList) {
			String[] account = new String[4];
			if(user[5].equals("1")){
				for(int i=0;i<4;i++){
					account[i]=user[i+2];
				}
				accounts.add(account);
			}	
		}
		jsonMap.put("accounts", accounts);
		return SUCCESS;
	}

	/**
	 * 账号切换
	 * @return
	 */
	public String switchAccount() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		// 获取当前切换的账号对象
		List<Account> accountList = accountService.getAccountListByName(accountService.securityUsername());
		String newAccountId = "";
		for (Account account : accountList) {// 找到选择的account，设置loginer
			if(account.getId().trim().equals(accountId.trim())) {
				newAccountId = account.getId();
				break;
			}
		}
		Account account = (Account) dao.query(Account.class, newAccountId);
		this.generateAuth(account.getType());
		this.setLoginInfo(loginer, account);
		return SUCCESS;
	}

	public String getCurrentAccount(){
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		String accountId = loginer.getCurrentAccountId();
		String username = accountService.securityUsername();
		userList = accountService.userInfo(username);
		for (String[] user : userList) {
			String[] account = new String[4];
			if(user[2].equals(accountId)){
				for(int i=0;i<4;i++){
					account[i]=user[i+2];
				}
				jsonMap.put("account", account);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 退出程序，移除登录账号的session连接数
	 * @return 
	 */
	public String logout(){
		SessionContext myc = SessionContext.getInstance();
		HttpSession mySession = request.getSession();//获得请求的session
		myc.DelSession(mySession);
		return SUCCESS;
	}

	/**
	 * 根据账号类型获取移动端权限
	 * @param accountType
	 */
	public void generateAuth(AccountType accountType){
		switch (accountType) {
		case ADMINISTRATOR://管理员
		case MINISTRY://部级管理人员
			jsonMap.put("statisticStatus", 1);//统计分析权限标志位（1有权限||0无权限）
			jsonMap.put("accountStatus", 1);//个人信息相关标志位（1管理人员||0研究人员）
			jsonMap.put("feeStatus", 1);//经费标志位（1有权限||0无权限）
			break;
		case PROVINCE://省级管理人员
			jsonMap.put("feeStatus", 0);
			jsonMap.put("statisticStatus", 0);
			jsonMap.put("accountStatus", 1);
			break;
		case LOCAL_UNIVERSITY:	
		case MINISTRY_UNIVERSITY://高校管理人员
			jsonMap.put("feeStatus", 1);
			jsonMap.put("statisticStatus", 0);
			jsonMap.put("accountStatus", 1);
			break;
		case DEPARTMENT://院系管理人员	
		case INSTITUTE://基地管理人员
			jsonMap.put("feeStatus", 0);
			jsonMap.put("statisticStatus", 0);
			jsonMap.put("accountStatus", 1);
			break;
		case EXPERT://外部专家
		case TEACHER://教师		
		case STUDENT://学生
			jsonMap.put("feeStatus", 0);
			jsonMap.put("statisticStatus", 0);
			jsonMap.put("accountStatus", 0);
			break;
		}
	}
	
	/**
	 * 将account对象属性设入loginer中
	 * @param loginer
	 * @param account
	 */
	private void setLoginInfo(LoginInfo loginer, Account account) {
		loginer.setAccount(account);// 设置当前登录账号
		AccountType type = account.getType();// 账号级别
		int isPrincipal = account.getIsPrincipal();// 账号类别
		// 设置账号级别
		loginer.setCurrentType(type);
		// 设置账号类别
		loginer.setIsPrincipal(isPrincipal);
		// 设置当前账号ID
		loginer.setCurrentAccountId(account.getId());
		// 设置账号类别信息，用于首页显示，根据数字类别转换为汉字显示
		if (type.equals(AccountType.ADMINISTRATOR)) {
			loginer.setCurrentTypeName("系统管理员账号");
		} else if (type.equals(AccountType.MINISTRY)) {
			loginer.setCurrentTypeName((isPrincipal == 1) ? "部级主账号" : "部级子账号");
		} else if (type.equals(AccountType.PROVINCE)) {
			loginer.setCurrentTypeName((isPrincipal == 1) ? "省级主账号" : "省级子账号");
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY)) {
			loginer.setCurrentTypeName((isPrincipal == 1) ? "部属高校主账号" : "部属高校子账号");
		} else if (type.equals(AccountType.LOCAL_UNIVERSITY)) {
			loginer.setCurrentTypeName((isPrincipal == 1) ? "地方高校主账号" : "地方高校子账号");
		} else if (type.equals(AccountType.DEPARTMENT)) {
			loginer.setCurrentTypeName((isPrincipal == 1) ? "高校院系主账号" : "高校院系子账号");
		} else if (type.equals(AccountType.INSTITUTE)) {
			loginer.setCurrentTypeName((isPrincipal == 1) ? "研究基地主账号" : "研究基地子账号");
		} else if (type.equals(AccountType.EXPERT)) {
			loginer.setCurrentTypeName("外部专家账号");
		} else if (type.equals(AccountType.TEACHER)) {
			loginer.setCurrentTypeName("教师账号");
		} else if (type.equals(AccountType.STUDENT)) {
			loginer.setCurrentTypeName("学生账号");
		} else {
			loginer.setCurrentTypeName("未知类型账号");
		}
		// 设置账号所属
		// 机构主账号设置机构ID、机构名称；
		// 机构子账号设置机构ID、机构名称、人员名称；
		// 外部专家账号设置机构名称、人员名称；
		// 教师、学生账号设置学校ID、学校名称、人员名称
		// 系统管理员账号设置机构名称
		// 其它账号设置机构名称
		String belongId = accountService.getBelongIdByAccount(account);
		if (type.within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)) {// 部级、省级、校级账号，需要查找agency信息
			if (isPrincipal == 1) {// 部级、省级、校级主账号查找机构信息
				Agency agency = dao.query(Agency.class, belongId);
				loginer.setCurrentBelongUnitId(agency.getId());
				loginer.setCurrentBelongUnitName(agency.getName());
				loginer.setCurrentPersonName(null);
				loginer.setAgency(agency);
			} else {// 部级、省级、校级子账号查找机构和人员信息
				Officer officer = accountService.getOfficerByOfficerId(belongId);
				loginer.setCurrentBelongUnitId(officer.getAgency().getId());
				loginer.setCurrentBelongUnitName(officer.getAgency().getName());
				loginer.setCurrentPersonName(officer.getPerson().getName());
				loginer.setOfficer(officer);
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {// 院系账号，需要查找department信息
			if (isPrincipal == 1) {// 院系主账号查找院系信息
				Department department = dao.query(Department.class, belongId);
				loginer.setCurrentBelongUnitId(department.getId());
				loginer.setCurrentBelongUnitName(department.getName());
				loginer.setCurrentPersonName(null);
				loginer.setDepartment(department);
			} else {// 院系子账号查找院系和人员信息
				Officer officer = accountService.getOfficerByOfficerId(belongId);
				loginer.setCurrentBelongUnitId(officer.getDepartment().getId());
				loginer.setCurrentBelongUnitName(officer.getDepartment().getName());
				loginer.setCurrentPersonName(officer.getPerson().getName());
				loginer.setOfficer(officer);
			}
		} else if (type.equals(AccountType.INSTITUTE)) {// 基地账号，需要查找institute信息
			if (isPrincipal == 1) {// 基地主账号
				Institute institute = dao.query(Institute.class, belongId);
				loginer.setCurrentBelongUnitId(institute.getId());
				loginer.setCurrentBelongUnitName(institute.getName());
				loginer.setCurrentPersonName(null);
				loginer.setInstitute(institute);
			} else {// 基地子账号
				Officer officer = accountService.getOfficerByOfficerId(belongId);
				loginer.setCurrentBelongUnitId(officer.getInstitute().getId());
				loginer.setCurrentBelongUnitName(officer.getInstitute().getName());
				loginer.setCurrentPersonName(officer.getPerson().getName());
				loginer.setOfficer(officer);
			}
		} else if (type.equals(AccountType.EXPERT)) {// 外部专家账号，需要查找人员信息
			Expert expert = accountService.getExpertByPersonId(belongId);
			loginer.setCurrentBelongUnitName(expert.getAgencyName());
			loginer.setCurrentPersonName(expert.getPerson().getName());
			loginer.setPerson(expert.getPerson());
		} else if (type.equals(AccountType.TEACHER)) {// 教师账号，需要查找学校和人员信息
			Teacher teacher = accountService.getTeacherByPersonId(belongId);
			loginer.setCurrentBelongUnitId(teacher.getUniversity().getId());
			loginer.setCurrentBelongUnitName(teacher.getUniversity().getName());
			loginer.setCurrentPersonName(teacher.getPerson().getName());
			loginer.setPerson(teacher.getPerson());
		} else if (type.equals(AccountType.STUDENT)) {// 学生账号，需要查找学校和人员信息
			Student student = accountService.getStudentByPersonId(belongId);
			loginer.setCurrentBelongUnitId(student.getUniversity().getId());
			loginer.setCurrentBelongUnitName(student.getUniversity().getName());
			loginer.setCurrentPersonName(student.getPerson().getName());
			loginer.setPerson(student.getPerson());
		} else if (type.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号提示为"系统管理员"
			loginer.setCurrentBelongUnitName("系统管理员");
			loginer.setCurrentPersonName("系统管理员");
			Person person = dao.query(Person.class, belongId);
			loginer.setPerson(person);
		} else {// 其它账号类型统一设置为"未知账号"
			loginer.setCurrentBelongUnitName("未知所属");
		}
		
		// 将当前登录信息对象loginer存入session
		ActionContext.getContext().getSession().put(GlobalInfo.LOGINER, loginer);
		
		// 更新账号上次登录时间、登录次数信息
		HttpServletRequest request = ServletActionContext.getRequest();
		account.setLastLoginDate(new Date());
		account.setLoginCount(account.getLoginCount() + 1);
		dao.modify(account);
		passportService.updatePassport(account.getPassport().getId());
		
		// 生成用于异步上传的文件夹标识，并存入session保存
		HttpSession session = request.getSession();
		String uploadKey = SignID.getRandomString(20);
		session.setAttribute("uploadKey", uploadKey);
		SwfuploadAction.addKeySessionPair(uploadKey, session.getId());
	}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String pageName() {
		return PAGENAME;
	}
}
