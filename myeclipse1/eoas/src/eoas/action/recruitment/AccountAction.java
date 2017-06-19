package eoas.action.recruitment;


import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


import eoas.tool.MD5;
import eoas.tool.RandomNumUtil;
import eoas.bean.Account;
import eoas.service.IAccountService;


public class AccountAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private String accountType, email, password, repassword, validCode;
	private Account account;


	private boolean valid;// 是否有效，用于判断帐号等
	private ByteArrayInputStream inputStream;// 输出验证码


	public HttpServletRequest request;
	private IAccountService accountService;
	
/*	public String toIndex() {
		return SUCCESS;
	}*/
	
	public String register() {
		if (!accountService.checkAccount(account.getEmail())) {// 检测邮箱是否可用
			request.setAttribute("tip", "此帐号已被注册");
			return INPUT;
		}
		
		account.setAccountType(account.getAccountType());
		account.setEmail(account.getEmail());
		account.setPassword(MD5.getMD5(account.getPassword()));
		accountService.add(account);
		return SUCCESS;
	}
	

	

	public String login() {
		Map session = ActionContext.getContext().getSession();
		String random = (String) session.get("random");// 读取缓存在session中的验证码
		System.out.println(validCode);
		if(random == null || random.isEmpty() || !(validCode.trim().equals(random))) {
			this.addFieldError("tip", "验证码错误，请重新输入");
		}
		email = account.getEmail();
		password = MD5.getMD5(account.getPassword());

		if(accountService.checkByEmailAndPassword(email, password) == true) {
			session.put("account", account);
			account.setPassword(password);
			session.put("email",email );
			return SUCCESS;
		} else {
			return INPUT;
		}
	}
	
	public String toModifyPassword() {
		
		return SUCCESS;
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
}