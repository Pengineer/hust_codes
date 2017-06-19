package csdc.action.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import csdc.bean.Account;
import csdc.bean.Passport;
import csdc.service.IAccountService;
import csdc.service.IPassportService;
import csdc.tool.SessionContext;
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
	
	private IAccountService accountService;// 账号管理接口
	private IPassportService passportService;
	private List<String[]> userList;// 可选的账号信息
	private String accountId;// 账号所属id, 最近一次登录的账号ID；
	private String username;// 用户名
	private String newAccountId;//新的账号id

	/**
	 * 描述：登录认证的处理
	 * @return status 认证结果
	 */
	public String login(){
		// 获取从mobile端登录的标识，并返回登录认证结果status
		Integer mobileStatus =  (Integer) session.get("mobileStatus");
		//loginStatus登录验证结果和账号类型放入jsonMap传回手机客户端
		int loginStatus = (null != mobileStatus) ? mobileStatus + 1 : 0; //loginStatus = 1 表示成功登录;
		jsonMap.put("loginStatus", loginStatus);
		if(loginStatus == 1){
			String accountType = (null != loginer) ? loginer.getCurrentType().toString() : "未知类型";
			jsonMap.put("accountType", accountType);
		}
		return SUCCESS;			
	}

	/**
	 * 通行证验证成功后进入选账号
	 * @return
	 */
	public String toSelectAccount() {
		List<String[]> accounts = new ArrayList<String[]>();
		// 获取当前通过认证的账号名
		username = accountService.securityUsername();
		// 获取当前用户可选账号
		userList = accountService.userInfo(username);
		if(userList.size() == 0){
			jsonMap.put(GlobalInfo.ERROR_INFO, "该通行证没有账号可选");
			return INPUT;
		} else {
			for (String[] user : userList) {
				String[] account = new String[4];
				if(user[5].equals("1")){//判断账号是否为启用状态
					for(int i=0;i<4;i++){
						account[i]=user[i+2];
					}
					accounts.add(account);
				}
			}		
			jsonMap.put("accounts", accounts);	
			return SUCCESS;
		}
	}
	
	public String selectAccount() {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		username = accountService.securityUsername();
		// accountId校验
		List<Account> accountList = accountService.getAccountListByName(username);
		
		for (Account account : accountList) {// 找到选择的account，设置loginer
			if(account.getId().trim().equals(accountId.trim())) {
				newAccountId = account.getId();
				break;
			}
		}
		
		Account account = (Account) dao.query(Account.class, newAccountId);// 获取当前登录账号对象
		Date currenttime = new Date();
		if (currenttime.after(account.getExpireDate())) {// 如果超过有效期，则阻止登录，并更新账号状态为停用
			account.setStatus(0);
			dao.modify(account);
			passportService.updatePassport(account.getPassport().getId());
			jsonMap.put(GlobalInfo.ERROR_INFO, "该账号已超过有效期");
			return INPUT;
		}
		else {
			switch (accountType) {
			case ADMINISTRATOR://管理员
			case MINISTRY://部级管理人员
				jsonMap.put("statisticStatus", 1);//统计分析权限标志位（1有权限||0无权限）
				jsonMap.put("accountStatus", 1);//个人信息相关标志位（1管理人员||0研究人员）
				break;
			case PROVINCE://省级管理人员
			case LOCAL_UNIVERSITY:	
			case MINISTRY_UNIVERSITY://高校管理人员
			case DEPARTMENT://院系管理人员	
			case INSTITUTE://基地管理人员
				jsonMap.put("statisticStatus", 0);
				jsonMap.put("accountStatus", 1);
				break;	
			case EXPERT://外部专家	
			case TEACHER://教师		
			case STUDENT://学生	
				jsonMap.put("statisticStatus", 0);
				jsonMap.put("accountStatus", 0);
				break;			
			}
			return SUCCESS;
		}
	}
	
	/**
	 * 进入切换账号页面
	 * @return
	 */
	public String toSwitchAccount() {
		List<String[]> accounts = new ArrayList<String[]>();
		username = accountService.securityUsername();// 获取当前通过认证的账号名		
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

	public String switchAccount() {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		// 获取当前切换的账号对象
		AccountType accountType = loginer.getCurrentType();
		List<Account> accountList = accountService.getAccountListByName(accountService.securityUsername());

		for (Account account : accountList) {// 找到选择的account，设置loginer
			if(account.getId().trim().equals(accountId.trim())) {
				newAccountId = account.getId();
				break;
			}
		}
//		Account account = (Account) dao.query(Account.class, newAccountId);
		//String serverPath = "/***.jsp";//跳转到消息系统页面
		switch (accountType) {
		case ADMINISTRATOR://管理员
		case MINISTRY://部级管理人员
			jsonMap.put("statisticStatus", 1);//统计分析权限标志位（1有权限||0无权限）
			jsonMap.put("accountStatus", 1);//个人信息相关标志位（1管理人员||0研究人员）
			break;
		case PROVINCE://省级管理人员
		case LOCAL_UNIVERSITY:	
		case MINISTRY_UNIVERSITY://高校管理人员
		case DEPARTMENT://院系管理人员	
		case INSTITUTE://基地管理人员
			jsonMap.put("statisticStatus", 0);
			jsonMap.put("accountStatus", 1);
			break;	
		case EXPERT://外部专家	
		case TEACHER://教师		
		case STUDENT://学生	
			jsonMap.put("statisticStatus", 0);
			jsonMap.put("accountStatus", 0);
			break;			
		}
		return SUCCESS;
	}

	public String getCurrentAccount(){
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		String accountId1 = loginer.getCurrentAccountId();
		username = accountService.securityUsername();
		userList = accountService.userInfo(username);
		for (String[] user : userList) {
			String[] account = new String[4];
			if(user[2].equals(accountId1)){
				for(int i=0;i<4;i++){
					account[i]=user[i+2];
				}
			}	
			jsonMap.put("account", account);
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
	
	//未知错误处理
	public String toError(){
		jsonMap = null;
		return SUCCESS;
	}

	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Override
	public String pageName() {
		// TODO Auto-generated method stub
		return PAGENAME;
	}
	public void setPassportService(IPassportService passportService){
		this.passportService = passportService;
	}
}
