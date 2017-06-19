package csdc.tool.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.Sysoption;
import csdc.service.IAccountService;
import csdc.tool.MD5;

public class MyAuthentication {
	private IAccountService accountService;
	public int getAuthenticationStatus(HttpServletRequest request) {
		//请求参数
		String email = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		String code = request.getParameter("rand");
		String label = request.getParameter("label");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("email", email);
		
		/**
		 * 1、判断验证码
		 * 2、判断帐号和密码是否匹配
		 * 
		 */
		if (email == null || email.trim().isEmpty()) {//账号名为空，阻止登录
			return 1;
		}
		if (password == null || password.isEmpty()) {// 密码为空，阻止登录
			return 2;
		}
		if (code == null || code.trim().isEmpty()) {// 验证码为空，阻止登录
			return 3;
		}
		
		String random = (String) request.getSession().getAttribute("random");//读取缓存在session中的验证码
		if (random == null || random.isEmpty() || !(code.trim().equals(random))) {//验证码错误，阻止登录
			return 4;
		}
		Account account = (Account)accountService.load(Account.class.getName()+".find", map);//获取认证的账号对象
		String md5Password = MD5.getMD5(password);
		if(account == null || !md5Password.equals(account.getPassword())) {//用户名密码错误，阻止登录
			return 5;
		}
		return 0;
	}
	
	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
}

