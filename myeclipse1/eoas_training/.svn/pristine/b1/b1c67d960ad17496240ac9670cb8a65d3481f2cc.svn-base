package csdc.action.oa;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Person;
import csdc.service.IBaseService;
import csdc.tool.MD5;


@SuppressWarnings("unchecked")
public class LoginAction extends ActionSupport {

	/**
	 * OA 登录
	 */
	private static final long serialVersionUID = 1L;
	private IBaseService baseService;
	private String email;// 用户账号
	private String password;// 用户密码
	private String validCode;// 校验码
	private boolean valid;// 是否有效，用于判断帐号等
	private ByteArrayInputStream inputStream;// 输出验证码
	
	public HttpServletRequest request;
	private Account account;
	private Person person;

	/** 至OA登陆界面 */
	public String loginUI() throws Exception {
		return "loginUI";
	}

	/** OA登陆 */
	@SuppressWarnings("rawtypes")
	public String oaLogin() throws Exception {
		Map map = ActionContext.getContext().getSession();
		String random = (String) map.get("random");// 读取缓存在session中的验证码
		email = account.getEmail();
		map.put("email", email);
		password = MD5.getMD5(account.getPassword());
		account = (Account)baseService.load(Account.class.getName()+".find", map);
		map.put("account", account);
		return SUCCESS;
	}

	// ---get & set---
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

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	
}
