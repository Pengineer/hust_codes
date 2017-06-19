package csdc.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import oracle.net.aso.s;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

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
import csdc.dao.IHibernateBaseDao;
import csdc.service.IAccountService;
import csdc.service.IBusinessService;
import csdc.service.IEntrustService;
import csdc.service.IGeneralService;
import csdc.service.IInstpService;
import csdc.service.IKeyService;
import csdc.service.IPassportService;
import csdc.service.IPostService;
import csdc.service.IViewService;
import csdc.service.imp.BaseService;
import csdc.tool.MD5;
import csdc.tool.RequestIP;
import csdc.tool.SignID;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 通过security认证后，进入系统登录后首页之前，将用户
 * 信息存入session、初始化首页相关信息。
 * @author 龚凡
 * @version 2011.04.08
 */
@SuppressWarnings("unchecked")
public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1430425486077928672L;
	@Autowired
	protected IHibernateBaseDao dao;
	
	private IAccountService accountService;// 账号管理接口
	private IGeneralService generalService;// 一般项目管理接口
	private IKeyService keyService;// 基地项目管理接口
	private IInstpService instpService;// 基地项目管理接口
	private IPostService postService;// 后期资助项目管理接口
	private IEntrustService entrustService;// 后期资助项目管理接口
	private IViewService viewService;// 接口
	private IBusinessService businessService;//业务管理接口
	private IPassportService passportService;
	private int type;
	private String accountId;// 账号所属id, 最近一次登录的账号ID；
	private String username;// 用户名
	private String serverPath;// 返回的页面路径
	private String serverName;// 选择的系统名称
	private List<String[]> userList;// 登录可选的账号信息
	private List<String[]> serverList;// 页面用到的系统信息
	protected Map jsonMap = new HashMap();// json对象容器

	private List<Object> homeNews;// 热点新闻
	private List<Object> homeNotice;// 通知公告
	private List<Object> homeToDo;// 待办事宜
	private List<Object> homeRemind;// 记事提醒
	private List<Object> businessManagement;//业务管理
	private List<Object> homeChat;//未读消息
	private List<Object> homeApply;//未读消息,好友申请
	
	
