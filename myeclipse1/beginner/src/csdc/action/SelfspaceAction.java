// ========================================================================
// 文件名: SelfspaceAction.java
//
// 文件说明:
//     本文件主要实现用户个人资料的管理，包括查看、修改个人信息，修改密码，以及
// 个人主页等功能。各个action与页面的对应关系查看struts-Selfspace.xml文件。
//
// 历史记录:
// 2010-03-03  龚凡                        创建文件，完成个人信息的查看与修改。
// ========================================================================
package csdc.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.User;
import csdc.bean.common.Visitor;
import csdc.service.IUserService;

import csdc.tool.MD5;

public class SelfspaceAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private IUserService userservice;// 用户管理模块接口
	private String opassword, password, repassword;//旧密码，新密码，重输新密码
	private String resetPasswordCode;// 密码重置校验码
	private String userid, username;

	/**
	 * 查看个人信息
	 * @return SUCCESS转到User查看
	 */
	public String viewInfo(){
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		userid = visitor.getUser().getId();
		return SUCCESS;
	}
	
	/**
	 * 修改个人信息
	 * @return SUCCESS转到User加载
	 */
	public String loadInfo(){
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		userid = visitor.getUser().getId();
		return SUCCESS;
	}

	/**
	 * 修改密码
	 * @return SUCCESS进入密码修改页面
	 */
	public String toModifyPassword(){
		return SUCCESS;
	}

	/**
	 * 修改密码
	 * @return SUCCESS修改成功页面
	 */
	public String modifyPassword() {
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = visitor.getUser();
		//核对原密码是否正确
		opassword = MD5.getMD5(opassword);
		if (!opassword.equals(user.getPassword())){
			request.setAttribute("tip", "原始密码输入不正确");
			return INPUT;
		}
		//修改密码
		password = MD5.getMD5(password);
		user.setPassword(password);
		userservice.modify(user);
		visitor.setUser(user);
		ActionContext.getContext().getSession().put("visitor", visitor);
		return SUCCESS;
	}

	/**
	 * 修改密码校验
	 */
	public void validateModifyPassword() {
		if (opassword == null || opassword.isEmpty()) {
			this.addFieldError("knowError", "原始密码不得为空");
		}
		if (password == null || password.isEmpty()) {
			this.addFieldError("knowError", "新密码不得为空");
		} else if (repassword == null || repassword.isEmpty()) {
			this.addFieldError("knowError", "重复密码不得为空");
		} else if (!password.equals(repassword)) {
			this.addFieldError("knowError", "两次输入的密码不一样");
		}
	}

	/**
	 * 进入密码重置页面
	 * @return SUCCESS进入密码重置页面
	 */
	public String toResetPassword() {
		User user = userservice.getByUsername(username);
		// 检验校验码
		if (resetPasswordCode.equals(user.getPwRetrieveCode())) {
			ActionContext.getContext().getSession().put("userid", user.getId());
			return SUCCESS;
		} else {
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("tip", "密码重置校验码错误");
			return INPUT;
		}
	}

	/**
	 * 密码重置校验
	 */
	public void validateToResetPassword() {
		if (username == null || username.isEmpty()) {
			this.addFieldError("knowError", "用户名不得为空");
		}
		if (resetPasswordCode == null || resetPasswordCode.isEmpty()) {
			this.addFieldError("knowError", "重置密码校验码不得为空");
		}
	}

	/**
	 * 重置密码
	 * @return SUCCESS返回成功提示
	 */
	public String resetPassword() {
		userid = (String) ActionContext.getContext().getSession().get("userid");
		User user = (User) userservice.query(User.class, userid);
		user.setPassword(MD5.getMD5(password));
		// 重置后修改密码重置校验码
		user.setPwRetrieveCode(MD5.getMD5(user.getUsername()));
		userservice.modify(user);
		return SUCCESS;
	}

	/**
	 * 重置密码校验
	 */
	public void validateResetPassword() {
		if (password == null || password.isEmpty()) {
			this.addFieldError("knowError", "新密码不得为空");
		} else if (repassword == null || repassword.isEmpty()) {
			this.addFieldError("knowError", "重复密码不得为空");
		} else if (!password.equals(repassword)) {
			this.addFieldError("knowError", "两次输入的密码不一样");
		}
	}

	public String getOpassword() {
		return opassword;
	}
	public void setOpassword(String opassword) {
		this.opassword = opassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getResetPasswordCode() {
		return resetPasswordCode;
	}
	public void setResetPasswordCode(String resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setUserservice(IUserService userservice) {
		this.userservice = userservice;
	}
}