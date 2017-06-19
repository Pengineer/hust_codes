package org.csdc.tool.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.csdc.bean.Application;
import org.csdc.bean.GlobalInfo;
import org.csdc.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;

import org.csdc.model.Account;
import org.csdc.service.imp.AccountService;
import org.csdc.tool.ApplicationContainer;
import org.csdc.tool.MD5;
import org.csdc.tool.RequestIP;
import org.csdc.tool.SessionContext;

/**
 * 自定义认证逻辑，供认证过滤器MyAuthenticationFilter调用
 * @author 龚凡
 * @version 2011.06.15
 */
public class MyAuthentication {

	@Autowired
	private AccountService accountService;// 账号管理接口
	@Autowired
	protected IBaseDao baseDao;

	/**
	 * 1、对账号、密码、验证码进行匹配
	 * 2、对登录账号的IP进行匹配
	 * 3、对该账号会话数进行控制
	 * @param request请求对象
	 * @return status认证状态(0通过认证,1-8未通过认证)
	 */
	public int getAuthenticationStatus(HttpServletRequest request) {
		// 获得请求的参数
		String username = request.getParameter("j_username");//此处必须使用j_username，在spring框架内部定义了一个常量
		String password = request.getParameter("j_password");
//		String code = request.getParameter("code");
				
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
			
		Account account = accountService.getAccountByAccountName(username);
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
}
