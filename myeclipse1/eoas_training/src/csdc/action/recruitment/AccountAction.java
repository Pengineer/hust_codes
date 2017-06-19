package csdc.action.recruitment;

import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.If;

import sun.java2d.loops.MaskBlit;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.sun.xml.internal.bind.v2.model.core.ID;

import csdc.bean.Account;
import csdc.bean.AccountRole;
import csdc.bean.Assess;
import csdc.bean.Asset;
import csdc.bean.Attendance;
import csdc.bean.Expert;
import csdc.bean.Mail;
import csdc.bean.Person;
import csdc.bean.Resume;
import csdc.bean.Role;
import csdc.bean.common.Visitor;
import csdc.service.IAccountService;
import csdc.service.IBaseService;
import csdc.tool.MD5;
import csdc.tool.RandomNumUtil;
import csdc.tool.SignID;


@SuppressWarnings("unchecked")
public class AccountAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private String accountType, email, password, opassword, repassword, validCode;
	private String accountId, redir;
	private String tip;
	private String label;
	private Account account;
	private Mail mail;// 邮件
	private boolean valid;// 是否有效，用于判断帐号等
	private ByteArrayInputStream inputStream;// 输出验证码
	public HttpServletRequest request;

	private IAccountService accountService;
	private IBaseService baseService;

	Map jsonMap = new HashMap();
	Map<String, Object> map = new HashMap<String, Object>();
	
	public String toList() {
		return SUCCESS;
	}

	public String list() {
	    ArrayList<Account> accountList = new ArrayList <Account> ();    
	    List<Object[]> aList = new ArrayList<Object[]>();
		Map map = ActionContext.getContext().getSession();
		map.put("email", null);
		//获取所有帐号信息
		accountList =  (ArrayList<Account>) baseService.list(Account.class, null);
		List<AccountRole> accountRoles = new ArrayList();
		String[] item;
		for(Account a : accountList){
			item = new String[4];
			item[0] = a.getEmail();
/*			if(null != a.getBelongId() && "" != a.getBelongId()) {
				Person person = (Person) baseService.load(Person.class, a.getBelongId());
				if(null != person) {
					item[1] = person.getRealName();
				} else {
					item[1] = "暂未填写个人信息";
				}
				
			} else {
				item[1] = "暂未填写个人信息";
			}*/
			item[1] = a.getName();
			item[2] = "";
			map.put("accountId", a.getId());
			
			try {
				accountRoles = baseService.list(AccountRole.class.getName() + ".listAccountRole", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(accountRoles.size()>0) {
				for(int j=0; j<accountRoles.size();j++) {
					Role role = (Role) baseService.load(Role.class, accountRoles.get(j).getRole().getId());
					item[2] += role.getName() + ",";
					
				}
				// 去掉最后的一个逗号
				if (!item[2].equals("")){
					item[2] = item[2].substring(0, item[2].length());
				}
			} else {
				item[2] = "[未分配]";
			}
			
			
/*			accountRoles = baseService.list(AccountRole.class, map);
			for (int i = 0; i < accountRoles.size(); i++) {
				Role role = (Role) baseService.load(Role.class, accountRoles.get(i).getRole().getId());
				item[2] = accountRoles.get(i).getRole().getName() + ";";
			}*/
			
			
			
			
/*			accountRoles = baseService.list(Role.class, map);
			if(accountRoles.size()>0) {
				for(int j=0; j<accountRoles.size();j++) {
					item[2] += accountRoles.get(j).getName() + ",";
				}
				// 去掉最后的一个逗号
				if (!item[2].equals("")){
					item[2] = item[1].substring(0, item[1].length()-1);
				}
			} else {
				item[2] = "[未分配]";
			}*/
			item[3]=a.getId();
			aList.add(item);
		}
		jsonMap.put("aaData", aList);
		
		
		return SUCCESS;
	}
	
	public String view() {
		account =  (Account) baseService.load(Account.class, accountId);
		return SUCCESS;
	}
	
	/**
	 * 退出系统，清除用户的visitor对象
	 * @return
	 */
	public String logout() {
		request.getSession().invalidate();
		return SUCCESS;
	}
	
	public String register() {
		request = ServletActionContext.getRequest();
		accountService.checkAccount(account.getEmail());
		if (!accountService.checkAccount(account.getEmail())) {// 检测邮箱是否可用
			request.setAttribute("tip", "此帐号已被注册");
			return INPUT;
		}
		// 设置主表信息
		String signID = SignID.getInstance().getSignID();
		account.setName(account.getName());
		account.setEmail(account.getEmail());
		account.setPassword(MD5.getMD5(account.getPassword()));
		account.setRegisterDate(new Date());
		account.setLoginCount(0);
		account.setMailboxVerified(0);
		account.setMailboxVerifyCode(MD5.getMD5(signID));
		baseService.add(account);
		
		// 向用户邮箱发送验证链接
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ path + "/";
		String body = account.getName() + "(" + account.getName() + ") 您好,请打开以下链接进行邮箱验证:<br />" + basePath
				+ "account/verifyMailbox.action?accountname=" + account.getName()
				+ "&verifycode=" + account.getMailboxVerifyCode();
		mail = new Mail();
		mail.setSendTo(account.getEmail());
		mail.setSubject("[EOAS] NADR账号邮箱验证");
		mail.setBody(body);
		mail.setIsHtml(1);
		redir = "/user/registerSuccess.action";
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String dologin() {
		String email = accountService.securityUsername();
		Map session = ActionContext.getContext().getSession();
		session.put("email",email );
		String random = (String) session.get("random");// 读取缓存在session中的验证码
		account = (Account)baseService.load(Account.class.getName()+".find", session);
		/*session.put("account", account);*/
/*		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("localhost") || ip.equals("127.0.0.1") ||ip.equals("0:0:0:0:0:0:0:1")) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				ip = addr.getHostAddress();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		account.setLastLoginDate(new Date());
		account.setLastLoginIp(ip);
		if(account.getLoginCount() == null) {
			account.setLoginCount(1);
		} else {
			account.setLoginCount(account.getLoginCount()+1);
		}
		baseService.modify(account);*/
		
		
		this.saveLoginInfo();
		String result = "";
/*		if(getLabel().equals("0")) {
			result = "eoas";
		} else if(getLabel().equals("1")) {
			result = "recruitment";
		}*/
		result = "eoas";
		return result;
	}
	
	public void saveLoginInfo() {
		Map session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("localhost") || ip.equals("127.0.0.1") ||ip.equals("0:0:0:0:0:0:0:1")) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				ip = addr.getHostAddress();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		account.setLastLoginDate(new Date());
		account.setLastLoginIp(ip);
		if(account.getLoginCount() == null) {
			account.setLoginCount(1);
		} else {
			account.setLoginCount(account.getLoginCount()+1);
		}
		try {
			baseService.modify(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Visitor visitor = new Visitor();
		visitor.setAccount(account);	
		session.put("visitor", visitor);
		
	}
	
	public String toIndex() {
		String result = "";
		if(getLabel().equals("0")) {
			result = "eoas";
		} else if(getLabel().equals("1")) {
			result = "recruitment";
		}
		return result;
	}
	
	public String toAdd() {
		return SUCCESS;
	}
	
	public String add() {
		accountService.checkAccount(account.getEmail());
		if (!accountService.checkAccount(account.getEmail())) {// 检测邮箱是否可用
			request.setAttribute("tip", "此帐号已被注册");
			return INPUT;
		}
		account.setName(account.getName());
		account.setEmail(account.getEmail());
		account.setPassword(MD5.getMD5(account.getPassword()));
		account.setRegisterDate(new Date());
		account.setLoginCount(0);
		account.setMailboxVerified(0);
/*		try {
			baseService.add(account);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		try {
			accountService.saveUser(account, null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String toModifyPassword() {
		Map session = ActionContext.getContext().getSession();
		account = (Account) session.get("account");
		return SUCCESS;
	}
	
	public String toModify() {
		account = (Account) baseService.load(Account.class, accountId);
		return SUCCESS;
	}
	
	public String modify() {
		Account acc = (Account) baseService.load(Account.class,accountId);
		account.setId(acc.getId());
		account.setPassword(MD5.getMD5(account.getPassword()));
		if(acc.getLoginCount() > 0) {
			account.setLoginCount(acc.getLoginCount());
		}else {
			account.setLoginCount(0);
		}
		account.setLoginCount(acc.getLoginCount());
		try {
			accountService.saveUser(account, null, true);
			/*accountService.modify(account);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonMap.put("tag", 1);
		return SUCCESS;
/*		if(account.getPassword().equals(MD5.getMD5(opassword))) {
			account.setPassword(MD5.getMD5(password));
			try {
				accountService.modify(account);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
		} else {
			tip = "原始密码输入不正确";
			return INPUT;
		}*/
	}
	/**
	 * 获取验证码
	 * @return 跳转成功
	 */
	public String rand() {
		RandomNumUtil rdnu = RandomNumUtil.Instance();
		// 取得带有随机字符串的图片
		this.setInputStream(rdnu.getImage());
		// 取得随机字符串放入HttpSession
		ActionContext.getContext().getSession().put("random", rdnu.getString());
		return SUCCESS;
	}
	
	public String checkAccount() {
		valid = accountService.checkAccount(account.getEmail());
		return SUCCESS;
	}
	
	public String delete() {
		Map map = new HashMap();
		map.put("accountId", accountId);
		map.put("auditorId", accountId);
/*		List<Assess> assessList = baseService.list(Assess.class.getName() + ".listByAccountOrAuditor", map);
		for (int i = 0; i < assessList.size(); i++) {
			baseService.delete(Assess.class, assessList.get(i).getId());
		}
		
		List<AccountRole> accountRoleList = baseService.list(AccountRole.class, map);
		for (int i = 0; i < accountRoleList.size(); i++) {
			baseService.delete(AccountRole.class, accountRoleList.get(i).getId());
		}
		
		List<Asset> assetList = baseService.list(Asset.class, map);
		for (int i = 0; i < assetList.size(); i++) {
			baseService.delete(Asset.class, assetList.get(i).getId());
		}*/

		baseService.delete(Account.class, accountId);
		Account account = (Account) baseService.load(Account.class, accountId);
		if(null != account) {
			jsonMap.put("result", 0);
		} else {
			jsonMap.put("result", 1);
		}
		return SUCCESS;
	}
	
	public String toRegister() {
		return SUCCESS;
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public String getOpassword() {
		return opassword;
	}

	public void setOpassword(String opassword) {
		this.opassword = opassword;
	}
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
	
	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	


	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRedir() {
		return redir;
	}

	public void setRedir(String redir) {
		this.redir = redir;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}