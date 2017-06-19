package csdc.tool.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import csdc.tool.bean.LoginInfo;
import csdc.dao.IBaseDao;
import csdc.model.Account;
import csdc.service.imp.AccountService;
import csdc.tool.MD5;
import org.springframework.beans.factory.annotation.Autowired;


/** 

* @Description: 自定义认证逻辑，供认证过滤器MyAuthenticationFilter调用 
* @author wangming 
* @date 2014-9-9 上午10:30:04 
*  
*/ 
public class MyAuthentication {

	@Autowired
	private AccountService accountService;// 账号管理接口
	@Autowired
	protected IBaseDao baseDao;

	public int getAuthenticationStatus(HttpServletRequest request) {
		// 获得请求的参数
		String username = request.getParameter("j_username");// 此处必须使用j_username，在spring框架内部定义了一个常量
		String password = request.getParameter("j_password");

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
		Account account = accountService.getAccountByName(username);
		String md5Password = MD5.getMD5(password);		
		
		if (account == null || !md5Password.equals(account.getPassword())) {
			return 5;// 用户名或者密码错误
		}
		
		if (account.getStatus() == 0) {
			return 6;//账号未启用
		} else {
			Date currenttime = new Date();
			if (currenttime.after(account.getExpireDate())) {
				account.setStatus(0);
				baseDao.modify(account);
				return 6;// 该用户已停用
			}
		}
		
		return 0;
	}
}