//	private List<TeacherProjectBean> teacherProjectBean;// 待处理项目
	
	private List<Map> homeStat;//首页统计图数据
	private List<Object> statReport;//统计分析报告
	private List<Object> personStat;//人员统计
	private List<Object> unitStat;//机构统计
	private List<Object> projectStat;//项目统计
	private List<Object> productReport;//成果统计
	private List<Object> awardStat;//奖励统计
	private static Object message;
	/**
	 * 存储通过验证账号的相关信息，主要包括创建LoginInfo
	 * 实例、更新账号登录信息、缓存当前账号用到的临时文件夹等。
	 */
	@Transactional
	public String doLogin() {
		// 获取当前通过认证的通行证
		username = accountService.securityUsername();
		
		// 获取当前登录通行证对象
		Passport passport = accountService.getPassportByName(username);
		HttpServletRequest request = ServletActionContext.getRequest();
		passport.setLastLoginIp(RequestIP.getRequestIp(request));
		passport.setLastLoginDate(new Date());
		if (null != passport.getLoginCount()) {
			passport.setLoginCount(passport.getLoginCount()+1);
		} else {
			passport.setLoginCount(1);
		}
		
		
		// new登录信息对象loginer，并设置相应的信息
		LoginInfo loginer = new LoginInfo();
		loginer.setPassport(passport);
		List<Account> accountList = accountService.getAccountListByName(username);
		for (Account account : accountList) {
			AccountType accountType = account.getType();
			if(accountType.equals(AccountType.ADMINISTRATOR)) {
				loginer.setIsSuperUser(1);
			} else {
				loginer.setIsSuperUser(0);
			}
		}
		
		// 如果只有一个可以选择的账号且在有效期内，则进一步初始化loginer
		if(accountList.size() == 1) {
			Account account = accountList.get(0);
			if(account.getStatus() == 1){
				Date currenttime = new Date();
				if (currenttime.before(account.getExpireDate())) {
					this.setLoginInfo(loginer, accountList.get(0));
				} else {
					account.setStatus(0);
					dao.modify(account);
					passportService.updatePassport(account.getPassport().getId());
					ActionContext.getContext().getSession().put(GlobalInfo.LOGINER, loginer);
				}
			} else {
				ActionContext.getContext().getSession().put(GlobalInfo.LOGINER, loginer);
			}
		} else {
			// 将当前登录信息对象loginer存入session
			ActionContext.getContext().getSession().put(GlobalInfo.LOGINER, loginer);
		}
		/**------------------ mobile登录处理  ------------------*/
		// 获取mobile:iphone||android登录的标识，以及登录认证的标记status
		Map mobileSession = ActionContext.getContext().getSession();
		String mobileLoginTag = (String) mobileSession.get("mobileTag");
		if(null != mobileLoginTag){
			
			try {
				String path = request.getContextPath();
				ServletActionContext.getResponse().sendRedirect(path + "/mobile/login/login.action");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "mobile";
		}//进行mobile:iphone||android登录处理
		/**------------------ mobile登录处理  ------------------*/
		return SUCCESS;
	}

	/**
	 * 账号验证成功之后进入选系统
	 * 选择账号
	 * @return
	 */
	public String toSelectAccount() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Passport passport = null;
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
			// 更新security里的用户上下文对象
			generateAuth(null, passport);
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
			return SUCCESS;
		}	
	}
	
	/**
	 * 切换账号时对输入的通行证进行校验
	 * @return
	 */
	public String ckeckAccount() {
		Passport passport = accountService.getPassportByName(username);
		if (passport != null) {
			userList = accountService.userInfo(username);
			boolean flag = false;// 检测是否有可用账号
			for (String[] user : userList) {
				if (user[5].equals("1") && !user[9].equals("0")) {flag = true;}
			}
			if (!flag) {
				jsonMap.put(GlobalInfo.ERROR_INFO, "该通行证下无可用账号！");
				return INPUT;
			}
			return SUCCESS;
		} else {
//			this.addFieldError(GlobalInfo.ERROR_INFO, "用户名错误！");
			jsonMap.put(GlobalInfo.ERROR_INFO, "用户名错误！");
			return INPUT;
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
		if(account != null) {
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
				return SUCCESS;
			}
		} else {
			this.addFieldError(GlobalInfo.ERROR_INFO, "您选择的账号不合法");
			return INPUT;
		}
	}
	
	/**
	 * 进入切换账号页面
	 */
	public String toSwitchAccount() {
		// 获取当前通过认证的账号名
		String username = accountService.securityUsername();
		
		// 获取当前通行证可选择账号的相关信息
		userList = accountService.userInfo(username);
		return SUCCESS;
	}
	
	public String switchAccount() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		// 获取当前切换的账号对象
		List<Account> accountList = accountService.getAccountListByName(accountService.securityUsername());
		String accountId1 = "";
		for (Account account : accountList) {// 找到选择的account，设置loginer
			if(account.getId().trim().equals(accountId.trim())) {
				accountId1 = account.getId();
				break;
			}
		}
		Account account = (Account) dao.query(Account.class, accountId1);
		// 之前登录的账号
		Account oldAccount = loginer.getAccount();
		Passport passport = loginer.getPassport();
		
		if(account != null) {
			this.generateAuth(account, passport);
			this.setLoginInfo(loginer, account);
			serverList = this.serverInfo(passport.getName());
			
			String serverListString = "";// 当前账号可访问系统
			for (String[] tmp : serverList) {
				if (tmp[1].equals("1")) {// 已完成的系统才参与计数
					serverListString += tmp[2];
				}
			}
			
			if(serverListString.contains(oldAccount.getLastLoginSystem())){// 第一优先：切换前账号上次访问系统
				serverPath = serverPathMap.get(oldAccount.getLastLoginSystem());
			} else if(serverListString.contains(account.getLastLoginSystem())) {// 第二优先：当前账号上次访问系统
				serverPath = serverPathMap.get(account.getLastLoginSystem());
			} else {// 第三优先：可访问的第一个系统
				int serverNum = 0;
				for (String[] tmp : serverList) {
					if (tmp[1].equals("1")) {
						serverNum++;
						serverPath = serverPathMap.get(tmp[2]);
						break;// 找到第一个可访问的系统
					}
				}
				if (serverNum == 0) {// 没有可访问系统，则设置跳转路径为无可访问系统页面
					serverPath = "/server/noServer.jsp";
				} else {
					// 只有一个系统可以访问，则设置跳转路径为该系统的访问页面。由于该系统的访问路径前面已经读取到，此处不做任何处理
				}
				
			}
			return SUCCESS;
		} else {
			this.addFieldError(GlobalInfo.ERROR_INFO, "您选择的账号不合法");
			return INPUT;
		}
	}
	
	/**
	 * 依据通行证和账号，以及账号对应的权限，生成新的Authentication, 代替旧的auth
	 * @param account
	 * @param passport
	 */
	private void generateAuth(Account account, Passport passport) {
		// 读取旧的Authentication
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		GrantedAuthority[] grantedAuthArray;// 账号权限
		List<String> userRight = null;
		if(account != null) {
			userRight = accountService.getRightByAccountId(account.getId());
			grantedAuthArray = new GrantedAuthority[userRight.size()];
			Iterator iterator = userRight.iterator();
			int i = 0;
			while (iterator.hasNext()) {// 遍历用户权限，生成security需要的权限对象GrantedAuthority
				grantedAuthArray[i] = new GrantedAuthorityImpl(((String) iterator.next()).toUpperCase());
				i++;
			}
		} else {
			grantedAuthArray = new GrantedAuthority[1];
			grantedAuthArray[0] = new GrantedAuthorityImpl("NULL");
		}
		
		
		// 重新生成UserDetails
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(passport.getName(), passport.getPassword(), true, true, true, true, grantedAuthArray);
		// 重新生成Authentication
		Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, auth.getCredentials(), grantedAuthArray);
		// 将新的Authentication放入SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		
	}
	/**
	 * 进入选择系统页面
	 */
	public String toSelectServer() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Account account = loginer.getAccount();
		Passport passport = loginer.getPassport();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 获得当前通过认证用户的账号名
		String username;
		if (principal instanceof UserDetails) {// 如果上下文信息为UserDails实例，则调用该接口的getUsername方法获取账号名
			username = ((UserDetails)principal).getUsername();
		} else {// 如果上下文信息不是UserDails实例，则它就是账号名
			username = principal.toString();
		}
		
		this.generateAuth(account, passport);
		
		// 获取当前用户可访问的系统信息
		serverList = this.serverInfo(username);
		// 统计当前账号可登录系统的个数
		int serverNum = 0;
		for (String[] tmp : serverList) {
			if (tmp[1].equals("1")) {// 已完成的系统才参与计数
				serverNum++;
				serverPath = serverPathMap.get(tmp[2]);// 遍历时记录最后一个可访问系统的访问路径
			}
		}
		
		// 根据可进入的系统，确定返回页面
		if (serverNum == 0) {// 没有可访问系统，则设置跳转路径为无可访问系统页面
			serverPath = "/server/noServer.jsp";
		} else if (serverNum == 1) {// 只有一个系统可以访问，则设置跳转路径为该系统的访问页面。由于该系统的访问路径前面已经读取到，此处不做任何处理
		} else {// 有多个系统可进入，则跳转到系统选择页面
			serverPath = "/server/selectServer.jsp";
		}
		
		// 获取当前账号上次登录的系统名称
		if(account != null) {
			serverName = account.getLastLoginSystem();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 进入切换系统页面
	 */
	public String toSwitchServer() {
		// 获取当前通过认证的账号名
		String username = accountService.securityUsername();
		
		// 获取当前用户可访问的系统信息
		serverList = this.serverInfo(username);
		return SUCCESS;
	}

	/**
	 * 切换系统
	 */
	public String switchServer() {
		boolean hasRight = false;// 是否允许访问指定的系统serverName标志
		
		// 根据系统名称获取该系统的访问路径
		serverPath = serverPathMap.get(serverName);
		
		// 获取当前通过认证的账号名
		String username = accountService.securityUsername();
		
		// 获取当前用户可访问的系统信息
		serverList = this.serverInfo(username);
		
		// 遍历当前用户可访问系统列表，判断待访问的系统serverName，是否在当前用户可访问的范围
		for (String[] tmp : serverList) {// 判断是否有权限
			if (tmp[2].equals(serverName)) {// 如果待访问系统在当前用户可访问的范围，则将访问标志置为true，并退出循环
				hasRight = true;
				break;
			}
		}
		
		if (!hasRight) {// 如果不能访问指定的系统serverName，则设置跳转路径为无可访问系统页面
			serverPath = "/server/noServer.jsp";
		} else {// 如果能访问指定的系统serverName，则更新访问的服务器信息到账号表中
			Map session = ActionContext.getContext().getSession();
			LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
			Account account = loginer.getAccount();
			if(account != null) {
				account.setLastLoginSystem(serverName);
				dao.modify(account);
				passportService.updatePassport(account.getPassport().getId());
			}
		}

		return SUCCESS;
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
	
	/**
	 * 系统信息，分别表示显示的中文名、是否可用、用于匹配的字符串
	 */
	private static final String[][] SERVER_INFO = new String[][]{
		{"基础数据库系统", "1", "basis"},
		{"专家数据库系统","0","expert"},
		{"项目管理系统", "1", "project"},
		{"统计分析与决策系统", "1", "statistic"},
		{"用户信息中心", "1", "ucenter"},
		{"系统管控中心", "1", "scenter"},
		{"奖励管理系统", "1", "award"},
		{"经费管理系统", "1", "fee"}
	};

	/**
	 * 系统名称与对应主页面访问路径
	 */
	private static Map<String, String> serverPathMap;
	static {
		serverPathMap = new HashMap<String, String>();
		serverPathMap.put("basis", "/server/basis/main.jsp");
		serverPathMap.put("expert", "/server/expert/main.jsp");
		serverPathMap.put("project", "/server/project/main.jsp");
		serverPathMap.put("statistic", "/server/statistic/main.jsp");
		serverPathMap.put("ucenter", "/server/ucenter/main.jsp");
		serverPathMap.put("scenter", "/server/scenter/main.jsp");
		serverPathMap.put("award", "/server/award/main.jsp");
		serverPathMap.put("fee", "/server/fee/main.jsp");
	}

	/**
	 * 根据当前账号信息获取可选择系统的相关信息
	 * @param 当前账号名称
	 * @return List数组，数组三列，分别记录系统中文名称、是否可用、用于选择匹配的字符串
	 */
	private List<String[]> serverInfo(String username) {
		// 获取当前用户的权限
		Collection<GrantedAuthority> rights = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		// 用于存储当前账号可进入的系统信息
		List<String[]> serverList = new ArrayList<String[]>();
		
		// 如果拥有相应系统的访问权限，则将该系统信息添加到可访问系统列表serverList
		if (rights.contains(new GrantedAuthorityImpl("ROLE_ACCESS_BASIS"))) {
			serverList.add(SERVER_INFO[0]);
		}
		if(rights.contains(new GrantedAuthorityImpl("ROLE_ACCESS_EXPERT"))){
			serverList.add(SERVER_INFO[1]);
		}
		if (rights.contains(new GrantedAuthorityImpl("ROLE_ACCESS_PROJECT"))) {
			serverList.add(SERVER_INFO[2]);
		}
		if (rights.contains(new GrantedAuthorityImpl("ROLE_ACCESS_STATISTIC"))) {
			serverList.add(SERVER_INFO[3]);
		}
		if (rights.contains(new GrantedAuthorityImpl("ROLE_ACCESS_UCENTER"))) {
			serverList.add(SERVER_INFO[4]);
		}
		if (rights.contains(new GrantedAuthorityImpl("ROLE_ACCESS_SCENTER"))) {
			serverList.add(SERVER_INFO[5]);
		}
		if (rights.contains(new GrantedAuthorityImpl("ROLE_ACCESS_AWARD"))) {
			serverList.add(SERVER_INFO[6]);
		}
		if (rights.contains(new GrantedAuthorityImpl("ROLE_ACCESS_FEE"))) {
			serverList.add(SERVER_INFO[7]);
		}
	
		return serverList;
	}
	
	// 移入accountService
//	/**
//	 * 获取当前通过认证的账号名称
//	 */
//	private String securityUsername() {
//		// 获得当前通过认证的用户上下文信息
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		// 获得当前通过认证用户的账号名
//		String username;
//		if (principal instanceof UserDetails) {// 如果上下文信息为UserDails实例，则调用该接口的getUsername方法获取账号名
//		username = ((UserDetails)principal).getUsername();
//		} else {// 如果上下文信息不是UserDails实例，则它就是账号名
//		username = principal.toString();
//		}
//		return username;
//	}

	/**
	 * 基础数据库系统，right页面
	 */
	public String basisRight() {
		homeNews();
		homeNotice();
		homeToDo();
		homeRemind();
		return SUCCESS;
	}
	
	/**
	 * 专家数据库系统，right页面
	 */
	public String expertRight() {
		homeNews();
		homeNotice();
		return SUCCESS;
	}

	/**
	 * 统计分析与决策系统，right页面
	 */
	public String statisticRight() {
		homeNews();
		homeNotice();
		return SUCCESS;
	}
	
	/**
	 * 奖励管理系统，right页面
	 */
	public String awardRight() {
//		Map session = ActionContext.getContext().getSession();
//		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
//		int accountType = loginer.getCurrentType();
//		if(accountType == 9){//教师账号登陆
//			return "teacher";
//		}
		homeNews();
		homeNotice();
		return SUCCESS;
	}
	
	/**
	 * 经费管理系统，right页面
	 */
	public String feeRight() {
//		Map session = ActionContext.getContext().getSession();
//		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
//		int accountType = loginer.getCurrentType();
//		if(accountType == 9){//教师账号登陆
//			return "teacher";
//		}
		homeNews();
		homeNotice();
		return SUCCESS;
	}
	
	/**
	 * 用户信息中心，right页面
	 */
	public String ucenterRight() {
		homeNews();
		homeNotice();
		homeChat();
		homeApply();
		return SUCCESS;
	}
	
	/**
	 * 系统管控中心，right页面
	 */
	public String scenterRight() {
		homeNews();
		homeNotice();
		return SUCCESS;
	}
	
	/**
	 * 项目管理系统，right页面
	 */
	@SuppressWarnings("rawtypes")
	public String projectRight() {
		homeNews();
		homeNotice();
		homeToDo();
		homeRemind();
		businessManagement();//业务日程
		reviewDeadlineRemind();//专家评审截止时间提醒
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		//研究人员待处理事宜
		if (loginer.getCurrentType().within(AccountType.EXPERT, AccountType.STUDENT)) {
			Map teacherProjectBean = new HashMap();
			Map teacherProjectBeanMap = new HashMap();
			Map gmap = generalService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "general");
			Map kmap = keyService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "key");
			Map bmap = instpService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "instp");
			Map pmap = postService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "post");
			Map emap = entrustService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "entrust");
			if(gmap != null){//一般项目
				teacherProjectBean.putAll(gmap);
			}
			if(kmap != null){//重大攻关项目
				teacherProjectBean.putAll(kmap);
			}
			if(bmap != null){//基地项目
				teacherProjectBean.putAll(bmap);
			}
			if(pmap != null){//后期资助项目
				teacherProjectBean.putAll(pmap);
			}
			if(emap != null){//委托应急课题
				teacherProjectBean.putAll(emap);
			}
			Iterator it = teacherProjectBean.entrySet().iterator(); 
			int i = 0;
			while (it.hasNext() && i < 3) {
				Map.Entry entry = (Map.Entry) it.next();
				teacherProjectBeanMap.put(entry.getKey(), entry.getValue());
				i++;
			}
			ServletActionContext.getRequest().setAttribute("teacherProjectBeanMap", teacherProjectBeanMap);
		}
		//专家待评审事宜
		if (loginer.getCurrentType().within(AccountType.EXPERT, AccountType.TEACHER)) {
			Map teacherReviewBean = new HashMap();
			Map teacherReviewBeanMap = new HashMap();
			Map gmap = generalService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(), "general");
			Map kmap = keyService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(), "key");
			Map bmap = instpService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(), "instp");
			Map pmap = postService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(),"post");
			Map emap = entrustService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(), "entrust");
			if(gmap != null){
				teacherReviewBean.putAll(gmap);
			}
			if(kmap != null){
				teacherReviewBean.putAll(kmap);
			}
			if(bmap != null){
				teacherReviewBean.putAll(bmap);
			}
			if(pmap != null){
				teacherReviewBean.putAll(pmap);
			}
			if(emap != null){//委托应急课题
				teacherReviewBean.putAll(emap);
			}
			Iterator it = teacherReviewBean.entrySet().iterator(); 
			int i = 0;
			while (it.hasNext() && i < 3) {
				Map.Entry entry = (Map.Entry) it.next();
				teacherReviewBeanMap.put(entry.getKey(), entry.getValue());
				i++;
			}
			ServletActionContext.getRequest().setAttribute("teacherReviewBeanMap", teacherReviewBeanMap);
		}
		//管理人员待审核事宜( 非8-10，等级高于外部专家)
		if (loginer.getCurrentType().compareWith(AccountType.EXPERT) > 0) {
			Map managerBusinessMap = new HashMap();
			Map gmap = generalService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "general");
			Map kmap = keyService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "key");
			Map bmap = instpService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "instp");
			Map pmap = postService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "post");
			Map emap = entrustService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "entrust");
			if(gmap != null){
				managerBusinessMap.putAll(gmap);
			}
			if(kmap != null){
				managerBusinessMap.putAll(kmap);
			}
			if(bmap != null){
				managerBusinessMap.putAll(bmap);
			}
			if(pmap != null){
				managerBusinessMap.putAll(pmap);
			}
			if(emap != null){
				managerBusinessMap.putAll(emap);
			}
			ServletActionContext.getRequest().setAttribute("managerBusinessMap", managerBusinessMap);
		}
		return SUCCESS;
	}

	/**
	 * 热点新闻
	 */
	@SuppressWarnings("rawtypes")
	private void homeNews() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select n.id, n.title, n.createDate from News n left join n.account a left join n.type sys where 1=1 order by n.createDate desc, n.id desc");
		homeNews = dao.query(hql.toString(), map, 0, 5);
	}

	/**
	 * 通知公告
	 */
	@SuppressWarnings("rawtypes")
	private void homeNotice() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select n.id, n.title, n.createDate from Notice n left join n.account a left join n.type sys where 1=1 order by n.createDate desc, n.id desc");
		homeNotice = dao.query(hql.toString(), map, 0, 5);
	}
	
	/**
	 * 未读消息
	 */
	@SuppressWarnings("rawtypes")
	private void homeChat() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("personId", loginer.getPerson().getId());
		//当前登陆者作为收信人所有的未读消息
		hql.append("select count(ib.friend), ib.sendName, ib.person from InBox ib where ib.viewStatus = 0 and ib.friend = :personId group by ib.sendName, ib.person ");
		homeChat = dao.query(hql.toString(), map, 0, 5);
	}
	
	/**
	 * 好友申请
	 */
	@SuppressWarnings("rawtypes")
	private void homeApply() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("personId", loginer.getPerson().getId());
		hql.append("select f.id, f.friendName, f.reason from Friend f where f.type = 0 and f.person.id = :personId ");
		homeApply = dao.query(hql.toString(), map);
	}
	
	
	/**
	 * 备忘提醒
	 */
	@SuppressWarnings("rawtypes")
	private void homeRemind() {
		Map map = viewService.viewMemo(1);
		homeRemind = (map.get("homeRemind") == null) ? null : (List<Object>) map.get("homeRemind");
	}
	
	/**
	 * 待办事宜
	 */
	private void homeToDo() {
	}
	
	/**
	 * 业务日程
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private void businessManagement() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		businessManagement = businessService.getBusinessByAccount(loginer.getCurrentAccountId());
	}
	
	/**
	 * 专家评审截止时间提醒
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private void reviewDeadlineRemind() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		if (loginer.getCurrentType().within(AccountType.EXPERT, AccountType.TEACHER)){
			Map reviewDeadlineBeanMap = new HashMap();
			Map gtmap = generalService.getReviewDeadlineByAccount(loginer.getCurrentAccountId(), "general");
			if(gtmap != null){
				reviewDeadlineBeanMap.putAll(gtmap);
			}
			Map ktmap = keyService.getReviewDeadlineByAccount(loginer.getCurrentAccountId(), "key");
			if(ktmap != null){
				reviewDeadlineBeanMap.putAll(ktmap);
			}
			Map btmap = instpService.getReviewDeadlineByAccount(loginer.getCurrentAccountId(), "instp");
			if(btmap != null){
				reviewDeadlineBeanMap.putAll(btmap);
			}
			Map ptmap = postService.getReviewDeadlineByAccount(loginer.getCurrentAccountId(), "post");
			if(ptmap != null){
				reviewDeadlineBeanMap.putAll(ptmap);
			}
			Map etmap = entrustService.getReviewDeadlineByAccount(loginer.getCurrentAccountId(), "entrust");
			if(etmap != null){
				reviewDeadlineBeanMap.putAll(etmap);
			}
			ServletActionContext.getRequest().setAttribute("reviewDeadlineBeanMap", reviewDeadlineBeanMap);
		}
	}

	/**
	 * 获取首页要显示的图表数据信息
	 * @return
	 */
	public List<Map> getHomeStat(){
		List<Object[]> homeStatList = dao.query("select m.id, m.title, m.chartConfig, m.type from MDXQuery m where m.isHomeShow = 1 order by m.date desc");
		homeStat = new ArrayList<Map>();
		for (Object[] object : homeStatList) {
			Map t = new HashMap();
			t.put("mdxid", object[0]);
			t.put("title", object[1]);
			t.put("chartConfig", object[2]);
			t.put("type", object[3]);
			homeStat.add(t);
		}
		JSONArray jsonHomeStat = JSONArray.fromObject(homeStat);
		return jsonHomeStat;
	}
	
	// 统计分析报告
	public List<Object> getStatReport() {
		return statReport;
	}
	
	//人员统计
	@SuppressWarnings("rawtypes")
	public List<Object> getPersonStat() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select m.id, m.title, m.date, m.type from MDXQuery m where m.type='person' order by m.date desc");
		personStat = dao.query(hql.toString(), map, 0, 5);
		return personStat;
	}
	
	//机构统计
	@SuppressWarnings("rawtypes")
	public List<Object> getUnitStat() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select m.id, m.title, m.date, m.type from MDXQuery m where m.type='unit' order by m.date desc");
		unitStat = dao.query(hql.toString(), map, 0, 5);
		return unitStat;
	}
	
	//项目统计
	@SuppressWarnings("rawtypes")
	public List<Object> getProjectStat() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select m.id, m.title, m.date, m.type from MDXQuery m where m.type like 'project%' order by m.date desc");
		projectStat = dao.query(hql.toString(), map, 0, 5);
		return projectStat;
	}
	
	//成果统计
	@SuppressWarnings("rawtypes")
	public List<Object> getProductReport() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select m.id, m.title, m.date, m.type from MDXQuery m where m.type like 'product%' order by m.date desc");
		productReport = dao.query(hql.toString(), map, 0, 5);
		return productReport;
	}
	
	//奖励统计
	@SuppressWarnings("rawtypes")
	public List<Object> getAwardStat() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select m.id, m.title, m.date, m.type from MDXQuery m where m.type='award' order by m.date desc");
		awardStat = dao.query(hql.toString(), map, 0, 5);
		return awardStat;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerPath() {
		return serverPath;
	}
	public List<String[]> getUserList() {
		return userList;
	}

	public void setUserList(List<String[]> userList) {
		this.userList = userList;
	}

	public List<String[]> getServerList() {
		return serverList;
	}
	public List<Object> getHomeNews() {
		return homeNews;
	}
	public List<Object> getHomeNotice() {
		return homeNotice;
	}
	public List<Object> getHomeToDo() {
		return homeToDo;
	}
	public List<Object> getHomeRemind() {
		return homeRemind;
	}
	public List<Object> getBusinessManagement() {
		return businessManagement;
	}
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	public void setKeyService(IKeyService keyService) {
		this.keyService = keyService;
	}
	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}
	public void setPostService(IPostService postService) {
		this.postService = postService;
	}
	public void setEntrustService(IEntrustService entrustService) {
		this.entrustService = entrustService;
	}
	public void setViewService(IViewService viewService) {
		this.viewService = viewService;
	}

	@SuppressWarnings("static-access")
	public void setMessage(Object message) {
		this.message = message;
	}

	public static Object getMessage() {
		return message;
	}
	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}
	public void setPassportService(IPassportService passportService){
		this.passportService = passportService;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public List<Object> getHomeChat() {
		return homeChat;
	}

	public List<Object> getHomeApply() {
		return homeApply;
	}
	
}
