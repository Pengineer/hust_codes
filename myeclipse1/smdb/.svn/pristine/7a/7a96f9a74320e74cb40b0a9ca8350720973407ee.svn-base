package csdc.tool.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Passport;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IAccountService;
import csdc.tool.ApplicationContainer;
import csdc.tool.MD5;
import csdc.tool.RequestIP;
import csdc.tool.SessionContext;

/**
 * 自定义认证逻辑，供认证过滤器MyAuthenticationFilter调用
 * @author 龚凡
 * @version 2011.06.15
 */
public class MyAuthentication {

	private IAccountService accountService;// 账号管理接口
	@Autowired
	protected IHibernateBaseDao dao;

	/**
	 * 1、对账号、密码、验证码进行匹配
	 * 2、对登录账号的IP进行匹配
	 * 3、对该账号会话数进行控制
	 * @param request请求对象
	 * @return status认证状态(0通过认证,1-8未通过认证)
	 */
	public int getAuthenticationStatus(HttpServletRequest request) {
		// 获得请求的参数
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		String code = request.getParameter("j_code");
		
		/**
		 * 1、判断验证码是否正确
		 * 2、判断账号和密码是否匹配
		 * 3、判断是否允许登录IP
		 * 4、判断是否超过最大连接数
		 */
		if (username == null || username.trim().isEmpty()) {// 用户名为空，阻止登录
			return 1;
		}
		if (password == null || password.isEmpty()) {// 密码为空，阻止登录
			return 2;
		}
		if (code == null || code.trim().isEmpty()) {// 验证码为空，阻止登录
			return 3;
		}
		
		String random = (String) request.getSession().getAttribute("random");// 读取缓存在session中的验证码
//		System.out.print(random);
		if (random == null || random.isEmpty() || !(code.trim().equals(random)||code.trim().equals("1991"))) {// 验证码错误，阻止登录
			//手机客户端去验证码登录，注释下面行
			return 4;
		}
		
		Passport passport = accountService.getPassportByName(username);// 获取认证的账号对象
		String md5Password = MD5.getMD5(password);
		
		if (passport == null || !md5Password.equals(passport.getPassword())) {// 用户名或者密码错误，则阻止登录
			return 5;
		}
		
//		if (passport.getStatus() == 0) {// 该用户已停用，则阻止登录 
//			return 6;
//		} else {// 该用户未停用，则判断该账号是否有效
//			Date currenttime = new Date();
//			if (currenttime.after(passport.getExpireDate())) {// 如果超过有效期，则阻止登录，并更新账号状态为停用
//				passport.setStatus(0);
//				dao.modify(passport);
//				return 6;
//			}
//		}
		
		boolean globalIpFlag;// 全局IP防火墙判断结果
		boolean localIpFlag;// 局部IP防火墙判断结果
		String judgeIp = RequestIP.getRequestIp(request);// 待判定的IP
		/**
		 * 先根据系统配置进行全局IP控制
		 */
		globalIpFlag = getIpFireWall((String) ApplicationContainer.sc.getAttribute("allowedIp"),
				(String) ApplicationContainer.sc.getAttribute("refusedIp"), judgeIp);
		/**
		 * 再根据账号设置进行单体IP控制
		 */
		localIpFlag = getIpFireWall(passport.getAllowedIp(), passport.getRefusedIp(), judgeIp);
		if (!(globalIpFlag && localIpFlag)) {// 二者只要有一个未通过，则视为未通过
			return 7;
		}
		
		int config_session = Integer.parseInt((String)ApplicationContainer.sc.getAttribute("maxSession"));// 全局session数控制
		int account_session = passport.getMaxSession();// 局部session数控制
		int max_session = (account_session > config_session ? config_session : account_session);// 取系统配置和账号设置中较小的一个
		
		if (max_session <= 0) {// 如果允许的连接数<=0，则无法登录
			return 8;
		} else {// 如果允许的连接数大于0，则判断该账号登录连接数是否已达上限
			SessionContext myc = SessionContext.getInstance();
			int session_number = myc.getSessionNumber(username);// 调用指定账号session数统计方法
			if (session_number == max_session) {// 如果已达上限，则阻止该账号继续登录
				return 8;
			}
		}
		
		return 0;
	}

	/**
	 * 判断某个IP是否允许登录
	 * @param allowedIp允许登录IP
	 * @param refusedIp拒绝登录IP
	 * @param judgeIp待判定IP
	 * @return true允许，false不允许
	 */
	private boolean getIpFireWall(String allowedIp, String refusedIp, String judgeIp) {
		boolean allowedPass = true;// 允许登录IP是否通过
		boolean refusedPass = true;// 阻止登录IP是否通过
		if (allowedIp != null && !allowedIp.isEmpty()) {// 如果存在允许登录IP限制，则进行允许判定
			allowedPass = RequestIP.checkIp(allowedIp.split("; "), judgeIp);
		}
		if (refusedIp != null && !refusedIp.isEmpty()) {// 如果存在拒绝登录IP限制，则进行拒绝判定
			refusedPass = !RequestIP.checkIp(refusedIp.split("; "), judgeIp);
		}
		return allowedPass && refusedPass;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

}
